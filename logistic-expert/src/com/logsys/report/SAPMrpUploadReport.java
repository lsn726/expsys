package com.logsys.report;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.logsys.demand.DemandContent_Week;
import com.logsys.demand.DemandDataReaderDB;
import com.logsys.model.ModelService;
import com.logsys.prodplan.ProdplanContent_PrdLineCombined;
import com.logsys.prodplan.ProdplanContent_Week;
import com.logsys.prodplan.ProdplanDataReaderDB;
import com.logsys.util.DateInterval;
import com.logsys.util.DateTimeUtils;
import com.logsys.util.ExcelUtils;
import com.logsys.util.Location;
import com.logsys.util.Matrixable;

/**
 * SAP的MRP上传报告
 * @author lx8sn6
 */
public class SAPMrpUploadReport {
	
	private static final Logger logger=Logger.getLogger(SAPMrpUploadReport.class);
	
	/**上传计划时前置的按天计划的周数*/
	private static final int ONDAYPLAN_WEEKNUM=2;
	
	/**日期开始列的向后修正列数，其中包括行表头,工厂,库位和MRP日期列*/
	private static final int PREFIX_DATECOL=4;
	
	/**SAP MRP的上传计划*/
	private static final String SHEET_NAME="SAP MRP Upload";
	
	/**所使用的工厂名*/
	private static final String FACTORY="FA01";

	/**合并生产线的生产计划列表*/
	private List<ProdplanContent_PrdLineCombined> pplist;
	
	/**按周的需求列表*/
	private List<DemandContent_Week> wkdemlist;
	
	/**按周计划列表*/
	private List<ProdplanContent_Week> wkpplist; 
	
	/**排序后的Model列表*/
	private Map<String,Integer> sortedmodelmap;
	
	/**包含数据的矩阵对象*/
	private Matrixable datamatrix;
	
	/**文件路径*/
	private String filepath; 
	
	/**需求+计划总共包含的week数*/
	private int totalweek;
	
	/**
	 * 构造函数
	 * @param filepath 文件路径
	 * @param totalweek 需求+计划总共包含的week数
	 */
	private SAPMrpUploadReport(String filepath, int weeknum) {
		this.filepath=filepath;
		if(weeknum<ONDAYPLAN_WEEKNUM) {
			logger.warn("警告，总周数小于按天计划的周数。因此将总周数设定为按天计划的周数。");
			totalweek=ONDAYPLAN_WEEKNUM;
		}
		else this.totalweek=weeknum;
	}
	
	/**
	 * 创建报告对象
	 * @filepath workbook文件路径
	 * @weeknum 总工需要生成的周数，这个数字需要大于ONDAYPLAN_WEEKNUM的周数，如果小于这个数字，则自动设置为ONDAYPLAN_WEEKNUM。
	 * @return 所创建的报告对象
	 */
	public static SAPMrpUploadReport createReportObject(String filepath, int weeknum) {
		File file=new File(filepath);
		if(file.exists()) {
			logger.error("不能生成SAPMrp上传报告对象,文件已存在.");
			return null;
		}
		SAPMrpUploadReport report=new SAPMrpUploadReport(filepath,weeknum);
		if(!report.init()) {
			logger.error("不能生成SAPMrp上传报告对象,初始化出现错误.");
			return null;
		}
		return report;
	}
	
	/**
	 * 初始化报告对象
	 * @return 成功true/失败false
	 */
	private boolean init() {
		Calendar cal=DateTimeUtils.getValidCalendar();
		long begin=0;
		long end=0;
		//按天生产计划，起始天数为本周第一天,结束为ONDAYPLAN_WEEKNUM周的周日
		begin=DateTimeUtils.getFirstDayOfWeek(-1);
		cal.setTimeInMillis(begin);
		cal.add(Calendar.WEEK_OF_YEAR, ONDAYPLAN_WEEKNUM-1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		end=cal.getTimeInMillis();
		pplist=ProdplanDataReaderDB.getProdLineCombinedPPList(new DateInterval(begin,end));
		if(pplist==null) {
			logger.error("初始化失败,不能产能合并生产线的生产计划列表。");
			return false;
		}
		//按周需求，起始天为本周开始第ONDAYPLAN_WEEKNUM周后的第一天，结束周为totalweek周的周日
		cal.setTimeInMillis(DateTimeUtils.getFirstDayOfWeek(-1));
		cal.add(Calendar.WEEK_OF_YEAR, ONDAYPLAN_WEEKNUM);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		begin=cal.getTimeInMillis();
		cal.setTimeInMillis(DateTimeUtils.getFirstDayOfWeek(-1));
		cal.add(Calendar.WEEK_OF_YEAR, totalweek-1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		end=cal.getTimeInMillis();
		wkdemlist=DemandDataReaderDB.getDemandDataFromDB_OnWeek(null, new Date(begin), new Date(end), true);
		if(wkdemlist==null) {
			logger.error("初始化失败，不能产生按周需求列表。");
			return false;
		}
		//按周计划，起始天为本周开始第ONDAYPLAN_WEEKNUM周后的第一天，结束周为totalweek周的周日
		wkpplist=ProdplanDataReaderDB.getOnWeekProdplanDataFromDB(null, new DateInterval(begin, end));
		if(wkpplist==null) {
			logger.error("初始化失败，不能产生按周计划列表");
		}
		//获取排序后的Model图
		sortedmodelmap=ModelService.getSortedModelMap();
		if(sortedmodelmap==null) {
			logger.error("初始化失败，不能产生排序后的Model图.");
			return false;
		}
		//生成新的矩阵对象
		datamatrix=new Matrixable();
		if(!generateDataMatrix()) {
			logger.error("初始化失败，不能产生矩阵数据");
			return false;
		}
		return true;
	}
	
	/**
	 * 生成上传矩阵数据对象
	 * @return 成功true/失败false
	 */
	private boolean generateDataMatrix() {
		//第一步：写入行表头，即成品集
		for(String pn:sortedmodelmap.keySet())
			if(!datamatrix.putRowHeaderCell(sortedmodelmap.get(pn), pn)) {
				logger.error("生成数据矩阵错误。写入行表头成品号时出现错误。行表头["+pn+"]行数["+sortedmodelmap.get(pn)+"].");
				return false;
			}
		//第二步：写入按天计划部分列表头
		Calendar cal=DateTimeUtils.getValidCalendar(DateTimeUtils.getFirstDayOfWeek(-1));
		int index=PREFIX_DATECOL;
		while(index<=7*ONDAYPLAN_WEEKNUM+PREFIX_DATECOL-1) {
			if(!datamatrix.putColHeaderCell(index,DateTimeUtils.getFormattedTimeStr_YearWeekWkday(cal.getTimeInMillis()))) {
				logger.error("生成数据矩阵错误。写入按天生产计划的列表头时出现错误。列表头日期["+cal.getTime()+"]列数["+index+"].");
				return false;
			}
			cal.add(Calendar.DAY_OF_YEAR, 1);
			index++;
		}
		//第三步：写入按天计划
		for(ProdplanContent_PrdLineCombined ppcont:pplist)
			if(!datamatrix.setData(ppcont.getPn(), DateTimeUtils.getFormattedTimeStr_YearWeekWkday(ppcont.getDate().getTime()), ppcont.getQty())) {
				logger.error("生成数据矩阵错误。写入按天生产计划出现错误。数据节点["+ppcont+"].");
				return false;
			}
		//第四步：写入按周需求部分列表头
		index=7*ONDAYPLAN_WEEKNUM+PREFIX_DATECOL;
		cal=DateTimeUtils.getValidCalendar(-1);
		cal.add(Calendar.WEEK_OF_YEAR, ONDAYPLAN_WEEKNUM);
		while(index<=totalweek+7*ONDAYPLAN_WEEKNUM+PREFIX_DATECOL-ONDAYPLAN_WEEKNUM-1)
			if(!datamatrix.putColHeaderCell(index, DateTimeUtils.getFormattedTimeStr_YearWeek(cal))) {
				logger.error("生成数据矩阵错误，写入按周需求列表头出现错误。列位置["+index+"]周数["+DateTimeUtils.getFormattedTimeStr_YearWeek(cal)+"].");
				return false;
			} else {
				cal.add(Calendar.WEEK_OF_YEAR, 1);
				index++;
			}
		//第五步：写入按周需求
		for(DemandContent_Week wkdem:wkdemlist)
			if(!datamatrix.setData(wkdem.getPn(), DateTimeUtils.getFormattedTimeStr_YearWeek(wkdem.getYear(),wkdem.getWeek()), wkdem.getQty())) {
				logger.error("生成数据矩阵错误，写入按周需求出现错误。需求节点["+wkdem+"].");
				return false;
			}
		//第六步：写入按周计划
		for(ProdplanContent_Week wkpp:wkpplist)
			if(!datamatrix.setData(wkpp.getPn(), DateTimeUtils.getFormattedTimeStr_YearWeek(wkpp.getYear(),wkpp.getWeek()), wkpp.getQty())) {
				logger.error("生成数据矩阵错误，写入按周计划出现错误，计划节点["+wkpp+"].");
				return false;
			}
		return true;
	}

	/**
	 * 将数据矩阵写入Excel表格，并对写入数据进行后期加工
	 * @return true成功/false失败
	 */
	public boolean writeToExcel() {
		//第一步：将Matrix写入Excel
		Workbook wb=new XSSFWorkbook();
		Sheet datasheet=wb.createSheet(SHEET_NAME);
		datamatrix.writeToExcelSheet(datasheet, new Location(0,0));
		//第二步：删除没有需求的空行
		for(int rowcounter=1;datasheet.getRow(rowcounter)!=null;rowcounter++)
			if(ExcelUtils.isExcelRowEmpty(datasheet.getRow(0), PREFIX_DATECOL, datasheet.getRow(rowcounter), PREFIX_DATECOL)) {
				if(rowcounter==datasheet.getLastRowNum()) {
					datasheet.removeRow(datasheet.getRow(rowcounter));
					break;
				}
				datasheet.shiftRows(rowcounter+1,datasheet.getLastRowNum(),-1);	//将下一行到最后一行向上移动一行，以删除本行数据
				rowcounter--;
			}
		//第三步：写入工厂/库位/起始日期信息
		Calendar cal=DateTimeUtils.getValidCalendar(DateTimeUtils.getFirstDayOfWeek(-1));
		SimpleDateFormat dformat=new SimpleDateFormat("MM/dd/yyyy");
		Row row;
		Cell cell;
		for(int rowcounter=1;datasheet.getRow(rowcounter)!=null;rowcounter++) {
			row=datasheet.getRow(rowcounter);
			cell=row.createCell(1);			//创建工厂
			cell.setCellValue(FACTORY);
			cell=row.createCell(2);			//创建库位
			cell.setCellValue(1);
			cell=row.createCell(3);			//创建日期
			cell.setCellValue(dformat.format(cal.getTime()));
		}
		//第四步：合并按天生产计划表格的表头
		cal=DateTimeUtils.getValidCalendar(DateTimeUtils.getFirstDayOfWeek(-1));
		for(int index=0;index<ONDAYPLAN_WEEKNUM;index++) {
			datasheet.getRow(0).getCell(PREFIX_DATECOL+index*7).setCellValue(DateTimeUtils.getFormattedTimeStr_YearWeek(cal));
			datasheet.addMergedRegion(new CellRangeAddress(0,0,PREFIX_DATECOL+index*7,PREFIX_DATECOL+(index+1)*7-1));
			cal.add(Calendar.WEEK_OF_YEAR, 1);
		}
		//第五步：写入硬盘
		try {
			FileOutputStream fileOut=new FileOutputStream(filepath);
			wb.write(fileOut);
			fileOut.close();
			logger.info("成功将SAP的MRP上传数据写入文件["+filepath+"].");
			return true;
		} catch(Throwable ex) {
			logger.error("不能将SAP的MRP上传数据写如文件，写入时出现错误：",ex);
			return false;
		}
	}
	
}
