/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transformers;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import transformers.gui.ImagePanel;

/**
 *
 * @author szymon
 */
public class ImageTransformer extends Thread {

    private final LinkedList<ToolModifier> modifiers;
    private int[] inBuff, outBuff;
    private int inW, inH, moveX, moveY, completedLines;
    private final ImagePanel imPanel;

    public ImageTransformer(LinkedList<ToolModifier> modifiers, int[] buff, int width, int height, ImagePanel imPanel) {
        this.modifiers = modifiers;
        synchronized (modifiers) {
            for (ToolModifier mod : modifiers) {
                mod.setNotifiedObject(this);
            }
        }

        this.inBuff = buff;
        this.inW = width;
        this.inH = height;
        this.imPanel = imPanel;
        this.moveX = 0;
        this.moveY = 0;

        this.completedLines = height;
        int outWidth = imPanel.getWidth() + 200;
        int outHeight = imPanel.getHeight() + 200;
        this.outBuff = new int[outWidth * outHeight];
        imPanel.setImage(new BufferedImage(outWidth, outHeight, BufferedImage.TYPE_INT_RGB));
        this.start();
        this.interrupt();
    }

    private void ensureBufferSize() {
        int pdevW = imPanel.getWidth();
        int pdevH = imPanel.getHeight();
        if (pdevW * pdevH > outBuff.length) {
            this.outBuff = null;
            imPanel.setImage(null);
            System.gc();
            pdevW += 200;
            pdevH += 200;
            this.outBuff = new int[pdevW * pdevH];
            imPanel.setImage(new BufferedImage(pdevW, pdevH, BufferedImage.TYPE_INT_RGB));
        }
    }

    private void refreshImage() {
        long startTime = System.nanoTime();
        LinkedList<ToolModifier> localCopyModifiers;
        synchronized (modifiers) {
            localCopyModifiers = (LinkedList<ToolModifier>) modifiers.clone();
        }

        Matrix matrix = new Matrix();
        for (ToolModifier mod : localCopyModifiers) {
            matrix = matrix.multiply(mod.getMatrix());
        }

        matrix = matrix.inverse();
        int a = (int) (matrix.a * 256);
        int b = (int) (matrix.b * 256);
        int c = (int) (matrix.c * 256);
        int d = (int) (matrix.d * 256);

        //main loop:
        ensureBufferSize();
        int oW = imPanel.getWidth();
        int oH = imPanel.getHeight();
        int oW2 = oW / 2;
        int oH2 = oH / 2;
        int inW2 = inW << 7;
        int inH2 = inH << 7;
        int oX, oY, oXtmp, oYtmp;
        int iX, iY;
        int up, down, left, right, i;
        int upProp, downProp, leftProp, rightProp;
        int outOffset = 0, inOffset = 0;
        int inSize = inBuff.length;
        int pixel;
        int pixelUp, pixelDown;
        int[] pixels = {-1, -1, -1, -1};
        int[] colors = {255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
        int[] props = new int[12];

//		while (k < size) {
//                if (this.isInterrupted()) {
//                    return i;
//                }
//                i = k;
//                k += step;
//                end = k < size ? k : size;
//                for (; i < end; ++i) {

//		//
        for (oY = 0; oY < oH && !this.isInterrupted(); ++oY) {
            for (oX = 0; oX < oW; ++oX) {
                oXtmp = oX - oW2;
                oYtmp = oY - oH2;
                iX = a * oXtmp + b * oYtmp + inW2;
                iY = c * oXtmp + d * oYtmp + inH2;

                left = iX >> 8;
                right = left + 1;
                down = iY >> 8;
                up = down + 1;

                rightProp = iX & 0xff;
                leftProp = 0xff - rightProp;
                upProp = iY & 0xff;
                downProp = 0xff - upProp;

                for (i = 0; i < 3; ++i) {
                    props[i] = leftProp * downProp;
                    props[i + 3] = rightProp * downProp;
                    props[i + 6] = leftProp * upProp;
                    props[i + 9] = rightProp * upProp;
                }

                pixels[0] = pixels[1] = pixels[2] = pixels[3] = -1;

                if (down > 0 && down < inH) {
                    if (left > 0 && left < inW) {
                        pixels[0] = inBuff[down * inW + left];
                    }
                    if (right > 0 && right < inW) {
                        pixels[1] = inBuff[down * inW + right];
                    }
                }

                if (up > 0 && up < inH) {
                    if (left > 0 && left < inW) {
                        pixels[2] = inBuff[up * inW + left];
                    }
                    if (right > 0 && right < inW) {
                        pixels[3] = inBuff[up * inW + right];
                    }
                }

                for (i = 0; i < 4; ++i) {
                    colors[3 * i] = (pixels[i] & 0xff0000) >> 16;
                    colors[3 * i + 1] = (pixels[i] & 0x00ff00) >> 8;
                    colors[3 * i + 2] = pixels[i] & 0x0000ff;
                }

                for (i = 0; i < 12; ++i) {
                    colors[i] *= props[i];
                }

                pixel = 0;
                pixel |= ((colors[0] + colors[3] + colors[6] + colors[9]) >> 0) & 0xff0000;
                pixel |= ((colors[1] + colors[4] + colors[7] + colors[10]) >> 8) & 0x00ff00;
                pixel |= ((colors[2] + colors[5] + colors[8] + colors[11]) >> 16) & 0x0000ff;

                outBuff[outOffset] = pixel;
                ++outOffset;
            }
        }
//		int step = 100000;
//		int outSize = oW * oH;
//
//		for(outOffset = 0; outOffset < outSize; outOffset += step) {
//			for(;)
//		}
        //completedLines = oY;
        //++oY;
        imPanel.getImage().getRaster().setDataElements(0, 0, oW, oY, outBuff);
        imPanel.repaint(0, 0, oW, oY);

        // pomiary czasu
        long runTime = System.nanoTime() - startTime;

        System.out.println("" + (oW * oY) + " pikseli w " + runTime / 1000000 + " ms");
    }

    @Override
    public void run() {
        refreshImage();
        while (true) {
            try {
                if (!interrupted()) {
                    System.gc();
                    Thread.sleep(3000);
                }
            } catch (InterruptedException ex) {
                do {
                    refreshImage();
                } while (Thread.interrupted());
            }
        }
    }
}

