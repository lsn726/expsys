package com.logsys.production;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * ��������д����--���ݿ�д����
 * @author lx8sn6
 */
public class ProductionDataWriterDB {

	private static final Logger logger=Logger.getLogger(ProductionDataWriterDB.class);
	
	/**
	 * ��������Ϣ�б�д�����ݿ� 
	 * @param pcontlist ����������Ϣ���б�
	 * @return д������/д��ʧ��-1
	 */
	public static int writeDataToDB(List<ProductionContent> pcontlist) {
		if(pcontlist==null) {
			logger.error("���ܽ�������Ϣд�����ݿ⣬�б����Ϊ�ա�");
			return -1;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:",ex);
			return -1;
		}
		int counter=0;
		try {
			for(ProductionContent pcont: pcontlist) {
				session.save(pcont);
				counter++;
				if(counter%50==0) {
					session.flush();
					session.clear();
				}
			}
			session.getTransaction().commit();
			return counter;
		} catch(Throwable ex) {
			logger.error("���ݿ�д����ִ���:",ex);
			session.close();
			return -1;
		}
	}
	
}
