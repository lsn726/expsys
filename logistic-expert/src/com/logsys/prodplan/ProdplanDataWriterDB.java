package com.logsys.prodplan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;
import com.logsys.util.DateInterval;

/**
 * �����ƻ�д���������ݿ�д����
 * @author lx8sn6
 */
public class ProdplanDataWriterDB {

	private static final Logger logger=Logger.getLogger(ProdplanDataWriterDB.class);
	
	/**
	 * ��pplist�б�д�����ݿ�
	 * @param pplist �����ƻ��б�
	 * @return д�����ݿ������
	 */
	public static int writeToDB(List<ProdplanContent> pplist) {
		if(pplist==null) {
			logger.error("���ܽ������ƻ��б�д�����ݿ�,pplist����Ϊ��.");
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
			for(ProdplanContent ppcont:pplist) {
				session.save(ppcont);
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
	
	/**
	 * �����ƶ������ڵ������ƻ�
	 * @param dinterval ʱ������
	 * @param version �����ƻ��汾,null��Ϊ��ǰʱ��
	 * @return �ɹ���������/-1ʧ��
	 */
	public static int backupProdPlan(DateInterval dinterval, Date version) {
		if(dinterval==null) {
			logger.error("���ܱ��������ƻ�,�����������Ϊ��.");
			return -1;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:"+ex);
			return -1;
		}
		Query query;
		List<ProdplanContent> plist=new ArrayList<ProdplanContent>();
		String hql="from ProdplanContent where date>=:begindate and date<=:enddate";	//�޶�����
		try {														//��ȡԭ��������
			query=session.createQuery(hql);
			query.setDate("begindate", dinterval.begindate);
			query.setDate("enddate", dinterval.enddate);
			plist=query.list();
		} catch(Throwable ex) {
			logger.error("���ܱ�������,ԭ���ݶ�ȡ����.",ex);
			return -1;
		}
		if(version==null) version=new Date();
		int counter=0;
		try {
			for(ProdplanContent ppcont: plist) {						//д�뱸������
				session.save(new ProdplanBackupContent(ppcont,version));
				if(counter%50==0) {
					session.flush();
					session.clear();
				}
				counter++;
			}
			session.getTransaction().commit();
			return counter;
		} catch(Throwable ex) {
			logger.error("����д�뱸�����ݣ�д�뱸�����ݿ����",ex);
			session.close();
			return -1;
		}
	}
	
	/**
	 * ɾ���涨�����ڵ������ƻ� 
	 * @param dinterval ָ����ʱ������
	 * @param resetPlanBeyondScope �Ƿ�ɾ��ָ����������δ���ļƻ�
	 * @return ɾ��������/-1ʧ��
	 */
	public static int deleteProdplan(DateInterval dinterval, boolean resetPlanBeyondScope) {
		if(dinterval==null) {
			logger.error("����ɾ���ƻ�.�������Ϊ��.");
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
		Query query;
		String hql="delete from ProdplanContent where date>=:begindate";
		if(!resetPlanBeyondScope)
			hql+=" and date<=:enddate";
		try {
			query=session.createQuery(hql);
			query.setDate("begindate", dinterval.begindate);
			if(!resetPlanBeyondScope) query.setDate("enddate", dinterval.enddate);
			int deletedqty=query.executeUpdate();
			session.close();
			return deletedqty;
		} catch(Throwable ex) {
			logger.error("����ɾ���ƻ���¼��",ex);
			session.close();
			return -1;
		}
	}
	
}
