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
 * Model����
 * @author lx8sn6
 */
public class ModelService {

	private static Logger logger=Logger.getLogger(ModelService.class);
	
	/**�ͺ��б�*/
	private static List<ModelContent> modellist;
	
	/**�ͺ�����ͼ: Map<�ͺţ�����>*/
	private static Map<String,Integer> modelorder;
	
	/**�ͺţ��ͺŶ���ӳ��ͼ*/
	private static Map<String,ModelContent> modelmap;
	
	static {
		if(!initModelService()) {
			logger.fatal("ModelServiceģ���ʼ�����ִ��󣬲��ܻ�ȡ�ͺ��б���๦�ܽ������ã�������ֹ��");
			System.exit(-1);
		}
	}

	/**
	 * ���ýӿڣ��������¶�ȡ�ͺ��б�
	 * @return ��ʼ���ɹ�true/��ʼ��ʧ��false
	 */
	public static boolean reInitModelService() {
		return initModelService();
	}
	
	/**
	 * ��ȡ�ͺ��б�
	 * @return �ͺ��б����
	 */
	public static List<ModelContent> getModelList() {
		return new ArrayList<ModelContent>(modellist);
	}
	
	/**
	 * ��ȡ�ͺ�˳��ͼ
	 * @return ˳��ͼ�б�
	 */
	public static Map<String,Integer> getSortedModelMap() {
		return new HashMap<String,Integer>(modelorder);
	}
	
	/**
	 * ͨ���ͺ�Part Number��ȡ�ͺŶ���
	 * @param pn �ͺŵ�Part Number
	 * @return ��Ӧ���ͺŶ���
	 */
	public static ModelContent getModelContentByPn(String pn) {
		return modelmap.get(pn).clone();
	}
	
	/**
	 * ��ʼ���ͺ��б���
	 */
	private static boolean initModelService() {
		if(!readSortedModelListFromDB(null)) {		//��ʼ���ͺ��б���ͺ�˳��ͼ����
			logger.error("δ�ܳɹ������ݶ�ȡ�ͺ����ݡ�");
			return false;
		}
		modelmap=new HashMap<String,ModelContent>();
		for(ModelContent mcont:modellist)			//��ʼpn->model�����ͺ�ͼ
			modelmap.put(mcont.getModel(), mcont);
		return true;
	}

	/**
	 * �����ݿ��ж�ȡ˳�򻰵ĳ�Ʒ�б�ͬʱ��ʼ�������������ͺ��б���ͺ�˳��ͼ�� ��������Ϊ��client,prjcode,info,client
	 * @param modelset ��Ҫ��ȡ���ͺż������null��Ϊ���ͺ�����
	 * @return ��ȡ�ɹ�true/��ȡʧ��false
	 */
	private static boolean readSortedModelListFromDB(Set<String> modelset) {
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session��������:"+ex);
			return false;
		}
		String hql="from ModelContent where ";		//��ʼ����hql
		if(modelset!=null&&modelset.size()!=0)	{	//�����Ϊ���������������
			for(String model:modelset)
				hql+="model='"+model+"' or ";
			hql=hql.substring(0, hql.length()-" or ".length());
		} else {									//����û���κ���������
			hql+="1=1";
		}
		hql+=" order by client,prjcode,info,model";
		List<ModelContent> mlist=null;			//�ͺ��б�
		Map<String,Integer> ordermap=null;		//˳���ͺ�ͼ
		Query query;
		try {
			query=session.createQuery(hql);
			mlist=query.list();
			int position=0;
			ordermap=new HashMap<String,Integer>();
			for(ModelContent model:mlist) {
				ordermap.put(model.getModel(), position);
				position++;
			}
		} catch(Throwable ex) {
			logger.error("������ִ���:"+ex.toString());
		} finally {
			session.close();
		}
		modellist=mlist;
		modelorder=ordermap;
		return true;
	}
	
}
