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
 * Model服务
 * @author lx8sn6
 */
public class ModelService {

	private static Logger logger=Logger.getLogger(ModelService.class);
	
	/**型号列表*/
	private static List<ModelContent> modellist;
	
	/**型号排序图: Map<型号，排序>*/
	private static Map<String,Integer> modelorder;
	
	/**型号，型号对象映射图*/
	private static Map<String,ModelContent> modelmap;
	
	static {
		if(!initModelService()) {
			logger.fatal("ModelService模块初始化出现错误，不能获取型号列表。许多功能将不可用！程序将终止。");
			System.exit(-1);
		}
	}

	/**
	 * 公用接口，用于重新读取型号列表
	 * @return 初始化成功true/初始化失败false
	 */
	public static boolean reInitModelService() {
		return initModelService();
	}
	
	/**
	 * 获取型号列表
	 * @return 型号列表对象
	 */
	public static List<ModelContent> getModelList() {
		return new ArrayList<ModelContent>(modellist);
	}
	
	/**
	 * 获取型号顺序图
	 * @return 顺序图列表
	 */
	public static Map<String,Integer> getSortedModelMap() {
		return new HashMap<String,Integer>(modelorder);
	}
	
	/**
	 * 通过型号Part Number获取型号对象
	 * @param pn 型号的Part Number
	 * @return 对应的型号对象
	 */
	public static ModelContent getModelContentByPn(String pn) {
		return modelmap.get(pn).clone();
	}
	
	/**
	 * 初始化型号列表函数
	 */
	private static boolean initModelService() {
		if(!readSortedModelListFromDB(null)) {		//初始化型号列表和型号顺序图对象
			logger.error("未能成功送数据读取型号数据。");
			return false;
		}
		modelmap=new HashMap<String,ModelContent>();
		for(ModelContent mcont:modellist)			//初始pn->model对象型号图
			modelmap.put(mcont.getModel(), mcont);
		return true;
	}

	/**
	 * 从数据库中读取顺序话的成品列表，同时初始化两个变量：型号列表和型号顺序图。 排序依据为：client,prjcode,info,client
	 * @param modelset 需要读取的型号集，如果null则为无型号限制
	 * @return 读取成功true/读取失败false
	 */
	private static boolean readSortedModelListFromDB(Set<String> modelset) {
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session创建错误:"+ex);
			return false;
		}
		String hql="from ModelContent where ";		//开始创建hql
		if(modelset!=null&&modelset.size()!=0)	{	//如果不为空则加入限制条件
			for(String model:modelset)
				hql+="model='"+model+"' or ";
			hql=hql.substring(0, hql.length()-" or ".length());
		} else {									//否则没有任何限制条件
			hql+="1=1";
		}
		hql+=" order by client,prjcode,info,model";
		List<ModelContent> mlist=null;			//型号列表
		Map<String,Integer> ordermap=null;		//顺序型号图
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
			logger.error("排序出现错误:"+ex.toString());
		} finally {
			session.close();
		}
		modellist=mlist;
		modelorder=ordermap;
		return true;
	}
	
}
