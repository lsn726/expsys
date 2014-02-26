package com.logsys.demand;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

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
	public static int writeToDatabase(List<DemandContent> demandlist) {
		if(demandlist==null) {
			logger.error("����Ϊ�ա�");
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
			logger.error("��������д��������⡣"+ex.toString());
			return 0;
		}
	}
	
}
