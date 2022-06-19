<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,com.digitalbd.*,AllLayout.*" %>

<%
String bookedid = (String)request.getAttribute("bookingid");
Booking booking = new Booking();
String totalSeat = (String) request.getAttribute("totalSeat");
HashMap<String,String> tickDetails = booking.FindTicket(bookedid);
Double totalamount = 0.0;
totalamount = Double.parseDouble(tickDetails.get("fare")) * Double.parseDouble(totalSeat);
Stations tempStation = new Stations();
Station stationFrom = tempStation.getStation(tickDetails.get("from"));
Station stationTo = tempStation.getStation(tickDetails.get("to"));
%>

<div class="ticket_print_section">
	<div class="rs_shadow single_ticket" style="background-image: url('images/ticket_bg.jpg');">
		<div class="ticket_header">
			<h2>Bus Ticketing System</h2>
		</div>
		<div class="ticket_inner">
			<div class="customer_part">
				<h4>
					<strong>Name of passenger</strong>
					<%= tickDetails.get("user_name") %>
				</h4>

				<div class="ticket_col_1">
					<table>
						<tr>
							<td>
								<strong>Bus Name</strong>
								<span><%= tickDetails.get("bus_name") %></span>
								<strong>From</strong>
								<span><%= stationFrom.name %></span>
								<strong>To</strong>
								<span><%= stationTo.name %></span>
							</td>
							<td class="wd_100px text_center">
								<strong>Bus No</strong>
								<%= tickDetails.get("bus_code") %>
							</td>
							<td class="wd_100px text_center">
								<strong>Journey Date</strong>
								<%= tickDetails.get("journeytime") %>
							</td>
							<td class="wd_100px text_center">
								<strong>Time</strong>
								<%= tickDetails.get("bookingtime") %>
							</td>
						</tr>
					</table>
					<table>
						<tr>
							<td>
								<strong>Class/Coach</strong>
								<span><%= tickDetails.get("bus_type") %></span>
							</td>
							<td  class="wd_100px text_center">
								<strong>Seat</strong>
								<%= tickDetails.get("seats") %>
							</td>
							<td  class="text_center">
								<strong>Issue Date</strong>
								<%= tickDetails.get("bookingtime") %>
							</td>
							<td  class="wd_100px text_center">
								<strong>Fare</strong>
								<%= totalamount %>
							</td>
							<td width="90"  class="text_center">
								<img class="qr_code" src="images/qr.png" alt="">
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="ticet_footer">
			<span>Have a nice journey.</span>
		</div>
	</div>
</div>