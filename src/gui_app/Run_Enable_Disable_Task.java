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
import java.io.InputStream;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author simmonds
 */
public class Run_Enable_Disable_Task extends SwingWorker<String, String>{

    private static final String REMOTE_HOST  = "10.10.235.180";
    private static final String USERNAME = "ahst1";
    private static final String PASSWORD = "n0tAHSm0dul@r";
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT =5000;
    
    private String ipAddress;
    private String equipmentName;
    private String techName;
    private String reason;
    private String disable;
    private JTextArea jTextArea;
    private JButton disableButton;
    
    public Run_Enable_Disable_Task(String ipAddress, String equipmentName, String techName, String reason, JTextArea jTextArea, JButton disableButton, String disable){
        this.ipAddress=ipAddress;
        this.equipmentName=equipmentName;
        this.techName=techName;
        this.reason=reason;
        this.disable=disable;
        this.jTextArea=jTextArea;
        this.disableButton=disableButton;
    }
    
    @Override
    protected String doInBackground() throws Exception {
        String remoteShellScript = "/home/ahst1/PTXC_DisableEnable/PTXC_EnableDisable_EquipmentTool.sh";
        Session jschSession = null;
        
        try {
            JSch jsch = new JSch();
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            jschSession.setConfig("StrictHostKeyChecking", "no");
            jschSession.setPassword(PASSWORD);
            jschSession.connect(SESSION_TIMEOUT);
            
            ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
            
        if (!ipAddress.equals("")){
                channelExec.setCommand("sh " + remoteShellScript + " " + ipAddress + " " + equipmentName + " '" + techName + "' '" + reason + "' " + disable); // --- number at the end should be the last octect of equipment IP ---
                channelExec.setErrStream(System.err);
                InputStream in = channelExec.getInputStream();
                channelExec.connect(CHANNEL_TIMEOUT);
                byte[] tmp = new byte[1024];
                while(true){
                    while (in.available() > 0) {
                        int i = in.read(tmp, 0, 1024);
                        if(i < 0) break;
                        jTextArea.setText(new String(tmp, 0, i));
                        System.out.print(new String(tmp, 0, i));
                    }
                    if (channelExec.isClosed()){
                        if(in.available() > 0) continue;
                        
                        System.out.println("exit-status: " + channelExec.getExitStatus());
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
        }
        catch (JSchException e){
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
        
    }
    
}
