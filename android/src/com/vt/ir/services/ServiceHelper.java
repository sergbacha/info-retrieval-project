/**
 * 
 */
package com.vt.ir.services;

import android.content.Context;
import android.content.Intent;
import android.location.Address;



public class ServiceHelper  {
	private static final String TAG = "ServiceHelper";
	
	/**
	 * Create an intent to send to out rest service that will do
	 * the actual rest call to the solr server
	 * 
	 * @param context
	 * @param address
	 * @param query
	 */
	public void search(Context context, Address address, String query) {
		
		// create the intent
		Intent i = new Intent(context, RestService.class);
		
		// put in the our extras
		i.putExtra(RestService.EXTRA_ADDRESS, address);
		i.putExtra(RestService.EXTRA_QUERY, query);
		// we want to do a search
		i.putExtra(RestService.EXTRA_SERVICE_ACTION, RestService.ACTION_SEARCH);
		
		context.startService(i);
	}
}
