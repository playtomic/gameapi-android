package com.playtomic.tests;

import java.util.ArrayList;

import com.playtomic.android.PResponse;
import com.playtomic.android.PlayerLevel;
import com.playtomic.android.PlayerLevels;
import com.playtomic.android.PlayerLevelSaveLoadHandler;
import com.playtomic.android.PlayerLevelListHandler;
import com.playtomic.android.PlayerLevelRateHandler;
import com.playtomic.android.ListOptions;

public class PTestPlayerLevels extends PTest 
{
	public static int rnd;
	
	public static void create(final TestHandler callback) 
	{
		final String section = "PTestPlayerLevels.create";

		final PlayerLevel level = new PlayerLevel();
		level.setName("create level" + rnd);
		level.setPlayerName("ben"+  rnd);
		level.setPlayerId("0");
		level.setData("this is the level data");
		level.setField("rnd", rnd);

		PlayerLevels.save (level, new PlayerLevelSaveLoadHandler() {
			
			@Override
			public void onFailure(PResponse r) {
				fail(section, "Request failed with errorcode " + r.getErrorCode());
				callback.done();
			}
			
			@Override
			public void onSuccess(PlayerLevel l, PResponse r) {
				if(l == null) {
					l = new PlayerLevel();
				}
				assertTrue(section + "#1", "Request succeeded", r.getSuccess());
				assertEquals(section + "#1", "No getErrorCode()", r.getErrorCode(), 0);
				assertTrue(section + "#1", "Returned level is not null", l.length() > 0);
				assertTrue(section + "#1", "Returned level has levelid", l.has("levelid"));
				assertEquals(section + "#1", "Level names match", level.getName(), l.getName()); 

				PlayerLevels.save (level, new PlayerLevelSaveLoadHandler() {
					
					@Override
					public void onFailure(PResponse r) {
						fail(section, "Request failed with errorcode " + r.getErrorCode());
						callback.done();
					}
					
					@Override
					public void onSuccess(PlayerLevel l2, PResponse r2) {
						assertTrue(section + "#2", "Request succeeded", r2.getSuccess());
						assertEquals(section + "#2", "Duplicate level getErrorCode()", r2.getErrorCode(), 405);
						callback.done();
					}
				});
			}
		});
	}
	
	public static void list(final TestHandler callback)
	{
		final String section = "PTestPlayerLevels.list";

		final ListOptions options = new ListOptions();
		options.set("page", 1);
		options.set("perpage", 10);
		options.set("data", false);
		options.setFieldFilter("rnd", rnd);
		
		PlayerLevels.list (options, new PlayerLevelListHandler() {
			
			@Override
			public void onFailure(PResponse r) {
				fail(section, "Request failed with errorcode " + r.getErrorCode());
				callback.done();
			}
			
			@Override
			public void onSuccess(ArrayList<PlayerLevel> levels, int numlevels, PResponse r) {
				
				if(levels == null) {
					levels = new ArrayList<PlayerLevel>();
				}
				assertTrue(section + "#1", "Request succeeded", r.getSuccess());
				assertEquals(section + "#1", "No getErrorCode()", r.getErrorCode(), 0);
				assertTrue(section + "#1", "Received levels", levels.size() > 0);
				assertTrue(section + "#1", "Received numlevels", numlevels >= levels.size());
	
				if(levels.size() > 0) {
					assertFalse(section + "#1", "First level has no data", levels.get(0).has("data"));
				} else {
					assertTrue(section + "#1", "First level has no data forced failure", false);
				}
	
				// list with data
				options.set("data", true);
	
				PlayerLevels.list (options, new PlayerLevelListHandler() {
					
					@Override
					public void onFailure(PResponse r) {
						fail(section, "Request failed with errorcode " + r.getErrorCode());
						callback.done();
					}
					
					@Override
					public void onSuccess(ArrayList<PlayerLevel> levels2, int numlevels2, PResponse r2) {
						
						if(levels2 == null) {
							levels2 = new ArrayList<PlayerLevel>();
						}
						assertTrue(section + "#2", "Request succeeded", r2.getSuccess());
						assertEquals(section + "#2", "No getErrorCode()", r2.getErrorCode(), 0);
						assertTrue(section + "#2", "Received levels", levels2.size() > 0);
						assertTrue(section + "#2", "Received numlevels", numlevels2 >= levels2.size());
		
						if(levels2.size() > 0) {
							assertTrue(section, "First level has data", levels2.get(0).has("data"));
						} else {
							assertTrue(section, "First level has no data forced failure", false);
						}
		
						callback.done();
					}
				});
			}
		});
	}
	
	public static void rate(final TestHandler callback)
	{
		final String section = "TestPlayerLevels.rate";

		PlayerLevel level = new PlayerLevel();
		level.setName("rate " + rnd);
		level.setPlayerName("ben" + rnd);
		level.setPlayerId("0");
		level.setData("this is the level data");
		level.setField("rnd", rnd);
		
		PlayerLevels.save (level, new PlayerLevelSaveLoadHandler() {
			
			@Override
			public void onFailure(PResponse r) {
				fail(section, "Request failed with errorcode " + r.getErrorCode());
				callback.done();
			}
			
			@Override
			public void onSuccess(final PlayerLevel l, PResponse r) {

				assertTrue(section + "#1", "Request succeeded", r.getSuccess());
				assertEquals(section + "#1", "No getErrorCode()", r.getErrorCode(), 0);
				assertTrue(section + "#1", "Returned level is not null", l.length() > 0);
				assertTrue(section + "#1", "Returned level has levelid", l.has("levelid"));

				// invalid rating
				PlayerLevels.rate (l.getLevelId(), 70, new PlayerLevelRateHandler() {
					
					@Override
					public void onSuccess(PResponse r) {
						fail(section, "Request succeeded when it should have failed locally " + r.getErrorCode());
						callback.done();
					}
					
					@Override
					public void onFailure(PResponse r2) {

						assertFalse(section + "#2", "Request failed", r2.getSuccess());
						assertEquals(section + "#2", "Invalid rating getErrorCode()", r2.getErrorCode(), 401);

						// valid rating
						PlayerLevels.rate (l.getLevelId(), 7, new PlayerLevelRateHandler() {
							
							@Override
							public void onFailure(PResponse r) {
								fail(section + "#2", "Request failed with errorcode " + r.getErrorCode());
								callback.done();
							}
							
							@Override
							public void onSuccess(PResponse r3) {
								
								assertTrue(section + "#3", "Request succeeded", r3.getSuccess());
								assertEquals(section + "#3", "No errrorcode", r3.getErrorCode(), 0);

								// duplicate rating
								PlayerLevels.rate (l.getLevelId(), 6, new PlayerLevelRateHandler() {
									
									@Override
									public void onSuccess(PResponse r) {
										fail(section + "#4", "Request succeeded when it should have failed " + r.getErrorCode());
										callback.done();
									}
									
									@Override
									public void onFailure(PResponse r4) {
										assertFalse(section + "#4", "Request failed", r4.getSuccess());
										assertEquals(section + "#4", "Already rated getErrorCode()", r4.getErrorCode(), 402);
										callback.done();
									}
								});
							}
						});
					}
				});
			}
		});
	}
	
	public static void load(final TestHandler callback)
	{
		final String section = "TestPlayerLevels.load";

		final PlayerLevel level = new PlayerLevel();
		level.setName("sample loading level " + rnd);
		level.setPlayerName("ben" + rnd);
		level.setPlayerId(Integer.toString(rnd));
		level.setData("this is the level data");
		level.setField("rnd", rnd);

		PlayerLevels.save (level, new PlayerLevelSaveLoadHandler() {
			
			@Override
			public void onFailure(PResponse r) {
				fail(section, "Request failed with errorcode " + r.getErrorCode());
				callback.done();
			}
			
			@Override
			public void onSuccess(PlayerLevel l, PResponse r) {
				assertTrue(section + "#1", "Request succeeded", r.getSuccess());
				assertEquals(section + "#1", "No getErrorCode()", r.getErrorCode(), 0);
				assertTrue(section + "#1", "Name is correct", l.has("levelid"));
				assertEquals(section + "#1", "Name is correct", level.getName(), l.getName());
				assertEquals(section + "#1", "Data is correct", level.getData(), l.getData());
				
				PlayerLevels.load (l.getLevelId(), new PlayerLevelSaveLoadHandler() {
					
					@Override
					public void onFailure(PResponse r) {
						fail(section, "Request failed with errorcode " + r.getErrorCode());
						callback.done();
					}
					
					@Override
					public void onSuccess(PlayerLevel l2, PResponse r2) {
						assertTrue(section + "#2", "Request succeeded", r2.getSuccess());
						assertEquals(section + "#2", "Name is correct", level.getName(), l2.getName());
						assertEquals(section + "#2", "Data is correct", level.getData(), l2.getData());
						callback.done();
					}
				});
			}
		});
	}
}