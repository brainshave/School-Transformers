/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RotationPanel.java
 *
 * Created on 2009-12-07, 14:58:47
 */
package transformers.gui;

import javax.swing.SpinnerNumberModel;
import org.jdesktop.beansbinding.Converter;
import transformers.ImageTransformer;
import transformers.Matrix;
import transformers.ToolModifier;

/**
 *
 * @author szymon
 */
public class RotationPanel extends javax.swing.JPanel implements ToolModifier {

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
	ImageTransformer it;
	public static float dgToRad = (float) Math.PI / 180;

	/** Creates new form RotationPanel */
	public RotationPanel() {
		initComponents();
		xSpinner.setModel(new SpinnerNumberModel(0, 0, 360, 1));
		ySpinner.setModel(new SpinnerNumberModel(0, 0, 360, 1));
	}

	protected RotationPanel(boolean a) {
		initComponents();
	}

	public Matrix getMatrix() {
		float xRads = dgToRad * xSlider.getValue();
		float yRads = dgToRad * ySlider.getValue();
		return new Matrix(
				(float) Math.cos(xRads), -(float) Math.sin(yRads),
				(float) Math.sin(xRads), (float) Math.cos(yRads));
	}

    public int getMoveX() {
        return 0;
    }

    public int getMoveY() {
        return 0;
    }



	public void setNotifiedObject(ImageTransformer it) {
		this.it = it;
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        xSlider = new javax.swing.JSlider();
        ySpinner = new javax.swing.JSpinner();
        unsynchroYCheckBox = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        ySlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        xSpinner = new javax.swing.JSpinner();

        setPreferredSize(new java.awt.Dimension(172, 108));
        setLayout(new java.awt.GridBagLayout());

        xSlider.setMaximum(360);
        xSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                xSliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 15);
        add(xSlider, gridBagConstraints);

        ySpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 360, 1));
        ySpinner.setEditor(new javax.swing.JSpinner.NumberEditor(ySpinner, ""));
        ySpinner.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, ySlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), ySpinner, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(getConv());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 14);
        add(ySpinner, gridBagConstraints);

        unsynchroYCheckBox.setText("y:");
        unsynchroYCheckBox.setAlignmentX(0.9F);
        unsynchroYCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        unsynchroYCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        unsynchroYCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unsynchroYCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(unsynchroYCheckBox, gridBagConstraints);

        jSeparator1.setMinimumSize(new java.awt.Dimension(10, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 7;
        add(jSeparator1, gridBagConstraints);

        ySlider.setMaximum(360);
        ySlider.setEnabled(false);
        ySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ySliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 15);
        add(ySlider, gridBagConstraints);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("x:");
        jLabel1.setAlignmentX(0.9F);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 3);
        add(jLabel1, gridBagConstraints);

        xSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 360, 1));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, xSlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), xSpinner, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(getConv());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 14);
        add(xSpinner, gridBagConstraints);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void unsynchroYCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unsynchroYCheckBoxActionPerformed
		if (unsynchroYCheckBox.isSelected()) {
			ySlider.setEnabled(true);
			ySpinner.setEnabled(true);
		} else {
			ySlider.setEnabled(false);
			ySpinner.setEnabled(false);
			ySlider.setValue(xSlider.getValue());
			if (it != null) {
				it.interrupt();
			}
		}
}//GEN-LAST:event_unsynchroYCheckBoxActionPerformed

    private void xSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_xSliderStateChanged
		if (!ySlider.isEnabled()) {
			ySlider.setValue(xSlider.getValue());
		}
		if (it != null) {
			it.interrupt();
		} else {
			//System.err.println("it == null");
		}
}//GEN-LAST:event_xSliderStateChanged

	private void ySliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ySliderStateChanged
		if (ySlider.isEnabled() && it != null) {
			this.it.interrupt();
		}
}//GEN-LAST:event_ySliderStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    protected javax.swing.JCheckBox unsynchroYCheckBox;
    protected javax.swing.JSlider xSlider;
    protected javax.swing.JSpinner xSpinner;
    protected javax.swing.JSlider ySlider;
    protected javax.swing.JSpinner ySpinner;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
