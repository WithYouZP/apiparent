package com.zp.api.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class WechartUtils {

	public static String getSign(Map<String, Object> parameter,String app_key) {
		List<String> keys = new ArrayList<>(parameter.keySet());
		Collections.sort(keys);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = ObjectUtils.convertToString(parameter.get(key));
			sb.append(buildKeyValue(key, value, false)).append("&");
		}
		String signTemp = sb.append("key=").append(app_key).toString();
		String sign = MD5.encryptToUp(signTemp);
		return sign;
	}

	/**
	 * 拼接键值对
	 * 
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
