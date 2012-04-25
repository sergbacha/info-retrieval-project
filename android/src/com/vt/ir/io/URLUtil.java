/**
 * 
 */
package com.vt.ir.io;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
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
	 * Builds an http url that makes bouding box queries to our solr server
	 * @param lat
	 * @param lon
	 * @param query
	 */
	public static String buildSolrBoundingBoxURL(String query, double lat, double lon, int distance){
		StringBuilder sb = new StringBuilder(urlBase);
		
		// envocde query
		
		try {
			query = URLEncoder.encode(query, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// add to url
		sb.append(urlQueryArg).append(query);//add query
		sb.append(urlGeoFilterArg1).append(lat).append(",").append(lon);//add lat long
		sb.append(urlGeoFilterArg2).append(distance);//append diamter
		sb.append(urlGeoFilterArg3);

//		URI uri = null;
//		try {
//			uri = new URI(sb.toString());
//			return uri.toASCIIString();
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return sb.toString();
	}
}
