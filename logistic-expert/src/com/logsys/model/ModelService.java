package com.logsys.model;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * Model服务
 * @author lx8sn6
 */
public class ModelService {

	private static Logger logger=Logger.getLogger(ModelService.class);
	
	/**型号列表*/
	private static List<ModelContent> modellist;
	
	static {
		modellist=getModelListFromDB(null);
		if(modellist==null)
			logger.error("ModelService模块初始化错误，不能获取型号列表。");
	}

	/**
	 * 从数据库中读取modelset中包含的模型信息
	 * @param modelset 模型pn集，null为不限制
	 * @return 模型列表
	 */
	public static List<ModelContent> getModelListFromDB(Set<String> modelset) {
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session创建错误:"+ex);
			return null;
		}
		String hql="from ModelContent where ";
		if(modelset!=null)	{				//如果不为空则加入限制条件
			for(String model:modelset)
				hql+="model='"+model+"' or ";
			hql=hql.substring(0, hql.length()-" or ".length());
		} else {							//否则没有任何限制条件
			hql+="1=1";
		}
		Query query;
		List<ModelContent> modellist=null;
		try {
			query=session.createQuery(hql);
			modellist=query.list();
		} catch(Throwable ex) {
			logger.error("读取数据错误:"+ex.toString());
		} finally {
			session.close();
		}
		return modellist;
	}
	
}
