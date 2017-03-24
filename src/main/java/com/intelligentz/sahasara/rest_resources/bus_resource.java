package com.intelligentz.sahasara.rest_resources;

import com.intelligentz.sahasara.constants.IdeaBizConstants;
import com.intelligentz.sahasara.exception.IdeabizException;
import com.intelligentz.sahasara.handler.DeviceHandler;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response subscribeForUpdates( @PathParam("app_id") String app_id, @PathParam("bus_id") String bus_id  ) {
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



}
