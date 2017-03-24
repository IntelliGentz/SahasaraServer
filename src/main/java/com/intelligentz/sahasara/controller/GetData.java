/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelligentz.sahasara.controller;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.intelligentz.sahasara.database.DBConnection;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author ndine
 */
public class GetData {
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static Connection connection;
    private static final Logger LOGGER = Logger.getLogger( GetData.class.getName() );
    public static String getDeviceData(String busRoute){
        
        LOGGER.log(Level.INFO, "getDevideData - "+busRoute);
        String data = "";
        try 
        {
            connection = DBConnection.getDBConnection().getConnection();
            String SQL1 = "SELECT BUS_ID,BUS_NO,mon,tue,wed,thu,fri,sat,sun FROM `schedule` natural join `bus` natural join `route` where route_id = ?";

            preparedStatement = connection.prepareStatement(SQL1);
            preparedStatement.setString(1, busRoute);
            
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String device_id = resultSet.getString("BUS_ID");
                String bus_no = resultSet.getString("BUS_NO");
                String[] week_id = {"mon","tue","wed","thu","fri","sat","sun"};
               
                LOGGER.log(Level.INFO, "getDevideData - result :{0}", device_id);
                
                data += "<tr>";
                data += "<td>"+bus_no+"</td>";
                for(int i=0; i<=6; i++){
                    String state = resultSet.getBoolean(week_id[i])?"checked":"";
                    data += "<td><input onclick=\"sendResponse(\'"+device_id+"\',\'"+week_id[i]+"\',this)\" type=\"checkbox\" "+state+"></td>";
                }
                data += "</tr>";
                
                
            }
            
        } 
        catch (SQLException | IOException | PropertyVetoException ex) 
        {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
        finally 
        {
            try {
                DbUtils.closeQuietly(resultSet);
                DbUtils.closeQuietly(preparedStatement);
                DbUtils.close(connection);
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        return data;
    }
    
    public static String getRouteData(){
        
        String data = "";
        try 
        {
            connection = DBConnection.getDBConnection().getConnection();
            String SQL1 = "SELECT ROUTE_ID,ROUTE_NAME FROM `route`";

            preparedStatement = connection.prepareStatement(SQL1);
            //preparedStatement.setString(1, busRoute);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                String route_id = resultSet.getString("ROUTE_ID");
                String route_name = resultSet.getString("ROUTE_NAME");
               
                LOGGER.log(Level.INFO, "getRouteData - result :{0}", route_name);
                
                data += "<option onclick=\"selectRoute(this)\" id=\""+route_id+"\" value=\""+route_id+"\">"+route_name+"</option>";
                
            }
            
        } 
        catch (SQLException | IOException | PropertyVetoException ex) 
        {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
        finally 
        {
            try {
                DbUtils.closeQuietly(resultSet);
                DbUtils.closeQuietly(preparedStatement);
                DbUtils.close(connection);
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        return data;
    }
    
    
}
