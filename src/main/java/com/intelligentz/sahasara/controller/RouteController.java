package com.intelligentz.sahasara.controller;

import com.intelligentz.sahasara.database.DBConnection;
import com.intelligentz.sahasara.database.DBHandle;
import com.intelligentz.sahasara.model.Route;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lakshan on 2017-03-24.
 */
public class RouteController {
//    public static void AddRoute(Route route){
//        String query="INSERT INTO route(ROUTE_NAME) VALUES(?)";
//        Object data[]={contact.getCustomerId(),contact.getContactNo()};
//        return DBHandle.setData(DBConnection.getConnectionToDB(), query, data);
//    }
    
    public static List<Object[]> getAllRoutes() throws SQLException, ClassNotFoundException, IOException, PropertyVetoException{
        String query="SELECT r.ROUTE_ID, r.ROUTE_NAME, (SELECT CITY_NAME FROM city WHERE CITY_ID=r.START), (SELECT CITY_NAME FROM city WHERE CITY_ID=r.END) FROM route r";
        Connection connection = DBConnection.getDBConnection().getConnection();
        ResultSet resultSet = DBHandle.getData(connection, query);
        List<Object[]> routeList=new ArrayList<Object[]>();
        while(resultSet.next()){
            routeList.add(new Object[]{resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)});
        }
        connection.close();
        return routeList;
    }
    
    public static String getRouteId(String routeName) throws ClassNotFoundException, SQLException, IOException, PropertyVetoException{
        String query ="SELECT ROUTE_ID FROM route WHERE ROUTE_NAME = ?";
        Object[] data={routeName};
        Connection connection = DBConnection.getDBConnection().getConnection();
        ResultSet resultSet= DBHandle.getData(connection, query,data);
        String id = null;
        if(resultSet.next()){
            id = resultSet.getString(1);
        }else{
            // when the route name is not existing, it will be added
            String query2="INSERT INTO route(ROUTE_NAME, ROUTE_CLUSTER, START, END) VALUES(?,?, ?,?)";
            Object data2[]={routeName,1, 1, 1};
            boolean status = DBHandle.setData(connection, query2, data2);
            if(status){
                String query3 ="SELECT ROUTE_ID FROM route WHERE ROUTE_NAME = ?";
                Object[] data3={routeName};
                ResultSet resultSet3= DBHandle.getData(connection, query3,data3);
                if(resultSet3.next()){
                    id = resultSet3.getString(1);
                }
            }
        }
        connection.close();
        return id;
    }
}
