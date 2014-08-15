package com.logsys.report.bwi;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.stock.StockWHContent;
import com.logsys.util.Location;

/**
 * BWI库房Excel信息汲取器
 * @author lx8sn6
 */
public class BWIWarehouseExcelReport {

	private static final Logger logger=Logger.getLogger(BWIWarehouseExcelReport.class);
	
	/**验证器*/
	private static Map<Location,String> validatorMap;
	
	/**库房Workbook对象*/
	private Workbook whwb;
	
	/**库存数据图：pn->到货日期->库存对象,到货日期按照升序排序*/
	private Map<String,TreeMap<Date,StockWHContent>> stockpool;
	
	/**标题行行数*/
	private static final int ROW_HEADER=0;
	
	/**起始行行数*/
	private static final int ROW_START=1;
	
	/**到货日期列*/
	private static final int COL_AOGDATE=0;
	
	/**零件号列*/
	private static final int COL_PN=1;
	
	/**订单号列*/
	private static final int COL_PO=2;
	
	/**批次号列*/
	private static final int COL_BN=3;
	
	/**ASN列*/
	private static final int COL_ASN=4;
	
	/**收货单据列*/
	private static final int COL_RECN=5;
	
	/**到货数量列*/
	private static final int COL_ARVQTY=6;
	
	/**供应商列*/
	private static final int COL_VENDOR=7;
	
	/**入库日期列 */
	private static final int COL_PUTINDATE=8;
	
	/**入库数量列*/
	private static final int COL_PUTINQTY=9;
	
	/**剩余数量列*/
	private static final int COL_QTYLEFT=10;
	
	static {
		validatorMap=new HashMap<Location,String>();
		validatorMap.put(new Location(ROW_HEADER,COL_AOGDATE), "到货日期");
		validatorMap.put(new Location(ROW_HEADER,COL_PN),"零件品号");
		validatorMap.put(new Location(ROW_HEADER,COL_PO), "订单号");
		validatorMap.put(new Location(ROW_HEADER,COL_BN), "批次号");
		validatorMap.put(new Location(ROW_HEADER,COL_ASN), "内向交货单号");
		validatorMap.put(new Location(ROW_HEADER,COL_RECN), "接货凭证");
		validatorMap.put(new Location(ROW_HEADER,COL_ARVQTY), "到货数量");
		validatorMap.put(new Location(ROW_HEADER,COL_VENDOR), "供应商");
		validatorMap.put(new Location(ROW_HEADER,COL_PUTINDATE), "入库日期");
		validatorMap.put(new Location(ROW_HEADER,COL_PUTINQTY), "入库数量");
		validatorMap.put(new Location(ROW_HEADER,COL_QTYLEFT), "剩余库存");
	}
	
	private BWIWarehouseExcelReport(Workbook whwb) {
		this.whwb=whwb;
		stockpool=new HashMap<String,TreeMap<Date,StockWHContent>>();
	}

	/**
	 * 创建库房报告提取对象
	 * @param whexcelpath 库房的Excel的源文件路径
	 * @return 创建的报告对象/null创建失败
	 */
	public static BWIWarehouseExcelReport createReportObject(String whexcelpath) {
		try {
			Workbook wb=WorkbookFactory.create(new File(whexcelpath));
			return new BWIWarehouseExcelReport(wb);
		} catch(Exception ex) {
			logger.error("创建库房Excel源文件对象出现错误,创建报告对象失败.");
			return null;
		}
	}
	
	/**
	 * 分析库房源报告对象,包含下列步骤:
	 * 1. 验证Sheet对象。
	 * 2. 提取数据
	 * 3. 生成数据池
	 */
	public void analyzeWorkbook() {
		Sheet itsheet;		//遍历sheet对象
		for(int sheetindex=0;;sheetindex++) {	//遍历所有sheet
			itsheet=whwb.getSheetAt(sheetindex);
			if(itsheet==null) break;
			if(!validateSheet(itsheet)) {
				logger.info("Sheet["+itsheet.getSheetName()+"]未通过验证，可能不是包含数据的sheet，跳过这个sheet。");
				continue;
			}
			StockWHContent cont;
			Cell cell=null;
			String pn;
			for(Row row:itsheet) {				//遍历sheet中的所有行
				if(row.getRowNum()<ROW_START) continue;
				cont=new StockWHContent();
				try {
					cell=row.getCell(COL_PN);		//读取Part Number
					if(cell!=null&&cell.getCellType()!=Cell.CELL_TYPE_ERROR) {
						pn=String.valueOf(cell.getNumericCellValue());	//赋值Part Number
						if(pn==""||pn==null) continue;	//没有PN号则跳过
						cont.setPn(pn);
						if(!stockpool.containsKey(pn))	//如果在数据池中没有pn所对应的Map，则需要创建新的Map
							stockpool.put(pn, new TreeMap<Date,StockWHContent>());
					} else continue;				//没有PN号则跳过
					cell=row.getCell(COL_AOGDATE);	//读取到货日期
					if(cell!=null) cont.setAogdate(cell.getDateCellValue());
					cell=row.getCell(COL_PO);		//读取订单号
					if(cell!=null) cont.setPn(String.valueOf(cell.getNumericCellValue()));
					cell=row.getCell(COL_BN);		//读取批次号
					if(cell!=null) cont.setBn(cell.getStringCellValue());
					cell=row.getCell(COL_ASN);		//读取ASN
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if(cell!=null) cont.setAsn(cell.getStringCellValue());
					cell=row.getCell(COL_RECN);		//读取收货单号
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if(cell!=null) cont.setRecn(cell.getStringCellValue());
					cell=row.getCell(COL_ARVQTY);	//读取到货数量
					if(cell!=null) cont.setArvqty(cell.getNumericCellValue());
					cell=row.getCell(COL_VENDOR);	//读取供应商
					if(cell!=null) cont.setVendor(cell.getStringCellValue());
					cell=row.getCell(COL_PUTINDATE);//读取入库日期
					if(cell!=null) cont.setPutindate(cell.getDateCellValue());
					cell=row.getCell(COL_PUTINQTY); //读取入库数量
					if(cell!=null) cont.setPutinqty(cell.getNumericCellValue());
					cell=row.getCell(COL_QTYLEFT);  //读取剩余库存
					if(cell!=null) cont.setQtyleft(cell.getNumericCellValue());
				} catch(Exception ex) {
					logger.warn("分析Sheet["+itsheet.getSheetName()+"]中位于行["+(cell.getRowIndex()+1)+"]列["+(cell.getColumnIndex()+1)+"]的单元格时出现错误,跳过这条记录。");
					continue;
				}
				stockpool.get(pn).put(cont.getAogdate(), cont);		//以到货日期作为索引加入Map
			}
		}
	}
	
	/**
	 * 验证Sheet对象
	 * @return 验证通过true,验证失败false
	 */
	private boolean validateSheet(Sheet sheet) {
		if(sheet==null) {
			logger.info("不能验证Sheet,参数为空.");
			return false;
		}
		Cell valcell;		//验证cell
		for(Location loc:validatorMap.keySet()) {
			valcell=sheet.getRow(loc.row).getCell(loc.column);
			if(valcell==null) return false;
			if(!valcell.getStringCellValue().equals(validatorMap.get(loc))) return false;
		}
		return true;
	}
	
}
