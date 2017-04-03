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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
            String SQL1 = "SELECT BUS_ID,BUS_NAME,BUS_NO,STATE,mon,tue,wed,thu,fri,sat,sun FROM `schedule` natural join `bus` natural join `route` where route_id = ?";

            preparedStatement = connection.prepareStatement(SQL1);
            preparedStatement.setString(1, busRoute);
            
            resultSet = preparedStatement.executeQuery();
            String[] week_id = {"mon","tue","wed","thu","fri","sat","sun"};
            String[] week_id_cap = {"MON","TUE","WED","THU","FRI","SAT","SUN"};
            DateFormat df = new SimpleDateFormat("MM-dd");
            
            data+=  "<thead><tr><th>Bus Number</th><th>State</th>";
                Calendar cal = Calendar.getInstance();
                cal.setTime(cal.getTime());
                int index = 0;
                switch(cal.get(Calendar.DAY_OF_WEEK)){
                    case Calendar.MONDAY: index = 0; break;
                    case Calendar.TUESDAY: index = 1; break;
                    case Calendar.WEDNESDAY: index = 2; break;
                    case Calendar.THURSDAY: index = 3; break;
                    case Calendar.FRIDAY: index = 4; break;
                    case Calendar.SATURDAY: index = 5; break;
                    case Calendar.SUNDAY: index = 6; break;
                    default: index = 0; break;
                }
                
                for( int j = index; j <= 6; j++){
                    cal.add(Calendar.DATE, j - index);
                    String temp = df.format(cal.getTime());
                    data += "<th>"+week_id_cap[j]+" "+temp+"</th>";
                    cal.add(Calendar.DATE, index - j);
                    
                }
                for( int j = 0; j < index; j++){
                    cal.add(Calendar.DATE, j + 7 - index);
                    String temp = df.format(cal.getTime());
                    data += "<th>"+week_id_cap[j]+" "+temp+"</th>";
                    cal.add(Calendar.DATE, index - j-7);
                }
							
	    data+= "</tr></thead><tfoot><tr><th>Bus Number</th><th>State</th>";
		for( int j = index; j <= 6; j++){
                    cal.add(Calendar.DATE, j - index);
                    String temp = df.format(cal.getTime());
                    data += "<th>"+week_id_cap[j]+" "+temp+"</th>";
                    cal.add(Calendar.DATE, index - j);
                    
                }
                for( int j = 0; j < index; j++){
                    cal.add(Calendar.DATE, j + 7 - index);
                    String temp = df.format(cal.getTime());
                    data += "<th>"+week_id_cap[j]+" "+temp+"</th>";
                    cal.add(Calendar.DATE, index - j-7);
                }
            data+= "</tr></tfoot><tbody>";
            while (resultSet.next()) {
                String device_id = resultSet.getString("BUS_ID");
                String bus_no = resultSet.getString("BUS_NO");
                String bus_name = resultSet.getString("BUS_NAME").split(",")[0].trim();
                int bus_state = resultSet.getInt("STATE");

                LOGGER.log(Level.INFO, "getDevideData - result :{0}", device_id);
                
                data += "<tr>";
                data += "<td>"+bus_name+"</td>";
                data += "<td>"+((bus_state==1)?"ACTIVE":"INACTIVE")+"</td>";
                for(int i = index; i <= 6; i++){
                    String state = resultSet.getBoolean(week_id[i])?"checked":"";
                    data += "<td><input onclick=\"sendResponse(\'"+device_id+"\',\'"+week_id[i]+"\',this)\" type=\"checkbox\" "+state+"></td>";
                }
                for(int i = 0; i < index; i++){
                    String state = resultSet.getBoolean(week_id[i])?"checked":"";
                    data += "<td><input onclick=\"sendResponse(\'"+device_id+"\',\'"+week_id[i]+"\',this)\" type=\"checkbox\" "+state+"></td>";
                }
                data += "</tr>";
                
                
            }
            data+= "</tbody>";
            
        } 
        catch (SQLException | IOException | PropertyVetoException ex) 
        {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            data = "Error occured!";
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
