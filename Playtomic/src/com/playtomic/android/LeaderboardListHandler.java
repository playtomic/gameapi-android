package com.playtomic.android;

import java.util.ArrayList;

public interface LeaderboardListHandler {
	void onSuccess(ArrayList<PlayerScore> scores, int numscores, PResponse response);
	void onFailure(PResponse response);
}