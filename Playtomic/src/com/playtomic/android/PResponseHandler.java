package com.playtomic.android;

import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;

public abstract class PResponseHandler extends JsonHttpResponseHandler {
	public abstract void onResponse(PResponse response, JSONObject data);
}