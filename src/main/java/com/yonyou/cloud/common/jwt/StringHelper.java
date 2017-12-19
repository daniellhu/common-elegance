package com.yonyou.cloud.common.jwt;

/**
 * String Util
 * 
 * @author BENJAMIN
 *
 */
public class StringHelper {
	public static String getObjectValue(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	public static boolean isNullOrEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}
}
