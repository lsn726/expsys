package com.logsys.setting;

import com.logsys.setting.pp.MRPSettings;
import com.logsys.setting.pp.bwi.BWIMRPSettings;
import com.logsys.setting.pp.bwi.BWIPPExcelInfo;
import com.logsys.setting.pp.bwi.BWIPPExcelInfo20140528;

/**
 * ������
 * @author ShaonanLi
 */
public class Settings {

	/**
	 * BWI������
	 * @author ShaonanLi
	 */
	public static class BWISettings {
		
		/**�����ƻ�Excel���������*/
		public static final BWIPPExcelInfo ppExcelInfo=new BWIPPExcelInfo20140528();
		
	}
	
	/**MRP������*/
	public static final MRPSettings mrpSetting=new BWIMRPSettings();
	
}
