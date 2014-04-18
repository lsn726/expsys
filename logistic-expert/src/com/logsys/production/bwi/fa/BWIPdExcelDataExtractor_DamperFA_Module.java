package com.logsys.production.bwi.fa;

import com.logsys.util.Location;



/**
 * BWI生产Excel信息提取器--减震器FA提取器--Module压装
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperFA_Module extends BWIPdExcelDataExtractor_DamperFA {

	/**FTQ and Lost Time起始行*/
	protected static final int FTQ_LT_STARTROW=21;
	
	/**FTQ and Lost Time表头起始行*/
	protected static final int FTQ_LT_HEADER_STARTROW=23;
	
	protected void initLocValidatorStrMap() {
		sheetValidator.clear();
		sheetValidator.put(new Location(4,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "班次/班组：\nShift/Group");
		sheetValidator.put(new Location(4,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "班次/班组：\nShift/group");
		sheetValidator.put(new Location(4,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "班次/班组：\nShift/group");
		sheetValidator.put(new Location(4,10), "日期\nDate");
		sheetValidator.put(new Location(4,37), "日期\nDate");
		sheetValidator.put(new Location(4,64), "日期\nDate");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "时段\nHour");
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "时段\nHour");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "时段\nHour");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_OPERATORQTY), "上班人数");			//产出数量列+上班人数所在列的差值
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_OPERATORQTY), "上班人数");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_OPERATORQTY), "上班人数");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_OUTPUTPN), "零件号\nPart No");		//产出数量列+PN所在列的差值
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_OUTPUTPN), "零件号\nPart No");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_OUTPUTPN), "零件号\nPart No");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL), "小时\n产出\nHourly Count");
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL), "小时\n产出\nHourly Count");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL), "小时\n产出\nHourly Count");
		//时间区间验证字符串不适用于FA
		sheetValidator.put(new Location(FTQ_LT_STARTROW,13), "F T Q");
		sheetValidator.put(new Location(FTQ_LT_STARTROW,40), "F T Q");
		sheetValidator.put(new Location(FTQ_LT_STARTROW,67), "F T Q");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,13), "Part No\n零件号");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,40), "Part No\n零件号");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,67), "Part No\n零件号");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,14), "Failure Mode\n失效模式");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,41), "Failure Mode\n失效模式");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,68), "Failure Mode\n失效模式");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,15), "Rework\n返工");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,42), "Rework\n返工");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,69), "Rework\n返工");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,16), "Scrap\n报废");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,43), "Scrap\n报废");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,70), "Scrap\n报废");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,17), "Remarks\n备注");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,44), "Remarks\n备注");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,71), "Remarks\n备注");
		sheetValidator.put(new Location(FTQ_LT_STARTROW,18), "Lost time");
		sheetValidator.put(new Location(FTQ_LT_STARTROW,45), "Lost time");
		sheetValidator.put(new Location(FTQ_LT_STARTROW,72), "Lost time");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,18), "Time\n时段");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,45), "Time\n时段");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,72), "Time\n时段");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,19), "Lost time\n损失时间");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,46), "Lost time\n损失时间");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,73), "Lost time\n损失时间");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,20), "LT mode\n损失类型");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,47), "LT mode\n损失类型");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,74), "LT mode\n损失类型");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,21), "Cause\n原因");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,48), "Cause\n原因");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,75), "Cause\n原因");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,22), "Remarks\n备注");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,49), "Remarks\n备注");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,76), "Remarks\n备注");
	}
	
}