package com.logsys.model;

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
public class ModelDataReader {

	private static final Logger logger=Logger.getLogger(ModelDataReader.class);
	
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
		Map<String,Integer> ordermap;
		try {
			Session session=HibernateSessionFactory.getSession();
			String hql="from ModelContent where ";
			for(String model:modelset)
				hql+="model='"+model+"' or ";
			hql=hql.substring(0, hql.length()-4);
			hql+=" order by client,prjcode,info,model";
			Query query=session.createQuery(hql);
			List<ModelContent> list=query.list();
			int position=0;
			ordermap=new HashMap<String,Integer>();
			for(ModelContent model:list) {
				ordermap.put(model.getModel(), position);
				position++;
			}
			return ordermap;
		} catch(Throwable ex) {
			logger.error("������ִ���:"+ex.toString());
			return null;
		}
	}
	
}
