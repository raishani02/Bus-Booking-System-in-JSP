package com.digitalbd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import TicketSystemInterface.DatabaseModel;

public class Booking implements DatabaseModel{
	private String tableName = "booking";
	public String id, destination_id, booking_date, journey_date, bus_id, seat_numbers, passenger_id, number_of_seat, payment_status, status, note;
	Database db;
	public Booking() {
		db = new Database();
	}
	public Booking(int argId) {
		
	}
	
	public ResultSet FindByUser(String userId){
		ResultSet result = null;
		String sql = "SELECT * FROM "+this.tableName+ " WHERE passenger_id='"+userId+"' ORDER BY id DESC";
		try {
			result = this.db.statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public HashMap<String,String> Find(String destination,String date,String totalSeat){
		HashMap<String,String> result = new HashMap<String,String>();
		result.put("message", "Seat Is Not Available!");
		result.put("is_avaiable","no");
		result.put("j_date", date);
		String query = null;
		query = "SELECT destinations.*,buses.name as busName,buses.code as busCode,buses.type as type from destinations"
				+ " INNER JOIN buses ON"
				+ " destinations.bus_id = buses.id"
				+ " WHERE destinations.id = '"+destination+"'";
		try {
			ResultSet resultset = this.db.statement.executeQuery(query);
			while(resultset.next()) {
				result.put("destinations_id",resultset.getString("id"));
				result.put("bus_name",resultset.getString("busName"));
				result.put("bus_code",resultset.getString("busCode"));
				result.put("bus_type",resultset.getString("type"));
				result.put("from",resultset.getString("station_from"));
				result.put("to",resultset.getString("station_to"));
				result.put("time",resultset.getString("time"));
				result.put("fare",resultset.getString("fare"));
				result.put("total_seat",resultset.getString("total_seat"));
				result.put("seat_range",resultset.getString("seat_range"));
				result.put("is_avaiable","yes");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public HashMap<String,String> FindTicket(String Id){
		HashMap<String,String> result = new HashMap<String,String>();
		
		String query = null;
	
		query= "Select users.name as u_name,buses.name as busName,buses.code as busCode,buses.type as type,"
				+ " booking.booking_date as bookingtime, booking.journey_date as journeytime,"
				+ " booking.seat_numbers as total_seat,destinations.station_from,"
				+ " destinations.station_to,destinations.fare as fare from booking"
				+ " inner join users on users.id = booking.passenger_id"
				+ " inner join buses on buses.id = booking.bus_id"
				+ " inner join destinations on destinations.id = booking.destination_id"
				+ " WHERE booking.id = '"+Id+"'";
		try {
			ResultSet resultset = this.db.statement.executeQuery(query);
			while(resultset.next()) {
				result.put("user_name",resultset.getString("u_name"));
				result.put("bus_name",resultset.getString("busName"));
				result.put("bus_code",resultset.getString("busCode"));
				result.put("bus_type",resultset.getString("type"));
				result.put("from",resultset.getString("station_from"));
				result.put("to",resultset.getString("station_to"));
				result.put("bookingtime",resultset.getString("bookingtime"));
				result.put("journeytime",resultset.getString("journeytime"));
				result.put("fare",resultset.getString("fare"));
				result.put("seats",resultset.getString("total_seat"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void SetById(int argId) {
		
	}
	public long BookNow(Destination destinationObj,String userId,String date,String totalSeat) {
		long bookId = 0;
		String[] seatRange = destinationObj.seat_range.split("-");
		int seatStart = Integer.parseInt(seatRange[0]);
		int seatEnd = Integer.parseInt(seatRange[1]);
		ArrayList<Integer> seatBooked = new ArrayList<Integer>();
		String seatsToBook= "";
		// Find all booked seat
		String sql = "SELECT seat_numbers FROM booking WHERE destination_id = '"+destinationObj.id+"'  AND journey_date ='"+date+"' ";
		try {
			ResultSet result = this.db.statement.executeQuery(sql);
			if(result.next()) {
				String tempSeat[] = result.getString("seat_numbers").split(",");
				for(String seat:tempSeat) {
					seatBooked.add(Integer.parseInt(seat));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// select free seat
		int maxSeatNeed = 0;
		for(int i=seatStart; i<=seatEnd; i++) {
			if(maxSeatNeed>=Integer.parseInt(totalSeat))
				break;
			
			if(!seatBooked.contains(i)) {
				maxSeatNeed++;
				if(seatsToBook.isEmpty()) {
					seatsToBook = Integer.toString(i);
				}else {
					seatsToBook = seatsToBook+","+Integer.toString(i);
				}
			}
			
		}
		this.destination_id = destinationObj.id;
		this.booking_date = date;
		this.journey_date = date;
		this.bus_id = destinationObj.bus_id;
		this.passenger_id = userId;
		this.number_of_seat = totalSeat;
		this.payment_status = "pending";
		this.status = "success";
		this.note = "note";
		this.seat_numbers = seatsToBook;
		// Insert the seats
		String sqlBooking = "INSERT INTO booking(destination_id,booking_date,journey_date,bus_id,seat_numbers,passenger_id,number_of_seat,payment_status,status,note)"
				+ " VALUES('"+this.destination_id+"','"+this.booking_date+"','"+this.journey_date+"','"+this.bus_id+"','"+this.seat_numbers+"','"+this.passenger_id+"','"+this.number_of_seat+"','"+this.payment_status+"','"+this.status+"','"+this.note+"')";
		
		try {
			bookId = this.db.statement.executeUpdate(sqlBooking,Statement.RETURN_GENERATED_KEYS);
			ResultSet res = this.db.statement.getGeneratedKeys();
		      System.out.println("Auto-incremented values of the column ID generated by the current PreparedStatement object: ");
		      while (res.next()) {
		    	  bookId = Long.parseLong(res.getString(1));
		         System.out.println(bookId);
		      }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Hello destination booking");
			e.printStackTrace();
		}
		
		return bookId;
	}
	public boolean IsAvailable(Destination destination,String date,String totalSeat) {
		int seatNeed = Integer.parseInt(totalSeat);
		boolean isAvailable = true;
		int busMaxSeat = Integer.parseInt(destination.total_seat);
		String sql = "SELECT SUM(number_of_seat) as totalSeatBooked FROM booking WHERE destination_id = '"+destination.id+"'  AND journey_date ='"+date+"' ";
		try {
			ResultSet result = this.db.statement.executeQuery(sql);
			if(result.next()) {
				if(busMaxSeat <= (result.getInt("totalSeatBooked") + seatNeed) ) {
					isAvailable = false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isAvailable;
	}
	
	@Override
	public int Save() {
		// TODO Auto-generated method stub
		return 0;
	}
	

	@Override
	public int Update() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void Delete() {
		// TODO Auto-generated method stub
	}

	@Override
	public String GetTableName() {
		// TODO Auto-generated method stub
		return this.tableName;
	}
	
	
	
}
