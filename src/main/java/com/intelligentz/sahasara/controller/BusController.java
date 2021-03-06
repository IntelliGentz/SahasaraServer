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
import org.apache.commons.dbutils.DbUtils;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Lakshan on 2017-03-24.
 */
public class BusController {


    // bus heading direction ?
    public static List<Bus> getAvailableBusses(double latitude,double longitude,String routeName,String endCity) throws SQLException, ClassNotFoundException, IOException, PropertyVetoException{
        
        // get day
        String[] week_id = {"mon","tue","wed","thu","fri","sat","sun"};
        String[] week_id_cap = {"MON","TUE","WED","THU","FRI","SAT","SUN"};
        DateFormat df = new SimpleDateFormat("MM-dd");
            
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
            default: index = 0;
        }
        
        String query = "SELECT b.BUS_ID, b.BUS_NAME, b.CURRENT_LONGITUDE, b.CURRENT_LATITUDE " +
                "FROM bus as b ,route as r,city as c,schedule as s " +
                "WHERE (b.ROUTE_ID = r.ROUTE_ID) AND (b.LAST_DESTINATION =c.CITY_ID) " +
                "AND (s.BUS_ID = b.BUS_ID) AND r.ROUTE_NAME = ?  " +
                "AND c.CITY_NAME = ? AND s."+week_id_cap[index]+" = 1 AND (b.STATE = 1)";
    //String query="SELECT b.BUS_ID, b.BUS_NAME, b.CURRENT_LONGITUDE, b.CURRENT_LATITUDE FROM bus b ,route r WHERE b.ROUTE_ID = r.ROUTE_ID AND r.ROUTE_NAME = ?";
       // String query="SELECT b.BUS_ID, b.BUS_NAME, b.CURRENT_LONGITUDE, b.CURRENT_LATITUDE FROM bus b ,route r WHERE b.ROUTE_ID = r.ROUTE_ID AND r.ROUTE_NAME = ? AND (STATE = 1)";
        Object[] data = new Object[]{routeName, endCity};
        Connection connection =DBConnection.getDBConnection().getConnection();
        ResultSet resultSet = DBHandle.getData(connection, query, data);

        List<Bus> busList = new ArrayList<>();
        while (resultSet.next()) {
            String busId = resultSet.getString(1);
            String busName = resultSet.getString(2);
            double busLon = resultSet.getDouble(3);
            double busLat = resultSet.getDouble(4);

            if(DistanceCalculator.isAtRange(busLat, busLon, latitude, longitude, 200))
                busList.add(new Bus(busName,busLon,busLat));
        }
        connection.close();
        return busList;
    }
    
    // 
    public static boolean addNewBus(Bus bus) throws SQLException, ClassNotFoundException, IOException, PropertyVetoException{
        String query="INSERT INTO bus(BUS_NAME,BUS_NO,ROUTE_ID,STATE,LAST_DESTINATION,CURRENT_LONGITUDE,CURRENT_LATITUDE,CUR_TIMESTAMP) VALUES(?,?,?,?,?,?,?,?)";
//        String sDate1=bus.getTime();
//        Date date1 = null;
//        try {
//            date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate1);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Object data[]={bus.getName(),bus.getNumber(),bus.getBusRouteId(),bus.getState(),406,bus.getLongitude(),bus.getLatitude(),bus.getTime()};
        Connection connection = DBConnection.getDBConnection().getConnection();
        boolean res = DBHandle.setData(connection, query, data);
        if(res){
            String query2 ="SELECT BUS_ID FROM bus WHERE BUS_NO = ?";
            Object[] data2={bus.getNumber()};
            
            ResultSet resultSet2= DBHandle.getData(connection, query2,data2);
            if(resultSet2.next()){
                int busId = resultSet2.getInt(1);
                
                String query3="INSERT INTO schedule(BUS_ID) VALUES(?)";
                Object data3[]={busId};
                boolean res3 = DBHandle.setData(connection, query3, data3);
                if(res3){
                    connection.close();
                    return true;
                }
            }
        }
        connection.close();
        return false;
        
    }
    
    public static boolean isAvailable(String id) throws ClassNotFoundException, SQLException, IOException, PropertyVetoException {
        Connection connection = null;
        try {
        String query ="SELECT * FROM bus WHERE BUS_NO = ?";
        Object[] data={id};
        connection = DBConnection.getDBConnection().getConnection();
        ResultSet resultSet= DBHandle.getData(connection, query,data);
        if(resultSet.next()){
            return true;
        }
        return false;

        }finally {
            DbUtils.close(connection);
        }

    }
    
    public static void checkAndUpdateNewBusses(List<Bus> busList) throws ClassNotFoundException, SQLException, IdeabizException, IOException, PropertyVetoException{
        for (Bus bus : busList) {
            if(!isAvailable(bus.getNumber())){
                String response = new DeviceHandler().getDeviceLocation(IdeaBizConstants.APP_ID,bus.getNumber());
                JsonParser parser = new JsonParser();
                JsonArray deviceListJson = (JsonArray) parser.parse(response);

                for (JsonElement deviceElement : deviceListJson){
                    JsonObject deviceObject = deviceElement.getAsJsonObject();
//
//
//                    String busName = deviceObject.get("name").getAsString();
//                    String[] busNameArray = busName.split(",");
//                    String busRouteName = null;
//                    if (busNameArray.length > 1 ) {
//                        busRouteName = busNameArray[1].trim();
//                    }
                    //
                    if (bus.getBusRouteId() != null) {
                        bus.setBusRouteId(RouteController.getRouteId(bus.getBusRouteId()));

                        int state = 1;
                        if (deviceObject.get("state").getAsString().equals("off")) {
                            state = 0;
                        }
                        bus.setState(state);

                        bus.setTime(deviceObject.get("timestamp").getAsString());
                        bus.setLongitude(deviceObject.get("lon").getAsDouble());
                        bus.setLatitude(deviceObject.get("lat").getAsDouble());

                        // TODO : bus.setLastDestination

                        addNewBus(bus);
                    }
                }
            }
        }
    }

    // update all busses locations
    public static boolean updateBusLocations() throws ClassNotFoundException, SQLException, IdeabizException, IOException, PropertyVetoException{
        String query="SELECT BUS_NO FROM bus";
        Connection connection = DBConnection.getDBConnection().getConnection();
        ResultSet resultSet = DBHandle.getData(connection, query);

        boolean status = false;
        boolean status2 = false;
        
        while(resultSet.next()){
            String busNo = resultSet.getString(1);
            String response = new DeviceHandler().getDeviceLocation(IdeaBizConstants.APP_ID,busNo);

            JsonParser parser = new JsonParser();
            JsonArray deviceListJson = (JsonArray) parser.parse(response);
            JsonElement deviceElement = deviceListJson.get(0);

            JsonObject deviceObject = deviceElement.getAsJsonObject();

            String timeStamp = deviceObject.get("timestamp").getAsString();
            double lon = deviceObject.get("lon").getAsDouble();
            double lat = deviceObject.get("lat").getAsDouble();

            String query2="UPDATE bus SET CUR_TIMESTAMP=?,CURRENT_LONGITUDE=?,CURRENT_LATITUDE=? WHERE BUS_NO=?";
            Object data2[]={timeStamp,lon,lat,busNo};

            status2 =DBHandle.setData(connection,query2,data2);

            if(!status2){
                break;
            }            
        }
        connection.close();
        return status & status2;
    }
    
    // when specific busses given
    public static boolean updateBusLocations(List<Bus> busses) throws ClassNotFoundException, SQLException, IOException, PropertyVetoException{
        Connection connection = DBConnection.getDBConnection().getConnection();
        boolean status = false;

        for(Bus bus : busses){
            String query="UPDATE bus SET CUR_TIMESTAMP=?,CURRENT_LONGITUDE=?,CURRENT_LATITUDE=?,STATE=? WHERE BUS_NO=?";
            Object data[]={bus.getTime(),bus.getLongitude(),bus.getLatitude(),bus.getState(),bus.getName()};

            status =DBHandle.setData(connection,query,data);
        }
        connection.close();
        return status;
    }

    public static List<String> getAllBusNumbers() throws ClassNotFoundException, SQLException, IdeabizException, IOException, PropertyVetoException {
        String query = "SELECT BUS_NO FROM bus";
        Connection connection = DBConnection.getDBConnection().getConnection();
        ResultSet resultSet = DBHandle.getData(connection, query);

        List<String> busNos = new ArrayList<>();
        while (resultSet.next()) {
            busNos.add(resultSet.getString(1));
        }
        connection.close();
        return busNos;
    }

    public static boolean updateLastDestination(String busNo,int cityId) throws IOException, SQLException, PropertyVetoException{
            Connection connection = DBConnection.getDBConnection().getConnection();
            boolean status = false;
            String query = "SELECT START, END, bus.ROUTE_ID FROM bus,route where bus.ROUTE_ID = route.ROUTE_ID AND bus.BUS_NO = ?";
            Object data2[] = {busNo};

        ResultSet resultSet = DBHandle.getData(connection, query, data2);

        if (resultSet.next()) {
            int start = resultSet.getInt(1);
            int end = resultSet.getInt(2);
            int routeId = resultSet.getInt(3);
            if (start == cityId || end == cityId) {
                query ="UPDATE bus SET LAST_DESTINATION=? WHERE BUS_NO=?";
                Object data[]={cityId,busNo};
                status = DBHandle.setData(connection,query,data);
            }



//            if (start != cityId && end != cityId) {
//                if (start == 406 && end == 407) {
//                    query ="UPDATE route SET START=? WHERE ROUTE_ID=?";
//                } else if(end == 407){
//                    query ="UPDATE route SET END=? WHERE ROUTE_ID=?";
//                }
//                Object data3[] = {cityId,routeId};
//                status = DBHandle.setData(connection,query,data3);
//            }
        }
            connection.close();
            return status;
    }

}
