/**
 * 
 */
package com.vt.ir.fragments;

import com.vt.ir.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment to control the SUbmit A crime portion of the app
 * 
 * @author 	Sergio Bernales
 * @date 	Apr 22, 2012
 *
 * Copyright 2012 Locomoti LLC. All rights reserved.
 *
 */
public class SubmitCrimeFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_submit_crime, container, false);
		return v;
	}
}
