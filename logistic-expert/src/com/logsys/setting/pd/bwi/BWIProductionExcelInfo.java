package com.logsys.setting.pd.bwi;

import com.logsys.util.Location;

/**
 * BWI的生产Excel信息抽象类
 * @author ShaonanLi
 */
public abstract class BWIProductionExcelInfo {

	/**
	 * 获取配置Sheet名称
	 * @return 配置Sheet名称
	 */
	public String getConfigSheetName() {
		return "Configuration";
	}
	
	/**
	 * 获取配置Sheet中生产线名称单元格的位置
	 * @return 配置Sheet中生产线名称单元格位置
	 */
	public Location getPrdLineNameLocInConfigSheet() {
		Location loc=new Location();
		loc.column=0;
		loc.row=1;
		return loc;
	}
	
	/**
	 * 获取配置Sheet中月份信息单元格位置
	 * @return 月份信息单元格位置
	 */
	public Location getDateInfoLocInConfigSheet() {
		Location loc=new Location();
		loc.column=1;
		loc.row=1;
		return loc;
	}

}
