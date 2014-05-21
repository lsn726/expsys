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
	 * @param pnset 型号集,如果为null,则不限制型号
	 * @param begin 开始时间,如果是null，则默认为不限制起始时间
	 * @param end 结束时间，如果是null,则默认为不限制结束时间
	 * @return 成功/true,失败/false
	 */
	@Deprecated
	public static boolean exportDemandToExcel(String exportfilepath,Set<String> pnset, Date begin, Date end) {
		List<DemandContent> demlist=DemandDataReaderDB.getDemandDataFromDB_OnDay(pnset, begin, end);	//首先从数据库里读取数据
		if(demlist==null) return false;
		Map<String,Map<Date,DemandContent>> demmap=DemandUtil.demListToMapByPn(demlist);	//从读取的List转换成按照型号和时间分类的格式
		if(demmap==null) return false;
		return DemandDataWriterExcel.writeToExcel(exportfilepath, demmap);		//写入文件路径
	}
	
	/**
	 * 将importfilepath中的Excel的数据导入到数据库的Demand表中，并备份以前数据
	 * @param importfilepath 包含需求数据的文件，格式为：第一列日期，第二列型号，第三列数量
	 * @param version 需求版本，null为当前时间
	 * @return 成功写入的条目数量,-1为失败
	 */
	public static int importDemandFromExcel(String importfilepath, Date version) {
		List<DemandContent> importdemlist=DemandDataReaderExcel.getDataFromFile(importfilepath);	//从文件中获取需求数据
		if(importdemlist==null) return -1;
		Map<String,Map<Date,DemandContent>> importdemmap=DemandUtil.demListToMapByPn(importdemlist);//无须的list->map
		if(importdemmap==null) return -1;
		int insertqty=DemandDataWriterDB.updateOrInsert(importdemmap);				//将需求map写入数据库
		if(insertqty<0) return -1;
		int backupqty=DemandDataWriterDB.backupDemandData(importdemmap,version);	//将原新版本数据进行备份
		if(backupqty<0) logger.error("备份数据出现错误.");
		logger.info("数据从Excel导入成功,备份条数:"+backupqty+",插入条数:"+insertqty+".");
		return insertqty;
	}
	
	
}
