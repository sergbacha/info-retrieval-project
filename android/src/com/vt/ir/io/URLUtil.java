package com.vt.ir.io;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Helps build SOlr Query URL. Because they are long and complicated like this:
 * 
 * http://localhost:8983/solr/select?wt=json&indent=true&q=car%20theft&fq={!geofil pt=38.8981759,-77.273669 d=1 sfield=coordinates_latlong}
 *
 * @author 	Sergio Bernales
 * @date 	Apr 22, 2012
 *
 *
 */
public class URLUtil {
	public static final String urlBase = "http://192.168.10.112:8983/solr/select?wt=json&indent=true";
	public static final String urlQueryArg = "&q=";
	public static final String urlGeoFilterArg1 = "&fq={!geofilt+pt=";
	public static final String urlGeoFilterArg2 = "+d=";
	public static final String urlGeoFilterArg3 = "+sfield=coordinates_latlong}";
	
	/**
	 * Builds an HTTP URL that makes bounding box queries to our solr server
	 * @param lat
	 * @param lon
	 * @param localQuery
	 */
	public static String buildSolrBoundingBoxURL(String localQuery, double lat, double lon, int distance){
		StringBuilder sb = new StringBuilder(urlBase);
		
		// envocde query
		localQuery = encodeURL(localQuery);

		// add to url
		sb.append(urlQueryArg).append(localQuery);//add query
		sb.append(urlGeoFilterArg1).append(lat).append(",").append(lon);//add lat long
		sb.append(urlGeoFilterArg2).append(distance);//append diamter
		sb.append(urlGeoFilterArg3);

		return sb.toString();
	}

	/**
	 * just encodes our query stringper http standards
	 * @param localQuery
	 * @return
	 */
	private static String encodeURL(String localQuery) {
		try {
			localQuery = URLEncoder.encode(localQuery, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return localQuery;
	}

	/**
	 * Builds a basic query search URL. No distance necessary.
	 * @return
	 */
	public static String buildBasicURL(String basicQuery) {
		StringBuilder sb = new StringBuilder(urlBase);
		
		// envocde query
		basicQuery = encodeURL(basicQuery);
		
		// add to url
		sb.append(urlQueryArg).append(basicQuery);//add query
		
		return sb.toString();
	}
}
