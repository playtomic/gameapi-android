package com.playtomic.android;

public interface PlayerLevelSaveLoadHandler {
	void onSuccess(PlayerLevel level, PResponse response);
	void onFailure(PResponse response);
}