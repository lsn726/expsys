package com.logsys.bom;

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
 * BOM的Excel数据读取器
 * @author lx8sn6
 */
public class BOMDataReaderExcel {

	private static final Logger logger=Logger.getLogger(BOMDataReaderExcel.class);
	
	/**SAP CS12标题行数*/
	private static final int ROW_HEADER_SAPCS12=0;
	
	/**SAP CS12内容开始行*/
	private static final int ROW_BEGIN_SAPCS12=1;
	
	/**SAP CS12等级列*/
	private static final int COL_LVL_SAPCS12=0;
	
	/**SAP CS12的组件号列*/
	private static final int COL_COMPN_SAPCS12=1;
	
	/**SAP CS12消耗数量列*/
	private static final int COL_QTY_SAPCS12=3;
	
	/**SAP CS12消耗计量单位*/
	private static final int COL_CUOM_SAPCS12=4;
	
	/**
	 * 从SAP的CS12命令所导出的Excel表格中提取对象,对于CS12的导出文件，要求先将总成件行加载ROW_BEGIN_SAPCS12行，并把Level标为0
	 * @param filepath 文件路径
	 * @return List<BOM信息>列表/null失败
	 */
	public static List<BOMContent> getDataFromExcel_CS12(String filepath) {
		if(filepath==null) {
			logger.error("不能从Excel中获取BOM信息,文件路径为null.");
			return null;
		}
		File file=new File(filepath);
		if(!file.exists()) {
			logger.error("不能从Excel中读取BOM信息,文件不存在。");
			return null;
		}
		if(file.isDirectory()) {
			logger.error("不能从Excel中获取BOM信息，文件路径是目录.");
			return null;
		}
		Workbook datasrc;
		InputStream instream;
		try {
			instream=new FileInputStream(filepath);
			datasrc=WorkbookFactory.create(instream);
			if(!validator_CS12(datasrc)) {
				logger.error("文件验证错误，不能从Excel表中读取BOM信息。");
				return null;
			}
		} catch(Throwable ex) {
			logger.error("文件打开错误，也许不是正确的Excel格式。");
			return null;
		}
		Sheet sheet;
		List<BOMContent> bomlist;
		BOMContent bomcon;
		try {
			sheet=datasrc.getSheetAt(0);
			bomlist=new ArrayList<BOMContent>();
			for(Row row:sheet) {
				if(row.getRowNum()<ROW_BEGIN_SAPCS12) continue;
				bomcon=new BOMContent();
				row.getCell(COL_LVL_SAPCS12).setCellType(Cell.CELL_TYPE_STRING);
				if(row.getCell(COL_LVL_SAPCS12).getStringCellValue().contains("."))
					bomcon.setLvl(Integer.parseInt(row.getCell(COL_LVL_SAPCS12).getStringCellValue().replace(".", "")));
				else
					bomcon.setLvl(Integer.parseInt(row.getCell(COL_LVL_SAPCS12).getStringCellValue()));
				row.getCell(COL_COMPN_SAPCS12).setCellType(Cell.CELL_TYPE_STRING);
				bomcon.setPn(row.getCell(COL_COMPN_SAPCS12).getStringCellValue());
				row.getCell(COL_QTY_SAPCS12).setCellType(Cell.CELL_TYPE_NUMERIC);
				bomcon.setQty(row.getCell(COL_QTY_SAPCS12).getNumericCellValue());
				bomcon.setUnit(row.getCell(COL_CUOM_SAPCS12).getStringCellValue());
				bomlist.add(bomcon);
			}
		} catch(Throwable ex) {
			logger.error("数据读取错误:"+ex);
			return null;
		}
		return bomlist;
	}
	
	/**
	 * SAP_CS12文件验证器，通过表头验证文件合法性
	 * @param filepath 要验证的文件路径
	 * @return 验证通过true/验证失败false
	 */
	private static boolean validator_CS12(Workbook wb) {
		if(wb==null) {
			logger.error("无法验证文件，参数为空。");
			return false;
		}
		Sheet sheet=wb.getSheetAt(0);
		Row header=sheet.getRow(ROW_HEADER_SAPCS12);
		if(header.getCell(COL_LVL_SAPCS12).getStringCellValue().equals("Explosion level"))
			if(header.getCell(COL_COMPN_SAPCS12).getStringCellValue().equals("Component number"))
				if(header.getCell(COL_QTY_SAPCS12).getStringCellValue().equals("Comp. Qty (CUn)"))
					if(header.getCell(COL_CUOM_SAPCS12).getStringCellValue().equals("Component unit"))
						return true;
		return false;
	}
	
}
