/**
 * 
 */
package com.vt.ir;

import android.location.Address;

/**
 * @author 	Sergio Bernales
 * @date 	Apr 30, 2012
 *
 * Copyright 2012 Locomoti LLC. All rights reserved.
 *
 */
public class Utils {
	
	private static final float GEO_POINT_CONV = 1000000;
	public static final short LAT_IN_ARRAY = 0x00;
	public static final short LON_IN_ARRAY = 0x01;
	
	
	/**
	 * Splits the string coordinatesinto two int values
	 * the geopoints can use
	 * 
	 * @param coor
	 * @return
	 */
	public static int[] splitCoordinates(String coor){
		String[] coors = coor.split(",");
		
		int[] intCoors = new int[2];
		
		// process longitude
		intCoors[LAT_IN_ARRAY] = (int) (Float.parseFloat(coors[LAT_IN_ARRAY])*GEO_POINT_CONV);
		
		// process latittude
		intCoors[LON_IN_ARRAY] = (int) (Float.parseFloat(coors[LON_IN_ARRAY])*GEO_POINT_CONV);
		
		return intCoors;
	}
	
	/**
	 * Get geo coordinates from an Address object for the
	 * gropoint format
	 * @param a
	 * @return
	 */
	public static int[] getCoordinates(Address a){
		int[] intCoors = new int[2];
		
		// process longitude
		intCoors[LAT_IN_ARRAY] = (int) (a.getLongitude()*GEO_POINT_CONV);
		
		// process latittude
		intCoors[LON_IN_ARRAY] = (int) (a.getLatitude()*GEO_POINT_CONV);
		
		return intCoors;
	}
	
	/**
	 * Surround query terms with bold tags so Android textviews
	 * can display as such
	 * @param terms
	 * @param text
	 * @return
	 */
	public static String boldQueryTerms(String[] terms, String text){
		
		for(String t : terms){
			text = text.replace(t, "<b>"+t+"</b>");
		}
		
		return text;
	}
}
