package com.logsys.bom;

import java.util.Date;
import java.util.List;

/**
 * BOM流程类
 * @author lx8sn6
 */
public class BOMProcess {

	/**
	 * 将SAP的CS12中所含有的BOM导入到数据库中
	 * @param filepath CS12的TCode所倒出后的文件
	 * @return 写入记录的数量/-1失败
	 */
	public static int uploadBOMFromExcel_SAPCS12(String filepath) {
		List<BOMContent> bomlist=BOMDataReaderExcel.getDataFromExcel_CS12(filepath);	//首先从Excel表中读取列表
		if(bomlist==null) return -1;
		int bkupcounter=BOMDataWriterDB.backupComplBOMFromTopNode(bomlist.get(0).getPn(), new Date());	//根据读取的列表，备份原BOM中的顶级节点下旧BOM
		if(bkupcounter==-1) return -1;
		
		return -1;
	}
	
}
