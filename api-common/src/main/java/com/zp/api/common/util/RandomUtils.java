package com.zp.api.common.util;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

/**
 * Title: RandomUtils.java
 *
 * @author zp
 *
 *         Description: 生成随机数，序列号工具类
 *
 *         Date:2018年1月12日
 */
public abstract class RandomUtils {

	private static final String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
			"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };

	private static final ThreadLocal<DecimalFormat> decimalFormatLocal = new ThreadLocal<DecimalFormat>() {
		@Override
		protected DecimalFormat initialValue() {
			return new DecimalFormat();
		}
	};

	/**
	 * 生成随机码 length 位数
	 * 
	 * @return
	 */
	public static String generateUuid(int length) {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < length; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}

	/**
	 * 生成随机数字
	 * 
	 * @param length
	 *            位数,如 生成4位，则传入 1000，5位传入10000
	 * @return
	 */
	public static String generateRandom(int length) {
		int l = generateInt(length);
		Integer random = (int) ((Math.random() * 9 + 1) * l);
		return random.toString();
	}

	private static int generateInt(int length) {
		StringBuffer sb = new StringBuffer("1");
		for (int i = 0; i < length - 1; i++) {
			sb.append(0);
		}
		return ObjectUtils.convertToInteger(sb);
	}

	/**
	 * 格式化数字，不足在数字前补0
	 * 
	 * @param customer
	 *            需要格式化的数字
	 * @param length
	 *            格式化长度
	 * @return
	 */
	public static String generateCustomer(int customer, int length) {
		DecimalFormat decimalFormat = decimalFormatLocal.get();
		String pattern = generatePattern(length);
		decimalFormat.applyPattern(pattern);
		return decimalFormat.format(customer);
	}

	public static String generatePattern(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(0);
		}
		return sb.toString();
	}
	
	public static String getNo(String key) {
		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 20);
		return key;
	}
}
