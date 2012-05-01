/***
 * Copyright (c) 2010 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.vt.ir.ui;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.readystatesoftware.maps.OnSingleTapListener;
import com.readystatesoftware.maps.TapControlledMapView;
import com.vt.ir.R;
import com.vt.ir.Utils;
import com.vt.ir.io.ResponseProcessor;
import com.vt.ir.services.ServiceHelper;
import com.vt.ir.tasks.MapPointsAsyncTask;
import com.vt.ir.tasks.MapPointsAsyncTask.OnMapPointsCreatedListener;
import com.vt.ir.vo.Crime;

public class TapControlledMap extends MapActivity implements OnMapPointsCreatedListener{

	public static final String EXTRA_QUERY = "extra_query";
	public static final String EXTRA_ADDRESS = "extra_address";
	
	
	String mQuery;
	Address mAddress;
	String mJsonResult;
	MapPointsAsyncTask mMapPointsAsyncTask;
	Crime[] mCrimes;
	
	TapControlledMapView mapView; // use the custom TapControlledMapView
	List<Overlay> mMapOverlays;
	Drawable mDrawable;
	Drawable mDrawable2;
	SimpleItemizedOverlay mDBItemizedOverlay;
	SimpleItemizedOverlay mUserItemizedOverlay;
	ServiceHelper mRestServieHelper;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);

        // setup the map view
        mapView = (TapControlledMapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		// dismiss balloon upon single tap of MapView (iOS behavior) 
		mapView.setOnSingleTapListener(new OnSingleTapListener() {		
			@Override
			public boolean onSingleTap(MotionEvent e) {
				mDBItemizedOverlay.hideAllBalloons();
				return true;
			}
		});
		
		// get references to map objects
		mMapOverlays = mapView.getOverlays();
		mDrawable = getResources().getDrawable(R.drawable.marker);
		mDrawable2 = getResources().getDrawable(R.drawable.marker2);
		
		// create the itemized overlay 1
		mDBItemizedOverlay = new SimpleItemizedOverlay(mDrawable, mapView);
		// set iOS behavior attributes for overlay
		mDBItemizedOverlay.setShowClose(false);
		mDBItemizedOverlay.setShowDisclosure(true);
		mDBItemizedOverlay.setSnapToCenter(false);
		
		// create itemized overlay 2
		mUserItemizedOverlay = new SimpleItemizedOverlay(mDrawable2, mapView);
		
		
        // get the query extras
        Bundle extras = getIntent().getExtras();
        mJsonResult = extras.getString(ResponseProcessor.EXTRA_JSON_STRING);
        mAddress = extras.getParcelable(EXTRA_ADDRESS);
        
        // 	create the geo points that will be drawn on our map
		mMapPointsAsyncTask = new MapPointsAsyncTask(mJsonResult, this);
		mMapPointsAsyncTask.execute(mDBItemizedOverlay, mUserItemizedOverlay);
    }
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		// example saving focused state of overlays
		if (mDBItemizedOverlay.getFocus() != null) outState.putInt("focused_1", mDBItemizedOverlay.getLastFocusedIndex());
		if (mUserItemizedOverlay.getFocus() != null) outState.putInt("focused_2", mUserItemizedOverlay.getLastFocusedIndex());
		super.onSaveInstanceState(outState);
	
	}

	/* (non-Javadoc)
	 * @see com.vt.ir.tasks.MapPointsAsyncTask.OnMapPointsCreatedListener#OnMapPointsCreated(com.vt.ir.ui.SimpleItemizedOverlay)
	 */
	@Override
	public void OnMapPointsCreated(Crime[] crimes) {
		
		if(crimes == null)
			return;
		
		// keep reference to the crimes as we will need it when user taps on them
		mCrimes = crimes;
		
		// add the aadded overlay to the map
		mMapOverlays.add(mDBItemizedOverlay);
		mMapOverlays.add(mUserItemizedOverlay);
//		
//		// second overlay
//
//		// set iOS behavior attributes for overlay
//		mItemizedOverlay2.setShowClose(false);
//		mItemizedOverlay2.setShowDisclosure(true);
//		mItemizedOverlay2.setSnapToCenter(false);
//		
//		GeoPoint point3 = new GeoPoint((int)(51.513329*1E6),(int)(-0.08896*1E6));
//		OverlayItem overlayItem3 = new OverlayItem(point3, "Sliding Doors (1998)", null);
//		mItemizedOverlay2.addOverlay(overlayItem3);
//		
//		GeoPoint point4 = new GeoPoint((int)(51.51738*1E6),(int)(-0.08186*1E6));
//		OverlayItem overlayItem4 = new OverlayItem(point4, "Mission: Impossible (1996)", 
//				"(Ethan & Jim cafe meeting)");
//		mItemizedOverlay2.addOverlay(overlayItem4);
//		
//		mMapOverlays.add(mItemizedOverlay2);
		
//		if (savedInstanceState == null) {
//			
		
		// center our map approriatley
	
		final MapController mc = mapView.getController();
		
		int[] centerCoords = Utils.getCoordinates(mAddress);
		GeoPoint p = new GeoPoint(centerCoords[Utils.LON_IN_ARRAY], centerCoords[Utils.LAT_IN_ARRAY]);
		mc.animateTo(p);
		mc.setZoom(15);// zoome level tweak
			
//		} else {
//			
//			// example restoring focused state of overlays
//			int focused;
//			focused = savedInstanceState.getInt("focused_1", -1);
//			if (focused >= 0) {
//				itemizedOverlay.setFocus(itemizedOverlay.getItem(focused));
//			}
//			focused = savedInstanceState.getInt("focused_2", -1);
//			if (focused >= 0) {
//				itemizedOverlay2.setFocus(itemizedOverlay2.getItem(focused));
//			}
//			
//		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
//		finish();
	}
}
