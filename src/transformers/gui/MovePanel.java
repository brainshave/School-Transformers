/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transformers.gui;

import javax.swing.SpinnerNumberModel;
import org.jdesktop.beansbinding.Converter;
import transformers.Matrix;

/**
 *
 * @author student
 */
public class MovePanel extends RotationPanel {

    public MovePanel() {
        super(true);
        xSpinner.setModel(new SpinnerNumberModel(0, -1000, 1000, 1));
        ySpinner.setModel(new SpinnerNumberModel(0, -1000, 1000, 1));
        ySlider.setMinimum(-1000);
        ySlider.setMaximum(1000);
        xSlider.setMinimum(-1000);
        xSlider.setMaximum(1000);
        //xSpinner.setEditor(new javax.swing.JSpinner.NumberEditor(xSpinner, "#0.000"));
        //ySpinner.setEditor(new javax.swing.JSpinner.NumberEditor(ySpinner, "#0.000"));
        xSpinner.setValue(0);
    }

    @Override
    public Converter getConv() {
        return new Converter<Integer, Integer>() {

            @Override
            public Integer convertForward(Integer value) {
                return value;
            }

            @Override
            public Integer convertReverse(Integer value) {
                return value;
            }
        };
    }

    @Override
    public Matrix getMatrix() {
        return new Matrix();
    }

    @Override
    public int getMoveX() {
        return - xSlider.getValue();
    }

    @Override
    public int getMoveY() {
        return - ySlider.getValue();
    }
}
