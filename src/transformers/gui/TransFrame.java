/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TransFrame.java
 *
 * Created on 2009-12-07, 13:57:44
 */
package transformers.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * przesuniecie, skalowanie (rozne w roznych kierunkach?), obrot, pochylenie.
 * @author szymon
 */
public class TransFrame extends javax.swing.JFrame {

    private int[] image;
    private int width;
    private int height;

    public void readImage() {
        try {
            FileDialog dialog = new FileDialog(this, "Wybierz plik", FileDialog.LOAD);
            dialog.setModal(true);
            dialog.setVisible(true);
            //if(dialog.)
            String path = dialog.getDirectory() + dialog.getFile();
            System.out.println("Path: " + path);
            File f = new File(path);
            BufferedImage i = ImageIO.read(f);
            width = i.getWidth();
            height = i.getHeight();
            if (image == null || image.length < width * height) {
                image = new int[width * height];
            }
            PixelGrabber grabber = new PixelGrabber(i, 0, 0, width, height, image, 0, width);
            try {
                grabber.grabPixels();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            imageButton.setText(path);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Nie moge odczytac pliku: " + ex, "Blad", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Creates new form TransFrame */
    public TransFrame() {
        image = null;
        initComponents();
        //controlsPanel.add(new ToolPanel("ASDF", new RotationPanel()));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        transformsMenu = new javax.swing.JPopupMenu();
        addScaleAction = new javax.swing.JMenuItem();
        addRotationAction = new javax.swing.JMenuItem();
        addBendAction = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        addTransformButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        controlsPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        imageButton = new javax.swing.JButton();

        addScaleAction.setText("Skalowanie");
        addScaleAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addScaleActionActionPerformed(evt);
            }
        });
        transformsMenu.add(addScaleAction);

        addRotationAction.setText("Obrót");
        addRotationAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRotationActionActionPerformed(evt);
            }
        });
        transformsMenu.add(addRotationAction);

        addBendAction.setText("Pochelenie");
        transformsMenu.add(addBendAction);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Transformers");

        jPanel1.setLayout(new java.awt.BorderLayout());

        addTransformButton.setText("Dodaj");
        addTransformButton.setMaximumSize(new java.awt.Dimension(300, 40));
        addTransformButton.setPreferredSize(new java.awt.Dimension(200, 25));
        addTransformButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTransformButtonActionPerformed(evt);
            }
        });
        jPanel1.add(addTransformButton, java.awt.BorderLayout.NORTH);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(250, 22));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(250, 200));

        controlsPanel.setLayout(new javax.swing.BoxLayout(controlsPanel, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(controlsPanel);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        imageButton.setText("Obrazek");
        imageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageButtonActionPerformed(evt);
            }
        });
        jPanel2.add(imageButton);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addTransformButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTransformButtonActionPerformed
        Component c = (Component) evt.getSource();
        transformsMenu.setMinimumSize(new Dimension(c.getWidth(), 0));
        transformsMenu.show(c, 0, c.getHeight());
    }//GEN-LAST:event_addTransformButtonActionPerformed

    private void addRotationActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRotationActionActionPerformed
        controlsPanel.add(new ToolPanel("Obrót", new RotationPanel()));
        controlsPanel.revalidate();
    }//GEN-LAST:event_addRotationActionActionPerformed

    private void addScaleActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addScaleActionActionPerformed
        controlsPanel.add(new ToolPanel("Skalowanie", new ScalePanel()));
        controlsPanel.revalidate();
    }//GEN-LAST:event_addScaleActionActionPerformed

    private void imageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageButtonActionPerformed
        // TODO add your handling code here:
        readImage();
    }//GEN-LAST:event_imageButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e2) {
            }
        }

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new TransFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addBendAction;
    private javax.swing.JMenuItem addRotationAction;
    private javax.swing.JMenuItem addScaleAction;
    private javax.swing.JButton addTransformButton;
    private javax.swing.JPanel controlsPanel;
    private javax.swing.JButton imageButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu transformsMenu;
    // End of variables declaration//GEN-END:variables
}
