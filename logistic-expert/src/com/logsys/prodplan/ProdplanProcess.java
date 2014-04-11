package com.logsys.prodplan;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.logsys.util.DateInterval;

/**
 * 生产计划流程
 * @author lx8sn6
 */
public class ProdplanProcess {

	private static Logger logger=Logger.getLogger(ProdplanProcess.class);
	
	/**
	 * 从Excel表中读取生产计划数据并写入数据库
	 * @param filepath 详细文件路径
	 * @param startdate 起始日期,null则默认为下一周的周一
	 * @param enddate 结束如期,null则不限制下限
	 * @param resetPlanBeyondScope 是否重置区间以外未来的计划，即是否删除指定区间未来期间后的计划
	 * @return 写入生产计划的条目数量
	 */
	public static int importProdplanFromExcel(String filepath, Date startdate, Date enddate, boolean resetPlanBeyondScope) {
		//首先从Excel文件中读取计划
		List<ProdplanContent> pplist=ProdplanDataReaderExcel.getFAPlanFromFileBWI(filepath, startdate, enddate);
		if(pplist==null) return -1;
		//确认读取的计划区间
		DateInterval dinterval=ProdplanUtils.getDataInterval(pplist);
		if(dinterval==null) return -1;
		//删除原区间计划
		int deletecounter=ProdplanDataWriterDB.deleteProdplan(dinterval,resetPlanBeyondScope);
		if(deletecounter<0) return -1;
		//写入新计划
		int writecounter=ProdplanDataWriterDB.writeToDB(pplist);
		if(writecounter<0) return -1;
		//备份区间，版本为当前时间
		int bkupcounter=ProdplanDataWriterDB.backupProdPlan(dinterval, null);
		if(bkupcounter<0) logger.error("警告！虽然新计划写入成功，但是没有成功备份数据！");
		return writecounter;
	}


}