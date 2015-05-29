package com.lw.legoroid.legoparser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.lw.legoroid.legoparser.UI.CustomUI;
import com.lw.legoroid.legoparser.UI.UIControllers;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Parse data from data Model to UI
 * @author Tharanga
 *
 */
public class ModelParser {

	private Object contextObject;
	private Object modelObject;
	private final String LOG_CAT = "parser_model_to_ui";
	private Class<?> R_ID;
	/**
	 * For Activity
	 * @param contextObject
	 * @param objectModel
	 */
	public ModelParser(Object contextObject, Object modelObject) {

		try {
			this.contextObject = contextObject;
			this.modelObject = modelObject;
			setR();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(LOG_CAT, e.getMessage());
		}
	}

	/**
	 * For Fragment
	 * @param contextObject
	 * @param classModel
	 * @param rootView
	 */
	public ModelParser(Object contextObject, Object modelObject, View rootView) {

		try {
			this.contextObject = contextObject;
			this.modelObject = modelObject;
			setR();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(LOG_CAT, e.getMessage());
		}
	}
	
	/**
	 * Setting R.id class
	 */
	private void setR(){
		try{
			
			Class<?> contextClass = contextObject.getClass();
			
			Method getPackageManager = contextClass.getMethod("getPackageManager",null);
	        
			//PackageManager pm = getPackageManager();
	        PackageManager pm = (PackageManager)getPackageManager.invoke(contextObject,null);
	        
	        Method getPackageName = contextClass.getMethod("getPackageName",null);
	        
	        //pm.getPackageInfo(this.getPackageName(), 0);
	        PackageInfo pi = pm.getPackageInfo( (String)getPackageName.invoke(contextObject, null), 0);
	        
	        String packegName = pi.packageName;
	        
	        Log.d(LOG_CAT, "PackegName : " + packegName);
	        
	        R_ID = Class.forName(packegName + ".R$id");
	        
		}catch(Exception e){
			Log.e(LOG_CAT, e.getMessage());
		}
	}
	
	/**
	 * Access view of Activity and start to parse 
	 * @return
	 */
	public void parse() {

		try {

			//Object of Activity Class
			Class<?> contextClass = contextObject.getClass();
			Method findViewById = contextClass.getMethod("findViewById",
					new Class[] { int.class });
			
			//Model Class
			Class<?> modelClass = modelObject.getClass();
			
			//access Model class fields
			for (Field fieldModel : modelClass.getDeclaredFields()) {
					
				fieldModel.setAccessible(true);
				Log.d(LOG_CAT, "Field : " + fieldModel.get(modelObject));
				
				Field fieldUI = null;
				
				fieldUI = checkUIFieldWithModelField(fieldModel);
				
				if(fieldUI != null){
					Log.d(LOG_CAT, "UI : " + fieldUI.getName());
					
					//invoke - Activity.findViewById(R.id.<element_id>)
					View view = (View) findViewById.invoke(contextObject,
							(Integer) fieldUI.get(null));
					
					getValuesFromModel(view, fieldModel);
					
				}else{
					Log.e(LOG_CAT, "Unable to find suitable UI element");
				}

			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(LOG_CAT, e.getMessage());
		}

	}
	
	/**
	 * Get UI field according to Model field
	 * @param classRId
	 * @param fieldModel
	 * @return
	 */
	private Field checkUIFieldWithModelField(Field fieldModel){
		
		//access R.id class fields
		Class<?> classRId = R_ID;
		
		Field fieldUI = null;
		try{
			//check model's field name equal to UI element
			fieldUI = classRId.getDeclaredField(fieldModel.getName());
			
		}catch(NoSuchFieldException  e1){
			
			//check custom UI annotation
			for(Annotation annotation : fieldModel.getDeclaredAnnotations()){
				CustomUI uiAnnotation = (CustomUI) annotation;
		
				try{
					fieldUI = classRId.getDeclaredField(uiAnnotation.ui());
				}catch(NoSuchFieldException e2){
					//Log.e(LOG_CAT, e2.getMessage());
				}
			}
			
		}
		
		return fieldUI;
	}
	
	/**
	 * Get values from Model
	 * @param view
	 * @param fieldRId
	 * @throws Exception
	 */
	private void getValuesFromModel(View view, Field fieldModel){
		
		try{
			String ui = view.getClass().getSimpleName();
			Object fieldValue = fieldModel.get(modelObject);
			
			if(UIControllers.EditText.equals(ui)){
				
				EditText converView = (EditText) view;
				
				converView.setText(fieldValue.toString());
				
			}else if(UIControllers.TextView.equals(ui)){
				
				TextView converView = (TextView) view;
				
				converView.setText(fieldValue.toString());
				
			}else if(UIControllers.RadioGroup.equals(ui)){
				
				RadioGroup converView = (RadioGroup) view;
				
				converView.check((R_ID.getDeclaredField(fieldValue.toString())).getInt(null));
				
			}else if(UIControllers.CheckBox.equals(ui)){
				
				CheckBox converView = (CheckBox) view;
				
				converView.setChecked(true);
				
			}else{
				Log.e(LOG_CAT, "Not found any supported UI");
			}
		
		}catch(Exception e){
			Log.e(LOG_CAT, "Unable to set value for UI");
		}
	}
		
}
