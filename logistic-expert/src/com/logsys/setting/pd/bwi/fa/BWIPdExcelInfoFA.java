package com.logsys.setting.pd.bwi.fa;

import com.logsys.production.ProductionInterval;
import com.logsys.production.ProductionInterval.PdInterval;
import com.logsys.setting.pd.bwi.BWIPdExcelInfo;
import com.logsys.util.Location;

/**
 * BWI生产Excel表配置--FA
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
		/**
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
		*/
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
		PRODMAP_ALIAS_STDPN.put("22271371 C7 前长重", "22271371");
		PRODMAP_ALIAS_STDPN.put("22271373  C7 前短重", "22271373");
		PRODMAP_ALIAS_STDPN.put("22271375 C7 后长", "22271375");
		PRODMAP_ALIAS_STDPN.put("22271375  C7 后长", "22271375");
		PRODMAP_ALIAS_STDPN.put("22271376 C7 后短", "22271376");
		PRODMAP_ALIAS_STDPN.put("22265578 L421 前左", "22265578");
		PRODMAP_ALIAS_STDPN.put("22265577 L421 前右", "22265577");
		PRODMAP_ALIAS_STDPN.put("22265581 L421 后", "22265581");
		PRODMAP_ALIAS_STDPN.put("22265581 沃尔沃后", "22265581");
		PRODMAP_ALIAS_STDPN.put("22272176 CF-11 前左", "22272176");
		PRODMAP_ALIAS_STDPN.put("22272177 CF-11 前右", "22272177");
		PRODMAP_ALIAS_STDPN.put("22271789 CF-11 后", "22271789");
		PRODMAP_ALIAS_STDPN.put("22271789 奇瑞后", "22271789");
		PRODMAP_ALIAS_STDPN.put("22261400 L7 后", "22261400");
		PRODMAP_ALIAS_STDPN.put("22261420 L7 前", "22261420");
		PRODMAP_ALIAS_STDPN.put("22272138 L7 后", "22272138");
		PRODMAP_ALIAS_STDPN.put("22272171 L7 前", "22272171");
		PRODMAP_ALIAS_STDPN.put("22271404  C7 后长", "22271404");
		PRODMAP_ALIAS_STDPN.put("22271382 C7 前长", "22271382");
		PRODMAP_ALIAS_STDPN.put("22272008 CF-11 RR 奇瑞后", "22272008");
		PRODMAP_ALIAS_STDPN.put("22272179 CF-11 FR 奇瑞前", "22272179");
		PRODMAP_ALIAS_STDPN.put("22261414 L7 宝马后", "22261414");
		PRODMAP_ALIAS_STDPN.put("22267756 L7 宝马前", "22267756");
		PRODMAP_ALIAS_STDPN.put("22262053 L421 FR 沃尔沃前", "22262053");
		PRODMAP_ALIAS_STDPN.put("22261454 L421 RR 沃尔沃后", "22261454");
		PRODMAP_ALIAS_STDPN.put("22283627 K413前", "22283627");
		PRODMAP_ALIAS_STDPN.put("22283613 K413后", "22283613");
		PRODMAP_ALIAS_STDPN.put("22271405 C7 后短", "22271405");
		PRODMAP_ALIAS_STDPN.put("22271383 C7 前短", "22271383");
		PRODMAP_ALIAS_STDPN.put("22271373 C7 前短重", "22271373");
		PRODMAP_ALIAS_STDPN.put("22292220 K-413 后", "22292220");
		PRODMAP_ALIAS_STDPN.put("22292220 K413 后", "22292220");
		PRODMAP_ALIAS_STDPN.put("22292219 K-413 前", "22292219");
		PRODMAP_ALIAS_STDPN.put("22292191 CF-11 后", "22292191");
		PRODMAP_ALIAS_STDPN.put("22292191 奇瑞后", "22292191");
		PRODMAP_ALIAS_STDPN.put("22292220 K413后", "22292220");
		PRODMAP_ALIAS_STDPN.put("22293161 K413后", "22293161");
		PRODMAP_ALIAS_STDPN.put("22283596 路虎后右", "22283596");
		PRODMAP_ALIAS_STDPN.put("22280909  路虎前左", "22280909");
		PRODMAP_ALIAS_STDPN.put("22280908 路虎前右", "22280908");
		PRODMAP_ALIAS_STDPN.put("22283595 路虎后左", "22283595");
		PRODMAP_ALIAS_STDPN.put("22268411 L7 前", "22268411");
		PRODMAP_ALIAS_STDPN.put("22285735 CF-11 FR 奇瑞前 新", "22285735");
		PRODMAP_ALIAS_STDPN.put("22293161 K413 RR 沃尔沃后", "22293161");
		PRODMAP_ALIAS_STDPN.put("22283627 路虎前", "22283627");
		PRODMAP_ALIAS_STDPN.put("22283613 路虎后", "22283613");
		PRODMAP_ALIAS_STDPN.put("22292219 K413 前", "22292219");
		PRODMAP_ALIAS_STDPN.put("22292218 K413 前", "22292218");
		PRODMAP_ALIAS_STDPN.put("22297053 CF-11 后", "22297053");
		PRODMAP_ALIAS_STDPN.put("22297053 奇瑞后", "22297053");
		PRODMAP_ALIAS_STDPN.put("22287778 奇瑞后CF-14K", "22287778");
		PRODMAP_ALIAS_STDPN.put("22285735 新 CF-11 FR 奇瑞前", "22285735");
		PRODMAP_ALIAS_STDPN.put("22287778 CF-14K奇瑞后", "22287778");
		PRODMAP_ALIAS_STDPN.put("22293290 CF-14K奇瑞前右", "22293290");
		PRODMAP_ALIAS_STDPN.put("22293289 CF-14K奇瑞前左", "22293289");
		PRODMAP_ALIAS_STDPN.put("22292211 CF-11 前左", "22292211");
		PRODMAP_ALIAS_STDPN.put("22292212 CF-11 前右", "22292212");
		PRODMAP_ALIAS_STDPN.put("22285735 CF-14K前", "22285735");
		PRODMAP_ALIAS_STDPN.put("22287786 CF-14K后", "22287786");
	}

	@Override
	protected void initProdInterval() {
		PRODMAP_LOC_INTERVAL.put(new Location(11,10), new ProductionInterval(PdInterval.Early1,8,15,9,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(12,10), new ProductionInterval(PdInterval.Early1,8,15,9,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(13,10), new ProductionInterval(PdInterval.Early1,8,15,9,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(14,10), new ProductionInterval(PdInterval.Early1,8,15,9,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(15,10), new ProductionInterval(PdInterval.Early2,9,0,10,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(16,10), new ProductionInterval(PdInterval.Early2,9,0,10,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(17,10), new ProductionInterval(PdInterval.Early2,9,0,10,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(18,10), new ProductionInterval(PdInterval.Early2,9,0,10,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(19,10), new ProductionInterval(PdInterval.Early3,10,10,11,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(20,10), new ProductionInterval(PdInterval.Early3,10,10,11,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(21,10), new ProductionInterval(PdInterval.Early3,10,10,11,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(22,10), new ProductionInterval(PdInterval.Early3,10,10,11,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(23,10), new ProductionInterval(PdInterval.Early4,11,0,12,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(24,10), new ProductionInterval(PdInterval.Early4,11,0,12,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(25,10), new ProductionInterval(PdInterval.Early4,11,0,12,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(26,10), new ProductionInterval(PdInterval.Early4,11,0,12,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(27,10), new ProductionInterval(PdInterval.Early5,12,30,13,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(28,10), new ProductionInterval(PdInterval.Early5,12,30,13,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(29,10), new ProductionInterval(PdInterval.Early5,12,30,13,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(30,10), new ProductionInterval(PdInterval.Early5,12,30,13,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(31,10), new ProductionInterval(PdInterval.Early6,13,0,14,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(32,10), new ProductionInterval(PdInterval.Early6,13,0,14,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(33,10), new ProductionInterval(PdInterval.Early6,13,0,14,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(34,10), new ProductionInterval(PdInterval.Early6,13,0,14,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(35,10), new ProductionInterval(PdInterval.Early7,14,0,15,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(36,10), new ProductionInterval(PdInterval.Early7,14,0,15,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(37,10), new ProductionInterval(PdInterval.Early7,14,0,15,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(38,10), new ProductionInterval(PdInterval.Early7,14,0,15,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(39,10), new ProductionInterval(PdInterval.Early8,15,10,16,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(40,10), new ProductionInterval(PdInterval.Early8,15,10,16,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(41,10), new ProductionInterval(PdInterval.Early8,15,10,16,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(42,10), new ProductionInterval(PdInterval.Early8,15,10,16,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(43,10), new ProductionInterval(PdInterval.Early9,16,0,17,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(44,10), new ProductionInterval(PdInterval.Early9,16,0,17,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(45,10), new ProductionInterval(PdInterval.Early9,16,0,17,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(46,10), new ProductionInterval(PdInterval.Early9,16,0,17,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(11,37), new ProductionInterval(PdInterval.Middle1,17,0,18,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(12,37), new ProductionInterval(PdInterval.Middle1,17,0,18,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(13,37), new ProductionInterval(PdInterval.Middle1,17,0,18,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(14,37), new ProductionInterval(PdInterval.Middle1,17,0,18,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(15,37), new ProductionInterval(PdInterval.Middle2,18,30,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(16,37), new ProductionInterval(PdInterval.Middle2,18,30,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(17,37), new ProductionInterval(PdInterval.Middle2,18,30,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(18,37), new ProductionInterval(PdInterval.Middle2,18,30,19,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(19,37), new ProductionInterval(PdInterval.Middle3,19,0,20,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(20,37), new ProductionInterval(PdInterval.Middle3,19,0,20,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(21,37), new ProductionInterval(PdInterval.Middle3,19,0,20,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(22,37), new ProductionInterval(PdInterval.Middle3,19,0,20,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(23,37), new ProductionInterval(PdInterval.Middle4,20,15,21,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(24,37), new ProductionInterval(PdInterval.Middle4,20,15,21,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(25,37), new ProductionInterval(PdInterval.Middle4,20,15,21,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(26,37), new ProductionInterval(PdInterval.Middle4,20,15,21,0,45));
		PRODMAP_LOC_INTERVAL.put(new Location(27,37), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(28,37), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(29,37), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(30,37), new ProductionInterval(PdInterval.Middle5,21,0,22,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(31,37), new ProductionInterval(PdInterval.Middle6,22,10,23,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(32,37), new ProductionInterval(PdInterval.Middle6,22,10,23,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(33,37), new ProductionInterval(PdInterval.Middle6,22,10,23,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(34,37), new ProductionInterval(PdInterval.Middle6,22,10,23,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(35,37), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(36,37), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(37,37), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(38,37), new ProductionInterval(PdInterval.Middle7,23,0,24,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(39,37), new ProductionInterval(PdInterval.Middle8,24,30,25,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(40,37), new ProductionInterval(PdInterval.Middle8,24,30,25,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(41,37), new ProductionInterval(PdInterval.Middle8,24,30,25,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(42,37), new ProductionInterval(PdInterval.Middle8,24,30,25,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(43,37), new ProductionInterval(PdInterval.Middle9,25,0,26,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(44,37), new ProductionInterval(PdInterval.Middle9,25,0,26,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(45,37), new ProductionInterval(PdInterval.Middle9,25,0,26,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(46,37), new ProductionInterval(PdInterval.Middle9,25,0,26,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(11,64), new ProductionInterval(PdInterval.Night1,26,0,27,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(12,64), new ProductionInterval(PdInterval.Night1,26,0,27,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(13,64), new ProductionInterval(PdInterval.Night1,26,0,27,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(14,64), new ProductionInterval(PdInterval.Night1,26,0,27,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(15,64), new ProductionInterval(PdInterval.Night2,27,10,28,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(16,64), new ProductionInterval(PdInterval.Night2,27,10,28,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(17,64), new ProductionInterval(PdInterval.Night2,27,10,28,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(18,64), new ProductionInterval(PdInterval.Night2,27,10,28,0,50));
		PRODMAP_LOC_INTERVAL.put(new Location(19,64), new ProductionInterval(PdInterval.Night3,28,0,29,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(20,64), new ProductionInterval(PdInterval.Night3,28,0,29,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(21,64), new ProductionInterval(PdInterval.Night3,28,0,29,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(22,64), new ProductionInterval(PdInterval.Night3,28,0,29,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(23,64), new ProductionInterval(PdInterval.Night4,29,0,30,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(24,64), new ProductionInterval(PdInterval.Night4,29,0,30,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(25,64), new ProductionInterval(PdInterval.Night4,29,0,30,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(26,64), new ProductionInterval(PdInterval.Night4,29,0,30,0,60));
		PRODMAP_LOC_INTERVAL.put(new Location(27,64), new ProductionInterval(PdInterval.Night5,30,30,31,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(28,64), new ProductionInterval(PdInterval.Night5,30,30,31,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(29,64), new ProductionInterval(PdInterval.Night5,30,30,31,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(30,64), new ProductionInterval(PdInterval.Night5,30,30,31,0,30));
		PRODMAP_LOC_INTERVAL.put(new Location(31,64), new ProductionInterval(PdInterval.Night6,31,0,32,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(32,64), new ProductionInterval(PdInterval.Night6,31,0,32,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(33,64), new ProductionInterval(PdInterval.Night6,31,0,32,15,75));
		PRODMAP_LOC_INTERVAL.put(new Location(34,64), new ProductionInterval(PdInterval.Night6,31,0,32,15,75));
	}

	@Override
	public Location getDateLocation() {
		return new Location(4,11);
	}
	
}
