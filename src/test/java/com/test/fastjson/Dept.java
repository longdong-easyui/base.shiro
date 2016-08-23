package com.test.fastjson;

import java.util.List;

public class Dept {


	private String id;
	private String name;
	private List<Employee> emps;
	

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

	public List<Employee> getEmps() {
		return emps;
	}

	public void setEmps(List<Employee> emps) {
		this.emps = emps;
	}

	public Dept(String id, String name, List<Employee> emps) {
		super();
		this.id = id;
		this.name = name;
		this.emps = emps;
	}
	
	public Dept(){}
	
	
}
