package com.logsys.demand;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * ��������д������д��Database
 * @author lx8sn6
 */
public class DemandDataWriterDB {

	private static Logger logger=Logger.getLogger(DemandDataWriterDB.class);
	
	/**
	 * ����д�����ݿ�����
	 * @param demandlist ���������б�
	 * @return д�����ݿ������ 
	 */
	public static int insert(List<DemandContent> demandlist) {
		if(demandlist==null) {
			logger.error("����Ϊ�գ������б�δ��д�롣");
			return 0;
		}
		if(demandlist.size()==0) return 0;
		try {
			Session session=HibernateSessionFactory.getSession();
			session.beginTransaction();
			int counter=0;
			for(DemandContent dcontent:demandlist) {
				session.save(dcontent);
				if(counter%100==0) {
					session.flush();
					session.clear();
				}
				counter++;
			}
			session.getTransaction().commit();
			return counter;
		} catch(Throwable ex) {
			logger.error("��������д����ִ���"+ex.toString());
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * ���¸���DemandUtil��fillEmptyDemandNodes���ɵ�Map������������Demand��Ϣ,�������Ŀû�������ݿ��У���д�����ݿ�
	 * @param demandlist ��������
	 * @return ���µ���Ŀ����
	 */
	public static int updateOrInsert(Map<String,Map<Date,DemandContent>> demandmap) {
		if(demandmap==null) {
			logger.error("����Ϊ�գ������б�δ�ܸ���");
			return 0;
		}
		if(demandmap.size()==0) return 0;
		try {
			Session session=HibernateSessionFactory.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			int counter=0;
			Map<Date,DemandContent> submap;
			Criteria criteria;
			Date mindate;
			Date maxdate;
			Query delquery;			//ɾ���ɼ�¼��Query
			Query insertquery;		//д���¼�¼��Query
			for(Object pn:demandmap.keySet()) {
				//����ɾ��������ָ���ͺŵļ�¼
				submap=demandmap.get(pn);
				mindate=DemandUtil.getMinDate(submap);
				maxdate=DemandUtil.getMaxDate(submap);
				delquery=session.createQuery("delete from DemandContent where pn=:pn and date>=:mindate and date<=:maxdate");
				delquery.setString("pn", (String)pn);
				delquery.setDate("mindate", mindate);
				delquery.setDate("maxdate", maxdate);
				delquery.executeUpdate();
				//��д���µļ�¼
				DemandContent tempDContent;
				for(Object date:submap.keySet()) {
					tempDContent=(DemandContent)submap.get(date);
					if(tempDContent==null) {
						logger.warn("�յ�null��������������Bug��");
						continue;
					}
					session.save(tempDContent);
					counter++;
					if(counter%50==0) {
						session.flush();
						session.clear();
					}
				}
				session.flush();
				session.clear();
			}
			session.getTransaction().commit();
			return counter;
		} catch(Throwable ex) {
			logger.error("�������ݸ��³��ִ���"+ex.toString());
			ex.printStackTrace();
			return 0;
		}
	}
	
}
