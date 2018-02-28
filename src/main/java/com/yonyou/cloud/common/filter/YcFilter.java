package com.yonyou.cloud.common.filter;

import java.io.IOException;

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
	private static final String HEADER_USER_ID = "userId";

	/**
	 * header中的dealerCode
	 */
	private static final String HEADER_DEALER_CODE = "dealerCode";
	
	/**
	 * header中的dealerName
	 */
	private static final String HEADER_DEALER_NAME = "dealerName";

	/**
	 * header中的TEL_PHONE
	 */
	private static final String HEADER_TEL_PHONE = "telPhone";

	/**
	 * header中userName
	 */
	private static final String HEADER_USER_NAME = "userName";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("init YcFilter");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		log.info("YcFilter is getting userinfo from header");
		// 操作的用户id
		String optUserId = ((HttpServletRequest) servletRequest).getHeader(HEADER_USER_ID);
		// 操作的用户类型
		String optUserName = ((HttpServletRequest) servletRequest).getHeader(HEADER_USER_NAME);
		// 操作的用户所属经销商
		String dealerCode = ((HttpServletRequest) servletRequest).getHeader(HEADER_DEALER_CODE);
		// 操作的用户所属经销商
		String dealerName = ((HttpServletRequest) servletRequest).getHeader(HEADER_DEALER_NAME);
		// 操作的用户手机号
		String telPhone = ((HttpServletRequest) servletRequest).getHeader(HEADER_TEL_PHONE);

		UserInfo user = new UserInfo();
		if (optUserId != null) {
			user.setId(optUserId);
			user.setUsername(optUserName);
			user.setTelPhone(telPhone);
			user.setDealerCode(dealerCode);
			user.setDealerName(dealerName);
			// 将用户信息放到threadlocal中
			UserLocal.setLocalUser(user);

			// 将用户信息放发哦slf4j中，方便日志打印
			MDC.put(USER_KEY, optUserId);

			log.info("YcFilter has set userInfo to threadlocal and log");
		} else {

			log.warn("this request has no userInfo use default");
		}

		filterChain.doFilter(servletRequest, servletResponse);

		MDC.remove(USER_KEY);

	}

	@Override
	public void destroy() {

	}
}