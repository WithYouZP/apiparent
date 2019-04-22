package com.zp.api.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.beanutils.PropertyUtilsBean;

public abstract class ObjectUtils {

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length() == 0;
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		if (obj instanceof Collection) {
			return ((List) obj) == null || ((List) obj).size() == 0;
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		return false;
	}

	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	public static boolean isNotEmpty(Object[] array) {
		return !isEmpty(array);
	}

	public static boolean isArray(Object obj) {
		return (obj != null && obj.getClass().isArray());
	}

	public static <A, O extends A> A[] addObjectToArray(A[] array, O obj) {
		Class<?> compType = Object.class;
		if (array != null) {
			compType = array.getClass().getComponentType();
		} else if (obj != null) {
			compType = obj.getClass();
		}
		int newArrLength = (array != null ? array.length + 1 : 1);
		@SuppressWarnings("unchecked")
		A[] newArr = (A[]) Array.newInstance(compType, newArrLength);
		if (array != null) {
			System.arraycopy(array, 0, newArr, 0, array.length);
		}
		newArr[newArr.length - 1] = obj;
		return newArr;
	}

	public static boolean equals(Object a, Object b) {
		if (Objects.equals(a, b)) {
			return true;
		}

		if (a.getClass().isArray() && b.getClass().isArray()) {
			return arrayEquals(a, b);
		}
		return false;
	}

	public static boolean equalsIgnoreCase(String a, String b) {
		if (equals(a, b)) {
			return true;
		}
		return a.equalsIgnoreCase(b);
	}

	private static boolean arrayEquals(Object o1, Object o2) {
		if (o1 instanceof Object[] && o2 instanceof Object[]) {
			return Arrays.equals((Object[]) o1, (Object[]) o2);
		}
		if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
			return Arrays.equals((boolean[]) o1, (boolean[]) o2);
		}
		if (o1 instanceof byte[] && o2 instanceof byte[]) {
			return Arrays.equals((byte[]) o1, (byte[]) o2);
		}
		if (o1 instanceof char[] && o2 instanceof char[]) {
			return Arrays.equals((char[]) o1, (char[]) o2);
		}
		if (o1 instanceof double[] && o2 instanceof double[]) {
			return Arrays.equals((double[]) o1, (double[]) o2);
		}
		if (o1 instanceof float[] && o2 instanceof float[]) {
			return Arrays.equals((float[]) o1, (float[]) o2);
		}
		if (o1 instanceof int[] && o2 instanceof int[]) {
			return Arrays.equals((int[]) o1, (int[]) o2);
		}
		if (o1 instanceof long[] && o2 instanceof long[]) {
			return Arrays.equals((long[]) o1, (long[]) o2);
		}
		if (o1 instanceof short[] && o2 instanceof short[]) {
			return Arrays.equals((short[]) o1, (short[]) o2);
		}
		return false;
	}

	public static Object[] toObjectArray(Object source) {
		if (source instanceof Object[]) {
			return (Object[]) source;
		}
		if (source == null) {
			return new Object[0];
		}
		if (!source.getClass().isArray()) {
			throw new IllegalArgumentException("Source is not an array: " + source);
		}
		int length = Array.getLength(source);
		if (length == 0) {
			return new Object[0];
		}
		Class<?> wrapperType = Array.get(source, 0).getClass();
		Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
		for (int i = 0; i < length; i++) {
			newArray[i] = Array.get(source, i);
		}
		return newArray;
	}

	public static List<?> arrayToList(Object source) {
		return Arrays.asList(ObjectUtils.toObjectArray(source));
	}

	public static String convertToString(Object obj) {
		if (ObjectUtils.isNotEmpty(obj)) {
			return String.valueOf(obj);
		}
		return "";
	}

	public static Integer convertToInteger(Object obj) {
		if (ObjectUtils.isNotEmpty(obj)) {
			return Integer.valueOf(convertToString(obj));
		}
		return null;
	}

	public static Double convertToDouble(Object obj) {
		if (ObjectUtils.isNotEmpty(obj)) {
			return Double.valueOf(convertToString(obj));
		}
		return null;
	}

	public static Long convertToLong(Object obj) {
		if (ObjectUtils.isNotEmpty(obj)) {
			return Long.valueOf(convertToString(obj));
		}
		return null;
	}

	public static boolean convertToBoolean(String str) {
		if (ObjectUtils.isNotEmpty(str)) {
			return Boolean.valueOf(str);
		}
		return false;
	}

	public static Short convertToShort(Object obj) {
		if (ObjectUtils.isNotEmpty(obj)) {
			return Short.valueOf(convertToString(obj));
		}
		return null;
	}

	public static BigDecimal convertToBigDecimal(Object obj) {
		if (ObjectUtils.isNotEmpty(obj)) {
			return new BigDecimal(convertToString(obj));
		}
		return null;
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("windows");
	}

	private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
	private final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
			"狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };

	public static String getConstellation(int month, int day) {
		return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
	}
	
	public final static String[] ZODIACS = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊",
            "猴", "鸡", "狗", "猪" }; 
	
	public static String getZodiac(Date birthDate) {
		int year = DateUtils.getDate(birthDate, ChronoField.YEAR);
		int start = 1900;
		if (year < 1900){
			return "未知";
		}
		return ZODIACS[(year - start) % ZODIACS.length];
	}

	/**对象转map
	 * @param obj
	 * @return
	 */
	public static Map<Object, Object> beanToMap(Object obj) { 
        Map<Object, Object> params = new HashMap<Object, Object>(0); 
        try { 
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
            for (int i = 0; i < descriptors.length; i++) { 
                String name = descriptors[i].getName(); 
                if (!"class".equals(name)) { 
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                } 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return params; 
}

	
	
	
	
	
	
	
	
}
