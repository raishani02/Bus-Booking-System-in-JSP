<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="com.digitalbd.Helper,com.digitalbd.buses,java.util.ArrayList,AllLayout.Bus,java.util.Iterator" %>   
<%@ include file="header.jsp" %>
<%

	buses trn = new buses();

	if(request.getParameter("delete") != null){
		String trnId = (String) request.getParameter("delete");
		trn.Delete(trnId);
	}

	ArrayList<Bus> Buslist = new ArrayList<Bus>();
	Buslist = trn.getAll();
	Iterator busIt = Buslist.iterator();
%>
<div class="text-right">
	<a class="btn btn-success" href="Add.jsp">Create Bus</a>
</div>
<br>
<div class="box successfully_purschase_ticket">
	<h2 class="box_title">All Bus List</h2>
	<table class="table table-bordered">
		<tr>
			<td wide="50">Bus Code</td>
			<td>Name</td>
			<td>Total Seat</td>
			<td>Actions</td>
		</tr>
		<%
		while(busIt.hasNext()){
			Bus bus = (Bus) busIt.next();
			
			%>
			<tr>
				<td><%= bus.code %></td>
				<td><%= bus.name %></td>
				<td><%= bus.totalSeat %></td>
				<td><a href="?delete=<%= bus.id %>" class="btn btn-sm btn-danger">Delete</a></td>
			</tr>
			<%
		}
		%>
		
	</table>
</div>
<%@ include file="footer.jsp" %>