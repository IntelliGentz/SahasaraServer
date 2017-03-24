/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelligentz.sahasara.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Heshan
 * 
 */
public class DBHandle {
    public static boolean setData(Connection connection , String querry) throws SQLException{
        Statement statement=connection.createStatement();
        int count=statement.executeUpdate(querry);
        if(count>0){
            return true;
        }else{
            return false;
        }
    }
    
    public static ResultSet getData(Connection connection , String querry) throws SQLException{
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(querry);
        return resultSet;
    }
    
    public static boolean setData(Connection connection,String sql,Object[] data) throws SQLException{
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        for (int i = 0; i < data.length; i++) {
            preparedStatement.setObject((i+1),data[i] );
        }
        int count=preparedStatement.executeUpdate();
         if(count>0){
            return true;
        }else{
            return false;
        }
    }
    
    public static ResultSet getData(Connection connection,String sql,Object[] data) throws SQLException{
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        for (int i = 0; i < data.length; i++) {
            preparedStatement.setObject((i+1),data[i] );
        }
        ResultSet resultSet=preparedStatement.executeQuery();
        return resultSet;
    }
}
