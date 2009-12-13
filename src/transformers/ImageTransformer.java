/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transformers;

import java.awt.Component;
import java.util.LinkedList;

/**
 *
 * @author szymon
 */
public class ImageTransformer extends Thread {

    private final LinkedList<ToolModifier> modifiers;
    private final int[] image;
    private final int width, height, moveX, moveY;
    private final Component paintDevice;

    public ImageTransformer(LinkedList<ToolModifier> modifiers, int[] image, int width, int height, Component paintDevice) {
        this.modifiers = modifiers;
        this.image = image;
        this.width = width;
        this.height = height;
        this.paintDevice = paintDevice;
        this.moveX = 0;
        this.moveY = 0;
    }

    public void addModifier(ToolModifier mod) {
        synchronized (modifiers) {
            modifiers.addLast(mod);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                ImageTransformer.sleep(1000);
            } catch (InterruptedException ex) {
                do {
                    LinkedList<ToolModifier> localCopyModifiers;
                    synchronized (modifiers) {
                        localCopyModifiers = (LinkedList<ToolModifier>) modifiers.clone();
                    }

                    Matrix matrix = new Matrix();
                    for (ToolModifier mod : localCopyModifiers) {
                        matrix = matrix.multiply(mod.getMatrix());
                    }
                } while (ImageTransformer.interrupted());
            }
        }
    }
}

