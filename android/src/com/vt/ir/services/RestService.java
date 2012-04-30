/**
 * 
 */
package com.vt.ir.services;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import com.vt.ir.io.ResponseProcessor;
import com.vt.ir.io.RestMethodInvoker;
import com.vt.ir.io.URLUtil;
import com.vt.ir.vo.Crime;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;


public class RestService extends IntentService {
	private static final String TAG = "RestService";
	
	public static final String EXTRA_ADDRESS = "address";
	public static final String EXTRA_QUERY = "query";
	public static final String EXTRA_SERVICE_ACTION = "action";
	public static final String EXTRA_CRIME = "crime";
	
	
	public static final int ACTION_LOCAL_SEARCH = 0x01;
	public static final int ACTION_BASIC_SEARCH = 0x02;
	public static final int ACTION_SUBMIT_CRIME = 0x03;
	
	// Mapper responsible for building json nodes and such
	ObjectMapper mObjectMapper;
	ResponseProcessor mResponseProcessor;
	
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
		mResponseProcessor = new ResponseProcessor(getContentResolver());
	}

	/* (non-Javadoc)
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		
		// get the action that client request 
		int match = intent.getIntExtra(EXTRA_SERVICE_ACTION, -1);
		Bundle extras = intent.getExtras();
		
		switch(match){
			case ACTION_LOCAL_SEARCH:
				// grab all the extras
				Address address = (Address) extras.get(EXTRA_ADDRESS);
				String localQuery = extras.getString(EXTRA_QUERY);
				
				localSearch(address, localQuery);
				break;
			case ACTION_BASIC_SEARCH:
				// grab all the extras
				String basicQuery = extras.getString(EXTRA_QUERY);
				
				basicSearch(basicQuery);
				break;
				
			case ACTION_SUBMIT_CRIME:
				
				Crime crime = extras.getParcelable(EXTRA_CRIME);
				
				submitCrime(crime);
			default:
				break;
		}
	}

	/**
	 * submits a crime to the server
	 * @param crime
	 */
	private void submitCrime(Crime crime) {
		Writer strWriter = new StringWriter();
		JsonNode rootNode = null;
		try {
			//create the root node
			rootNode = mObjectMapper.createObjectNode();
			
			// add the add command
			JsonNode addCommandNode = mObjectMapper.createObjectNode();
			((ObjectNode)rootNode).put("add", addCommandNode );
			
			// add the doc node
			JsonNode docNode = mObjectMapper.createObjectNode();
			((ObjectNode)addCommandNode).put("doc", docNode );
			
			// add our data
			mObjectMapper.writeValue(strWriter, crime);
			String crimeJsonString =  strWriter.toString();
			JsonNode crimeNode = mObjectMapper.readTree(crimeJsonString.getBytes());
			((ObjectNode)docNode).putAll((ObjectNode)crimeNode);
			
			
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d(TAG, ":");
		
		RestMethodInvoker invoker = new RestMethodInvoker(this, URLUtil.POST_CRIME_URL);
		
		invoker.post(mObjectMapper, rootNode.toString());
		
	}
	/**
	 * @param basicQuery
	 */
	private void basicSearch(String basicQuery) {
		String url = URLUtil.buildBasicURL(basicQuery);

		// create an invoker that will do the server magic
		RestMethodInvoker invoker = new RestMethodInvoker(this, url);
		
		// get the dan thing
		JsonNode searchResults = invoker.get(mObjectMapper);
		
		mResponseProcessor.processSearchResults(this, searchResults, null);
	}
	/**
	 * Create an invoker objects to make a search
	 * request to our solr server
	 * 
	 * @param address
	 * @param query
	 */
	private void localSearch(Address address, String query) {
		// build the query url
		String url = URLUtil.buildSolrBoundingBoxURL(query, address.getLatitude(), 
							address.getLongitude(), 20);
		
		// create an invoker that will do the server magic
		RestMethodInvoker invoker = new RestMethodInvoker(this, url);
		
		// get the dan thing
		JsonNode searchResults = invoker.get(mObjectMapper);
		
		mResponseProcessor.processSearchResults(this, searchResults, address);
	}
}
