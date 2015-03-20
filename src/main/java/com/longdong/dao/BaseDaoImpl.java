package com.longdong.dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class BaseDaoImpl {
	
	public static final String dateFormate = "yyyy-MM-dd";
	public static final String dateTimeFormate = "yyyy-MM-dd HH:mm:ss";
	
	
	/**
	 * 用反射获取查询条件
	 * @param obj
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 */
	public String getWhereQueryConditon(Object obj) {
		StringBuilder sb = new StringBuilder();
		sb.append(" where 1=1 ");
		try{
			Class clazz = obj.getClass();
			
			Field[] fields = clazz.getDeclaredFields();
			
			for(Field field : fields){
				PropertyDescriptor pd;
				pd = new PropertyDescriptor(field.getName(),clazz);
				
				Method getMethod = pd.getReadMethod();//获得get方法  
				Class returnClazz = getMethod.getReturnType();
				
		        Object o = getMethod.invoke(obj);//执行get方法返回一个Object  
		        
		        if(o!=null){
		        	if(returnClazz == Date.class){
						Date date = (Date)o;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
						String dateStr = sdf.format(date);
						
						sb.append(" and ").append(field.getName()).append("=").append("'"+dateStr+"'");
					}else if(returnClazz == Integer.class ){
						sb.append(" and ").append(field.getName()).append("=").append(o.toString());
					}else if(returnClazz == String.class){
						sb.append(" and ").append(field.getName()).append("=").append("'"+o.toString()+"'");
					}
		        }
			}
			
			
		}catch(Exception e){
			e.fillInStackTrace();
		}
		return sb.toString();
	}
	
}
