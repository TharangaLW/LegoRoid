package com.lw.legoroid.test;

import com.lw.legoroid.legoparser.UI.CustomUI;

public class User {
	
	private String userId;
	
	private String firstName;
	
	private String gender;
	
	private boolean employee;
	
	@CustomUI(ui="txt_age")
	private int age;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public boolean isEmployee() {
		return employee;
	}
	public void setEmployee(boolean employee) {
		this.employee = employee;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
	
	
	
	
	
}
