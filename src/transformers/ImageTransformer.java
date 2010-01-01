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
    private int maxX = Integer.MIN_VALUE, minX = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE, minY = Integer.MAX_VALUE;

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

        moveX = 0;
        moveY = 0;
        Matrix matrix = new Matrix();
        for (ToolModifier mod : localCopyModifiers) {
            matrix = matrix.multiply(mod.getMatrix());
            moveX += mod.getMoveX();
            moveY += mod.getMoveY();
        }

        ensureBufferSize();
        int oW = imPanel.getWidth();
        int oH = imPanel.getHeight();
        int oW2 = oW / 2;
        int oH2 = oH / 2;
        int inW2 = inW << 7;
        int inH2 = inH << 7;
        int a, b, c, d;

        int localMaxX = Integer.MIN_VALUE, localMinX = Integer.MAX_VALUE, localMaxY = Integer.MIN_VALUE, localMinY = Integer.MAX_VALUE;

        a = (int) (matrix.a * 256);
        b = (int) (matrix.b * 256);
        c = (int) (matrix.c * 256);
        d = (int) (matrix.d * 256);

        int[] signums = {-1, 1};
        for (int sig1 : signums) {
            for (int sig2 : signums) {
                int x = ((a * (sig1 * (inW) / 2) + b * (sig2 * (inH) / 2) + (oW << 7)) >> 8) + moveX;
                int y = ((c * (sig1 * (inW) / 2) + d * (sig2 * (inH) / 2) + (oH << 7)) >> 8) + moveY;
                if (x < localMinX) {
                    localMinX = x - 5;
                }
                if (x > localMaxX) {
                    localMaxX = x + 5;
                }

                if(y < localMinY) {
                    localMinY = y - 5;
                }
                if (y > localMaxY) {
                    localMaxY = y + 5;
                }
            }
        }

        matrix = matrix.inverse();
        a = (int) (matrix.a * 256);
        b = (int) (matrix.b * 256);
        c = (int) (matrix.c * 256);
        d = (int) (matrix.d * 256);

        //main loop:
        int oX, oY, oXtmp, oYtmp;
        int iX, iY;
        int g;
        int up, down, left, right, i;
        int upProp, downProp, leftProp, rightProp;
        int outOffset = 0;
        int pixel;

        int tmp;
        int[] pixels = {-1, -1, -1, -1};
        int[] colors = {255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
        int[] props = new int[12];

        for (g = 0; g < 8 && !this.isInterrupted(); g += 1) {
            outOffset = g * oW;
            for (oY = g; oY < oH; oY += 8) {
                for (oX = 0; oX < oW; ++oX) {
                    oXtmp = oX - oW2;
                    oYtmp = oY - oH2;
                    iX = a * oXtmp + b * oYtmp + inW2;
                    iY = c * oXtmp + d * oYtmp + inH2;

                    left = (iX >> 8) + moveX;
                    right = left + 1;
                    down = (iY >> 8) + moveY;
                    up = down + 1;

                    rightProp = iX & 0xff;
                    leftProp = 0xff - rightProp;
                    upProp = iY & 0xff;
                    downProp = 0xff - upProp;

                    props[0] = props[1] = props[2] = leftProp * downProp;
                    props[3] = props[4] = props[5] = rightProp * downProp;
                    props[6] = props[7] = props[8] = leftProp * upProp;
                    props[9] = props[10] = props[11] = rightProp * upProp;

                    pixels[0] = pixels[1] = pixels[2] = pixels[3] = -1;

                    if (down > 0 && down < inH) {
                        tmp = down * inW;
                        if (left > 0 && left < inW) {
                            pixels[0] = inBuff[tmp + left];
                        }
                        if (right > 0 && right < inW) {
                            pixels[1] = inBuff[tmp + right];
                        }
                    }

                    if (up > 0 && up < inH) {
                        tmp = up * inW;
                        if (left > 0 && left < inW) {
                            pixels[2] = inBuff[tmp + left];
                        }
                        if (right > 0 && right < inW) {
                            pixels[3] = inBuff[tmp + right];
                        }
                    }

                    tmp = 0;
                    for (i = 0; i < 4; ++i) {
                        colors[tmp] = (pixels[i] & 0xff0000) >> 16;
                        colors[tmp + 1] = (pixels[i] & 0x00ff00) >> 8;
                        colors[tmp + 2] = pixels[i] & 0x0000ff;
                        tmp += 3;
                    }

                    for (i = 0; i < 12; ++i) {
                        colors[i] *= props[i];
                    }

                    pixel = 0;
                    pixel |= (colors[0] + colors[3] + colors[6] + colors[9]) & 0xff0000;
                    pixel |= ((colors[1] + colors[4] + colors[7] + colors[10]) >> 8) & 0x00ff00;
                    pixel |= ((colors[2] + colors[5] + colors[8] + colors[11]) >> 16) & 0x0000ff;

                    outBuff[outOffset] = pixel;
                    ++outOffset;
                }
                //imPanel.repaint(oX, oY, oW, 1);
                outOffset += oW * 7;
            }
        }
        imPanel.getImage().getRaster().setDataElements(0, 0, oW, oH, outBuff);
        //imPanel.repaint(0, 0, oW, oH);

        if (localMinX < minX) {
            minX = localMinX;
        }
        if (localMinY < minY) {
            minY = localMinY;
        }
        if (localMaxX > maxX) {
            maxX = localMaxX;
        }
        if (localMaxY > maxY) {
            maxY = localMaxY;
        }
        if (this.isInterrupted()) {
            for (i = (localMinY >> 3) << 3; i < localMaxY; i += 8) {
                imPanel.repaint(localMinX, i, localMaxX - localMinX, g);
            }
        } else {
            imPanel.repaint(minX, minY, maxX - minX, maxY - minY);
            minX = localMinX;
            maxX = localMaxX;
            minY = localMinY;
            maxY = localMaxY;
        }
        // pomiary czasu
        long runTime = System.nanoTime() - startTime;

        System.out.println("" + (oW * oH) + " pikseli w " + runTime / 1000000 + " ms " + minX + " " + maxX + " " + minY + " " + maxY);
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

