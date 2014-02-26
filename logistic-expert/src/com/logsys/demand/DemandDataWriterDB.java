package com.logsys.demand;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
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
			return 0;
		}
	}
	
	/**
	 * �������ݿ������,�������Ŀû�������ݿ��У���д�����ݿ�
	 * @param demandlist ��������
	 * @return ���µ���Ŀ����
	 */
	public static int updateOrInsert(List<DemandContent> demandlist) {
		if(demandlist==null) {
			logger.error("����Ϊ�գ������б�δ�ܸ���");
			return 0;
		}
		if(demandlist.size()==0) return 0;
		try {
			Session session=HibernateSessionFactory.getSessionFactory().getCurrentSession();
			Criteria criteria;
			List<DemandContent> result;
			DemandContent temp;
			for(DemandContent dcontent:demandlist) {
				criteria=session.createCriteria("DemandContent")
					.add(Restrictions.eq("pn", dcontent.getPn()))
					.add(Restrictions.eq("date", dcontent.getDate()));
				result=criteria.list();
				if(result.size()==0) continue;
				if(result.size()!=1) {
					logger.error("����:"+dcontent.getDate()+",pn:"+dcontent.getPn()+",�ж����¼����.���ݿ����,Υ��pn-date����Լ��.");
					return 0;
				}
				temp=result.get(0);
				temp.setQty(dcontent.getQty());
				temp.setDlvfix(dcontent.getDlvfix());
				criteria=session.createCriteria("DemandContent");
				
			}
		} catch(Throwable ex) {
			logger.error("�������ݸ��³��ִ���"+ex.toString());
			return 0;
		}
	}
	
}
