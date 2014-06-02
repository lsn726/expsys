package com.logsys.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间日期工具类
 * @author lx8sn6
 */
public class DateTimeUtils {

	//private static Logger logger=Logger.getLogger(TimeUtils.class);
	
	/**每周第一天变量*/
	public static final int FIRST_DAY_OF_WEEK=Calendar.MONDAY;
	
	/**格式化日期字符--年*/
	public static final String FORMATTED_TIMESTR_YEAR="年";
	
	/**格式化日期字符--月*/
	public static final String FORMATTED_TIMESTR_MONTH="月";
	
	/**格式化日期字符--周*/
	public static final String FORMATTED_TIMESTR_WEEK="周";
	
	/**最小日期字符串*/
	public static final String FORMATTED_TIMESTR_MINDATE="MinimumDate-";
	
	/**最大日期字符串*/
	public static final String FORMATTED_TIMESTR_MAXDATE="MaximumDate-";
	
	/**
	 * 获取合理的时间变量:设置周一为每周第一天，并且设置最少有四天在当年时这个周才为本年第一周
	 * @return 日历对象
	 */
	public static Calendar getValidCalendar() {
		Calendar cal=Calendar.getInstance();
		cal.setMinimalDaysInFirstWeek(4);			//最少有四天在当年时这个周才为本年第一周
		cal.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);		//周一为每周第一天
		return cal;
	}
	
	/**
	 * 获取参数中日期对象的所在周的第一天,小时分钟秒皆为0,参数为null则默认判断日期为当前日期
	 * @param calendar 确定所在所在周的Calendar参数
	 * @return 所在周第一天的Calendar对象
	 */
	public static Calendar getFirstDayOfWeek(Calendar calendar) {
		Calendar cal;
		if(calendar==null)
			cal=getValidCalendar();
		else
			cal=calendar;
		Calendar res=getValidCalendar();
		res.clear();
		res.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		res.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR));
		res.set(Calendar.DAY_OF_WEEK, FIRST_DAY_OF_WEEK);
		return res;
	}
	
	/**
	 * getFirstDayOfWeek(Calendar)的Date参数版本
	 */
	public static Calendar getFirstDayOfWeek(Date date) {
		Calendar cal=getValidCalendar();
		if(date==null)
			cal=null;
		else
			cal.setTime(date);
		return getFirstDayOfWeek(cal);
	}
	
	/**
	 * 获取参数中日期对象的所在月份的第一天,小时分钟秒皆为0,参数为null则默认判断日期为当前日期
	 * @param calendar 确定所在所在月的Calendar参数
	 * @return 所在月第一天的Calendar对象
	 */
	public static Calendar getFirstDayOfMonth(Calendar calendar) {
		Calendar cal;
		if(calendar==null)
			cal=getValidCalendar();
		else
			cal=calendar;
		Calendar res=getValidCalendar();
		res.clear();
		res.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		res.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		res.set(Calendar.DAY_OF_MONTH, 1);
		return res;
	}
	
	/**
	 * getFirstDayOfMonth(Calendar)的Date参数版本
	 */
	public static Calendar getFirstDayOfMonth(Date date) {
		Calendar cal=getValidCalendar();
		if(date==null)
			cal=null;
		else
			cal.setTime(date);
		return getFirstDayOfMonth(cal);
	}
	
	/**
	 * 获取格式化的时间字符串--年周字符串
	 * @param calendar 要格式化的日期对象，如果为null则当前日期为判断日期
	 * @return 格式化的年周字符串
	 */
	public static String getFormattedTimeStr_YearWeek(Calendar calendar) {
		Calendar cal;
		if(calendar==null)
			cal=getValidCalendar();
		else
			cal=calendar;
		return cal.get(Calendar.YEAR)+FORMATTED_TIMESTR_YEAR+cal.get(Calendar.WEEK_OF_YEAR)+FORMATTED_TIMESTR_WEEK;
	}
	
	/**
	 * getFormattedTimeStr_YearWeek(Calendar)的Date参数版本
	 */
	public static String getFormattedTimeStr_YearWeek(Date date) {
		Calendar cal=getValidCalendar();
		if(date!=null)
			cal.setTime(date);
		return cal.get(Calendar.YEAR)+FORMATTED_TIMESTR_YEAR+cal.get(Calendar.WEEK_OF_YEAR)+FORMATTED_TIMESTR_WEEK;
	}
	
	/**
	 * 获取格式化的时间字符串--年月字符串
	 * @param calendar 要格式化的日期对象，如果为null则当前日期为判断日期
	 * @return 格式化的年月字符串
	 */
	public static String getFormattedTimeStr_YearMonth(Calendar calendar) {
		Calendar cal;
		if(calendar==null)
			cal=getValidCalendar();
		else
			cal=calendar;
		return cal.get(Calendar.YEAR)+FORMATTED_TIMESTR_YEAR+cal.get(Calendar.MONTH)+FORMATTED_TIMESTR_MONTH;
	}
	
	/**
	 * 返回最小时间的Calendar对象
	 * @return 最小时间的Calendar对象
	 */
	public static Calendar getMinCalendar() {
		Calendar cal=getValidCalendar();
		cal.clear();
		cal.set(1900, 1, 1);
		return cal;
	}
	
	/**
	 * 返回最小时间的Date对象
	 * @return 最小时间的Date对象
	 */
	public static Date getMinDate() {
		return getMinCalendar().getTime();
	}

	/**
	 * 返回最大时间的Calendar对象
	 * @return 最大时间的Calendar对象
	 */
	public static Calendar getMaxCalendar() {
		Calendar cal=getValidCalendar();
		cal.clear();
		cal.set(2150, 12, 31);
		return cal;
	}
	
	/**
	 * 返回最大时间的Date对象
	 * @return 最大时间的Date对象
	 */
	public static Date getMaxDate() {
		return getMaxCalendar().getTime();
	}
	
}
