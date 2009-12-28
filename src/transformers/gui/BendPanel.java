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
public class BendPanel extends RotationPanel {

	public BendPanel() {
		super(true);
		xSpinner.setModel(new SpinnerNumberModel(0d, -2d, 2d, 0.025d));
		ySpinner.setModel(new SpinnerNumberModel(0d, -2d, 2d, 0.025d));
        xSpinner.setEditor(new javax.swing.JSpinner.NumberEditor(xSpinner, "#0.000"));
        ySpinner.setEditor(new javax.swing.JSpinner.NumberEditor(ySpinner, "#0.000"));
		xSpinner.setValue(0d);
	}


    @Override
    public Matrix getMatrix() {
        return new Matrix(1, ((Double) xSpinner.getValue()).floatValue(),
                ((Double) ySpinner.getValue()).floatValue() ,1);
    }

	@Override
	public Converter getConv() {
		return new Converter<Integer, Double> () {

			@Override
			public Double convertForward(Integer value) {
				return (value.doubleValue() / 45) - 4;
			}

			@Override
			public Integer convertReverse(Double value) {
				return ((int) (value * 45)) + 180;
			}

		};
	}



}
