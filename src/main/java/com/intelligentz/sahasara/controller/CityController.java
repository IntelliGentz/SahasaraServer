package com.intelligentz.sahasara.controller;

import com.intelligentz.sahasara.database.DBConnection;
import com.intelligentz.sahasara.database.DBHandle;
import com.intelligentz.sahasara.model.City;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lakshan on 2017-03-24.
 */
public class CityController {
    
        public static boolean AddCity(City city) throws SQLException, ClassNotFoundException{
            String query="INSERT INTO city(CITY_NAME,LONGITUDE,LATITUDE) VALUES(?,?,?)";
            Object data[]={city.getName(),city.getLongitude(),city.getLatitude()};
            return DBHandle.setData(DBConnection.getConnectionToDB(), query, data);
        }
        
        public static City GetCity(String id) throws ClassNotFoundException, SQLException {
            String query ="SELECT * FROM city WHERE CITY_ID = ?";
            Object[] data={id};
            ResultSet resultSet= DBHandle.getData(DBConnection.getConnectionToDB(), query,data);
            if(resultSet.next()){
                return new City(resultSet.getInt("CITY_ID"),resultSet.getString("CITY_NAME"),resultSet.getDouble("LONGITUDE"),resultSet.getDouble("LATITUDE"));
            }
            return null;
        }
}
