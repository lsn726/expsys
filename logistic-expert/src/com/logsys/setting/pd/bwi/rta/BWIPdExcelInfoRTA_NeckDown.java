package com.logsys.setting.pd.bwi.rta;

import com.logsys.util.Location;

/**
 * BWI生产Excel表配置--RTA--NeckDown
 * @author lx8sn6
 */
public class BWIPdExcelInfoRTA_NeckDown extends BWIPdExcelInfoRTA {

	protected void initLocVerifyStrMap() {
		VERIFYMAP_LOC_STR.put(new Location(4,2), "班次/班组:\nShift/Group");
		VERIFYMAP_LOC_STR.put(new Location(4,27), "班次/班组:\nShift/group");
		VERIFYMAP_LOC_STR.put(new Location(4,52), "班次/班组:\nShift/group");
		VERIFYMAP_LOC_STR.put(new Location(4,8), "日期\nDate");
		VERIFYMAP_LOC_STR.put(new Location(4,33), "日期\nDate");
		VERIFYMAP_LOC_STR.put(new Location(4,58), "日期\nDate");
		VERIFYMAP_LOC_STR.put(new Location(8,2), "时段\nHour");
		VERIFYMAP_LOC_STR.put(new Location(8,27), "时段\nHour");
		VERIFYMAP_LOC_STR.put(new Location(8,52), "时段\nHour");
		VERIFYMAP_LOC_STR.put(new Location(8,5), "上班人数");
		VERIFYMAP_LOC_STR.put(new Location(8,30), "上班人数");
		VERIFYMAP_LOC_STR.put(new Location(8,55), "上班人数");
		VERIFYMAP_LOC_STR.put(new Location(8,6), "零件号\nPart No");
		VERIFYMAP_LOC_STR.put(new Location(8,31), "零件号\nPart No");
		VERIFYMAP_LOC_STR.put(new Location(8,56), "零件号\nPart No");
		VERIFYMAP_LOC_STR.put(new Location(8,8), "小时\n产出\nHourly Count");
		VERIFYMAP_LOC_STR.put(new Location(8,33), "小时\n产出\nHourly Count");
		VERIFYMAP_LOC_STR.put(new Location(8,58), "小时\n产出\nHourly Count");
		VERIFYMAP_LOC_STR.put(new Location(47,4), "累计\nTotal");
		VERIFYMAP_LOC_STR.put(new Location(47,29), "累计\nTotal");
		VERIFYMAP_LOC_STR.put(new Location(47,54), "累计\nTotal");
		VERIFYMAP_LOC_STR.put(new Location(11,2), "8:15-9:00");
		VERIFYMAP_LOC_STR.put(new Location(11,27), "16:45-17:00");
		//VERIFYMAP_LOC_STR.put(new Location(11,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(15,2), "9:00-10:00");
		VERIFYMAP_LOC_STR.put(new Location(15,27), "17:00-18:00");
		//VERIFYMAP_LOC_STR.put(new Location(15,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(19,2), "10:00-11:00");
		VERIFYMAP_LOC_STR.put(new Location(19,27), "18:00-19:00");
		//VERIFYMAP_LOC_STR.put(new Location(19,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(23,2), "11:00-12:00");
		VERIFYMAP_LOC_STR.put(new Location(23,27), "19:00-20:00");
		//VERIFYMAP_LOC_STR.put(new Location(23,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(27,2), "12:00-13:00");
		VERIFYMAP_LOC_STR.put(new Location(27,27), "20:00-21:00");
		//VERIFYMAP_LOC_STR.put(new Location(27,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(31,2), "13:00-14:00");
		VERIFYMAP_LOC_STR.put(new Location(31,27), "21:00-22:00");
		//VERIFYMAP_LOC_STR.put(new Location(31,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(35,2), "14:00-15:00");
		VERIFYMAP_LOC_STR.put(new Location(35,27), "22:00-23:00");
		//VERIFYMAP_LOC_STR.put(new Location(35,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(39,2), "15:00-16:00");
		VERIFYMAP_LOC_STR.put(new Location(39,27), "23:00-24:00");
		//VERIFYMAP_LOC_STR.put(new Location(39,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(43,2), "16:00-16:45");
		VERIFYMAP_LOC_STR.put(new Location(43,27), "0:00-01:15");
		VERIFYMAP_LOC_STR.put(new Location(43,52), EMPTY_STR);
	}
	
}
