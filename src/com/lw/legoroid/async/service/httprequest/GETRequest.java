package com.lw.legoroid.async.service.httprequest;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class GETRequest implements HttpServiceRequest{
	
	String strResponse;
	JSONObject jsonObjectResponce;
	
	public JSONObject makeHttpRequest(String url,String requestData) throws Exception {
        return null;
    }

	@Override
	public JSONObject makeHttpRequest(String url,
			List<NameValuePair> requestData) throws Exception {
		// TODO Auto-generated method stub
		try {
        	
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
           
            if (requestData != null) {
            	String paramString = URLEncodedUtils
                        .format(requestData, "utf-8");
                url += "?" + paramString;
            }
            
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Accept", "*/*");
            httpResponse = httpClient.execute(httpGet);
            
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
