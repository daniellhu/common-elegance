package com.yonyou.cloud.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 标记会被框架Around的API
 * 
 * @author BENJAMIN
 *
 */
@Target(ElementType.METHOD)
public @interface Monitor{

}
