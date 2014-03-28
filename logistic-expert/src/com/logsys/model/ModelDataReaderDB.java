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
 * 模型数据读取器
 * @author lx8sn6
 */
public class ModelDataReaderDB {

	private static final Logger logger=Logger.getLogger(ModelDataReaderDB.class);
	
	/**
	 * 根据modelset所提供的型号查询排序，返回Map<型号,顺序>。排序依据为：client,prjcode,info,client
	 * @param modelset 需要排序的型号集
	 * @return Map<型号,排序>对象
	 */
	public static Map<String,Integer> sortModels(Set<String> modelset) {
		if(modelset==null) {
			logger.error("参数为空。");
			return null;
		}
		if(modelset.size()==0)
			return new HashMap<String,Integer>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session创建错误:"+ex);
			return null;
		}
		String hql="from ModelContent where ";		//开始创建hql
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
			logger.error("排序出现错误:"+ex.toString());
		} finally {
			session.close();
		}
		return ordermap;
	}

	
	/**
	 * 从数据库中读取modelset中包含的模型信息
	 * @param modelset 模型pn集，null为不限制
	 * @return 模型列表
	 */
	public static List<ModelContent> getDataFromDB(Set<String> modelset) {
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