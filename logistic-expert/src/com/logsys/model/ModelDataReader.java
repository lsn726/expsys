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
 * 模型数据读取器
 * @author lx8sn6
 */
public class ModelDataReader {

	private static final Logger logger=Logger.getLogger(ModelDataReader.class);
	
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
			logger.error("排序出现错误:"+ex.toString());
			return null;
		}
	}
	
}
