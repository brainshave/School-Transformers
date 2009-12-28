/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transformers.gui;

import transformers.Matrix;

/**
 *
 * @author szymon
 */
public class BendPanel extends ScalePanel {

    @Override
    public Matrix getMatrix() {
        return new Matrix(1, (Float) xSpinner.getValue(),
                (Float) ySpinner.getValue() ,1);
    }

}
