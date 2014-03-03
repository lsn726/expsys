package com.logsys.demand;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 需求数据写入器:Excel
 * @author lx8sn6
 */
public class DemandDataWriterExcel {

	private static final Logger logger=Logger.getLogger(DemandDataWriterExcel.class);
	
	/**
	 * 将由DemandUtil.demListToMapByPn()生成的数据写入Excel表格
	 * @param filepath 文件路径
	 * @param demmap 经过DemandUtil.demListToMapByPn()处理，包含Demand的数据表格
	 * @return 成功true/失败false
	 */
	public static boolean writeToExcel(String filepath,Map<String,Map<Date,DemandContent>> demmap) {
		if(filepath==null) {
			logger.error("文件路径参数为空.");
			return false;
		}
		File file=new File(filepath);
		if(file.exists()) {
			logger.error("文件已存在.");
			return false;
		}
		try {
			Workbook wb=new XSSFWorkbook();
			Sheet demsheet=wb.createSheet("Demand");
			
			FileOutputStream fileOut=new FileOutputStream(filepath);
			wb.write(fileOut);
			fileOut.close();
			return true;
		} catch(Throwable ex) {
			logger.error("需求写入出现错误:"+ex);
			ex.printStackTrace();
			return false;
		}
	}

}
