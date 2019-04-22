package com.zp.api.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title: ToolKits.java
 *
 * @author zp
 *
 *         Description: 字符串操作类
 *
 *         Date:2018年1月12日
 */
public abstract class StringUtils {

	/**
	 * removeNullObject
	 * 
	 * @author dezhi.jin
	 * @param obj
	 * @return void 删除null对象
	 */
	@SuppressWarnings({ "rawtypes" })
	public static void removeNullObject(Object obj) {
		if (obj != null) {
			if (obj instanceof Map) {
				Map map = (Map) obj;
				Iterator iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();
					Object value = entry.getValue();
					if (value == null || "".equals(value)) {
						iterator.remove();
					} else {
						removeNullObject(value);
					}
				}
			} else if (obj instanceof List) {
				List list = (List) obj;
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object value = iterator.next();
					if (value == null) {
						iterator.remove();
					} else {
						removeNullObject(value);
					}
				}
			}
		}
	}

	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	public static boolean hasLength(String str) {
		return (str != null && !str.isEmpty());
	}

	public static boolean hasText(CharSequence str) {
		return (hasLength(str) && containsText(str));
	}

	public static boolean hasText(String str) {
		return (hasLength(str) && containsText(str));
	}

	private static boolean containsText(CharSequence str) {
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsWhitespace(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}

		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 去掉首尾的空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trimWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}

		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 去掉所有空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		int len = str.length();
		StringBuilder sb = new StringBuilder(str.length());
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if (!Character.isWhitespace(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 去掉字符串前面的空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trimLeadingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}

		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	/**
	 * 去掉字符串最后的空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trimTrailingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}

		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 判断字符串是否已某个字符开头
	 * 
	 * @param str
	 * @param prefix
	 * @return
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		return (str != null && prefix != null && str.length() >= prefix.length()
				&& str.regionMatches(true, 0, prefix, 0, prefix.length()));
	}

	/**
	 * 判断字符串是否已某个字符串结尾
	 * 
	 * @param str
	 * @param suffix
	 * @return
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		return (str != null && suffix != null && str.length() >= suffix.length()
				&& str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length()));
	}

	/**
	 * 字符串替换
	 * 
	 * @param inString
	 *            原字符串
	 * @param oldPattern
	 *            旧字符串
	 * @param newPattern
	 *            新字符串
	 * @return
	 */
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
			return inString;
		}
		int index = inString.indexOf(oldPattern);
		if (index == -1) {
			return inString;
		}

		int capacity = inString.length();
		if (newPattern.length() > oldPattern.length()) {
			capacity += 16;
		}
		StringBuilder sb = new StringBuilder(capacity);

		int pos = 0;
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}

		sb.append(inString.substring(pos));
		return sb.toString();
	}

	/**
	 * 字符串删除
	 * 
	 * @param inString
	 * @param pattern
	 *            需要删除的字符串
	 * @return
	 */
	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	/**
	 * 删除字符串中所有相同的字符
	 * 
	 * @param inString
	 * @param charsToDelete
	 * @return
	 */
	public static String deleteAny(String inString, String charsToDelete) {
		if (!hasLength(inString) || !hasLength(charsToDelete)) {
			return inString;
		}

		StringBuilder sb = new StringBuilder(inString.length());
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	/**
	 * 首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (!hasLength(str)) {
			return str;
		}

		char baseChar = str.charAt(0);
		char updatedChar;
		if (capitalize) {
			updatedChar = Character.toUpperCase(baseChar);
		} else {
			updatedChar = Character.toLowerCase(baseChar);
		}
		if (baseChar == updatedChar) {
			return str;
		}

		char[] chars = str.toCharArray();
		chars[0] = updatedChar;
		return new String(chars, 0, chars.length);
	}

	/**
	 * 截取字符串返回后半部分
	 * 
	 * @param qualifiedName
	 * @param separator
	 * @return
	 */
	public static String unqualifylast(String qualifiedName, char separator) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	/**
	 * 截取字符串返回前半部分
	 * 
	 * @param qualifiedName
	 * @param separator
	 * @return
	 */
	public static String unqualifyfirst(String qualifiedName, char separator) {
		return qualifiedName.substring(0, qualifiedName.lastIndexOf(separator));
	}

	/**
	 * 将两个字符串数组合成一个数组，并去掉其中相同的数据
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static String[] mergeStringArrays(String[] array1, String[] array2) {
		if (ObjectUtils.isEmpty(array1)) {
			return array2;
		}
		if (ObjectUtils.isEmpty(array2)) {
			return array1;
		}

		List<String> result = new ArrayList<String>();
		result.addAll(Arrays.asList(array1));
		for (String str : array2) {
			if (!result.contains(str)) {
				result.add(str);
			}
		}
		return toStringArray(result);
	}

	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return collection.toArray(new String[collection.size()]);
	}

	/**
	 * 去掉数组中数据首尾的空格
	 * 
	 * @param array
	 * @return
	 */
	public static String[] trimArrayElements(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return new String[0];
		}

		String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			String element = array[i];
			result[i] = trimWhitespace(element);
		}
		return result;
	}

	/**
	 * 去掉数组重复数组
	 * 
	 * @param array
	 * @return
	 */
	public static String[] removeDuplicateStrings(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return array;
		}

		Set<String> set = new LinkedHashSet<String>();
		for (String element : array) {
			set.add(element);
		}
		return toStringArray(set);
	}

	public static String subNum(String str) {
		if (!hasDigit(str)) {
			return str;
		}
		return splitNotNumber(str);
	}

	// 截取非数字
	public static String splitNotNumber(String content) {
		Pattern pattern = Pattern.compile("\\D+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	// 判断一个字符串是否含有数字
	public static boolean hasDigit(String content) {
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(content);
		if (m.matches())
			return true;
		return false;

	}

	
}
