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
 * @author szymon
 */
public class ScalePanel extends RotationPanel {

    public ScalePanel() {
        super();
        xSpinner.setModel(new SpinnerNumberModel(1d, 0.01d, 10d, 0.1d));
        ySpinner.setModel(new SpinnerNumberModel(1d, 0.01d, 10d, 0.1d));
        xSpinner.setEditor(new javax.swing.JSpinner.NumberEditor(xSpinner, "#0.000"));
        ySpinner.setEditor(new javax.swing.JSpinner.NumberEditor(ySpinner, "#0.000"));
    }

    @Override
    public Matrix getMatrix() {
        return new Matrix((Float) xSpinner.getValue(), 0,
                0, (Float) ySpinner.getValue());
    }

    @Override
    public Converter getConv() {
        return new Converter<Integer, Double>() {
            @Override
            public Double convertForward(Integer value) {
                if (value < 180) {
                    return ((double) value + 1.8 ) / 180;
                } else if( value == 180) {
                    return (double) 1;
                } else {
                    return (double) (value - 180) / 20 + 1;
                }
            }

            @Override
            public Integer convertReverse(Double value) {
                if(value < 1) {
                    return (int)(value * 180 - 1.8);
                } else if(value == 1) {
                    return 180;
                } else {
                    return (int)(((value - 1) * 20) + 180);
                }

            }
        };
    }
}
