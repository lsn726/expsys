package com.logsys.matoperdoc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.util.Location;

/**
 * SAP倒出的物料操作文档Excel数据读取器--MB51的倒出数据读取器
 * @author lx8sn6
 */
public class MatOperDocContentSAPExcelReaderMB51 {

	private static final Logger logger=Logger.getLogger(MatOperDocContentSAPExcelReaderMB51.class);
	
	/**Excel的验证图：位置->字符串*/
	private static final Map<Location,String> excelValidatorMap=new HashMap<Location,String>();
	
	/**Excel表中的日期格式转换器*/
	//private static final DateFormat dateformat=new SimpleDateFormat("yyyy.MM.dd");
	
	/**正式记录开始行数*/
	private static final int ROW_BEGIN=3;
	
	/**PN列*/
	private static final int COL_PN=1;
	
	/**Storage Location列*/
	private static final int COL_SLOC=1;
	
	/**Plant列*/
	private static final int COL_PLANT=10;
	
	/**订单号列*/
	private static final int COL_ORDER=10;
	
	/**文档号列*/
	private static final int COL_DOCNUM=4;
	
	/**移动类型列*/
	private static final int COL_MVMTYPE=2;
	
	/**Posting Date列*/
	private static final int COL_PSTDATE=7;
	
	/**操作数量列*/
	private static final int COL_QTY=8;
	
	/**计量单位列*/
	private static final int COL_UOM=9;
	
	/**客户列*/
	private static final int COL_CUSTOMER=12;
	
	/**供应商列*/
	private static final int COL_VENDOR=13;
	
	/**记录头部列*/
	private static final int COL_HEADER=14;
	
	static {
		//初始化验证字符串
		excelValidatorMap.put(new Location(1,1), "Material");
		excelValidatorMap.put(new Location(1,5), "Material Description");
		excelValidatorMap.put(new Location(1,COL_PLANT), "Plnt");
		excelValidatorMap.put(new Location(1,11), "Name 1");
		excelValidatorMap.put(new Location(2,COL_PN), "SLoc");
		excelValidatorMap.put(new Location(2,COL_MVMTYPE), "MvT");
		excelValidatorMap.put(new Location(2,COL_DOCNUM), "Mat. Doc.");
		excelValidatorMap.put(new Location(2,COL_PSTDATE), "Pstng Date");
		excelValidatorMap.put(new Location(2,COL_QTY), "         Quantity");
		excelValidatorMap.put(new Location(2,COL_UOM), "BUn");
		excelValidatorMap.put(new Location(2,COL_ORDER), "Order");
		excelValidatorMap.put(new Location(2,COL_CUSTOMER), "Customer");
		excelValidatorMap.put(new Location(2,COL_VENDOR), "Vendor");
		excelValidatorMap.put(new Location(2,COL_HEADER), "Document Header Text");
	}
	
	/**
	 * 从SAP中倒出的Excel操作记录文档数据读取器,包含数据的sheet位于位置0
	 * @param filepath 文件路径
	 * @return 物料操作列表/null失败
	 */
	public static List<MatOperDocContent> readDataFromSAPExcel(String filepath) {
		Workbook wb;
		InputStream readstream;
		try {
			readstream=new FileInputStream(filepath);
			wb=WorkbookFactory.create(readstream);
		} catch(Throwable ex) {
			logger.error("不能从文件路径读取Excel文件。",ex);
			return null;
		}
		Sheet sheet;
		String matpn=null;	//操作物料pn
		String plant=null;	//工厂
		List<MatOperDocContent> doclist;	//文档列表对象
		MatOperDocContent doctemp;
		int lastrow=0;		//记录上一行行数
		try {
			sheet=wb.getSheetAt(0);
			if(!validateSheet(sheet)) return null;
			doclist=new ArrayList<MatOperDocContent>();
			for(Row row:sheet) {			//开始遍历sheet表单
				//System.out.println("正在处理第"+row.getRowNum()+"行");
				if(row.getRowNum()<ROW_BEGIN) continue;		//如果行数小于起始行，则继续
				if(row.getRowNum()-lastrow>1) {				//如果前一次出现跳行，则说明前一个物料操作记录结束，新的物料操作记录开始
					if(row.getCell(COL_PLANT)!=null) {		//跳行后如果有工厂更新，则更新工厂
						row.getCell(COL_PLANT).setCellType(Cell.CELL_TYPE_STRING);
						plant=row.getCell(COL_PLANT).getStringCellValue();
					} else
						plant=null;
					if(row.getCell(COL_PN)!=null) {			//跳行后如果有PN更新，则更新PN
						row.getCell(COL_PN).setCellType(Cell.CELL_TYPE_STRING);
						matpn=row.getCell(COL_PN).getStringCellValue();
					} else
						matpn=null;
					lastrow=row.getRowNum();
					continue;								//跳行后的第一行为物料和工厂信息，因此跳过
				}
				if(row.getCell(COL_MVMTYPE)!=null) {		//如果MVMCell不为null，则需要沿用上一行的matpn和plant，并提取数据
					doctemp=extractMatOperDoc(row);
					if(doctemp==null) return null;			//提取失败则返回null;
					doctemp.setPn(matpn);
					doctemp.setPlant(plant);
					doclist.add(doctemp);
					lastrow=row.getRowNum();
					continue;
				}
				logger.error("不能继续从Excel中提取操作记录文档数据，在没有出现跳行的情况下，发现了MVMType为空的情况：位于行["+row.getRowNum()+"]");
				return null;
			}
			return doclist;
		} catch(Throwable ex) {
			logger.error("读取SAP中提取的Excel操作记录文档数据出现错误:",ex);
			return null;
		}
		
	}
	
	/**
	 * 从Excel行中提取Content信息
	 * @param row 行对象
	 * @param pn 操作物料
	 * @return 提取的对象/null
	 */
	private static MatOperDocContent extractMatOperDoc(Row row) {
		if(row==null) {
			logger.error("不能从行对象中提取物料操作记录，行对象为空。");
			return null;
		}
		try {
			MatOperDocContent operdoc=new MatOperDocContent();
			if(row.getCell(COL_SLOC)!=null) {
				row.getCell(COL_SLOC).setCellType(Cell.CELL_TYPE_STRING);
				operdoc.setSloc(row.getCell(COL_SLOC).getStringCellValue());
			}
			row.getCell(COL_MVMTYPE).setCellType(Cell.CELL_TYPE_STRING);
			operdoc.setMvtype(row.getCell(COL_MVMTYPE).getStringCellValue());
			row.getCell(COL_DOCNUM).setCellType(Cell.CELL_TYPE_STRING);
			operdoc.setDocnum(row.getCell(COL_DOCNUM).getStringCellValue());
			//operdoc.setPostdate(dateformat.parse(row.getCell(COL_PSTDATE).getStringCellValue()));
			operdoc.setPostdate(row.getCell(COL_PSTDATE).getDateCellValue());
			if(row.getCell(COL_QTY)!=null)
				operdoc.setQty(row.getCell(COL_QTY).getNumericCellValue());
			if(row.getCell(COL_UOM)!=null)
				operdoc.setUom(row.getCell(COL_UOM).getStringCellValue());
			if(row.getCell(COL_ORDER)!=null) {
				row.getCell(COL_ORDER).setCellType(Cell.CELL_TYPE_STRING);
				operdoc.setOrdernum(row.getCell(COL_ORDER).getStringCellValue());
			}
			if(row.getCell(COL_CUSTOMER)!=null) {
				row.getCell(COL_CUSTOMER).setCellType(Cell.CELL_TYPE_STRING);
				operdoc.setCustomer(row.getCell(COL_CUSTOMER).getStringCellValue());
			}
			if(row.getCell(COL_VENDOR)!=null) {
				row.getCell(COL_VENDOR).setCellType(Cell.CELL_TYPE_STRING);
				operdoc.setVendor(row.getCell(COL_VENDOR).getStringCellValue());
			}
			if(row.getCell(COL_HEADER)!=null) {
				row.getCell(COL_HEADER).setCellType(Cell.CELL_TYPE_STRING);
				operdoc.setHeader(row.getCell(COL_HEADER).getStringCellValue());
			}
			return operdoc;
		} catch(Throwable ex) {
			logger.error("不能从行对象中提取物料操作记录,为第["+row.getRowNum()+"]行。",ex);
			return null;
		}
	}
	
	
	/**
	 * 验证Excel的Sheet是否是正确的物料操作文档读取器表格
	 * @param sheet Excel中的Sheet
	 * @return 验证成功true/验证失败false
	 */
	private static boolean validateSheet(Sheet sheet) {
		if(sheet==null) {
			logger.error("不能验证Sheet是否是正确的物料操作文档表格,Sheet对象为空。");
			return false;
		}
		Cell cell;
		for(Location loc:excelValidatorMap.keySet()) {
			cell=sheet.getRow(loc.row).getCell(loc.column);
			if(cell==null) {
				logger.error("物料操作文档Sheet验证失败，位置Row["+loc.row+"]Column["+loc.column+"]的单元格为空。");
				return false;
			}
			if(!cell.getStringCellValue().equals(excelValidatorMap.get(loc))) {
				logger.error("物料操作文档Sheet验证失败，位置Row["+loc.row+"]Column["+loc.column+"]的内容["+cell.getStringCellValue()+"]与验证字符串["+excelValidatorMap.get(loc)+"不符。");
				return false;
			}
		}
		return true;
	}
	
}
