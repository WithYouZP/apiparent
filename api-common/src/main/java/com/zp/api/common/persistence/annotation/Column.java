package com.zp.api.common.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Title: Column.java
 *
 *  @author zp
 *
 *  Description: 自定义字段名注解，实体类与数据库字段名映射
 *
 *  Date:2017年11月30日
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)  
public @interface Column {
	String name() default "";
}
