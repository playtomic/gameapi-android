package com.playtomic.android;

import org.json.JSONObject;

public class Newsletter {

    private static String SECTION = "newsletter";
    private static String SUBSCRIBE = "subscribe";

    /**
     * Subscribes a person to your newsletter
     * @param options   JSONObject with email and other fields you configure
     * @param callback	NewsletterSubscribeHandler for receiving the response
     */
    public static void subscribe(JSONObject options, final NewsletterSubscribeHandler callback) {
        PRequest.load(SECTION, SUBSCRIBE, options, new PResponseHandler() {

            @Override
            public void onResponse(PResponse response, JSONObject data) {
                if(response.getSuccess()) {
                    callback.onSuccess(response);
                } else {
                    callback.onFailure(response);
                }
            }

        });
    }
}
