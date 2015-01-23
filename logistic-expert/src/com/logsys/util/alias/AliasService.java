package com.logsys.util.alias;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * 别名服务类
 * @author lx8sn6
 */
public class AliasService {

	private static Logger logger=Logger.getLogger(AliasService.class);
	
	/**别名列表*/
	private static List<AliasContent> aliaslist;
	
	/**标准名称->别名名称的映射图*/
	private static Map<String,List<String>> std_to_aliasmap;
	
	/**别名名称->标准名称的映射图*/
	private static Map<String,String> alias_to_stdmap;
	
	//静态初始化别名服务模块
	static {
		if(!init()) {
			logger.fatal("出现致命错误，别名服务初始化失败，程序终止。");
			System.exit(-1);
		}
	}
	
	/**
	 * 静态初始化别名服务函数
	 * @return 成功true/失败false
	 */
	private static boolean init() {
		if(!initAliasList()) {
			logger.error("从数据库中读取别名列表出现错误，终止初始化别名服务。");
			return false;
		}
		if(!initStdToAliasMap()) {
			logger.error("初始化标准名至别名的地图出现错误，终止初始化别名服务。");
			return false;
		}
		if(!initAliasToStdmap()) {
			logger.error("初始化别名至标准明出现错误，终止初始化别名服务。");
			return false;
		}
		return true;
	}
	
	/**
	 * 初始化别名列表
	 * @return 成功true/失败false
	 */
	private static boolean initAliasList() {
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Exception ex) {
			logger.error("Session创建错误:"+ex);
			return false;
		}
		String hql="from AliasContent";
		Query query;
		try {
			query=session.createQuery(hql);
			aliaslist=query.list();
		} catch(Exception ex) {
			logger.error("初始化别名列表出现错误。",ex);
			return false;
		} finally {
			session.close();
		}
		return true;
	}
	
	/**
	 * 初始化标准明至别名图
	 * @return 成功true/失败false
	 */
	private static boolean initStdToAliasMap() {
		if(aliaslist==null) {
			logger.error("不能初始化标准明至别名图，尚未从数据库中提取别名表的内容。");
			return false;
		}
		std_to_aliasmap=new HashMap<String,List<String>>();
		String stdname;
		//开始遍历并初始化标准名称->别名列表
		for(AliasContent acont:aliaslist) {
			stdname=acont.getStdname();
			if(!std_to_aliasmap.containsKey(stdname))		//如果没有包含标准名称，则创建新的Key，List对。
				std_to_aliasmap.put(stdname, new ArrayList<String>());
			std_to_aliasmap.get(stdname).add(acont.getAliasname());
		}
		return true;
	}
	
	/**
	 * 初始化别名对应标准名图
	 * @return 成功true/失败false
	 */
	private static boolean initAliasToStdmap() {
		if(aliaslist==null) {
			logger.error("不能初始化别名至标准名地图，尚未从数据库中提取别名表的内容。");
			return false;
		}
		alias_to_stdmap=new HashMap<String,String>();
		String aliasname;
		//开始遍历并初始化别名至标准名图
		for(AliasContent acont:aliaslist) {
			aliasname=acont.getAliasname();
			if(alias_to_stdmap.containsKey(aliasname)) {
				logger.error("初始化别名至标准名图出现错误，一个别名同时拥有多个标准名称。");
				return false;
			}
			alias_to_stdmap.put(aliasname, acont.getStdname());
		}
		return true;
	}

	/**
	 * 重新初始化别名服务外部接口。
	 * @return 成功true/失败false
	 */
	public static boolean reInitAliasService() {
		return init();
	}
	
	/**
	 * 从别名获取标准名
	 * @param aliasname 别名
	 * @return 标准名称/没有标准名称null
	 */
	public static String getStdNameByAliasName(String aliasname) {
		if(!alias_to_stdmap.containsKey(aliasname)) {
			logger.info("不能找到别名["+aliasname+"]所对应的标准名称。");
			return null;
		} else
			return alias_to_stdmap.get(aliasname);
	}
	
	/**
	 * 从标准名获取别名列表
	 * @param stdname 标准名称
	 * @return 别名列表/没有别名列表null
	 */
	public static List<String> getAliasNameListByStdName(String stdname) {
		if(!std_to_aliasmap.containsKey(stdname)) {
			logger.info("不能找到标准名["+stdname+"]所对应的别名列表.");
			return null;
		} else
			return std_to_aliasmap.get(stdname);
	}
	
}