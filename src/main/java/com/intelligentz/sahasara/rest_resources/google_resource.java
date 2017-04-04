package com.intelligentz.sahasara.rest_resources;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intelligentz.sahasara.controller.BusController;
import com.intelligentz.sahasara.exception.IdeabizException;
import com.intelligentz.sahasara.handler.GoogleAPIHandler;
import com.intelligentz.sahasara.model.Bus;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
 * Created by Lakshan on 2017-04-04.
 */
@Path("google/")
public class google_resource {
    public static Logger logger = Logger.getLogger(bus_resource.class);
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    @Path("getdirection/")
    public Response getBusList(String request) {
        JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
        String origin = jsonObject.get("origin").getAsString();
        String destination = jsonObject.get("destination").getAsString();
        String response = "";
        try {
            response = new GoogleAPIHandler().getDirections(origin,destination);
        } catch (IdeabizException e) {
            e.printStackTrace();
        }
        //System.out.println("ALERT =========" + System.currentTimeMillis() + "============= :"+request);
        return Response.status(Response.Status.OK).entity(response).build();
    }
}
