package com.zp.api.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.zp.api.common.persistence.annotation.Column;
import com.zp.api.common.persistence.annotation.FieldMapping;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
/**
 * Title: BeanUtils.java
 *
 * @author zp
 *
 *         Description: javaBean与Map数据结构相互转化工具类
 *
 *         Date:2018年1月12日
 */
public abstract class BeanUtils {

	/**
	 * 将map转换为数据库映射对象，首先根据对象中的属性的column注解匹配，如果没有column注解，则根据属性名称匹配
	 * 
	 * @param clz
	 * @param map
	 * @return
	 */
	public static <E> E convertMap(Class<E> clz, Map<String, Object> map) {
		E entity = null;
		try {
			Field[] fields = getFields(clz);
			entity = clz.newInstance();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String columnName = entry.getKey();
				Object columnValue = entry.getValue();
				if (ObjectUtils.isEmpty(columnValue)){
					continue;
				}
				for (Field field : fields) {
					String fieldName = field.getName();
					if (ObjectUtils.equals(fieldName, "serialVersionUID")) {
						continue;
					}
					PropertyDescriptor descriptor = null;
					try {
						descriptor = new PropertyDescriptor(fieldName, clz);
					} catch (IntrospectionException e) {
					}
					if (ObjectUtils.isEmpty(descriptor)){
						continue;
					}
					String propertyName = descriptor.getName();
					if (propertyName.equals("class")){
						continue;
					}
					Method method = descriptor.getReadMethod();
					if (!method.isAnnotationPresent(Column.class)) {
						if (fieldName.equalsIgnoreCase(columnName)) {
							org.apache.commons.beanutils.BeanUtils.setProperty(entity, fieldName, columnValue);
							break;
						}
						continue;
					}
					Column column = method.getAnnotation(Column.class);
					if (column.name().equalsIgnoreCase(columnName) || fieldName.equalsIgnoreCase(columnName)) {
						org.apache.commons.beanutils.BeanUtils.setProperty(entity, fieldName, columnValue);
						break;
					}
				}
			}
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return entity;
	}

	private static Field[] getFields(Class<?> clz) {
		Field[] fields = clz.getDeclaredFields();
		Class<?> superClass = clz.getSuperclass();
		Field[] superFields = superClass.getDeclaredFields();
		Field[] tmp = Arrays.copyOf(fields, fields.length + superFields.length);
		System.arraycopy(superFields, 0, tmp, fields.length, superFields.length);
		return tmp;
	}

	/**
	 * javaBean属性值复制
	 * 
	 * @param dest
	 *            源
	 * @param target
	 *            目标
	 */
	public static void copyProperties(Object dest, Object target){
		Class<?> destClass = dest.getClass();
		Class<?> targetClass = target.getClass();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(destClass);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		Field[] targetFields = getFields(targetClass);
		ConvertUtils.register(new DateConverter(null), Date.class);
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			String destName = descriptor.getName();
			if (Objects.equals(destName, "class")) {
				continue;
			}
			Method readMethod = descriptor.getReadMethod();
			Object destValue = null;
			try {
				destValue = readMethod.invoke(dest, new Object[0]);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if (ObjectUtils.isEmpty(destValue)) {
				continue;
			}
			if (!readMethod.isAnnotationPresent(FieldMapping.class)) {
				copyProperties(targetFields, destName, target, destValue);
				continue;
			}
			FieldMapping beanMapping = readMethod.getAnnotation(FieldMapping.class);
			String beanName = beanMapping.name();
			copyProperties(targetFields, beanName, target, destValue);
		}
	}

	private static void copyProperties(Field[] targetFields, String name, Object target, Object destValue) {
		for (Field targetField : targetFields) {
			String targetName = targetField.getName();
			if (Objects.equals(targetName, "class")) {
				continue;
			}
			if (Objects.equals(targetName, name)) {
				try {
					org.apache.commons.beanutils.BeanUtils.setProperty(target, targetName, destValue);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	/**
	 * 将一个Map转化为JavaBean
	 * 
	 * @param clz
	 * @param maps
	 * @return
	 */
	public static <E> List<E> convertMap(Class<E> clz, List<Map<String, Object>> maps) {
		if (ObjectUtils.isEmpty(maps)) {
			return new ArrayList<E>();
		}
		List<E> entities = new ArrayList<E>(maps.size());
		for (Map<String, Object> map : maps) {
			E e = (E) convertMap(clz, map);
			entities.add(e);
		}
		return entities;
	}

	/**
	 * 根据属性名称反射获取值
	 * 
	 * @param name
	 * @param o
	 * @return
	 */
	public static Object getObjectValue(String name, Object o) {
		Class<?> c = o.getClass();
		Object result = null;
		try {
			PropertyDescriptor descriptor = new PropertyDescriptor(name, c);
			Method method = descriptor.getReadMethod();
			try {
				result = method.invoke(o);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 */
	public static Map<String, Object> convertBean(Object bean) {
		Class<?> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(type);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				convertBean(returnMap, readMethod, propertyName, bean);
			}
		}
		return returnMap;
	}

	/**
	 * 将javaBean转为map，map的key为javaBean的column的注解name值
	 * 
	 * @param bean
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map<String, Object> convertColumn(Object bean) {
		Class<?> type = bean.getClass();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(type);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		Map<String, Object> returnMap = new HashMap<String, Object>(propertyDescriptors.length);
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (propertyName.equals("class")) {
				continue;
			}
			Method readMethod = descriptor.getReadMethod();
			if (!readMethod.isAnnotationPresent(Column.class)) {
				continue;
			}
			Column column = readMethod.getAnnotation(Column.class);
			String key = column.name();
			convertBean(returnMap, readMethod, key, bean);
		}
		return returnMap;
	}

	private static void convertBean(Map<String, Object> returnMap, Method readMethod, String key, Object obj) {
		try {
			Object result = readMethod.invoke(obj, new Object[0]);
			if (result != null) {
				returnMap.put(key, result);
			} else {
				returnMap.put(key, null);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static <E> List<Map<String, Object>> convertColumns(List<E> datas) {
		List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>(datas.size());
		try {
			for (E entity : datas) {
				Map<String, Object> dataMap = convertColumn(entity);
				dataMaps.add(dataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return dataMaps;
	}

	public static Map<String, Object> convert(Object bean) {
		if (ObjectUtils.isEmpty(bean)) {
			return new HashMap<String, Object>();
		}
		Class<?> type = bean.getClass();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(type);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		Map<String, Object> returnMap = new HashMap<String, Object>(propertyDescriptors.length);
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (propertyName.equals("class")) {
				continue;
			}
			Method readMethod = descriptor.getReadMethod();
			convert(returnMap, readMethod, propertyName, bean);
		}
		return returnMap;
	}
	
	private static void convert(Map<String, Object> returnMap, Method readMethod, String key, Object obj) {
		try {
			Object result = readMethod.invoke(obj, new Object[0]);
			if (ObjectUtils.isNotEmpty(result)) {
				returnMap.put(key, result);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将Map的key转为小写
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> keyToLower(Map<String, Object> map) {
		Map<String, Object> lowerMap = new HashMap<String, Object>(map.size());
		for (Map.Entry<String, Object> ent : map.entrySet()) {
			String lowerKey = ent.getKey().toLowerCase();
			Object value = ent.getValue();
			lowerMap.put(lowerKey, value);
		}
		return lowerMap;
	}

	/**
	 * 将Map集合的key转为小写
	 * 
	 * @param maps
	 * @return
	 */
	public static List<Map<String, Object>> keyToLower(List<Map<String, Object>> maps) {
		List<Map<String, Object>> lowMaps = new ArrayList<Map<String, Object>>(maps.size());
		for (Map<String, Object> map : maps) {
			Map<String, Object> lowMap = keyToLower(map);
			lowMaps.add(lowMap);
		}
		return lowMaps;
	}
}
