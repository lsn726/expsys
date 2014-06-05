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
	
	/**
	 * 将Excel文件中的需求数据提取，备份，删除原数据，并写入数据库。
	 * @param filepath Excel文件路径
	 * @param version 需求版本,null为当前时间
	 * @return 成功true/失败false
	 */
	public static boolean importDemandFromExcel_v2(String filepath, Date version) {
		DemandExcelService demExcelService=DemandExcelService.getDemandExcelService(filepath);
		if(demExcelService==null) {
			logger.error("不能从Excel中倒出需求数据，需求Excel服务创建失败。");
			return false;
		}
		return true;
	}

}
