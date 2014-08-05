package com.logsys.setting.pd.bwi.rta;

import com.logsys.production.ProductionInterval;
import com.logsys.production.ProductionInterval.PdInterval;
import com.logsys.util.Location;

/**
 * BWI生产Excel表配置--RTA--倒角
 * @author ShaonanLi
 */
public class BWIPdExcelInfoRTA_Chemfer extends BWIPdExcelInfoRTA {
	
	@Override
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
		VERIFYMAP_LOC_STR.put(new Location(11,27), "17:00-18:00");
		VERIFYMAP_LOC_STR.put(new Location(11,52), "02:30-03:30");
		VERIFYMAP_LOC_STR.put(new Location(15,2), "9:00-10:00");
		VERIFYMAP_LOC_STR.put(new Location(15,27), "18:00-19:00");
		VERIFYMAP_LOC_STR.put(new Location(15,52), "03:30-04:30");
		VERIFYMAP_LOC_STR.put(new Location(19,2), "10:00-11:00");
		VERIFYMAP_LOC_STR.put(new Location(19,27), "19:00-20:00");
		VERIFYMAP_LOC_STR.put(new Location(19,52), "04:30-05:30");
		VERIFYMAP_LOC_STR.put(new Location(23,2), "11:00-12:00");
		VERIFYMAP_LOC_STR.put(new Location(23,27), "20:00-21:00");
		VERIFYMAP_LOC_STR.put(new Location(23,52), "05:30-06:30");
		VERIFYMAP_LOC_STR.put(new Location(27,2), "12:00-13:00");
		VERIFYMAP_LOC_STR.put(new Location(27,27), "21:00-22:00");
		VERIFYMAP_LOC_STR.put(new Location(27,52), "06:30-07:30");
		VERIFYMAP_LOC_STR.put(new Location(31,2), "13:00-14:00");
		VERIFYMAP_LOC_STR.put(new Location(31,27), "22:00-23:00");
		VERIFYMAP_LOC_STR.put(new Location(31,52), "07:30-08:00");
		VERIFYMAP_LOC_STR.put(new Location(35,2), "14:00-15:00");
		VERIFYMAP_LOC_STR.put(new Location(35,27), "23:00-00:00");
		VERIFYMAP_LOC_STR.put(new Location(35,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(39,2), "15:00-16:00");
		VERIFYMAP_LOC_STR.put(new Location(39,27), "00:00-01:00");
		VERIFYMAP_LOC_STR.put(new Location(39,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(43,2), "16:00-16:45");
		VERIFYMAP_LOC_STR.put(new Location(43,27), "01:00-01:30");
		VERIFYMAP_LOC_STR.put(new Location(43,52), EMPTY_STR);
	}

	@Override
	protected void initProdInterval() {
		PRODMAP_LOC_INTERVAL.put(new Location(11,8), new ProductionInterval(PdInterval.Early1,8,15,9,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(12,8), new ProductionInterval(PdInterval.Early1,8,15,9,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(13,8), new ProductionInterval(PdInterval.Early1,8,15,9,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(14,8), new ProductionInterval(PdInterval.Early1,8,15,9,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(15,8), new ProductionInterval(PdInterval.Early2,9,0,10,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(16,8), new ProductionInterval(PdInterval.Early2,9,0,10,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(17,8), new ProductionInterval(PdInterval.Early2,9,0,10,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(18,8), new ProductionInterval(PdInterval.Early2,9,0,10,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(19,8), new ProductionInterval(PdInterval.Early3,10,0,11,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(20,8), new ProductionInterval(PdInterval.Early3,10,0,11,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(21,8), new ProductionInterval(PdInterval.Early3,10,0,11,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(22,8), new ProductionInterval(PdInterval.Early3,10,0,11,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(23,8), new ProductionInterval(PdInterval.Early4,11,0,12,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(24,8), new ProductionInterval(PdInterval.Early4,11,0,12,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(25,8), new ProductionInterval(PdInterval.Early4,11,0,12,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(26,8), new ProductionInterval(PdInterval.Early4,11,0,12,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(27,8), new ProductionInterval(PdInterval.Early5,12,0,13,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(28,8), new ProductionInterval(PdInterval.Early5,12,0,13,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(29,8), new ProductionInterval(PdInterval.Early5,12,0,13,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(30,8), new ProductionInterval(PdInterval.Early5,12,0,13,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(31,8), new ProductionInterval(PdInterval.Early6,13,0,14,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(32,8), new ProductionInterval(PdInterval.Early6,13,0,14,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(33,8), new ProductionInterval(PdInterval.Early6,13,0,14,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(34,8), new ProductionInterval(PdInterval.Early6,13,0,14,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(35,8), new ProductionInterval(PdInterval.Early7,14,0,15,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(36,8), new ProductionInterval(PdInterval.Early7,14,0,15,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(37,8), new ProductionInterval(PdInterval.Early7,14,0,15,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(38,8), new ProductionInterval(PdInterval.Early7,14,0,15,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(39,8), new ProductionInterval(PdInterval.Early8,15,0,16,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(40,8), new ProductionInterval(PdInterval.Early8,15,0,16,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(41,8), new ProductionInterval(PdInterval.Early8,15,0,16,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(42,8), new ProductionInterval(PdInterval.Early8,15,0,16,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(43,8), new ProductionInterval(PdInterval.Early9,16,0,16,45,45));
		PRODMAP_LOC_INTERVAL.put(new Location(44,8), new ProductionInterval(PdInterval.Early9,16,0,16,45,45));
		PRODMAP_LOC_INTERVAL.put(new Location(45,8), new ProductionInterval(PdInterval.Early9,16,0,16,45,45));
		PRODMAP_LOC_INTERVAL.put(new Location(46,8), new ProductionInterval(PdInterval.Early9,16,0,16,45,45));
		PRODMAP_LOC_INTERVAL.put(new Location(11,33), new ProductionInterval(PdInterval.Middle1,17,0,18,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(12,33), new ProductionInterval(PdInterval.Middle1,17,0,18,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(13,33), new ProductionInterval(PdInterval.Middle1,17,0,18,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(14,33), new ProductionInterval(PdInterval.Middle1,17,0,18,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(15,33), new ProductionInterval(PdInterval.Middle2,18,0,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(16,33), new ProductionInterval(PdInterval.Middle2,18,0,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(17,33), new ProductionInterval(PdInterval.Middle2,18,0,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(18,33), new ProductionInterval(PdInterval.Middle2,18,0,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(19,33), new ProductionInterval(PdInterval.Middle3,19,0,20,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(20,33), new ProductionInterval(PdInterval.Middle3,19,0,20,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(21,33), new ProductionInterval(PdInterval.Middle3,19,0,20,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(22,33), new ProductionInterval(PdInterval.Middle3,19,0,20,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(23,33), new ProductionInterval(PdInterval.Middle4,20,0,21,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(24,33), new ProductionInterval(PdInterval.Middle4,20,0,21,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(25,33), new ProductionInterval(PdInterval.Middle4,20,0,21,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(26,33), new ProductionInterval(PdInterval.Middle4,20,0,21,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(27,33), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(28,33), new ProductionInterval(PdInterval.Middle5,22,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(29,33), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(30,33), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(31,33), new ProductionInterval(PdInterval.Middle6,22,0,23,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(32,33), new ProductionInterval(PdInterval.Middle6,22,0,23,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(33,33), new ProductionInterval(PdInterval.Middle6,22,0,23,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(34,33), new ProductionInterval(PdInterval.Middle6,22,0,23,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(35,33), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(36,33), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(37,33), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(38,33), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(39,33), new ProductionInterval(PdInterval.Middle8,24,0,25,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(40,33), new ProductionInterval(PdInterval.Middle8,24,0,25,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(41,33), new ProductionInterval(PdInterval.Middle8,24,0,25,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(42,33), new ProductionInterval(PdInterval.Middle8,24,0,25,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(43,33), new ProductionInterval(PdInterval.Middle9,25,0,25,30,30));
		PRODMAP_LOC_INTERVAL.put(new Location(44,33), new ProductionInterval(PdInterval.Middle9,25,0,25,30,30));
		PRODMAP_LOC_INTERVAL.put(new Location(45,33), new ProductionInterval(PdInterval.Middle9,25,0,25,30,30));
		PRODMAP_LOC_INTERVAL.put(new Location(46,33), new ProductionInterval(PdInterval.Middle9,25,0,25,30,30));
		PRODMAP_LOC_INTERVAL.put(new Location(11,58), new ProductionInterval(PdInterval.Night1,26,30,27,30,60));
		PRODMAP_LOC_INTERVAL.put(new Location(11,58), new ProductionInterval(PdInterval.Night2,27,30,28,30,60));
		PRODMAP_LOC_INTERVAL.put(new Location(11,58), new ProductionInterval(PdInterval.Night3,28,30,29,30,60));
		PRODMAP_LOC_INTERVAL.put(new Location(11,58), new ProductionInterval(PdInterval.Night4,29,30,30,30,60));
		PRODMAP_LOC_INTERVAL.put(new Location(11,58), new ProductionInterval(PdInterval.Night5,30,30,31,30,60));
		PRODMAP_LOC_INTERVAL.put(new Location(11,58), new ProductionInterval(PdInterval.Night6,31,30,32,0,30));
	}
	
}
