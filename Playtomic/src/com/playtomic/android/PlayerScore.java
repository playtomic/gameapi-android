package com.playtomic.android;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
import org.json.JSONException;

public class PlayerScore extends JSONObject {
	
	public PlayerScore() {
		
	}
	
	public PlayerScore(JSONObject data) {
		
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
      
    public long getPoints() {
    	return optLong("points", 0);
    }
    
    public void setPoints(long points) {
    	setValue("points", points);
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

    public JSONObject filters() {

        if(!has("filters")) {
            setValue("filters", new JSONObject());
        }

        return optJSONObject("filters");
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

    public void setFilter(String name, Object value) {
        if(!has("filters")) {
            setValue("filters", new JSONObject());
        }

        try {
            filters().put(name, value);
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
    
    public int getRank() {
    	return optInt("rank", 0);
    }
    
    public void setTable(String table) {
    	setValue("table", table);
    }
    
    // listing options
    public void setAllowDuplicates(Boolean allow) {
    	setValue("allowduplicates", allow);
    }
    
    public void setHighest() {
    	setValue("highest", true);
    }
    
    public void setLowest() {
    	setValue("highest", false);
    }

    public boolean getSubmitted() {
        return optBoolean("submitted", false);
    }
    
    public void setPerPage(int perpage) {
    	setValue("perpage", perpage);
    }
    
    public void setFriendsList(ArrayList<String> friends) {
    	setValue("friendslist", friends);
    }
    
    private void setValue(String key, Object value)
    {
    	try {
    		put(key, value);
    	} catch(JSONException err) {
    		
    	}
    }
}
