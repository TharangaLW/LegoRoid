package com.lw.legoroid.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.lw.legoroid.R;
import com.lw.legoroid.R.id;
import com.lw.legoroid.R.layout;
import com.lw.legoroid.async.service.LegoService;
import com.lw.legoroid.async.service.OnServiceTask;
import com.lw.legoroid.async.service.RequestMethod;
import com.lw.legoroid.legoparser.ModelParser;
import com.lw.legoroid.legoparser.UIParser;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
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
				
				UIParser uiParser  = new UIParser(MainActivity.this,User.class);
				User user = (User) uiParser.parse();
				
				Toast.makeText(MainActivity.this, 
							"userId : " + user.getUserId() + 
							" , fname : " + user.getFirstName() +
							" , gender : " + user.getGender() +
							" , employee state : " + user.isEmployee() +
							" , age : " + user.getAge()
							, Toast.LENGTH_SHORT  ).show();
							
				
				
				/*
				User user = new User();
				user.setUserId("u1");
				user.setFirstName("tharanga");
				user.setGender("female");
				user.setEmployee(true);
				user.setAge(10);
				
				ModelParser modelParser = new ModelParser(MainActivity.this, user);
				modelParser.parser();
				*/
			}
		});
		
	}
	
	
}
