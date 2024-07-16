/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_app;

import java.sql.Timestamp;

/**
 *
 * @author John Simmonds
 */
public class Fault {
    String  eqmtID, fault, zd_ticket_num, status;
    Timestamp update;
    int id;
    
    public Fault(int id, String eqmtID, String fault, String zd_ticket_num, String status, Timestamp update){
        this.id=id;
        this.eqmtID=eqmtID;
        this.fault=fault;
        this.zd_ticket_num=zd_ticket_num;
        this.status=status;
        this.update=update;
    }
    
}