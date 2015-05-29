package com.lw.legoroid.ui_parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import com.lw.legoroid.R;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UIParser {

	private Object contextObject;
	private Class classModel;
	private Object objectResult;

	// Activity
	public UIParser(Object contextObject, Class classModel) {

		try {
			this.contextObject = contextObject;
			this.classModel = classModel;
			objectResult = classModel.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("PARSER_UI", e.getMessage().toString());
		}
	}

	// Fragment
	public UIParser(Object contextObject, Class classModel , View rootView) {

		try {
			this.contextObject = contextObject;
			this.classModel = classModel;
			objectResult = classModel.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("PARSER_UI", e.getMessage().toString());
		}
	}

	public Object parserFromUI() {

		try {
			Class classObj = contextObject.getClass();
			Method methodFindViewById = classObj.getMethod("findViewById",
					new Class[] { int.class });

			List<String> listFieldModels = new ArrayList<String>();

			// access Model class fields
			for (Field fieldModel : classModel.getDeclaredFields()) {

				// Log.d("PARSER_UI",fieldModel.getName());
				listFieldModels.add(fieldModel.getName());
			}

			// access R.id class fields
			Class classRId = R.id.class;

			for (Field fieldRId : classRId.getFields()) {

				if (listFieldModels.contains(fieldRId.getName())) {
					Log.d("PARSER_UI", "Field : " + fieldRId.getName()
							+ " ,Value : " + fieldRId.get(null));
					View view = (View) methodFindViewById.invoke(contextObject,
							(Integer) fieldRId.get(null));
					setValuesFromUI(view, fieldRId.getName());
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("PARSER_UI", e.getMessage().toString());
		}

		return objectResult;
	}

	private void setValuesFromUI(View view, String fieldName) {

		String ui = view.getClass().getSimpleName();

		if (ui.equals("EditText")) {
			EditText converView = (EditText) view;
			Log.d("PARSER_UI", UIControllers.EditText.toString()
					+ " | value : " + converView.getText().toString());
			setValuesToModel(fieldName, converView.getText().toString());

		} else if (ui.equals("TextView")) {
			TextView converView = (TextView) view;
			Log.d("PARSER_UI", UIControllers.TextView.toString()
					+ " | value : " + converView.getText().toString());
			setValuesToModel(fieldName, converView.getText().toString());

		} else {

		}

	}

	private void setValuesToModel(String fieldName, String fieldValue) {

		try {

			Field field = classModel.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(objectResult, fieldValue);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("PARSER_UI", e.getMessage().toString());
		}

	}
}
