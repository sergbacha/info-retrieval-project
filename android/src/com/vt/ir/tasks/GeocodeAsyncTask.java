/**
 * 
 */
package com.vt.ir.tasks;

import android.location.Address;
import android.os.AsyncTask;

/**
 * @author 	Sergio Bernales
 * @date 	Apr 21, 2012
 *
 * Copyright 2012 Locomoti LLC. All rights reserved.
 *
 */
public class GeocodeAsyncTask extends AsyncTask<String, Integer, Address>{

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Address doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void onPostExecute(Long result) {
       
    }
}
