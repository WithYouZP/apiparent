package com.zp.api.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  Title: DateUtils.java
 *
 *  @author zp
 *
 *  Description: 日期处理工具类
 *  java8新提供的时间api，优化了性能，线程安全性.
 *
 *  Date:2018年1月12日
 */
public abstract class DateUtils {

	protected static Log logger = LogFactory.getLog(DateUtils.class);
	
	public static final String PATTERN_MONITOR_TIME = "yy-MM-dd HH";

	public static final String PATTERN_FULL_HOUR = "yyyy-MM-dd HH";

	public static final String HOUR = "HH";

	public static final String PATTERN_SIMPLE = "yyyy-MM-dd";

	public static final String PATTERN_MINUTES = "yyyy-MM-dd HH:mm";

	public static final String PATTERN_NORMAL = "yyyy-MM-dd HH:mm:ss";

	public static final String PATTERN_FULL = "yyyy-MM-dd HH:mm:ss S";

	public static final String PATTERN_FULL_SIMPLE = "yyyyMMddHHmmss";
	public static final String PATTERN_FULL_DAY = "yyyyMMdd";

	public static final String ORACLE_FORMAT = "YYYY-MM-DD HH24:MI:SS";

	public static final String SSSS = "yyyyMMddHHmmssSSSS";
	
	public static final String PATTERN_FULL_MINUTES = "yyyyMMddHHmm";
	
	/**
	 * 生成当前日期
	 * 带时分秒
	 * * @return
	 */
	public static Date nowDate(){
		LocalDateTime localDateTime = LocalDateTime.now();
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * 生成当前日期
	 * 不带时分秒
	 * * @return
	 */
	public static Date nowLocalDate() {
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDate localDate = localDateTime.toLocalDate();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
	}
	
	/**
	 * 生成当前日期字符串
	 * @return
	 */
	public static String now(){
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_NORMAL);
		return localDateTime.format(formatter);
	}
	
	/**
	 * 根据传入格式，获取当前日期字符串
	 * @param pattern
	 * @return
	 */
	public static String now(String pattern){
		return format(nowDate(), pattern);
	}
	
	/**
	 * 将日期字符串格式化为日期，日期格式为yyyy-MM-dd HH:mm:ss
	 * @param src
	 * @return
	 */
	public static Date parse(String src){
		if(isValidDate(src)){
			return parseDate(src, PATTERN_SIMPLE);
		}
		return parseDateTime(src,PATTERN_NORMAL);
	}
	
	/**
	 * 根据传入格式将日期字符串格式化为日期
	 * @param src
	 * @return
	 */
	public static Date parse(String src,String pattern){
		if(isValidDate(src)){
			return parseDate(src, pattern);
		}
		return parseDateTime(src, pattern);
	}
	
	private static Date parseDate(String src,String pattern) {
		DateTimeFormatter.ofPattern(pattern).getDecimalStyle();
		LocalDate localDate = LocalDate.parse(src, DateTimeFormatter.ofPattern(pattern));
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
	}
	
	private static Date parseDateTime(String src,String pattern) {
		LocalDateTime localDateTime = LocalDateTime.parse(src, DateTimeFormatter.ofPattern(pattern));
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); 
	}
	
	/**
	 * 将时间戳长度格式化为日期
	 * @param timeMills
	 * @return
	 */
	public static Date parse(Long timeMills){
		Instant instant = Instant.ofEpochMilli(timeMills);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant,ZoneId.systemDefault());
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * 获取当前日期的开始时间
	 * @return
	 */
	public static Date startOfToday() {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = LocalDate.now().atStartOfDay(zone).toInstant();
		return Date.from(instant);
	}

	/**
	 * 获取指定日期的开始时间
	 * @param date
	 * @return
	 */
	public static Date startOfToday(Date date){
		Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
	    LocalDate localDate = localDateTime.toLocalDate();
	    return Date.from(localDate.atStartOfDay(zone).toInstant());
	}
	
	/**
	 * 获取当前日期的结束时间
	 * @return
	 */
	public static Date endOfToday() {
		long time = startOfToday().getTime();
		Instant instant = Instant.ofEpochMilli(time).plusMillis(TimeUnit.DAYS.toMillis(1) - 1);
		return Date.from(instant);
	}
	
	/**
	 * 获取指定日期的结束时间
	 * @param date
	 * @return
	 */
	public static Date endOfToday(Date date) {
		long time = startOfToday(date).getTime();
		Instant instant = Instant.ofEpochMilli(time).plusMillis(TimeUnit.DAYS.toMillis(1) - 1);
		return Date.from(instant);
	}
	
	/**
	 * 日期加一个数，根据field不同加不同值,field为ChronoUnit.*
	 * @param date
	 * @param number
	 * @param field，为ChronoUnit枚举
	 * @return
	 */
	public static Date add(Date date, long number, TemporalUnit field) {
		LocalDateTime localDateTime = convertLocalDateTime(date);
		return convertDate(localDateTime.plus(number, field));
	}
	
	/**
	 * 日期减一个数，根据field不同减不同值,field为ChronoUnit.*
	 * @param date
	 * @param number
	 * @param field，为ChronoUnit枚举
	 * @return
	 */
	public static Date minu(Date date, long number, TemporalUnit field){
		LocalDateTime localDateTime = convertLocalDateTime(date);
		return convertDate(localDateTime.minus(number, field));
	}
	
	public static LocalDateTime convertLocalDateTime(Date date){
		Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
	    return localDateTime;
	}
	
	public static Date convertDate(LocalDateTime localDateTime){
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); 
	}
	
	/**
	 * 将日期格式化为日期字符串，格式化后得到类型 yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String format(Date date){
		LocalDateTime localDateTime = convertLocalDateTime(date);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_NORMAL);
		return localDateTime.format(formatter);
	}
	
	/**
	 * 将日期格式化为日期字符串，格式化后得到传入的格式化类型
	 * @param date
	 * @param pattern 格式化类型
	 * @return
	 */
	public static String format(Date date,String pattern){
		if(isValidDate(pattern)){
			return formatDate(date, pattern);
		}
		return formatDateTime(date, pattern);
	}
	
	private static String formatDate(Date date,String pattern){
		LocalDateTime localDateTime = convertLocalDateTime(date);
		LocalDate localDate = localDateTime.toLocalDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return localDate.format(formatter);
	}
	
	private static String formatDateTime(Date date,String pattern){
		LocalDateTime localDateTime = convertLocalDateTime(date);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return localDateTime.format(formatter);
	}
	
	/**
	 * 根据field获取当前日期所在当前日/月/年
	 * @param field ChronoField枚举
	 * @return
	 */
	public static int getDate(TemporalField field){
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.get(field);
	}
	
	/**
	 * 根据field获取传入日期所在当前日/月/年
	 * @param field ChronoField枚举
	 * @param date
	 * @return
	 */
	public static int getDate(Date date,TemporalField field){
		LocalDateTime localDateTime = convertLocalDateTime(date);
		return localDateTime.get(field);
	}
	
	/**
	 * 计算两个日期之间相差
	 * @param unit 根据计算是相差天/月/年,ChronoUnit的枚举
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static Long subtract(Date beginTime, Date endTime,TemporalUnit unit) {
		LocalDateTime startDate = convertLocalDateTime(beginTime);
		LocalDateTime endDate = convertLocalDateTime(endTime);
		return unit.between(startDate, endDate);
	}
	
	/**
	 * 判断日期字符串是否有时分秒，如果有，返回false，没有，返回true
	 * @param src
	 * @return
	 */
	public static boolean isValidDate(String src){
		String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(src);
        return m.matches();
	}
	
	  /**
	   * 得到几天后的时间
	   * @param d
	   * @param day
	   * @return
	   */
	  public static Date getDateAfter(Date d,int day){
	   Calendar now =Calendar.getInstance();
	   now.setTime(d);
	   now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
	   return now.getTime();
	  }

	
}
