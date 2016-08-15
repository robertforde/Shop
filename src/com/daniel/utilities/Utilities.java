package com.daniel.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utilities {
	
	public static float floatToNumDec(float theFloat, int decimals){
		BigDecimal bd = new BigDecimal(theFloat);
		bd = bd.setScale(decimals, RoundingMode.HALF_UP);
		theFloat = bd.floatValue();
		return theFloat;
	}

	public static String stringToDec(String s){
		int dot = s.indexOf(".");
		String dec = s.substring(dot+1);

		if(dot == -1) {
			s += ".00";
		}else if (dec.length() == 1) {
			s += "0";
		}
		return s;
	}
	
	public static String removeCommas(String s){
		int comma = s.indexOf(",");
		
		if (comma >= 1) {
			String before = s.substring(0, comma);
			String after = s.substring(comma+1);
			s = before + after;
		}
		
		return s;
	}
	
	public static String floatToString2Dec(float f){
		f = floatToNumDec(f,2);
		String stringF = String.valueOf(f);
		stringF = stringToDec(stringF);
		
		return stringF;
	}
	
}