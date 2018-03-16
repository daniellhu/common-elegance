package com.yonyou.cloud.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.yonyou.cloud.common.performance.PerformanceMonitor;

/**
 * 性能监控
 * 
 * @author BENJAMIN
 *
 */
@Component
@Aspect
@Order(6)
public class MonitorAspect {
	@Around("@annotation(com.yonyou.cloud.common.annotation.Monitor)")
	public Object logServiceAccess(ProceedingJoinPoint pjp) throws Throwable {
		/**
		 * 记录切面的信息
		 */
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String fullMethodName = className + "." + methodName;

		PerformanceMonitor.begin(fullMethodName);

		Object result = null;
		result = pjp.proceed();
		
		PerformanceMonitor.end();
		
		return result;
	}
}