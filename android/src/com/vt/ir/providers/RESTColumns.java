/**
 * 
 */
package com.vt.ir.providers;


public interface RESTColumns {

	/**
	 * Stores the state of a given table record in the xontext of
	 * a RESTful call. For example, if a table record is being updated
	 * in the server and a REST call has been sent to the server, this state
	 * would be STATE_PUTTING. WHen the rest call returns, this state will be cleared
	 * 
	 */
	String REST_STATE = "table_rest_state";
	
	/**
	 * Store the result of the last REST call.
	 * Taking the example from above. Once the PUT call returns, if it was successfull
	 * the Success HTTP code (200) will be stored in this column
	 */
	String REST_RESULT = "rest_result";
	
	/** Last time this entry was updated by/in android */
    String UPDATED = "updated";
    
    /** Last time this entry synced with server (HTTP success response) */
    String SYNCED = "synced";
    
}
