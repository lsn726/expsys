package com.logsys.demand;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.logsys.util.DateInterval;

/***
 * 需求数据工具类
 * @author lx8sn6
 */
public class DemandUtil {

	private static Logger logger=Logger.getLogger(DemandUtil.class);
	
	/**字符串前缀：最小值*/
	public static final String PREFIX_MINDATE="MinDate-";
	
	/**字符串前缀：最大值*/
	public static final String PREFIX_MAXDATE="MaxDate-";
	
	/**总记录字符串*/
	public static final String TOTAL_STR="TotalRecord";
	
	/**
	 * 获取List<DemandContent>中时间的最小值和最大值的区间对象
	 * @param demlist 需求内容列表对象
	 * @return 时间最小值和最大值的去见对象/null
	 */
	public static DateInterval getMinMaxDateInDemandList(List<DemandContent> demlist) {
		if(demlist==null) {
			logger.error("不能在需求内容列表中获取时间最小值，列表为空。");
			return null;
		}
		if(demlist.size()==0) return null;
		Date mindate=demlist.get(0).getDate();
		Date maxdate=demlist.get(0).getDate();
		for(DemandContent demcont:demlist) {
			if(mindate.after(demcont.getDate()))
				mindate=demcont.getDate();
			if(maxdate.before(demcont.getDate()))
				maxdate=demcont.getDate();
		}
		return new DateInterval(mindate,maxdate);
	}
	
	/**
	 * 在BackupDemandList中获取最大最小的版本日期Map，格式为：PREFIX_MINDATE+"型号"->该型号最小日期 、PREFIX_MAXDATE+"型号"->该型号最大日期、PREFIX_MINDATE+TOTAL_STR->整体最小日期、PREFIX_MAXDATE+TOTAL_STR->整体最大日期
	 * @param bkupdemlist 备份需求列表按周对象 
	 * @return 代表版本日期的最大最小值的区间映射对象/null
	 */
	public static Map<String,Date> getMinMaxVersionDateInBackupDemandList(List<DemandBackupContent_Week> bkupdemwklist) {
		if(bkupdemwklist==null) {
			logger.error("不能提取数据备份需求列表的最大最小版本数据，备份列表参数值为null.");
			return null;
		}
		Map<String,Date> verMap=new HashMap<String,Date>();
		Date tempdate;		//临时日期变量
		Date contdate;		//内容日期时间
		String pn;			//物料号
		for(DemandBackupContent_Week bkdemcont:bkupdemwklist) {	//遍历循环获得最小值
			pn=bkdemcont.getPn();
			contdate=bkdemcont.getVersion();				//以版本为判别依据
			tempdate=verMap.get(PREFIX_MINDATE+pn);			//先筛选最小值
			if(tempdate==null||tempdate.after(contdate))	//最小值为空，或者最小值在新值之后，则写入新的最小值
				verMap.put(PREFIX_MINDATE+pn, contdate);
			tempdate=verMap.get(PREFIX_MAXDATE+pn);			//再筛选最大值
			if(tempdate==null||tempdate.before(contdate))	//最大值为空，或者最大值在新职之前，则写入新的最大值
				verMap.put(PREFIX_MAXDATE+pn, contdate);
			tempdate=verMap.get(PREFIX_MINDATE+TOTAL_STR);	//再筛选总值最小值
			if(tempdate==null||tempdate.after(contdate))
				verMap.put(PREFIX_MINDATE+TOTAL_STR, contdate);
			tempdate=verMap.get(PREFIX_MAXDATE+TOTAL_STR);	//再筛总值选最大值
			if(tempdate==null||tempdate.before(contdate))
				verMap.put(PREFIX_MAXDATE+TOTAL_STR, contdate);
		}
		return verMap;
	}
	
	/**
	 * 在BackupDemandList中获取最大最小的需求日期Map，格式为："Min-型号"->该型号最小日期 、"Max-型号"->该型号最大日期、"Min-TotalRecord"->整体最小日期、"Max-TotalRecord"->整体最大日期
	 * @param bkupdemlist 备份需求列表按周对象 
	 * @return 代表需求日期的最大最小值的区间映射对象/null
	 */
	public static Map<String,Date> getMinMaxDemandDateInBackupDemandList(List<DemandBackupContent_Week> bkupdemwklist) {
		if(bkupdemwklist==null) {
			logger.error("不能提取数据备份需求列表的最大最小需求数据，备份列表参数值为null.");
			return null;
		}
		Map<String,Date> verMap=new HashMap<String,Date>();
		Date tempdate;		//临时日期变量
		Date contdate;		//内容日期时间
		String pn;			//物料号
		for(DemandBackupContent_Week bkdemcont:bkupdemwklist) {	//遍历循环获得最小值
			pn=bkdemcont.getPn();
			contdate=bkdemcont.getDate();					//以需求为判别依据
			tempdate=verMap.get(PREFIX_MINDATE+pn);			//先筛选最小值
			if(tempdate==null||tempdate.after(contdate))	//最小值为空，或者最小值在新值之后，则写入新的最小值
				verMap.put(PREFIX_MINDATE+pn, contdate);
			tempdate=verMap.get(PREFIX_MAXDATE+pn);			//再筛选最大值
			if(tempdate==null||tempdate.before(contdate))	//最大值为空，或者最大值在新职之前，则写入新的最大值
				verMap.put(PREFIX_MAXDATE+pn, contdate);
			tempdate=verMap.get(PREFIX_MINDATE+TOTAL_STR);	//再筛选总值最小值
			if(tempdate==null||tempdate.after(contdate))
				verMap.put(PREFIX_MINDATE+TOTAL_STR, contdate);
			tempdate=verMap.get(PREFIX_MAXDATE+TOTAL_STR);	//再筛总值选最大值
			if(tempdate==null||tempdate.before(contdate))
				verMap.put(PREFIX_MAXDATE+TOTAL_STR, contdate);
		}
		return verMap;
	}
	
}