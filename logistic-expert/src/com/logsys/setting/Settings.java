package com.logsys.setting;

import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.setting.pd.bwi.BWIPLInfo20140331;
import com.logsys.setting.pp.bwi.BWIPPExcelInfo;
import com.logsys.setting.pp.bwi.BWIPPExcelInfo20140331;

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
		public static final BWIPPExcelInfo ppExcelInfo=new BWIPPExcelInfo20140331();
		
		/**生产线信息配置类*/
		public static final BWIPLInfo plinfo=new BWIPLInfo20140331();
		
	}
	
}
