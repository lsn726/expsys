package com.logsys.model;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * Model����
 * @author lx8sn6
 */
public class ModelService {

	private static Logger logger=Logger.getLogger(ModelService.class);
	
	/**�ͺ��б�*/
	private static List<ModelContent> modellist;
	
	static {
		modellist=getModelListFromDB(null);
		if(modellist==null)
			logger.error("ModelServiceģ���ʼ�����󣬲��ܻ�ȡ�ͺ��б�");
	}

	/**
	 * �����ݿ��ж�ȡmodelset�а�����ģ����Ϣ
	 * @param modelset ģ��pn����nullΪ������
	 * @return ģ���б�
	 */
	public static List<ModelContent> getModelListFromDB(Set<String> modelset) {
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session��������:"+ex);
			return null;
		}
		String hql="from ModelContent where ";
		if(modelset!=null)	{				//�����Ϊ���������������
			for(String model:modelset)
				hql+="model='"+model+"' or ";
			hql=hql.substring(0, hql.length()-" or ".length());
		} else {							//����û���κ���������
			hql+="1=1";
		}
		Query query;
		List<ModelContent> modellist=null;
		try {
			query=session.createQuery(hql);
			modellist=query.list();
		} catch(Throwable ex) {
			logger.error("��ȡ���ݴ���:"+ex.toString());
		} finally {
			session.close();
		}
		return modellist;
	}
	
}
