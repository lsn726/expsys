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

	/**经过授权的机器码*/
	public static final String AUT_MACHINE_STR="-1463e3b8ee2aadf2fc8724a2e2a5846b8";
	
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
