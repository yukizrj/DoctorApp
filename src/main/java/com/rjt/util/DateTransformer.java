package com.rjt.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class DateTransformer {
	static Map<Integer, String> map=new LinkedHashMap();
	
	static{
		map.put(1, "Sun");
		map.put(2, "Mon");
		map.put(3, "Tue");
		map.put(4, "Wed");
		map.put(5, "Thu");
		map.put(6, "Fri");
		map.put(7, "Sat");
	}
	
	public static String toString(int day){
		return map.get(day);
	}
	
	public static Timestamp getDate(String date){
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date d = df.parse(date);
			
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Timestamp getTime(String time){
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date d = df.parse("1991-12-25 " + time.replace("AM", "").replace("PM", ""));
			
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Timestamp getDateTime(String datetime){
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date d = df.parse(datetime);
			
			return new Timestamp(d.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getDateString(Date date){
		if(date!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(date);
		}
		else{
			return null;
		}
	}
	
	public static String getTimeString(Date date){
		if(date!=null){
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			return df.format(date);
		}
		else{
			return null;
		}
	}
}
