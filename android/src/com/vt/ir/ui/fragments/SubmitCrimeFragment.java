/**
 * 
 */
package com.vt.ir.ui.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.vt.ir.R;
import com.vt.ir.ui.fragments.SearchFragment.OnSearchListener;
import com.vt.ir.vo.Crime;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Fragment to control the SUbmit A crime portion of the app
 * 
 * @author 	Sergio Bernales
 * @date 	Apr 22, 2012
 *
 * Copyright 2012 Locomoti LLC. All rights reserved.
 *
 */
public class SubmitCrimeFragment extends Fragment implements OnCheckedChangeListener, OnClickListener {

	
	Spinner mCategorySpinner;
	EditText mLocationEditText;
	private CheckBox mCheckBox;
	private boolean mMyLocationChecked;
	DatePicker mDatePicker;
	EditText mDescription;
	Address mAddress;
	Button mSubmitButton;
	private Geocoder mGeocoder;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mGeocoder = new Geocoder(activity, Locale.getDefault());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_submit_crime, container, false);
		
		mCategorySpinner = (Spinner) v.findViewById(R.id.spinner_category);
		addItemsOnSpinner();
		mLocationEditText = (EditText) v.findViewById(R.id.location_text);
		mCheckBox = (CheckBox) v.findViewById(R.id.my_location_checkbox);
		mCheckBox.setOnCheckedChangeListener(this);
		mDatePicker = (DatePicker) v.findViewById(R.id.crime_date);
		mDescription = (EditText) v.findViewById(R.id.crime_description);
		mSubmitButton = (Button) v.findViewById(R.id.submit_button);
		mSubmitButton.setOnClickListener(this);
		
		return v;
	}

	/**
	 * 
	 */
	private void addItemsOnSpinner() {
		List<String> list = new ArrayList<String>();
		list.add("Theft");
		list.add("Larceny");
		list.add("Vandalism");
		list.add("Trespassing");
		list.add("Domestic Dispute");
		list.add("Murder");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCategorySpinner.setAdapter(dataAdapter);
		
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
	         mAddress.setLongitude(l.getLatitude());
		}
		else {
			mLocationEditText.setEnabled(true);
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if(!mMyLocationChecked){
			GeocodeAsyncTask task = new  GeocodeAsyncTask(mGeocoder);
			task.execute(mLocationEditText.getText().toString());
		}
		else{
			Crime newCrime = createNewCrime();
			((OnCrimeSubmitListener) getActivity()).onCrimeSubmit(newCrime);
		}
		
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
	       SubmitCrimeFragment.this.onGeocodeComplete(result);
	    }
	}
	
	/**
	 * After geocode has been completed
	 * @param result 
	 */
	public void onGeocodeComplete(Address result) {
		mAddress = result;
		
		Crime newCrime = createNewCrime();
		((OnCrimeSubmitListener) getActivity()).onCrimeSubmit(newCrime);
	}
	
	/**
	 * @return
	 */
	private Crime createNewCrime() {
		Crime newCrime = new Crime();
		newCrime.setCrimeDate("2009-01-09T23:59:59Z");
		newCrime.setId(Long.toString(System.currentTimeMillis()));
		newCrime.setCatagory((String) mCategorySpinner.getSelectedItem());
		newCrime.setBussiness(mAddress.getAddressLine(0));
		newCrime.setCounty(mAddress.getLocality());
		newCrime.setCatagorie("ALL OTHER OFFENSES");
		newCrime.setCrimegroup("B");
		newCrime.setDescription(mDescription.getText().toString());
		newCrime.setLocation(mAddress.getAddressLine(0));
		newCrime.setCity(mAddress.getSubLocality());
		newCrime.setState(mAddress.getLocality());
		newCrime.setZipcode(mAddress.getPostalCode());
		newCrime.setService("mycrime");
		newCrime.setAccuracy("0");
		
		StringBuilder s = new StringBuilder();
		s.append(mAddress.getLatitude()).append(",").append(mAddress.getLongitude());
		newCrime.setCoordinates_latlong(s.toString());
		
		return newCrime;
	}



	public interface OnCrimeSubmitListener{
		public void onCrimeSubmit(Crime crime);
	}
}
