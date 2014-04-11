package com.logsys.setting;

import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.setting.pd.bwi.BWIPLInfo20140331;
import com.logsys.setting.pd.bwi.BWIProductionExcelInfo;
import com.logsys.setting.pd.bwi.BWIProductionExcelInfo20140411;
import com.logsys.setting.pp.bwi.BWIPPExcelInfo;
import com.logsys.setting.pp.bwi.BWIPPExcelInfo20140410;

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
		public static final BWIPPExcelInfo ppExcelInfo=new BWIPPExcelInfo20140410();
		
		/**��������Ϣ������*/
		public static final BWIPLInfo plinfo=new BWIPLInfo20140331();
		
		/**����Excel��Ϣ������*/
		public static final BWIProductionExcelInfo pdExcelInfo=new BWIProductionExcelInfo20140411();
		
	}
	
}
