# **Welcome to the LegoRoid**

Development framework for Android.
## UI to Model Parser

Parse input data from UI to Java Model

**Model** 
```java
public class User {
	
	private String userId;
	
	private String firstName;
	
	private String gender;
	
	private boolean employee;
	
	@CustomUI(ui="txt_age")
	private int age;

    .....
}
```

**Layout**
```xml
<RelativeLayout 
    <EditText
        android:id="@+id/userId"
        ... />

    <EditText
        android:id="@+id/firstName"
        ... />
    
    <Button
        android:id="@+id/btnSubmit"
        ... />

    <RadioGroup
        android:id="@+id/gender"
        ... >

        <RadioButton
            android:id="@+id/male"
            ... />

        <RadioButton
            android:id="@+id/female"
            ... />
    </RadioGroup>

    <CheckBox
        android:id="@+id/employee"
        ... />

    <TextView
        android:id="@+id/txt_age"
        ... />
</RelativeLayout>
```

**Activity / Fragment**
```java
public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		UIParser parser = new UIParser(MainActivity.this, User.class);
		User user = (User) parser.parse();
		
		Toast.makeText(MainActivity.this, 
				"userId : " + user.getUserId() + 
				" , fname : " + user.getFirstName() +
				" , gender : " + user.getGender() +
				" , employee state : " + user.isEmployee() +
				" , age : " + user.getAge()
				, Toast.LENGTH_SHORT  ).show();
		
	}
....

}
```


## Model to UI Parser
Parse data from Java Model to UI

**Model**
```java
public class User {
	
	private String userId;
	
	private String firstName;
	
	private String gender;
	
	private boolean employee;
	
	@CustomUI(ui="txt_age")
	private int age;

    .....
}
```

**Layout**
```xml
<RelativeLayout 
    <EditText
        android:id="@+id/userId"
        ... />

    <EditText
        android:id="@+id/firstName"
        ... />
    
    <Button
        android:id="@+id/btnSubmit"
        ... />

    <RadioGroup
        android:id="@+id/gender"
        ... >

        <RadioButton
            android:id="@+id/male"
            ... />

        <RadioButton
            android:id="@+id/female"
            ... />
    </RadioGroup>

    <CheckBox
        android:id="@+id/employee"
        ... />

    <TextView
        android:id="@+id/txt_age"
        ... />
</RelativeLayout>
```

**Activity / Fragment**
```java

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    
		User user = new User();
		user.setUserId("u1");
		user.setFirstName("tharanga");
		user.setGender("male");
		user.setEmployee(true);
		user.setAge(10);
				
		ModelParser modelParser = new ModelParser(MainActivity.this, user);
		modelParser.parser();
		
	}
	
	.....
}

```

 
## HTTP REST Client
**Config the Server Path in AndroidMenifest.xml**

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="org.cdap.ridetrust" >
    ...
    <application>

        <meta-data
            android:name="server_base_url"
            android:value="http://server_url" />

    </application>

</manifest>
```

**Access the Service - Send Basic Value Pair**

```java
List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
nameValuePairs.add(new BasicNameValuePair("userId", appSession.getUserId()));

LegoService legoService = new LegoService(getActivity());
legoService.setOnServiceTask(new OnServiceTask() {
     @Override
     public void onBeforeExecute() {
          ...
     }

     @Override
     public void onAfterExecute(JSONObject jsonObject) {
          ...
     }
});
legoService.makeRequest("/user",nameValuePairs, RequestMethod.GET);

```

**Access the Service - Send JSON Object**

```java
JSONObject request = new JSONObject();
request.put("passengerId",userId);
request.put("date",date);
request.put("time",time);
request.put("paymentType",paymentType);
request.put("route",new JSONArray(routesData).getJSONObject(selectedIndex));

LegoService legoService = new LegoService(getActivity());
legoService.setOnServiceTask(new OnServiceTask() {
     @Override
     public void onBeforeExecute() {
          ...
     }

     @Override
     public void onAfterExecute(JSONObject jsonObject) {
          ...
     }
});
legoService.makeRequest("/ride/find_matching_rides",request, RequestMethod.POST);

```
## ORM Kit

##License
LegoRoid is under [Apache 2 license](http://www.apache.org/licenses/LICENSE-2.0.html).
