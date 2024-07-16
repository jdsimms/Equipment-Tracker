/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_app;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Toolkit;


/**
 *
 * @author simmonds
 */
public class Disable_Frame extends javax.swing.JFrame implements PropertyChangeListener{
    
    static Equipment equipment;
    public Run_Enable_Disable_Task task;
    static Application_Main parent;
    /**
     * Creates new form Disable_Frame
     * @param app - Main Application Window
     */
    public Disable_Frame(Equipment equipment, Application_Main parent) {
        initComponents();
        this.equipment=equipment;
        this.parent=parent;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox_disableReason = new javax.swing.JComboBox<>();
        jComboBox_techList = new javax.swing.JComboBox<>();
        jButton_disable = new javax.swing.JButton();
        jButton_cancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextArea1.setText("Please Select Your Name and the Disable Reason\n---------------------------------------------------------------------------\n 1. Click Disable to Continue with Disable\n\n 2. Click Close to Cancel Disable");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Disable Equipment");
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\mms files\\Equipment Support Tool\\ICONS\\dump_truck_icon_182665_eJF_icon.png"));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jComboBox_disableReason.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Equipment is going to the shop.", "Equipment is going for a PM.", "Equipment is going for tires.", "Equipment is going to MEM.", "Equipment is going to Non-Autonomous Operation.", "Operator is not AHS Trained.", "Faulty E-Stop.", "Needs Safety Envelope Calculation.", "Disabled for LV Road Use.", "Still Needs to be POC.", "Other." }));

        jComboBox_techList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Matthew Schellenberg", "Dion Mak", "Thomas Woodard", "Ripudaman Rai", "Hernan Barrios", "Steven Pollard", "Billy Ng", "John Simmonds", "Parth Makwana", "Noor Al-Bochaki" }));

        jButton_disable.setText("Disable");
        jButton_disable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_disableActionPerformed(evt);
            }
        });

        jButton_cancel.setText("Close");
        jButton_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cancelActionPerformed(evt);
            }
        });

        jLabel1.setText("Tech Spec:");

        jLabel2.setText("Disable Reason:");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jButton_cancel)
                        .addGap(29, 29, 29)
                        .addComponent(jButton_disable))
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox_techList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_disableReason, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_techList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_disableReason, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_cancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_disable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cancelActionPerformed
        parent.setEnabled(true);
        this.dispose();
        parent.toFront();
    }//GEN-LAST:event_jButton_cancelActionPerformed

    private void jButton_disableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_disableActionPerformed
        jButton_disable.setEnabled(false);
        task = new Run_Enable_Disable_Task(equipment.ptx_ip, equipment.name, jComboBox_techList.getSelectedItem().toString(), jComboBox_disableReason.getSelectedItem().toString(),
                    jTextArea1, jButton_disable, "Disable");
        task.addPropertyChangeListener(this);
        task.execute();
    }//GEN-LAST:event_jButton_disableActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        parent.setEnabled(true);
        parent.refresh(equipment.name);
        parent.clearFields();
        parent.openAll(equipment.name);
        parent.toFront();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        parent.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Disable_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Disable_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Disable_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Disable_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Disable_Frame(equipment, parent).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_cancel;
    private javax.swing.JButton jButton_disable;
    private javax.swing.JComboBox<String> jComboBox_disableReason;
    private javax.swing.JComboBox<String> jComboBox_techList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
