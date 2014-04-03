package com.logsys.prodplan;

import java.util.List;

import org.apache.log4j.Logger;

import com.logsys.util.DateInterval;

/**
 * 生产计划工具类
 * @author lx8sn6
 */
public class ProdplanUtils {

	private static final Logger logger=Logger.getLogger(ProdplanUtils.class);
	
	/**
	 * 从pplist确认其中生产计划的区间
	 * @param pplist
	 * @return 时间区间
	 */
	public static DateInterval getDataInterval(List<ProdplanContent> pplist) {
		if(pplist==null) {
			logger.error("不能确认区间，因为参数为空。");
			return null;
		}
		if(pplist.size()==0) return null;
		DateInterval dinterval=new DateInterval();
		dinterval.begindate=pplist.get(0).getDate();		//开始时间和结束时间都初始化为0
		dinterval.enddate=pplist.get(0).getDate();
		for(ProdplanContent ppcont:pplist) {				//循环找寻开始时间和结束时间
			if(ppcont.getDate().before(dinterval.begindate)) dinterval.begindate=ppcont.getDate();
			if(ppcont.getDate().after(dinterval.enddate)) dinterval.enddate=ppcont.getDate();
		}
		return dinterval;
	}
	
}
