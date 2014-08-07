package com.logsys.stock;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Stock流程类
 * @author lx8sn6
 */
public class StockProcess {

	private static final Logger logger=Logger.getLogger(StockProcess.class);
	
	/**
	 * 将SAP的MB52命令倒出的Excel库存数据写入数据库
	 * @param filepath Excel文件路径
	 * @param stockdate 库存记录的日期，null则默认为当天
	 * @return 写入数据的条数/-1失败
	 */
	public static int importStockDataFromExcel_SAP_MB52(String filepath,Date stockdate) {
		ExcelStockService_SAP_MB52 service=ExcelStockService_SAP_MB52.getExcelReader("e:\\stock.xlsx");
		if(!service.extractStockList(new Date())) {
			logger.error("提取库存数据时出现错误.操作失败。");
			return -1;
		}
		int writedqty=service.writeStockListToDB();
		if(writedqty<0) {
			logger.error("将库存列表写入数据库时出现错误。操作失败.");
			return -1;
		}
		logger.info("库存数据写入成功。写入数量["+writedqty+"]条.");
		return writedqty;
	}
	
}
