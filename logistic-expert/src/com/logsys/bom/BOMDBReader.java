package com.logsys.bom;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * BOM���ݿ��ȡ��
 * @author lx8sn6
 */
public class BOMDBReader {

	private static final Logger logger=Logger.getLogger(BOMDBReader.class);
	
	/**
	 * ��ȡBOM�б�
	 * @param version BOM�İ汾,nullΪ�����ư汾
	 * @return BOM�б�
	 */
	public static List<BOMContent> getBOMList(Calendar version) {
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session��������"+ex);
			return null;
		}
		String hql="from BOMContent where 1=1";
		if(version!=null)					//version��Ϊ����׷��version����
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
			logger.error("BOM���ݶ�ȡ����",ex);
			return null;
		}
	}
	
}
