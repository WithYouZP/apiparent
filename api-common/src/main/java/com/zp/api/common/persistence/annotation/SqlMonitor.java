package com.zp.api.common.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *  Title: SqlMonitor.java
 *
 *  @author zp
 *
 *  Description: 自定义注解，用于监控sql语句
 *
 *  Date:2018年1月12日
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)  
public @interface SqlMonitor {

	String value() default "";
}
