package com.playtomic.android;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class PlayerAchievement extends JSONObject {

    public PlayerAchievement() {

    }

    public PlayerAchievement(JSONObject data) {

        if(data == null) {
            return;
        }

        Iterator<?> keys = data.keys();

        while (keys.hasNext()) {
            String key = keys.next().toString();
            setValue(key, data.opt(key));
        }
    }

    public PlayerAward getPlayer()
    {
        if(!has("player")) {
            return null;
        }

        return new PlayerAward((JSONObject) opt("player"));
    }

    public ArrayList<PlayerAward> getFriends()
    {
        if(!has("friends")) {
            return null;
        }

        ArrayList<PlayerAward> friends = new ArrayList<PlayerAward>();
        JSONArray friendsraw = optJSONArray("friends");

        if(friendsraw == null) {
            return null;
        }

        for(int i = 0; i<friendsraw.length(); i++) {
            JSONObject friendobj = (JSONObject)friendsraw.opt(i);
            PlayerAward award = new PlayerAward(friendobj);
            friends.add(award);
        }

        return friends;
    }

    public void setPlayerId(String playerid) {
        setValue("playerid", playerid);
    }

    public void setPlayerName(String name) {
        setValue("playername", name);
    }

    public String getAchievement() {
        return optString("achievement", "");
    }

    public void setAchievement(String name) {
        setValue("achievement", name);
    }

    public void setAchievementKey(String name) {
        setValue("achievementkey", name);
    }

    public void setSource(String source) {
        setValue("source", source);
    }

    public void setAllowDuplicates() {
        setValue("allowduplicates", true);
    }

    public void setOverwrite() {
        setValue("overwrite", true);
    }

    public JSONObject fields() {

        if(!has("fields")) {
            setValue("fields", new JSONObject());
        }

        return optJSONObject("fields");
    }

    public void setField(String name, Object value) {
        if(!has("fields")) {
            setValue("fields", new JSONObject());
        }

        try {
            fields().put(name, value);
        } catch(JSONException err) {

        }
    }

    public String getRDate() {
        return optString("rdate", "Just now");
    }

    public Date getDate() {
        SimpleDateFormat dateparser = new SimpleDateFormat("yyyy/mm/dd", Locale.US);
        String datestring = optString("date", dateparser.format(new Date()));
        try {
            return dateparser.parse(datestring);
        } catch (ParseException e) {
            return new Date();
        }
    }

    private void setValue(String key, Object value)
    {
        try {
            put(key, value);
        } catch(JSONException err) {

        }
    }
}
