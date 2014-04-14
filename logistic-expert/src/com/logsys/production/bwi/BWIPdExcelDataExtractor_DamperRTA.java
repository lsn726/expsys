package com.logsys.production.bwi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.logsys.production.LostTimeContent;
import com.logsys.production.ProductionContent;
import com.logsys.setting.Settings;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.util.DateInterval;
import com.logsys.util.Location;

/**
 * 减震器RTA生产线的生产数据提取器
 * @author lx8sn6
 *
 */
public class BWIPdExcelDataExtractor_DamperRTA implements BWIPdExcelDataExtractor {
	
	private static final Logger logger=Logger.getLogger(BWIPdExcelDataExtractor_DamperRTA.class);
	
	/**生产线信息*/
	protected static final BWIPLInfo plinfo=Settings.BWISettings.plinfo;
	
	/**每个生产时段有几个可输入行*/
	protected static final int SPAN_PER_INTERVAL=4;
	
	/**早班产出数量所在列*/
	protected static final int SHIFTEARLY_OUTPUTQTYCOL=8;
	
	/**中班产出数量所在列*/
	protected static final int SHIFTMIDDLE_OUTPUTQTYCOL=33;
	
	/**夜班产出数量所在列*/
	protected static final int SHIFTNIGHT_OUTPUTQTYCOL=58;
	
	/**产出PN所在列相对于产出数量所在列的差值*/
	protected static final int RELATIVECAL_OUTPUTPN=-2;
	
	/**工作工人所在列相对于产出数量所在列的差值*/
	protected static final int RELATIVECAL_OPERATORQTY=-3;
	
	/**生产数据起始行*/
	protected static final int OUTPUTDATA_STARTROW=11;
	
	/**Sheet的产出时间位置*/
	protected static final Location DATE_LOC=new Location(4,9);
	
	/**用于初始化的时间变量，初始化outputlocdatemap*/
	protected static final Calendar INIT_CALENDAR=Calendar.getInstance();
	
	/**验证字符串枚举->验证字符串对照表*/
	protected Map<ValidatorStr,String> validatorStrMap=new HashMap<ValidatorStr,String>();
	
	/**产品别名->标准名对照表*/
	protected Map<String,String> prdaliasmap=new HashMap<String,String>();
	
	/**Sheet内容验证器:位置->识别字符串对翟表*/
	protected Map<Location,String> sheetValidator=new HashMap<Location,String>();
	
	/**Sheet产出位置->时间区间对照表*/
	protected Map<Location,DateInterval> outputlocdatemap=new HashMap<Location,DateInterval>();
	
	/*验证字符串枚举表*/
	protected enum ValidatorStr {
		Interval_0815_0900,
		Interval_0900_1000,
		Interval_1000_1100,
		Interval_1100_1200,
		Interval_1200_1300,
		Interval_1300_1400,
		Interval_1400_1500,
		Interval_1500_1600,
		Interval_1600_1645,
		Interval_1645_1700,
		Interval_1700_1800,
		Interval_1800_1900,
		Interval_1900_2000,
		Interval_2000_2100,
		Interval_2100_2200,
		Interval_2200_2300,
		Interval_2300_2400,
		Interval_2400_2515,
	}
	
	public BWIPdExcelDataExtractor_DamperRTA() {
		initValidatorStr();
		initPrdAliasMap();
		initSheetValidator();
		initOutputLocIntervalMap();
	}
	
	/**
	 * 验证字符串枚举->验证字符串表初始化
	 */
	protected void initValidatorStr() {
		validatorStrMap.clear();
		validatorStrMap.put(ValidatorStr.Interval_0815_0900, "8：15-9：00");
		validatorStrMap.put(ValidatorStr.Interval_0900_1000, "9：00-10：00");
		validatorStrMap.put(ValidatorStr.Interval_1000_1100, "10：00-11：00");
		validatorStrMap.put(ValidatorStr.Interval_1100_1200, "11：00-12：00");
		validatorStrMap.put(ValidatorStr.Interval_1200_1300, "12：00-13：00");
		validatorStrMap.put(ValidatorStr.Interval_1300_1400, "13：00-14：00");
		validatorStrMap.put(ValidatorStr.Interval_1400_1500, "14：00-15：00");
		validatorStrMap.put(ValidatorStr.Interval_1500_1600, "15：00-16：00");
		validatorStrMap.put(ValidatorStr.Interval_1600_1645, "16：00-16：45");
		validatorStrMap.put(ValidatorStr.Interval_1645_1700, "16：45-17：00");
		validatorStrMap.put(ValidatorStr.Interval_1700_1800, "17：00-18：00");
		validatorStrMap.put(ValidatorStr.Interval_1800_1900, "18：00-19：00");
		validatorStrMap.put(ValidatorStr.Interval_1900_2000, "19：00-20：00");
		validatorStrMap.put(ValidatorStr.Interval_2000_2100, "20：00-21：00");
		validatorStrMap.put(ValidatorStr.Interval_2100_2200, "21：00-22：00");
		validatorStrMap.put(ValidatorStr.Interval_2200_2300, "22：00-23：00");
		validatorStrMap.put(ValidatorStr.Interval_2300_2400, "23：00-24：00");
		validatorStrMap.put(ValidatorStr.Interval_2400_2515, "00：00-01：15");
	}
	
	/**
	 * 初始化产品别名->标准名对照表
	 */
	protected void initPrdAliasMap() {
		prdaliasmap.clear();
		prdaliasmap.put("22261665 奥迪前长", "22261665");
		prdaliasmap.put("22261664 奥迪前短", "22261664");
		prdaliasmap.put("22258275 奥迪后长", "22258275");
		prdaliasmap.put("22258280 奥迪后短", "22258280");
		prdaliasmap.put("22265450 宝马前", "22265450");
		prdaliasmap.put("22261401 宝马后", "22261401");
		prdaliasmap.put("22262045 沃尔沃前", "22262045");
		prdaliasmap.put("22262043 沃尔沃前", "22262043");
		prdaliasmap.put("22261449 沃尔沃后", "22261449");
		prdaliasmap.put("22272186 奇瑞前", "22272186");
		prdaliasmap.put("22272003 奇瑞后", "22272003");
	}
	
	/**
	 * 初始化Sheet验证信息对照表
	 * ！！注意！！如果修改对照信息需要同时修正产出图
	 */
	protected void initSheetValidator() {
		sheetValidator.clear();
		int rowindex=0;
		sheetValidator.put(new Location(4,2), "班次/班组：\nShift/Group");
		sheetValidator.put(new Location(4,27), "班次/班组：\nShift/group");
		sheetValidator.put(new Location(4,52), "班次/班组：\nShift/group");
		sheetValidator.put(new Location(4,8), "日期\nDate");
		sheetValidator.put(new Location(4,33), "日期\nDate");
		sheetValidator.put(new Location(4,58), "日期\nDate");
		sheetValidator.put(new Location(8,2), "时段\nHour");
		sheetValidator.put(new Location(8,27), "时段\nHour");
		sheetValidator.put(new Location(8,52), "时段\nHour");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_OPERATORQTY), "上班人数");			//产出数量列+上班人数所在列的差值
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_OPERATORQTY), "上班人数");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_OPERATORQTY), "上班人数");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_OUTPUTPN), "零件号\nPart No");		//产出数量列+PN所在列的差值
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_OUTPUTPN), "零件号\nPart No");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_OUTPUTPN), "零件号\nPart No");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL), "小时\n产出\nHourly Count");
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL), "小时\n产出\nHourly Count");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL), "小时\n产出\nHourly Count");
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_0815_0900)); //起始行+每个区间的行数
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_1645_1700)); //起始行+每个区间的行数，而后区间+1，进入下一个区间
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_0900_1000));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_1700_1800));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1000_1100));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_1800_1900));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1100_1200));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_1900_2000));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1200_1300));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_2000_2100));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1300_1400));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_2100_2200));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1400_1500));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_2200_2300));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1500_1600));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_2300_2400));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1600_1645));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_2400_2515));
		sheetValidator.put(new Location(17,11), "F T Q");
		sheetValidator.put(new Location(17,36), "F T Q");
		sheetValidator.put(new Location(17,61), "F T Q");
		sheetValidator.put(new Location(19,11), "Part No\n零件号");
		sheetValidator.put(new Location(19,36), "Part No\n零件号");
		sheetValidator.put(new Location(19,61), "Part No\n零件号");
		sheetValidator.put(new Location(19,12), "Failure Mode\n失效模式");
		sheetValidator.put(new Location(19,37), "Failure Mode\n失效模式");
		sheetValidator.put(new Location(19,62), "Failure Mode\n失效模式");
		sheetValidator.put(new Location(19,13), "Rework\n返工");
		sheetValidator.put(new Location(19,38), "Rework\n返工");
		sheetValidator.put(new Location(19,63), "Rework\n返工");
		sheetValidator.put(new Location(19,14), "Scrap\n报废");
		sheetValidator.put(new Location(19,39), "Scrap\n报废");
		sheetValidator.put(new Location(19,64), "Scrap\n报废");
		sheetValidator.put(new Location(19,15), "Remarks\n备注");
		sheetValidator.put(new Location(19,40), "Remarks\n备注");
		sheetValidator.put(new Location(19,65), "Remarks\n备注");
		sheetValidator.put(new Location(17,16), "Lost time");
		sheetValidator.put(new Location(17,41), "Lost time");
		sheetValidator.put(new Location(17,66), "Lost time");
		sheetValidator.put(new Location(19,16), "Time\n时段");
		sheetValidator.put(new Location(19,41), "Time\n时段");
		sheetValidator.put(new Location(19,66), "Time\n时段");
		sheetValidator.put(new Location(19,17), "Lost time\n损失时间");
		sheetValidator.put(new Location(19,42), "Lost time\n损失时间");
		sheetValidator.put(new Location(19,67), "Lost time\n损失时间");
		sheetValidator.put(new Location(19,18), "LT mode\n损失类型");
		sheetValidator.put(new Location(19,43), "LT mode\n损失类型");
		sheetValidator.put(new Location(19,68), "LT mode\n损失类型");
		sheetValidator.put(new Location(19,19), "Cause\n原因");
		sheetValidator.put(new Location(19,44), "Cause\n原因");
		sheetValidator.put(new Location(19,69), "Cause\n原因");
		sheetValidator.put(new Location(19,20), "Remarks\n备注");
		sheetValidator.put(new Location(19,45), "Remarks\n备注");
		sheetValidator.put(new Location(19,70), "Remarks\n备注");
	}
	
	/**
	 * 初始化Sheet产出位置->时间区间对照表
	 */
	protected void initOutputLocIntervalMap() {
		outputlocdatemap.clear();
		//初始化验证与信息
		Date begin;
		Date end;
		DateInterval dinterval;
		int rowindex=0;
		//设定时间段产出位置图
		//初始化时间信息
		INIT_CALENDAR.clear();
		INIT_CALENDAR.set(1900, 1, 1);			//初始化为1900年1月1日
		Calendar cal=(Calendar)INIT_CALENDAR.clone();
		//早班区间初始化
		rowindex=OUTPUTDATA_STARTROW;			//行索引初始化
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY, 8);		//设置区间开始时间
		cal.set(Calendar.MINUTE, 15);
		begin=cal.getTime();
		cal.set(Calendar.HOUR_OF_DAY, 9);		//设置区间结束时间
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);	//设定工作区间
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,9);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY, 10);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,10);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY, 11);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,11);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,12);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,13);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,13);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,14);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,14);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,15);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,15);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,16);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,16);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,16);
		cal.set(Calendar.MINUTE, 45);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		//中班区间初始化
		rowindex=OUTPUTDATA_STARTROW;			//行索引初始化
		cal.set(Calendar.HOUR_OF_DAY, 16);		//设置区间开始时间
		cal.set(Calendar.MINUTE, 45);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY, 17);		//设置区间结束时间
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);	//设定工作区间
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,17);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,18);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,18);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,19);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,19);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,20);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,20);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,21);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,21);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,22);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,22);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,24);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,24);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,25);
		cal.set(Calendar.MINUTE, 15);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
	}
	
	@Override
	public List<ProductionContent> extractOutputData(Sheet datasrc, String stdprodline) throws Throwable {
		if(!validateDatasrc(datasrc,stdprodline)) return null;
		Date proddate=datasrc.getRow(DATE_LOC.row).getCell(DATE_LOC.column).getDateCellValue();		//获取当前sheet的日期
		Calendar prodcal=Calendar.getInstance();
		prodcal.clear();
		prodcal.setTime(proddate);
		long milsecdiff=prodcal.getTimeInMillis()-INIT_CALENDAR.getTimeInMillis();					//计算与初始化日期之间的差别毫秒数 
		List<ProductionContent> prodlist=new ArrayList<ProductionContent>();
		ProductionContent prodcont;
		Cell cell;
		Calendar startcal;		//时间段开始
		Calendar endcal;		//时间段结束
		String outputprd;		//产出信息
		for(Location loc:outputlocdatemap.keySet()) {			//遍历产出数据位置读取产出数据
			cell=datasrc.getRow(loc.row).getCell(loc.column);
			if(cell==null) continue;							//如果为null则说明没有该位置没有生产数据
			if(cell.getNumericCellValue()==0) continue;			//产出数量为0则跳过
			prodcont=new ProductionContent();
			prodcont.setOutputqty(cell.getNumericCellValue());	//加入生产数据
			prodcont.setWorkcenter(stdprodline);				//设置工作中心
			outputprd=datasrc.getRow(loc.row).getCell(loc.column+RELATIVECAL_OUTPUTPN).getStringCellValue();	//由行差定位产出内容单元格后读取
			if(!prdaliasmap.containsKey(outputprd)) {			//如果没有对照于这个产品的别名，则说明出现错误数据，或者对照表需要补充
				logger.error("未能识别产出产品的别名:"+outputprd+".需要确认是错误数据，或者别名对照表的数据不全面.位置信息：Sheet:"+datasrc.getSheetName()+" Row:"+loc.row+" Cell:"+(loc.column+RELATIVECAL_OUTPUTPN));
				return null;
			}
			prodcont.setOutputprd(prdaliasmap.get(outputprd));	//设定产出内容
			prodcont.setOperatorqty((int)datasrc.getRow(loc.row).getCell(loc.column+RELATIVECAL_OPERATORQTY).getNumericCellValue());	//由行差定位产出内容单元格后读取，并设定操作人数
			prodcont.setDate(proddate);							//设定生产日期
			startcal=Calendar.getInstance();					//由毫秒差获得区间开始的正确时间
			startcal.setTime(outputlocdatemap.get(loc).begindate);
			startcal.setTimeInMillis(startcal.getTimeInMillis()+milsecdiff);
			prodcont.setTfbegin(startcal.getTime());
			endcal=Calendar.getInstance();						//由毫秒差获得区间结束的正确时间
			endcal.setTime(outputlocdatemap.get(loc).enddate);
			endcal.setTimeInMillis(endcal.getTimeInMillis()+milsecdiff);
			prodcont.setTfend(endcal.getTime());
			prodlist.add(prodcont);								//加入列表
		}
		return prodlist;
	}

	@Override
	public List<LostTimeContent> extractLostTimeData(Sheet datasrc, String stdprodline) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateDatasrc(Sheet datasrc, String stdprodline) throws Throwable {
		if(datasrc==null||stdprodline==null) {
			logger.error("验证RTA生产用Excel数据源失败：参数为空.");
			return false;
		}
		if(plinfo.getProdzoneByStdProdlineName(stdprodline)!=BWIPLInfo.STDNAME_DAMPER_RTA) {
			logger.error("验证RTA生产用Excel数据源失败：生产线"+stdprodline+"并非RTA标准生产线数据源.");
			return false;
		}
		Cell cell;
		for(Location loc:sheetValidator.keySet()) {			//验证表格可信性
			cell=datasrc.getRow(loc.row).getCell(loc.column);
			if(!cell.getStringCellValue().equals(sheetValidator.get(loc))) {
				logger.error("Sheet:"+datasrc.getSheetName()+" "+"位置行:"+loc.row+" 列:"+loc.column+" 的内容:["+cell.getStringCellValue()+"]与验证器中的内容:["+sheetValidator.get(loc)+"]不相符。请检查表格内容是否正确，或者验证器内容是否过期.");
				return false;
			}
			//System.out.println(cell.getStringCellValue()+"["+loc.row+"/"+loc.column+"]");
		}
		return true;
	}

}
