package com.lw.legoroid.async.service;

import java.util.List;

import org.apache.http.NameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import com.lw.legoroid.async.service.httprequest.DELETERequest;
import com.lw.legoroid.async.service.httprequest.GETRequest;
import com.lw.legoroid.async.service.httprequest.HttpServiceRequest;
import com.lw.legoroid.async.service.httprequest.POSTRequest;
import com.lw.legoroid.async.service.httprequest.PUTRequest;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class LegoService {

	private Context context;
	private String serverBaseUrl;
	private OnServiceTask onServiceTask;
	private String strRequestData;
	private List<NameValuePair> nameValuePairRequestData;
	private String apiPath;
	private int requestMethod;

	public LegoService(Context context) throws Exception {
		this.context = context;
		getServerUrl();
	}

	public void makeRequest(String apiPath,
			List<NameValuePair> nameValuePairRequestData, int requestMethod)
			throws Exception {
		try {
			this.apiPath = apiPath;
			this.nameValuePairRequestData = nameValuePairRequestData;
			this.requestMethod = requestMethod;

			if (nameValuePairRequestData != null) {
				new RequestAsyncTask().execute();
			}

		} catch (Exception e) {
			throw new Exception(
					"Exception : makeRequest(String apiPath,String strRequestData,int requestMethod)");
		}

	}

	public void makeRequest(String apiPath, String strRequestData,
			int requestMethod) throws Exception {
		try {
			this.apiPath = apiPath;
			this.strRequestData = strRequestData;
			this.requestMethod = requestMethod;

			if (strRequestData != null) {
				new RequestAsyncTask().execute();
			}

		} catch (Exception e) {
			throw new Exception(
					"Exception : makeRequest(String apiPath,String strRequestData,int requestMethod)");
		}

	}

	public void makeRequest(String apiPath, Object objectRequestDataSet,
			int requestMethod) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String strRequestDataSet = objectMapper
					.writeValueAsString(objectRequestDataSet);

			JSONObject jsonRequestData = new JSONObject(strRequestDataSet);
			this.apiPath = apiPath;
			this.strRequestData = jsonRequestData.toString();
			this.requestMethod = requestMethod;

			if (jsonRequestData != null) {
				new RequestAsyncTask().execute();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(
					"Exception : makeRequest(String apiPath,Object objectRequestDataSet,int requestMethod)");
		}

	}

	private class RequestAsyncTask extends AsyncTask<Void, Void, JSONObject> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			if (onServiceTask != null) {
				onServiceTask.onBeforeExecute();
			}
		}

		@Override
		protected JSONObject doInBackground(Void... parm) {
			// TODO Auto-generated method stub
			HttpServiceRequest httpServiceRequest;
			try {
				if (RequestMethod.POST == requestMethod) {
					httpServiceRequest = new POSTRequest();

					if (strRequestData != null) {
						return httpServiceRequest.makeHttpRequest(serverBaseUrl
								+ apiPath, strRequestData);
					} else {
						return httpServiceRequest.makeHttpRequest(serverBaseUrl
								+ apiPath, nameValuePairRequestData);
					}
				} else if (RequestMethod.GET == requestMethod) {
					httpServiceRequest = new GETRequest();

					if (strRequestData != null) {
						return httpServiceRequest.makeHttpRequest(serverBaseUrl
								+ apiPath, strRequestData);
					} else {
						return httpServiceRequest.makeHttpRequest(serverBaseUrl
								+ apiPath, nameValuePairRequestData);
					}

				} else if (RequestMethod.PUT == requestMethod) {
					httpServiceRequest = new PUTRequest();

					if (strRequestData != null) {
						return httpServiceRequest.makeHttpRequest(serverBaseUrl
								+ apiPath, strRequestData);
					} else {
						return httpServiceRequest.makeHttpRequest(serverBaseUrl
								+ apiPath, nameValuePairRequestData);
					}

				} else if (RequestMethod.DELETE == requestMethod) {
					httpServiceRequest = new DELETERequest();

					if (strRequestData != null) {
						return httpServiceRequest.makeHttpRequest(serverBaseUrl
								+ apiPath, strRequestData);
					} else {
						return httpServiceRequest.makeHttpRequest(serverBaseUrl
								+ apiPath, nameValuePairRequestData);
					}
				} else {
					return null;
				}
			} catch (Exception e) {
				System.out.println("Exception : " + e.getMessage().toString());
				Log.e("LEGO-SERVICE", e.getMessage().toString());
				return null;
			}

		}

		@Override
		protected void onPostExecute(JSONObject resultJson) {
			// TODO Auto-generated method stub
			super.onPostExecute(resultJson);
			strRequestData = null;
			nameValuePairRequestData = null;
			if (onServiceTask != null) {
				onServiceTask.onAfterExecute(resultJson);
			}
		}

	}

	private void getServerUrl() throws Exception {

		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			Bundle bundle = ai.metaData;
			serverBaseUrl = bundle.getString("server_base_url");
		} catch (Exception e) {
			throw new Exception("Exception getServerUrl() : " + e.getMessage());
		}

	}

	public void setOnServiceTask(OnServiceTask onServiceTask) {
		this.onServiceTask = onServiceTask;
	}

}
