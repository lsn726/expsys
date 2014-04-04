package com.logsys.prodplan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;
import com.logsys.util.DateInterval;

/**
 * 生产计划数据读取器
 * @author lx8sn6
 */
public class ProdplanDataReaderDB {

	private static final Logger logger=Logger.getLogger(ProdplanDataReaderDB.class);
	
	/**
	 * 获取生产计划。
	 * @param dinterval 生产计划的区间。如果begin为null,则默认上限为下周一；如果end为null,则默认不限制下限。 
	 * @return 读取的生产计划信息列表
	 */
	public static List<ProdplanContent> getProdplan(DateInterval dinterval) {
		if(dinterval==null) {
			logger.error("不能获取生产计划，参数为空。");
			return null;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("创建Session错误:",ex);
			return null;
		}
		Query query;
		String hql="from ProdplanContent where 1=1";
		if(dinterval.begindate==null) {					//如果没有开始时间，则设置默认为下周一
			Calendar cal=Calendar.getInstance();
			int week=cal.get(Calendar.WEEK_OF_YEAR)+1;
			int year=cal.get(Calendar.YEAR);
			cal.setWeekDate(year, week, cal.MONDAY);
			hql+=" and date>=:begindate";
			dinterval.begindate=new Date(year+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(cal.DAY_OF_MONTH));
		}
		if(dinterval.enddate!=null) hql+=" and date<=:enddate";			//如果有enddate，则加入限制
		List<ProdplanContent> pplist=new ArrayList<ProdplanContent>();	//生产计划列表
		try {											//开始读取列表
			query=session.createQuery(hql);
			query.setDate("begindate", dinterval.begindate);
			if(dinterval.enddate!=null) query.setDate("enddate", dinterval.enddate);
			pplist=query.list();
			session.close();
			return pplist;
		} catch(Throwable ex) {
			logger.error("数据库读取出现错误:",ex);
			session.close();
			return null;
		}
	}

}
