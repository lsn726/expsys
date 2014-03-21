package com.logsys.bom;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * BOM数据读取器，数据库读取器
 * @author lx8sn6
 */
public class BOMDataReaderDB {

	private static final Logger logger=Logger.getLogger(BOMDataReaderDB.class);
	
	/**
	 * 获取该PN在BOM表中所有的节点。注意!:不是获取该PN下辖BOM的完整列表，而是获取这个PN号码本身的节点.
	 * @return 成功列表/失败null
	 */
	public static List<BOMContent> getBOMListByPN(String pn) {
		if(pn==null) {
			logger.error("不能BOM节点列表，pn为空。");
			return null;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session创建错误:"+ex);
			return null;
		}
		List<BOMContent> bomlist=null;
		Query query;
		String hql;
		hql="from BOMContent where pn="+pn;
		try {
			query=session.createQuery(hql);
			bomlist=query.list();
		} catch(Throwable ex) {
			logger.error("BOM的PN列表不能获取，读取数据库出现错误:"+ex);
		} finally {
			session.close();
		}
		return bomlist;
	}
	
	/**
	 * 获取所有指定BOM级别的节点
	 * @param level 指定的BOM级别，必须大于0
	 * @return 顶级节点列表/null
	 */
	public static List<BOMContent> getNodesByLevel(int level) {
		if(level<0) {
			logger.error("BOM级别不能小于0.");
			return null;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("创建Session错误:"+ex);
			return null;
		}
		Query query;
		List<BOMContent> levellist=null;
		String hql;
		hql="from BOMContent where lvl="+level;
		try {
			query=session.createQuery(hql);
			levellist=query.list();
		} catch(Throwable ex) {
			logger.error("列表读取出现错误:"+ex);
		} finally {
			session.close();
		}
		return levellist;
	}
	
	/**
	 * 获取指定PN节点下的所有子节点，包括节点本身.即获取完整的BOM。
	 * @param pn 需要获取节点的物料号
	 * @return 完整的BOM列表/失败null
	 */
	public static List<BOMContent> getComplBOMByPN(String pn) {
		if(pn==null) {
			logger.error("列表错误，物料号值为空.");
			return null;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("创建Session错误:"+ex);
			return null;
		}
		List<BOMContent> pnlist=getBOMListByPN(pn);			//获得所有BOM中pn的节点
		if(pnlist==null) return null;
		if(pnlist.size()==0) return null;
		int pnlvl=pnlist.get(0).getLvl();
		List<BOMContent> lvllist=getNodesByLevel(pnlvl);	//获得所有同级别的节点
		if(lvllist==null) return null;
		if(lvllist.size()==0) return null;
		int beginkey=-1;
		int endkey=-1;
		for(BOMContent bcont:lvllist) {						//
			
		}
		return null;
	}
	
}
