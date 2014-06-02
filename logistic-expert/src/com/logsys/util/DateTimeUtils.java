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
	
	/**��С�����ַ���*/
	public static final String FORMATTED_TIMESTR_MINDATE="MinimumDate-";
	
	/**��������ַ���*/
	public static final String FORMATTED_TIMESTR_MAXDATE="MaximumDate-";
	
	/**
	 * ��ȡ�����ʱ�����:������һΪÿ�ܵ�һ�죬�������������������ڵ���ʱ����ܲ�Ϊ�����һ��
	 * @return ��������
	 */
	public static Calendar getValidCalendar() {
		Calendar cal=Calendar.getInstance();
		cal.setMinimalDaysInFirstWeek(4);			//�����������ڵ���ʱ����ܲ�Ϊ�����һ��
		cal.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);		//��һΪÿ�ܵ�һ��
		return cal;
	}
	
	/**
	 * ��ȡ���������ڶ���������ܵĵ�һ��,Сʱ�������Ϊ0,����Ϊnull��Ĭ���ж�����Ϊ��ǰ����
	 * @param calendar ȷ�����������ܵ�Calendar����
	 * @return �����ܵ�һ���Calendar����
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
	 * getFirstDayOfWeek(Calendar)��Date�����汾
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
	 * ��ȡ���������ڶ���������·ݵĵ�һ��,Сʱ�������Ϊ0,����Ϊnull��Ĭ���ж�����Ϊ��ǰ����
	 * @param calendar ȷ�����������µ�Calendar����
	 * @return �����µ�һ���Calendar����
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
	 * getFirstDayOfMonth(Calendar)��Date�����汾
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
