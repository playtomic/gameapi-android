package com.playtomic.android;

import java.util.ArrayList;

public interface AchievementStreamHandler {
    void onSuccess(ArrayList<PlayerAward> achievements, int numachievements, PResponse response);
    void onFailure(PResponse response);
}
