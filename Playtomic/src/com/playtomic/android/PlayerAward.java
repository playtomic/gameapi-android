package com.playtomic.android;

import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
import org.json.JSONException;

public class PlayerAward extends JSONObject {

    public PlayerAward() {

    }

    public PlayerAward(JSONObject data) {

        if(data == null) {
            return;
        }

        Iterator<?> keys = data.keys();

        while (keys.hasNext()) {
            String key = keys.next().toString();
            setValue(key, data.opt(key));
        }
    }

    public String getPlayerId() {
        return optString("playerid", "");
    }

    public void setPlayerId(String playerid) {
        setValue("playerid", playerid);
    }

    public String getPlayerName() {
        return optString("playername", "");
    }

    public void setPlayerName(String name) {
        setValue("playername", name);
    }

    public String getSource() {
        return optString("source", "");
    }

    public void setSource(String source) {
        setValue("source", source);
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

    public PlayerAchievement getAwarded() {
        if(!has("awarded")) {
            return null;
        }

        JSONObject awardobj = (JSONObject)optJSONObject("awarded");
        return new PlayerAchievement(awardobj);
    }

    public long getAwards() {
        return optLong("awards");
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
