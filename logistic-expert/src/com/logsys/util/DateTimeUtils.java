package com.logsys.util;

import java.util.Calendar;
import java.util.Date;

/**
 * ʱ�����ڹ�����
 * @author lx8sn6
 */
public class DateTimeUtils {

	//private static Logger logger=Logger.getLogger(TimeUtils.class);
	
	/**ÿ�ܵ�һ�����*/
	public static final int FIRST_DAY_OF_WEEK=Calendar.MONDAY;
	
	/**��ʽ�������ַ�--��*/
	public static final String FORMATTED_TIMESTR_YEAR="��";
	
	/**��ʽ�������ַ�--��*/
	public static final String FORMATTED_TIMESTR_MONTH="��";
	
	/**��ʽ�������ַ�--��*/
	public static final String FORMATTED_TIMESTR_WEEK="��";
	
	/**��ʽ�������ַ�--��*/
	public static final String FORMATTED_TIMESTR_DAYOFWEEK="����";
	
	/**��С�����ַ���*/
	public static final String FORMATTED_TIMESTR_MINDATE="MinimumDate-";
	
	/**��������ַ���*/
	public static final String FORMATTED_TIMESTR_MAXDATE="MaximumDate-";
	
	/**
	 * ��ȡ��ǰʱ��ĺ������:������һΪÿ�ܵ�һ�죬�������������������ڵ���ʱ����ܲ�Ϊ�����һ��
	 * @return ������������
	 */
	public static Calendar getValidCalendar() {
		Calendar cal=Calendar.getInstance();
		cal.setMinimalDaysInFirstWeek(4);			//�����������ڵ���ʱ����ܲ�Ϊ�����һ��
		cal.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);	//��һΪÿ�ܵ�һ��
		return cal;
	}
	
	/**
	 * ��ȡָ��ʱ����ĺ������:������һΪÿ�ܵ�һ�죬�������������������ڵ���ʱ����ܲ�Ϊ�����һ��
	 * @param millsec ָ��ʱ��ĺ���ʱ��� ��-1Ϊ��Ϊ��ǰʱ��
	 * @return ������������
	 */
	public static Calendar getValidCalendar(long millsec) {
		Calendar cal=getValidCalendar();
		if(millsec>=0) cal.setTimeInMillis(millsec);
		return cal;
	}
	
	/**
	 * �������ʱ����е�Сʱ�����ӣ��룬��������,
	 * @param millsec ��Ҫ����ĺ���ʱ���,���С��0,�򷵻ص�ǰʱ���������ʱ���
	 * @return ����Сʱ�����ӣ��룬�������ݵĺ���ʱ���
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
	 * ��ȡ����ʱ��������ܵĵ�һ��,Сʱ�������������Ϊ0��
	 * @param millsec Ҫ��ȡ�����ܵ�һ��ĺ���ʱ��������С��0��Ĭ��Ϊ��ǰ�ܵ�һ�졣
	 * @return �����ܵ�һ��ĺ���ʱ���
	 */
	public static long getFirstDayOfWeek(long millsec) {
		Calendar cal=getValidCalendar(cutHourMinSecMil(millsec));
		cal.set(Calendar.DAY_OF_WEEK, FIRST_DAY_OF_WEEK);
		return cal.getTimeInMillis();
	}
	
	/**
	 * ��ȡ����ʱ��������µĵ�һ��,Сʱ�������������Ϊ0��
	 * @param millsec Ҫ��ȡ�����µ�һ��ĺ���ʱ��������С��0��Ĭ��Ϊ��ǰ�µ�һ�졣
	 * @return �����µ�һ���ʱ���
	 */
	public static long getFirstDayOfMonth(long millsec) {
		millsec=cutHourMinSecMil(millsec);
		Calendar cal=getValidCalendar();
		cal.setTimeInMillis(millsec);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTimeInMillis();
	}
	
	/**
	 * ��ȡ��ʽ����ʱ���ַ���--�����ַ���
	 * @param calendar Ҫ��ʽ�������ڶ������Ϊnull��ǰ����Ϊ�ж�����
	 * @return ��ʽ���������ַ���
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
	 * getFormattedTimeStr_YearWeek(Calendar)��Date�����汾
	 */
	public static String getFormattedTimeStr_YearWeek(Date date) {
		Calendar cal=getValidCalendar();
		if(date!=null)
			cal.setTime(date);
		return cal.get(Calendar.YEAR)+FORMATTED_TIMESTR_YEAR+cal.get(Calendar.WEEK_OF_YEAR)+FORMATTED_TIMESTR_WEEK;
	}
	
	/**
	 * ��ȡ��ʽ����ʱ���ַ���--�����ַ���
	 * @param year ����
	 * @param week ����
	 * @return ��ʽ���������ַ���
	 */
	public static String getFormattedTimeStr_YearWeek(int year, int week) {
		return year+FORMATTED_TIMESTR_YEAR+week+FORMATTED_TIMESTR_WEEK;
	}
	
	/**
	 * ��ȡ��ʽ����ʱ���ַ���--�����������ڼ�
	 * @param millsec Ҫ��ʽ��������ʱ��������Ϊ<0���ж�����Ϊ����
	 * @return �����������ڼ�
	 */
	public static String getFormattedTimeStr_YearWeekWkday(long millsec) {
		Calendar cal=getValidCalendar(millsec);
		return cal.get(Calendar.YEAR)+FORMATTED_TIMESTR_YEAR+cal.get(Calendar.WEEK_OF_YEAR)+FORMATTED_TIMESTR_WEEK+" "+FORMATTED_TIMESTR_DAYOFWEEK+cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * ��ȡ��ʽ����ʱ���ַ���--�����ַ���
	 * @param calendar Ҫ��ʽ�������ڶ������Ϊnull��ǰ����Ϊ�ж�����
	 * @return ��ʽ���������ַ���
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
	 * ������Сʱ���Calendar����
	 * @return ��Сʱ���Calendar����
	 */
	public static Calendar getMinCalendar() {
		Calendar cal=getValidCalendar();
		cal.clear();
		cal.set(1900, 1, 1);
		return cal;
	}
	
	/**
	 * ������Сʱ���Date����
	 * @return ��Сʱ���Date����
	 */
	public static Date getMinDate() {
		return getMinCalendar().getTime();
	}

	/**
	 * �������ʱ���Calendar����
	 * @return ���ʱ���Calendar����
	 */
	public static Calendar getMaxCalendar() {
		Calendar cal=getValidCalendar();
		cal.clear();
		cal.set(2150, 12, 31);
		return cal;
	}
	
	/**
	 * �������ʱ���Date����
	 * @return ���ʱ���Date����
	 */
	public static Date getMaxDate() {
		return getMaxCalendar().getTime();
	}
	
}
