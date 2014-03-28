package com.logsys.bom;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * BOM流程类
 * @author lx8sn6
 */
public class BOMProcess {

	private static final Logger logger=Logger.getLogger(BOMProcess.class);
	
	/**
	 * 将SAP的CS12中所含有的BOM导入到数据库中
	 * @param filepath CS12的TCode所倒出后的文件
	 * @return 写入记录的数量/-1失败
	 */
	public static int uploadBOMFromExcel_SAPCS12(String filepath) {
		List<BOMContent> bomlist=BOMDataReaderExcel.getDataFromExcel_CS12(filepath);	//首先从Excel表中读取列表
		if(bomlist==null) return -1;
		int bkupcounter=BOMDataWriterDB.backupComplBOMFromTopNode(bomlist.get(0).getPn(), new Date());	//根据读取的列表，备份原BOM中的顶级节点下旧BOM
		if(bkupcounter==-1)
			logger.warn("无法备份原列表,可能是新的BOM.");
		int delcounter=BOMDataWriterDB.delComplBOMFromTopNode(bomlist.get(0).getPn());	//删除原BOM
		if(delcounter==-1)
			if(bkupcounter==-1)
				logger.warn("无法删除原列表,因为是新的BOM.");
			else {
				logger.error("错误，已经备份了原列表，但是未能成功删除这个BOM,请手动删除备份BOM。");
				return -1;
			}
		int writecounter=BOMDataWriterDB.writeDataToDB(bomlist, new Date());			//写入新BOM
		return writecounter;
	}
	
}
