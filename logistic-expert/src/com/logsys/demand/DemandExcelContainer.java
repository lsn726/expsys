package com.logsys.demand;

import java.io.File;
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

import com.logsys.model.ModelContent;
import com.logsys.model.ModelDataReaderDB;
import com.logsys.model.ModelUtil;
import com.logsys.util.DateInterval;

/**
 * Demand导入时Excel表格的封装对象
 * 运行工厂函数getDemandExcelContainer()获取对象
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
	private List<DemandContent> demandlist;
	
	/**
	 * 时间区间映射图：PartNumber->时间区间对象，mindate为需求pn最小值，maxdate为需求pn最大值
	 * 主要用于更新需求数据库的前序步骤，即删除旧有需求数据时，确定删除需求的日期范围。
	 **/
	private Map<String,DateInterval> demandInterval;
	
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
		DemandExcelContainer container=new DemandExcelContainer();
		try {
			container.demwb=WorkbookFactory.create(file);
		} catch(Throwable ex) {
			logger.error("不能由文件["+filepath+"]创建需求Excel表格对象,生成Workbook对象时出现错误。:",ex);
			return null;
		}
		if(!container.processWorkBook()) {
			logger.error("不能处理Excel工作簿["+filepath+"]文件。");
			return null;
		}
		return container;
	}
	
	/**
	 * 处理工作簿函数
	 * @return 处理成功/处理失败
	 */
	private boolean processWorkBook() {
		if(!verifyDataSheet()) {		//第一步：验证表格准确性
			logger.error("不能处理需求工作簿，验证表格未通过。");
			return false;
		}
		if(!extractData()) {			//第二步：提取表格数据
			logger.error("不能处理需求工作簿，未能成功提取Excel数据。");
			return false;
		}
		if(!analyzeData()) {			//第三步：分析提取的数据
			logger.error("不能处理需求工作簿，未能成功分析提取出的需求数据。");
			return false;
		}
		if(!reviseData()) {				//第四步：后期数据加工
			logger.error("不能处理需求工作簿，未能成功进行后期数据加工。");
		}
		return true;
	}
	
	/**
	 * 核查数据Sheet的准确性
	 * @return 验证成功true/验证失败false
	 */
	private boolean verifyDataSheet() {
		Sheet sheet=demwb.getSheet(DEMAND_SHEET_NAME);
		if(sheet==null) {
			logger.error("无法核查数据sheet的准确性。没有名为["+DEMAND_SHEET_NAME+"]的sheet，请将包含需求数据的sheet重命名。");
			return false;
		}
		return true;
	}
	
	/**
	 * 提取文件中的数据
	 * @return true提取成功/false提取失败
	 */
	private boolean extractData() {
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
				logger.error("不能从工作簿中提取需求数据，提取时出现错误。",ex);
				return false;
			}
			demlist.add(node);
		}
		demandlist=demlist;
		return true;
	}
	
	/**
	 * 分析表格中的数据，并根据原数据生成必要数据，本步骤不改变原有数据或者其衍生数据
	 * 1、将每个型号的最大时间和最小时间确定
	 * @return 分析成功true/分析失败false
	 */
	private boolean analyzeData() {
		if(demandlist==null) {
			logger.error("不能分析需求列表，需求列表尚未被初始化。");
			return false;
		}
		Map<String,DateInterval> intervalmap=new HashMap<String,DateInterval>();
		String pn;
		Date date;
		DateInterval itval;
		for(DemandContent demcont:demandlist) {		//遍历需求列表，求出每个pn的需求最小时间和最大时间
			pn=demcont.getPn();
			date=demcont.getDate();
			itval=intervalmap.get(pn);
			if(itval==null) {						//如果没有区间对象，则创建对象，将初始值设置为当前date
				itval=new DateInterval();
				itval.begindate=date;
				itval.enddate=date;
				intervalmap.put(pn, itval);			//加入新节点后继续
				continue;
			}
			if(date.before(itval.begindate))		//如果时间早于区间最小值，则更新最小值
				itval.begindate=date;
			if(date.after(itval.enddate))			//如果时间晚于区间最大值，则更新最大值
				itval.enddate=date;
		}
		demandInterval=intervalmap;					//设置区间对象
		return true;
	}
	
	/**
	 * 对于数据的后期修订。本步骤会根据业务需要而更改原有数据或者其衍生数据。
	 * @return 成功true/失败false
	 */
	private boolean reviseData() {
		return true;
	}
	
}
