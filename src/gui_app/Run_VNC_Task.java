/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_app;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.SwingWorker;

/**
 *
 * @author simmonds
 */
public class Run_VNC_Task extends SwingWorker<String, String>{

    private static final String USERNAME = "dlog";
    private static final String PASSWORD = "gold";
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT =5000;
    private static String REMOTE_HOST;
    
    static JButton button;
    
    public Run_VNC_Task(String ipAddress, JButton button){
        this.REMOTE_HOST=ipAddress;
        this.button=button;
    }
    
    @Override
    protected String doInBackground() throws Exception {
        String remoteShellScript = "vncserver\n";
        Session jschSession = null;
        
        try {
            JSch jsch = new JSch();
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            jschSession.setConfig("StrictHostKeyChecking", "no");
            jschSession.setPassword(PASSWORD);
            jschSession.connect(SESSION_TIMEOUT);
            
            Channel channel = jschSession.openChannel("shell");
            
            channel.setInputStream(new ByteArrayInputStream(remoteShellScript.getBytes(StandardCharsets.UTF_8)));
            channel.setOutputStream(System.out);
            
            InputStream in = channel.getInputStream();
            StringBuilder outBuff = new StringBuilder();
            int exitStatus = -1;
            
            channel.connect();
            
            while(true){
                for (int c; (c = in.read()) >=0;) {
                    outBuff.append((char) c);
                }
                
                if (channel.isClosed()) {
                    if (in.available() > 0 ) continue;
                    exitStatus = channel.getExitStatus();
                    break;
                }
            }
            
        }catch (JSchException e){
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
        button.setEnabled(true);
    }
}
