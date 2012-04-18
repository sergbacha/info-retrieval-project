/**
 * 
 */
package com.vt.ir.providers;

import java.lang.ref.WeakReference;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Resolves database calls in new thread. The call comes
 * always from the UI thread and this will let it keep
 * doing UI things.
 * 
 * @author 	Sergio Bernales
 * @date 	Mar 15, 2012
 *
 * Copyright 2012 Locomoti LLC. All rights reserved.
 *
 */

//TODO: BIG todo: modify parent AsynQueryHandler to receive instead of ContentValues, our VO objects


public class CrimeAsyncQueryHandler extends AsyncQueryHandler {
	
	/** Reference to object we will callback. Usually the activity
	 * that started the async method call.*/
	private WeakReference<AsyncQueryListener> mListener;
	
	
	/**
     * Interface to listen for completed query operations.
     */
    public interface AsyncQueryListener {
        void onQueryComplete(int token, Object cookie, Cursor cursor);
        void onInsertComplete(int token, Object cookie, Uri uri);
        void onUpdateComplete(int token, Object cookie, int result);
    }
	
	/**
	 * Constructor setting the callback after the query has completed to l
	 * 
	 * @param l Usually the activity
	 * @param cr
	 */
	public CrimeAsyncQueryHandler(ContentResolver cr, AsyncQueryListener l) {
		super(cr);
		setQueryListener(l);
		
	}

	/**
     * Assign the given  AsyncQueryListener to receive query events from
     * asynchronous calls. Will replace any existing listener. 'Tis
     * usuallt an activity
     */
    public void setQueryListener(AsyncQueryListener listener) {
        mListener = new WeakReference<AsyncQueryListener>(listener);
    }
    
    
    /**
     * Clear any AsyncQueryListener set through
     * setQueryListener(AsyncQueryListener)
     */
    public void clearQueryListener() {
        mListener = null;
    }  
    
    
    /** Once the query is complete, call our listener callback */
    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        final AsyncQueryListener listener = mListener == null ? null : mListener.get();
        if (listener != null) {
            listener.onQueryComplete(token, cookie, cursor);
        } else if (cursor != null) {
            cursor.close();
        }
    }
    
    /** Once the insert is complete, call our listener callback */
    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        final AsyncQueryListener listener = mListener == null ? null : mListener.get();
        if (listener != null) {
            listener.onInsertComplete(token, cookie, uri);
        }
    }
    
    /* (non-Javadoc)
     * @see android.content.AsyncQueryHandler#onUpdateComplete(int, java.lang.Object, int)
     */
    @Override
    protected void onUpdateComplete(int token, Object cookie, int result) {
    	// TODO definitley remove this method
    	mListener.get().onUpdateComplete(token, cookie, result);
    }
}
