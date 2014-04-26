package com.logsys.prodplan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.setting.pd.bwi.BWIPLInfo.ProdLine;
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
			cal.setWeekDate(year, week, Calendar.MONDAY);
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

	/**
	 * 从数据库中读取按周的生产计划数量
	 * @param plset 计划所包含的生产线,null不限制生产线
	 * @param dinterval 时间区间，begin为null则不限起始时间,end为null则不限制结束时间,整体为null对时间不进行限制
	 * @return 按周的计划列表/null
	 */
	public static List<ProdplanContent_Week> getOnWeekProdplanDataFromDB(Set<ProdLine> plset, DateInterval dinterval) {
		if(plset!=null)
			if(plset.size()==0) return new ArrayList<ProdplanContent_Week>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("创建Session错误:",ex);
			return null;
		}
		String hql="select new com.logsys.prodplan.ProdplanContent_Week(pn,year(date),week(date,3),sum(qty)) from ProdplanContent where 1=1";
		if(dinterval!=null) {
			if(dinterval.begindate!=null) hql+=" and date>=:begin";
			if(dinterval.enddate!=null) hql+=" and date<=:end";
		}
		if(plset!=null) {
			hql+=" and (";
			for(ProdLine pl:plset)
				hql+=" prdline='"+BWIPLInfo.getStdNameByLineEnum(pl)+"' or";
			hql=hql.substring(0, hql.length()-3);
			hql+=")";
		}
		hql+=" group by year(date),week(date,3),pn";
		Query query;
		try {
			query=session.createQuery(hql);
			if(dinterval!=null) {
				if(dinterval.begindate!=null) query.setDate("begin", dinterval.begindate);
				if(dinterval.enddate!=null) query.setDate("end", dinterval.enddate);
			}
			List<ProdplanContent_Week> ppwklist=query.list();
			session.close();
			return ppwklist;
		} catch(Throwable ex) {
			logger.error("从数据库中读取按周的生产计划数据时出现错误：",ex);
			session.close();
			return null;
		}
	}
	
}
