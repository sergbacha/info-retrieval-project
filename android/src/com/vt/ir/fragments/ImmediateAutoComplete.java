/**
 * 
 */
package com.vt.ir.fragments;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class ImmediateAutoComplete extends AutoCompleteTextView {

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ImmediateAutoComplete(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ImmediateAutoComplete(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		
	}
	
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ImmediateAutoComplete(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Always return true!
	 */
	@Override
	public boolean enoughToFilter() {
		// TODO Auto-generated method stub
		return true;
	}
}
