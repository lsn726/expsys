package com.logsys.setting.pd.bwi.pr;

import com.logsys.production.ProductionInterval;
import com.logsys.production.ProductionInterval.PdInterval;
import com.logsys.setting.pd.bwi.BWIPdExcelInfo;
import com.logsys.util.Location;

/**
 * BWI生产Excel表配置--PR
 * @author lx8sn6
 */
public class BWIPdExcelInfoPR extends BWIPdExcelInfo {
	
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
		VERIFYMAP_LOC_STR.put(new Location(17,11), "F T Q");
		VERIFYMAP_LOC_STR.put(new Location(17,36), "F T Q");
		VERIFYMAP_LOC_STR.put(new Location(17,61), "F T Q");
		VERIFYMAP_LOC_STR.put(new Location(17,16), "Lost time");
		VERIFYMAP_LOC_STR.put(new Location(17,41), "Lost time");
		VERIFYMAP_LOC_STR.put(new Location(17,66), "Lost time");
		VERIFYMAP_LOC_STR.put(new Location(19,11), "Part No\n零件号");
		VERIFYMAP_LOC_STR.put(new Location(19,36), "Part No\n零件号");
		VERIFYMAP_LOC_STR.put(new Location(19,61), "Part No\n零件号");
		VERIFYMAP_LOC_STR.put(new Location(19,12), "Failure Mode\n失效模式");
		VERIFYMAP_LOC_STR.put(new Location(19,37), "Failure Mode\n失效模式");
		VERIFYMAP_LOC_STR.put(new Location(19,62), "Failure Mode\n失效模式");
		VERIFYMAP_LOC_STR.put(new Location(19,13), "Rework\n返工");
		VERIFYMAP_LOC_STR.put(new Location(19,38), "Rework\n返工");
		VERIFYMAP_LOC_STR.put(new Location(19,63), "Rework\n返工");
		VERIFYMAP_LOC_STR.put(new Location(19,14), "Scrap\n报废");
		VERIFYMAP_LOC_STR.put(new Location(19,39), "Scrap\n报废");
		VERIFYMAP_LOC_STR.put(new Location(19,64), "Scrap\n报废");
		VERIFYMAP_LOC_STR.put(new Location(19,15), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(19,40), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(19,65), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(19,16), "Time\n时段");
		VERIFYMAP_LOC_STR.put(new Location(19,41), "Time\n时段");
		VERIFYMAP_LOC_STR.put(new Location(19,66), "Time\n时段");
		VERIFYMAP_LOC_STR.put(new Location(19,17), "Lost time\n损失时间");
		VERIFYMAP_LOC_STR.put(new Location(19,42), "Lost time\n损失时间");
		VERIFYMAP_LOC_STR.put(new Location(19,67), "Lost time\n损失时间");
		VERIFYMAP_LOC_STR.put(new Location(19,18), "LT mode\n损失类型");
		VERIFYMAP_LOC_STR.put(new Location(19,43), "LT mode\n损失类型");
		VERIFYMAP_LOC_STR.put(new Location(19,68), "LT mode\n损失类型");
		VERIFYMAP_LOC_STR.put(new Location(19,19), "Cause\n原因");
		VERIFYMAP_LOC_STR.put(new Location(19,44), "Cause\n原因");
		VERIFYMAP_LOC_STR.put(new Location(19,69), "Cause\n原因");
		VERIFYMAP_LOC_STR.put(new Location(19,20), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(19,45), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(19,70), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(47,4), "累计\nTotal");
		VERIFYMAP_LOC_STR.put(new Location(47,29), "累计\nTotal");
		VERIFYMAP_LOC_STR.put(new Location(47,54), "累计\nTotal");
		VERIFYMAP_LOC_STR.put(new Location(11,2), "8:15-9:00");
		VERIFYMAP_LOC_STR.put(new Location(11,27), "16:45-18:00");
		VERIFYMAP_LOC_STR.put(new Location(11,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(15,2), "9:00-10:00");
		VERIFYMAP_LOC_STR.put(new Location(15,27), "18:00-19:00");
		VERIFYMAP_LOC_STR.put(new Location(15,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(19,2), "10:00-11:00");
		VERIFYMAP_LOC_STR.put(new Location(19,27), "19:00-20:00");
		VERIFYMAP_LOC_STR.put(new Location(19,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(23,2), "11:00-12:00");
		VERIFYMAP_LOC_STR.put(new Location(23,27), "20:00-21:00");
		VERIFYMAP_LOC_STR.put(new Location(23,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(27,2), "12:00-13:00");
		VERIFYMAP_LOC_STR.put(new Location(27,27), "21:00-22:00");
		VERIFYMAP_LOC_STR.put(new Location(27,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(31,2), "13:00-14:00");
		VERIFYMAP_LOC_STR.put(new Location(31,27), "22:00-23:00");
		VERIFYMAP_LOC_STR.put(new Location(31,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(35,2), "14:00-15:00");
		VERIFYMAP_LOC_STR.put(new Location(35,27), "23:00-0:00");
		VERIFYMAP_LOC_STR.put(new Location(35,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(39,2), "15:00-16:00");
		VERIFYMAP_LOC_STR.put(new Location(39,27), "0:00-01:15");
		VERIFYMAP_LOC_STR.put(new Location(39,52), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(43,2), "16:00-16:45");
		VERIFYMAP_LOC_STR.put(new Location(43,27), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(43,52), EMPTY_STR);
	}
	
	protected void initProdAliasStdPnMap() {
		PRODMAP_ALIAS_STDPN.put("22271386 C7 前长", "22271386");
		PRODMAP_ALIAS_STDPN.put("22261386 C7前长奥迪", "22271386");
		PRODMAP_ALIAS_STDPN.put("22271387  C7 前短", "22271387");
		PRODMAP_ALIAS_STDPN.put("22271402 C7 后长", "22271402");
		PRODMAP_ALIAS_STDPN.put("22271403 C7 后短", "22271403");
		PRODMAP_ALIAS_STDPN.put("22261415  L7 后", "22261415");
		PRODMAP_ALIAS_STDPN.put("22261435  L7 前", "22261435");
		PRODMAP_ALIAS_STDPN.put("22271172 CQAC 前", "22271172");
		PRODMAP_ALIAS_STDPN.put("22271119 CQAC后", "22271119");
		PRODMAP_ALIAS_STDPN.put("22262054 VOLVO 前", "22262054");
		PRODMAP_ALIAS_STDPN.put("222261455 VOLVO后", "22261455");
		PRODMAP_ALIAS_STDPN.put("22261455 VOLVO后", "22261455");
		PRODMAP_ALIAS_STDPN.put("22261455 VOLIVO 后", "22261455");
		PRODMAP_ALIAS_STDPN.put("22261387 C7 前短奥迪", "22261387");
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
		PRODMAP_LOC_INTERVAL.put(new Location(11,33), new ProductionInterval(PdInterval.Middle1,16,45,18,0,75));
		PRODMAP_LOC_INTERVAL.put(new Location(12,33), new ProductionInterval(PdInterval.Middle1,16,45,18,0,75));
		PRODMAP_LOC_INTERVAL.put(new Location(13,33), new ProductionInterval(PdInterval.Middle1,16,45,18,0,75));
		PRODMAP_LOC_INTERVAL.put(new Location(14,33), new ProductionInterval(PdInterval.Middle1,16,45,18,0,75));
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
		PRODMAP_LOC_INTERVAL.put(new Location(28,33), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(29,33), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(30,33), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(31,33), new ProductionInterval(PdInterval.Middle6,22,0,23,0,40));
		PRODMAP_LOC_INTERVAL.put(new Location(32,33), new ProductionInterval(PdInterval.Middle6,22,0,23,0,40));
		PRODMAP_LOC_INTERVAL.put(new Location(33,33), new ProductionInterval(PdInterval.Middle6,22,0,23,0,40));
		PRODMAP_LOC_INTERVAL.put(new Location(34,33), new ProductionInterval(PdInterval.Middle6,22,0,23,0,40));
		PRODMAP_LOC_INTERVAL.put(new Location(35,33), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(36,33), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(37,33), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(38,33), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(39,33), new ProductionInterval(PdInterval.Middle8,24,0,25,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(40,33), new ProductionInterval(PdInterval.Middle8,24,0,25,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(41,33), new ProductionInterval(PdInterval.Middle8,24,0,25,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(42,33), new ProductionInterval(PdInterval.Middle8,24,0,25,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(43,33), new ProductionInterval(PdInterval.Middle9,25,15,26,15,60));	//需要目视验证！
		PRODMAP_LOC_INTERVAL.put(new Location(44,33), new ProductionInterval(PdInterval.Middle9,25,15,26,15,60));	//需要目视验证！
		PRODMAP_LOC_INTERVAL.put(new Location(45,33), new ProductionInterval(PdInterval.Middle9,25,15,26,15,60));	//需要目视验证！
		PRODMAP_LOC_INTERVAL.put(new Location(46,33), new ProductionInterval(PdInterval.Middle9,25,15,26,15,60));	//需要目视验证！
		PRODMAP_LOC_INTERVAL.put(new Location(11,58), new ProductionInterval(PdInterval.Night1,26,15,26,0,45));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(12,58), new ProductionInterval(PdInterval.Night1,25,15,26,0,45));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(13,58), new ProductionInterval(PdInterval.Night1,25,15,26,0,45));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(14,58), new ProductionInterval(PdInterval.Night1,25,15,26,0,45));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(15,58), new ProductionInterval(PdInterval.Night2,26,0,27,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(16,58), new ProductionInterval(PdInterval.Night2,26,0,27,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(17,58), new ProductionInterval(PdInterval.Night2,26,0,27,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(18,58), new ProductionInterval(PdInterval.Night2,26,0,27,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(19,58), new ProductionInterval(PdInterval.Night3,27,0,28,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(20,58), new ProductionInterval(PdInterval.Night3,27,0,28,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(21,58), new ProductionInterval(PdInterval.Night3,27,0,28,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(22,58), new ProductionInterval(PdInterval.Night3,27,0,28,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(23,58), new ProductionInterval(PdInterval.Night4,28,0,29,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(24,58), new ProductionInterval(PdInterval.Night4,28,0,29,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(25,58), new ProductionInterval(PdInterval.Night4,28,0,29,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(26,58), new ProductionInterval(PdInterval.Night4,28,0,29,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(27,58), new ProductionInterval(PdInterval.Night5,29,0,30,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(28,58), new ProductionInterval(PdInterval.Night5,29,0,30,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(29,58), new ProductionInterval(PdInterval.Night5,29,0,30,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(30,58), new ProductionInterval(PdInterval.Night5,29,0,30,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(31,58), new ProductionInterval(PdInterval.Night6,30,0,31,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(32,58), new ProductionInterval(PdInterval.Night6,30,0,31,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(33,58), new ProductionInterval(PdInterval.Night6,30,0,31,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(34,58), new ProductionInterval(PdInterval.Night6,30,0,31,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(35,58), new ProductionInterval(PdInterval.Night7,31,0,32,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(36,58), new ProductionInterval(PdInterval.Night7,31,0,32,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(37,58), new ProductionInterval(PdInterval.Night7,31,0,32,0,60));		//需要目视确认
		PRODMAP_LOC_INTERVAL.put(new Location(38,58), new ProductionInterval(PdInterval.Night7,31,0,32,0,60));		//需要目视确认
	}

	@Override
	public Location getDateLocation() {
		return new Location(4,9);
	}
	
}
