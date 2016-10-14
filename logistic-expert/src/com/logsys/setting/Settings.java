package com.logsys.setting;

import com.logsys.setting.pp.bwi.BWIPPExcelInfo;
import com.logsys.setting.pp.bwi.BWIPPExcelInfo20161013;

/**
 * 设置类
 * @author ShaonanLi
 */
public class Settings {

	/**经过授权的机器码*/
	public static final String AUT_MACHINE_STR="-518f8ee3b8aab7cbf21c92849ba34206c";
	
	/**
	 * BWI设置类
	 * @author ShaonanLi
	 */
	public static class BWISettings {
		
		/**生产计划Excel表格配置类*/
		public static final BWIPPExcelInfo ppExcelInfo=new BWIPPExcelInfo20161013();
		
	}
	
}
