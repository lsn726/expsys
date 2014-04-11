package com.logsys.production;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.setting.Settings;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.setting.pd.bwi.BWIProductionExcelInfo;
import com.logsys.util.Location;

/**
 * 生产数据提取器，从Excel提取
 * @author ShaonanLi
 */
public class ProductionDataReaderExcel {

	private static final Logger logger=Logger.getLogger(ProductionDataReaderExcel.class);
	
	private static BWIProductionExcelInfo pdExcelInfo=Settings.BWISettings.pdExcelInfo;
	
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
			sheet=wb.getSheet(pdExcelInfo.getConfigSheetName());
			temploc=pdExcelInfo.getPrdLineNameLocInConfigSheet();
			prodline=sheet.getRow(temploc.row).getCell(temploc.row).getStringCellValue();	//获取生产线名称
			prodline=plinfo.getStdProdlineNameByAlias(prodline);							//获取正式名称
			if(prodline==null) {						//如果没有正式生产线名称，则退出
				logger.error("出现错误，没有找到指定生产线的正式名称。");
				return null;
			}
			temploc=pdExcelInfo.getDateInfoLocInConfigSheet();
			tempdate=sheet.getRow(temploc.row).getCell(temploc.column).getDateCellValue();	//获取月份信息
			tempcal=Calendar.getInstance();
			tempcal.setTime(tempdate);
			month=tempcal.get(Calendar.MONTH);
			pdlist=new ArrayList<ProductionContent>();
			for(daycounter=1;;daycounter++) {			//循环遍历每天的生产Sheet，从1日开始
				sheet=wb.getSheet(String.valueOf(month));
				if(sheet==null) break;					//如果没有更多day_of_month的sheet，则说明已经遍历完成
				for(Row row:sheet) {					//按行遍历当前sheet
					
				}
			}
		} catch(Throwable ex) {
			logger.error("从Excel表格中读取生产数据出现错误。",ex);
			return null;
		}
		return null;
	}

}
