/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_app;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

/**
 * This class should run the equipment_tracker script on the supportVM on a 
 * separate worker thread so that the main GUI application doesn't freeze.
 * 
 * @author John Simmonds
 */
public class Run_Update_Script_Task extends SwingWorker<String, String> {
    
    private static final String REMOTE_HOST  = "10.10.235.180";
    private static final String USERNAME = "ahst1";
    private static final String PASSWORD = "n0tAHSm0dul@r";
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT =5000;
    private JTextField jTextField_name;
    private ProgressMonitor progressMonitor;
    private Application_Main parentMain;

    public Run_Update_Script_Task(Application_Main parentMain, ProgressMonitor progressMonitor) {
        this.jTextField_name=parentMain.jTextField_name;
        this.progressMonitor=progressMonitor;
        this.parentMain=parentMain;
    }
    
    
    @Override
    public String doInBackground(){
        String remoteShellScript = "/home/ahst1/HW_Tracker/equipment_hw_tracker.sh";
        Session jschSession = null;
        
        try {
            JSch jsch = new JSch();
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            jschSession.setConfig("StrictHostKeyChecking", "no");
            jschSession.setPassword(PASSWORD);
            jschSession.connect(SESSION_TIMEOUT);
            
            ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
            String equipmentIP = "";
            
            setProgress(0);
            
            if(!jTextField_name.getText().equals("")){
//                Progress_Frame up = new Progress_Frame();
//                up.setVisible(true);
//                up.setAlwaysOnTop(true);
                try {
                    DB_connect db = new DB_connect();
                    ResultSet rs = db.execute("select distinct equipment_ip from enable_disable where equipment_name=\'" + jTextField_name.getText() + "\';");
                    if (rs.next())
                        equipmentIP=rs.getString("equipment_ip");
                    setProgress(10);
                } catch (SQLException ex){
                    Logger lgr = Logger.getLogger(DB_connect.class.getName());
                    lgr.log(Level.SEVERE, ex.getMessage(), ex);
                }
            } else {
                //  -- tell the user there is no equipment selected to run the update on!
            }
            if (!equipmentIP.equals("")){
                channelExec.setCommand("sh " + remoteShellScript + " " + equipmentIP.split("\\.")[3]); // --- number at the end should be the last octect of equipment IP ---
                channelExec.setErrStream(System.err);
                InputStream in = channelExec.getInputStream();
                channelExec.connect(CHANNEL_TIMEOUT);
                byte[] tmp = new byte[1024];
                int bytesread = 0;
                while(true){
                    int progress = 10;
                    while (in.available() > 0) {
                        int i = in.read(tmp, 0, 1024);
                        if(i < 0) break;
                        bytesread ++;
                        if (bytesread == 1){
                            setProgress(28);
                        }
                        if (bytesread == 2){
                            setProgress(46);
                        }
                        if (bytesread == 3){
                            setProgress(64);
                        };
                        if (bytesread == 3){
                            setProgress(82);
                        };
                        System.out.print(new String(tmp, 0, i));
                        
                    }
                    if (channelExec.isClosed()){
                        if(in.available() > 0) continue;
                        
                        System.out.println("exit-status: " + channelExec.getExitStatus());
                        setProgress(100);
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ee) {
                    }
                }
                channelExec.disconnect();
            } else {
                // -- Tell the user that the equipment is not in enable disable list.
            }
        } catch (JSchException | IOException e){
            e.printStackTrace();
        } finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }
        return "";
    }
    
    @Override
    public void done(){
        // Refresh the equipment on main display after Update Script is run
        String equipmentName = jTextField_name.getText();
        Equipment currEquipment = parentMain.equipmentMap.get(equipmentName);
        DB_connect db = new DB_connect();
        currEquipment.fillEnableSettings(db);
        DB_connect db2 = new DB_connect();
        currEquipment.fillHardwareSettings(db2);
        parentMain.equipmentMap.put(equipmentName, currEquipment);
        
        parentMain.clearFields();
        parentMain.openAll(equipmentName);

    }
}
