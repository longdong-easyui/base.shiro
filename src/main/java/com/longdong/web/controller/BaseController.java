package com.longdong.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSON;
import com.longdong.web.editor.IntegerEditor;




public class BaseController{
	
	
	private static final Logger logger = Logger.getLogger(BaseController.class);
	
	
	@InitBinder  
	protected void initBinder(WebDataBinder binder) {  
			 binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
			 binder.registerCustomEditor(int.class,new IntegerEditor()); 
	}  
	
	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 */
	public void writeJson(Object object,HttpServletResponse response) {
		try {
			String json;
			
			//json = JSON.toJSONString(object,SerializerFeature.WriteDateUseDateFormat);
			json = JSON.toJSONStringWithDateFormat(object,"yyyy-MM-dd");
			
			logger.info("response json:" + json);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

	
}
