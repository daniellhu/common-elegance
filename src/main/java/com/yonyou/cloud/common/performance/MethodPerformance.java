package com.yonyou.cloud.common.performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.cloud.common.annotation.Monitor;

/**
 * 方法性能监控
 * 
 * @author BENJAMIN
 *
 */
public class MethodPerformance {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private long begin;
	
	private long end;
	
	private String serviceMethod;
	
	
	/**
	 * 构造函数
	 * 传入监控的方法名
	 * 
	 * @param serviceMethod
	 */
	public MethodPerformance(String serviceMethod) {
		this.serviceMethod=serviceMethod;
		this.begin=System.currentTimeMillis();
	}
	
	/**
	 * 打印执行情况
	 * 
	 */
	public void printPerformance() {
		end = System.currentTimeMillis();
		long elapse = end - begin;
		logger.info(serviceMethod+"执行花费了"+elapse+"ms。");
	}

}
