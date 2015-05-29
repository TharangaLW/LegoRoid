package com.lw.legoroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.lw.legoroid.async.service.LegoService;
import com.lw.legoroid.async.service.OnServiceTask;
import com.lw.legoroid.async.service.RequestMethod;
import com.lw.legoroid.ui_parser.UIParser;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn = (Button) findViewById(R.id.btnSubmit);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*UIParser uiParser  = new UIParser(MainActivity.this,User.class);
				User user = (User) uiParser.parserFromUI();
				Toast.makeText(MainActivity.this, "userId : " + user.getUserId() + " ," + user.getPassword(), Toast.LENGTH_SHORT  ).show();*/
			
				try{
					String dd = getFileContent();
					
					LegoService legoService = new LegoService(getApplicationContext());
					legoService.makeRequest("/ride", dd, RequestMethod.POST);
				}catch(Exception e){
					Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT  ).show();
				}
				
			}
		});
		
	}
	
	private String getFileContent(){
		String json = null;
        try {

            InputStream is = getAssets().open("test_json.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
