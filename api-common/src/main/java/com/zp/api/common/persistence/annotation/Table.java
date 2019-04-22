package com.zp.api.common.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Title: Table.java
 *
 *  @author zp
 *
 *  Description: 自定义表名注解，实体类与数据库表面映射
 *
 *  Date:2017年11月30日
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.TYPE)  
public @interface Table {
	String name() default "";
}
