/**
 * 
 */
package com.vt.ir.ui.fragments;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.vt.ir.R;
import com.vt.ir.services.ServiceHelper;
import com.vt.ir.ui.TapControlledMap;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SearchFragment extends Fragment implements OnClickListener {

	TextView mGeocodeResults;
	Geocoder mGeocoder;
	EditText mLocationEditText;
	ServiceHelper mRestServieHelper;
	Address mAddress;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		mGeocoder = new Geocoder(activity, Locale.getDefault());
		mRestServieHelper = new ServiceHelper();
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if(container == null)
			return null;
		
		// create the view
		View v = inflater.inflate(R.layout.fragment_search, container, false);
		
		// references to view
		mGeocodeResults = (TextView) v.findViewById(R.id.geocoded_results);
		mLocationEditText = (EditText) v.findViewById(R.id.input_text);
		
		// set the button listeners
		Button tempbutton = (Button) v.findViewById(R.id.location_button);
		tempbutton.setOnClickListener(this);
		
		Button button = (Button) v.findViewById(R.id.search_button);
		button.setOnClickListener(this);
		
		return v;
	}
	
	/**
	 * Bakcgorund geocdogin task to let UI be free
	 * @author 	Sergio Bernales
	 * @date 	Apr 21, 2012
	 *
	 * Copyright 2012 Locomoti LLC. All rights reserved.
	 *
	 */
	private class GeocodeAsyncTask extends AsyncTask<String, Integer, Address>{

		Geocoder mGeocoder;
		/**
		 * 
		 */
		public GeocodeAsyncTask(Geocoder geocoder) {
			mGeocoder = geocoder;
		} 
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Address doInBackground(String... address) {
			List<Address> addresses = null;
			try {
				addresses = mGeocoder.getFromLocationName(address[0], 3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(addresses != null)
				return addresses.get(0);
			return null;
		}
		
		protected void onPostExecute(Address result) {
	       SearchFragment.this.onGeocodeComplete(result);
	    }
	}


	/**
	 * After geocode has been completed
	 * @param result 
	 */
	public void onGeocodeComplete(Address result) {
		mGeocodeResults.setText(result.getAddressLine(0));
		mAddress = result;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		int id = v.getId();
		
		String inputText = mLocationEditText.getText().toString();
		
		if(id == R.id.location_button){
			// create and start the geocoding task
			GeocodeAsyncTask task = new  GeocodeAsyncTask(mGeocoder);
			task.execute(inputText);
		}
		else{
			mRestServieHelper.search(getActivity(), mAddress, inputText);
			Intent i = new Intent(getActivity(), TapControlledMap.class);
			startActivity(i);
		}
	}
}
