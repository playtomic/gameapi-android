package com.playtomic.android;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;

public class Achievements {

    private static String SECTION = "achievements";
    private static String LIST = "list";
    private static String STREAM = "stream";
    private static String SAVE = "save";

    /**
     * Loads all achievements
     * @parma options   ListOptions or JSONObject of options
     * @param callback	AchievementListHandler for receiving the response and data
     */
    public static void list(ListOptions options, final AchievementListHandler callback) {
        PRequest.load(SECTION, LIST, options, new PResponseHandler() {
            @Override
            public void onResponse(PResponse response, JSONObject data) {
            if(response.getSuccess())
            {
                ArrayList<PlayerAchievement> achievements = new ArrayList<PlayerAchievement>();
                JSONArray acharray = data.optJSONArray("achievements");

                for(int i=0; i<acharray.length(); i++) {
                    JSONObject achjson = null;
                    try {
                        achjson = (JSONObject) acharray.get(i);
                    } catch (JSONException e) {
                        continue;
                    }
                    achievements.add(new PlayerAchievement(achjson));
                }

                callback.onSuccess(achievements, response);
            }
            else
            {
                callback.onFailure(response);
            }
            }
        });
    }

    /**
     * Loads a stream of achievements by players
     * @parma options   ListOptions or JSONObject of options
     * @param callback	AchievementStreamHandler for receiving the response and data
     */
    public static void stream(ListOptions options, final AchievementStreamHandler callback) {
        PRequest.load(SECTION, STREAM, options, new PResponseHandler() {
            @Override
            public void onResponse(PResponse response, JSONObject data) {
                if(response.getSuccess())
                {
                    ArrayList<PlayerAward> achievements = new ArrayList<PlayerAward>();
                    JSONArray acharray = data.optJSONArray("achievements");
                    int numachievements = data.optInt("numachievements");

                    for(int i=0; i<acharray.length(); i++) {
                        JSONObject achjson = null;
                        try {
                            achjson = (JSONObject) acharray.get(i);
                        } catch (JSONException e) {
                            continue;
                        }
                        achievements.add(new PlayerAward(achjson));
                    }

                    callback.onSuccess(achievements, numachievements, response);
                }
                else
                {
                    callback.onFailure(response);
                }
            }
        });
    }

    /**
     * Saves the level
     * @param achievement   The PlayerAchievement to save
     * @param callback	AchievementSaveHandler for receiving the response and data
     */
    public static void save(PlayerAchievement achievement, final AchievementSaveHandler callback) {

        PRequest.load(SECTION, SAVE, achievement, new PResponseHandler() {
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
}
