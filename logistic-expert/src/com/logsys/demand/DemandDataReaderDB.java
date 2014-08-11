package com.logsys.demand;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * 需求文件数据库读取器
 * @author lx8sn6
 */
public class DemandDataReaderDB {

	private static final Logger logger=Logger.getLogger(DemandDataReaderDB.class);
	
	/**
	 * 从数据库中提取指定型号，指定时间区间内的需求数据
	 * @param pnset 型号集，null表明需要所有型号
	 * @param begin 开始时间，null表明没有起始时间上限
	 * @param end 结束时间，null表明没有起始时间下限
	 * @return 成功则是包含需求数据的列表/失败则为null
	 */
	public static List<DemandContent> getDemandDataFromDB_OnDay(Set<String> pnset, Date begin, Date end) {
		if(begin!=null&&end!=null) 
			if(begin.after(end)) {
				logger.error("起始时间晚于结束时间。");
				return null;
			}
		if(pnset!=null)
			if(pnset.size()==0)
				return new ArrayList<DemandContent>();
		//生成hql语句
		String hql;
		if(pnset!=null) {							//如果需要pnset约束
			hql="from DemandContent where (";
			for(Object pn:pnset)
				hql=hql+"pn='"+(String)pn+"' or ";
			hql=hql.substring(0,hql.length()-4);	//去掉最后一个or
			hql+=")";
			if(begin!=null) hql+=" and date>=:begindate";
			if(end!=null) hql+=" and date<=:enddate";
		}  else {
			hql="from DemandContent";
			if(begin!=null&&end!=null)
				hql+=" where date>=:begindate and date<=:enddate";
			else if(begin!=null&&end==null)
				hql+=" where date>=:begindate";
			else if(begin==null&&end!=null)
				hql+=" where date<=:enddate";
		}
		//开始提取数量
		List<DemandContent> demlist=null;
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session创建错误："+ex);
			return null;
		}
		Query query;
		try {
			query=session.createQuery(hql);
			if(begin!=null) query.setDate("begindate", begin);
			if(end!=null) query.setDate("enddate", end);
			demlist=query.list();
		} catch(Throwable ex) {
			logger.error("数据提取出现错误："+ex);
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return demlist;
	}
	
	/**
	 * 从数据库中读取按周需求数据
	 * @param pnset 需求型号列表，null不限制型号
	 * @param begin 开始日期，null不限制起始日期
	 * @param end 结束日期，null不限制结束日期
	 * @return 按周需求/null
	 */
	public static List<DemandContent_Week> getDemandDataFromDB_OnWeek(Set<String> pnset, Date begin, Date end) {
		if(begin!=null&&end!=null) 
			if(begin.after(end)) {
				logger.error("起始时间晚于结束时间。");
				return null;
			}
		if(pnset!=null)
			if(pnset.size()==0) return new ArrayList<DemandContent_Week>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("创建Session错误:",ex);
			return null;
		}
		String hql="select new com.logsys.demand.DemandContent_Week(pn,substring(yearweek(date,3),1,4),substring(yearweek(date,3),5,2),sum(qty)) from DemandContent where 1=1";
		if(begin!=null) hql+=" and date>=:begindate";
		if(end!=null) hql+=" and date<=:enddate";
		if(pnset!=null) {
			hql+=" and (";
			for(String pn:pnset) hql+=" pn='"+pn+"' or";
			hql=hql.substring(0,hql.length()-3);
			hql+=")";
		}
		hql+=" group by substring(yearweek(date,3),1,4),substring(yearweek(date,3),5,2),pn";
		Query query;
		try {
			query=session.createQuery(hql);
			if(begin!=null) query.setDate("begindate", begin);
			if(end!=null) query.setDate("enddate", end);
			List<DemandContent_Week> dwlist=query.list();
			session.close();
			return dwlist;
		} catch(Throwable ex) {
			logger.error("从数据库读取按周需求数据时错误：",ex);
			return null;
		}
	}
	
	/**
	 * 从数据库中读取按月需求数据
	 * @param pnset 需求型号列表，null不限制型号
	 * @param begin 开始日期，null不限制起始日期
	 * @param end 结束日期，null不限制结束日期
	 * @return 按月需求/null
	 */
	public static List<DemandContent_Month> getDemandDataFromDB_OnMonth(Set<String> pnset, Date begin, Date end) {
		if(begin!=null&&end!=null) 
			if(begin.after(end)) {
				logger.error("起始时间晚于结束时间。");
				return null;
			}
		if(pnset!=null)
			if(pnset.size()==0) return new ArrayList<DemandContent_Month>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("创建Session错误:",ex);
			return null;
		}
		String hql="select new com.logsys.demand.DemandContent_Month(pn,year(date),month(date),sum(qty)) from DemandContent where 1=1";
		if(begin!=null) hql+=" and date>=:begindate";
		if(end!=null) hql+=" and date<=:enddate";
		if(pnset!=null) {
			hql+=" and (";
			for(String pn:pnset) hql+=" pn='"+pn+"' or";
			hql=hql.substring(0,hql.length()-3);
			hql+=")";
		}
		hql+=" group by year(date),month(date),pn";
		Query query;
		try {
			query=session.createQuery(hql);
			if(begin!=null) query.setDate("begindate", begin);
			if(end!=null) query.setDate("enddate", end);
			List<DemandContent_Month> dwlist=query.list();
			session.close();
			return dwlist;
		} catch(Throwable ex) {
			logger.error("从数据库读取按周需求数据时错误：",ex);
			return null;
		}
	}
	
	/**
	 * 读取备份的需求数据，按天需求数据
	 * @param pnset 需要读取成品号集，如果为null，则读取所有的型号
	 * @param begin 需求开始时间，如果为null，则不限制起始时间
	 * @param end   需求结束时间，如果为null，则不限制结束时间
	 * @return 备份需求列表/null空
	 */
	public static List<DemandBackupContent> getBackupDemandDataFromDB_OnDay(Set<String> pnset, Calendar begin, Calendar end) {
		if(pnset!=null)
			if(pnset.size()==0) return new ArrayList<DemandBackupContent>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("创建Session错误:",ex);
			return null;
		}
		String hql="from DemandBackupContent where 1=1";
		if(begin!=null) hql+=" and date>=:begindate";
		if(end!=null) hql+=" and date<=:enddate";
		if(pnset!=null) {
			hql+=" and (";
			for(String pn:pnset) hql+=" pn='"+pn+"' or";
			hql=hql.substring(0,hql.length()-3);
			hql+=")";
		}
		Query query;
		try {
			query=session.createQuery(hql);
			if(begin!=null) query.setCalendar("begindate", begin);
			if(end!=null) query.setCalendar("enddate", end);
			List<DemandBackupContent> dembkuplist=query.list();
			session.close();
			return dembkuplist;
		} catch(Throwable ex) {
			logger.error("从数据库读取按天备份需求数据时出现错误：",ex);
			return null;
		}
	}
	
	/**
	 * 读取备份的需求数据，按周需求数据
	 * @param pnset 需要读取成品号集，如果为null，则读取所有的型号
	 * @param begin 需求开始时间，如果为null，则不限制起始时间
	 * @param end   需求结束时间，如果为null，则不限制结束时间
	 * @return 备份需求列表/null空
	 */
	public static List<DemandBackupContent_Week> getBackupDemandDataFromDB_OnWeek(Set<String> pnset, Calendar begin, Calendar end) {
		if(pnset!=null)
			if(pnset.size()==0) return new ArrayList<DemandBackupContent_Week>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("创建Session错误:",ex);
			return null;
		}
		String hql="select new com.logsys.demand.DemandBackupContent_Week(substring(yearweek(date,3),1,4),substring(yearweek(date,3),5,2),version,pn,sum(qty),date,dlvfix) from DemandBackupContent where 1=1";
		if(begin!=null) hql+=" and date>=:begindate";
		if(end!=null) hql+=" and date<=:enddate";
		if(pnset!=null) {
			hql+=" and (";
			for(String pn:pnset) hql+=" pn='"+pn+"' or";
			hql=hql.substring(0,hql.length()-3);
			hql+=")";
		}
		hql+=" group by substring(yearweek(date,3),1,4),substring(yearweek(date,3),5,2),version,pn";
		Query query;
		try {
			query=session.createQuery(hql);
			if(begin!=null) query.setCalendar("begindate", begin);
			if(end!=null) query.setCalendar("enddate", end);
			List<DemandBackupContent_Week> dembkuplist=query.list();
			session.close();
			return dembkuplist;
		} catch(Throwable ex) {
			logger.error("从数据库读取按周备份需求数据时出现错误：",ex);
			return null;
		}
	}
	
}