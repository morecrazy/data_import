package com.sina.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;



/**
 * 描述：Date 类实际上只是一个包裹类, 它包含的是一个长整型数据, 表示的
 * 
 * 是从GMT(格林尼治标准时间)1970年1月1日00:00:00这一刻之前或者是之后经历的毫秒数
 * 
 * @author 梁焱
 * 
 * @since 2007-03-09
 */

public class DateUtils {
    
    /**
     * Number of milliseconds in a standard second.
     */
    public static final long MILLIS_PER_SECOND = 1000;
    /**
     * Number of milliseconds in a standard minute.
     */
    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    /**
     * Number of milliseconds in a standard hour.
     */
    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
    /**
     * Number of milliseconds in a standard day.
     */
    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;
    
    /**
     * 默认日期格式yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	
    //TODO 获得时间
	/**
	 * 获得当前年份
	 * @return 当前年份
	 */
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	/**
	 * 获得当前月份
	 * @return 当前月份
	 */
	public static int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}
	/**
	 * 获得当前DAY OF MONTH
	 * @return 当前DAY OF MONTH
	 */
	public static int getCurrentDay() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获得当前DAY OF MONTH
	 * @return 当前DAY OF MONTH
	 */
	public static int getCurrentHour() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int getCurrentHour(java.util.Date date) {
		Calendar cal = dateToCalendar(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获得当前的日期
	 * @return 返回当前的日期
	 */
	public static java.util.Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	/**
	 * 获得昨天日期
	 * @param pattern
	 * @return 返回昨天日期
	 */
	public static java.util.Date getYestodayDate() {
	    return new java.util.Date(System.currentTimeMillis() - MILLIS_PER_DAY);
	}
	
	/**
	 * 获得明天日期
	 * @param pattern
	 * @return 返回明天日期
	 */
	public static java.util.Date getTomorrowDate() {
		return new java.util.Date(System.currentTimeMillis() + MILLIS_PER_DAY);
	}
	
	/**
	 * 获得昨天日期
	 * @param pattern
	 * @return 返回昨天日期
	 */
	public static java.util.Date getYestodayDate(java.util.Date date) {
	    return new java.util.Date(date.getTime() - MILLIS_PER_DAY);
	}
	
	/**
	 * 获得明天日期
	 * @param pattern
	 * @return 返回明天日期
	 */
	public static java.util.Date getTomorrowDate(java.util.Date date) {
		return new java.util.Date(date.getTime() + MILLIS_PER_DAY);
	}
	
	/**
	 * 获得当前的日期(yyyy-MM-dd)
	 * @return 返回当前的日期
	 */
	public static String getCurrentDateAccurateToDay() {
	    return getCurrentDate(DEFAULT_DATE_PATTERN);
	}
	
	/**
	 * 获得当前的日期(yyyy-MM-dd EE)
	 * @return 返回当前的日期
	 */
	public static String getCurrentDateAccurateToWeek() {
		return getCurrentDate("yyyy-MM-dd EE");
	}
	
	/**
	 * 获得当前的时间(yyyy-MM-dd hh:mm:ss)
	 * @return 返回当前时间
	 */
	public static String getCurrentDateAccurateToSecond() {
	    return getCurrentDate("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获得当前的时间(yyyyMMddHHmmssSSS)
	 * @return 返回当前时间
	 */
	public static String getCurrentDateAccurateToMillisecond() {
	    return getCurrentDate("yyyy-MM-dd HH:mm:ss,SSS");
	}
	
	/**
	 * 获得昨天日期(yyyy-MM-dd)
	 * @return 返回昨天日期
	 */
	public static String getYestodayStringDate() {
		return getYestodayDate(DEFAULT_DATE_PATTERN);
	}
	/**
	 * 获得昨天日期的年
	 * @return 返回昨天年
	 */
	public static String getYestodayStringDateYear() {
		String d = getYestodayStringDate();
		return d.substring(0,4);
	}
	
	/**
	 * 获得昨天日期的月
	 * @return 返回昨天月
	 */
	public static String getYestodayStringDateMonth() {
		String d = getYestodayStringDate();
		return d.substring(5,7);
	}
	
	/**
	 * 获得昨天日期的日
	 * @return 返回昨天日期
	 */
	public static String getYestodayStringDateD() {
		String d = getYestodayStringDate();
		return d.substring(8,10);
	}
	
	/**
	 * 获得明天日期(yyyy-MM-dd)
	 * @return 返回明天日期
	 */
	public static String getTomorrowStringDate() {
		return getTomorrowDate(DEFAULT_DATE_PATTERN);
	}
	
	//TODO 获得指定格式的时间
	/**
	 * 按格式获得昨天日期
	 * @param pattern
	 * @return 返回昨天日期
	 */
	public static String getYestodayDate(String pattern) {
	    java.util.Date yestodayDate = new java.util.Date(System.currentTimeMillis() - MILLIS_PER_DAY);
	    return dateToString(yestodayDate, pattern);
	}
	
	/**
	 * 按格式获得明天日期
	 * @param pattern
	 * @return 返回明天日期
	 */
	public static String getTomorrowDate(String pattern) {
		java.util.Date tomorrowDate = new java.util.Date(System.currentTimeMillis() + MILLIS_PER_DAY);
		return dateToString(tomorrowDate, pattern);
	}
	
	/**
	 * 返回指定格式的当天日期
	 * @param pattern 时间样式
	 * @return 返回指定格式的当天日期
	 */
	public static String getCurrentDate(String pattern) {
	    Calendar cal = Calendar.getInstance();
	    java.util.Date date = cal.getTime();
		return dateToString(date, pattern);
	}
	
	//TODO 转换日期格式
	
	/**
	 * 以指定的格式来格式化日期
	 * @param date
	 * @param pattern
	 */
	public static String dateToString(java.util.Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
        return sdf.format(date);
	}
	
	/**
	 * 以指定的格式来格式化日期
	 * @param date
	 * @param pattern
	 */
	public static String getDateByFormat(String date, String oldPattern, String newPattern) {
	    java.util.Date newDate = stringToDate(date, oldPattern);
        return dateToString(newDate, newPattern);
	}
	
	/**
	 * 分析字符串的文本，生成 Date
	 * 
	 * 说明：SimpleDateFormat是DateFormat的子类。
	 * 在DateFormat中，也可以调用parse方法来解析日期型的数据，不过不推荐这样做。
	 * 建议还是采用SimpleDateFormat类中parse(Strng source, ParsePosition positon)来解析。
	 * 具体原因，因为DateFormat中的parse方法有bug且不支持定位解析。
	 * 
	 * @param date
	 * @return 分析成功则返回分析得到的日期，失败则返回null
	 */
	public static java.util.Date stringToDate(String date, String pattern) {
	    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	    java.util.Date newDate = formatter.parse(date, new ParsePosition(0));
	    return newDate;
	}
	
	/**
	 * 分析字符串的文本，生成 Calendar
	 * @param date
	 * @param pattern
	 */
	public static Calendar stringToCalendar(String date, String pattern) {
		java.util.Date newDate = stringToDate(date, pattern);
	    return dateToCalendar(newDate);
	}
	
	/**
	 * Calendar转Date
	 * @param calendar
	 * @return Date
	 */
	public static java.util.Date calendarToDate(Calendar cal) {
		return cal.getTime();
	}
	
	/**
	 * Date转Calendar
	 * @param date
	 * @return Calendar
	 */
	public static Calendar dateToCalendar(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	/**
	 * 获得指定格式的日期
	 * @param dayOfMonth
	 * @param hourOfDay
	 * @param minute
	 * @param second
	 * @return Date
	 */
	public static java.util.Date getSomeTime(java.util.Date date, int dayOfMonth, int hourOfDay, int minute, int second) {
		Calendar cal = dateToCalendar(date);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return calendarToDate(cal);
	}
	
	/**
	 * 获得指定格式的日期
	 * @param month
	 * @param dayOfMonth
	 * @param hourOfDay
	 * @param minute
	 * @param second
	 * @return Date
	 */
	public static java.util.Date getSomeTime(java.util.Date date, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
		Calendar cal = dateToCalendar(date);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return calendarToDate(cal);
	}
	
	/**
	 * 获得指定格式的日期
	 * @param hourOfDay
	 * @param minute
	 * @param second
	 * @return Date
	 */
	public static java.util.Date getSomeTime(java.util.Date date, int hourOfDay, int minute, int second) {
		Calendar cal = dateToCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return calendarToDate(cal);
	}

	public static void main(String[] args) {
		String a = getYestodayStringDateMonth();
		System.out.println(getYestodayStringDateYear());
		System.out.println(getYestodayStringDateMonth());
		System.out.println(getYestodayStringDateD());
		System.out.println(getYestodayDate("/yyyy/MM/dd/*"));
	}
}
