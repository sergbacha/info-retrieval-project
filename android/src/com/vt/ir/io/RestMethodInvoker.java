/**
 * 
 */
package com.vt.ir.io;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
	
	HttpsURLConnection mHttpsURLConnection;
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
			mHttpsURLConnection = (HttpsURLConnection) mUrl.openConnection();
			loadCommonHttpSettings();

			// read the input into our JSON vo wrapper
			getResponse = jsonMapper.readTree(mHttpsURLConnection.getInputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			mHttpsURLConnection.disconnect();
		}
		
		return getResponse;
	}

	/**
	 * Common Http Setting that all calls will us.
	 * Like they all will use the same user-agent
	 * and the same enocding type.
	 * 
	 */
	private void loadCommonHttpSettings() {
		mHttpsURLConnection.addRequestProperty(USER_AGENT_HEADER, USER_AGENT + " ( " + Build.DEVICE + " " + Build.ID + ")");
		mHttpsURLConnection.setRequestProperty(CONTENT_TYPE_HEADER, APPLICATION_JSON);
	}
}

