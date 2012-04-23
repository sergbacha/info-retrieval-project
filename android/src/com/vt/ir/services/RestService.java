/**
 * 
 */
package com.vt.ir.services;

import org.codehaus.jackson.map.ObjectMapper;

import com.vt.ir.io.RestMethodInvoker;
import com.vt.ir.io.URLUtil;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;


public class RestService extends IntentService {
	private static final String TAG = "RestService";
	
	public static final String EXTRA_ADDRESS = "address";
	public static final String EXTRA_QUERY = "query";
	public static final String EXTRA_SERVICE_ACTION = "action";
	
	public static final int ACTION_SEARCH = 0x01;
	
	// Mapper responsible for building json nodes and such
	ObjectMapper mObjectMapper;
	
	public RestService() {
		super(TAG);
	}
	public RestService(String name) {
		super(TAG);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		
		mObjectMapper = new ObjectMapper();
	}

	/* (non-Javadoc)
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		
		// get the action that client request 
		int match = intent.getIntExtra(EXTRA_SERVICE_ACTION, -1);
		
		switch(match){
			case ACTION_SEARCH:
				Bundle extras = intent.getExtras();
				
				// grab all the extras
				Address address = (Address) extras.get(EXTRA_ADDRESS);
				String query = extras.getString(EXTRA_QUERY);
				
				search(address, query);
				
				
				break;
				
			default:
				break;
		}
	}

	/**
	 * Create an invoker objects to make a search
	 * request to our solr server
	 * 
	 * @param address
	 * @param query
	 */
	private void search(Address address, String query) {
		
		// build the query url
		String url = URLUtil.buildSolrBoundingBoxURL(query, address.getLatitude(), 
							address.getLongitude(), 2);
		
		
		// create an invoker that will do the server magic
		RestMethodInvoker invoker = new RestMethodInvoker(this, url);
		
		// get the dan thing
		invoker.get(mObjectMapper);
	}
	

}
