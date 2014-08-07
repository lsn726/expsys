package com.logsys.stock;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;
import com.logsys.util.Location;

/**
 * Excel库存服务,负责处理来自SAP的MB52所导出的数据。需要由工厂函数getExcelReader()来创建对象。
 * @author lx8sn6
 */
public class ExcelStockService_SAP_MB52 {

	private static final Logger logger=Logger.getLogger(ExcelStockService_SAP_MB52.class);
	
	/**验证图*/
	private static Map<Location,String> validatorMap=new HashMap<Location,String>();
	
	/**数据起始行*/
	private static final int STARTROW=3;
	
	/**数据列:PartNumber*/
	private static final int COL_PN=1;
	
	/**数据列:工厂*/
	private static final int COL_PLANT=2;
	
	/**数据列:存储位置*/
	private static final int COL_SLOC=3;
	
	/**数据列:非限制区*/
	private static final int COL_UNS=4;
	
	/**数据列:计量单位*/
	private static final int COL_UOM=5;
	
	/**数据列:转移中数量*/
	private static final int COL_INTRAN=6;
	
	/**数据列:IQC*/
	private static final int COL_IQC=7;
	
	/**数据列:隔离数量*/
	private static final int COL_BLK=8;
	
	/**物料状态:非限制*/
	public static final String MAT_STATUS_UNS="Unrestricted";
	
	/**物料状态:IQC*/
	public static final String MAT_STATUS_IQC="IQC";
	
	/**物料状态:Block*/
	public static final String MAT_STATUS_BLK="Block";
	
	/**物料状态:运输中*/
	public static final String MAT_STATUS_INT="InTransit";
	
	/**包含Stock数据的sheet对象*/
	private Sheet stocksheet; 
	
	/**提取的库存列表数据*/
	private List<StockContent> stocklist;
	
	static {
		validatorMap.put(new Location(1,1), "Material Number");
		validatorMap.put(new Location(1,2), "Plnt");
		validatorMap.put(new Location(1,3), "SLoc");
		validatorMap.put(new Location(1,4), "      Unrestricted");
		validatorMap.put(new Location(1,5), "BUn");
		validatorMap.put(new Location(1,6), "   Transit/Transf.");
		validatorMap.put(new Location(1,7), "  In Quality Insp.");
		validatorMap.put(new Location(1,8), "           Blocked");
	}
	
	private ExcelStockService_SAP_MB52(Sheet datasheet) {
		stocksheet=datasheet;
	}
	
	/**
	 * 以文件路径为参数获取Excel的SAP MB52的Excel文件读取器
	 * @param filepath Excel文件路径
	 * @return 读取器对象
	 */
	public static ExcelStockService_SAP_MB52 getExcelReader(String filepath) {
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
		ExcelStockService_SAP_MB52 mb52reader;
		Workbook datasrc;
		InputStream instream;
		try {
			instream=new FileInputStream(filepath);
			datasrc=WorkbookFactory.create(instream);
			if(!validator(datasrc)) {				//验证Excel文件
				logger.error("文件验证错误，不能从Excel表中读取BOM信息。");
				return null;
			}
			mb52reader=new ExcelStockService_SAP_MB52(datasrc.getSheetAt(0));
		} catch(Throwable ex) {
			logger.error("文件打开错误，也许不是正确的Excel格式。");
			return null;
		}
		return mb52reader;
	}
	
	/**
	 * MB52文件验证器
	 * @param wb 包含数据的Workbook对象
	 * @return 验证成功true/验证失败false
	 */
	private static boolean validator(Workbook wb) {
		if(wb==null) {
			logger.error("不能验证MB52的数据Excel，工作簿对象为null.");
			return false;
		}
		Sheet sheet=wb.getSheetAt(0);
		if(sheet==null) {
			logger.error("SAP_MB52库存Sheet验证失败.不能提取位于位置0的Sheet对象.");
			return false;
		}
		Cell cell;
		String cellstr;
		for(Location loc:validatorMap.keySet()) {		//遍历位置以验证表格
			cell=sheet.getRow(loc.row).getCell(loc.column);
			if(cell==null) {
				logger.error("SAP_MB52库存Sheet验证失败.位置行["+loc.row+"]列["+loc.column+"]的单元格为空.");
				return false;
			}
			cellstr=cell.getStringCellValue();
			if(!cellstr.equals(validatorMap.get(loc))) {
				logger.error("SAP_MB52库存Sheet验证失败.位置行["+loc.row+"]列["+loc.column+"]的单元格内容+["+cellstr+"]与验证字符串内容["+validatorMap.get(loc)+"]不相符.");
				return false;
			}
		}
		return true;
	}

	/**
	 * 提取Stock列表
	 * @param date 提取出的库存所标注的库存日期，null则为当天。
	 * @return true成功/false失败
	 */
	public boolean extractStockList(Date date) {
		if(stocksheet==null) {
			logger.error("不能提取库存数据，库存数据Sheet对象为空，是由于没有正确初始化。");
			return false;
		}
		if(date==null) date=new Date();
		String pn;
		String plant;
		String sloc;
		String uom;
		stocklist=new ArrayList<StockContent>();
		double qty;
		try {
			for(Row row:stocksheet) {
				if(row.getRowNum()<STARTROW) continue;
				row.getCell(COL_PN).setCellType(Cell.CELL_TYPE_STRING);
				pn=row.getCell(COL_PN).getStringCellValue();
				plant=row.getCell(COL_PLANT).getStringCellValue();
				row.getCell(COL_SLOC).setCellType(Cell.CELL_TYPE_STRING);
				sloc=row.getCell(COL_SLOC).getStringCellValue();
				uom=row.getCell(COL_UOM).getStringCellValue();
				qty=row.getCell(COL_UNS).getNumericCellValue();		//读取非限制
				if(qty!=0.0)				//如果非限制有数值，则加入非限制对象
					stocklist.add(new StockContent(date,pn,plant,sloc,qty,uom,MAT_STATUS_UNS));
				qty=row.getCell(COL_INTRAN).getNumericCellValue();	//读取在途
				if(qty!=0.0)
					stocklist.add(new StockContent(date,pn,plant,sloc,qty,uom,MAT_STATUS_INT));
				qty=row.getCell(COL_IQC).getNumericCellValue();		//读取IQC
				if(qty!=0.0)
					stocklist.add(new StockContent(date,pn,plant,sloc,qty,uom,MAT_STATUS_IQC));
				qty=row.getCell(COL_BLK).getNumericCellValue();		//读取Block
				if(qty!=0.0)
					stocklist.add(new StockContent(date,pn,plant,sloc,qty,uom,MAT_STATUS_BLK));
			}
		} catch(Exception ex) {
			logger.error("在读取库存列表时出现错误。",ex);
			stocklist=null;
			return false;
		}
		return true;
	}
	
	/**
	 * 将库存数据写入数据库
	 * @return 提取数据的条数/-1失败
	 */
	public int writeStockListToDB() {
		if(stocklist==null) {
			logger.error("不能将库存数据写入数据库，库存列表尚未被初始化。需要运行extractStockList()函数。");
			return -1;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("session创建出现错误:"+ex);
			return -1;
		}
		int counter=0;
		for(StockContent stockcont:stocklist) {
			session.save(stockcont);
			if(counter++%100==0) {
				session.flush();
				session.clear();
			}
		}
		session.getTransaction().commit();
		session.close();
		return counter;
	}
	
}
