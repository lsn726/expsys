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
		begindate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);		//起始日期为本周周一
		dematrix_onday=genDemandMatrix_OnDay(begindate.getTime(),null);	//获取按天计划
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
		Matrixable dem_onday=new Matrixable();		//声明按天需求矩阵
		if(demlist_onday.size()==0) return dem_onday;
		for(String pn:matorder_fin.keySet())		//将成品号写入行表头
			dem_onday.putRowHeaderCell(matorder_fin.get(pn), pn);
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
		for(int counter=1;!begin.after(end);begin.add(Calendar.DAY_OF_YEAR, 1),counter++)	//将日期写入列表头,从第三列开始
			dem_onday.putColHeaderCell(counter, dateconv.format(begin.getTime()));
		boolean result;
		for(DemandContent demcont:demlist_onday) { //写入需求数据
			result=dem_onday.setData(demcont.getPn(), dateconv.format(demcont.getDate()), (Double)demcont.getQty());
			if(!result) {
				logger.error("在矩阵对象中写入数据失败。");
				return null;
			}
		}
		return dem_onday;
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
		Sheet ondaysheet=wb.createSheet("Demand_Daily");			//创建sheet
		dematrix_onday.writeToExcelSheet(ondaysheet, new Location(0,0));
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