/**
 * 
 */
package com.vt.ir.services;

import com.vt.ir.vo.Crime;

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
	public void localSearch(Context context, Address address, String query) {
		
		// create the intent
		Intent i = new Intent(context, RestService.class);
		
		// put in the our extras
		i.putExtra(RestService.EXTRA_ADDRESS, address);
		i.putExtra(RestService.EXTRA_QUERY, query);
		// we want to do a search
		i.putExtra(RestService.EXTRA_SERVICE_ACTION, RestService.ACTION_LOCAL_SEARCH);
		
		context.startService(i);
	}
	
	/**
	 * Create an intent to send out rest service that will do the
	 * actual rest call to the solr server
	 * 
	 * @param context
	 * @param query
	 */
	public void basicSearch(Context context, String query) {
		
		// create the intent
		Intent i = new Intent(context, RestService.class);
		
		// put in the our extras
		i.putExtra(RestService.EXTRA_QUERY, query);
		// we want to do a search
		i.putExtra(RestService.EXTRA_SERVICE_ACTION, RestService.ACTION_BASIC_SEARCH);
		
		context.startService(i);
	}

	/**
	 * @param crimeHomeActivity
	 * @param crime
	 */
	public void submitCrime(Context context, Crime crime) {
		
		// create the intent
		Intent i = new Intent(context, RestService.class);
		
		// put in our extras
		i.putExtra(RestService.EXTRA_CRIME, crime);
		// we want to submit a crime
		i.putExtra(RestService.EXTRA_SERVICE_ACTION, RestService.ACTION_SUBMIT_CRIME);
		
		context.startService(i);
	}
}
