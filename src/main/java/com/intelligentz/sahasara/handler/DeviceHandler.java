package com.intelligentz.sahasara.handler;

/**
 * Created by lakshan on 11/12/16.
 */


import com.intelligentz.sahasara.constants.AuthorizationTypes;
import com.intelligentz.sahasara.constants.ContentTypes;
import com.intelligentz.sahasara.constants.URLs;
import com.intelligentz.sahasara.exception.IdeabizException;
import com.intelligentz.sahasara.model.ideabiz.RequestMethod;
import org.apache.log4j.Logger;

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

}