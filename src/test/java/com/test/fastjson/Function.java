package com.test.fastjson;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;

public class Function implements JSONAware {
	private String functionString;

	public Function() {
	}

	public Function(String functionString) {
		this.functionString = functionString;
	}

	@Override
	public String toJSONString() {
		return this.functionString;
	}

	public String getFunctionString() {
		return functionString;
	}

	public void setFunctionString(String functionString) {
		this.functionString = functionString;
	}

	public static void main(String[] args) {
		Function click = new Function("function(){alert(1);}");

		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("name", "tony");
		json.put("click", click);
		String jsonString = JSON.toJSONString(json);
		System.out.println(jsonString);
	}
}
