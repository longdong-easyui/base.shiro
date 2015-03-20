package com.test.reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.longdong.entity.User;

public class ReflectTest {

	public static void main(String[] args) {
			StringBuilder sb = new StringBuilder();
			User user = new User();
			user.setAddress("上海市");
			user.setUsername("zhangsan");
			user.setAge(23);
			//user.setBirthday(new Date());
			Class clazz = user.getClass();
			Field[] fields = clazz.getDeclaredFields();
			try {
				
				for(Field field : fields){
					PropertyDescriptor pd;
					pd = new PropertyDescriptor(field.getName(),clazz);
					Class propClazz = pd.getPropertyType();
					Method getMethod = pd.getReadMethod();//获得get方法  
					Class returnClazz = getMethod.getReturnType();
					
			        Object o = getMethod.invoke(user);//执行get方法返回一个Object  
			        
			        System.out.println(o);  
			        if(o!=null){
			        	if(returnClazz == Date.class){
							Date d = (Date)o;
							String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(d);
							sb.append(" and ").append(field.getName()).append("=").append("'"+dateStr+"'");
						}else if(returnClazz == Integer.class ){
							sb.append(" and ").append(field.getName()).append("=").append(o.toString());
						}else if(returnClazz == String.class){
							sb.append(" and ").append(field.getName()).append("=").append("'"+o.toString()+"'");
						}
			        }
				}
				System.out.println(sb.toString());
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		
	}

}
