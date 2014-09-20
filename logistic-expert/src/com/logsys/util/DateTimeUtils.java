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
	
	/**格式化日期字符--周*/
	public static final String FORMATTED_TIMESTR_DAYOFWEEK="星期";
	
	/**最小日期字符串*/
	public static final String FORMATTED_TIMESTR_MINDATE="MinimumDate-";
	
	/**最大日期字符串*/
	public static final String FORMATTED_TIMESTR_MAXDATE="MaximumDate-";
	
	/**
	 * 获取当前时间的合理变量:设置周一为每周第一天，并且设置最少有四天在当年时这个周才为本年第一周
	 * @return 合理日历对象
	 */
	public static Calendar getValidCalendar() {
		Calendar cal=Calendar.getInstance();
		cal.setMinimalDaysInFirstWeek(4);			//最少有四天在当年时这个周才为本年第一周
		cal.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);	//周一为每周第一天
		return cal;
	}
	
	/**
	 * 获取指定时间戳的合理变量:设置周一为每周第一天，并且设置最少有四天在当年时这个周才为本年第一周
	 * @param millsec 指定时间的毫秒时间戳 ，-1为则为当前时间
	 * @return 合理日历对象
	 */
	public static Calendar getValidCalendar(long millsec) {
		Calendar cal=getValidCalendar();
		if(millsec>=0) cal.setTimeInMillis(millsec);
		return cal;
	}
	
	/**
	 * 清除毫秒时间戳中的小时，分钟，秒，毫秒数据,
	 * @param millsec 需要清除的毫秒时间戳,如果小于0,则返回当前时间消除后的时间戳
	 * @return 消除小时，分钟，秒，毫秒数据的毫秒时间戳
	 */
	public static long cutHourMinSecMil(long millsec) {
		Calendar cal=getValidCalendar(millsec);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 获取参数时间戳所在周的第一天,小时分钟秒毫秒设置为0。
	 * @param millsec 要获取所在周第一天的毫秒时间戳，如果小于0则默认为当前周第一天。
	 * @return 所在周第一天的毫秒时间戳
	 */
	public static long getFirstDayOfWeek(long millsec) {
		Calendar cal=getValidCalendar(cutHourMinSecMil(millsec));
		cal.set(Calendar.DAY_OF_WEEK, FIRST_DAY_OF_WEEK);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 获取参数时间戳所在月的第一天,小时分钟秒毫秒设置为0。
	 * @param millsec 要获取所在月第一天的毫秒时间戳，如果小于0则默认为当前月第一天。
	 * @return 参数月第一天的时间戳
	 */
	public static long getFirstDayOfMonth(long millsec) {
		millsec=cutHourMinSecMil(millsec);
		Calendar cal=getValidCalendar();
		cal.setTimeInMillis(millsec);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTimeInMillis();
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
	 * 获取格式化的时间字符串--年周字符串
	 * @param year 年数
	 * @param week 周数
	 * @return 格式化的年周字符串
	 */
	public static String getFormattedTimeStr_YearWeek(int year, int week) {
		return year+FORMATTED_TIMESTR_YEAR+week+FORMATTED_TIMESTR_WEEK;
	}
	
	/**
	 * 获取格式化的时间字符串--年数周数星期几
	 * @param millsec 要格式化的日期时间戳，如果为<0则判断日期为当天
	 * @return 年数周数星期几
	 */
	public static String getFormattedTimeStr_YearWeekWkday(long millsec) {
		Calendar cal=getValidCalendar(millsec);
		return cal.get(Calendar.YEAR)+FORMATTED_TIMESTR_YEAR+cal.get(Calendar.WEEK_OF_YEAR)+FORMATTED_TIMESTR_WEEK+" "+FORMATTED_TIMESTR_DAYOFWEEK+cal.get(Calendar.DAY_OF_WEEK);
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
