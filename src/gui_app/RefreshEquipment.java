/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_app;

import java.util.TimerTask;
import javax.swing.JTextField;

/**
 * @author John Simmonds
 */
public class RefreshEquipment extends TimerTask {
    
    private Application_Main parentMain;
    private String equipmentName;
    
     public RefreshEquipment(Application_Main parentMain, String equipmentName){
         this.parentMain=parentMain;
         this.equipmentName=equipmentName;
     }
    
    public void run() {
        // Refresh the equipment on main display after Update Script is run
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
