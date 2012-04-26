package com.vt.ir.adapters;

import com.vt.ir.ui.fragments.SearchFragment;
import com.vt.ir.ui.fragments.SubmitCrimeFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

	SearchFragment mSearchFragment;
	SubmitCrimeFragment mSubmitCrimeFragment;
	
	static private final int ITEM_SEARCH_FRAGMENT = 0;
	static private final int ITEM_SUBMIT_CRIME_FRAGMENT = 1;
	
	/**
	 * @param fm
	 */
	public HomeFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		
		mSearchFragment = new SearchFragment();
		mSubmitCrimeFragment = new SubmitCrimeFragment();
	}

	@Override
	public Fragment getItem(int item) {
		switch(item){
			case ITEM_SEARCH_FRAGMENT:
				return mSearchFragment;
			case ITEM_SUBMIT_CRIME_FRAGMENT:
				return mSubmitCrimeFragment;
			default:
				return null;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
}
