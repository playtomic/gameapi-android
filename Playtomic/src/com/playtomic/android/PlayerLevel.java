package com.playtomic.android;

import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
import org.json.JSONException;

public class PlayerLevel extends JSONObject {

	public PlayerLevel() {
		
	}
	
	public PlayerLevel(JSONObject data) {
		
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
    
    public String getName() {
    	return optString("name", "");
    }
    
    public void setName(String name) {
    	setValue("name", name);
    }
    
    public String getLevelId() {
    	return optString("levelid", "");
    }
    
    public String getPlayerName() {
    	return optString("playername", "");
    }
    
    public void setPlayerName(String name) {
    	setValue("playername", name);
    }

    public String getData() {
    	return optString("data", "");
    }
    
    public void setData(String data) {
    	setValue("data", data);
    }
    
    public String getSource() {
    	return optString("source", "");
    }
    
    public void setSource(String source) {
    	setValue("source", source);
    }     
    
    public long getVotes() {
    	return optLong("votes", 0);
    }
    
    public void setVotes(long points) {
    	setValue("votes", points);
    }
    
    public long getScore() {
    	return optLong("score", 0);
    }
    
    public double getRating() {
    	long score = getScore();
    	long votes = getVotes();
    	
    	if(score == 0 || votes == 0) {
    		return 0;
    	}
    	
    	return getScore() / getVotes();
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