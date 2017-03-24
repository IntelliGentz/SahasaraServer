package com.intelligentz.sahasara.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intelligentz.sahasara.DistanceCalculator;
import com.intelligentz.sahasara.constants.IdeaBizConstants;
import com.intelligentz.sahasara.database.DBConnection;
import com.intelligentz.sahasara.database.DBHandle;
import com.intelligentz.sahasara.exception.IdeabizException;
import com.intelligentz.sahasara.handler.DeviceHandler;
import com.intelligentz.sahasara.model.Bus;
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
    
    public static boolean addNewBus(Bus bus) throws SQLException, ClassNotFoundException{
        String query="INSERT INTO bus(BUS_NAME,BUS_NO,ROUTE_ID,STATE,LAST_DESTINATION,CURRENT_TIMESTAMP,CURRENT_LONGITUDE,CURRENT_LATITUDE) VALUES(?,?,?,?,?,?,?,?)";
        Object data[]={bus.getName(),bus.getNumber(),bus.getBusRouteId(),bus.getState(),bus.getLastDestination().getId(),bus.getLongitude(),bus.getLatitude()};
        return DBHandle.setData(DBConnection.getConnectionToDB(), query, data);
    }
    
    public static boolean isAvailable(String id) throws ClassNotFoundException, SQLException {
            String query ="SELECT * FROM bus WHERE BUS_NO = ?";
            Object[] data={id};
            ResultSet resultSet= DBHandle.getData(DBConnection.getConnectionToDB(), query,data);
            if(resultSet.next()){
                return true;
            }
            return false;
    }
    
//    public static boolean checkAndUpdateNewBusses(List<Bus> busList) throws ClassNotFoundException, SQLException, IdeabizException{
//        for (Bus bus : busList) {
//            if(!isAvailable(bus.getNumber())){
//                String response = new DeviceHandler().getDeviceLocation(IdeaBizConstants.APP_ID,bus.getNumber());
//                JsonParser parser = new JsonParser();
//                JsonArray deviceListJson = (JsonArray) parser.parse(response);
//
//                for (JsonElement deviceElement : deviceListJson){
//                    //JsonObject deviceObject = deviceElement.getAsJsonObject();
//
//                }
//            }
//        }
//    }
}
