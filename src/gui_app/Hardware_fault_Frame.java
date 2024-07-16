/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_app;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 *
 * @author John Simmonds
 */
public final class Hardware_fault_Frame extends javax.swing.JFrame {

    Equipment curr_equipment;
    SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    
    public boolean editable;
    
    DefaultTableModel model = new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fault", "Zendesk Ticket #", "Status", "Updated"
            }
        );
    
    public Map<Integer, String> columnNames = new HashMap<Integer, String>()
    {{
            put(0, "ID");
            put(1, "fault");
            put(2, "zd_ticket_num");
            put(3, "status");
            put(4, "updated");
    }};
    
    
    /**
     * Creates new form Hardware_fault_Frame
     * @param curr_equipment
     */
    protected Hardware_fault_Frame(Equipment curr_equipment) {
        initComponents();
        this.curr_equipment=curr_equipment;
        set_current();
        jTable_faults.setModel(model);
        jTextField_ID.setText(Integer.toString(getNextID()));
        showFaults(curr_equipment);
    }
    
    public void set_current(){
        jTextField_name.setText(curr_equipment.name);
    }
    
    protected void showFaults(Equipment equipment){
        TableColumnAdjuster tca = new TableColumnAdjuster(jTable_faults, 2);
        tca.adjustColumns();
        DB_connect db = new DB_connect();
        final JFrame frame = new JFrame();
        DefaultTableModel modelNew = (DefaultTableModel) jTable_faults.getModel();
        int rows = modelNew.getRowCount();
        for (int i = rows - 1; i >= 0; i--) {
            modelNew.removeRow(i);
        }
        jTextField_ID.setText(Integer.toString(getNextID()));
        
        
        equipment.faultList.forEach((fault) -> {
            modelNew.addRow(new Object[]{fault.id, fault.fault, fault.zd_ticket_num, fault.status, df.format(fault.update)});
        });
        if (equipment.faultList.size() == 0){
            jTable_faults.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        }
        
        JPopupMenu menu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Edit");
        /**
         * Listener when a row is selected and then when the selection is changed
         */
        jTable_faults.getSelectionModel().addListSelectionListener(new ListSelectionListener (){
           public void valueChanged(ListSelectionEvent event){
                editable = false;
            }
        });
        Action action = new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                TableCellListener tcl = (TableCellListener)e.getSource();
                System.out.println("Row   : " + tcl.getRow());
                System.out.println("Column: " + tcl.getColumn());
                System.out.println("Old   : " + tcl.getOldValue());
                System.out.println("New   : " + tcl.getNewValue());
                if (!tcl.getOldValue().toString().equals(tcl.getNewValue().toString())){
                    int reply = JOptionPane.showConfirmDialog(frame, "Are you sure you want to Edit?", "Confirm", JOptionPane.YES_NO_OPTION);
                    switch (reply){
                        case JOptionPane.YES_OPTION:
                            db.update(tcl.getNewValue().toString(), columnNames.get(tcl.getColumn()), "hardware_faults", Integer.parseInt(jTable_faults.getValueAt(tcl.getRow(), 0).toString()));
                            break;
                            
                        case JOptionPane.NO_OPTION:
                            tcl.setOldValue();
                        default:
                            break;
                    }
                }
            }
        };
        TableCellListener tcl = new TableCellListener(jTable_faults,action);
        
        /**
         * Listener when Edit is clicked on a row
         */      
        editItem.addActionListener((ActionEvent e) -> {
            editable = true; 
        });
        menu.add(editItem);
        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener((ActionEvent e) -> {
            int reply = JOptionPane.showConfirmDialog(frame, "Are you sure you want to Delete Fault?", "Confirm", JOptionPane.YES_NO_OPTION);
            switch (reply){
                case JOptionPane.YES_OPTION:
                    int id = (Integer) jTable_faults.getValueAt(jTable_faults.getSelectedRow(), 0);
                    db.delete("hardware_faults", id);
                    Iterator itr = curr_equipment.faultList.iterator();
                    while (itr.hasNext()){
                        Fault x = (Fault) itr.next();
                        if (x.id == id)
                            itr.remove();
                    }
                    showFaults(curr_equipment);
                    break;
                case JOptionPane.NO_OPTION:
                    // -- do nothing --
                default:
                    break;
            }
        });
        menu.add(deleteItem);
        jTable_faults.setComponentPopupMenu(menu);
        jTable_faults.setModel(modelNew);
        tca.adjustColumns();
        
    }
    
    public int getNextID(){ 
        int ID=0;
        DB_connect db = new DB_connect();
        try{
            String query = "SELECT AUTO_INCREMENT from information_schema.TABLES where TABLE_SCHEMA = \"fr_support\" and TABLE_NAME = \"hardware_faults\";";
            ResultSet rs = db.execute(query);
            if (rs.next())
                ID = rs.getInt("AUTO_INCREMENT"); 
        }
        catch (SQLException ex){
            Logger lgr = Logger.getLogger(DB_connect.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        if (ID != 1 ){
            return ID + 1;
        }
        return 1;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_faults = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int col) {
                if (col == 0){
                    return false;
                }
                return editable;
            }
        };
        jLabel2 = new javax.swing.JLabel();
        jTextField_name = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField_zd_ticket_num = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox_status = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jButton_add = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea_fault = new javax.swing.JTextArea();
        jTextField_ID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Hardware Faults");
        setResizable(false);

        jTable_faults.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable_faults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fault", "Zendesk Ticket #", "Status", "Updated"
            }
        ));
        jTable_faults.setEnabled(false);
        jTable_faults.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable_faults);

        jLabel2.setText("Equipment Name:");

        jTextField_name.setEditable(false);
        jTextField_name.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_name.setFocusable(false);

        jLabel3.setText("Fault:");

        jLabel4.setText("Zendesk Ticket #:");

        jComboBox_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "In Progress", "Completed" }));

        jLabel5.setText("Status:");

        jButton_add.setText("Add New");
        jButton_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addActionPerformed(evt);
            }
        });

        jButton8.setText("Exit");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane3.setHorizontalScrollBar(null);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(0, 0));

        jTextArea_fault.setColumns(20);
        jTextArea_fault.setRows(5);
        jTextArea_fault.setBorder(null);
        jScrollPane3.setViewportView(jTextArea_fault);

        jTextField_ID.setEditable(false);
        jTextField_ID.setFocusable(false);

        jLabel1.setText("New ID:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField_ID))
                                    .addGap(21, 21, 21)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jTextField_name)))
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3))
                        .addGap(18, 33, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_zd_ticket_num, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel5))
                                    .addComponent(jComboBox_status, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(jButton_add, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_add, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(463, 463, 463))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField_zd_ticket_num, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_status, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addActionPerformed
        // TODO add your handling code here:
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        curr_equipment.addFault(jTextArea_fault.getText(), jTextField_zd_ticket_num.getText(), jComboBox_status.getSelectedItem().toString(), timestamp);
        showFaults(curr_equipment);
    }//GEN-LAST:event_jButton_addActionPerformed

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
            java.util.logging.Logger.getLogger(Hardware_fault_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Hardware_fault_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Hardware_fault_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Hardware_fault_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        //java.awt.EventQueue.invokeLater(new Runnable() {
           // public void run() {
             //   new Hardware_fault_Frame().setVisible(true);
            //}
        //});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton_add;
    private javax.swing.JComboBox<String> jComboBox_status;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable_faults;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea_fault;
    private javax.swing.JTextField jTextField_ID;
    private javax.swing.JTextField jTextField_name;
    private javax.swing.JTextField jTextField_zd_ticket_num;
    // End of variables declaration//GEN-END:variables
}
