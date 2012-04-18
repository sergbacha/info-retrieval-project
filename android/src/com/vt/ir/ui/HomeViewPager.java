/**
 * 
 */
package com.vt.ir.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class HomeViewPager extends ViewPager {

	Context mContext;

	public HomeViewPager(Context context, AttributeSet attr) {
		super(context, attr);
		
		mContext = context; 

	}
}
