package com.intelligentz.sahasara.controller;

import com.intelligentz.sahasara.DistanceCalculator;
import com.intelligentz.sahasara.database.DBConnection;
import com.intelligentz.sahasara.database.DBHandle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Lakshan on 2017-03-24.
 */
public class BusController {
    
    public static List<Object[]> getAvailableBusses(double latitude,double longitude,String routeName,String endCity) throws SQLException, ClassNotFoundException{
        String query="SELECT b.BUS_ID, b.BUS_NAME, b.CURRENT_LONGITUDE, b.CURRENT_LATITUDE FROM bus b ,route r WHERE b.ROUTE_ID = r.ROUTE_ID AND r.ROUTE_NAME = ?";
        Object[] data = new Object[]{routeName};
        ResultSet resultSet = DBHandle.getData(DBConnection.getConnectionToDB(), query, data);

        List<Object[]> busList = new ArrayList<Object[]>();
        while (resultSet.next()) {
            String busId = resultSet.getString(1);
            String busName = resultSet.getString(2);
            double busLon = resultSet.getDouble(3);
            double busLat = resultSet.getDouble(4);

            if(DistanceCalculator.isAtRange(busLat, busLon, latitude, longitude, 2))
                busList.add(new Object[]{busName,busLon,busLat});
        }
        return busList;
    }
}
