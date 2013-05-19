package com.playtomic.android;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

public class Leaderboards {

    private static String SECTION = "leaderboards";
    private static String SAVEANDLIST = "saveandlist";
    private static String SAVE = "save";
    private static String LIST = "list";
    
    /**
     * Saves your score
     * @param callback	LeaderboardSaveHandler for receiving the response and data
     */ 
    public static void save(PlayerScore score, final LeaderboardSaveHandler callback) {
        PRequest.load(SECTION, SAVE, score, new PResponseHandler() {
        	
        	@Override
        	public void onResponse(PResponse response, JSONObject data) {
            if(response.getSuccess()) {
                callback.onSuccess(response);
            } else {
                callback.onFailure(response);
            }
        	}
        	
        });
    }

    /**
     * Fetches a high score table
     * @param options	ListOptions of parameters
     * @param callback	LeaderboardListHandler for receiving the response and data
     */ 
    public static void list(ListOptions options, final LeaderboardListHandler callback) {
    	PRequest.load(SECTION, LIST, options, new PResponseHandler() {
        	
        	@Override
        	public void onResponse(PResponse response, JSONObject data) {
                if(response.getSuccess()) {
                    ArrayList<PlayerScore> scores = processScores(data);
                    int numscores = data.optInt("numscores");
                    callback.onSuccess(scores, numscores, response);
                } else {
                    callback.onFailure(response);
                }
            }
        	
    	});
    }
    
    /**
     * Saves a score and fetches the high score table with the page corresponding
     * to the submitted score
     * @param score		PlayerScore with score and table data
     * @param callback	LeaderboardListHandler for receiving the response and data
     */ 
    public static void saveAndList(PlayerScore score, final LeaderboardListHandler callback) {
    	PRequest.load(SECTION, SAVEANDLIST, score, new PResponseHandler() {
        	
        	@Override
        	public void onResponse(PResponse response, JSONObject data) {
        		if(response.getSuccess()) {
        			ArrayList<PlayerScore> scores = processScores(data);
        			int numscores = data.optInt("numscores");
        			callback.onSuccess(scores, numscores, response);
        		} else {
        			callback.onFailure(response);
        		}
           	}
    	});
    }
    
    private static ArrayList<PlayerScore> processScores(JSONObject data) {
    	
    	JSONArray scoresraw = data.optJSONArray("scores");
    	ArrayList<PlayerScore> scores = new ArrayList<PlayerScore>();
    	
    	for(int i=0; i<scoresraw.length(); i++) {
    		JSONObject scoredata = (JSONObject) scoresraw.opt(i);
    		PlayerScore score = new PlayerScore(scoredata);
    		scores.add(score);
    	}
    	
    	return scores;
    }
}
