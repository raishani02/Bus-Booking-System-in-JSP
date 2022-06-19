<%@page import="java.util.*,AllLayout.*,com.digitalbd.*,AllLayout.*" %>
<%
ArrayList<HashMap<String,String>> buses = new ArrayList<HashMap<String,String>>();
buses trnObj = new buses();
String stationTo = (String) request.getParameter("to");
String stationFrom = (String) request.getParameter("from");
String sCoach = (String) request.getParameter("coach");
String journey_date = (String) request.getParameter("journey_date");
Station stationFromObj = new Station();
Station stationToObj = new Station();
if(stationTo != null || stationFrom != null){
	buses = trnObj.SearchBusFromTo(stationFrom, stationTo,sCoach);
	Stations tempStationsObj = new Stations();
	stationFromObj = tempStationsObj.getStation(stationFrom);
	stationToObj = tempStationsObj.getStation(stationTo);
}

%>

<%@ include file="header.jsp" %>
<form class="ticket_selecting_form" action="<%= Helper.baseUrl %>FindTicket.jsp">
	<h2 class="title">  <span>Bus Route Showing For ::</span> <%= stationFromObj.name %> to  <%= stationToObj.name %> <span>:: Journey Date -</span> <%= journey_date %> </h2>
	<table class="table rs_shadow">
		<tr>
			<th>Serial</th>
			<th>Bus No</th>
			<th>Bus Name</th>
			<th>Class</th>
			<th>Departure Time</th>
			<th>Unit Fare</th>
			<th>Number Of Seat</th>
			<th>Selection</th>
		</tr>
		<% for(int i =0; i<buses.size(); i++){ 
			HashMap<String,String> tempBus= buses.get(i);
		
		%>
		<tr>
			<td><%= i+1 %></td>
			<td><%= tempBus.get("code") %></td>
			<td><%= tempBus.get("name") %></td>
			<td><%= tempBus.get("coach") %></td>
			<td><%= tempBus.get("time") %></td>
			<td><%= tempBus.get("fare")+" " +Helper.Currency %></td>
			<td>
				<select name="total_seat"  class="form-control total_seat_select">
					    <option value="1">01</option>
					    <option value="2">02</option>
					    <option value="3">03</option>
					    <option value="4">04</option>
				</select>
			</td>
			<td>
				<a href="javascript:;" class="btn btn-success rs_search_ticket" data-date="<%= journey_date %>" data-destination="<%= tempBus.get("destination_id") %>">Search Ticket</a>
			</td>
		</tr>
		<% } %>
	</table>
</form>
<div id="rs_ticket_result">

</div>
<%@ include file='footer.jsp' %>