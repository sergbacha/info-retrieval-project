/**
 * 
 */
package com.vt.ir.io;

import org.codehaus.jackson.JsonNode;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.util.Log;


public class ResponseProcessor {
	private static final String TAG = "ResponseProcessor";
	
	ContentResolver mResolver;
	
	
	public ResponseProcessor(ContentResolver resolver){
		mResolver = resolver;
	}
	
	/**
	 * Processes the json results of a search query
	 * @param searchResults
	 */
	public void processSearchResults(JsonNode searchResults){
		Log.d(TAG, searchResults.toString());
	}

//
//	/**
//	 * Process the new user response that was passed from the server
//	 * This should include most important an _id generated in mongoose
//	 * 
//	 * @param newUserNode
//	 */
//	public void processNewUserResponse(JsonNode newUserNode) {
//		//TODO: what if there is another user that was created? ERORR!
//		
//		Log.d(TAG, "Processor is doing its thing");
//		
//		// update the record in the db TODO: add synces/created timestamps to thiss
//		mResolver.update(User.CONTENT_URI, UserVO.toContentValues(newUserNode), User.DEFAULT_WHERE, null);
//		
//	}
//
//
//	/**
//	 * Process the server response to the resquest of creating a new game/challenge.
//	 * This means updating the android db with the server id's
//	 */
//	public void processNewGameResponse(JsonNode newGameNode) {
//		
//		// get the game content values to save to our db
//		ContentValues gameValues = NewGameVO.toContentValues(newGameNode);
//		
//		// update only the corresponding record  (we use the androidID we sent to node in teh request)
//		String androidID = NewGameVO.getAndroidID(newGameNode);
//		
//		// save to the our db
//		mResolver.update(Game.buildNewGameURI(), gameValues, Game.WHERE_SPECIFIC_GAME, new String[] {androidID});
//	}
//
//
//	/**
//	 * We should receive the same same object we went if everything went fine
//	 * if not, then the
//	 * 
//	 * @param submitMatchNode
//	 */
//	public void processMatchMoveResponse(JsonNode gameNode) {
//		Log.d(TAG, "we have received a submit resposne");
//		
//		// get the game id
//		String gameNodeID = GameVO.getGameNodeID(gameNode);
//		
//		// create value object to hold the game node id
//		ContentValues values = new ContentValues(1);
//		values.put(Game.GAME_NODE_ID, gameNodeID);
//		
//		// save the results
//		mResolver.update(Matches.buildMatchMoveResponse(), values, null, null);
//	}
}
