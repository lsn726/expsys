package com.logsys.util;

import java.io.File;
import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * ͨ�ù�����
 * @author lx8sn6
 */
public class GeneralUtils {

	private static Logger logger=Logger.getLogger(GeneralUtils.class);
	
	/**
	 * ��ȡ�����ʱ�����:������һΪÿ�ܵ�һ�죬�������������������ڵ���ʱ����ܲ�Ϊ�����һ��
	 * @return ��������
	 */
	public static Calendar getValidCalendar() {
		Calendar cal=Calendar.getInstance();
		cal.setMinimalDaysInFirstWeek(4);			//�����������ڵ���ʱ����ܲ�Ϊ�����һ��
		cal.setFirstDayOfWeek(Calendar.MONDAY);		//��һΪÿ�ܵ�һ��
		return cal;
	}
	
	/**
	 * �Ƿ��ܴ������ļ�
	 * @param filepath �ļ���·��
	 * @return �ļ�����ɹ�/nullʧ��
	 */
	public static boolean isValidNewFilePath(String filepath) {
		if(filepath==null) {
			logger.error("�ļ�·�������������ļ����ļ�·������Ϊ��.");
			return false;
		}
		File file=new File(filepath);
		if(file.exists()) {
			logger.error("�ļ�·�������������ļ����ļ��Ѵ��ڡ�");
			return false;
		}
		return true;
	}

}
