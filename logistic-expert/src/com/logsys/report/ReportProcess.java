package com.logsys.report;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.logsys.util.Location;
import com.logsys.util.Matrixable;

/**
 * 报告流程类
 * @author ShaonanLi
 */
public class ReportProcess {

	private static final Logger logger=Logger.getLogger(ReportProcess.class);
	
	/**
	 * 产生MRP矩阵报告并写入Excel表格
	 * @param weeknum 需求表格长度
	 * @return 成功true/失败false
	 */
	public static boolean genMRPMatrixToExcel(String filepath,int weeknum) {
		if(filepath==null) {
			logger.error("不能产生需求矩阵并写入Excel，文件路径为空。");
			return false;
		}
		File file=new File(filepath);
		if(file.exists()) {
			logger.error("不能产生需求矩阵并写入Excel，文件["+filepath+"]已存在");
			return false;
		}
		MrpReportForExcel mrpreport=new MrpReportForExcel();		//产生MRP报表对象
		Matrixable demmatrix=mrpreport.getDemandMatrix(weeknum); //生成报表对象
		if(demmatrix==null) {
			logger.error("不能产生需求矩阵并写入Excel，需求矩阵生成失败。");
			return false;
		} else
			logger.info("需求矩阵生成成功。");
		Workbook wb=new XSSFWorkbook();								//创建工作簿
		Sheet demsheet=wb.createSheet("DemandMatrix");				//创建sheet
		demmatrix.writeToExcelSheet(demsheet, new Location(0,0));	//需求矩阵写入sheet，起始位置0,0
		try {
			FileOutputStream fileOut=new FileOutputStream(filepath);
			wb.write(fileOut);
			fileOut.close();
			logger.info("成功生成需求矩阵并写入文件["+filepath+"]");
			return true;
		} catch(Throwable ex) {
			logger.error("不能产生需求矩阵并写入Excel，Excel表格写入磁盘时出现错误：",ex);
			return false;
		}
	}

	/**
	 * 产生需求矩阵(按天/周/月)并写入Excel文件filepath
	 * @param filepath 文件路径
	 * @param genBackTraceSheet 是否写入回溯需求矩阵
	 * @return 成功true/失败false;
	 */
	public static boolean genDemandMatrixToExcel(String filepath, boolean genBackTraceSheet) {
		boolean result;
		try {
			result=new DemandReportForExcel().writeReportToFile(filepath, genBackTraceSheet);
			if(result)
				logger.info("成功产生需求矩阵并写入文件["+filepath+"]");
			else
				logger.info("不能产生需求矩阵和写入文件.");
			return result;
		} catch (Exception e) {
			logger.error("不能产生需求报告：",e);
			return false;
		}
	}
	
}
