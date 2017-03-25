package com.intelligentz.sahasara.controller;

import com.intelligentz.sahasara.database.DBConnection;
import com.intelligentz.sahasara.database.DBHandle;
import com.intelligentz.sahasara.model.City;
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
public class CityController {
    
        public static boolean AddCity(City city) throws SQLException, ClassNotFoundException, IOException, PropertyVetoException{
            String query="INSERT INTO city(CITY_NAME,LONGITUDE,LATITUDE) VALUES(?,?,?)";
            Object data[]={city.getName(),city.getLongitude(),city.getLatitude()};
            Connection connection = DBConnection.getDBConnection().getConnection();
            boolean status = DBHandle.setData(connection, query, data);

            connection.close();
            return status;
        }
        
        public static City GetCity(String id) throws ClassNotFoundException, SQLException, IOException, PropertyVetoException {
            String query ="SELECT * FROM city WHERE CITY_ID = ?";
            Object[] data={id};
            Connection connection = DBConnection.getDBConnection().getConnection();
            ResultSet resultSet= DBHandle.getData(connection, query,data);
            City  city = null;
            if(resultSet.next()){
                city = new City(resultSet.getInt("CITY_ID"),resultSet.getString("CITY_NAME"),resultSet.getDouble("LONGITUDE"),resultSet.getDouble("LATITUDE"));
            }
            connection.close();
            return city;
        }
}
