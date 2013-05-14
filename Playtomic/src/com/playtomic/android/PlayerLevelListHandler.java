package com.playtomic.android;

import java.util.ArrayList;

public interface PlayerLevelListHandler {
	void onSuccess(ArrayList<PlayerLevel> levels, int numscores, PResponse response);
	void onFailure(PResponse response);
}