package com.digitalbd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import AllLayout.Bus;

public class buses {
	public String name,id,code,type="Shuvon",total_seat="";
	public int totalSeat;
	String table = "buses";
	Database db;
	public buses(){
		this.total_seat=this.name=this.id=this.code = "";
		db = new Database();
		this.totalSeat = 0;
	}
	public buses(String trnId) {
		db = new Database();
		String sql = "SELECT * FROM "+this.table+" WHERE id='"+trnId+"'";
		try {
			ResultSet result = this.db.statement.executeQuery(sql);
			while(result.next()) {
				this.name = result.getString("name");
				this.id = result.getString("id");
				this.type = result.getString("type");
				this.code = result.getString("code");
				this.total_seat = result.getString("total_seat");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<Bus> getAll() {
		ArrayList<Bus> buses = new ArrayList<Bus>();
		String sqlQuery = "SELECT * FROM " + this.table;
		try {
			ResultSet result = db.statement.executeQuery(sqlQuery);
			while(result.next()) {
				Bus temp = new Bus();
				temp.id = result.getString("id");
				temp.name = result.getString("name");
				temp.code = result.getString("code");
				temp.type = result.getString("type");
				temp.totalSeat = Integer.parseInt(result.getString("total_seat"));
				buses.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buses;
	}
	public void Save() {
		if(this.id.equals("")) {
			this.CreateNew();
		}else {
			this.CreateNew();
		}
		
	}
	public void Delete (String trnId) {
		String sql = "DELETE FROM "+this.table+" WHERE id = '"+trnId+"'";
		try {
			this.db.statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*Search Destinations buses*/
	
	public ArrayList<HashMap<String,String>> SearchBusFromTo(String from,String to,String coach){
		ArrayList<HashMap<String,String>> buses = new ArrayList<HashMap<String,String>>();
		String sql = null;
		if(coach != null && !coach.equals("any")) {
			 sql = "SELECT destinations.*,buses.type as coach,buses.id as BusId,buses.name,buses.code,buses.type FROM buses"
					+ " INNER JOIN destinations ON "
					+ " buses.id = destinations.Bus_id"
					+ " WHERE destinations.station_from = '"+from+"'"
					+ " AND destinations.station_to = '"+to+"'"
					+ " AND buses.type = '"+coach+"'"
					+ " ORDER BY name ASC";
		}else {
			 sql = "SELECT destinations.*,buses.type as coach,buses.id as BusId,buses.name,buses.code,buses.type FROM buses"
					+ " INNER JOIN destinations ON "
					+ " buses.id = destinations.Bus_id"
					+ " WHERE destinations.station_from = '"+from+"'"
					+ " AND destinations.station_to = '"+to+"'"
					+ " ORDER BY name ASC";
		}
		
		try {
			ResultSet result = this.db.statement.executeQuery(sql);
			while(result.next()) {
				HashMap<String,String> tempBus = new HashMap<String,String>();
				tempBus.put("name", result.getString("name"));
				tempBus.put("destination_id", result.getString("id"));
				tempBus.put("coach", result.getString("coach"));
				tempBus.put("Bus_id", result.getString("BusId"));
				tempBus.put("code", result.getString("code"));
				tempBus.put("time", result.getString("time"));
				tempBus.put("code", result.getString("code"));
				tempBus.put("fare", result.getString("fare"));
				buses.add(tempBus);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return buses;
	}
	
	private int CreateNew() {
		String sqlQquery = "";
		sqlQquery = "INSERT INTO "+this.table+"(name,code,total_seat,type)"
				+ " VALUES('"+this.name+"','"+this.code+"','"+Integer.toString(this.totalSeat)+"','"+this.type+"')";
					
		try {
			return  this.db.statement.executeUpdate(sqlQquery,Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
}