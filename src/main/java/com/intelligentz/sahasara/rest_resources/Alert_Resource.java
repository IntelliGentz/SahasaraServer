package com.intelligentz.sahasara.rest_resources;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intelligentz.sahasara.constants.IdeaBizConstants;
import com.intelligentz.sahasara.controller.BusController;
import com.intelligentz.sahasara.exception.IdeabizException;
import com.intelligentz.sahasara.handler.DeviceHandler;
import com.intelligentz.sahasara.model.Bus;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Lakshan on 2017-03-24.
 */
@Path("alert/")
public class Alert_Resource {
    public static Logger logger = Logger.getLogger(bus_resource.class);
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    @Path("subupdate/")
    public void getBusList(String request) {
        JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
        Iterable<?> keys = jsonObject.entrySet();
        Map<String, Object> attributes = new HashMap<String, Object>();
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
        ArrayList<Bus> busList = new ArrayList<>();
        Bus bus = null;
        JsonObject value;
        for(Map.Entry<String,JsonElement> entry : entrySet){
            bus = new Bus();
            bus.setNumber(entry.getKey());
            value = entry.getValue().getAsJsonObject();
            bus.setLatitude(Double.valueOf(value.get("lat").getAsString()));
            bus.setLatitude(Double.valueOf(value.get("lon").getAsString()));
            bus.setTime(value.get("timestamp").getAsString());
            bus.setState(Integer.parseInt(value.get("state").getAsString()));
            busList.add(bus);
        }

        try {
            BusController.updateBusLocations(busList);
            System.out.println("LOCATION UPDATED =========" + System.currentTimeMillis() + "============= :"+request);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        //System.out.println("ALERT =========" + System.currentTimeMillis() + "============= :"+request);
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    @Path("proximity/")
    public void getProximityAlert(String request) {
        JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
        String busNumber = jsonObject.get("number").getAsString();
        int cityId = Integer.parseInt(jsonObject.get("locationId").getAsString());
        try {
            BusController.updateLastDestination(busNumber,cityId);
            System.out.println("PROXIMITY UPDATED =========" + System.currentTimeMillis() + "============= :"+request);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
}
