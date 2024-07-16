/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_app;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;

/**
 *
 * @author John Simmonds
 */
public final class Application_Main extends javax.swing.JFrame implements PropertyChangeListener{
    
    public Map<String, Equipment> equipmentMap = new HashMap<>();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String searchName;
    
    public ProgressMonitor progressMonitor;
    public Run_Update_Script_Task task;
    
    int bootload = 1; // To Check if it is the first time the program is being run.
    
    Timer timerPTXOld;
    Timer timerPTXNew;
    
    Timer timerAVIOld;
    Timer timerAVINew;
    /**
     * Creates new form Application_Main
     */
    public Application_Main() {
        initComponents();
        equipmentList();
        fill_combo_box_type();
        fill_combo_box_name("All");
        combo_box_name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (combo_box_name.getItemCount() != 0){
                    clearFields();
                    openAll(combo_box_name.getSelectedItem().toString());
                    
                }
            }
        });
        timerPTXNew = new Timer(true);
        timerAVINew = new Timer(true);
    }
    
    protected Map<String, Equipment> equipmentList(){
       DB_connect db = new DB_connect();
       try{ 
           // -- Can only grab these values from bill list if it is standardized at the sites.. -- 
            ResultSet rs1 = db.execute("select BILL_LIST.Equipment, BILL_LIST.Equipment_Type, BILL_LIST.profile, BILL_LIST.DATE FROM BILL_LIST Order By BILL_LIST.Equipment;");
            Equipment equipment;
            while(rs1.next()){
                equipment= new Equipment(rs1.getString("Equipment"), rs1.getString("Equipment_Type"), rs1.getString("profile"), rs1.getTimestamp("DATE", Calendar.getInstance(TimeZone.getDefault())));
                equipment.fillEnableSettings(db);
                DB_connect db2 = new DB_connect();
                equipment.fillHardwareSettings(db2);
                DB_connect db3 = new DB_connect();
                equipment.fillHardwareFaults(db3);
                if (equipment.type == null || equipment.name == null || equipment.type.equals("eqmt_fuelbaysystem") || equipment.type.equals("eqmt_hopper") || equipment.name.equals("Central Kit")){
                    //do nothing;
                } else {
                    
                    equipmentMap.put(equipment.name, equipment);
                    //System.out.println(equipment.name + " Action:" + equipment.enabled + " enable tech:" + equipment.tech + " enable date:" + equipment.en_date);
                }
            }
            if (db.connection != null)
                db.connection.close(); 
        }
       catch(SQLException ex){
           Logger lgr = Logger.getLogger(DB_connect.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
       }
       
       return equipmentMap;
    }
    
    public void fill_combo_box_type(){
        ArrayList<String> types = new ArrayList<>();
        
        equipmentMap.entrySet().stream().forEachOrdered((entry) -> {
            String type = entry.getValue().type.split("eqmt_")[1].toUpperCase();
            if (entry.getValue().type == null || entry.getValue().type.equals("eqmt_hopper") || entry.getValue().type.equals("eqmt_fuelbaysystem")){
                //do nothing -- We do not track these types
            } else {
                if (types.contains(type)){
                    //do nothing
                } else{
                    types.add(type);
                    combo_box_eq_type.addItem(type);
                }
            }
        });
    }
    
    public void fill_combo_box_name(String type){ 
        ArrayList<String> items = new ArrayList<>();
        
        equipmentMap.entrySet().stream().forEachOrdered((entry) -> {
          if (entry.getValue().name.equals("Central Kit") || entry.getValue().type == null || entry.getValue().type.equals("eqmt_fuelbaysystem") || entry.getValue().type.equals("eqmt_hoppper")) {
              //do nothing -- We don't want to track these equipment.
          } else if (type.equals("All")){
              String value = entry.getValue().name;
              if (entry.getValue().isHardwareEmpty())
                  value = value + " Missing HW Data";
              items.add(value);
          } else if (entry.getValue().type.equals(type)) {
              String value = entry.getValue().name;
              if (entry.getValue().isHardwareEmpty())
                  value = value + " Missing HW Data";
              items.add(value);
          }
       });
        
        Collections.sort(items);
        Iterator<String> iterator = items.iterator();
        while(iterator.hasNext()){
            combo_box_name.addItem(iterator.next());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        combo_box_eq_type = new javax.swing.JComboBox<>();
        jButton_clear = new javax.swing.JButton();
        combo_box_name = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextField_name = new javax.swing.JTextField();
        jTextField_type = new javax.swing.JTextField();
        jTextField_profile = new javax.swing.JTextField();
        jTextField_servercon = new javax.swing.JTextField();
        jTextField_tech = new javax.swing.JTextField();
        jTextField_endate = new javax.swing.JTextField();
        jTextField_ptx_ip = new javax.swing.JTextField();
        jTextField_ptx_netmask = new javax.swing.JTextField();
        jTextField_ptx_gateway = new javax.swing.JTextField();
        jTextField_ptx_serial = new javax.swing.JTextField();
        jTextField_ptx_mac = new javax.swing.JTextField();
        jTextField_ptx_lang = new javax.swing.JTextField();
        jTextField_ptx_zone = new javax.swing.JTextField();
        jTextField_ptx_image = new javax.swing.JTextField();
        jTextField_fr_version = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTextField_radio_ip = new javax.swing.JTextField();
        jTextField_radio_netmask = new javax.swing.JTextField();
        jTextField_avi_gateway = new javax.swing.JTextField();
        jTextField_avi_serial = new javax.swing.JTextField();
        jTextField_avi_mac = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTextField_ptx_watchdog = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTextField_avi_watchdog = new javax.swing.JTextField();
        jTextField_mm2_firmware = new javax.swing.JTextField();
        jTextField_mm2_serial = new javax.swing.JTextField();
        jTextField_mod_date = new javax.swing.JTextField();
        jButtonRunUpdate = new javax.swing.JButton();
        jButtonRunUpdate.setEnabled(false);
        jLabel32 = new javax.swing.JLabel();
        jTextField_enabled = new javax.swing.JTextField();
        jTextField_avi_lte = new javax.swing.JTextField();
        jButton_search = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jButton_enable = new javax.swing.JButton();
        jButton_enable.setEnabled(false);
        jButton_disable = new javax.swing.JButton();
        jButton_disable.setEnabled(false);
        jLabel34 = new javax.swing.JLabel();
        jRadioButton_PTX_status = new javax.swing.JRadioButton();
        jRadioButton_PTX_status.setEnabled(false);
        jRadioButton_AVI_status = new javax.swing.JRadioButton();
        jRadioButton_AVI_status.setEnabled(false);
        jButton_connectPTX = new javax.swing.JButton();
        jButton_connectPTX.setEnabled(false);
        jButton_connectAVI = new javax.swing.JButton();
        jButton_connectAVI.setEnabled(false);
        jButton_viewScreen = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel35 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Equipment Support Tool");
        setBackground(javax.swing.UIManager.getDefaults().getColor("DesktopIcon.borderRimColor"));
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\mms files\\Equipment Support Tool\\ICONS\\dump_truck_icon_182665_eJF_icon.png")
        );
        setName("frame_main"); // NOI18N
        setResizable(false);

        jLabel2.setText("Equipment Type");

        jLabel1.setText("Equipment Name");

        combo_box_eq_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        combo_box_eq_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_box_eq_typeActionPerformed(evt);
            }
        });

        jButton_clear.setText("Refresh");
        jButton_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel3.setText("Name:");

        jLabel4.setText("Type:");

        jLabel5.setText("Profile:");

        jLabel6.setText("Server Connect Date:");

        jLabel7.setText("Tech:");

        jLabel8.setText("Enable Date:");

        jLabel10.setText("Field Computer");

        jLabel11.setText("IP:");

        jLabel9.setText("Radio");

        jLabel12.setText("GNSS");

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel13.setText("IP:");

        jLabel14.setText("Firmware:");

        jLabel15.setText("Netmask:");

        jLabel16.setText("Gateway:");

        jLabel17.setText("Serial Number:");

        jLabel18.setText("MAC Address:");

        jLabel19.setText("Screen Language:");

        jLabel20.setText("Screen Zone:");

        jLabel21.setText("Image:");

        jLabel22.setText("Netmask:");

        jLabel23.setText("Gateway:");

        jLabel24.setText("Serial Number:");

        jLabel25.setText("MAC Address:");

        jLabel26.setText("Serial Number:");

        jLabel27.setText("Date Last Modified:");

        jLabel28.setText("Frontrunner Version:");

        jTextField_name.setEditable(false);
        jTextField_name.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_name.setBorder(null);

        jTextField_type.setEditable(false);
        jTextField_type.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_type.setBorder(null);

        jTextField_profile.setEditable(false);
        jTextField_profile.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_profile.setBorder(null);

        jTextField_servercon.setEditable(false);
        jTextField_servercon.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_servercon.setBorder(null);

        jTextField_tech.setEditable(false);
        jTextField_tech.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_tech.setBorder(null);

        jTextField_endate.setEditable(false);
        jTextField_endate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_endate.setBorder(null);

        jTextField_ptx_ip.setEditable(false);
        jTextField_ptx_ip.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ptx_ip.setBorder(null);

        jTextField_ptx_netmask.setEditable(false);
        jTextField_ptx_netmask.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ptx_netmask.setBorder(null);

        jTextField_ptx_gateway.setEditable(false);
        jTextField_ptx_gateway.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ptx_gateway.setBorder(null);

        jTextField_ptx_serial.setEditable(false);
        jTextField_ptx_serial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ptx_serial.setBorder(null);

        jTextField_ptx_mac.setEditable(false);
        jTextField_ptx_mac.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ptx_mac.setBorder(null);

        jTextField_ptx_lang.setEditable(false);
        jTextField_ptx_lang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ptx_lang.setBorder(null);

        jTextField_ptx_zone.setEditable(false);
        jTextField_ptx_zone.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ptx_zone.setBorder(null);

        jTextField_ptx_image.setEditable(false);
        jTextField_ptx_image.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ptx_image.setBorder(null);

        jTextField_fr_version.setEditable(false);
        jTextField_fr_version.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_fr_version.setBorder(null);

        jLabel29.setText("Watchdog:");

        jTextField_radio_ip.setEditable(false);
        jTextField_radio_ip.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_radio_ip.setBorder(null);

        jTextField_radio_netmask.setEditable(false);
        jTextField_radio_netmask.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_radio_netmask.setBorder(null);

        jTextField_avi_gateway.setEditable(false);
        jTextField_avi_gateway.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_avi_gateway.setBorder(null);

        jTextField_avi_serial.setEditable(false);
        jTextField_avi_serial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_avi_serial.setBorder(null);

        jTextField_avi_mac.setEditable(false);
        jTextField_avi_mac.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_avi_mac.setBorder(null);

        jLabel30.setText("LTE:");

        jTextField_ptx_watchdog.setEditable(false);
        jTextField_ptx_watchdog.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ptx_watchdog.setBorder(null);

        jLabel31.setText("Watchdog:");

        jTextField_avi_watchdog.setEditable(false);
        jTextField_avi_watchdog.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_avi_watchdog.setBorder(null);

        jTextField_mm2_firmware.setEditable(false);
        jTextField_mm2_firmware.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_mm2_firmware.setBorder(null);

        jTextField_mm2_serial.setEditable(false);
        jTextField_mm2_serial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_mm2_serial.setBorder(null);

        jTextField_mod_date.setEditable(false);
        jTextField_mod_date.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_mod_date.setBorder(null);

        jButtonRunUpdate.setText("Run Update");
        jButtonRunUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunUpdateActionPerformed(evt);
            }
        });

        jLabel32.setText("Enabled:");

        jTextField_enabled.setEditable(false);
        jTextField_enabled.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_enabled.setBorder(null);

        jTextField_avi_lte.setEditable(false);
        jTextField_avi_lte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_avi_lte.setBorder(null);

        jButton_search.setText("Search");
        jButton_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_searchActionPerformed(evt);
            }
        });

        jButton_enable.setText("Enable");
        jButton_enable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_enableActionPerformed(evt);
            }
        });

        jButton_disable.setText("Disable");
        jButton_disable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_disableActionPerformed(evt);
            }
        });

        jRadioButton_PTX_status.setText("PTX ");

        jRadioButton_AVI_status.setText("AVI ");

        jButton_connectPTX.setText("Connect");
        jButton_connectPTX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_connectPTXActionPerformed(evt);
            }
        });

        jButton_connectAVI.setText("Connect");
        jButton_connectAVI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_connectAVIActionPerformed(evt);
            }
        });

        jButton_viewScreen.setText("View ");
        jButton_viewScreen.setEnabled(false);
        jButton_viewScreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_viewScreenActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setText("Radio");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel35.setText("Field Computer");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(combo_box_eq_type, 0, 208, Short.MAX_VALUE)
                                .addComponent(combo_box_name, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator8))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton_search, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_ptx_ip)
                            .addComponent(jTextField_ptx_netmask)
                            .addComponent(jTextField_ptx_gateway)
                            .addComponent(jTextField_ptx_serial)
                            .addComponent(jTextField_ptx_mac)
                            .addComponent(jTextField_ptx_lang)
                            .addComponent(jTextField_ptx_zone)
                            .addComponent(jLabel10)
                            .addComponent(jTextField_ptx_image)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_ptx_watchdog))))
                            .addComponent(jTextField_fr_version))
                        .addGap(30, 30, 30)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_avi_serial)
                            .addComponent(jTextField_avi_mac)
                            .addComponent(jTextField_radio_netmask)
                            .addComponent(jTextField_avi_gateway)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25)
                                    .addComponent(jTextField_radio_ip, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_avi_lte))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_avi_watchdog)))
                        .addGap(37, 37, 37)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jButtonRunUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton_connectAVI)
                                        .addGap(165, 165, 165)
                                        .addComponent(jRadioButton_AVI_status))
                                    .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel33))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton_connectPTX)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(jLabel35)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel34)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton_viewScreen)
                                        .addGap(71, 71, 71)
                                        .addComponent(jRadioButton_PTX_status))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel14)
                                        .addComponent(jLabel26)
                                        .addComponent(jLabel27)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField_mm2_firmware, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField_mm2_serial, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jTextField_mod_date, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator4)
                    .addComponent(jSeparator3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(jTextField_enabled, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jTextField_tech, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField_endate, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_enable, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_disable, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jTextField_name, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jTextField_type, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jTextField_profile, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jTextField_servercon, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_profile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_servercon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel32))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_tech, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_endate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_enabled, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_enable)
                            .addComponent(jButton_disable))
                        .addGap(40, 40, 40)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5)
                            .addComponent(jSeparator6)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel14))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField_radio_ip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField_mm2_firmware, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_ptx_ip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_radio_netmask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabel23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_avi_gateway, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel24)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_avi_serial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel25)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_avi_mac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField_avi_lte))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField_avi_watchdog)
                                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(8, 8, 8)
                                        .addComponent(jTextField_ptx_netmask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel16)
                                        .addGap(8, 8, 8)
                                        .addComponent(jTextField_ptx_gateway, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_ptx_serial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_ptx_mac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_ptx_lang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_ptx_zone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel21))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_mm2_serial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel27)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_mod_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(jButtonRunUpdate)
                                        .addGap(19, 19, 19)
                                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField_ptx_image, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_fr_version, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField_ptx_watchdog, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(9, 9, 9))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel34)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel35)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jButton_viewScreen)
                                                        .addComponent(jRadioButton_PTX_status))
                                                    .addComponent(jButton_connectPTX))))
                                        .addGap(23, 23, 23)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel33)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButton_connectAVI)
                                            .addComponent(jRadioButton_AVI_status)))))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(8, 8, 8)
                        .addComponent(combo_box_eq_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combo_box_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_clear)
                            .addComponent(jButton_search))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator2))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public void refresh(String equipmentName){
        if (equipmentName.contains(" Missing HW Data"))
            equipmentName = equipmentName.split(" Missing HW Data")[0];
        
        Equipment currEquipment = this.equipmentMap.get(equipmentName);
        DB_connect db = new DB_connect();
        currEquipment.fillEnableSettings(db);
        DB_connect db2 = new DB_connect();
        currEquipment.fillHardwareSettings(db2);
        this.equipmentMap.put(equipmentName, currEquipment);
    }
    
    public void openAll(String selected_text){
        
        
        jButton_connectAVI.setEnabled(true);
        jButton_connectPTX.setEnabled(true);
        jButtonRunUpdate.setEnabled(true);
        jButton_viewScreen.setEnabled(true);
        // Setting all Text Fields from the Equipment Object..
        
        if (selected_text.contains(" Missing HW Data"))
            selected_text = selected_text.split(" Missing HW Data")[0];
   
        refresh(selected_text);
        
        Equipment sel_eq = equipmentMap.get(selected_text);
        
        // Start Timer Task to Ping Equipment until closed
        if (bootload > 1);
            timerPTXNew = new Timer(true);
            timerAVINew = new Timer(true);
        
        Ping pingPTXTask = new Ping(jRadioButton_PTX_status, sel_eq.ptx_ip);
        timerPTXNew.scheduleAtFixedRate(pingPTXTask, 0, 1 * 1000);
        
        Ping pingAVITask = new Ping(jRadioButton_AVI_status, sel_eq.avi_ip);
        timerPTXNew.scheduleAtFixedRate(pingAVITask, 0, 1 * 1000);
        
        if (sel_eq != null){
            jTextField_name.setText(sel_eq.name);
        }
        
        if (sel_eq.type != null) {
            String eq_type = sel_eq.type;
            if (eq_type.matches("eqmt_.*"))
                eq_type=eq_type.split("eqmt_")[1].toUpperCase();
                jTextField_type.setText(eq_type);
        }
        
        if (sel_eq.profile != null){
            jTextField_profile.setText(sel_eq.profile);
        }
        if (sel_eq.conn_date != null){
            String s1 = df.format(sel_eq.conn_date);
            jTextField_servercon.setText(s1);
        } else{
            jTextField_servercon.setText("");
        }
        if (sel_eq.en_date != null){
            String s2 = df.format(sel_eq.en_date);
            jTextField_endate.setText(s2);
        } else{
            jTextField_endate.setText("");
        }
        if (sel_eq.date_mod != null){
            String s3 = df.format(sel_eq.date_mod);
            jTextField_mod_date.setText(s3);
        } else{
            jTextField_mod_date.setText("");
        }

        if (sel_eq != null) {
            jTextField_tech.setText(sel_eq.tech);
        }
        if (sel_eq.enabled != null && !sel_eq.enabled.equals("")){
            jTextField_enabled.setText(sel_eq.enabled);
            if (sel_eq.enabled.equals("Disabled")){
                jButton_disable.setEnabled(false);
                jButton_enable.setEnabled(true);
            }
            if (sel_eq.enabled.equals("Enabled")){
                jButton_enable.setEnabled(false);
                jButton_disable.setEnabled(true);
            }
        }
        if (!sel_eq.isHardwareEmpty()) {
            jTextField_ptx_ip.setText(sel_eq.ptx_ip);
            jTextField_ptx_netmask.setText(sel_eq.ptx_netmask);
            jTextField_ptx_gateway.setText(sel_eq.ptx_gateway);
            jTextField_ptx_serial.setText(sel_eq.ptx_serial);
            jTextField_ptx_mac.setText(sel_eq.ptx_mac);
            jTextField_ptx_lang.setText(sel_eq.ptx_lang);
            jTextField_ptx_zone.setText(sel_eq.ptx_zone);
            jTextField_ptx_image.setText(sel_eq.ptx_image);
            jTextField_fr_version.setText(sel_eq.ptx_front_version);

            jTextField_radio_ip.setText(sel_eq.avi_ip);
            jTextField_radio_netmask.setText(sel_eq.avi_netmask);
            jTextField_avi_gateway.setText(sel_eq.avi_gateway);
            jTextField_avi_serial.setText(sel_eq.avi_serial);
            jTextField_avi_mac.setText(sel_eq.avi_mac);

            jTextField_mm2_firmware.setText(sel_eq.gnss_firmware);
            jTextField_mm2_serial.setText(sel_eq.gnss_serial);

            
            jTextField_ptx_watchdog.setText(sel_eq.ptx_watchdog);
            jTextField_avi_lte.setText(sel_eq.avi_lte);
            jTextField_avi_watchdog.setText(sel_eq.avi_watchdog);
            
           
        }
        bootload = bootload + 1;
    }
    private void combo_box_eq_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_box_eq_typeActionPerformed
            combo_box_name.removeAllItems();
            if (combo_box_eq_type.getSelectedItem().toString().equals("All"))
                fill_combo_box_name(combo_box_eq_type.getSelectedItem().toString());
            else {
                String current_sel = "eqmt_" + combo_box_eq_type.getSelectedItem().toString().toLowerCase();
                fill_combo_box_name(current_sel);
            }
    }//GEN-LAST:event_combo_box_eq_typeActionPerformed
    
    public void clearFields() {
        
        if (bootload > 1) {
            timerPTXOld = timerPTXNew;
            timerPTXOld.cancel();
            timerPTXOld.purge();
            
            timerAVIOld = timerAVINew;
            timerAVIOld.cancel();
            timerAVIOld.purge();
        }
        
        jTextField_name.setText("");
        jTextField_type.setText("");        
        jTextField_profile.setText("");        
        jTextField_servercon.setText("");     
        jTextField_endate.setText("");                
        jTextField_mod_date.setText("");
        jTextField_tech.setText("");
        

        jTextField_ptx_ip.setText("");
        jTextField_ptx_netmask.setText("");
        jTextField_ptx_gateway.setText("");
        jTextField_ptx_serial.setText("");
        jTextField_ptx_mac.setText("");
        jTextField_ptx_lang.setText("");
        jTextField_ptx_zone.setText("");
        jTextField_ptx_image.setText("");
        jTextField_fr_version.setText("");

        jTextField_radio_ip.setText("");
        jTextField_radio_netmask.setText("");
        jTextField_avi_gateway.setText("");
        jTextField_avi_serial.setText("");
        jTextField_avi_mac.setText("");

        jTextField_mm2_firmware.setText("");
        jTextField_mm2_serial.setText("");
        
        jTextField_enabled.setText("");
        jTextField_ptx_watchdog.setText("");
        jTextField_avi_lte.setText("");
        jTextField_avi_watchdog.setText("");
    }
    
    private void jButton_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_searchActionPerformed
        JFrame searchFrame = new Search_Frame(equipmentMap, combo_box_eq_type,combo_box_name, this);
        searchFrame.setLocationRelativeTo(this);
        searchFrame.setVisible(true);
        searchFrame.setAlwaysOnTop(true);
        this.setEnabled(false);
        
    }//GEN-LAST:event_jButton_searchActionPerformed

    private void jButtonRunUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunUpdateActionPerformed
        // --- Connect to Support VM and Run the Equipment HW tracker Script ---
        // -- should run the session connect in Worker thread because it freezes the Event thread that holds the main GUI App --
        progressMonitor = new ProgressMonitor(Application_Main.this, "Updating Equipment Please Wait..", "", 0, 100);
        progressMonitor.setProgress(0);
        task = new Run_Update_Script_Task(this, progressMonitor);
        task.addPropertyChangeListener(this);
        task.execute();
        jButtonRunUpdate.setEnabled(false);
    }//GEN-LAST:event_jButtonRunUpdateActionPerformed

    private void jButton_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearActionPerformed
        if (!combo_box_name.getSelectedItem().toString().equals(""));
            this.refresh(combo_box_name.getSelectedItem().toString());
            this.clearFields();
            this.openAll(combo_box_name.getSelectedItem().toString());
    }//GEN-LAST:event_jButton_clearActionPerformed

    private void jButton_disableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_disableActionPerformed
        Equipment currEquipment = equipmentMap.get(jTextField_name.getText());
        
        Disable_Frame disableFrame = new Disable_Frame(currEquipment, this);
        disableFrame.setLocationRelativeTo(this);
        disableFrame.setVisible(true);
        this.setEnabled(false);
    }//GEN-LAST:event_jButton_disableActionPerformed

    private void jButton_enableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_enableActionPerformed
        Equipment currEquipment = equipmentMap.get(jTextField_name.getText());
        
        Enable_Frame enableFrame = new Enable_Frame(currEquipment, this);
        enableFrame.setLocationRelativeTo(this);
        enableFrame.setVisible(true);
        this.setEnabled(false);
    }//GEN-LAST:event_jButton_enableActionPerformed

    private void jButton_connectPTXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_connectPTXActionPerformed
        Equipment currEquipment = equipmentMap.get(jTextField_name.getText());
        Runtime r = Runtime.getRuntime();
        String username = "dlog";
        String password = "gold";
        String session = currEquipment.ptx_ip;
        String s = "C:\\Program Files\\PuTTy\\putty.exe -ssh -l "+username+" -pw "+password+" "+session+" -L 3333:192.168.0.101:8002 -L 3334:192.168.0.102:8002";
        try {
            r.exec(s);
            Thread.sleep(3000);
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton_connectPTXActionPerformed

    private void jButton_connectAVIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_connectAVIActionPerformed
        Equipment currEquipment = equipmentMap.get(jTextField_name.getText());
        Runtime r = Runtime.getRuntime();
        String username = "root";
        String password = "mYngR3+NFjNh";
        String session = currEquipment.avi_ip;
        String s = "C:\\Program Files\\PuTTy\\putty.exe -ssh -l "+username+" -pw "+password+" "+session;
        try {
            r.exec(s);
            Thread.sleep(3000);
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton_connectAVIActionPerformed

    private void jButton_viewScreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_viewScreenActionPerformed
        Equipment currEquipment = equipmentMap.get(combo_box_name.getSelectedItem().toString());
        Run_VNC_Task VNCtask = new Run_VNC_Task(currEquipment.ptx_ip, jButton_viewScreen);
        VNCtask.addPropertyChangeListener(this);
        VNCtask.execute();
        Runtime r = Runtime.getRuntime();
        String session = currEquipment.ptx_ip;
        String s = "C:\\Program Files\\RealVNC\\VNC Viewer\\vncviewer.exe "+session+":5900";
        try {
            
            Thread.sleep(3000);
            r.exec(s);
            
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton_viewScreenActionPerformed

    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()){
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message = String.format("Completed...%d%%.\n", progress);
            progressMonitor.setNote(message);
            if (progressMonitor.isCanceled() || task.isDone()) {
                if (progressMonitor.isCanceled()) {
                    task.cancel(true);
                }
                jButtonRunUpdate.setEnabled(true);
            }
        }
    }
    
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
            java.util.logging.Logger.getLogger(Application_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Application_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Application_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
       
        java.awt.EventQueue.invokeLater(() -> {
            new Application_Main().setVisible(true);
        });
            
       
        
              
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> combo_box_eq_type;
    public javax.swing.JComboBox<String> combo_box_name;
    private javax.swing.JButton jButtonRunUpdate;
    private javax.swing.JButton jButton_clear;
    private javax.swing.JButton jButton_connectAVI;
    private javax.swing.JButton jButton_connectPTX;
    private javax.swing.JButton jButton_disable;
    private javax.swing.JButton jButton_enable;
    private javax.swing.JButton jButton_search;
    private javax.swing.JButton jButton_viewScreen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton_AVI_status;
    private javax.swing.JRadioButton jRadioButton_PTX_status;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTextField_avi_gateway;
    private javax.swing.JTextField jTextField_avi_lte;
    private javax.swing.JTextField jTextField_avi_mac;
    private javax.swing.JTextField jTextField_avi_serial;
    private javax.swing.JTextField jTextField_avi_watchdog;
    private javax.swing.JTextField jTextField_enabled;
    private javax.swing.JTextField jTextField_endate;
    private javax.swing.JTextField jTextField_fr_version;
    private javax.swing.JTextField jTextField_mm2_firmware;
    private javax.swing.JTextField jTextField_mm2_serial;
    private javax.swing.JTextField jTextField_mod_date;
    public javax.swing.JTextField jTextField_name;
    private javax.swing.JTextField jTextField_profile;
    private javax.swing.JTextField jTextField_ptx_gateway;
    private javax.swing.JTextField jTextField_ptx_image;
    private javax.swing.JTextField jTextField_ptx_ip;
    private javax.swing.JTextField jTextField_ptx_lang;
    private javax.swing.JTextField jTextField_ptx_mac;
    private javax.swing.JTextField jTextField_ptx_netmask;
    private javax.swing.JTextField jTextField_ptx_serial;
    private javax.swing.JTextField jTextField_ptx_watchdog;
    private javax.swing.JTextField jTextField_ptx_zone;
    private javax.swing.JTextField jTextField_radio_ip;
    private javax.swing.JTextField jTextField_radio_netmask;
    private javax.swing.JTextField jTextField_servercon;
    private javax.swing.JTextField jTextField_tech;
    private javax.swing.JTextField jTextField_type;
    // End of variables declaration//GEN-END:variables

     
}


