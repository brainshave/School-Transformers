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
		this.outBuff = new int[0];
		imPanel.setImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
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
		int inW2 = inW/2;
		int inH2 = inH/2;
		int oX, oY, oXtmp, oYtmp;
		int iX, iY;
		int outOffset = 0, inOffset = 0;
		int inSize = inBuff.length;
		int pixel;

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
				iX = ((a * oXtmp + b * oYtmp) >> 8) + inW2;
				iY = ((c * oXtmp + d * oYtmp) >> 8) + inH2;

				inOffset = iY * inW + iX;
				if (inOffset > 0 && inOffset < inSize) {
					outBuff[outOffset] = inBuff[inOffset];
				} else {
					outBuff[outOffset] = -1;
				}
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
		++oY;
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

