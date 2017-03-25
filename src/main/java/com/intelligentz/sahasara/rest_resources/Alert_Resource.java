package com.intelligentz.sahasara.rest_resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intelligentz.sahasara.constants.IdeaBizConstants;
import com.intelligentz.sahasara.exception.IdeabizException;
import com.intelligentz.sahasara.handler.DeviceHandler;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        System.out.println("ALERT ====================== :"+request);
    }
}
