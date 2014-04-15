package com.logsys.production.bwi;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

import com.logsys.production.LostTimeContent;
import com.logsys.production.ProductionContent;

/**
 * BWI的生产日报Excel信息提取器
 * @author ShaonanLi
 */
public interface BWIPdExcelDataExtractor {
	
	public static final String EMPTYSTRVALUE="EMPTYSTRVALUE";
	
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
	 * 通过指定标准生产线名称(BWIPLInfo)来判断datasrc中的数据格式是否正确
	 * @param datasrc 包含数据的生产信息Sheet中的数据
	 * @param stdprodline 标准生产线名称(BWIPLInfo)
	 * @return 通过数据验证true/未通过false
	 */
	public abstract boolean validateDatasrc(Sheet datasrc,String stdprodline) throws Throwable;
	
}
