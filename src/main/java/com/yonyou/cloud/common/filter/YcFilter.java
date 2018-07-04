package com.yonyou.cloud.common.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.yonyou.cloud.common.vo.user.UserInfo;

/**
 * 帮助服务将header中的用户信息放到threadlocal中 同时也放到MDC中方便logback打印日志
 * 
 * @author BENJAMIN
 *
 */
@WebFilter(urlPatterns = "/*", filterName = "YcFilter")
public class YcFilter implements Filter {
	Logger log = LoggerFactory.getLogger(YcFilter.class);

	/**
	 * MDC中user的key
	 */
	private static final String USER_KEY = "user";

	/**
	 * header中的userid
	 */
	private static final String HEADER_USER_ID = "userid";

	/**
	 * header中的dealerCode
	 */
	private static final String HEADER_DEALER_CODE = "dealercode";

	/**
	 * header中的dealerName
	 */
	private static final String HEADER_DEALER_NAME = "dealername";

	/**
	 * header中的TEL_PHONE
	 */
	private static final String HEADER_TEL_PHONE = "telphone";

	/**
	 * header中userName
	 */
	private static final String HEADER_USER_NAME = "username";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("init YcFilter");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		// 操作的用户id
//		String optUserId = request.getHeader(HEADER_USER_ID);
		// 操作的用户类型
		// String optUserName = ((HttpServletRequest)
		// servletRequest).getHeader(HEADER_USER_NAME);
		// 操作的用户所属经销商
//		String dealerCode = request.getHeader(HEADER_DEALER_CODE);
		// 操作的用户所属经销商
		// String dealerName = ((HttpServletRequest)
		// servletRequest).getHeader(HEADER_DEALER_NAME);
		// 操作的用户手机号
//		String telPhone = request.getHeader(HEADER_TEL_PHONE);
		
		String uri = request.getRequestURI();
		String method = request.getMethod();
		
		log.info("uri-->"+uri);
		log.info("method-->"+method);
		
		log.info("YcFilter is getting userinfo from header");
		Enumeration<String> e = ((HttpServletRequest) servletRequest).getHeaderNames();
		UserInfo user = new UserInfo();
		Map<String,String> attrMap = new HashMap<String,String>();
				
		// 遍历头部信息集
		while (e.hasMoreElements()) {
			// 取出信息名
			String name = (String) e.nextElement();
			// 取出信息值
			String value = request.getHeader(name);
			
			if(HEADER_USER_ID.equals(name)) {
				user.setId(value);
				MDC.put(USER_KEY, value);
			}else if(HEADER_TEL_PHONE.equals(name)) {
				user.setTelPhone(value);	
			}else if(HEADER_DEALER_CODE.equals(name)) {
				user.setDealerCode(value);
			}else if(HEADER_DEALER_NAME.equals(name)) {
				user.setDealerName(URLDecoder.decode(value,"UTF-8"));
			}else if(HEADER_USER_NAME.equals(name)) {
				user.setUsername(URLDecoder.decode(value,"UTF-8"));
			}else {
				attrMap.put(name, value);
			}
			
//			log.info("header name "+ name +" = " + value);
		}
		
		user.setAttrMap(attrMap);
		UserLocal.setLocalUser(user);

//		if (optUserId != null) {
//			user.setId(optUserId);
//			// user.setUsername(URLDecoder.decode(optUserName,"UTF-8"));
//			user.setTelPhone(telPhone);
//			user.setDealerCode(dealerCode);
//			// user.setDealerName(URLDecoder.decode(dealerName,"UTF-8"));
//			// 将用户信息放到threadlocal中
//			UserLocal.setLocalUser(user);
//
//			// 将用户信息放发哦slf4j中，方便日志打印
//			MDC.put(USER_KEY, optUserId);
//
//			log.info("YcFilter has set userInfo to threadlocal and log");
//		} else {
//
//			log.warn("this request has no userInfo use default");
//		}

		filterChain.doFilter(servletRequest, servletResponse);

		MDC.remove(USER_KEY);

	}

	@Override
	public void destroy() {

	}
}