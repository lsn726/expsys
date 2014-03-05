package com.logsys.demand;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/***
 * 需求数据工具类
 * @author lx8sn6
 */
public class DemandUtil {

	private static Logger logger=Logger.getLogger(DemandUtil.class);
	
	private static final Date DATE_MAX=new Date("2199/12/31");
	
	private static final Date DATE_MIN=new Date("1910/12/31");
	
	/**
	 * 在经过 函数demListToMapByPn处理后的单个demandmap(型号)中查找最小日期的函数.
	 * @param demandlist 需求数据列表
	 * @return 返回的找到的最小需求日期
	 */
	public static Date getMinDate(Map<Date,DemandContent> demandmap) {
		if(demandmap==null) {
			logger.error("需求数据列表为空。");
			return null;
		}
		if(demandmap.size()==0) {
			logger.warn("需求数据列表不包含任何数据。");
			return null;
		}
		Date mindate=(Date)DATE_MAX.clone();//设置一个最大日期
		for(Date date:demandmap.keySet())
			if(mindate.after(date))			//如果最小日期在此最新日期之后
				mindate=date;				//设置为最小日期
		return (Date)mindate.clone();
	}
	
	/**
	 * 在经过 函数demListToMapByPn处理后的单个demandmap(型号)中查找最大日期的函数.
	 * @param demandlist 需求数据列表
	 * @return 返回的找到的最晚需求日期
	 */
	public static Date getMaxDate(Map<Date,DemandContent> demandmap) {
		if(demandmap==null) {
			logger.error("需求数据列表为空。");
			return null;
		}
		if(demandmap.size()==0) {
			logger.warn("需求数据列表不包含任何数据。");
			return null;
		}
		Date maxdate=(Date)DATE_MIN.clone();//设置一个最小日期
		for(Date date:demandmap.keySet())
			if(maxdate.before(date))		//如果最小日期在此最新日期之后
				maxdate=date;				//设置为最小日期
		return (Date)maxdate.clone();
	}
	
	/**
	 * 检视DemandUtil.demListToMapByPn()函数处理的结果，计算多个型号的最小时间。
	 * @param demmap
	 * @return 返回的找到的最早需求日期
	 */
	public static Date getMinDateInMultiModel(Map<String,Map<Date,DemandContent>> demmap) {
		if(demmap==null) {
			logger.error("需求数据参数为空");
			return null;
		}
		Date mindate=(Date)DATE_MAX.clone();
		Date temp;
		for(String model:demmap.keySet()) {
			temp=getMinDate(demmap.get(model));
			if(mindate.after(temp))
				mindate=temp;
		}
		return mindate;
	}
	
	/**
	 * 检视DemandUtil.demListToMapByPn()函数处理的结果，计算多个型号的最小时间。
	 * @param demmap
	 * @return 返回的找到的最晚需求日期
	 */
	public static Date getMaxDateInMultiModel(Map<String,Map<Date,DemandContent>> demmap) {
		if(demmap==null) {
			logger.error("需求数据参数为空");
			return null;
		}
		Date maxdate=(Date)DATE_MIN.clone();
		Date temp;
		for(String model:demmap.keySet()) {
			temp=getMaxDate(demmap.get(model));
			if(maxdate.before(temp))
				maxdate=temp;
		}
		return maxdate;
	}
	
	/**
	 * 混合无顺序的List->Map<型号,Map<日期,DemandBean>>
	 * @param demandlist 需求列表
	 * @return 格式化后的Map对象
	 */
	public static Map<String,Map<Date,DemandContent>> demListToMapByPn(List<DemandContent> demandlist) {
		if(demandlist==null) {
			logger.error("需求数据参数为空。");
			return null;
		}
		String pn;
		Map<String,Map<Date,DemandContent>> demandmap=new HashMap<String,Map<Date,DemandContent>>();
		for(DemandContent demand:demandlist) {
			if(demandlist==null) continue;
			pn=demand.getPn();
			if(!demandmap.containsKey(pn))						//如果型号不存在，则新建Map并写入
				demandmap.put(pn, new HashMap<Date,DemandContent>());
			demandmap.get(pn).put((Date)demand.getDate().clone(), demand);	//写入需求数据
		}
		return demandmap;
	}
	
	/**
	 * 为demListToMapByPn()生成的需求表中的一单个型号填充需求为0的空需求点,如果在某天没有需求数据，则填充那天的新需求数据，需求量为0
	 * @param demandmap 由demListToMapByPn()函数生成的需求map中的单个型号需求
	 * @param begin 生成空需求点的起始时间
	 * @param end 生成空需求点的结束时间
	 * @return 填充的需求点数量
	 */
	public static int fillEmptyDemandNodes(Map<Date,DemandContent> demandmap, Date begin, Date end) {
		if(demandmap==null||begin==null||end==null) {
			logger.error("参数为空");
			return -1;
		}
		if(demandmap.size()==0) return 0;
		if(begin.after(end)) {
			logger.error("开始时间晚于结束时间。");
			return -1;
		}
		int counter=0;
		Date index=(Date)begin.clone();
		Calendar cal=Calendar.getInstance();
		DemandContent dcontent;
		String pn=demandmap.get(demandmap.keySet().toArray()[0]).getPn();
		while(true) {
			if(index.after(end)) break;
			if(!demandmap.containsKey(index)) {		//如果没有这个日期，则加入空节点
				dcontent=new DemandContent();
				dcontent.setDate((Date)index.clone());
				dcontent.setPn(pn);
				dcontent.setQty(0);
				dcontent.setDlvfix(0);
				demandmap.put(dcontent.getDate(), dcontent);
				counter++;
			}
			cal.setTime(index);
			cal.add(Calendar.DAY_OF_MONTH,1);
			index=cal.getTime();
		}
		return counter;
	}
	
	

}