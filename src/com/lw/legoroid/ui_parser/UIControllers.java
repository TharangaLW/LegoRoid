package com.lw.legoroid.ui_parser;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public enum UIControllers {
	
	TextView(TextView.class.getSimpleName()),
	EditText(TextView.class.getSimpleName()),
	Spinner(TextView.class.getSimpleName()),
	CheckBox(TextView.class.getSimpleName()),
	RadioButton(TextView.class.getSimpleName()),
	RadioGroup(TextView.class.getSimpleName()),
	RatingBar(TextView.class.getSimpleName()),
	SeekBar(TextView.class.getSimpleName()),
	TEST("");
	
	private final String controller;

    private UIControllers(String s) {
    	controller = s;
    }
    
    public String toString(){
        return controller;
    }
    
    public boolean equalsCheck(String otherName){
        return (otherName != null) && controller.equals(otherName);
    }
}
