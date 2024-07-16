/*
 * Class created to ping IP addresses to check availability
 */
package gui_app;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JRadioButton;

/**
 *
 * @author John Simmonds
 */
public class Ping extends TimerTask{
    JRadioButton radioButton;
    String ipAddress;
    
    public Ping(JRadioButton radioButton, String ipAddress){
        this.radioButton=radioButton;
        this.ipAddress=ipAddress;
        radioButton.setEnabled(true);
    }
    
    public void sendPingRequest() throws UnknownHostException, IOException {
        
        InetAddress ptx = InetAddress.getByName(this.ipAddress);
        //System.out.println("Sending Ping Request to " + ipAddress);
        
        if (ipAddress != null && ptx.isReachable(5000)){
            //System.out.println(ipAddress + " PTX is Online");
            radioButton.setIcon(new RoundIcon(Color.green));
        } 
        else {
            //System.out.println(ipAddress + " PTX is Offline");
            radioButton.setIcon(new RoundIcon(Color.red));
        } 
    }

    @Override
    public void run() {
        try {
            sendPingRequest();
        }
        catch (UnknownHostException ex) {
            Logger lgr = Logger.getLogger(DB_connect.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        catch (IOException ex ){
            Logger lgr = Logger.getLogger(DB_connect.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
