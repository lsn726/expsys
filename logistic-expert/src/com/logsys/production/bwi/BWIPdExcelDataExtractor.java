package com.logsys.production.bwi;

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
 * BWI的生产日报Excel信息提取器抽象类
 * @author ShaonanLi
 */
public abstract class BWIPdExcelDataExtractor {
	
	private static final Logger logger=Logger.getLogger(BWIPdExcelDataExtractor.class);
	
	/**用于在Hashmap中写入空字符串*/
	public static final String EMPTYSTRVALUE="EMPTYSTRVALUE";
	
	/**生产线区域字符串，用于validateDatasrc()判别提取器是否属于正确的生产区域*/
	public static String PRDZONE;
	
	/**生产线信息*/
	protected static final BWIPLInfo plinfo=Settings.BWISettings.plinfo;
	
	/*验证字符串枚举表*/
	public static enum ValidatorStr {
		/**早班区间1*/
		Interval_Early1,
		/**早班区间2*/
		Interval_Early2,
		/**早班区间3*/
		Interval_Early3,
		/**早班区间4*/
		Interval_Early4,
		/**早班区间5*/
		Interval_Early5,
		/**早班区间6*/
		Interval_Early6,
		/**早班区间7*/
		Interval_Early7,
		/**早班区间8*/
		Interval_Early8,
		/**早班区间9*/
		Interval_Early9,
		/**中班区间1*/
		Interval_Middle1,
		/**中班区间2*/
		Interval_Middle2,
		/**中班区间3*/
		Interval_Middle3,
		/**中班区间4*/
		Interval_Middle4,
		/**中班区间5*/
		Interval_Middle5,
		/**中班区间6*/
		Interval_Middle6,
		/**中班区间7*/
		Interval_Middle7,
		/**中班区间8*/
		Interval_Middle8,
		/**中班区间9*/
		Interval_Middle9,
		/**晚班区间1*/
		Interval_Night1,
		/**晚班区间2*/
		Interval_Night2,
		/**晚班区间3*/
		Interval_Night3,
		/**晚班区间4*/
		Interval_Night4,
		/**晚班区间5*/
		Interval_Night5,
		/**晚班区间6*/
		Interval_Night6,
		/**晚班区间7*/
		Interval_Night7,
		/**晚班区间8*/
		Interval_Night8,
		/**晚班区间9*/
		Interval_Night9
	}
	
	/**产品别名->标准名对照表*/
	protected Map<String,String> prdaliasmap=new HashMap<String,String>();
	
	/**Sheet内容验证器:需要验证的位置->验证字符串对照表*/
	protected Map<Location,String> sheetValidator=new HashMap<Location,String>();
	
	/**Sheet产出位置->时间区间对照表*/
	protected Map<Location,DateInterval> outputlocdatemap=new HashMap<Location,DateInterval>();
	
	/**验证字符串枚举->验证字符串对照表，用于验证PR和RTA的表格格式*/
	protected Map<ValidatorStr,String> validatorStrMap=new HashMap<ValidatorStr,String>();
	
	public BWIPdExcelDataExtractor() {
		initValidatorStr();		//首先初始化验证字符串
		initPrdAliasMap();		//初始化别名->标准名对照表
		initLocValidatorStrMap();	//初始化位置->验证字符串对照表，需要先初始化验证字符串和别名->标准名对照表
		initOutputLocIntervalMap();	//初始化位置->产出区间对翟表
	}
	
	/**
	 * 验证字符串枚举->验证字符串表初始化
	 */
	protected void initValidatorStr() {}
	
	/**
	 * 初始化产品别名->标准名对照表
	 */
	protected void initPrdAliasMap() {}
	
	/**
	 * 初始化Sheet位置->验证字符串对照表
	 * ！！注意！！如果修改对照信息需要同时修正产出图
	 */
	protected void initLocValidatorStrMap() {}
	
	/**
	 * 初始化Sheet产出位置->时间区间对照表
	 */
	protected void initOutputLocIntervalMap() {}
	
	/**
	 * 通过指定标准生产线名称(BWIPLInfo)提取datasrc中的产出数据
	 * @param datasrc 包含数据的生产信息Sheet中的数据
	 * @param stdprodline 标准生产线名称(BWIPLInfo)
	 * @return 读取的生产数据列表
	 */
	public abstract List<ProductionContent> extractOutputData(Sheet datasrc,String stdprodline) throws Throwable;
	
	/**
	 * 通过指定标准生产线名称(BWIPLInfo)提取datasrc中的损失时间数据
	 * @param datasrc 包含数据的生产信息Sheet中的数据
	 * @param stdprodline 标准生产线名称(BWIPLInfo)
	 * @return 读取的损失时间数据列表
	 */
	public abstract List<LostTimeContent> extractLostTimeData(Sheet datasrc,String stdprodline) throws Throwable;
	
	/**
	 * 通过指定标准生产线名称(BWIPLInfo)来判断datasrc中的数据格式是否正确.验证生产线用变量PRDZONE来验证
	 * @param datasrc 包含数据的生产信息Sheet中的数据
	 * @param stdprodline 标准生产线名称(BWIPLInfo)
	 * @return 通过数据验证true/未通过false
	 */
	public boolean validateDatasrc(Sheet datasrc, String stdprodline) throws Throwable {
		if(datasrc==null||stdprodline==null) {
			logger.error("验证生产用Excel数据源失败：参数为空.");
			return false;
		}
		if(!plinfo.getProdzoneByStdProdlineName(stdprodline).equals(PRDZONE)) {
			logger.error("验证生产用Excel数据源失败：生产线"+stdprodline+"并非"+PRDZONE+"标准生产线数据源.");
			return false;
		}
		Cell cell;
		String validstr;
		for(Location loc:sheetValidator.keySet()) {			//验证表格可信性
			cell=datasrc.getRow(loc.row).getCell(loc.column);
			validstr=sheetValidator.get(loc);
			//如果获取值不匹配，或者获取字符串为""的代理值EMPTYSTRVALUE时
			if(!cell.getStringCellValue().equals(validstr))
				if(!validstr.equals(EMPTYSTRVALUE)) {		//如果验证字符串是指定的空字符串
					logger.error("Sheet:"+datasrc.getSheetName()+" "+"位置行:"+loc.row+" 列:"+loc.column+" 的内容:["+cell.getStringCellValue()+"]与验证器中的内容:["+sheetValidator.get(loc)+"]不相符。请检查表格内容是否正确，或者验证器内容是否过期.");
					return false;
				}
			//System.out.println(cell.getStringCellValue()+"["+loc.row+"/"+loc.column+"]");
		}
		return true;
	}
	
}
