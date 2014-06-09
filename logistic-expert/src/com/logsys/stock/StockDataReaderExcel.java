package com.logsys.stock;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Stock数据读取器--Excel数据读取器
 * @author lx8sn6
 *
 */
public class StockDataReaderExcel {

	private static final Logger logger=Logger.getLogger(StockDataReaderExcel.class);
	
	/**
	 * 从SAP的mb52命令倒出的Excel列表中提取数据
	 * @param filepath Excel的sheet命令
	 * @return 库存信息列表/null
	 */
	public static List<StockContent> readDataFromExcelSheet_SAPMB52(String filepath) {
		if(filepath==null) {
			logger.error("不能读取Excel文件，文件路径为空.");
			return null;
		}
		File file=new File(filepath);
		if(!file.exists()) {
			logger.error("不能读取Excel文件，文件不存在.");
			return null;
		}
		if(file.isDirectory()) {
			logger.error("不能读取Excel文件，文件是目录.");
			return null;
		}
		Workbook datasrc;
		InputStream instream;
		try {
			instream=new FileInputStream(filepath);
			datasrc=WorkbookFactory.create(instream);
			if(!validator_mb52(datasrc)) {
				logger.error("文件验证错误，不能从Excel表中读取BOM信息。");
				return null;
			}
		} catch(Throwable ex) {
			logger.error("文件打开错误，也许不是正确的Excel格式。");
			return null;
		}
		return null;
	}
	
	/**
	 * MB52文件验证器
	 * @param wb 包含数据的Workbook对象
	 * @return 验证成功true/验证失败false
	 */
	private static boolean validator_mb52(Workbook wb) {
		if(wb==null) {
			logger.error("不能验证MB52的数据Excel，工作簿对象为null.");
			return false;
		}
		return true;
	}

}
