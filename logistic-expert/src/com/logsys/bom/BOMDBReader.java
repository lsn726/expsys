package com.logsys.bom;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * BOM数据库读取器
 * @author lx8sn6
 */
public class BOMDBReader {

	private static final Logger logger=Logger.getLogger(BOMDBReader.class);
	
	/**
	 * 获取BOM列表
	 * @param version BOM的版本,null为不限制版本
	 * @return BOM列表
	 */
	public static List<BOMContent> getBOMList(Calendar version) {
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session创建错误："+ex);
			return null;
		}
		String hql="from BOMContent where 1=1";
		if(version!=null)					//version不为空则追加version限制
			hql+=" and version=ver";
		List<BOMContent> bomlist;
		Query query;
		try {
			query=session.createQuery(hql);
			if(version!=null) query.setCalendarDate("ver", version);
			bomlist=query.list();
			session.close();
			return bomlist;
		} catch(Exception ex) {
			logger.error("BOM数据读取错误。",ex);
			return null;
		}
	}
	
}
