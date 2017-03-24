package com.intelligentz.sahasara.controller;

import com.intelligentz.sahasara.database.DBConnection;
import com.intelligentz.sahasara.database.DBHandle;
import com.intelligentz.sahasara.model.Route;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lakshan on 2017-03-24.
 */
public class RouteController {
    public static void AddRoute(Route route){
//        String query="INSERT INTO route VALUES(?,?)";
//        Object data[]={contact.getCustomerId(),contact.getContactNo()};
//        return DBHandle.setData(DBConnection.getConnectionToDB(), query, data);
    }
    
    public static List<Object[]> getAllRoutes() throws SQLException, ClassNotFoundException, IOException, PropertyVetoException{
        String query="SELECT r.ROUTE_ID, r.ROUTE_NAME, (SELECT CITY_NAME FROM city WHERE CITY_ID=r.START), (SELECT CITY_NAME FROM city WHERE CITY_ID=r.END) FROM route r";
        ResultSet resultSet = DBHandle.getData(DBConnection.getDBConnection().getConnection(), query);
        List<Object[]> routeList=new ArrayList<Object[]>();
        while(resultSet.next()){
            routeList.add(new Object[]{resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)});
        }
        return routeList;
    }
    
    public static String getRouteId(String routeName) throws ClassNotFoundException, SQLException, IOException, PropertyVetoException{
        String query ="SELECT ROUTE_ID FROM route WHERE ROUTE_NAME = ?";
        Object[] data={routeName};
        ResultSet resultSet= DBHandle.getData(DBConnection.getDBConnection().getConnection(), query,data);
        if(resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
