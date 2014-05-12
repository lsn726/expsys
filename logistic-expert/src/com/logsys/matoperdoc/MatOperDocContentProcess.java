package com.logsys.matoperdoc;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * 物料操作记录流程类
 * @author ShaonanLi
 */
public class MatOperDocContentProcess {

	private static final Logger logger=Logger.getLogger(MatOperDocContentProcess.class);
	
	/**
	 * 将从SAP的MB51中倒出的数据写入数据库
	 * @param filepath 文件路径
	 * @return 写入条数
	 */
	public static int importSAPMb51ExcelFileIntoDB(String filepath) {
		List<MatOperDocContent> doclist=MatOperDocContentSAPExcelReaderMB51.readDataFromSAPExcel(filepath);
		int qtywrited=MatOperDocContentDataWriterDB.writeToDB(doclist);
		logger.info("物料操作数据成功写入数据库。物料导出["+doclist.size()+"]条,物料写入["+qtywrited+"]条。");
		return qtywrited;
	}
	
}
