package com.mahindra.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommonUtilities {

	public static Map<String, String> sessionValue = new HashMap<String, String>();
	
	static boolean display = true;
	public static void SOP(String s){
		
		if(display)
		System.out.println(s);
		
	}
	
	public static String formatDate(String MMddyyyy){
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String startDate = MMddyyyy;
		Date frmDate = null;
		String yyyyMMdd = null;
		
		if (startDate != null && !startDate.equals("")) {
			try {
				frmDate = sdf.parse(startDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Handle the ParseException here

			// This is to format the your current date to the desired format
			// DateFormat sdff = new SimpleDateFormat("dd-MMM-yy");
			DateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
			yyyyMMdd = sdff.format(frmDate);
		}
		
		return yyyyMMdd;
	}
}
