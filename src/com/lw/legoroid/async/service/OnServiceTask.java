package com.lw.legoroid.async.service;

import org.json.JSONObject;

public interface OnServiceTask {

	public void onBeforeExecute();
	public void onAfterExecute(JSONObject object);
		
}
