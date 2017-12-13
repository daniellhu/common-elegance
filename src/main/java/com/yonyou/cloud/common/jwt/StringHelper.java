package com.yonyou.cloud.common.jwt;

public class StringHelper {
    public static String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }
    public static boolean isNullOrEmpty(String str){
    	if(str==null || "".equals(str))
    		return true;
    	return false;
    }
}
