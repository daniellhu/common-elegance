package com.yonyou.cloud.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.xiaoleilu.hutool.util.NumberUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.yonyou.cloud.common.beans.PageResultResponse;
import com.yonyou.cloud.common.beans.RestResultResponse;
import com.yonyou.cloud.common.beans.ResultBean;
import com.yonyou.cloud.common.exception.BizException;

/**
 * 代理所有API的统一返回
 * 
 * @author BENJAMIN
 *
 */
@Component
@Aspect
@Order(5)
public class ApiAspect {
	private Logger logger = LoggerFactory.getLogger(ApiAspect.class);

	@Around("@annotation(com.yonyou.cloud.common.annotation.YcApi)")
	public Object logServiceAccess(ProceedingJoinPoint pjp) throws Throwable {
		// 开始时间 记录执行时间
		long start = System.currentTimeMillis(); 

		/**
		 * 记录切面的信息
		 */
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String fullMethodName = className + "." + methodName;

		logger.info(fullMethodName + "将被调用");

		Object result = null;
		result = pjp.proceed();
		// 执行按成，rest设置调用成功
		if (result instanceof ResultBean) {
			((ResultBean) result).setResultCode(ResultBean.SUCCESS);
		}

		long end = System.currentTimeMillis();
		long elapsedMilliseconds = end - start;
		
		
		

		if (result != null && result instanceof ResultBean) {
			((ResultBean) result).setElapsedMilliseconds(elapsedMilliseconds);
		}
		
		logger.info(fullMethodName + "执行耗时:" + elapsedMilliseconds + " 毫秒");
		logger.info("执行结果:" + result );

		return result;
	}

	// private Map<String, Object> getFieldsName(Class cls, String clazzName,
	// String methodName, Object[] args)
	// throws NotFoundException {
	// Map<String, Object> map = new HashMap<String, Object>();
	//
	// ClassPool pool = ClassPool.getDefault();
	// // ClassClassPath classPath = new ClassClassPath(this.getClass());
	// ClassClassPath classPath = new ClassClassPath(cls);
	// pool.insertClassPath(classPath);
	//
	// CtClass cc = pool.get(clazzName);
	// CtMethod cm = cc.getDeclaredMethod(methodName);
	// MethodInfo methodInfo = cm.getMethodInfo();
	// CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
	// LocalVariableAttribute attr = (LocalVariableAttribute)
	// codeAttribute.getAttribute(LocalVariableAttribute.tag);
	// if (attr == null) {
	// // exception
	// }
	// // String[] paramNames = new String[cm.getParameterTypes().length];
	// int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
	// for (int i = 0; i < cm.getParameterTypes().length; i++) {
	// map.put(attr.variableName(i + pos), args[i]);// paramNames即参数名
	// }
	//
	// // Map<>
	// return map;
	// }
}