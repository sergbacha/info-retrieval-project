package com.vt.ir.ui;


import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.vt.ir.R;
import com.vt.ir.adapters.HomeFragmentPagerAdapter;
import com.vt.ir.services.ServiceHelper;
import com.vt.ir.ui.fragments.SearchFragment.OnSearchListener;

/**
 * Entry activity for the crime search app
 * 
 * @author 	Sergio Bernales
 * @date 	Apr 22, 2012
 *
 * Copyright 2012 Locomoti LLC. All rights reserved.
 *
 */
public class CrimeHomeActivity extends FragmentActivity implements OnSearchListener{
	public static final String TAG = "HomeActivity";

	ViewPager mViewPager;
	HomeFragmentPagerAdapter mHomePagerAdapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
				super.onCreate(arg0);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_home);
		
		// get view pager for homme layout
		mViewPager = (ViewPager) findViewById(R.id.home_view_pager);
		
		// put in fragment pager adapter
		mHomePagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mHomePagerAdapter);

	}

	/* (non-Javadoc)
	 * @see com.vt.ir.ui.fragments.SearchFragment.OnSearchListener#onSearch()
	 */
	@Override
	public void onSearch(String query, Address address) {
		
		// put extra for search activity
		Intent i = new Intent(this, TapControlledMap.class);
		
		Bundle extras = new Bundle();
		extras.putString(TapControlledMap.EXTRA_QUERY, query);
		extras.putParcelable(TapControlledMap.EXTRA_ADDRESS, address);
		
		i.putExtras(extras);
		
		startActivity(i);
	}
}

	