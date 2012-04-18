/**
 * 
 */
package com.vt.ir.providers;

import java.util.Arrays;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.vt.ir.providers.RoshamboContract.Crime;
import com.vt.ir.providers.RoshamboDatabase.Tables;
import com.vt.ir.services.ServiceHelper;

public class RoshamboProvider extends ContentProvider {
	private static final String TAG = "CrimeProvider";
	
	/** Reference to our database */
	private RoshamboDatabase mOpenHelper;
	
	/** Reference to our Service Helper(intent creator for our IntentService */
	private ServiceHelper mServiceHelper;
	
	
	/** Our URI types*/
	private static final int CRIME = 100; // query, insert
		
	/** shared preference file */
	private static final String PROVIDER_PREFERENCES = "prefs";
	
	/**
	 * Build our URImatcher that catches
	 * all URI variations supported by this content provider.
	 * Basically we map paths to a type numbers so that in
	 * getType URI we can do a switch/case to return a type instead
	 * of if/else's.
	 */
	private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		final String authority = RoshamboContract.CONTENT_AUTHORITY;
		
		mUriMatcher.addURI(authority, "crime", CRIME);
	}
	
	/* (non-Javadoc)
	 * @see android.content.ContentProvider#getType(android.net.Uri)
	 */
	@Override
	public String getType(Uri uri) {
		final int match = mUriMatcher.match(uri);
		switch(match) {
			case CRIME:
				return Crime.CONTENT_TYPE;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
		final Context context = getContext();
		
		mServiceHelper = new ServiceHelper();
		
		mOpenHelper = new RoshamboDatabase(context);
		if(mOpenHelper == null)
			return false;
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.content.ContentProvider#delete(android.net.Uri, java.lang.String, java.lang.String[])
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}



	/* (non-Javadoc)
	 * @see android.content.ContentProvider#insert(android.net.Uri, android.content.ContentValues)
	 */
	@Override
	public Uri insert(Uri uri, ContentValues newGameValues) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = mUriMatcher.match(uri);
		
		switch (match){
			case CRIME: {
				
				return null;
			}
		
			default: {
				throw new UnsupportedOperationException("Unknown uri: " + uri);
			}
		}
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.d(TAG, "query(uri=" + uri + ", proj=" + Arrays.toString(projection) + ")");
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		
		final int match = mUriMatcher.match(uri);
		switch (match){
			case CRIME: {
//				Cursor c = null;
//				try{
//					c = db.query(Tables.USER, projection, selection, null, null, null, null);
//				}
//				catch(SQLException e ){
//					e.getMessage();
//					e.printStackTrace();
//				}
				return null;
			}
					default: {
				throw new UnsupportedOperationException("Unknown uri: " + uri);
			}
				
		}
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[])
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		Log.d(TAG, "update(uri=" + uri + ", values=" + values.toString() + ")");
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		
		final int match = mUriMatcher.match(uri);
		switch (match){
				
			default:{
				return -1;
			}
				
		}
	}
}
