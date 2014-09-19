package com.logsys.report;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.demand.DemandContent_Week;
import com.logsys.demand.DemandDataReaderDB;
import com.logsys.model.ModelService;
import com.logsys.prodplan.ProdplanContent_PrdLineCombined;
import com.logsys.prodplan.ProdplanDataReaderDB;
import com.logsys.util.DateInterval;
import com.logsys.util.DateTimeUtils;
import com.logsys.util.Matrixable;

/**
 * SAP的MRP上传报告
 * @author lx8sn6
 */
public class SAPMrpUploadReport {
	
	private static final Logger logger=Logger.getLogger(SAPMrpUploadReport.class);
	
	/**上传计划时前置的按天计划的周数*/
	private static final int ONDAYPLAN_WEEKNUM=2;

	/**合并生产线的生产计划列表*/
	private List<ProdplanContent_PrdLineCombined> pplist;
	
	/**按周的需求列表*/
	private List<DemandContent_Week> wkdemlist;
	
	/**排序后的Model列表*/
	private Map<String,Integer> sortedmodelmap;
	
	/**包含数据的矩阵对象*/
	private Matrixable datamatrix;
	
	/**Workbook对象*/
	private Workbook wb;
	
	/**需求+计划总共包含的week数*/
	private int totalweek;
	
	/**
	 * 构造函数
	 * @param wkbk workbook对象
	 * @param totalweek 需求+计划总共包含的week数
	 */
	private SAPMrpUploadReport(Workbook wkbk, int weeknum) {
		wb=wkbk;
		if(weeknum<ONDAYPLAN_WEEKNUM) totalweek=ONDAYPLAN_WEEKNUM;
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
		Workbook wkbk;
		try {
			wkbk=WorkbookFactory.create(file);
		} catch (Exception ex) {
			logger.error("不能生成SAPMrp上传报告对象,创建Workbook对象时出现错误。",ex);
			return null;
		}
		SAPMrpUploadReport report=new SAPMrpUploadReport(wkbk,weeknum);
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
		Calendar begin=DateTimeUtils.getValidCalendar();
		Calendar end=DateTimeUtils.getValidCalendar();
		begin.clear();
		begin.setWeekDate(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR), Calendar.MONDAY);
		end.clear();
		end.setWeekDate(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR)+ONDAYPLAN_WEEKNUM-1, Calendar.SUNDAY);
		pplist=ProdplanDataReaderDB.getProdLineCombinedPPList(new DateInterval(begin.getTime(),end.getTime()));
		if(pplist==null) {
			logger.error("初始化失败,不能产能合并生产线的生产计划列表。");
			return false;
		}
		end.clear();
		end.setWeekDate(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR)+totalweek-1, Calendar.SUNDAY);
		wkdemlist=DemandDataReaderDB.getDemandDataFromDB_OnWeek(null, begin.getTime(), end.getTime());
		if(wkdemlist==null) {
			logger.error("初始化失败，不能产生按周需求列表。");
			return false;
		}
		sortedmodelmap=ModelService.getSortedModelMap();
		if(sortedmodelmap==null) {
			logger.error("初始化失败，不能产生排序后的Model图.");
			return false;
		}
		datamatrix=new Matrixable();
		return true;
	}
	
	/**
	 * 生成矩阵数据对象
	 * @return 成功true/失败false
	 */
	public boolean generateDataMatrix() {
		return false;
	}

}
