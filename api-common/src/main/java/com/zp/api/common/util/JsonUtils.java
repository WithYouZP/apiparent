package com.zp.api.common.util;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *  Title: JsonUtils.java
 *
 *  @author zp
 *
 *  Description: javaBean，json，Map操作工具类
 *
 *  Date:2018年1月12日
 */
public abstract class JsonUtils {
	
	private static final ThreadLocal<Gson> gsonLocal = new ThreadLocal<Gson>() {
		@Override
		protected Gson initialValue() {
			return new GsonBuilder().disableHtmlEscaping().create();
		}
	};
	public static String toJson(Object obj){
		Gson gson = gsonLocal.get();
		return gson.toJson(obj);
	}

	public static <T> T toBean(String json,Class<T> clz){
		if(ObjectUtils.isEmpty(json)){
			return null;
		}
		Gson gson = gsonLocal.get();
		return gson.fromJson(json, clz);
	}

	public static List<?> toList(String json){
		 Gson gson = gsonLocal.get();
		 return gson.fromJson(json, new TypeToken<List<?>>(){}.getType());
	}
	
	public static <K,V> Map<K, V> toMap(String json){
		 Gson gson = gsonLocal.get();
		 return gson.fromJson(json, new TypeToken<Map<K, V>>(){}.getType());
	}
}
