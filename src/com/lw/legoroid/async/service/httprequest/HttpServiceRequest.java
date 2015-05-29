package com.lw.legoroid.async.service.httprequest;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.content.Context;

public interface HttpServiceRequest {	
	
	public JSONObject makeHttpRequest(String url,String requestData) throws Exception;
	public JSONObject makeHttpRequest(String url,List<NameValuePair> requestData) throws Exception;
}
