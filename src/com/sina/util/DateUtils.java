package com.sina.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;



/**
 * ������Date ��ʵ����ֻ��һ��������, ����������һ������������, ��ʾ��
 * 
 * �Ǵ�GMT(�������α�׼ʱ��)1970��1��1��00:00:00��һ��֮ǰ������֮�����ĺ�����
 * 
 * @author ����
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
     * Ĭ�����ڸ�ʽyyyy-MM-dd
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	
    //TODO ���ʱ��
	/**
	 * ��õ�ǰ���
	 * @return ��ǰ���
	 */
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	/**
	 * ��õ�ǰ�·�
	 * @return ��ǰ�·�
	 */
	public static int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}
	/**
	 * ��õ�ǰDAY OF MONTH
	 * @return ��ǰDAY OF MONTH
	 */
	public static int getCurrentDay() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * ��õ�ǰDAY OF MONTH
	 * @return ��ǰDAY OF MONTH
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
	 * ��õ�ǰ������
	 * @return ���ص�ǰ������
	 */
	public static java.util.Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	/**
	 * �����������
	 * @param pattern
	 * @return ������������
	 */
	public static java.util.Date getYestodayDate() {
	    return new java.util.Date(System.currentTimeMillis() - MILLIS_PER_DAY);
	}
	
	/**
	 * �����������
	 * @param pattern
	 * @return ������������
	 */
	public static java.util.Date getTomorrowDate() {
		return new java.util.Date(System.currentTimeMillis() + MILLIS_PER_DAY);
	}
	
	/**
	 * �����������
	 * @param pattern
	 * @return ������������
	 */
	public static java.util.Date getYestodayDate(java.util.Date date) {
	    return new java.util.Date(date.getTime() - MILLIS_PER_DAY);
	}
	
	/**
	 * �����������
	 * @param pattern
	 * @return ������������
	 */
	public static java.util.Date getTomorrowDate(java.util.Date date) {
		return new java.util.Date(date.getTime() + MILLIS_PER_DAY);
	}
	
	/**
	 * ��õ�ǰ������(yyyy-MM-dd)
	 * @return ���ص�ǰ������
	 */
	public static String getCurrentDateAccurateToDay() {
	    return getCurrentDate(DEFAULT_DATE_PATTERN);
	}
	
	/**
	 * ��õ�ǰ������(yyyy-MM-dd EE)
	 * @return ���ص�ǰ������
	 */
	public static String getCurrentDateAccurateToWeek() {
		return getCurrentDate("yyyy-MM-dd EE");
	}
	
	/**
	 * ��õ�ǰ��ʱ��(yyyy-MM-dd hh:mm:ss)
	 * @return ���ص�ǰʱ��
	 */
	public static String getCurrentDateAccurateToSecond() {
	    return getCurrentDate("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * ��õ�ǰ��ʱ��(yyyyMMddHHmmssSSS)
	 * @return ���ص�ǰʱ��
	 */
	public static String getCurrentDateAccurateToMillisecond() {
	    return getCurrentDate("yyyy-MM-dd HH:mm:ss,SSS");
	}
	
	/**
	 * �����������(yyyy-MM-dd)
	 * @return ������������
	 */
	public static String getYestodayStringDate() {
		return getYestodayDate(DEFAULT_DATE_PATTERN);
	}
	/**
	 * ����������ڵ���
	 * @return ����������
	 */
	public static String getYestodayStringDateYear() {
		String d = getYestodayStringDate();
		return d.substring(0,4);
	}
	
	/**
	 * ����������ڵ���
	 * @return ����������
	 */
	public static String getYestodayStringDateMonth() {
		String d = getYestodayStringDate();
		return d.substring(5,7);
	}
	
	/**
	 * ����������ڵ���
	 * @return ������������
	 */
	public static String getYestodayStringDateD() {
		String d = getYestodayStringDate();
		return d.substring(8,10);
	}
	
	/**
	 * �����������(yyyy-MM-dd)
	 * @return ������������
	 */
	public static String getTomorrowStringDate() {
		return getTomorrowDate(DEFAULT_DATE_PATTERN);
	}
	
	//TODO ���ָ����ʽ��ʱ��
	/**
	 * ����ʽ�����������
	 * @param pattern
	 * @return ������������
	 */
	public static String getYestodayDate(String pattern) {
	    java.util.Date yestodayDate = new java.util.Date(System.currentTimeMillis() - MILLIS_PER_DAY);
	    return dateToString(yestodayDate, pattern);
	}
	
	/**
	 * ����ʽ�����������
	 * @param pattern
	 * @return ������������
	 */
	public static String getTomorrowDate(String pattern) {
		java.util.Date tomorrowDate = new java.util.Date(System.currentTimeMillis() + MILLIS_PER_DAY);
		return dateToString(tomorrowDate, pattern);
	}
	
	/**
	 * ����ָ����ʽ�ĵ�������
	 * @param pattern ʱ����ʽ
	 * @return ����ָ����ʽ�ĵ�������
	 */
	public static String getCurrentDate(String pattern) {
	    Calendar cal = Calendar.getInstance();
	    java.util.Date date = cal.getTime();
		return dateToString(date, pattern);
	}
	
	//TODO ת�����ڸ�ʽ
	
	/**
	 * ��ָ���ĸ�ʽ����ʽ������
	 * @param date
	 * @param pattern
	 */
	public static String dateToString(java.util.Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
        return sdf.format(date);
	}
	
	/**
	 * ��ָ���ĸ�ʽ����ʽ������
	 * @param date
	 * @param pattern
	 */
	public static String getDateByFormat(String date, String oldPattern, String newPattern) {
	    java.util.Date newDate = stringToDate(date, oldPattern);
        return dateToString(newDate, newPattern);
	}
	
	/**
	 * �����ַ������ı������� Date
	 * 
	 * ˵����SimpleDateFormat��DateFormat�����ࡣ
	 * ��DateFormat�У�Ҳ���Ե���parse���������������͵����ݣ��������Ƽ���������
	 * ���黹�ǲ���SimpleDateFormat����parse(Strng source, ParsePosition positon)��������
	 * ����ԭ����ΪDateFormat�е�parse������bug�Ҳ�֧�ֶ�λ������
	 * 
	 * @param date
	 * @return �����ɹ��򷵻ط����õ������ڣ�ʧ���򷵻�null
	 */
	public static java.util.Date stringToDate(String date, String pattern) {
	    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	    java.util.Date newDate = formatter.parse(date, new ParsePosition(0));
	    return newDate;
	}
	
	/**
	 * �����ַ������ı������� Calendar
	 * @param date
	 * @param pattern
	 */
	public static Calendar stringToCalendar(String date, String pattern) {
		java.util.Date newDate = stringToDate(date, pattern);
	    return dateToCalendar(newDate);
	}
	
	/**
	 * CalendarתDate
	 * @param calendar
	 * @return Date
	 */
	public static java.util.Date calendarToDate(Calendar cal) {
		return cal.getTime();
	}
	
	/**
	 * DateתCalendar
	 * @param date
	 * @return Calendar
	 */
	public static Calendar dateToCalendar(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	/**
	 * ���ָ����ʽ������
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
	 * ���ָ����ʽ������
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
	 * ���ָ����ʽ������
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
