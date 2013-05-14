package com.playtomic.android;
import org.json.JSONObject;
import org.json.JSONException;

public class ListOptions extends JSONObject {
	
	public void set(String name, Object value) {
		try {
			put(name, value);
		} catch(JSONException err) {
			
		}
	}

	public void setFieldFilter(String name, Object value) {
    	if(!has("filters")) {
    		set("filters", new JSONObject());
    	}
    	
    	try {
    		optJSONObject("filters").put(name, value);
    	} catch(JSONException err) {
    		
    	}
	}
}
