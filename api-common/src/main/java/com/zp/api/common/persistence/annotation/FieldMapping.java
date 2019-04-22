package com.zp.api.common.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Title: FieldMapping.java
 *
 *  @author zp
 *
 *  Description: 自定义注解，处理javaBean字段映射
 *
 *  Date:2017年11月30日
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)  
public @interface FieldMapping {

	String name() default "";
}
