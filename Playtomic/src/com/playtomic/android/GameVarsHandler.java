package com.playtomic.android;

import java.util.Hashtable;

public interface GameVarsHandler {
	void onSuccess(Hashtable<String, Object> gamevars, PResponse response);
	void onFailure(PResponse response);
}