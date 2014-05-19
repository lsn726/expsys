package com.logsys.report;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.logsys.demand.DemandContent;
import com.logsys.demand.DemandContent_Month;
import com.logsys.demand.DemandContent_Week;
import com.logsys.demand.DemandDataReaderDB;
import com.logsys.demand.DemandUtil;
import com.logsys.model.ModelContent;
import com.logsys.model.ModelDataReaderDB;
import com.logsys.model.ModelUtil;
import com.logsys.util.DateInterval;
import com.logsys.util.GeneralUtils;
import com.logsys.util.Location;
import com.logsys.util.Matrixable;

/**
 * 需求报表--按日的Sheet，按周的Sheet和按月的Sheet
 * @author ShaonanLi
 */
public class DemandReportForExcel {

	private static final Logger logger=Logger.getLogger(DemandReportForExcel.class);
	
	/**产成品顺序*/
	private Map<String,Integer> matorder_fin;
	
	/**产成品列表*/
	private List<ModelContent> matlist_fin;
	
	/**产成品集*/
	private Set<String> matset_fin;
	
	/**按天需求表*/
	private Matrixable dematrix_onday;
	
	/**按周需求表*/
	private Matrixable dematrix_onweek;
	
	/**按月需求表*/
	private Matrixable dematrix_onmonth;
	
	/**Sheet名称：按天需求*/
	private static final String SHEETNAME_DEM_ONDAY="Demand_Daily";
	
	/**Sheet名称：按周需求*/
	private static final String SHEETNAME_DEM_ONWEEK="Demand_Weekly";
	
	/**Sheet名称：按月需求*/
	private static final String SHEETNAME_DEM_ONMONTH="Demand_Monthly";
	
	public DemandReportForExcel() throws Exception {
		if(!init()) {
			logger.error("初始化需求报告失败。");
			throw new Exception("初始化需求报告失败。");
		}
	}
	
	private boolean init() {
		matlist_fin=ModelDataReaderDB.getDataFromDB(null);			//初始化成品列表，获取所有Model
		if(matlist_fin==null) return false;
		matset_fin=ModelUtil.getModelSet(matlist_fin);				//初始化成品集
		if(matset_fin==null) return false;
		matorder_fin=ModelDataReaderDB.sortModels(matset_fin);		//初始化成品顺序图
		if(matorder_fin==null) return false;
		Calendar begindate=GeneralUtils.getValidCalendar();
		Calendar now=GeneralUtils.getValidCalendar();
		begindate.clear();
		begindate.set(Calendar.YEAR,now.get(Calendar.YEAR));
		begindate.set(Calendar.WEEK_OF_YEAR, now.get(Calendar.WEEK_OF_YEAR));
		begindate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);				//按天计划和安周计划起始日期为本周周一
		dematrix_onday=genDemandMatrix_OnDay(begindate.getTime(),null);		//获取按天计划
		if(dematrix_onday==null) return false;
		dematrix_onweek=genDemandMatrix_OnWeek(begindate.getTime(),null);	//获取按周计划
		if(dematrix_onweek==null) return false;
		begindate.clear();
		begindate.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1);	//按月计划起始日期
		dematrix_onmonth=genDemandMatrix_OnMonth(begindate.getTime(),null);	//获取按月计划
		if(dematrix_onmonth==null) return false;
		return true;
	}
	
	/**
	 * 获取按天需求数据矩阵
	 * @return 按天需求矩阵对象
	 */
	private Matrixable genDemandMatrix_OnDay(Date begindate, Date enddate) {
		List<DemandContent> demlist_onday=DemandDataReaderDB.getDemandDataFromDB_OnDay(null, begindate, enddate);	//获得按天需求元数据
		if(demlist_onday==null) {
			logger.error("不能生成按天的需求矩阵，按天需求列表获取错误。");
			return null;
		}
		Matrixable demmat_onday=new Matrixable();		//声明按天需求矩阵
		if(demlist_onday.size()==0) return demmat_onday;
		for(String pn:matorder_fin.keySet())		//将成品号写入行表头
			demmat_onday.putRowHeaderCell(matorder_fin.get(pn), pn);
		DateFormat dateconv=new SimpleDateFormat("yyyy/MM/dd");	//日期格式
		DateInterval interval=DemandUtil.getMinMaxDateInDemandList(demlist_onday);	//获取时间最小值和最大值
		Calendar begin=GeneralUtils.getValidCalendar();
		Calendar end=GeneralUtils.getValidCalendar();
		if(begindate==null)							//设置起始时间
			begin.setTime(interval.begindate);
		else
			begin.setTime(begindate);
		if(enddate==null)							//设置结束时间
			end.setTime(interval.enddate);
		else
			end.setTime(enddate);
		for(int counter=1;!begin.after(end);begin.add(Calendar.DAY_OF_YEAR, 1),counter++)	//将日期写入列表头
			demmat_onday.putColHeaderCell(counter, dateconv.format(begin.getTime()));
		boolean result;
		for(DemandContent demcont:demlist_onday) { //写入需求数据
			result=demmat_onday.setData(demcont.getPn(), dateconv.format(demcont.getDate()), (Double)demcont.getQty());
			if(!result) {
				logger.error("在矩阵对象中写入数据失败。");
				return null;
			}
		}
		return demmat_onday;
	}
	
	/**
	 * 获取按周需求数据矩阵
	 * @return 按周需求矩阵对象
	 */
	private Matrixable genDemandMatrix_OnWeek(Date begindate, Date enddate) {
		List<DemandContent_Week> demlist_onweek=DemandDataReaderDB.getDemandDataFromDB_OnWeek(null, begindate, enddate);	//获得按周需求元数据
		if(demlist_onweek==null) {
			logger.error("不能生成按周的需求矩阵，按周需求列表获取错误。");
			return null;
		}
		Matrixable demmat_onweek=new Matrixable();		//声明按周需求矩阵
		if(demlist_onweek.size()==0) return demmat_onweek;
		for(String pn:matorder_fin.keySet())		//将成品号写入行表头
			demmat_onweek.putRowHeaderCell(matorder_fin.get(pn), pn);
		int colcounter=0;		//列索引
		String colheader;		//列表头
		for(DemandContent_Week weekdem:demlist_onweek)	{ //循环写入表头和需求数据
			colheader=weekdem.getYear()+"年"+weekdem.getWeek()+"周";	//列表头
			if(!demmat_onweek.isContentInColHeader(colheader))			//如果不存在于列表头中，则加入列表头
				demmat_onweek.putColHeaderCell(++colcounter, colheader);
			demmat_onweek.setData(weekdem.getPn(), colheader, weekdem.getQty()); //写入数据
		}
		return demmat_onweek;
	}
	
	/**
	 * 获取按月需求数据矩阵
	 * @return 按月需求矩阵对象
	 */
	private Matrixable genDemandMatrix_OnMonth(Date begindate, Date enddate) {
		List<DemandContent_Month> demlist_onmonth=DemandDataReaderDB.getDemandDataFromDB_OnMonth(null, begindate, enddate);	//获得按周需求元数据
		if(demlist_onmonth==null) {
			logger.error("不能生成按周的需求矩阵，按周需求列表获取错误。");
			return null;
		}
		Matrixable demmat_onmonth=new Matrixable();		//声明按天需求矩阵
		if(demlist_onmonth.size()==0) return demmat_onmonth;
		for(String pn:matorder_fin.keySet())		//将成品号写入行表头
			demmat_onmonth.putRowHeaderCell(matorder_fin.get(pn), pn);
		int colcounter=0;		//列索引
		String colheader;		//列表头
		for(DemandContent_Month monthdem:demlist_onmonth)	{ //循环写入表头和需求数据
			colheader=monthdem.getYear()+"年"+monthdem.getMonth()+"月";	//列表头
			if(!demmat_onmonth.isContentInColHeader(colheader))			//如果不存在于列表头中，则加入列表头
				demmat_onmonth.putColHeaderCell(++colcounter, colheader);
			demmat_onmonth.setData(monthdem.getPn(), colheader, monthdem.getQty());	//写入数据
		}
		return demmat_onmonth;
	}
	
	/**
	 * 将报告写入Excel文件
	 * @param filepath 要写入的文件路径
	 * @return 成功true/失败false
	 */
	public boolean writeReportToFile(String filepath) {
		if(filepath==null) {
			logger.error("不能产生需求矩阵并写入Excel，文件路径为空。");
			return false;
		}
		File file=new File(filepath);
		if(file.exists()) {
			logger.error("不能产生需求矩阵并写入Excel，文件["+filepath+"]已存在");
			return false;
		}
		Workbook wb=new XSSFWorkbook();								//创建工作簿
		Sheet ondaysheet=wb.createSheet(SHEETNAME_DEM_ONDAY);		//创建按天sheet
		dematrix_onday.writeToExcelSheet(ondaysheet, new Location(0,0));	//写入天列表
		Sheet onweeksheet=wb.createSheet(SHEETNAME_DEM_ONWEEK);		//创建按周sheet
		dematrix_onweek.writeToExcelSheet(onweeksheet, new Location(0,0));	//写入周列表
		Sheet onmonthsheet=wb.createSheet(SHEETNAME_DEM_ONMONTH);	//创建按月sheet
		dematrix_onmonth.writeToExcelSheet(onmonthsheet, new Location(0,0));//写入月列表
		try {
			FileOutputStream fileOut=new FileOutputStream(filepath);
			wb.write(fileOut);
			fileOut.close();
			logger.info("成功生成需求报告并写入Excel文件["+filepath+"]");
			return true;
		} catch(Throwable ex) {
			logger.error("不能产生需求报告并写入Excel，出现错误：",ex);
			return false;
		}
	}
	
}