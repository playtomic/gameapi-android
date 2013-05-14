package com.playtomic.android;


public interface GeoIPHandler {
	void onSuccess(PlayerCountry country, PResponse response);
	void onFailure(PResponse response);
}