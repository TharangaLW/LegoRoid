package com.lw.legoroid.async.service.httprequest;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class POSTRequest implements HttpServiceRequest{

	String strResponse;
	JSONObject jsonObjectResponce = null;
	
	public JSONObject makeHttpRequest(String url,String requestData) throws Exception {
        try {
        	
        	Log.e("LEGO-SERVICE" ,"API-URL : " + url);
        	
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "*/*");
           
            if (requestData != null) {
            	StringEntity stringEntity = new StringEntity(requestData);
            	//stringEntity.setContentType("application/json;charset=UTF-8");
            	//stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
                httpPost.setEntity(stringEntity);
            }

            httpResponse = httpClient.execute(httpPost);
            
            httpEntity = httpResponse.getEntity();
            strResponse = EntityUtils.toString(httpEntity);
            
            Log.d("LEGO-SERVICE" , "Responce String : " + strResponse);
            
            jsonObjectResponce = new JSONObject(strResponse);
 
        } catch (Exception e){
        	throw new Exception(e.getMessage().toString());
        }
         
        return jsonObjectResponce;
 
    }

	@Override
	public JSONObject makeHttpRequest(String url,
			List<NameValuePair> requestData) throws Exception {
		// TODO Auto-generated method stub
		try {
        	
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "*/*");
           
            if (requestData != null) {
                httpPost.setEntity(new UrlEncodedFormEntity(requestData));
            }

            httpResponse = httpClient.execute(httpPost);
            
            httpEntity = httpResponse.getEntity();
            strResponse = EntityUtils.toString(httpEntity);
            
            Log.d("LEGO-SERVICE" , "Responce String : " + strResponse);
            
            jsonObjectResponce = new JSONObject(strResponse);
 
        } catch (Exception e){
        	throw new Exception(e.getMessage().toString());
        }
         
        return jsonObjectResponce;
	}
	
}
