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
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class SearchFragment extends Fragment implements OnClickListener, OnCheckedChangeListener {

	TextView mGeocodeResults;
	Geocoder mGeocoder;
	EditText mQueryEditText;
	EditText mLocationEditText;
	Address mAddress;
	private CheckBox mCheckBox;
	private boolean mMyLocationChecked;
	ProgressBar mProgressBar;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		mGeocoder = new Geocoder(activity, Locale.getDefault());
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
		mQueryEditText = (EditText) v.findViewById(R.id.query_text);
		mLocationEditText = (EditText) v.findViewById(R.id.location_text);
		mProgressBar = (ProgressBar) v.findViewById(R.id.search_progress);
		
//		// set the button listeners
//		Button tempbutton = (Button) v.findViewById(R.id.location_button);
//		tempbutton.setOnClickListener(this);
		
		// set up listeners
		Button button = (Button) v.findViewById(R.id.search_button);
		button.setOnClickListener(this);
		
		mCheckBox = (CheckBox) v.findViewById(R.id.my_location_checkbox);
		mCheckBox.setOnCheckedChangeListener(this);
		
		return v;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mProgressBar.setVisibility(View.GONE);
		mGeocodeResults.setText("");
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
		((OnSearchListener) getActivity()).onSearch(mQueryEditText.getText().toString(), mAddress);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		int id = v.getId();
		
		// get the input box vales
		String queryText = mQueryEditText.getText().toString();
		String locationText = mLocationEditText.getText().toString();
		
		// if any are emtpy, ERROR
		if(TextUtils.isEmpty(locationText)){
			Toast t = Toast.makeText(getActivity(), "Both query and location need to be filled", 5000);
			t.show();
			return;
		}
		if(TextUtils.isEmpty(queryText))
			queryText = "*";
		
		mProgressBar.setVisibility(View.VISIBLE);
//		// first geocode the addrress
		if(!mMyLocationChecked){
			GeocodeAsyncTask task = new  GeocodeAsyncTask(mGeocoder);
			task.execute(locationText);
		}
		else{
			mGeocodeResults.setText(mAddress.getAddressLine(0));
			((OnSearchListener) getActivity()).onSearch(mQueryEditText.getText().toString(), mAddress);
		}
//		
//		if(id == R.id.location_button){
//			// create and start the geocoding task
//			
//			
//		}
//		else{
//			
//		}
	}
	
	/**
	 * Parent activity should implement this to get a call
	 * when user hits search
	 * @author 	Sergio Bernales
	 * @date 	Apr 27, 2012
	 *
	 * Copyright 2012 Locomoti LLC. All rights reserved.
	 *
	 */
	public interface OnSearchListener{
		public void onSearch(String query, Address address);
	}


	/* (non-Javadoc)
	 * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		mMyLocationChecked = isChecked;
		if(isChecked){
			mLocationEditText.setText("My Location");
			mLocationEditText.setEnabled(false);
			LocationManager lm = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
			List<String> providers = lm.getProviders(true);

			Location l = null;
	        
	        for (int i=providers.size()-1; i>=0; i--) {
	                l = lm.getLastKnownLocation(providers.get(i));
	                if (l != null) break;
	        }
	        
	         mAddress = new Address(Locale.getDefault());
	         mAddress.setLongitude(l.getLongitude());
	         mAddress.setLatitude(l.getLatitude());
		}
		else {
			mLocationEditText.setEnabled(true);
			mLocationEditText.setText("");
			
		}
			
		
	}
}
