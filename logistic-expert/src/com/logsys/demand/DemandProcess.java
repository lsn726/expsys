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
	 * 将Excel文件中的需求数据提取，备份，删除原数据，并写入数据库。
	 * @param filepath Excel文件路径
	 * @return 成功true/失败false
	 */
	public static boolean importDemandFromExcel(String filepath) {
		DemandExcelService demExcelService=DemandExcelService.getDemandExcelService(filepath);
		if(demExcelService==null) {
			logger.error("不能从Excel中倒出需求数据，需求Excel服务创建失败。");
			return false;
		}
		return demExcelService.syncDatabase();
	}

}
