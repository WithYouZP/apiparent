package com.zp.api.common.persistence.refect;

import com.zp.api.common.exception.BusinessException;
import com.zp.api.common.exception.DaoException;
import com.zp.api.common.persistence.annotation.Column;
import com.zp.api.common.persistence.annotation.Id;
import com.zp.api.common.persistence.annotation.Null;
import com.zp.api.common.persistence.annotation.Table;
import com.zp.api.common.util.ObjectUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class RefectBudiler {

	public static String getTable(Class<?> clz) {
		if (!clz.isAnnotationPresent(Table.class)) {
			throw new DaoException("entity must be table.");
		}
		Table table = clz.getAnnotation(Table.class);
		String tableName = table.name();
		return tableName;
	}

	public static String getId(Class<?> clz) {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(clz);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		String id = null;
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String propertyName = descriptor.getName();
			if (propertyName.equals("class")) {
				continue;
			}
			Method readMethod = descriptor.getReadMethod();
			if (!readMethod.isAnnotationPresent(Id.class)) {
				continue;
			}
			Column column = readMethod.getAnnotation(Column.class);
			id = column.name();
			break;
		}
		if (ObjectUtils.isEmpty(id)) {
			throw new DaoException("id annotation must be in entity.");
		}
		return id;
	}

	public static void isNull(Object obj) {
		Class<?> clz = obj.getClass();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(clz);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String descriptorName = descriptor.getName();
			if (Objects.equals(descriptorName, "class")) {
				continue;
			}
			Method readMethod = descriptor.getReadMethod();
			try {
				if (!readMethod.isAnnotationPresent(Null.class)) {
					continue;
				}
				Object destValue = readMethod.invoke(obj, new Object[0]);
				if (ObjectUtils.isNotEmpty(destValue)) {
					continue;
				}
				Null validNull = readMethod.getAnnotation(Null.class);
				String desc = validNull.desc();
				throw new BusinessException(desc);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}
