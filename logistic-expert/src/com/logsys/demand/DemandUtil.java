package com.logsys.demand;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/***
 * 需求数据工具类
 * @author lx8sn6
 */
public class DemandUtil {

	private static Logger logger=Logger.getLogger(DemandUtil.class);
	
	/**
	 * 在demandlist中查找指定part bumber的最早需求日期
	 * @param demandlist 需求数据列表
	 * @param pn 指定的part number
	 * @return 返回的指定pn最小需求日期
	 */
	public static Date getMinDate(List<DemandContent> demandlist,String pn) {
		if(demandlist==null) {
			logger.error("需求数据列表为空。");
			return null;
		}
		if(demandlist.size()==0) {
			logger.warn("需求数据列表不包含任何数据。");
			return null;
		}
		Date mindate=new Date("2199/12/31");		//设置一个最大日期
		for(DemandContent dcontent:demandlist)
			if(dcontent.getPn().equals(pn))			//如果型号相符
				if(mindate.after(dcontent.getDate()))	//如果最小日期在此最新日期之后
					mindate=(Date) dcontent.getDate().clone();			//设置为最小日期
		return mindate;
	}
	
	/**
	 * 在demandlist中查找指定part bumber的最晚需求日期
	 * @param demandlist 需求数据列表
	 * @param pn 指定型号
	 * @return 最晚需求日期
	 */
	public static Date getMaxDate(List<DemandContent> demandlist,String pn) {
		if(demandlist==null) {
			logger.error("需求数据列表为空。");
			return null;
		}
		if(demandlist.size()==0) {
			logger.warn("需求数据列表不包含任何数据。");
			return null;
		}
		Date maxdate=new Date("1910/12/31");		//设置一个最小日期
		for(DemandContent dcontent:demandlist)
			if(dcontent.getPn().equals(pn))			//如果型号相符
				if(maxdate.before(dcontent.getDate()))			//如果最小日期在此最新日期之后
					maxdate=(Date) dcontent.getDate().clone();	//设置为最小日期
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
		if(begin.after(end)) {
			logger.error("开始时间晚于结束时间。");
			return -1;
		}
		int counter=0;
		Date index=(Date)begin.clone();
		while(true) {
			if(index.after(end)) break;
			if(!demandmap.containsKey(index)) {		//如果没有这个日期，则加入空节点
				
			}
		}
	}

}