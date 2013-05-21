package com.playtomic.tests;

import java.util.ArrayList;

import android.util.Log;
import com.playtomic.android.*;
import org.json.JSONArray;

public class PTestAchievements extends PTest {

    public static int rnd;

    public static void list(final TestHandler callback) {

        final String section = "PTestAchievements.list";

        PlayerAchievement achievement1 = new PlayerAchievement();
        achievement1.setAchievement("Super Mega Achievement #1");
        achievement1.setAchievementKey("secretkey");
        achievement1.setPlayerId("1");
        achievement1.setPlayerName("ben");
        achievement1.setField("rnd", rnd);

        PlayerAchievement achievement2 = new PlayerAchievement();
        achievement2.setAchievement("Super Mega Achievement #1");
        achievement2.setAchievementKey("secretkey");
        achievement2.setPlayerId("2");
        achievement2.setPlayerName("michelle");
        achievement2.setField("rnd", rnd);

        PlayerAchievement achievement3 = new PlayerAchievement();
        achievement3.setAchievement("Super Mega Achievement #1");
        achievement3.setAchievementKey("secretkey");
        achievement3.setPlayerId("3");
        achievement3.setPlayerName("peter");
        achievement3.setField("rnd", rnd);

        PlayerAchievement achievement4 = new PlayerAchievement();
        achievement4.setAchievement("Super Mega Achievement #2");
        achievement4.setAchievementKey("secretkey2");
        achievement4.setPlayerId("3");
        achievement4.setPlayerName("peter");
        achievement4.setField("rnd", rnd);

        PlayerAchievement achievement5 = new PlayerAchievement();
        achievement5.setAchievement("Super Mega Achievement #2");
        achievement5.setAchievementKey("secretkey2");
        achievement5.setPlayerId("2");
        achievement5.setPlayerName("michelle");
        achievement5.setField("rnd", rnd);

        ArrayList<PlayerAchievement> achievements = new ArrayList<PlayerAchievement>();
        achievements.add(achievement1);
        achievements.add(achievement2);
        achievements.add(achievement3);
        achievements.add(achievement4);
        achievements.add(achievement5);

        listLoop(section, achievements, callback);
    }

    private static void listLoop(final String section, final ArrayList<PlayerAchievement> achievements, final TestHandler callback)
    {
        PlayerAchievement achievement = achievements.get(0);
        achievements.remove(0);

        try {
            Thread.sleep (2000);
        } catch (InterruptedException e) {
        }

        Achievements.save(achievement, new AchievementSaveHandler() {

            @Override
            public void onSuccess(PResponse r) {
                assertTrue(section + "#" + (5 - achievements.size()), "Request succeeded", r.getSuccess());
                assertEquals(section + "#" + (5 - achievements.size()), "No errorcode", r.getErrorCode(), 0);

                if(achievements.size() > 0) {
                    listLoop(section, achievements, callback);
                    return;
                }

                ListOptions options = new ListOptions();
                options.setFieldFilter("rnd", rnd);

                Achievements.list(options, new AchievementListHandler() {
                    @Override
                    public void onSuccess(ArrayList<PlayerAchievement> achievements, PResponse r) {
                        assertTrue(section + "#6", "Request succeeded", r.getSuccess());
                        assertEquals(section + "#6", "No errorcode", r.getErrorCode(), 0);
                        assertEquals(section + "#6", "Achievement 1 is correct", achievements.get(0).getAchievement(), "Super Mega Achievement #1");
                        assertEquals(section + "#6", "Achievement 1 is correct", achievements.get(1).getAchievement(), "Super Mega Achievement #2");
                        assertEquals(section + "#6", "Achievement 1 is correct", achievements.get(2).getAchievement(), "Super Mega Achievement #3");
                        callback.done();
                    }

                    @Override
                    public void onFailure(PResponse response) {
                        fail(section + "#6", "Request failed with errorcode " + response.getErrorCode());
                        callback.done();
                    }
                });
            }

            @Override
            public void onFailure(PResponse response) {
                fail(section + "#1", "Request failed with errorcode " + response.getErrorCode());
                callback.done();
            }
        });
    }

    public static void listWithFriends(final TestHandler callback) {
        final String section = "PTestAchievements.listWithFriends";

        JSONArray friends = new JSONArray();
        friends.put("1");
        friends.put("2");
        friends.put("3");

        ListOptions options = new ListOptions();
        options.setFieldFilter("rnd", rnd);
        options.set("friendslist", friends);

        Achievements.list(options, new AchievementListHandler() {
            @Override
            public void onSuccess(ArrayList<PlayerAchievement> achievements, PResponse r) {
                assertTrue(section, "Request succeeded", r.getSuccess());
                assertEquals(section, "No errorcode", r.getErrorCode(), 0);
                assertEquals(section, "Achievement 1 is correct", achievements.get(0).getAchievement(), "Super Mega Achievement #1");
                assertEquals(section, "Achievement 2 is correct", achievements.get(1).getAchievement(), "Super Mega Achievement #2");
                assertEquals(section, "Achievement 3 is correct", achievements.get(2).getAchievement(), "Super Mega Achievement #3");
                assertTrue(section, "Achievement 1 has friends", achievements.get(0).getFriends() != null);
                assertTrue(section, "Achievement 2 has friends", achievements.get(1).getFriends() != null);
                assertTrue(section, "Achievement 3 has no friends", achievements.get(2).getFriends() == null);
                assertTrue(section, "Achievement 1 has 3 friends", achievements.get(0).getFriends().size() == 3);
                assertTrue(section, "Achievement 1 friend 1", achievements.get(0).getFriends().get(0).getPlayerName().equals("ben"));
                assertTrue(section, "Achievement 1 friend 2", achievements.get(0).getFriends().get(1).getPlayerName().equals("michelle"));
                assertTrue(section, "Achievement 1 friend 3", achievements.get(0).getFriends().get(2).getPlayerName().equals("peter"));
                assertTrue(section, "Achievement 2 has 2 friend", achievements.get(1).getFriends().size() == 2);
                assertTrue(section, "Achievement 2 friend 1", achievements.get(1).getFriends().get(0).getPlayerName().equals("michelle"));
                assertTrue(section, "Achievement 2 friend 2", achievements.get(1).getFriends().get(1).getPlayerName().equals("peter"));
                callback.done();
            }

            @Override
            public void onFailure(PResponse response) {
                fail(section, "Request failed with errorcode " + response.getErrorCode());
                callback.done();
            }
        });
    }

    public static void listWithPlayer(final TestHandler callback) {
        final String section = "PTestAchievements.listWithPlayer";

        ListOptions options = new ListOptions();
        options.setFieldFilter("rnd", rnd);
        options.set("playerid", "1");

        Achievements.list(options, new AchievementListHandler() {
            @Override
            public void onSuccess(ArrayList<PlayerAchievement> achievements, PResponse r) {
                assertTrue(section, "Request succeeded", r.getSuccess());
                assertEquals(section, "No errorcode", r.getErrorCode(), 0);
                assertEquals(section, "Achievement 1 is correct", achievements.get(0).getAchievement(), "Super Mega Achievement #1");
                assertEquals(section, "Achievement 2 is correct", achievements.get(1).getAchievement(), "Super Mega Achievement #2");
                assertEquals(section, "Achievement 3 is correct", achievements.get(2).getAchievement(), "Super Mega Achievement #3");
                assertTrue(section, "Achievement 1 has no friends", achievements.get(0).getFriends() == null);
                assertTrue(section, "Achievement 2 has no friends", achievements.get(1).getFriends() == null);
                assertTrue(section, "Achievement 3 has no friends", achievements.get(2).getFriends() == null);
                assertTrue(section, "Achievement 1 has does have player", achievements.get(0).getPlayer() != null);
                assertTrue(section, "Achievement 2 has no player", achievements.get(1).getPlayer() == null);
                assertTrue(section, "Achievement 3 has no player", achievements.get(2).getPlayer() == null);
                assertTrue(section, "Achievement 1 player is ben", achievements.get(0).getPlayer().getPlayerName().equals("ben"));
                callback.done();
            }

            @Override
            public void onFailure(PResponse response) {
                fail(section, "Request failed with errorcode " + response.getErrorCode());
                callback.done();
            }
        });
    }

    public static void listWithPlayerAndFriends(final TestHandler callback) {
        final String section = "PTestAchievements.listWithPlayerAndFriends";

        JSONArray friends = new JSONArray();
        friends.put("2");
        friends.put("3");

        ListOptions options = new ListOptions();
        options.setFieldFilter("rnd", rnd);
        options.set("playerid", "1");
        options.set("friendslist", friends);

        Achievements.list(options, new AchievementListHandler() {
            @Override
            public void onSuccess(ArrayList<PlayerAchievement> achievements, PResponse r) {
                assertTrue(section, "Request succeeded", r.getSuccess());
                assertEquals(section, "No errorcode", r.getErrorCode(), 0);
                assertEquals(section, "Achievement 1 is correct", achievements.get(0).getAchievement(), "Super Mega Achievement #1");
                assertEquals(section, "Achievement 2 is correct", achievements.get(1).getAchievement(), "Super Mega Achievement #2");
                assertEquals(section, "Achievement 3 is correct", achievements.get(2).getAchievement(), "Super Mega Achievement #3");
                assertTrue(section, "Achievement 1 has player", achievements.get(0).getPlayer() != null);
                assertTrue(section, "Achievement 1 has friends", achievements.get(0).getFriends() != null);
                assertTrue(section, "Achievement 2 has friends", achievements.get(1).getFriends() != null);
                assertTrue(section, "Achievement 2 has no player", achievements.get(1).getPlayer() == null);
                assertTrue(section, "Achievement 3 has no friends", achievements.get(2).getFriends() == null);
                assertTrue(section, "Achievement 3 has no player", achievements.get(2).getPlayer() == null);
                assertTrue(section, "Achievement 1 player", achievements.get(0).getPlayer().getPlayerName().equals("ben"));
                assertTrue(section, "Achievement 1 has 2 friend", achievements.get(0).getFriends().size() == 2);
                assertTrue(section, "Achievement 1 friend 1", achievements.get(0).getFriends().get(0).getPlayerName().equals("michelle"));
                assertTrue(section, "Achievement 1 friend 2", achievements.get(0).getFriends().get(1).getPlayerName().equals("peter"));
                assertTrue(section, "Achievement 2 has 2 friend", achievements.get(1).getFriends().size() == 2);
                assertTrue(section, "Achievement 2 friend 1", achievements.get(1).getFriends().get(0).getPlayerName().equals("michelle"));
                assertTrue(section, "Achievement 2 friend 2", achievements.get(1).getFriends().get(1).getPlayerName().equals("peter"));
                callback.done();
            }

            @Override
            public void onFailure(PResponse response) {
                fail(section, "Request failed with errorcode " + response.getErrorCode());
                callback.done();
            }
        });
    }

    public static void stream(final TestHandler callback) {
        final String section = "PTestAchievements.stream";

        ListOptions options = new ListOptions();
        options.setFieldFilter("rnd", rnd);

        Achievements.stream(options, new AchievementStreamHandler() {
            @Override
            public void onSuccess(ArrayList<PlayerAward> achievements, int numachievements, PResponse r) {
                assertTrue(section, "Request succeeded", r.getSuccess());
                assertEquals(section, "No errorcode", r.getErrorCode(), 0);
                assertTrue(section, "5 achievements returned", achievements.size() == 5);
                assertTrue(section, "5 achievements in total", numachievements == 5);
                assertTrue(section, "Achievement 1 person", achievements.get(0).getPlayerName().equals("michelle"));
                assertTrue(section, "Achievement 1 achievement", achievements.get(0).getAwarded().getAchievement().equals("Super Mega Achievement #2"));
                assertTrue(section, "Achievement 2 person", achievements.get(1).getPlayerName().equals("peter"));
                assertTrue(section, "Achievement 2 achievement", achievements.get(1).getAwarded().getAchievement().equals("Super Mega Achievement #2"));
                assertTrue(section, "Achievement 3 person", achievements.get(2).getPlayerName().equals("peter"));
                assertTrue(section, "Achievement 3 achievement", achievements.get(2).getAwarded().getAchievement().equals("Super Mega Achievement #1"));
                assertTrue(section, "Achievement 4 person", achievements.get(3).getPlayerName().equals("michelle"));
                assertTrue(section, "Achievement 4 achievement", achievements.get(3).getAwarded().getAchievement().equals("Super Mega Achievement #1"));
                assertTrue(section, "Achievement 5 person", achievements.get(4).getPlayerName().equals("ben"));
                assertTrue(section, "Achievement 5 achievement", achievements.get(4).getAwarded().getAchievement().equals("Super Mega Achievement #1"));
                callback.done();
            }

            @Override
            public void onFailure(PResponse response) {
                fail(section, "Request failed with errorcode " + response.getErrorCode());
                callback.done();
            }
        });
    }

    public static void streamWithFriends(final TestHandler callback) {
        final String section = "PTestAchievements.streamWithFriends";

        JSONArray friends = new JSONArray();
        friends.put("2");
        friends.put("3");

        ListOptions options = new ListOptions();
        options.setFieldFilter("rnd", rnd);
        options.set("friendslist", friends);
        options.set("group", true);

        Achievements.stream(options, new AchievementStreamHandler() {
            @Override
            public void onSuccess(ArrayList<PlayerAward> achievements, int numachievements, PResponse r) {
                assertTrue(section, "Request succeeded", r.getSuccess());
                assertEquals(section, "No errorcode", r.getErrorCode(), 0);
                assertTrue(section, "2 achievements returned", achievements.size() == 2);
                assertTrue(section, "2 achievements in total", numachievements == 2);
                assertTrue(section, "Achievement 1 awards", achievements.get(0).getAwards() == 2);
                assertTrue(section, "Achievement 1 achievement", achievements.get(0).getAwarded().getAchievement().equals("Super Mega Achievement #2"));
                assertTrue(section, "Achievement 1 person", achievements.get(0).getPlayerName().equals("michelle"));
                assertTrue(section, "Achievement 2 awards", achievements.get(1).getAwards() == 2);
                assertTrue(section, "Achievement 2 achievement", achievements.get(1).getAwarded().getAchievement().equals("Super Mega Achievement #2"));
                assertTrue(section, "Achievement 2 person", achievements.get(1).getPlayerName().equals("peter"));
                callback.done();
            }

            @Override
            public void onFailure(PResponse response) {
                fail(section, "Request failed with errorcode " + response.getErrorCode());
                callback.done();
            }
        });
    }

    public static void streamWithPlayerAndFriends(final TestHandler callback) {
        final String section = "PTestAchievements.streamWithPlayerAndFriends";

        JSONArray friends = new JSONArray();
        friends.put("2");
        friends.put("3");

        ListOptions options = new ListOptions();
        options.setFieldFilter("rnd", rnd);
        options.set("friendslist", friends);
        options.set("playerid", "1");
        options.set("group", true);

        Achievements.stream(options, new AchievementStreamHandler() {
            @Override
            public void onSuccess(ArrayList<PlayerAward> achievements, int numachievements, PResponse r) {
                assertTrue(section, "Request succeeded", r.getSuccess());
                assertEquals(section, "No errorcode", r.getErrorCode(), 0);
                assertTrue(section, "3 achievements returned", achievements.size() == 3);
                assertTrue(section, "3 achievements in total", numachievements == 3);
                assertTrue(section, "Achievement 1 person", achievements.get(0).getPlayerName().equals("michelle"));
                assertTrue(section, "Achievement 1 awards", achievements.get(0).getAwards() == 2);
                assertTrue(section, "Achievement 1 achievement", achievements.get(0).getAwarded().getAchievement().equals("Super Mega Achievement #2"));
                assertTrue(section, "Achievement 2 person", achievements.get(1).getPlayerName().equals("peter"));
                assertTrue(section, "Achievement 2 awards", achievements.get(1).getAwards() == 2);
                assertTrue(section, "Achievement 2 achievement", achievements.get(1).getAwarded().getAchievement().equals("Super Mega Achievement #2"));
                assertTrue(section, "Achievement 3 person", achievements.get(2).getPlayerName().equals("ben"));
                assertTrue(section, "Achievement 3 awards", achievements.get(2).getAwards() == 1);
                assertTrue(section, "Achievement 3 achievement", achievements.get(2).getAwarded().getAchievement().equals("Super Mega Achievement #1"));
                callback.done();
            }

            @Override
            public void onFailure(PResponse response) {
                fail(section, "Request failed with errorcode " + response.getErrorCode());
                callback.done();
            }
        });
    }

    public static void save(final TestHandler callback)
    {
        final String section = "PTestAchievements.save";

        final PlayerAchievement achievement = new PlayerAchievement();
        achievement.setAchievement("Super Mega Achievement #1");
        achievement.setAchievementKey("secretkey");
        achievement.setPlayerId(Integer.toString(rnd));
        achievement.setPlayerName("a random name " + rnd);
        achievement.setField("rnd", rnd);

        Achievements.save(achievement, new AchievementSaveHandler() {
            @Override
            public void onSuccess(PResponse r) {
                assertTrue(section + "#1", "Request succeeded", r.getSuccess());
                assertEquals(section + "#1", "No errorcode", r.getErrorCode(), 0);

                // second save gets rejected
                Achievements.save(achievement, new AchievementSaveHandler() {

                    @Override
                    public void onFailure(PResponse r2) {
                        assertFalse(section + "#2", "Request failed", r2.getSuccess());
                        assertEquals(section + "#2", "Already had achievement errorcode", r2.getErrorCode(), 505);

                        // third save gets allowed
                        achievement.setOverwrite();

                        Achievements.save(achievement, new AchievementSaveHandler() {
                            @Override
                            public void onSuccess(PResponse r3) {
                                assertTrue(section + "#3", "Request succeeded", r3.getSuccess());
                                assertEquals(section + "#3", "No errorcode", r3.getErrorCode(), 506);

                                // fourth save gets allowed
                                achievement.remove("overwrite");
                                achievement.setAllowDuplicates();

                                Achievements.save(achievement, new AchievementSaveHandler() {
                                    @Override
                                    public void onSuccess(PResponse r4) {
                                        assertTrue(section + "#4", "Request succeeded", r4.getSuccess());
                                        assertEquals(section + "#4", "No errorcode", r4.getErrorCode(), 506);
                                        callback.done();
                                    }

                                    @Override
                                    public void onFailure(PResponse response) {
                                        fail(section + "#4", "Request failed with errorcode " + response.getErrorCode());
                                        callback.done();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(PResponse response) {
                                fail(section + "#3", "Request failed with errorcode " + response.getErrorCode());
                                callback.done();
                            }
                        });
                    }

                    @Override
                    public void onSuccess(PResponse response) {
                        fail(section + "#2", "Request succeeded when it should have failed with errorcode " + response.getErrorCode());
                        callback.done();
                    }
                });
            }

            @Override
            public void onFailure(PResponse response) {
                fail(section + "#1", "Request failed with errorcode " + response.getErrorCode());
                callback.done();
            }
        });
    }
}
