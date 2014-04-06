package com.logsys.setting.pp.bwi;

import java.util.Date;

/**
 * BWI生产计划Excel表格信息
 * @author ShaonanLi
 */
public interface BWIPPExcelInfo {

	/**
	 * 获取pn所在列数
	 * @return pn所在列数
	 */
	public int getPnCol();
	
	/**
	 * 获取总装1线的日期行数
	 * @return 总装1线日期行数
	 */
	public int getFA1DateRow();
	
	/**
	 * 获取总装1线计划起始行数
	 * @return 总装1线计划起始行数
	 */
	public int getFA1PPBeginRow();
	
	/**
	 * 获取总装1线计划结束行数
	 * @return 总装1线计划结束行数
	 */
	public int getFA1PPEndRow();
	
	/**
	 * 获取总装2线日期行数
	 * @return 总装2线日期行数
	 */
	public int getFA2DateRow();
	
	/**
	 * 获取总装2线计划起始行数
	 * @return 总装2线计划起始行数
	 */
	public int getFA2PPBeginRow();
	
	
	/**
	 * 获取总装2线计划结束列数
	 * @return 总装2线计划结束列数
	 */
	public int getFA2PPEndRow();
	
	/**
	 * 获取版本 
	 * @return 版本
	 */
	public Date getVersion();
	
}
