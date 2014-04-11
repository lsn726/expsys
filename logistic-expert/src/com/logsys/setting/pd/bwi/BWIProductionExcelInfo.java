package com.logsys.setting.pd.bwi;

import java.util.Date;

import com.logsys.util.Location;

/**
 * BWI的生产Excel信息接口
 * @author ShaonanLi
 */
public interface BWIProductionExcelInfo {
	
	/**
	 * 获取配置Sheet名称
	 * @return 配置Sheet名称
	 */
	public String getConfigSheetName();
	
	/**
	 * 获取配置Sheet中生产线名称单元格的位置
	 * @return 配置Sheet中生产线名称单元格位置
	 */
	public Location getPrdLineNameLocInConfigSheet();
	
	/**
	 * 获取配置Sheet中月份信息单元格位置
	 * @return 月份信息单元格位置
	 */
	public Location getDateInfoLocInConfigSheet();

	/**
	 * 获取生产sheet中操作工数量所在列
	 * @return 操作工数量所在列
	 */
	public int getOperatorQtyColInProdSheet();

	/**
	 * 获取生产sheet中Part Number所在列
	 * @return part number所在列
	 */
	public int getPnColInProdSheet();
	
	/**
	 * 获取生产Sheet中产出数量所在列
	 * @return 产出数量所在列
	 */
	public int getOutputQtyColInProdSheet();
	
	/**
	 * 获取生产Sheet中时间段起始所在列
	 * @return 时间段起始所在列
	 */
	public int getTimeFrameBeginColInProdSheet();
	
	/**
	 * 获取生产Sheet中时间段结束所在列
	 * @return 时间段结束所在列
	 */
	public int getTimeFrameEndColInProdSheet();
	
	/**
	 * 获取早班起始列
	 * @return 早班起始列
	 */
	public int getEarlyShiftStartColInProdSheet();
	
	/**
	 * 获取中班起始列 
	 * @return 中班起始列
	 */
	public int getMiddleShiftStartColInProdSheet();
	
	/**
	 * 获取晚班起始列
	 * @return 晚班起始列
	 */
	public int getNightShiftStartColInProdSheet();
	
	/**
	 * 获取生产信息其实行数
	 * @return
	 */
	public int getProductionBeginRowInProdSheet();
	
	/**
	 * 获取配置版本
	 * @return 配置版本
	 */
	public Date getVersion();
	
}
