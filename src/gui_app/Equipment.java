/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_app;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author John Simmonds
 */
class Equipment {
    
    String name, type, profile, enabled, tech, ptx_ip, ptx_netmask, ptx_gateway, ptx_serial,
            ptx_mac, ptx_lang, ptx_zone, ptx_image, ptx_front_version, ptx_watchdog, avi_ip, avi_netmask, avi_gateway,
            avi_serial, avi_mac, avi_lte, avi_watchdog, gnss_firmware, gnss_serial;
    Timestamp en_date, conn_date, date_mod;
    
    protected ArrayList<Fault> faultList = new ArrayList<>();
    
    SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            
    public Equipment(String name, String type, String profile, Timestamp conn_date){        
        this.name=name;
        this.type=type;
        this.profile=profile;
        this.conn_date=conn_date;
        
    }    
    // This function should have been in Application Main... But here we are.
    public void fillHardwareSettings(DB_connect db){
         try {
             ResultSet rs1 = db.execute("SELECT * FROM equipment_tracker WHERE" + " EquipmentID=\"" + this.getName() + "\";");
             String mm2_String = "0";
             if (rs1.next()){
                 if (!rs1.getString("MM2_SN").equals("")){
                     mm2_String = rs1.getString("MM2_SN");
                 }
                this.setHardware(rs1.getString("PTX_IP"), rs1.getString("PTX_NETMASK"), rs1.getString("PTX_GATEWAY"),
                rs1.getString("PTX_SN"), rs1.getString("PTX_MAC"), rs1.getString("PTX_LANG"), rs1.getString("PTX_ZONE"),
                rs1.getString("PTX_IMAGE"), rs1.getString("PTX_UPGRADE"), rs1.getString("PTX_WD"), rs1.getString("AVI_IP"),
                rs1.getString("AVI_NETMASK"), rs1.getString("AVI_GATEWAY"), rs1.getString("AVI_SN"), rs1.getString("AVI_MAC"),
                rs1.getString("AVI_LTE"), rs1.getString("AVI_WD"), rs1.getString("MM2_FW"), mm2_String, rs1.getTimestamp("DATE_MOD", Calendar.getInstance(TimeZone.getDefault())));
             }
             if (db.connection != null)
                db.connection.close();
        }
         catch( SQLException ex){
             Logger lgr = Logger.getLogger(DB_connect.class.getName());
             lgr.log(Level.SEVERE, ex.getMessage(), ex);
         }
    }
    
    // This one too... I hate myself for leaving it...
    public void fillEnableSettings(DB_connect db){
        try {
            ResultSet rs2 = db.execute("select t.equipment_name, t.tech,t.action, t.date FROM enable_disable t INNER JOIN(select equipment_name, max(date) as MaxDate FROM enable_disable GROUP BY equipment_name) tm on t.equipment_name=tm.equipment_name and t.date=tm.MaxDate and t.equipment_name=\""+ this.getName() +"\" Order By t.equipment_name;");
            if (rs2.next()){
                this.setTech(rs2.getString("tech"));
                this.setEnabled(rs2.getString("action"));
                this.setEnDate(rs2.getTimestamp("date", Calendar.getInstance(TimeZone.getDefault())));
            }
            if (db.connection != null)
                db.connection.close();
        }
        catch (SQLException ex){
            Logger lgr = Logger.getLogger(DB_connect.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    public void fillHardwareFaults(DB_connect db){
        try {
            ResultSet rs = db.execute("select ID, eqmtID ,fault, zd_ticket_num, status, updated from hardware_faults WHERE" + " eqmtID=\"" + this.getName() + "\";");
            while(rs.next()){
                this.faultList.add(new Fault(rs.getInt("ID"), rs.getString("eqmtID"), rs.getString("fault"), rs.getString("zd_ticket_num"), rs.getString("status"), rs.getTimestamp("DATE_MOD", Calendar.getInstance(TimeZone.getDefault()))));       
            }
            if (db.connection != null)
                db.connection.close();
        }catch (SQLException ex){
            Logger lgr = Logger.getLogger(DB_connect.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void setHardware(String ptx_ip, String ptx_netmask, String ptx_gateway, String ptx_serial, 
                            String ptx_mac, String ptx_lang, String ptx_zone, String ptx_image, String ptx_upgrade,
                            String ptx_wd, String avi_ip, String avi_netmask, String avi_gateway, String avi_serial,
                            String avi_mac, String avi_lte, String avi_wd, String mm2_fw, String mm2_sn, Timestamp date){
        this.ptx_ip=ptx_ip;
        this.ptx_netmask=ptx_netmask;
        this.ptx_gateway=ptx_gateway;
        this.ptx_serial=ptx_serial;
        this.ptx_mac=ptx_mac;
        this.ptx_lang=ptx_lang;
        this.ptx_zone=ptx_zone;
        this.ptx_image=ptx_image;
        this.ptx_front_version=ptx_upgrade;
        this.ptx_watchdog=ptx_wd;
        
        this.avi_ip=avi_ip;
        this.avi_netmask=avi_netmask;
        this.avi_gateway=avi_gateway;
        this.avi_serial=avi_serial;
        this.avi_mac=avi_mac;
        this.avi_lte=avi_lte;
        this.avi_watchdog=avi_wd;
        
        this.gnss_firmware=mm2_fw;
        this.gnss_serial=mm2_sn;
        this.date_mod=date;
        
    }
    
    
    public void addFault(String fault, String zd_ticket_num, String status, Timestamp updated){
        DB_connect db = new DB_connect();
        try{
            String query = "INSERT INTO hardware_faults(eqmtID, fault, zd_ticket_num, status, updated) VALUES (\""+ this.name + "\" ,\"" + fault + "\" ,\"" + zd_ticket_num + "\" ,\"" + status + "\" ,\"" + df.format(updated) + "\");";
            db.insert(query);
            ResultSet rs = db.execute("SELECT ID from hardware_faults WHERE eqmtID=" + "\'" + this.name + "\' and updated=\'" +df.format(updated)+"\';");
            if (rs.next()){
                this.faultList.add(new Fault(rs.getInt("ID"), this.name, fault, zd_ticket_num, status, updated));
            }
        }
        catch (SQLException ex){
            Logger lgr = Logger.getLogger(DB_connect.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void updateFaults(){
        
    }
    
    public void editFault(){
        
    }
    
    public void removeFault(){
        
    }
    
    public boolean isHardwareEmpty(){
        if (getAVIgateway() == null || getAVIip() == null || getAVInetmask() == null ||getAVIlte() == null || getAVIserial() == null || getAVImac() == null || getAVIwatchdog() == null
                || getPTXip() == null || getPTXgateway() == null || getPTXnetmask() == null || getPTXserial() == null || getPTXfrversion() == null || getPTXimage() == null || getPTXwatchdog() == null
                || getPTXmac() == null || getPTXlang() == null || getPTXzone() == null || getGNSSfirmware() == null || getGNSSserial() == null || getAVIgateway().equals("") || getAVIip().equals("") 
                || getAVInetmask().equals("") ||getAVIlte().equals("") || getAVIserial().equals("") || getAVImac().equals("") || getAVIwatchdog().equals("") || getPTXip().equals("") 
                || getPTXgateway().equals("") || getPTXnetmask().equals("") || getPTXserial().equals("") || getPTXfrversion().equals("") || getPTXimage().equals("") || getPTXwatchdog().equals("")
                || getPTXmac().equals("") || getPTXlang().equals("") || getPTXzone().equals("") || getGNSSfirmware().equals("") || getGNSSserial().equals(""))
            return true;
        
        return false;
    }
    
    public void setEnabled(String enabled){
        this.enabled=enabled;
    }
    public void setTech(String tech){
        this.tech=tech;
    }
    public void setEnDate(Timestamp en_date){
        this.en_date=en_date;
    }
    
        
    public String getName(){
        return this.name;
    }
    public String getType(){
        return this.type;
    }   
    public String getProfile(){
        return this.profile;
    }
    public Timestamp getConnDate(){
        return this.conn_date;
    }
    public String getEnabled(){
        return this.enabled;
    }
    public String getTech(){
        return this.tech;
    }
    public Timestamp getEnDate(){
        return this.en_date;
    }
    
    
    public String getPTXip(){
        return this.ptx_ip;
    }
    public String getPTXnetmask(){
        return this.ptx_netmask;
    }
    public String getPTXgateway(){
        return this.ptx_gateway;
    }
    public String getPTXserial(){
        return this.ptx_serial;
    }
    public String getPTXmac(){
        return this.ptx_mac;
    }
    public String getPTXlang(){
        return this.ptx_lang;
    }
    public String getPTXzone(){
        return this.ptx_zone;
    }
    public String getPTXimage(){
        return this.ptx_image;
    }
    public String getPTXfrversion(){
        return this.ptx_front_version;
    }
    public String getPTXwatchdog(){
        return this.ptx_watchdog;
    }
    
    
    public String getAVIip(){
        return this.avi_ip;
    }
    public String getAVInetmask(){
        return this.avi_netmask;
    }
    public String getAVIgateway(){
        return this.avi_gateway;
    }
    public String getAVIserial(){
        return this.avi_serial;
    }
    public String getAVImac(){
        return this.avi_serial;
    }
    public String getAVIlte(){
        return this.avi_lte;
    }
    public String getAVIwatchdog(){
        return this.avi_watchdog;
    }
        
    public String getGNSSfirmware(){
        return this.gnss_firmware;
    }
    public String getGNSSserial(){
        return this.gnss_serial;
    }
    
    public void printEquipment(){
        System.out.println(this.getName() + " " + this.getType() + " " + this.getProfile() + " " + this.getEnabled() + this.getPTXip());
    }
}
