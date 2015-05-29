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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Parse data from UI to Model Object
 * @author Tharanga
 *
 */
public class UIParser {

	private Object contextObject;
	private Object resultObject;
	private Class<?> modelClass;
	private final String LOG_CAT = "parser_ui_to_model";
	private Class<?> R_ID;

	/**
	 * For Activity
	 * @param contextObject
	 * @param classModel
	 */
	public UIParser(Object contextObject, Class<?> modelClass) {

		try {
			this.contextObject = contextObject;
			this.modelClass = modelClass;
			resultObject = modelClass.newInstance();
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
	public UIParser(Object contextObject, Class<?> modelClass , View rootView) {

		try {
			this.contextObject = contextObject;
			this.modelClass = modelClass;
			resultObject = modelClass.newInstance();
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
	public Object parse() {

		try {
			
			//Object of Activity Class
			Class<?> contextClass = contextObject.getClass();
			Method findViewById = contextClass.getMethod("findViewById",
					new Class[] { int.class });
			
			//access Model class fields
			for (Field fieldModel : modelClass.getDeclaredFields()) {
				
				//set access true
				fieldModel.setAccessible(true);
				
				Field fieldUI = checkUIFieldWithModelField(fieldModel);
				
				if(fieldUI != null){
					Log.d(LOG_CAT, "Field : " + fieldUI.getName()
							+ " ,Value : " + fieldUI.get(null));
					
					//invoke - Activity.findViewById(R.id.<element_id>)
					View view = (View) findViewById.invoke(contextObject,
							(Integer) fieldUI.get(null));
					
					getValuesFromUI(view, fieldModel);
					
				}else{
					Log.e(LOG_CAT, "Unable to find suitable UI element");
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(LOG_CAT, e.getMessage());
		}

		return resultObject;
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
	 * Get values from UI
	 * @param view
	 * @param fieldRId
	 * @throws Exception
	 */
	private void getValuesFromUI(View view, Field fieldModel) throws Exception{

		String ui = view.getClass().getSimpleName();
		String fieldName = fieldModel.getName();
		
		if(UIControllers.EditText.equals(ui)){
			
			EditText converView = (EditText) view;
			Log.d(LOG_CAT, UIControllers.EditText.toString()
					+ " | value : " + converView.getText().toString());
			setValuesToModel(fieldName, converView.getText().toString());
			
		}else if(UIControllers.TextView.equals(ui)){
			
			TextView converView = (TextView) view;
			Log.d(LOG_CAT, UIControllers.TextView.toString()
					+ " | value : " + converView.getText().toString());
			setValuesToModel(fieldName, converView.getText().toString());
			
		}else if(UIControllers.RadioGroup.equals(ui)){
			
			RadioGroup converView = (RadioGroup) view;
			int selectedId = converView.getCheckedRadioButtonId();
			
			Class<?> classObj = contextObject.getClass();
			Method methodFindViewById = classObj.getMethod("findViewById",new Class[] { int.class });
			
			RadioButton radioButton = (RadioButton)methodFindViewById.invoke(contextObject,selectedId);
			
			setValuesToModel(fieldName, radioButton.getText().toString());
			
		}else if(UIControllers.CheckBox.equals(ui)){
			
			boolean checked = ((CheckBox) view).isChecked();
			setValuesToModel(fieldName, checked);
			
		}
		
		
		else{
			Log.e(LOG_CAT, "Not found any supported UI");
		}
		

	}

	/**
	 * Set value for a field
	 * @param fieldName
	 * @param fieldValue
	 */
	private void setValuesToModel(String fieldName, Object fieldValue) {

		try {
			
			Field field = modelClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			
			if(Integer.class.getSimpleName().equals(field.getType().getSimpleName()) || int.class.getSimpleName().equals(field.getType().getSimpleName())){
				
				field.setInt(resultObject, Integer.valueOf(fieldValue.toString()));
				
			}else if(Double.class.getSimpleName().equals(field.getType().getSimpleName()) || double.class.getSimpleName().equals(field.getType().getSimpleName())){
				
				field.setDouble(resultObject, Double.valueOf(fieldValue.toString()));
				
			}else if(Float.class.getSimpleName().equals(field.getType().getSimpleName()) || float.class.getSimpleName().equals(field.getType().getSimpleName())){
				
				field.setFloat(resultObject, Float.valueOf(fieldValue.toString()));
				
			}else{
				field.set(resultObject, fieldValue);
			}
			
			
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(LOG_CAT, e.getMessage());
		}

	}
	
}
