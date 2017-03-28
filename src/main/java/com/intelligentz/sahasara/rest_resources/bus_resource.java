package com.intelligentz.sahasara.rest_resources;

import com.google.gson.JsonArray;
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

/**
 * Created by Lakshan on 2017-03-20.
 */
@Path("/bus")
public class bus_resource {
    public static Logger logger = Logger.getLogger(bus_resource.class);
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("{app_id}/getall")
    public Response getBusList( @PathParam("app_id") String app_id) {
        String result = "";
        if (app_id.equals("*")){
            app_id = IdeaBizConstants.APP_ID;
        }
        try {
            result = new DeviceHandler().getDeviceList(app_id);
        } catch (IdeabizException e) {
            logger.error("Ideabiz error:"+e.getMessage());
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("{app_id}/getlocation/{bus_id}")
    public Response getBusLocation( @PathParam("app_id") String app_id, @PathParam("bus_id") String bus_id  ) {
        String result = "";
        if (app_id.equals("*")){
            app_id = IdeaBizConstants.APP_ID;
        }
        try {
            result = new DeviceHandler().getDeviceLocation(app_id, bus_id);
        } catch (IdeabizException e) {
            logger.error("Ideabiz error:"+e.getMessage());
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("{app_id}/subupdate/")
    public Response subscribeForUpdates( @PathParam("app_id") String app_id ) {
        String result = "";
        if (app_id.equals("*")){
            app_id = IdeaBizConstants.APP_ID;
        }
//        ArrayList<String> deviced = new ArrayList<>();
//        deviced.add("766426526");
//        deviced.add("766426174");
//        deviced.add("766426334");
//        deviced.add("766426176");
//        deviced.add("768511580");
//        deviced.add("765140331");
        try {
            result = new DeviceHandler().subscribeForUpdates(app_id, (ArrayList<String>) BusController.getAllBusNumbers(), 60);
        } catch (IdeabizException e) {
            logger.error("Ideabiz error:"+e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/search")
    public Response searchBuses(String request) {
        JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
        String route = jsonObject.get("route").getAsString();
        String heading = jsonObject.get("heading").getAsString();
        String lat = jsonObject.get("lat").getAsString();
        String lon = jsonObject.get("lon").getAsString();
        ArrayList<Bus> busList;
        try {
            busList = (ArrayList<Bus>) BusController.getAvailableBusses(Double.parseDouble(lat),Double.parseDouble(lon),route,heading);
            JsonArray jsonArray = new JsonArray();
            for (Bus bus : busList) {
                JsonObject busObject = new JsonObject();
                busObject.addProperty("name",bus.getName());
                busObject.addProperty("lat",bus.getLatitude());
                busObject.addProperty("lon",bus.getLongitude());
                jsonArray.add(busObject);
            }
            JsonObject returnObject = new JsonObject();
            returnObject.addProperty("success",1);
            returnObject.addProperty("message","Search Completed");
            returnObject.add("buses",jsonArray);
            return Response.status(Response.Status.OK).entity(returnObject.toString()).build();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.OK).entity("Error").build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("{app_id}/proxupdate/")
    public Response subscribeForProximityAlert( @PathParam("app_id") String app_id ) {
        String result = "";
        if (app_id.equals("*")){
            app_id = IdeaBizConstants.APP_ID;
        }
        try {
            result = new DeviceHandler().subscribeForProximityAlert(app_id);
        } catch (IdeabizException e) {
            logger.error("Ideabiz error:" + e.getMessage());
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

}
