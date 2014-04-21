package com.logsys.setting.pd.bwi.fa;

import com.logsys.util.Location;

/**
 * BWI生产Excel表配置--FA--UV线
 * @author lx8sn6
 */
public class BWIPdExcelInfoFA_ReboundStop extends BWIPdExcelInfoFA {

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
		VERIFYMAP_LOC_STR.put(new Location(17,13), "F T Q");
		VERIFYMAP_LOC_STR.put(new Location(17,40), "F T Q");
		VERIFYMAP_LOC_STR.put(new Location(17,67), "F T Q");
		VERIFYMAP_LOC_STR.put(new Location(17,18), "Lost time");
		VERIFYMAP_LOC_STR.put(new Location(17,45), "Lost time");
		VERIFYMAP_LOC_STR.put(new Location(17,72), "Lost time");
		VERIFYMAP_LOC_STR.put(new Location(19,13), "Part No\n零件号");
		VERIFYMAP_LOC_STR.put(new Location(19,40), "Part No\n零件号");
		VERIFYMAP_LOC_STR.put(new Location(19,67), "Part No\n零件号");
		VERIFYMAP_LOC_STR.put(new Location(19,14), "Failure Mode\n失效模式");
		VERIFYMAP_LOC_STR.put(new Location(19,41), "Failure Mode\n失效模式");
		VERIFYMAP_LOC_STR.put(new Location(19,68), "Failure Mode\n失效模式");
		VERIFYMAP_LOC_STR.put(new Location(19,15), "Rework\n返工");
		VERIFYMAP_LOC_STR.put(new Location(19,42), "Rework\n返工");
		VERIFYMAP_LOC_STR.put(new Location(19,69), "Rework\n返工");
		VERIFYMAP_LOC_STR.put(new Location(19,16), "Scrap\n报废");
		VERIFYMAP_LOC_STR.put(new Location(19,43), "Scrap\n报废");
		VERIFYMAP_LOC_STR.put(new Location(19,70), "Scrap\n报废");
		VERIFYMAP_LOC_STR.put(new Location(19,17), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(19,44), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(19,71), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(19,18), "Time\n时段");
		VERIFYMAP_LOC_STR.put(new Location(19,45), "Time\n时段");
		VERIFYMAP_LOC_STR.put(new Location(19,72), "Time\n时段");
		VERIFYMAP_LOC_STR.put(new Location(19,19), "Lost time\n损失时间");
		VERIFYMAP_LOC_STR.put(new Location(19,46), "Lost time\n损失时间");
		VERIFYMAP_LOC_STR.put(new Location(19,73), "Lost time\n损失时间");
		VERIFYMAP_LOC_STR.put(new Location(19,20), "LT mode\n损失类型");
		VERIFYMAP_LOC_STR.put(new Location(19,47), "LT mode\n损失类型");
		VERIFYMAP_LOC_STR.put(new Location(19,74), "LT mode\n损失类型");
		VERIFYMAP_LOC_STR.put(new Location(19,21), "Cause\n原因");
		VERIFYMAP_LOC_STR.put(new Location(19,48), "Cause\n原因");
		VERIFYMAP_LOC_STR.put(new Location(19,75), "Cause\n原因");
		VERIFYMAP_LOC_STR.put(new Location(19,22), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(19,49), "Remarks\n备注");
		VERIFYMAP_LOC_STR.put(new Location(19,76), "Remarks\n备注");
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
		VERIFYMAP_LOC_STR.put(new Location(43,4), "0.708333333333334");
		VERIFYMAP_LOC_STR.put(new Location(43,29), "0.0416666666666667");
		VERIFYMAP_LOC_STR.put(new Location(43,31), "0.0833333333333333");
		VERIFYMAP_LOC_STR.put(new Location(43,56), EMPTY_STR);
		VERIFYMAP_LOC_STR.put(new Location(43,58), EMPTY_STR);
	}
	
}
