package com.logsys.production.bwi.fa;

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
import com.logsys.production.bwi.BWIPdExcelDataExtractor;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.util.DateInterval;
import com.logsys.util.Location;

/**
 * 减震器RTA生产线的生产数据提取器
 * @author lx8sn6
 */
public abstract class BWIPdExcelDataExtractor_DamperFA extends BWIPdExcelDataExtractor {
	
	private static final Logger logger=Logger.getLogger(BWIPdExcelDataExtractor_DamperFA.class);
	
	/*FA验证区间枚举表*/
	public static enum IntervalValidator {
		/**早班区间1起始*/
		Interval_Early1_Begin,
		/**早班区间1结束*/
		Interval_Early1_End,
		/**早班区间2开始*/
		Interval_Early2_Begin,
		/**早班区间2结束*/
		Interval_Early2_End,
		/**早班区间3开始*/
		Interval_Early3_Begin,
		/**早班区间3结束*/
		Interval_Early3_End,
		/**早班区间4开始*/
		Interval_Early4_Begin,
		/**早班区间4结束*/
		Interval_Early4_End,
		/**早班区间5开始*/
		Interval_Early5_Begin,
		/**早班区间5结束*/
		Interval_Early5_End,
		/**早班区间6开始*/
		Interval_Early6_Begin,
		/**早班区间6结束*/
		Interval_Early6_End,
		/**早班区间7开始*/
		Interval_Early7_Begin,
		/**早班区间7结束*/
		Interval_Early7_End,
		/**早班区间8开始*/
		Interval_Early8_Begin,
		/**早班区间8结束*/
		Interval_Early8_End,
		/**早班区间9开始*/
		Interval_Early9_Begin,
		/**早班区间9结束*/
		Interval_Early9_End,
		/**中班区间1开始*/
		Interval_Middle1_Begin,
		/**中班区间1结束*/
		Interval_Middle1_End,
		/**中班区间2开始*/
		Interval_Middle2_Begin,
		/**中班区间2结束*/
		Interval_Middle2_End,
		/**中班区间3开始*/
		Interval_Middle3_Begin,
		/**中班区间3结束*/
		Interval_Middle3_End,
		/**中班区间4开始*/
		Interval_Middle4_Begin,
		/**中班区间4结束*/
		Interval_Middle4_End,
		/**中班区间5开始*/
		Interval_Middle5_Begin,
		/**中班区间5结束*/
		Interval_Middle5_End,
		/**中班区间6开始*/
		Interval_Middle6_Begin,
		/**中班区间6结束*/
		Interval_Middle6_End,
		/**中班区间7开始*/
		Interval_Middle7_Begin,
		/**中班区间7结束*/
		Interval_Middle7_End,
		/**中班区间8开始*/
		Interval_Middle8_Begin,
		/**中班区间8结束*/
		Interval_Middle8_End,
		/**中班区间9开始*/
		Interval_Middle9_Begin,
		/**中班区间9结束*/
		Interval_Middle9_End,
		/**晚班区间1开始*/
		Interval_Night1_Begin,
		/**晚班区间1结束*/
		Interval_Night1_End,
		/**晚班区间2开始*/
		Interval_Night2_Begin,
		/**晚班区间2结束*/
		Interval_Night2_End,
		/**晚班区间3开始*/
		Interval_Night3_Begin,
		/**晚班区间3结束*/
		Interval_Night3_End,
		/**晚班区间4开始*/
		Interval_Night4_Begin,
		/**晚班区间4结束*/
		Interval_Night4_End,
		/**晚班区间5开始*/
		Interval_Night5_Begin,
		/**晚班区间5结束*/
		Interval_Night5_End,
		/**晚班区间6开始*/
		Interval_Night6_Begin,
		/**晚班区间6结束*/
		Interval_Night6_End,
		/**晚班区间7开始*/
		Interval_Night7_Begin,
		/**晚班区间7结束*/
		Interval_Night7_End,
		/**晚班区间8开始*/
		Interval_Night8_Begin,
		/**晚班区间8结束*/
		Interval_Night8_End,
		/**晚班区间9开始*/
		Interval_Night9_Begin,
		/**晚班区间9结束*/
		Interval_Night9_End
	}
	
	/**每个生产时段有几个可输入行*/
	protected static final int SPAN_PER_INTERVAL=4;
	
	/**早班产出数量所在列*/
	protected static final int SHIFTEARLY_OUTPUTQTYCOL=10;
	
	/**中班产出数量所在列*/
	protected static final int SHIFTMIDDLE_OUTPUTQTYCOL=37;
	
	/**夜班产出数量所在列*/
	protected static final int SHIFTNIGHT_OUTPUTQTYCOL=64;
	
	/**产出PN所在列相对于产出数量所在列的差值*/
	protected static final int RELATIVECAL_OUTPUTPN=-2;
	
	/**工作工人所在列相对于产出数量所在列的差值*/
	protected static final int RELATIVECAL_OPERATORQTY=-3;
	
	/**时间区间所在列相对于产出数量所在列的差值*/
	protected static final int RELATIVECAL_INTERVAL_BEGIN=-8;
	
	/**时间区间所在列相对于产出数量所在列的差值*/
	protected static final int RELATIVECAL_INTERVAL_END=-6;
	
	/**生产数据起始行*/
	protected static final int OUTPUTDATA_STARTROW=11;
	
	/**FTQ and Lost Time起始行*/
	protected static final int FTQ_LT_STARTROW=17;
	
	/**FTQ and Lost Time表头起始行*/
	protected static final int FTQ_LT_HEADER_STARTROW=19;
	
	/**Sheet的产出时间位置*/
	protected static final Location DATE_LOC=new Location(4,11);
	
	/**用于初始化的时间变量，初始化outputlocdatemap*/
	protected static final Calendar INIT_CALENDAR=Calendar.getInstance();
	
	/**用于核对FA特殊的时间区间标示方法*/
	protected static final Map<IntervalValidator,Date> intervalValidator=new HashMap<IntervalValidator,Date>();
	
	{
		//初始化时间信息
		INIT_CALENDAR.clear();
		INIT_CALENDAR.set(1900, 1, 1);			//初始化为1900年1月1日
		
	}
	
	public BWIPdExcelDataExtractor_DamperFA() {
		super();
		PRDZONE=BWIPLInfo.STDNAME_DAMPER_FA;
	}
	
	protected void initValidatorStr() {
		validatorStrMap.clear();
		//FA使用与RTA和PR不同的标记时间区间方式
	}
	
	/**
	 * 初始化区间验证器
	 */
	protected void initIntervalValidator() {
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 15);
		intervalValidator.put(IntervalValidator.Interval_Early1_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 9);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early1_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 9);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early2_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 10);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early2_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 10);
		cal.set(Calendar.MINUTE, 10);
		intervalValidator.put(IntervalValidator.Interval_Early3_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 11);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early3_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 11);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early4_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 12);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early4_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 12);
		cal.set(Calendar.MINUTE, 30);
		intervalValidator.put(IntervalValidator.Interval_Early5_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 13);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early5_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 13);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early6_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 14);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early6_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 14);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early7_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 15);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early7_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 15);
		cal.set(Calendar.MINUTE, 10);
		intervalValidator.put(IntervalValidator.Interval_Early8_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 16);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early8_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 16);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early9_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 17);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early9_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 17);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle1_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 18);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle1_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 18);
		cal.set(Calendar.MINUTE, 30);
		intervalValidator.put(IntervalValidator.Interval_Middle2_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 19);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle2_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 19);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle3_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 20);
		cal.set(Calendar.MINUTE, 15);
		intervalValidator.put(IntervalValidator.Interval_Middle3_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 20);
		cal.set(Calendar.MINUTE, 15);
		intervalValidator.put(IntervalValidator.Interval_Middle4_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 21);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle4_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 21);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle5_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 22);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle5_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 22);
		cal.set(Calendar.MINUTE, 10);
		intervalValidator.put(IntervalValidator.Interval_Middle6_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle6_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle7_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle7_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 30);
		intervalValidator.put(IntervalValidator.Interval_Middle8_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle8_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle9_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle9_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night1_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night1_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 10);
		intervalValidator.put(IntervalValidator.Interval_Night2_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night2_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night3_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night3_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night4_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 6);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night4_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 6);
		cal.set(Calendar.MINUTE, 30);
		intervalValidator.put(IntervalValidator.Interval_Night5_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 7);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night5_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 7);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night6_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 15);
		intervalValidator.put(IntervalValidator.Interval_Night6_End, cal.getTime());
	}
	
	protected void initPrdAliasMap() {
		prdaliasmap.clear();
		prdaliasmap.put("22261665 奥迪前长", "22261665");
	}
	
	protected void initLocValidatorStrMap() {
		sheetValidator.put(new Location(4,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "班次/班组：\nShift/Group");
		sheetValidator.put(new Location(4,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "班次/班组：\nShift/group");
		sheetValidator.put(new Location(4,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "班次/班组：\nShift/group");
		sheetValidator.put(new Location(4,8), "日期\nDate");
		sheetValidator.put(new Location(4,33), "日期\nDate");
		sheetValidator.put(new Location(4,58), "日期\nDate");
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
	
	protected void initOutputLocIntervalMap() {
		outputlocdatemap.clear();
		//初始化验证与信息
		Date begin;
		Date end;
		DateInterval dinterval;
		int rowindex=0;
		//设定时间段产出位置图
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

}
