/**
 * 
 */
package com.vt.ir.providers;

import android.net.Uri;
import android.provider.BaseColumns;

import com.vt.ir.providers.RoshamboDatabase.Tables;


public class RoshamboContract {
	
	interface CrimeColumns {
		/** Lattitude of crime */
		String CRIME_LAT = "crime_lat";
		/** Longitude of Crime*/
		String CRIME_LONG = "crime_long";
		/** Search keyword for these results*/
		String CRIME_KEYWORD = null;
	}

	/** Items with which to build a URI */
	public static final String CONTENT_AUTHORITY = "com.vt.ir.crime";
	private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	
	
	/** Path for our different tables */
	private static final String PATH_CRIME = "crime";
	
	
	/**
	 * Our crime data. usually the results of a previous search query
	 */
	public static class Crime implements CrimeColumns, RESTColumns, BaseColumns{
		public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_CRIME).build();
		
		public static final String CONTENT_TYPE = 
			"vnd.android.cursor.item/vnd.roshambo.user";
		
     
        /**
         * Uri with the official android db id
         */
        public static Uri buildUserUri(int crimeID) {
        	return CONTENT_URI.buildUpon().appendPath(Integer.toBinaryString(crimeID)).build();
        }
   	}
}
