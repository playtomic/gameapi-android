package com.playtomic.tests;

import java.util.Random; 
import com.playtomic.android.Playtomic;

public class PTests {
	
	PTestMethod[] _tests;
	int _testIndex = -1;
	
	public void start() 
	{
		Playtomic.initialize("testpublickey", "testprivatekey", "http://192.168.1.114:3000/");
		
		PTest.Setup ();	
		PTestLeaderboards.rnd = PTestPlayerLevels.rnd = PTestAchievements.rnd = RND();
		
		final TestHandler callback = new TestHandler() {
			@Override
			public void done() {
				_testIndex++;
				
				if(_testIndex < _tests.length) {
					_tests[_testIndex].run();
				} else {
					PTest.render();
				}
			}
		};	
		
		_tests = new PTestMethod[] {
				new PTestMethod() { public void run() { PTestGeoIP.lookup(callback); } },
				new PTestMethod() { public void run() { PTestGameVars.all(callback); } },
				new PTestMethod() { public void run() { PTestGameVars.single(callback); } },
				new PTestMethod() { public void run() { PTestLeaderboards.firstScore(callback); } },
				new PTestMethod() { public void run() { PTestLeaderboards.secondScore(callback); } },
				new PTestMethod() { public void run() { PTestLeaderboards.highScores(callback); } },
				new PTestMethod() { public void run() { PTestLeaderboards.lowScores(callback); } },
				new PTestMethod() { public void run() { PTestLeaderboards.allScores(callback); } },
				new PTestMethod() { public void run() { PTestLeaderboards.friendsScores(callback); } },
				new PTestMethod() { public void run() { PTestLeaderboards.ownScores(callback); } },
				new PTestMethod() { public void run() { PTestPlayerLevels.create(callback); } },
				new PTestMethod() { public void run() { PTestPlayerLevels.list(callback); } },
				new PTestMethod() { public void run() { PTestPlayerLevels.load(callback); } },
				new PTestMethod() { public void run() { PTestAchievements.list(callback); } },
                new PTestMethod() { public void run() { PTestAchievements.listWithFriends(callback); } },
                new PTestMethod() { public void run() { PTestAchievements.listWithPlayer(callback); } },
                new PTestMethod() { public void run() { PTestAchievements.listWithPlayerAndFriends(callback); } },
                new PTestMethod() { public void run() { PTestAchievements.stream(callback); } },
                new PTestMethod() { public void run() { PTestAchievements.streamWithFriends(callback); } },
                new PTestMethod() { public void run() { PTestAchievements.streamWithPlayerAndFriends(callback); } },
                new PTestMethod() { public void run() { PTestAchievements.save(callback); } }
		};
		
		callback.done();
	}
	
	private static int RND()
	{
		Random random = new Random();
		return random.nextInt();
	}
}
