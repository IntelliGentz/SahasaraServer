package com.intelligentz.sahasara.rest_resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intelligentz.sahasara.controller.BusController;
import com.intelligentz.sahasara.controller.RouteController;
import com.intelligentz.sahasara.model.Bus;

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

/**
 * Created by Lakshan on 2017-03-25.
 */
@Path("/route")
public class route_resource {
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/getall")
    public Response searchBuses(String request) {
        ArrayList<Object[]> routeList;
        try {
            routeList = (ArrayList<Object[]>) RouteController.getAllRoutes();
            JsonArray jsonArray = new JsonArray();
            for (Object[] route : routeList) {
                JsonObject routeObject = new JsonObject();
                routeObject.addProperty("name", (String) route[1]);
                routeObject.addProperty("start",(String)route[2]);
                routeObject.addProperty("end",(String)route[3]);
                jsonArray.add(routeObject);
            }
            JsonObject returnObject = new JsonObject();
            returnObject.addProperty("success",1);
            returnObject.addProperty("message","Load Completed");
            returnObject.add("routes",jsonArray);
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

}
