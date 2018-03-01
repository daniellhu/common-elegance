package com.yonyou.cloud.common.service.utils;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xiaoleilu.hutool.util.ReflectUtil;


/** 
 * 实体类相关工具类 
 * 快速注入 create_date create_by update_by
 * 
 * @author BENJAMIN
 *
 */
public class EntityUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(EntityUtils.class);
	
	/**
	 * 快速将bean的createBy、createDate、updateBy、updateDate附上相关值
	 * 
	 */
	public static <T> void setCreatAndUpdatInfo(T entity) {
		setCreateInfo(entity);
		setUpdatedInfo(entity);
	}
	
	/**
	 * 快速将bean的createBy、createDate附上相关值
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public static <T> void setCreateInfo(T entity) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		//防止新建线程获取不到原线程中的数据
		if(requestAttributes!=null) {
			HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
			String hostIp = "";
			String name = "";
			String id = "";
			if(request!=null) {
				hostIp = String.valueOf(request.getHeader("userhost"));
				name = String.valueOf(request.getHeader("username"));
				try {
					name = URLDecoder.decode(name,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error("userName 转换失败",e);
				}
				id = String.valueOf(request.getHeader("userid"));
			}
			// 默认属性
			String[] fields = {"createBy","createDate"};
			Field field = ReflectUtil.getField(entity.getClass(), "createDate");
			// 默认值
			Object [] value = null;
			if(field!=null&&field.getType().equals(Date.class)){
				value = new Object []{id,new Date()};
			}
			// 填充默认属性值
			setDefaultValues(entity, fields, value);
		}
	}

	/**
	 * 快速将bean的updateBy、updateDate附上相关值
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public static <T> void setUpdatedInfo(T entity) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		//防止新建线程获取不到原线程中的数据
		if(requestAttributes!=null) {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			String hostIp = "";
			String name = "";
			String id = "";
			if(request!=null) {
				hostIp = String.valueOf(request.getHeader("userhost"));
				name = String.valueOf(request.getHeader("username"));
				try {
					name = URLDecoder.decode(name,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error("userName 转换失败",e);
				}
				id = String.valueOf(request.getHeader("userid"));
			}
			// 默认属性
			String[] fields = {"updateBy","updateDate"};
			Field field = ReflectUtil.getField(entity.getClass(), "updateDate");
			Object [] value = null;
			if(field!=null&&field.getType().equals(Date.class)){
				value = new Object []{id,new Date()};
			}
			// 填充默认属性值
			setDefaultValues(entity, fields, value);
		}
	}
	/**
	 * 依据对象的属性数组和值数组对对象的属性进行赋值
	 * 
	 * @param entity 对象
	 * @param fields 属性数组
	 * @param value 值数组
	 */
	private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
		for(int i=0;i<fields.length;i++){
			String field = fields[i];
//			if(ReflectUtil.hasField(entity, field)){
//				ReflectionUtils.invokeSetter(entity, field, value[i]);
//			}
			if(ReflectUtil.getField(entity.getClass(), field)!=null){
				ReflectUtil.setFieldValue(entity, field, value[i]);
			}
		}
	}
	
	
	/**
	 * 根据主键属性，判断主键是否值为空
	 * 
	 */
	public static <T> boolean isPKNotNull(T entity,String field){
		if(ReflectUtil.getField(entity.getClass(), field)!=null) {
			return false;
		}
		Object value = ReflectUtil.getFieldValue(entity, field);
		return value!=null&&!"".equals(value);
	}
}
