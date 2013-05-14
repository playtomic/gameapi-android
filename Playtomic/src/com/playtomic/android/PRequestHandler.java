package com.playtomic.android;

import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;

public abstract class PRequestHandler extends JsonHttpResponseHandler {
	
	@Override
	public abstract void onSuccess(JSONObject object);

	@Override
    public abstract void onFailure(Throwable e, JSONObject errorResponse);
}