package com.intelligentz.sahasara.handler;

/**
 * Created by lakshan on 11/12/16.
 */


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intelligentz.sahasara.constants.AuthorizationTypes;
import com.intelligentz.sahasara.constants.ContentTypes;
import com.intelligentz.sahasara.constants.URLs;
import com.intelligentz.sahasara.controller.BusController;
import com.intelligentz.sahasara.exception.IdeabizException;
import com.intelligentz.sahasara.model.Bus;
import com.intelligentz.sahasara.model.ideabiz.RequestMethod;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.StringMatchFilter;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Malinda_07654 on 6/10/2016.
 */
public class DeviceHandler {

    public static Logger logger = Logger.getLogger(DeviceHandler.class);

    //sending sms with custom sender name and address
    public String getDeviceList(String app_id) throws IdeabizException{
        logger.info("Retriving Device list :");
        String URL = URLs.DEVICE_LIST_URL + app_id;
        String response = new IdeaBizAPIHandler().sendAPICall(URL, RequestMethod.POST, "{}","", ContentTypes.TYPE_FORM_URL_ENCODED,ContentTypes.TYPE_JSON, AuthorizationTypes.TYPE_BEARER);
        logger.info("Device list Response :"+ response);
        System.out.println("Device list Response :"+ response);
        if (response.contains("requestError")){

            response+="\n......\n Request: ";
        }
        JsonParser parser = new JsonParser();
        JsonArray deviceListJson = (JsonArray) parser.parse(response);
        ArrayList<Bus> busList = new ArrayList<>();
         Bus bus;
        for (JsonElement deviceElement : deviceListJson){
            JsonObject deviceObject = deviceElement.getAsJsonObject();
            String busName = deviceObject.get("name").getAsString();
            String[] busNameArray = busName.split(",");
            String busRouteId = null;
            if (busNameArray.length > 1) {
                busRouteId = busNameArray[1].trim();
            }
            bus = new Bus();
            bus.setNumber(deviceObject.get("number").getAsString());
            bus.setName(busName);
            bus.setBusRouteId(busRouteId);
            busList.add(bus);
        }
        try {
            BusController.checkAndUpdateNewBusses(busList);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return response;
    }
    public String getDeviceLocation(String app_id, String bus_id) throws IdeabizException{
        String body = "?number="+bus_id;
        logger.info("Retriving Device list :");
        String URL = URLs.DEVICE_LOCATION_URL + app_id + body;
        System.out.println("URL:" + URL);
        String response = new IdeaBizAPIHandler().sendAPICall(URL, RequestMethod.POST, "{}","", ContentTypes.TYPE_FORM_URL_ENCODED,ContentTypes.TYPE_JSON, AuthorizationTypes.TYPE_BEARER);
        logger.info("Device location Response :"+ response);
        if (response.contains("requestError")){
            response+="\n......\n Request: ";
        }
        return response;
    }
    public String subscribeForUpdates(String app_id, ArrayList<String> bus_id_list, int interval) throws IdeabizException{
        String body = "?interval="+ String.valueOf(interval);
        body += "&callbackUrl=";
        try {
            body += URLEncoder.encode(URLs.SUBSCRIBER_ALERT_RECEIVING_URL, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        body += "&deviceList=";
        body += getListAsAString(bus_id_list);
        logger.info("Subscibing for updates :");
        String URL = URLs.SUBSCRIBE_FOR_UPDATES_URL + app_id + body;
        System.out.println("URL:" + URL);
        String response = new IdeaBizAPIHandler().sendAPICall(URL, RequestMethod.POST, "{}","", ContentTypes.TYPE_FORM_URL_ENCODED,ContentTypes.TYPE_JSON, AuthorizationTypes.TYPE_BEARER);
        logger.info("Subscribe update Response :"+ response);
        if (response.contains("requestError")){

            response+="\n......\n Request: ";
        }
        return response;
    }

    public String subscribeForProximityAlert(String app_id) throws IdeabizException{
        String body = "?callbackUrl=";
        try {
            body += URLEncoder.encode(URLs.SUBSCRIBER_PROXIMITY_ALERT_RECEIVING_URL, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info("Subscibing for proximity :");
        String URL = URLs.SUBSCRIBE_FOR_PROXIMITY_ALERT_URL + app_id + body;
        System.out.println("URL:" + URL);
        String response = new IdeaBizAPIHandler().sendAPICall(URL, RequestMethod.POST, "{}","", ContentTypes.TYPE_FORM_URL_ENCODED,ContentTypes.TYPE_JSON, AuthorizationTypes.TYPE_BEARER);
        logger.info("Subscribe update Response :"+ response);
        if (response.contains("requestError")){
            response+="\n......\n Request: ";
        }
        return response;
    }

    private String getListAsAString(ArrayList<String> bus_id_list) {
        String listString = "";
        for (String bus_id : bus_id_list) {
            listString += (bus_id + ",");
        }
        return listString.substring(0,listString.length()-1);
    }


}