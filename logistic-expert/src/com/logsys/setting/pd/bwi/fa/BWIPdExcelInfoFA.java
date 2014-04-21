package com.logsys.setting.pd.bwi.fa;

import com.logsys.production.ProductionInterval;
import com.logsys.production.ProductionInterval.PdInterval;
import com.logsys.setting.pd.bwi.BWIPdExcelInfo;
import com.logsys.util.Location;

/**
 * BWI生产Excel表配置--RTA
 * @author lx8sn6
 */
public class BWIPdExcelInfoFA extends BWIPdExcelInfo {
	
	protected void initLocVerifyStrMap() {
		VERIFYMAP_LOC_STR.put(new Location(4,2), "班次/班组:\nShift/Group");
		VERIFYMAP_LOC_STR.put(new Location(4,29), "班次/班组:\nShift/group");
		VERIFYMAP_LOC_STR.put(new Location(4,56), "班次/班组:\nShift/group");
		VERIFYMAP_LOC_STR.put(new Location(4,10), "日期\nDate");
		VERIFYMAP_LOC_STR.put(new Location(4,37), "日期\nDate");
		VERIFYMAP_LOC_STR.put(new Location(4,64), "日期\nDate");
		VERIFYMAP_LOC_STR.put(new Location(8,2), "时段\nHour");
		VERIFYMAP_LOC_STR.put(new Location(8,29), "时段\nHour");
		VERIFYMAP_LOC_STR.put(new Location(8,56), "时段\nHour");
		VERIFYMAP_LOC_STR.put(new Location(8,7), "上班人数");
		VERIFYMAP_LOC_STR.put(new Location(8,34), "上班人数");
		VERIFYMAP_LOC_STR.put(new Location(8,61), "上班人数");
		VERIFYMAP_LOC_STR.put(new Location(8,8), "零件号\nPart No");
		VERIFYMAP_LOC_STR.put(new Location(8,35), "零件号\nPart No");
		VERIFYMAP_LOC_STR.put(new Location(8,62), "零件号\nPart No");
		VERIFYMAP_LOC_STR.put(new Location(8,10), "小时\n产出\nHourly Count");
		VERIFYMAP_LOC_STR.put(new Location(8,37), "小时\n产出\nHourly Count");
		VERIFYMAP_LOC_STR.put(new Location(8,64), "小时\n产出\nHourly Count");
		VERIFYMAP_LOC_STR.put(new Location(25,13), "F T Q");
		VERIFYMAP_LOC_STR.put(new Location(25,40), "F T Q");
		VERIFYMAP_LOC_STR.put(new Location(25,67), "F T Q");
		VERIFYMAP_LOC_STR.put(new Location(25,18), "Lost time");
		VERIFYMAP_LOC_STR.put(new Location(25,45), "Lost time");
		VERIFYMAP_LOC_STR.put(new Location(25,72), "Lost time");
		VERIFYMAP_LOC_STR.put(new Location(27,13), "Part No\n零件号");
		VERIFYMAP_LOC_STR.put(new Location(27,40), "Part No\n零件号");
		VERIFYMAP_LOC_STR.put(new Location(27,67), "Part No\n零件号");
		VERIFYMAP_LOC_STR.put(new Location(27,14), "Failure Mode\n失效模式");
		VERIFYMAP_LOC_STR.put(new Location(27,41), "Failure Mode\n失效模式");
		VERIFYMAP_LOC_STR.put(new Location(27,68), "Failure Mode\n失效模式");
		VERIFYMAP_LOC_STR.put(new Location(27,15), "Rework\n返工");
		VERIFYMAP_LOC_STR.put(new Location(27,42), "Rework\n返工");
		VERIFYMAP_LOC_STR.put(new Location(27,69), "Rework\n返工");
		VERIFYMAP_LOC_STR.put(new Location(27,16), "Scrap\n报废");
		VERIFYMAP_LOC_STR.put(new Location(27,43), "Scrap\n报废");
		VERIFYMAP_LOC_STR.put(new Location(27,70), "Scrap\n报废");
		VERIFYMAP_LOC_STR.put(new Location(27,17), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(27,44), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(27,71), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(27,18), "Time\n时段");
		VERIFYMAP_LOC_STR.put(new Location(27,45), "Time\n时段");
		VERIFYMAP_LOC_STR.put(new Location(27,72), "Time\n时段");
		VERIFYMAP_LOC_STR.put(new Location(27,19), "Lost time\n损失时间");
		VERIFYMAP_LOC_STR.put(new Location(27,46), "Lost time\n损失时间");
		VERIFYMAP_LOC_STR.put(new Location(27,73), "Lost time\n损失时间");
		VERIFYMAP_LOC_STR.put(new Location(27,20), "LT mode\n损失类型");
		VERIFYMAP_LOC_STR.put(new Location(27,47), "LT mode\n损失类型");
		VERIFYMAP_LOC_STR.put(new Location(27,74), "LT mode\n损失类型");
		VERIFYMAP_LOC_STR.put(new Location(27,21), "Cause\n原因");
		VERIFYMAP_LOC_STR.put(new Location(27,48), "Cause\n原因");
		VERIFYMAP_LOC_STR.put(new Location(27,75), "Cause\n原因");
		VERIFYMAP_LOC_STR.put(new Location(27,22), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(27,49), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(27,76), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(47,6), "累计\nTotal");
		VERIFYMAP_LOC_STR.put(new Location(47,33), "累计\nTotal");
		VERIFYMAP_LOC_STR.put(new Location(47,60), "累计\nTotal");
		VERIFYMAP_LOC_STR.put(new Location(11,2), "0.34375");
		VERIFYMAP_LOC_STR.put(new Location(11,4), "0.375");
		VERIFYMAP_LOC_STR.put(new Location(11,29), "0.708333333333333");
		VERIFYMAP_LOC_STR.put(new Location(11,31), "0.75");
		VERIFYMAP_LOC_STR.put(new Location(11,56), "0.0833333333333333");
		VERIFYMAP_LOC_STR.put(new Location(11,58), "0.125");
		VERIFYMAP_LOC_STR.put(new Location(15,2), "0.375");
		VERIFYMAP_LOC_STR.put(new Location(15,4), "0.416666666666667");
		VERIFYMAP_LOC_STR.put(new Location(15,29), "0.770833333333333");
		VERIFYMAP_LOC_STR.put(new Location(15,31), "0.791666666666667");
		VERIFYMAP_LOC_STR.put(new Location(15,56), "0.131944444444444");
		VERIFYMAP_LOC_STR.put(new Location(15,58), "0.166666666666667");
		VERIFYMAP_LOC_STR.put(new Location(19,2), "0.423611111111111");
		VERIFYMAP_LOC_STR.put(new Location(19,4), "0.458333333333333");
		VERIFYMAP_LOC_STR.put(new Location(19,29), "0.791666666666667");
		VERIFYMAP_LOC_STR.put(new Location(19,31), "0.84375");
		VERIFYMAP_LOC_STR.put(new Location(19,56), "0.166666666666667");
		VERIFYMAP_LOC_STR.put(new Location(19,58), "0.208333333333333");
		VERIFYMAP_LOC_STR.put(new Location(23,2), "0.458333333333333");
		VERIFYMAP_LOC_STR.put(new Location(23,4), "0.5");
		VERIFYMAP_LOC_STR.put(new Location(23,29), "0.84375");
		VERIFYMAP_LOC_STR.put(new Location(23,31), "0.875");
		VERIFYMAP_LOC_STR.put(new Location(23,56), "0.208333333333333");
		VERIFYMAP_LOC_STR.put(new Location(23,58), "0.25");
		VERIFYMAP_LOC_STR.put(new Location(27,2), "0.520833333333333");
		VERIFYMAP_LOC_STR.put(new Location(27,4), "0.541666666666667");
		VERIFYMAP_LOC_STR.put(new Location(27,29), "0.875");
		VERIFYMAP_LOC_STR.put(new Location(27,31), "0.916666666666667");
		VERIFYMAP_LOC_STR.put(new Location(27,56), "0.270833333333333");
		VERIFYMAP_LOC_STR.put(new Location(27,58), "0.291666666666667");
		VERIFYMAP_LOC_STR.put(new Location(31,2), "0.541666666666667");
		VERIFYMAP_LOC_STR.put(new Location(31,4), "0.583333333333334");
		VERIFYMAP_LOC_STR.put(new Location(31,29), "0.923611111111111");
		VERIFYMAP_LOC_STR.put(new Location(31,31), "0.958333333333333");
		VERIFYMAP_LOC_STR.put(new Location(31,56), "0.291666666666667");
		VERIFYMAP_LOC_STR.put(new Location(31,58), "0.34375");
		VERIFYMAP_LOC_STR.put(new Location(35,2), "0.583333333333333");
		VERIFYMAP_LOC_STR.put(new Location(35,4), "0.625");
		VERIFYMAP_LOC_STR.put(new Location(35,29), "0.958333333333333");
		VERIFYMAP_LOC_STR.put(new Location(35,31), "1");
		VERIFYMAP_LOC_STR.put(new Location(35,56), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(35,58), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(39,2), "0.631944444444444");
		VERIFYMAP_LOC_STR.put(new Location(39,4), "0.666666666666667");
		VERIFYMAP_LOC_STR.put(new Location(39,29), "0.0208333333333333");
		VERIFYMAP_LOC_STR.put(new Location(39,31), "0.0416666666666667");
		VERIFYMAP_LOC_STR.put(new Location(39,56), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(39,58), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(43,2), "0.666666666666667");
		VERIFYMAP_LOC_STR.put(new Location(43,4), "0.708333333333333");
		VERIFYMAP_LOC_STR.put(new Location(43,29), "0.0416666666666667");
		VERIFYMAP_LOC_STR.put(new Location(43,31), "0.0833333333333333");
		VERIFYMAP_LOC_STR.put(new Location(43,56), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(43,58), EMPTY_STR);
	}
	
	protected void initProdAliasStdPnMap() {
		PRODMAP_ALIAS_STDPN.put("22261665 奥迪前长", "22261665");
		PRODMAP_ALIAS_STDPN.put("22261664 奥迪前短", "22261664");
		PRODMAP_ALIAS_STDPN.put("22258275 奥迪后长", "22258275");
		PRODMAP_ALIAS_STDPN.put("22258280 奥迪后短", "22258280");
		PRODMAP_ALIAS_STDPN.put("22265450 宝马前", "22265450");
		PRODMAP_ALIAS_STDPN.put("22261401 宝马后", "22261401");
		PRODMAP_ALIAS_STDPN.put("22262045 沃尔沃前", "22262045");
		PRODMAP_ALIAS_STDPN.put("22262043 沃尔沃前", "22262043");
		PRODMAP_ALIAS_STDPN.put("22262043 VOLVO前", "22262043");
		PRODMAP_ALIAS_STDPN.put("22262043 沃尔沃前左", "22262043");
		PRODMAP_ALIAS_STDPN.put("22262044 沃尔沃前右", "22262044");
		PRODMAP_ALIAS_STDPN.put("22261449 沃尔沃后", "22261449");
		PRODMAP_ALIAS_STDPN.put("22261450 沃尔沃后", "22261450");
		PRODMAP_ALIAS_STDPN.put("22272186 奇瑞前", "22272186");
		PRODMAP_ALIAS_STDPN.put("22272186 CQAC前", "22272186");
		PRODMAP_ALIAS_STDPN.put("22272191 奇瑞前", "22272191");
		PRODMAP_ALIAS_STDPN.put("22272184 奇瑞前左", "22272184");
		PRODMAP_ALIAS_STDPN.put("22272003 奇瑞后", "22272003");
		PRODMAP_ALIAS_STDPN.put("22271368 奇瑞后", "22271368");
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
		PRODMAP_LOC_INTERVAL.put(new Location(11,33), new ProductionInterval(PdInterval.Middle1,16,45,17,0,15));
		PRODMAP_LOC_INTERVAL.put(new Location(12,33), new ProductionInterval(PdInterval.Middle1,16,45,17,0,15));
		PRODMAP_LOC_INTERVAL.put(new Location(13,33), new ProductionInterval(PdInterval.Middle1,16,45,17,0,15));
		PRODMAP_LOC_INTERVAL.put(new Location(14,33), new ProductionInterval(PdInterval.Middle1,16,45,17,0,15));
		PRODMAP_LOC_INTERVAL.put(new Location(15,33), new ProductionInterval(PdInterval.Middle2,17,0,18,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(16,33), new ProductionInterval(PdInterval.Middle2,17,0,18,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(17,33), new ProductionInterval(PdInterval.Middle2,17,0,18,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(18,33), new ProductionInterval(PdInterval.Middle2,17,0,18,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(19,33), new ProductionInterval(PdInterval.Middle3,18,0,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(20,33), new ProductionInterval(PdInterval.Middle3,18,0,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(21,33), new ProductionInterval(PdInterval.Middle3,18,0,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(22,33), new ProductionInterval(PdInterval.Middle3,18,0,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(23,33), new ProductionInterval(PdInterval.Middle4,19,0,20,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(24,33), new ProductionInterval(PdInterval.Middle4,19,0,20,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(25,33), new ProductionInterval(PdInterval.Middle4,19,0,20,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(26,33), new ProductionInterval(PdInterval.Middle4,19,0,20,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(27,33), new ProductionInterval(PdInterval.Middle5,20,0,21,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(28,33), new ProductionInterval(PdInterval.Middle5,20,0,21,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(29,33), new ProductionInterval(PdInterval.Middle5,20,0,21,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(30,33), new ProductionInterval(PdInterval.Middle5,20,0,21,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(31,33), new ProductionInterval(PdInterval.Middle6,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(32,33), new ProductionInterval(PdInterval.Middle6,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(33,33), new ProductionInterval(PdInterval.Middle6,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(34,33), new ProductionInterval(PdInterval.Middle6,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(35,33), new ProductionInterval(PdInterval.Middle7,22,0,23,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(36,33), new ProductionInterval(PdInterval.Middle7,22,0,23,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(37,33), new ProductionInterval(PdInterval.Middle7,22,0,23,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(38,33), new ProductionInterval(PdInterval.Middle7,22,0,23,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(39,33), new ProductionInterval(PdInterval.Middle8,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(40,33), new ProductionInterval(PdInterval.Middle8,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(41,33), new ProductionInterval(PdInterval.Middle8,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(42,33), new ProductionInterval(PdInterval.Middle8,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(43,33), new ProductionInterval(PdInterval.Middle9,24,0,25,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(44,33), new ProductionInterval(PdInterval.Middle9,24,0,25,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(45,33), new ProductionInterval(PdInterval.Middle9,24,0,25,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(46,33), new ProductionInterval(PdInterval.Middle9,24,0,25,15,75));
	}

	@Override
	public Location getDateLocation() {
		return new Location(4,11);
	}
	
}
