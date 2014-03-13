package com.logsys.material;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 物料信息读取表,从Excel命令读取的数据
 * @author lx8sn6
 */
public class MaterialDataReaderExcel {

	private static Logger logger=Logger.getLogger(MaterialDataReaderExcel.class);
	
	/**标题行*/
	private static final int ROW_HEADER=3;
	
	/**开始行*/
	private static final int ROW_BEGIN=5;
	
	/**pn列*/
	private static final int COL_PN=1;
	
	/**工厂列*/
	private static final int COL_PLANT=2;
	
	/**描述列*/
	private static final int COL_DESC=4;
	
	/**物料类型列*/
	private static final int COL_TYPE=6;
	
	/**计量单位列*/
	private static final int COL_UOM=8;
	
	/**价格列*/
	private static final int COL_PRICE=14;
	
	/**汇率列*/
	private static final int COL_CURRENCY=15;
	
	/**计价数量列*/
	private static final int COL_PRICEQTY=16;
	
	/**
	 * 从指定的文件路径获取物料基础信息数据
	 * @param filepath 指定的文件路径
	 * @return 成功物料信息列表<pn,MaterialContent>/失败null
	 */
	public static Map<String,MaterialContent> getMatDataFromExcel_MM60(String filepath) {
		if(filepath==null) {
			logger.error("参数为空.");
			return null;
		}
		File file=new File(filepath);
		if(!file.exists()) {
			logger.error("文件不存在.");
			return null;
		}
		if(file.isDirectory()) {
			logger.error("文件是目录.");
			return null;
		}
		Workbook datasrc;
		InputStream readstream;
		Sheet sheet;
		try {
			readstream=new FileInputStream(filepath);
			datasrc=WorkbookFactory.create(readstream);
			sheet=datasrc.getSheetAt(0);
			if(!isExcelContentValid(datasrc)) {
				logger.error("物料数据文件格式错误。");
				return null;
			}
		} catch(Throwable e) {
			logger.error("Excel文件读取错误.也许是文件并非Excel文件。");
			return null;
		}
		Map<String,MaterialContent> matlist=new HashMap<String,MaterialContent>();
		MaterialContent mcontent;
		for(Row row:sheet) {
			if(row.getRowNum()<ROW_BEGIN) continue;
			mcontent=new MaterialContent();
			row.getCell(COL_PN).setCellType(Cell.CELL_TYPE_STRING);
			mcontent.setPn(row.getCell(COL_PN).getStringCellValue());
			mcontent.setPlant(row.getCell(COL_PLANT).getStringCellValue());
			mcontent.setDescription(row.getCell(COL_DESC).getStringCellValue());
			mcontent.setType(row.getCell(COL_TYPE).getStringCellValue());
			mcontent.setUom(row.getCell(COL_UOM).getStringCellValue());
			mcontent.setPrice(row.getCell(COL_PRICE).getNumericCellValue());
			mcontent.setCurrency(row.getCell(COL_CURRENCY).getStringCellValue());
			mcontent.setQtyprice((int)row.getCell(COL_PRICEQTY).getNumericCellValue());
			matlist.put(mcontent.getPn(),mcontent);
		}
		return matlist;
	}
	
	/**
	 * Excel文件验证器,通过表头的内容格式验证文件是否正确
	 * @param wb Excel的Workbook对象
	 * @return 验证通过true/验证通过false
	 */
	private static boolean isExcelContentValid(Workbook wb) {
		if(wb==null) {
			logger.error("参数为空.");
			return false;
		}
		Sheet sheet=wb.getSheetAt(0);
		Row header=sheet.getRow(ROW_HEADER);
		if(header.getCell(COL_PN).getStringCellValue().equals("Material"))
			if(header.getCell(COL_PLANT).getStringCellValue().equals("Plnt"))
				if(header.getCell(COL_DESC).getStringCellValue().equals("Material Description"))
					if(header.getCell(COL_TYPE).getStringCellValue().equals("MTyp"))
						if(header.getCell(COL_UOM).getStringCellValue().equals("BUn"))
							if(header.getCell(COL_PRICE).getStringCellValue().equals("      Price"))
								if(header.getCell(COL_CURRENCY).getStringCellValue().equals("Crcy"))
									if(header.getCell(COL_PRICEQTY).getStringCellValue().equals("   per"))
										return true;
		return false;
	}

	
	
}