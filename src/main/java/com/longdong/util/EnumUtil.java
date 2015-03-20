package com.longdong.util;




/**
 * 
 * @author john
 *
 */
public class EnumUtil {
	
	 /**
	  * 状态枚举
	  * @author john
	  *
	  */
	 public static enum RETURN_JSON_STATUS {
		 	SUCCESS(0, "成功"), FAILURE(1,"失败");
			public int key;
			public String value;
			private RETURN_JSON_STATUS(int key, String value) {
				this.key = key;
				this.value = value;
			}
			public static RETURN_JSON_STATUS get(int key) {
				RETURN_JSON_STATUS[] values = RETURN_JSON_STATUS.values();
				for (RETURN_JSON_STATUS object : values) {
					if (object.key == key) {
						return object;
					}
				}
				return null;
			}
			
		}
	 	
	 
}
