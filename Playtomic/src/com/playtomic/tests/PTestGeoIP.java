package com.playtomic.tests;

import com.playtomic.android.GeoIP; 
import com.playtomic.android.GeoIPHandler;
import com.playtomic.android.PlayerCountry;
import com.playtomic.android.PResponse;

public class PTestGeoIP extends PTest {
		
	public static void lookup(final TestHandler callback) {
		
		final String section = "TestGeoIP.lookup";
		
		GeoIP.lookup(new GeoIPHandler() {
			
			@Override
			public void onSuccess(PlayerCountry geo, PResponse r) {
				assertTrue(section, "Request succeeded", r.getSuccess());
				assertEquals(section, "No errorcode", r.getErrorCode(), 0);
				assertNotNull(section, "Has country name", geo.getName());
				assertNotNull(section, "Has country code", geo.getCode());
				callback.done();
			}
			
			@Override
			public void onFailure(PResponse response) {
				fail(section, "Request failed with errorcode " + response.getErrorCode());
				callback.done();
			}
		});
	}
}