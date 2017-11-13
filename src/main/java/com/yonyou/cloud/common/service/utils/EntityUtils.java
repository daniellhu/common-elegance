package com.yonyou.cloud.common.service.utils;


import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.ReflectionUtils;
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
	 * 
	 */
	public static <T> void setCreateInfo(T entity){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String hostIp = "";
		String name = "";
		String id = "";
		if(request!=null) {
			hostIp = String.valueOf(request.getHeader("userHost"));
			name = String.valueOf(request.getHeader("userName"));
			name = URLDecoder.decode(name);
			id = String.valueOf(request.getHeader("userId"));
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

	/**
	 * 快速将bean的updateBy、updateDate附上相关值
	 * 
	 */
	public static <T> void setUpdatedInfo(T entity){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String hostIp = "";
		String name = "";
		String id = "";
		if(request!=null) {
			hostIp = String.valueOf(request.getHeader("userHost"));
			name = String.valueOf(request.getHeader("userName"));
			name = URLDecoder.decode(name);
			id = String.valueOf(request.getHeader("userId"));
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
