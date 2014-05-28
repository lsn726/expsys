package com.logsys.setting;

import com.logsys.setting.pp.MRPSettings;
import com.logsys.setting.pp.bwi.BWIMRPSettings;
import com.logsys.setting.pp.bwi.BWIPPExcelInfo;
import com.logsys.setting.pp.bwi.BWIPPExcelInfo20140528;

/**
 * 设置类
 * @author ShaonanLi
 */
public class Settings {

	/**
	 * BWI设置类
	 * @author ShaonanLi
	 */
	public static class BWISettings {
		
		/**生产计划Excel表格配置类*/
		public static final BWIPPExcelInfo ppExcelInfo=new BWIPPExcelInfo20140528();
		
	}
	
	/**MRP设置类*/
	public static final MRPSettings mrpSetting=new BWIMRPSettings();
	
}
