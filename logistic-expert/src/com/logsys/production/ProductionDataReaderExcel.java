package com.logsys.production;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.setting.Settings;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.util.Location;

/**
 * 生产数据提取器，从Excel提取
 * @author ShaonanLi
 */
public class ProductionDataReaderExcel {

	private static final Logger logger=Logger.getLogger(ProductionDataReaderExcel.class);
	
	private static BWIPLInfo plinfo=Settings.BWISettings.plinfo;
	
	/**
	 * 从文件filepath中读取数据
	 * @param filepath
	 * @return
	 */
	public static List<ProductionContent> readDataFromFile(String filepath) {
		File file=new File(filepath);			//由文件路径创建新文件
		Workbook wb;
		InputStream readstream;
		try {
			readstream=new FileInputStream(filepath);
			wb=WorkbookFactory.create(readstream);
		} catch(Throwable ex) {
			logger.error("不能从文件路径读取Excel文件。",ex);
			return null;
		}
		Sheet sheet=null;
		String sheetname;
		Location temploc;
		Date tempdate;
		Calendar tempcal;
		String prodline;		//生产线名称
		int month;				//Excel日报所在的表格月份
		int daycounter;			//日期计数器
		List<ProductionContent> pdlist;
		try {
		} catch(Throwable ex) {
			logger.error("从Excel表格中读取生产数据出现错误。",ex);
			return null;
		}
		return null;
	}

}
