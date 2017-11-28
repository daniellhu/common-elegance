package com.yonyou.cloud.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.cloud.common.vo.user.UserInfo;

public class UserLocal {
	
	private  static Logger logger = LoggerFactory.getLogger(UserLocal.class);

	 private static ThreadLocal<UserInfo> local = new ThreadLocal<UserInfo>();
	 
	 
	 public static void setLocalUser(UserInfo user){
		 local.set(user);
		 logger.info( "当前线程：" + Thread.currentThread().getName() +" 用户="+local.get() );
	 }
	 
	 public static UserInfo getLocalUser(){
		 return local.get();
	 }
	 
	 public static void removeLocalUser(){
		 local.remove();
	 }
	 
	 
}