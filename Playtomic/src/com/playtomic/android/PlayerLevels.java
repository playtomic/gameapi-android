package com.playtomic.android;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayerLevels {
    
    private static String SECTION = "playerlevels";
    private static String SAVE = "save";
    private static String LIST = "list";
    private static String LOAD = "load";
    private static String RATE = "rate";
    
    public static void load(String levelid, final PlayerLevelSaveLoadHandler callback) {

    	JSONObject postdata = new JSONObject();
    	
    	try {
    		postdata.put("levelid", levelid);
    	}catch(JSONException e) {
    		callback.onFailure(new PResponse(false, 1));
    		return;
    	}
    	
    	PRequest.load(SECTION, LOAD, postdata, new PResponseHandler() {
			@Override
			public void onResponse(PResponse response, JSONObject data) {		
				if(response.getSuccess())
				{
					JSONObject leveldata = data.optJSONObject("level");		
		    		PlayerLevel level = new PlayerLevel(leveldata);
					callback.onSuccess(level, response);
				}
				else
				{
					callback.onFailure(response);
				}
			}
    	});
    }
    
    public static void save(PlayerLevel level, final PlayerLevelSaveLoadHandler callback) {

    	PRequest.load(SECTION, SAVE, level, new PResponseHandler() {
			@Override
			public void onResponse(PResponse response, JSONObject data) {		
				if(response.getSuccess())
				{
					JSONObject leveldata = data.optJSONObject("level");		
		    		PlayerLevel nlevel = new PlayerLevel(leveldata);
					callback.onSuccess(nlevel, response);
				}
				else
				{
					callback.onFailure(response);
				}
			}
    	});
    }
    
    public static void rate(String levelid, int rating, final PlayerLevelRateHandler callback) {

    	if(rating < 1 || rating > 10) {
    		callback.onFailure(new PResponse(false, 401));
    		return;
    	}
    	
    	JSONObject postdata = new JSONObject();
    	
    	try {
    		postdata.put("levelid", levelid);
    		postdata.put("rating", rating);
    	}catch(JSONException e) {
    		callback.onFailure(new PResponse(false, 1));
    		return;
    	}
    	
    	PRequest.load(SECTION, RATE, postdata, new PResponseHandler() {
			@Override
			public void onResponse(PResponse response, JSONObject data) {		
				if(response.getSuccess())
				{
					callback.onSuccess(response);
				}
				else
				{
					callback.onFailure(response);
				}
			}
    	});	
    }

    public static void list(JSONObject options, final PlayerLevelListHandler callback) {
    	PRequest.load(SECTION, LIST, options, new PResponseHandler() {
			@Override
			public void onResponse(PResponse response, JSONObject data) {		
				if(response.getSuccess())
				{
			    	JSONArray levelsraw = data.optJSONArray("levels");
			    	ArrayList<PlayerLevel> levels = new ArrayList<PlayerLevel>();
			    	int numlevels = data.optInt("numlevels");
			    	
			    	for(int i=0; i<levelsraw.length(); i++) {
			    		Object leveldata = levelsraw.opt(i);    		
			    		PlayerLevel level = new PlayerLevel((JSONObject) leveldata);
			    		levels.add(level);
			    	}

					callback.onSuccess(levels, numlevels, response);
				}
				else
				{
					callback.onFailure(response);
				}
			}
    	});
    }
}
