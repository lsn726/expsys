package com.logsys.demand;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Demand导入时Excel表格的封装对象
 * @author lx8sn6
 */
public class DemandExcelContainer {

	private static final Logger logger=Logger.getLogger(DemandExcelContainer.class);
	
	/**需求日期列*/
	private static final int COL_DATE=0;
	
	/**需求型号列*/
	private static final int COL_PN=1;
	
	/**需求数量列*/
	private static final int COL_QTY=2;
	
	/**需求Sheet的名称*/
	private static final String DEMAND_SHEET_NAME="UploadDemand";
	
	/**需求Excel表格的对象*/
	private Workbook demwb;
	
	/**从Excel中提取的需求数据*/
	private List<DemandContent> extractlist;
	
	/**禁止从外部创建对象*/
	private DemandExcelContainer() {}
	
	/**
	 * 需求Excel表格容器类的工厂函数
	 * @param filepath 文件路径
	 * @return 容器对象/null
	 */
	public static DemandExcelContainer getDemandExcelContainer(String filepath) {
		if(filepath==null) {
			logger.error("不能由文件["+filepath+"]创建需求Excel表格对象:文件路径为空");
			return null;
		}
		File file=new File(filepath);
		if(!file.exists()) {
			logger.error("不能由文件["+filepath+"]创建需求Excel表格对象:文件不存在");
			return null;
		}
		if(file.isDirectory()) {
			logger.error("不能由文件["+filepath+"]创建需求Excel表格对象:文件路径是文件夹 ");
			return null;
		}
		DemandExcelContainer container=new DemandExcelContainer();
		try {
			container.demwb=WorkbookFactory.create(file);
		} catch(Throwable ex) {
			logger.error("不能由文件["+filepath+"]创建需求Excel表格对象,生成Workbook对象时出现错误。:",ex);
			return null;
		}
		if(container.demwb.getSheet(DEMAND_SHEET_NAME)==null) {
			logger.error("不能由文件["+filepath+"]创建需求Excel表格对象,工作簿中不含有名为["+DEMAND_SHEET_NAME+"]的Sheet。");
			return null;
		}
		return container;
	}
	
	/**
	 * 处理工作簿函数
	 * @return 处理成功/处理失败
	 */
	public boolean processWorkBook() {
		Sheet datasheet=demwb.getSheet(DEMAND_SHEET_NAME);
		if(!verifyDataSheet(datasheet)) {		//第一步：验证表格准确性
			logger.error("不能处理Workbook，验证表格未通过。");
			return false;
		}
		if(!extractData()) {					//第二步：提取表格数据
			logger.error("不能处理Workbook，未能成功提取Excel数据。");
			return false;
		}
		//TODO: 第三步:分析表格数据
		return true;
	}
	
	/**
	 * 核查数据Sheet的准确性
	 * @param sheet 包含数据的表格对象
	 * @return 验证成功true/验证失败false
	 */
	public boolean verifyDataSheet(Sheet sheet) {
		if(sheet==null) {
			logger.error("无法核查数据sheet的准确性。sheet参数为空。");
			return false;
		}
		return true;
	}
	
	/**
	 * 提取文件中的数据
	 * @return true提取成功/false提取失败
	 */
	public boolean extractData() {
		Sheet datasheet=demwb.getSheet(DEMAND_SHEET_NAME);
		List<DemandContent> demlist=new ArrayList<DemandContent>(); 
		DemandContent node;
		for(Row row:datasheet) {
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
				return false;
			}
			demlist.add(node);
		}
		extractlist=demlist;
		return true;
	}
	
	/**
	 * 分析表格中的数据，包括：将每个型号的最大时间和最小时间确定
	 * @return 分析成功true/分析失败false
	 */
	public boolean anaylzeData() {
		return false;
	}
	
}
