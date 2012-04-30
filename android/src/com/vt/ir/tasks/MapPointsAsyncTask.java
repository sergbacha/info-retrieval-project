/**
 * 
 */
package com.vt.ir.tasks;

import java.io.FileDescriptor;
import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import com.vt.ir.Utils;
import com.vt.ir.ui.SimpleItemizedOverlay;
import com.vt.ir.vo.Crime;
import com.vt.ir.vo.CrimesWrapper;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author 	Sergio Bernales
 * @date 	Apr 29, 2012
 *
 * Copyright 2012 Locomoti LLC. All rights reserved.
 *
 */
public class MapPointsAsyncTask extends AsyncTask<SimpleItemizedOverlay, Integer, Crime[]> {
	private static final String TAG = "MapPointsAsyncTask";

	private static final String FIELD_RESPONSE = "response";
	private static final String FIELD_RESPONSE_HEADERS = "responseHeader";
	private static final String FIELD_QUERY = "q";
	private static final String FIELD_PARAMS = "params";
	private static final String FIELD_DOCS = "docs";
	
	ObjectMapper mObjectMapper;
	Activity mActivity;
	String mJsonCrimes;
	String mQuery;
	
	
	
	
	/**
	 * 
	 */
	public MapPointsAsyncTask(String jsonPoints, Activity activity) {
		mObjectMapper = new ObjectMapper();
		mActivity = activity;
		mJsonCrimes = jsonPoints;
	}
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Crime[] doInBackground(SimpleItemizedOverlay... params) {
		// TODO Auto-generated method stub
		Log.d(TAG, mJsonCrimes);
		
		// convert the json string into actual crime objects
		Crime[] crimes = null;
		try {
			JsonNode rootNode = mObjectMapper.readTree(mJsonCrimes.getBytes());
			
			// grab the query to use for syntax highlighting of the descriptions
			mQuery = rootNode.get(FIELD_RESPONSE_HEADERS)
							.get(FIELD_PARAMS)
							.get(FIELD_QUERY).getTextValue();
			
			
			// get the relevants docs array
			JsonNode responseNode = rootNode.get(FIELD_RESPONSE);
			JsonNode crimeDocsArray = responseNode.get(FIELD_DOCS);

			crimes = mObjectMapper.readValue(crimeDocsArray, Crime[].class);
			
			Log.d(TAG, "figuring this out");
			
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//get overlay where we will place our crime points
		SimpleItemizedOverlay itemizedOverlay = params[0];
		
		// make sure borht the crime objects and the overlay exist!
		if(crimes == null || itemizedOverlay == null)
			return null;
				
		
		String[] queryTermArray = mQuery.split(" ");
		// now loop through each crime and create the dang points
		for(Crime c : crimes){
			int[] coords = Utils.splitCoordinates(c.getCoordinates_latlong());
			GeoPoint point = new GeoPoint(coords[Utils.LAT_IN_ARRAY], coords[Utils.LON_IN_ARRAY]);
			OverlayItem overlayItem = new OverlayItem(point, 
							Utils.boldQueryTerms(queryTermArray, c.getCatagory()), 
							Utils.boldQueryTerms(queryTermArray, c.getDescription()));

			itemizedOverlay.addOverlay(overlayItem);
		}

		return crimes;
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Crime[] crimes) {
		((OnMapPointsCreatedListener) mActivity).OnMapPointsCreated(crimes);
	}
	
	public interface OnMapPointsCreatedListener{
		public void OnMapPointsCreated(Crime[] crimes);
	}
}
