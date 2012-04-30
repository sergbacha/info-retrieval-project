/**
 * 
 */
package com.vt.ir.io;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;
import android.os.Build;
import android.util.Log;

/**
 * In charge of send HTTP call to the server.
 *	(from google io) entity which:
 *	Ð Prepares the HTTP URL & HTTP request body
 *	Ð Executes the HTTP transaction
 *	Ð Processes the HTTP response
 * 
 * @author 	Sergio Bernales
 * @date 	Mar 19, 2012
 *
 * Copyright 2012 Locomoti LLC. All rights reserved.
 *
 */
public class RestMethodInvoker implements HttpHelper{
	private static final String TAG = "RestMethodInvoker";
	private static final boolean DEBUG = true;
	
	HttpURLConnection mHttpURLConnection;
	Context mContext;

	private URL mUrl;
	private String mResource;
	 
	public RestMethodInvoker(Context context, String url) {
		mContext = context;
		
		// Create the URL
		try {
				mUrl = new URL(url);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Send a get request to our server and return
	 * the response in json format.
	 * Returns null if got an error or nothing
	 * 
	 * @param mJsonMapper 
	 * @return 
	 */
	public JsonNode get(final ObjectMapper jsonMapper) {
		JsonNode getResponse = null;
		
		try {
			mHttpURLConnection = (HttpURLConnection) mUrl.openConnection();
			loadCommonHttpSettings();

			// read the input into our JSON vo wrapper
			getResponse = jsonMapper.readTree(mHttpURLConnection.getInputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			mHttpURLConnection.disconnect();
		}
		
		return getResponse;
	}
	
	/**
	 * Send a post request to our server. It will use the URL that was created
	 * at object initialization (see buildRestMethodInvoker(...))
	 * 
	 * @param newUser the JSON data we will add to our request body
	 */
	public JsonNode post(final ObjectMapper jsonMapper, final String jsonString) {
		JsonNode postResponse = null;
		
		try {
			Log.d(TAG, "posting: "+mUrl.toString());
			
			// open the connection
			mHttpURLConnection = (HttpURLConnection) mUrl.openConnection();
			
			
			// specificy other settings that all request verbs have in common
			loadCommonHttpSettings();
			
			// specific settings settings of this verb
			mHttpURLConnection.setDoOutput(true);
			mHttpURLConnection.setDoInput(true);
			mHttpURLConnection.setRequestMethod(HTTP_POST);


			
			// add data to connection
//			final String data = jsonData.toString();
			/** Performance suggestions per ANdroid javadocs HttpURLConnection*/
			mHttpURLConnection.setFixedLengthStreamingMode(jsonString.length());
			

			// remember we set http.keepalive in IntroActivity to false
			
			// send the output // could also use DataOutputStream
			BufferedOutputStream output = new BufferedOutputStream(mHttpURLConnection.getOutputStream());
			output.write(jsonString.getBytes());
			output.flush();
			
			output.close();
			
			// get the input
			postResponse = jsonMapper.readTree(mHttpURLConnection.getInputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			// ALWAYS DISCONNECT
			mHttpURLConnection.disconnect();
		}
		
		return postResponse;
	}

	/**
	 * Common Http Setting that all calls will us.
	 * Like they all will use the same user-agent
	 * and the same enocding type.
	 * 
	 */
	private void loadCommonHttpSettings() {
		mHttpURLConnection.addRequestProperty(USER_AGENT_HEADER, USER_AGENT + " ( " + Build.DEVICE + " " + Build.ID + ")");
		mHttpURLConnection.setRequestProperty(CONTENT_TYPE_HEADER, APPLICATION_JSON);
		mHttpURLConnection.setRequestProperty("Connection", "keep-alive");
	}
}
