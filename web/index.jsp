<%-- 
    Document   : Schudule
    Created on : 23-Mar-2017, 23:58:16
    Author     : ndine
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@page import="com.intelligentz.sahasara.controller.GetData"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>iLocate</title>
    </head>
    <script language="JavaScript" src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script language="JavaScript" src="js/jquery.dataTables.min.js" type="text/javascript"></script>
<script language="JavaScript" src="js/dataTables.bootstrap.js" type="text/javascript"></script>
<script language="JavaScript" src="js/dataTables.bootstrap.js" type="text/javascript"></script>
<script type="text/javascript" src="js/script.js"></script>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/style.css">

<link rel="stylesheet" type="text/css" href="css/dataTables.bootstrap.css">
<body>
<div class="container">
	<div class="row">
		<h2 class="text-center">iLocate Bus schedule table</h2>
	</div>
    
        <select style="width:20%; position:absolute; left: 40%;" class="form-control input-sm" >
            <option>Select the route</option>
            <%
                    out.println(GetData.getRouteData());
            %>
        </select>
        <br>
        <br>
            <%
                if(request.getParameter("route")!=null){
                    out.println("<center><p> <b>Route</b> : <i>"+request.getParameter("route")+"</i></p></center>");
                }
            %>
        <div class="row">
		
            <div class="col-md-12">
            
                
                
           
<table id="datatable" class="table table-striped table-bordered" cellspacing="0" width="100%">
    				<thead>
						<tr>
							<th>Device</th>
							<th>MON</th>
							<th>TUE</th>
							<th>WED</th>
							<th>THU</th>
							<th>FRI</th>
							<th>SAT</th>
							<th>SUN</th>
						</tr>
					</thead>

					<tfoot>
						<tr>
							<th>Device</th>
							<th>MON</th>
							<th>TUE</th>
							<th>WED</th>
							<th>THU</th>
							<th>FRI</th>
							<th>SAT</th>
							<th>SUN</th>
						</tr>
					</tfoot>

					<tbody>
						<%
                                                    if(request.getParameter("route") != null){
                                                        out.println(GetData.getDeviceData(request.getParameter("route")));
                                                    }
                                                %>
                                                    
					</tbody>
				</table>
                                <script>
                                    
                                    function sendResponse(device_id, week_id, element) {
                                        var xhttp = new XMLHttpRequest();
                                        var State = element.checked? "1": "0";
                                        xhttp.onreadystatechange = function() {
                                          if (this.readyState == 4 && this.status == 200) {
                                            if(this.getResponseHeader("STATE") === "Success"){
                                                return true;
                                            }
                                            else{
                                                alert('Failed to update status');
                                                element.checked = State? false: true;
                                                window.location.assign("./index.jsp?route="+request.getParameter("route"));
                                                return false;
                                            }
                                          }
                                        };
                                        xhttp.open("GET", "updateDeviceAvailability.jsp?device_id="+device_id+"&week_id="+week_id+"&state="+State, true);
                                        xhttp.send();
                                    }
                                    function selectRoute(e){
                                        var val = e.value;
                                        window.location.assign("./index.jsp?route="+val);
                                    }
                                </script>

	
	</div>
	</div>
</div>

<div class="modal fade" id="edit" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
      <div class="modal-dialog">
    <div class="modal-content">
          <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title custom_align" id="Heading">Edit Your Detail</h4>
      </div>
          <div class="modal-body">
          <div class="form-group">
        <input class="form-control " type="text" placeholder="Tiger Nixon">
        </div>
        <div class="form-group">
        
        <input class="form-control " type="text" placeholder="System Architect">
        </div>
        <div class="form-group">
        
        
      <input class="form-control " type="text" placeholder="Edinburgh">
        
        </div>
      </div>
          <div class="modal-footer ">
        <button type="button" class="btn btn-warning btn-lg" style="width: 100%;"><span class="glyphicon glyphicon-ok-sign"></span> Update</button>
      </div>
        </div>
    <!-- /.modal-content --> 
  </div>
      <!-- /.modal-dialog --> 
    </div>
    
    
    
    <div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
      <div class="modal-dialog">
    <div class="modal-content">
          <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title custom_align" id="Heading">Delete this entry</h4>
      </div>
          <div class="modal-body">
       
       <div class="alert alert-danger"><span class="glyphicon glyphicon-warning-sign"></span> Are you sure you want to delete this Record?</div>
       
      </div>
        <div class="modal-footer ">
        <button type="button" class="btn btn-success" ><span class="glyphicon glyphicon-ok-sign"></span> Yes</button>
        <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> No</button>
      </div>
        </div>
    <!-- /.modal-content --> 
  </div>
      <!-- /.modal-dialog --> 
    </div>
    </body>
</html>
