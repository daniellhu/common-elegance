package com.yonyou.cloud.common.performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 性能监控者
 * 
 * @author BENJAMIN
 *
 */
public class PerformanceMonitor {

	private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitor.class);
	
	private static ThreadLocal<MethodPerformance> perfomarnceRecord = new ThreadLocal<MethodPerformance>();
	
	public static void begin(String method) {
		logger.info("begin monitor...");
		MethodPerformance mp = new MethodPerformance(method);
		perfomarnceRecord.set(mp);
	}
	
	public static void end() {
		logger.info("end monitor...");
		MethodPerformance mp = perfomarnceRecord.get();
		mp.printPerformance();
		perfomarnceRecord.remove();
	}
	
}
