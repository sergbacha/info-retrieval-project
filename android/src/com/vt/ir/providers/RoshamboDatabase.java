/**
 * 
 */
package com.vt.ir.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.vt.ir.providers.RoshamboContract.CrimeColumns;

/**
 * Will manage our database of Games
 * 
 * @author 	Sergio Bernales
 * @date 	Mar 14, 2012
 *
 * Copyright 2012 Locomoti LLC. All rights reserved.
 *
 */
public class RoshamboDatabase extends SQLiteOpenHelper {
	private static final String TAG = "CrimeDatabase";
	
	//TODO: create constants ME, YES, NO in integer format
	
	
	/** Database info the instantiate it */
	private static final String DATABASE_NAME = "crime.db";
	private static final int VER_SESSION_HASHTAG = 1;
	private static final int DATABASE_VERSION = VER_SESSION_HASHTAG;
	
	/** Tables that we will use that relate to challenges */
	interface Tables {
		String CRIME = "crime";
	}
	
	/** Table to Table references */
    private interface References {
//        String GAME_ID = "REFERENCES " + Tables.GAME + "(" + Game._ID + ")";
    }
	
	/**
	 * Constructor calling the parent constructor.
	 * 
	 * @param context
	 */
	public RoshamboDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	/* *
	 * Create the databases
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.CRIME + " ("
                + BaseColumns._ID 				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CrimeColumns.CRIME_LAT		+ " TEXT," //TODO: Make reference to Game above
                + CrimeColumns.CRIME_LONG 		+ " TEXT,"
                + CrimeColumns.CRIME_KEYWORD	+ " TEXT,"
                + RESTColumns.REST_STATE		+ " TEXT,"
                + RESTColumns.REST_RESULT		+ " TEXT,"
                + RESTColumns.UPDATED 			+ " LONG,"
                + RESTColumns.SYNCED 			+ " LONG)");
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("drop table " + Tables.USER);
//		db.execSQL("drop table " + Tables.GAME);
//		db.execSQL("drop table " + Tables.MATCH);

	}
}
