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
 * �����ƻ����ݶ�ȡ��
 * @author lx8sn6
 */
public class ProdplanDataReaderDB {

	private static final Logger logger=Logger.getLogger(ProdplanDataReaderDB.class);
	
	/**
	 * ��ȡ�����ƻ���
	 * @param dinterval �����ƻ������䡣���beginΪnull,��Ĭ������Ϊ����һ�����endΪnull,��Ĭ�ϲ��������ޡ� 
	 * @return ��ȡ�������ƻ���Ϣ�б�
	 */
	public static List<ProdplanContent> getProdplan(DateInterval dinterval) {
		if(dinterval==null) {
			logger.error("���ܻ�ȡ�����ƻ�������Ϊ�ա�");
			return null;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:",ex);
			return null;
		}
		Query query;
		String hql="from ProdplanContent where 1=1";
		if(dinterval.begindate==null) {					//���û�п�ʼʱ�䣬������Ĭ��Ϊ����һ
			Calendar cal=Calendar.getInstance();
			int week=cal.get(Calendar.WEEK_OF_YEAR)+1;
			int year=cal.get(Calendar.YEAR);
			cal.setWeekDate(year, week, Calendar.MONDAY);
			hql+=" and date>=:begindate";
			dinterval.begindate=new Date(year+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(cal.DAY_OF_MONTH));
		}
		if(dinterval.enddate!=null) hql+=" and date<=:enddate";			//�����enddate�����������
		List<ProdplanContent> pplist=new ArrayList<ProdplanContent>();	//�����ƻ��б�
		try {											//��ʼ��ȡ�б�
			query=session.createQuery(hql);
			query.setDate("begindate", dinterval.begindate);
			if(dinterval.enddate!=null) query.setDate("enddate", dinterval.enddate);
			pplist=query.list();
			session.close();
			return pplist;
		} catch(Throwable ex) {
			logger.error("���ݿ��ȡ���ִ���:",ex);
			session.close();
			return null;
		}
	}

	/**
	 * �����ݿ��ж�ȡ���ܵ������ƻ�����
	 * @param plset �ƻ���������������,null������������
	 * @param dinterval ʱ�����䣬beginΪnull������ʼʱ��,endΪnull�����ƽ���ʱ��,����Ϊnull��ʱ�䲻��������
	 * @return ���ܵļƻ��б�/null
	 */
	public static List<ProdplanContent_Week> getOnWeekProdplanDataFromDB(Set<ProdLine> plset, DateInterval dinterval) {
		if(plset!=null)
			if(plset.size()==0) return new ArrayList<ProdplanContent_Week>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:",ex);
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
			logger.error("�����ݿ��ж�ȡ���ܵ������ƻ�����ʱ���ִ���",ex);
			session.close();
			return null;
		}
	}
	
}
