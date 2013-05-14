package com.playtomic.tests;

import java.util.Hashtable;
import com.playtomic.android.GameVars; 
import com.playtomic.android.GameVarsHandler;
import com.playtomic.android.PResponse;

public class PTestGameVars extends PTest {
		
	public static void all(final TestHandler callback) {
		
		final String section = "TestGameVars.all";
		
		GameVars.load(new GameVarsHandler() {
			
			@Override
			public void onSuccess(Hashtable<String, Object> gv, PResponse r) {
				assertTrue(section, "Request succeeded", r.getSuccess());
				assertEquals(section, "No errorcode", r.getErrorCode(), 0);
				assertTrue(section, "Has known testvar1", gv.containsKey("testvar1"));
				assertTrue(section, "Has known testvar2", gv.containsKey("testvar2"));
				assertTrue(section, "Has known testvar3", gv.containsKey("testvar3"));
				assertEquals(section, "Has known testvar1 value", (String)gv.get("testvar1"), "testvalue1");
				assertEquals(section, "Has known testvar2 value", (String)gv.get("testvar2"), "testvalue2");
				assertEquals(section, "Has known testvar3 value", (String)gv.get("testvar3"), "testvalue3 and the final gamevar");
				callback.done();
			}
			
			@Override
			public void onFailure(PResponse response) {
				fail(section, "Request failed with errorcode " + response.getErrorCode());
				callback.done();
			}
		});
	}
		
	public static void single(final TestHandler callback) {
		
		final String section = "TestGameVars.single";

		GameVars.loadSingle("testvar1", new GameVarsHandler() {
			
			@Override
			public void onSuccess(Hashtable<String, Object> gv, PResponse r) {
				assertTrue(section, "Request succeeded", r.getSuccess());
				assertEquals(section, "No errorcode", r.getErrorCode(), 0);
				assertTrue(section, "Has testvar1", gv.containsKey("testvar1"));
				assertEquals(section, "Has known testvar1 value", (String)gv.get("testvar1"), "testvalue1");
				assertFalse(section, "Does not have testvar2", gv.containsKey("testvar2"));
				assertFalse(section, "Does not have testvar3", gv.containsKey("testvar3"));
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