package com.playtomic.tests;

import java.util.ArrayList;
import com.playtomic.android.PResponse;
import com.playtomic.android.PlayerScore;
import com.playtomic.android.Leaderboards;
import com.playtomic.android.LeaderboardSaveHandler;
import com.playtomic.android.LeaderboardListHandler;
import com.playtomic.android.ListOptions;
import org.json.JSONArray;

public class PTestLeaderboards extends PTest 
{	
	public static int rnd;
	
	public static void firstScore(final TestHandler callback) 
	{		
		final String section = "TestLeaderboards.firstScore";

		final PlayerScore score = new PlayerScore();
		score.setTable("scores" + rnd);
		score.setName("person1");
		score.setPoints(10000);
		score.setHighest();
		score.setField("rnd", rnd);
		
		Leaderboards.save (score, new LeaderboardSaveHandler() {
			
			@Override
			public void onFailure(PResponse r) {
				fail(section, "Request failed with errorcode " + r.getErrorCode());
				callback.done();
			}
			
			@Override
			public void onSuccess(PResponse r) {
				
				assertTrue(section + "#1", "Request succeeded", r.getSuccess());
				assertEquals(section + "#1", "No errorcode", r.getErrorCode(), 0);

				// duplicate score gets rejected
				score.setPoints(9000);
				try {
					Thread.sleep (1000);
				} catch (InterruptedException e) {
				}

				Leaderboards.save (score, new LeaderboardSaveHandler() {
					
					@Override
					public void onFailure(PResponse r) {
						fail(section, "Request failed with errorcode " + r.getErrorCode());
						callback.done();
					}
					
					@Override
					public void onSuccess(PResponse r2) {
						
						assertTrue(section + "#2", "Request succeeded", r2.getSuccess());
						assertEquals(section + "#2", "Rejected duplicate score", r2.getErrorCode(), 209);
	
						// better score gets accepted
						score.setPoints(11000);
	
						Leaderboards.save (score, new LeaderboardSaveHandler() {
							
							@Override
							public void onFailure(PResponse r) {
								fail(section, "Request failed with errorcode " + r.getErrorCode());
								callback.done();
							}
							
							@Override
							public void onSuccess(PResponse r3) {
								
								assertTrue(section + "#3", "Request succeeded", r3.getSuccess());
								assertEquals(section + "#3", "No errorcode", r3.getErrorCode(), 0);
		
								// score gets accepted
								score.setPoints(9000);
								score.setAllowDuplicates(true);
		
								Leaderboards.save (score, new LeaderboardSaveHandler() {
									
									@Override
									public void onFailure(PResponse r) {
										fail(section, "Request failed with errorcode " + r.getErrorCode());
										callback.done();
									}
									
									@Override
									public void onSuccess(PResponse r4) {
										assertTrue(section + "#4", "Request succeeded", r4.getSuccess());
										assertEquals(section + "#4", "No errorcode", r4.getErrorCode(), 0);
										callback.done();
									}
								});
							};
						});
					};
				});
			};
		});
	}

	public static void secondScore(final TestHandler callback) 
	{		
		final String section = "TestLeaderboards.secondScore";
		
		PlayerScore score = new PlayerScore();
		score.setTable("scores" + rnd);
		score.setName("person2");
		score.setPoints(20000);
		score.setHighest();
		score.setField("rnd", rnd);

		try {
			Thread.sleep (1000);
		} catch (InterruptedException e) {
		}
		
		Leaderboards.save (score, new LeaderboardSaveHandler() {
			
			@Override
			public void onFailure(PResponse r) {
				fail(section, "Request failed with errorcode " + r.getErrorCode());
				callback.done();
			}
			
			@Override
			public void onSuccess(PResponse r) {
				assertTrue(section, "Request succeeded", r.getSuccess());
				assertEquals(section, "No errorcode", r.getErrorCode(), 0);
				callback.done();
			}
		});
	}
	
	public static void highScores(final TestHandler callback)
	{
		final String section = "TestLeaderboards.gighscores";

		ListOptions options = new ListOptions();
		options.set("table", "scores" + rnd);
		options.set("highest", true);
		options.setFieldFilter("rnd", rnd);
		
		Leaderboards.list (options, new LeaderboardListHandler() {
			
			@Override
			public void onFailure(PResponse r) {
				fail(section, "Request failed with errorcode " + r.getErrorCode());
				callback.done();
			}
			
			@Override
			public void onSuccess(ArrayList<PlayerScore> scores, int numscores, PResponse r) {
				
				if(scores == null) {
					scores = new ArrayList<PlayerScore>();
				}
	
				assertTrue(section, "Request succeeded", r.getSuccess());
				assertEquals(section, "No errorcode", r.getErrorCode(), 0);
				assertTrue(section, "Received scores", scores.size() > 0);
				assertTrue(section, "Received numscores", numscores > 0);
	
				if(scores.size() > 1) {
					assertTrue(section, "First score is greater than second", scores.get(0).getPoints() > scores.get(1).getPoints());
				} else {
					assertTrue(section, "First score is greater than second forced failure", false);
				}
	
				callback.done();
			}
		});
	}
	
	public static void lowScores(final TestHandler callback)
	{
		final String section = "TestLeaderboards.lowScores";

		ListOptions options = new ListOptions();
		options.set("table", "scores" + rnd);
		options.set("lowest", true);
		options.setFieldFilter("rnd", rnd);
		options.set("perpage", 2);

		Leaderboards.list (options, new LeaderboardListHandler() {
			
			@Override
			public void onFailure(PResponse r) {
				fail(section, "Request failed with errorcode " + r.getErrorCode());
				callback.done();
			}
			
			@Override
			public void onSuccess(ArrayList<PlayerScore> scores, int numscores, PResponse r) {
			
				if(scores == null) {
					scores = new ArrayList<PlayerScore>();
				}
				
				assertTrue(section, "Request succeeded", r.getSuccess());
				assertEquals(section, "No errorcode", r.getErrorCode(), 0);
				assertTrue(section, "Received scores", scores.size() == 2);
				assertTrue(section, "Received numscores", numscores > 0);
	
				if(scores.size() > 1) {
					assertTrue(section, "First score is less than second", scores.get(0).getPoints() < scores.get(1).getPoints());
				} else {
					assertTrue(section, "First score is less than second forced failure", false);
				}
	
				callback.done();
			}
		});
	}
	
	public static void allScores(final TestHandler callback)
	{
		final String section = "TestLeaderboards.allScores";

		ListOptions options = new ListOptions();
		options.set("table", "scores" + rnd);
		options.set("mode", "newest");
		options.set("perpage", 2);
		
		Leaderboards.list (options, new LeaderboardListHandler() {
			
			@Override
			public void onFailure(PResponse r) {
				fail(section, "Request failed with errorcode " + r.getErrorCode());
				callback.done();
			}
			
			@Override
			public void onSuccess(ArrayList<PlayerScore> scores, int numscores, PResponse r) {
				if(scores == null) {
					scores = new ArrayList<PlayerScore>();
				}
				assertTrue(section, "Request succeeded", r.getSuccess());
				assertEquals(section, "No errorcode", r.getErrorCode(), 0);
				assertTrue(section, "Received scores", scores.size() > 0);
				assertTrue(section, "Received numscores", numscores > 0);
	
				if(scores.size() > 1) {
					assertTrue(section, "First score is newer or equal to second", scores.get(0).getDate().before(scores.get(1).getDate()));
				} else {
					assertTrue(section, "First score is newer or equal to second forced failure", false);
				}
	
				callback.done();
			}
		});
	}

	public static void friendsScores(final TestHandler callback)
	{
		final String section = "TestLeaderboards.friendsScores";

		String[] playerids = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
		int points = 0;

		for(int i = 0; i < 10; i++)
		{
			try {
				Thread.sleep (500);
			} catch (InterruptedException e) {
			}
			
			points += 1000;
			String playerid = playerids [i];
			
			PlayerScore score = new PlayerScore();
			score.setTable("friends" + rnd);
			score.setName("playerid" + playerid);
			score.setPlayerId(playerid);
			score.setPoints(points);
			score.setHighest();
			score.setField("rnd", rnd);

			Leaderboards.save (score, new LeaderboardSaveHandler(){
				@Override
				public void onFailure(PResponse r) {
					fail(section, "Request failed with errorcode " + r.getErrorCode());
					callback.done();
				}
				
				@Override 
				public void onSuccess(PResponse r) {
					
				}
			});
		}

		JSONArray friends = new JSONArray();
		friends.put("1");
		friends.put("2");
		friends.put("3");
		
		ListOptions options = new ListOptions();
		options.set("table", "friends" + rnd);
		options.set("friendslist", friends);
		options.set("perpage", 3);
		
		Leaderboards.list(options, new LeaderboardListHandler() {
			
			public void onFailure(PResponse r) {
				fail(section, "Request failed with errorcode " + r.getErrorCode());
				callback.done();
			}
			
			public void onSuccess(ArrayList<PlayerScore> scores, int numscores, PResponse r) {
				if(scores == null) {
					scores = new ArrayList<PlayerScore>();
				}
				
				assertTrue(section, "Request succeeded", r.getSuccess());
				assertEquals(section, "No errorcode", r.getErrorCode(), 0);
				assertTrue(section, "Received 3 scores", scores.size() == 3);
				assertTrue(section, "Received numscores 3", numscores == 3);
				assertEquals(section, "Player id #1", scores.get(0).getPlayerId(), "3");
				assertEquals(section, "Player id #2", scores.get(1).getPlayerId(), "2");
				assertEquals(section, "Player id #3", scores.get(2).getPlayerId(), "1");
				callback.done();
			}
		});
	}

	public static void ownScores(final TestHandler callback)
	{
		final String section = "TestLeaderboards.ownScores";
		int points = 0;
		
		for(int i=0; i<9; i++)
		{			
			try {
				Thread.sleep (500);
			} catch (InterruptedException e) {
			}
			
			points += 1000;
			PlayerScore score = new PlayerScore();
			score.setTable("personal" + rnd);
			score.setName("test account");
			score.setPlayerId("test@testuri.com");
			score.setPoints(points);
			score.setHighest();
			score.setAllowDuplicates(true);
			score.setField("rnd", rnd);

			Leaderboards.save (score, new LeaderboardSaveHandler(){
				@Override
				public void onFailure(PResponse r) {
					fail(section, "Request failed with errorcode " + r.getErrorCode());
					callback.done();
				}
				
				@Override 
				public void onSuccess(PResponse r) {
					
				}
			});
		}
		
		PlayerScore finalscore = new PlayerScore();
		finalscore.setTable("personal" + rnd);
		finalscore.setName("test account");
		finalscore.setPlayerId("test@testuri.com");
		finalscore.setPoints(3000);
		finalscore.setHighest();
		finalscore.setAllowDuplicates(true);
		finalscore.setField("rnd", rnd);
		finalscore.setPerPage(5);

		Leaderboards.saveAndList(finalscore, new LeaderboardListHandler() {
			
			public void onFailure(PResponse r) {
				fail(section, "Request failed with errorcode " + r.getErrorCode());
				callback.done();
			}
			
			public void onSuccess(ArrayList<PlayerScore> scores, int numscores, PResponse r) {
				if(scores == null) {
					scores = new ArrayList<PlayerScore>();
				}

				assertTrue(section, "Request succeeded", r.getSuccess());
				assertEquals(section, "No errorcode", r.getErrorCode(), 0);
				assertTrue(section, "Received 5 scores", scores.size() == 5);
				assertTrue(section, "Received numscores 10", numscores == 10);
				assertTrue(section, "Score 1 ranked 6", scores.get(0).getRank() == 6);
				assertTrue(section, "Score 2 ranked 7", scores.get(1).getRank() == 7);
				assertTrue(section, "Score 3 ranked 8", scores.get(2).getRank() == 8);
				assertTrue(section, "Score 4 ranked 9", scores.get(3).getRank() == 9);
				assertTrue(section, "Score 5 ranked 10", scores.get(4).getRank() == 10);
				assertTrue(section, "Score 1 points", scores.get(0).getPoints() == 4000);
				assertTrue(section, "Score 2 points", scores.get(1).getPoints() == 3000);
				assertTrue(section, "Score 3 points", scores.get(2).getPoints() == 3000);
				assertTrue(section, "Score 4 points", scores.get(3).getPoints() == 2000);
				assertTrue(section, "Score 5 points", scores.get(4).getPoints() == 1000);
				callback.done();
			}
		});
	}
}