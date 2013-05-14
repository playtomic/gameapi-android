package com.playtomic.android;

public class Playtomic {
	
	/**
	 * Initializes the API
	 * @param publickey		The public key you configured in your database
	 * @param privatekey	The private key you configured in your database
	 * @param apiurl		The path to your API server, http://example.com/
	 */
    public static void initialize(String publickey, String privatekey, String apiurl) {
    	PRequest.initialize(publickey, privatekey, apiurl);
    }
}
