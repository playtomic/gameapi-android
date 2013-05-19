package com.playtomic.android;

import java.util.ArrayList;

public interface AchievementListHandler {
    void onSuccess(ArrayList<PlayerAchievement> achievements, PResponse response);
    void onFailure(PResponse response);
}
