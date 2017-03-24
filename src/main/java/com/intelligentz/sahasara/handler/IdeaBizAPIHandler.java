package com.intelligentz.sahasara.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intelligentz.sahasara.exception.IdeabizException;
import com.intelligentz.sahasara.model.ideabiz.Authenticator;
import com.intelligentz.sahasara.model.Data.DataImp;
import com.intelligentz.sahasara.model.Data.DataInterface;
import com.intelligentz.sahasara.model.ideabiz.RequestHandller;
import com.intelligentz.sahasara.model.ideabiz.RequestMethod;


public class IdeaBizAPIHandler {

    DataInterface df;
    public IdeaBizAPIHandler(){
        this.df=new DataImp();
    }

    public   String sendAPICall(String url, RequestMethod requestMethod, String body, String urlPara, String contenctType, String accpet, String authorizationType) throws IdeabizException {
        RequestHandller requestHandller = new RequestHandller();
        Authenticator authenticator = new Authenticator(df);
        String results = "{}";
        JsonParser parser = new JsonParser();
     switch (requestMethod){
         case GET:
             try {
                 results = requestHandller.getHTTP(url, authenticator.getAccessToken(),contenctType,accpet, authorizationType);
                 JsonObject tokenOut = (JsonObject)parser.parse(results);
                 return results;
             }
             catch(Exception e){
                 System.out.println("token error hit");
                 if(results.contains("Access Token")){
                     System.out.println("token expire hit");
                     authenticator.renewToken();
                     results = sendAPICall(url,requestMethod,body,urlPara, contenctType, accpet, authorizationType);
                 }else {
                     throw new IdeabizException("Error Accessing Ideabiz APIs: "+e.getMessage());
                 }
             }
             break;
         case POST:

            try {

                results = requestHandller.postHTTP(url, urlPara, authenticator.getAccessToken(), body, contenctType, accpet, authorizationType);
                System.out.println("POST result:"+results);
                JsonElement tokenOut = parser.parse(results);
                return results;
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                    if(results.contains("Inactive") || results.contains("Expired")){
                        System.out.println("token expire hit");
                        authenticator.renewToken();
                        results = sendAPICall(url,requestMethod,body,urlPara, contenctType, accpet, authorizationType);
                    }else {
                        throw new IdeabizException("Error Accessing Ideabiz APIs: "+e.getMessage());
                    }
                }
             break;
     }
        return results;
    }
}
