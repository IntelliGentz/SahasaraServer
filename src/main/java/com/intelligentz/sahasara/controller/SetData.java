/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DBConnection;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author ndine
 */
public class SetData {
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static Connection connection;
    private static final Logger LOGGER = Logger.getLogger( SetData.class.getName() );
    public static String setDeviceData(String busRoute, String device_id, String week_id, String State){
        
        LOGGER.log(Level.INFO, "getDevideData - "+busRoute);
        List<String> x = new ArrayList<>(Arrays.asList("mon","tue","wed","thu","fri","sat","sun"));
        String data = "Failed";
        try 
        {
            if(!x.contains(week_id)){
                return data;
            }
            connection = DBConnection.getDBConnection().getConnection();
            String SQL1 = "UPDATE schedule set "+week_id+"=? where device_id=?";

            preparedStatement = connection.prepareStatement(SQL1);
            preparedStatement.setString(1, State);
            preparedStatement.setString(2, device_id);
            
            preparedStatement.executeUpdate();
            LOGGER.log(Level.INFO, "setDevideData - result :{0}", device_id);
            data = "Success";
            
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
