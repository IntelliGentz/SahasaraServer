package com.intelligentz.sahasara.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intelligentz.sahasara.constants.AuthorizationTypes;
import com.intelligentz.sahasara.constants.ContentTypes;
import com.intelligentz.sahasara.constants.URLs;
import com.intelligentz.sahasara.exception.IdeabizException;
import com.intelligentz.sahasara.model.Bus;
import com.intelligentz.sahasara.model.ideabiz.RequestMethod;
import org.apache.http.auth.BasicUserPrincipal;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Lakshan on 2017-04-04.
 */
public class GoogleAPIHandler {
    public static Logger logger = Logger.getLogger(GoogleAPIHandler.class);
    public static final String API_KEY = "AIzaSyCHh_y_XBtPs0oZx_qYV6xSCSA0TAnLvEE";

    public String getDirections(String origin, String destination) throws IdeabizException {
        String body = "?origin="+origin;
        body += "&destination="+destination;
        body += "&key="+API_KEY;
        logger.info("Retriving directions :");
        String URL = URLs.GOOGLE_DIRECTION_URL + body;
        System.out.println("URL:" + URL);
        String response = new IdeaBizAPIHandler().sendAPICall(URL, RequestMethod.POST, "{}","", ContentTypes.TYPE_FORM_URL_ENCODED,ContentTypes.TYPE_JSON, AuthorizationTypes.TYPE_BEARER);
        logger.info("Device location Response :"+ response);
        if (response.contains("requestError")){
            response+="\n......\n Request: ";
        }
        return response;
    }

    public ArrayList<Bus> getBusListDirection (ArrayList<Bus> busList, String destination) {
        for (Bus bus : busList) {
            try {
                String origin = String.valueOf(bus.getLatitude()) + "," + String.valueOf(bus.getLongitude());
                String response = getDirections(origin,destination);
                JsonParser parser = new JsonParser();
                JsonObject directionObject = (JsonObject) parser.parse(response);
                JsonArray routes = directionObject.get("routes").getAsJsonArray();
                JsonObject route = routes.get(0).getAsJsonObject();
                JsonArray legs = route.get("legs").getAsJsonArray();
                JsonObject leg = legs.get(0).getAsJsonObject();
                JsonObject duration = leg.get("duration").getAsJsonObject();
                String durationText = duration.get("text").getAsString();
                bus.setExpectedTime(durationText);
            } catch (Exception E) {
                logger.error(E.getMessage());
            }
        }
        return busList;
    }
}
