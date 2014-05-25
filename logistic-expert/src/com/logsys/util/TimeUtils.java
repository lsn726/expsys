package com.logsys.util;

import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * 时间日期工具类
 * @author lx8sn6
 */
public class TimeUtils {

	private static Logger logger=Logger.getLogger(TimeUtils.class);
	
	/**每周第一天变量*/
	public static final int FIRST_DAY_OF_WEEK=Calendar.MONDAY;
	
	/**格式化日期字符--年*/
	public static final String FORMATTED_TIMESTR_YEAR="年";
	
	/**格式化日期字符--月*/
	public static final String FORMATTED_TIMESTR_MONTH="月";
	
	/**格式化日期字符--周*/
	public static final String FORMATTED_TIMESTR_WEEK="周";
	
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
		return cal.get(Calendar.YEAR)+FORMATTED_TIMESTR_YEAR+calendar.get(Calendar.WEEK_OF_YEAR)+FORMATTED_TIMESTR_WEEK;
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
		return cal.get(Calendar.YEAR)+FORMATTED_TIMESTR_YEAR+calendar.get(Calendar.MONTH)+FORMATTED_TIMESTR_MONTH;
	}

}
