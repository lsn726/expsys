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
