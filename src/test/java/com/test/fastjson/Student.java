package com.test.fastjson;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Student {
	
	@JSONField(name="ID", ordinal = 3)  //别名命名
	private String id;
	
	@JSONField(serialize=false)
	private String name;
	
	
	@JSONField(ordinal = 1)
	private int age;
	
	@JSONField(ordinal = 2, format="yyyy-MM-dd")
	private Date birthDay;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Student(String id, String name, int age, Date birthDay) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.birthDay = birthDay;
	}

}
