package com.lw.legoroid.legoparser;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Include annotations,Enum
 * @author Tharanga
 *
 */
public class UI{
	
	@Documented
	@Target(ElementType.FIELD)
	@Inherited
	@Retention(RetentionPolicy.RUNTIME)
	/**
	 * Custom UI
	 * @author Tharanga
	 *
	 */
	public @interface CustomUI{
		String ui();
	}
	
	/**
	 * UI Controllers
	 * @author Tharanga
	 *
	 */
	public enum UIControllers {
		
		TextView(TextView.class.getSimpleName()),
		EditText(EditText.class.getSimpleName()),
		Spinner(Spinner.class.getSimpleName()),
		CheckBox(CheckBox.class.getSimpleName()),
		RadioButton(RadioButton.class.getSimpleName()),
		RadioGroup(RadioGroup.class.getSimpleName()),
		RatingBar(RatingBar.class.getSimpleName()),
		SeekBar(SeekBar.class.getSimpleName());
		
		private final String controller;

	    private UIControllers(String s) {
	    	controller = s;
	    }
	    
	    public String toString(){
	        return controller;
	    }
	    
	    public boolean equals(String otherName){
	        return (otherName != null) && controller.equals(otherName);
	    }
	}
}
