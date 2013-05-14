package com.playtomic.android;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.*;

public class PRequest {
	
	private static String APIURL;
	private static String PublicKey;
	private static String PrivateKey;
	
	public static void initialize(String publickey, String privatekey, String apiurl)
	{
		if(!apiurl.endsWith("/"))
		{
			apiurl += "/";
		}
		
		apiurl += "v1?publickey=" + publickey;
		
		APIURL = apiurl;
		PrivateKey = privatekey;
		PublicKey = publickey;
	}

	public static void load(String section, String action, JSONObject postdata, final PResponseHandler callback)
	{
		if(postdata == null)
		{
			postdata = new JSONObject();
		}
		else if(postdata.has("publickey"))
		{
			postdata.remove("publickey");
			postdata.remove("section");
			postdata.remove("action");
		}
		
		try {
			postdata.put("publickey", PublicKey);
			postdata.put("section", section);
			postdata.put("action", action);
		} catch(JSONException e) {
			callback.onResponse(new PResponse(false, 1), null);
			return;
		}

		String jsonstring = postdata.toString();
		String data = PEncode.base64(jsonstring);
		String hash = PEncode.md5(jsonstring + PrivateKey);
	
		RequestParams postparams = new RequestParams();
		postparams.put("data", data);
		postparams.put("hash", hash);

		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("Content-Type", "application/json");
		client.addHeader("Accept", "application/json");
		client.setTimeout(10000);
		
		client.post(APIURL, postparams, new PRequestHandler() {

		    @Override
			public void onSuccess(JSONObject object) {

				PResponse success = new PResponse();
				
				try {
					success.setErrorCode(object.has("errorcode") ? object.getInt("errorcode") : 1);
				} catch(JSONException ex) {
					success.setErrorCode(1);
				}
				
				try {				
					success.setSuccess(object.has("success") ? object.getBoolean("success") : false);
				} catch(JSONException ex) {
					success.setSuccess(false);
				}
				
				callback.onResponse(success, object);
			}

			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
				callback.onResponse(new PResponse(false, 1), null);
			}
		});
	}
}