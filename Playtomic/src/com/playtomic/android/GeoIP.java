package com.playtomic.android;

import org.json.JSONObject;

public class GeoIP {

    private static String SECTION = "geoip";
    private static String LOOKUP = "lookup";

    public static void lookup(final GeoIPHandler callback) {

    	PRequest.load(SECTION, LOOKUP, null, new PResponseHandler() {
    		
			@Override
			public void onResponse(PResponse response, JSONObject data) {
				
				if(response.getSuccess())
				{
					PlayerCountry country = new PlayerCountry();
					country.setName(data.optString("name"));
					country.setCode(data.optString("code"));
					callback.onSuccess(country, response);
				}
				else
				{
					callback.onFailure(response);
				}
			}
    	});
    }    
}