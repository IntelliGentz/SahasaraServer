<%--
  Created by IntelliJ IDEA.
  User: Lakshan
  Date: 2017-03-31
  Time: 1:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String filename = "app-debug.apk";
    String filepath = "/media/apps/dinethe1016/apache-tomcat-8.0.20/webapps/";
    //String filepath = "C:\\Projects\\Sehesara\\app\\build\\outputs\\apk\\";
    response.setContentType("APPLICATION/OCTET-STREAM");
    response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");

    java.io.FileInputStream fileInputStream=new java.io.FileInputStream(filepath + filename);

    int i;
    while ((i=fileInputStream.read()) != -1) {
        out.write(i);
    }
    fileInputStream.close();
%>
