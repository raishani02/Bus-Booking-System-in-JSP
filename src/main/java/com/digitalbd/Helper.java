package com.digitalbd;

import java.util.HashMap;

public class Helper {
	public static String baseUrl = "http://localhost:8080/bus17/";
	public static String TestName="Arslan";
	public static String Currency = "Rs";
	public static HashMap<String,String> bussCoach(){
		HashMap<String,String> coach = new HashMap<String,String>();
		coach.put("Luxury", "Luxury");
		coach.put("Super-Luxury", "Super-Luxury");
		coach.put("Executive", "Executive");
		return coach;
	}
}
