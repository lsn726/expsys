package com.logsys.demand;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 需求流程，所有需求相关功能的流程在此类中集中。
 * @author lx8sn6
 */
public class DemandProcess {

	private static Logger logger=Logger.getLogger(DemandProcess.class);
	
	/**
	 * 将pnset中的型号，在begin和end之间的需求，导出到文件exportfilepath,如果begin和/end为null，则默认为相应日期不限制
	 * @param exportfilepath 文件路径
	 * @param pnset 型号集
	 * @param begin 开始时间,如果是null，则默认为不限制起始时间
	 * @param end 结束时间，如果是null,则默认为不限制结束时间
	 * @return 成功/true,失败/false
	 */
	public static boolean exportDemandToExcel(String exportfilepath,Set<String> pnset, Date begin, Date end) {
		if(exportfilepath==null) {
			logger.error("文件路径参数为空.");
			return false;
		}
		List<DemandContent> demlist=DemandDataReaderDB.getDataFromDB(pnset, begin, end);	//首先从数据库里读取数据
		if(demlist==null) return false;
		Map<String,Map<Date,DemandContent>> demmap=DemandUtil.demListToMapByPn(demlist);	//从读取的List转换成按照型号和时间分类的格式
		if(demmap==null) return false;
		return DemandDataWriterExcel.writeToExcel(exportfilepath, demmap);		//写入文件路径
	}
	
	
}
