package com.logsys.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * ģ�����ݶ�ȡ��
 * @author lx8sn6
 */
public class ModelDataReaderDB {

	private static final Logger logger=Logger.getLogger(ModelDataReaderDB.class);
	
	/**
	 * ����modelset���ṩ���ͺŲ�ѯ���򣬷���Map<�ͺ�,˳��>����������Ϊ��client,prjcode,info,client
	 * @param modelset ��Ҫ������ͺż�
	 * @return Map<�ͺ�,����>����
	 */
	public static Map<String,Integer> sortModels(Set<String> modelset) {
		if(modelset==null) {
			logger.error("����Ϊ�ա�");
			return null;
		}
		if(modelset.size()==0)
			return new HashMap<String,Integer>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session��������:"+ex);
			return null;
		}
		String hql="from ModelContent where ";		//��ʼ����hql
		for(String model:modelset)
			hql+="model='"+model+"' or ";
		hql=hql.substring(0, hql.length()-4);
		hql+=" order by client,prjcode,info,model";
		Map<String,Integer> ordermap=null;
		Query query;
		try {
			query=session.createQuery(hql);
			List<ModelContent> list=query.list();
			int position=0;
			ordermap=new HashMap<String,Integer>();
			for(ModelContent model:list) {
				ordermap.put(model.getModel(), position);
				position++;
			}
		} catch(Throwable ex) {
			logger.error("������ִ���:"+ex.toString());
		} finally {
			session.close();
		}
		return ordermap;
	}

	
	/**
	 * �����ݿ��ж�ȡmodelset�а�����ģ����Ϣ
	 * @param modelset ģ��pn����nullΪ������
	 * @return ģ���б�
	 */
	public static List<ModelContent> getDataFromDB(Set<String> modelset) {
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