package com.logsys.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.logsys.demand.DemandContent_Week;
import com.logsys.demand.DemandDataReaderDB;
import com.logsys.material.MaterialContent;
import com.logsys.material.MaterialDataReaderDB;
import com.logsys.material.MaterialUtil;
import com.logsys.model.ModelContent;
import com.logsys.model.ModelService;
import com.logsys.prodplan.ProdplanContent_Week;
import com.logsys.prodplan.ProdplanDataReaderDB;
import com.logsys.production.ProductionContent_Week;
import com.logsys.production.ProductionDataReaderDB;
import com.logsys.setting.pd.bwi.BWIPLInfo.ProdLine;
import com.logsys.util.DateInterval;
import com.logsys.util.DateTimeUtils;
import com.logsys.util.Matrixable;

/**
 * 创建Mrp报告
 * @author lx8sn6
 */
public class MrpReportForExcel {

	private static final Logger logger=Logger.getLogger(MrpReportForExcel.class);
	
	/**原材料顺序*/
	Map<Integer,String> matorder_raw;
	
	/**原材料列表*/
	List<MaterialContent> matlist_raw;
	
	/**原材料pn集*/
	Set<String> matset_raw;
	
	/**产成品顺序*/
	Map<String,Integer> matorder_fin;
	
	/**产成品列表*/
	List<ModelContent> matlist_fin;
	
	/**BOM图*/
	Map<String,Map<String,Double>> bommap;
	
	/**初始化所有需要的功能呢*/
	private boolean init() {
		matlist_raw=MaterialDataReaderDB.getDataFromDB(null, "buy", true, true);	//初始化原材料列表
		if(matlist_raw==null) return false;
		matset_raw=MaterialUtil.getPnSet(matlist_raw);				//初始化原材料集
		if(matset_raw==null) return false;
		matorder_raw=MaterialUtil.getOrderedMatMap(matset_raw);		//获取原材料顺序图
		if(matorder_raw==null) return false;
		matlist_fin=ModelService.getModelList();					//初始化成品列表，获取所有Model
		if(matlist_fin==null) return false;
		matorder_fin=ModelService.getSortedModelMap();				//初始化成品顺序图
		if(matorder_fin==null) return false;
		return true;
	}
	
	/**
	 * 产生需求矩阵，需求矩阵格式如下：
	 * 列表头：需求的型号
	 * 行表头：需求的日期
	 * 矩阵值：需求数量
	 * 原则：有计划，放计划；没计划，放需求；有产出，拿计划/需求减去产出
	 * @param weeknum 出现在矩阵中需求的周数，必须大于0
	 * @param filldem 是否将有生产计划的周数中，没有计划的型号以需求代替。True代替/False不代替
	 * @return 需求矩阵
	 */
	public Matrixable getDemandMatrix(int weeknum, boolean filldem) {
		init();
		if(weeknum<=0) {
			logger.error("不能产生需求矩阵，需求周数必须大于0。");
			return null;
		}
		Set<ProdLine> plset=new HashSet<ProdLine>();
		plset.add(ProdLine.DAMPER_FA_FA1);
		plset.add(ProdLine.DAMPER_FA_FA2);
		List<ProductionContent_Week> pdwklist;					//按周产出列表
		List<DemandContent_Week> demwklist;						//按周需求列表
		List<ProdplanContent_Week> ppwklist;					//按周生产计划列表
		Calendar begin=DateTimeUtils.getValidCalendar();
		Calendar end=DateTimeUtils.getValidCalendar();
		//按周产出列表初始化
		if(begin.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY)	//是周一则不用查询
			pdwklist=new ArrayList<ProductionContent_Week>();
		else {													//如果不是周一
			begin.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);	//需要先将开始时间设置为周一
			Set<ProdLine> outputplset=new HashSet<ProdLine>();
			outputplset.add(ProdLine.DAMPER_FA_FINAL_CHECK);
			pdwklist=ProductionDataReaderDB.getOnWeekProductionDataFromDB(outputplset, new DateInterval(begin.getTime(),null));
			if(pdwklist==null) return null;
		}
		//按周需求列表初始化
		begin=DateTimeUtils.getValidCalendar();
		begin.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);		//设置需求开始时间为本周周一
		end.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		end.add(Calendar.WEEK_OF_YEAR, weeknum);				//末尾日期为weeknum周后的周末
		demwklist=DemandDataReaderDB.getDemandDataFromDB_OnWeek(null, begin.getTime(), end.getTime(), true);
		if(demwklist==null) return null;
		//按周生产计划列表初始化
		ppwklist=ProdplanDataReaderDB.getOnWeekProdplanDataFromDB(plset, new DateInterval(begin.getTime(),null));
		if(ppwklist==null) return null;
		Matrixable demandMatrix=new Matrixable();//需求矩阵对象
		//初始化表头
		for(String fertpn:matorder_fin.keySet())				//将成品的顺序写入行表头
			demandMatrix.putRowHeaderCell(matorder_fin.get(fertpn), fertpn);
		begin=DateTimeUtils.getValidCalendar();
		int year=begin.get(Calendar.YEAR);						//年
		int week=begin.get(Calendar.WEEK_OF_YEAR);				//周
		for(int index=0;index<weeknum;index++)	{				//将年和周数写入列表头
			if((week==1 && begin.get(Calendar.MONTH)==Calendar.DECEMBER)/** || (week-begin.get(Calendar.WEEK_OF_YEAR)>=51 && year-begin.get(Calendar.YEAR)==0)*/)	//特别处理A年52周后一周，本应为A+1年第一周，却由于当周开始日期在A年，显示为A年第一周的情况
				year++;
			else
				year=begin.get(Calendar.YEAR);
			week=begin.get(Calendar.WEEK_OF_YEAR);
			//if(week==53) year--;		//临时处理第53周
			demandMatrix.putColHeaderCell(index+1, String.format("%dwk%02d", year ,week));
			begin.add(Calendar.WEEK_OF_YEAR, 1);
		}
		//写入矩阵数据：首先写入按周需求
		Integer rowindex;			//行索引
		Integer colindex;			//列索引
		Double mrpfactor;			//mrp乘数，将出来的数据乘以这个因数
		Integer roundvalue;			//向上取整数
		for(DemandContent_Week wkdem:demwklist) {
			rowindex=demandMatrix.getRowPosByRowHeader(wkdem.getPn());	//根据成品号获取行索引
			if(rowindex==null) {
				logger.error("不能产生需求矩阵，周需求对象中的成品号码["+wkdem.getPn()+"]不能在行表头中定位。对象明细："+wkdem.toString());
				return null;
			}
			colindex=demandMatrix.getColPosByColHeader(String.format("%dwk%02d",wkdem.getYear(),wkdem.getWeek()));	//根据年和周数确定列索引
			if(colindex==null) {
				logger.warn("周需求对象中的年/周数组合["+String.format("%dwk%02d",wkdem.getYear(),wkdem.getWeek())+"]不能在列表头中定位。对象明细："+wkdem.toString()+".可能超越了规定的需求矩阵周数。将跳过这个对象。");
				continue;
			}
			mrpfactor=ModelService.getModelContentByPn(wkdem.getPn()).getMrpfactor();	//获取mrp乘数
			if(mrpfactor==null) {
				logger.error("不能产生需求矩阵，写入周需求时，成品号码["+wkdem.getPn()+"]没有报废率。请确认号码是否正确，或者在MRPSettings中添加报废率。");
				return null;
			}
			roundvalue=ModelService.getModelContentByPn(wkdem.getPn()).getMrpround();	//获取向上取整值
			if(roundvalue==null) {
				logger.error("不能产生需求矩阵，写入周需求时，成品号码["+wkdem.getPn()+"]没有向上取整数。请确认号码是否正确，或者在MRPSettings中添加向上取整数。");
				return null;
			}
			//System.out.println(wkdem.getPn()+"["+rowindex+"]/["+colindex+"]/["+wkdem.getQty()+"]");
			demandMatrix.setData(rowindex, colindex, Math.ceil(wkdem.getQty()*mrpfactor/100.0/roundvalue)*roundvalue);	//根据位置写入数据
		}
		//写入矩阵数据之前进行清空所在列需求的操作
		if(!filldem) {				//如果不需要用使用需求替代空余的生产计划，则需要先行删除计划所在周的所有需求数据。
			Set<Integer> ppcolset=new HashSet<Integer>();		//需要删除的所在列的集
			for(ProdplanContent_Week wkpp:ppwklist)
				ppcolset.add(demandMatrix.getColPosByColHeader(String.format("%dwk%02d",wkpp.getYear(),wkpp.getWeek())));
			for(int col:ppcolset)
				demandMatrix.cleanCol(col);
		}
		//写入矩阵数据：Prodplan代替Demand
		for(ProdplanContent_Week wkpp:ppwklist) {
			rowindex=demandMatrix.getRowPosByRowHeader(wkpp.getPn());	//根据成品号获取行索引
			if(rowindex==null) {
				logger.error("不能产生需求矩阵，周计划对象中的成品号码["+wkpp.getPn()+"]不能在表头中定位。对象明细："+wkpp.toString());
				return null;
			}
			colindex=demandMatrix.getColPosByColHeader(String.format("%dwk%02d",wkpp.getYear(),wkpp.getWeek()));	//根据年和周数确定列索引
			if(colindex==null) {
				logger.warn("周计划对象中的年/周数组合["+String.format("%dwk%02d",wkpp.getYear(),wkpp.getWeek())+"]不能在列表头中定位。对象明细："+wkpp.toString()+".可能超越了规定的需求矩阵周数。将跳过这个对象。");
				continue;
			}
			mrpfactor=ModelService.getModelContentByPn(wkpp.getPn()).getMrpfactor();	//获取mrp乘数
			if(mrpfactor==null) {
				logger.error("不能产生需求矩阵，写入周生产计划时，成品号码["+wkpp.getPn()+"]没有报废率。请确认号码是否正确，或者在MRPSettings中添加报废率。");
				return null;
			}
			roundvalue=ModelService.getModelContentByPn(wkpp.getPn()).getMrpround();	//获取向上取整值
			if(roundvalue==null) {
				logger.error("不能产生需求矩阵，写入周生产计划时，成品号码["+wkpp.getPn()+"]没有向上取整数。请确认号码是否正确，或者在MRPSettings中添加向上取整数。");
				return null;
			}
			demandMatrix.setData(rowindex, colindex, Math.ceil(wkpp.getQty()*mrpfactor/100.0/roundvalue)*roundvalue);
		}
		//写入矩阵数据：扣除实际生产数量
		for(ProductionContent_Week wkprod:pdwklist) {
			rowindex=demandMatrix.getRowPosByRowHeader(wkprod.getOutput());	//根据成品号获取行索引
			if(rowindex==null) {
				logger.error("不能产生需求矩阵，周生产对象中的成品号码["+wkprod.getOutput()+"]不能在表头中定位。对象明细："+wkprod.toString());
				return null;
			}
			colindex=demandMatrix.getColPosByColHeader(String.format("%dwk%02d",wkprod.getYear(),wkprod.getWeek()));	//根据年和周数确定列索引
			if(colindex==null) {
				logger.warn("周生产对象中的年/周数组合["+String.format("%dwk%02d",wkprod.getYear(),wkprod.getWeek())+"]不能在列表头中定位。对象明细："+wkprod.toString()+".可能超越了规定的需求矩阵周数。将跳过这个对象。");
				continue;
			}
			mrpfactor=ModelService.getModelContentByPn(wkprod.getOutput()).getMrpfactor();	//获取mrp乘数
			if(mrpfactor==null) {
				logger.error("不能产生需求矩阵，写入周生产数据时，成品号码["+wkprod.getOutput()+"]没有报废率。请确认号码是否正确，或者在MRPSettings中添加报废率。");
				return null;
			}
			roundvalue=ModelService.getModelContentByPn(wkprod.getOutput()).getMrpround();	//获取向上取整值
			if(roundvalue==null) {
				logger.error("不能产生需求矩阵，写入周生产数据时，成品号码["+wkprod.getOutput()+"]没有向上取整数。请确认号码是否正确，或者在MRPSettings中添加向上取整数。");
				return null;
			}
			Double ori=(Double)demandMatrix.getData(rowindex, colindex);	//先获取原先的数据
			if(ori==null) ori=0.0;									//如果没有原数据则初始化为0
			demandMatrix.setData(rowindex, colindex, Math.ceil((ori-wkprod.getQty())*mrpfactor/100.0/roundvalue)*roundvalue);	//将新值设置为旧值
		}
		return demandMatrix;
	}
	
}
