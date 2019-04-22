package com.zp.api.common.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Title: Null.java
 *
 *  @author zp
 *
 *  Description: 自定义注解实现,非空验证
 *
 *  Date:2018年2月2日
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)  
public @interface Null {
	String desc() default "";
}
