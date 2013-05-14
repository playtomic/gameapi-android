package com.playtomic.android;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

public class Leaderboards {

    private static String SECTION = "leaderboards";
    private static String SAVEANDLIST = "saveandlist";
    private static String SAVE = "save";
    private static String LIST = "list";
    
    public static void save(PlayerScore score, final LeaderboardSaveHandler handler) {
        PRequest.load(SECTION, SAVE, score, new PResponseHandler() {
        	
        	@Override
        	public void onResponse(PResponse response, JSONObject data) {
        		if(response.getSuccess()) {
        			handler.onSuccess(response);
        		} else {
        			handler.onFailure(response);
        		}
        	}
        	
        });
    }

    public static void list(JSONObject options, final LeaderboardListHandler handler) {
    	PRequest.load(SECTION, LIST, options, new PResponseHandler() {
        	
        	@Override
        	public void onResponse(PResponse response, JSONObject data) {
        		if(response.getSuccess()) {
        			ArrayList<PlayerScore> scores = processScores(data);
        			int numscores = data.optInt("numscores");
        			handler.onSuccess(scores, numscores, response);
        		} else {
        			handler.onFailure(response);
        		}
        	}
        	
    	});
    }
    
    public static void saveAndList(JSONObject options, final LeaderboardListHandler handler) {
    	PRequest.load(SECTION, SAVEANDLIST, options, new PResponseHandler() {
        	
        	@Override
        	public void onResponse(PResponse response, JSONObject data) {
        		if(response.getSuccess()) {
        			ArrayList<PlayerScore> scores = processScores(data);
        			int numscores = data.optInt("numscores");
        			handler.onSuccess(scores, numscores, response);
        		} else {
        			handler.onFailure(response);
        		}
        	}
        	
    	});
    }
    
    private static ArrayList<PlayerScore> processScores(JSONObject data) {
    	
    	JSONArray scoresraw = data.optJSONArray("scores");
    	ArrayList<PlayerScore> scores = new ArrayList<PlayerScore>();
    	
    	for(int i=0; i<scoresraw.length(); i++) {
    		JSONObject scoredata = (JSONObject) scoresraw.opt(i);
    		PlayerScore score = new PlayerScore((JSONObject) scoredata);
    		scores.add(score);
    	}
    	
    	return scores;
    }
}
