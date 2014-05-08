package com.logsys.matoperdoc;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * ���ϲ�������д����--���ݿ�д����
 * @author ShaonanLi
 */
public class MatOperDocContentDataWriterDB {

	private static final Logger logger=Logger.getLogger(MatOperDocContentDataWriterDB.class);
	
	/**
	 * ��pplist�б�д�����ݿ�
	 * @param pplist �����ƻ��б�
	 * @return д�����ݿ������
	 */
	public static int writeToDB(List<MatOperDocContent> doclist) {
		if(doclist==null) {
			logger.error("���ܽ����ϲ�����¼д�����ݿ�,�б����Ϊ��.");
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
			for(MatOperDocContent doccont:doclist) {
				//System.out.println(doccont);
				session.save(doccont);
				if(counter%50==0) {
					session.flush();
					session.clear();
				}
				counter++;
			}
			session.getTransaction().commit();
			return counter;
		} catch(Throwable ex) {
			logger.error("���ݿ�д����ִ���",ex);
			session.close();
			return -1;
		}
	}
	
}
