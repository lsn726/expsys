package com.logsys.util;

import java.io.File;
import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * 通用工具类
 * @author lx8sn6
 */
public class GeneralUtils {

	private static Logger logger=Logger.getLogger(GeneralUtils.class);
	
	/**
	 * 获取合理的时间变量:设置周一为每周第一天，并且设置最少有四天在当年时这个周才为本年第一周
	 * @return 日历对象
	 */
	public static Calendar getValidCalendar() {
		Calendar cal=Calendar.getInstance();
		cal.setMinimalDaysInFirstWeek(4);			//最少有四天在当年时这个周才为本年第一周
		cal.setFirstDayOfWeek(Calendar.MONDAY);		//周一为每周第一天
		return cal;
	}
	
	/**
	 * 是否能创建新文件
	 * @param filepath 文件的路径
	 * @return 文件对象成功/null失败
	 */
	public static boolean isValidNewFilePath(String filepath) {
		if(filepath==null) {
			logger.error("文件路径不能用于新文件，文件路径参数为空.");
			return false;
		}
		File file=new File(filepath);
		if(file.exists()) {
			logger.error("文件路径不能用于新文件，文件已存在。");
			return false;
		}
		return true;
	}

}
