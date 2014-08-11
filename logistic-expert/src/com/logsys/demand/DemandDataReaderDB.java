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
 * �����ļ����ݿ��ȡ��
 * @author lx8sn6
 */
public class DemandDataReaderDB {

	private static final Logger logger=Logger.getLogger(DemandDataReaderDB.class);
	
	/**
	 * �����ݿ�����ȡָ���ͺţ�ָ��ʱ�������ڵ���������
	 * @param pnset �ͺż���null������Ҫ�����ͺ�
	 * @param begin ��ʼʱ�䣬null����û����ʼʱ������
	 * @param end ����ʱ�䣬null����û����ʼʱ������
	 * @return �ɹ����ǰ����������ݵ��б�/ʧ����Ϊnull
	 */
	public static List<DemandContent> getDemandDataFromDB_OnDay(Set<String> pnset, Date begin, Date end) {
		if(begin!=null&&end!=null) 
			if(begin.after(end)) {
				logger.error("��ʼʱ�����ڽ���ʱ�䡣");
				return null;
			}
		if(pnset!=null)
			if(pnset.size()==0)
				return new ArrayList<DemandContent>();
		//����hql���
		String hql;
		if(pnset!=null) {							//�����ҪpnsetԼ��
			hql="from DemandContent where (";
			for(Object pn:pnset)
				hql=hql+"pn='"+(String)pn+"' or ";
			hql=hql.substring(0,hql.length()-4);	//ȥ�����һ��or
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
		//��ʼ��ȡ����
		List<DemandContent> demlist=null;
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session��������"+ex);
			return null;
		}
		Query query;
		try {
			query=session.createQuery(hql);
			if(begin!=null) query.setDate("begindate", begin);
			if(end!=null) query.setDate("enddate", end);
			demlist=query.list();
		} catch(Throwable ex) {
			logger.error("������ȡ���ִ���"+ex);
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return demlist;
	}
	
	/**
	 * �����ݿ��ж�ȡ������������
	 * @param pnset �����ͺ��б�null�������ͺ�
	 * @param begin ��ʼ���ڣ�null��������ʼ����
	 * @param end �������ڣ�null�����ƽ�������
	 * @return ��������/null
	 */
	public static List<DemandContent_Week> getDemandDataFromDB_OnWeek(Set<String> pnset, Date begin, Date end) {
		if(begin!=null&&end!=null) 
			if(begin.after(end)) {
				logger.error("��ʼʱ�����ڽ���ʱ�䡣");
				return null;
			}
		if(pnset!=null)
			if(pnset.size()==0) return new ArrayList<DemandContent_Week>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:",ex);
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
			logger.error("�����ݿ��ȡ������������ʱ����",ex);
			return null;
		}
	}
	
	/**
	 * �����ݿ��ж�ȡ������������
	 * @param pnset �����ͺ��б�null�������ͺ�
	 * @param begin ��ʼ���ڣ�null��������ʼ����
	 * @param end �������ڣ�null�����ƽ�������
	 * @return ��������/null
	 */
	public static List<DemandContent_Month> getDemandDataFromDB_OnMonth(Set<String> pnset, Date begin, Date end) {
		if(begin!=null&&end!=null) 
			if(begin.after(end)) {
				logger.error("��ʼʱ�����ڽ���ʱ�䡣");
				return null;
			}
		if(pnset!=null)
			if(pnset.size()==0) return new ArrayList<DemandContent_Month>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:",ex);
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
			logger.error("�����ݿ��ȡ������������ʱ����",ex);
			return null;
		}
	}
	
	/**
	 * ��ȡ���ݵ��������ݣ�������������
	 * @param pnset ��Ҫ��ȡ��Ʒ�ż������Ϊnull�����ȡ���е��ͺ�
	 * @param begin ����ʼʱ�䣬���Ϊnull����������ʼʱ��
	 * @param end   �������ʱ�䣬���Ϊnull�������ƽ���ʱ��
	 * @return ���������б�/null��
	 */
	public static List<DemandBackupContent> getBackupDemandDataFromDB_OnDay(Set<String> pnset, Calendar begin, Calendar end) {
		if(pnset!=null)
			if(pnset.size()==0) return new ArrayList<DemandBackupContent>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:",ex);
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
			logger.error("�����ݿ��ȡ���챸����������ʱ���ִ���",ex);
			return null;
		}
	}
	
	/**
	 * ��ȡ���ݵ��������ݣ�������������
	 * @param pnset ��Ҫ��ȡ��Ʒ�ż������Ϊnull�����ȡ���е��ͺ�
	 * @param begin ����ʼʱ�䣬���Ϊnull����������ʼʱ��
	 * @param end   �������ʱ�䣬���Ϊnull�������ƽ���ʱ��
	 * @return ���������б�/null��
	 */
	public static List<DemandBackupContent_Week> getBackupDemandDataFromDB_OnWeek(Set<String> pnset, Calendar begin, Calendar end) {
		if(pnset!=null)
			if(pnset.size()==0) return new ArrayList<DemandBackupContent_Week>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:",ex);
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
			logger.error("�����ݿ��ȡ���ܱ�����������ʱ���ִ���",ex);
			return null;
		}
	}
	
}