/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author John Simmonds -- Need to changed the way the connection is set and returned on everything.
 */
public class DB_connect {
    static String url = "jdbc:mysql://10.10.235.180:3306/fr_support?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static String user = "root";
    static String password = "modular";
    
    protected Connection connection = null;
    protected Statement statement =null;
    
    protected ResultSet execute(String sql) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
        return statement.executeQuery(sql);
    }
    
    protected void insert(String sql){
        try{
            connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareCall(sql);
            statement.execute();
            connection.close();
        }    
        catch (SQLException ex){
            Logger lgr = Logger.getLogger(DB_connect.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
                
    protected void update(String newValue, String sqlColumnName, String sqlTable, int ID){
        String query = "update " + sqlTable + " set " + sqlColumnName + "=\'" + newValue + "\'" + " where ID=\'" + Integer.toString(ID) + "\';";
        try {
            connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareCall(query);
            statement.execute();
            connection.close();
        } catch (SQLException ex){
            Logger lgr = Logger.getLogger(DB_connect.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    protected void delete(String sqltable, int ID){
        String query = "delete from " + sqltable + " where ID=\'" + Integer.toString(ID) + "\';";
        try {
            connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareCall(query);
            statement.execute();
            connection.close();
        } catch (SQLException ex){
            Logger lgr = Logger.getLogger(DB_connect.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
