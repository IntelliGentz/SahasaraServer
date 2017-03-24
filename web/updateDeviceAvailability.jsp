<%-- 
    Document   : checkHospitalId
    Created on : 19-Mar-2017, 16:55:30
    Author     : ndine
--%>
<%@ page session="true" %>
<%@page import="com.intelligentz.sahasara.controller.SetData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title></title>
    </head>
    <body>
        <%
           response.addHeader("STATE", SetData.setDeviceData(request.getParameter("device_id"),request.getParameter("week_id"),request.getParameter("state")));
        %>
    </body>
</html>
