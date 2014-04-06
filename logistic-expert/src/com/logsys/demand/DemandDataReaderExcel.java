package com.logsys.demand;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 需求数据Excel文件读取器
 * @author lx8sn6
 */
public class DemandDataReaderExcel {
	
	private static Logger logger=Logger.getLogger(DemandDataReaderExcel.class);

	/**需求日期列*/
	private static final int COL_DATE=0;
	
	/**需求型号列*/
	private static final int COL_PN=1;
	
	/**需求数量*/
	private static final int COL_QTY=2;
	
	/**
	 * 从文件获取需求
	 * @return 包含需求数据的需求
	 */
	public static List<DemandContent> getDataFromFile(String filepath) {
		if(filepath==null) {
			logger.error("文件路径为空");
			return null;
		}
		File file=new File(filepath);
		if(!file.exists()) {
			logger.error("文件不存在");
			return null;
		}
		if(file.isDirectory()) {
			logger.error("文件路径是文件夹 ");
			return null;
		}
		List<DemandContent> demandlist;
		Workbook datasrc;
		InputStream readStream;
		Sheet sheet;
		try {
			readStream=new FileInputStream(filepath);
			datasrc=WorkbookFactory.create(readStream);
			sheet=datasrc.getSheetAt(0);
		} catch(Throwable e) {
			logger.error("文件打开错误,或者文件格式错误。可能不是正确的Excel文件。");
			return null;
		}
		demandlist=new ArrayList<DemandContent>();
		DemandContent node;
		for(Row row:sheet) {
			node=new DemandContent();
			try {
				node.setDate(row.getCell(COL_DATE).getDateCellValue());
				row.getCell(COL_PN).setCellType(Cell.CELL_TYPE_STRING);
				node.setPn(row.getCell(COL_PN).getStringCellValue());
				row.getCell(COL_QTY).setCellType(Cell.CELL_TYPE_NUMERIC);
				node.setQty(row.getCell(COL_QTY).getNumericCellValue());
			} catch(Throwable ex) {
				logger.error("Excel中数据格式错误。");
				ex.printStackTrace();
				return null;
			}
			demandlist.add(node);
		}
		return demandlist;
	}
	
}
