package com.logsys.prodplan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;
import com.logsys.util.DateInterval;

/**
 * 生产计划写入器，数据库写入器
 * @author lx8sn6
 */
public class ProdplanDataWriterDB {

	private static final Logger logger=Logger.getLogger(ProdplanDataWriterDB.class);
	
	/**
	 * 将pplist列表写入数据库
	 * @param pplist 生产计划列表
	 * @return 写入数据库的数据
	 */
	public static int writeToDB(List<ProdplanContent> pplist) {
		if(pplist==null) {
			logger.error("不能将生产计划列表写入数据库,pplist参数为空.");
			return -1;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("创建Session错误:",ex);
			return -1;
		}
		int counter=0;
		try {
			for(ProdplanContent ppcont:pplist) {
				session.save(ppcont);
				if(counter%50==0) {
					session.flush();
					session.clear();
				}
				counter++;
			}
			session.getTransaction().commit();
			return counter;
		} catch(Throwable ex) {
			logger.error("数据库写入出现错误：",ex);
			session.close();
			return -1;
		}
	}
	
	/**
	 * 备份制定区间内的生产计划
	 * @param dinterval 时间区间
	 * @param version 生产计划版本,null则为当前时间
	 * @return 成功备份条数/-1失败
	 */
	public static int backupProdPlan(DateInterval dinterval, Date version) {
		if(dinterval==null) {
			logger.error("不能备份生产计划,日期区间参数为空.");
			return -1;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("创建Session错误:"+ex);
			return -1;
		}
		Query query;
		List<ProdplanContent> plist=new ArrayList<ProdplanContent>();
		String hql="from ProdplanContent where date>=:begindate and date<=:enddate";	//限定区间
		try {														//读取原区间数据
			query=session.createQuery(hql);
			query.setDate("begindate", dinterval.begindate);
			query.setDate("enddate", dinterval.enddate);
			plist=query.list();
		} catch(Throwable ex) {
			logger.error("不能备份数据,原数据读取错误.",ex);
			return -1;
		}
		if(version==null) version=new Date();
		int counter=0;
		try {
			for(ProdplanContent ppcont: plist) {						//写入备份数据
				session.save(new ProdplanBackupContent(ppcont,version));
				if(counter%50==0) {
					session.flush();
					session.clear();
				}
				counter++;
			}
			session.getTransaction().commit();
			return counter;
		} catch(Throwable ex) {
			logger.error("不能写入备份数据，写入备份数据库错误。",ex);
			session.close();
			return -1;
		}
	}
	
	/**
	 * 删除规定区间内的生产计划 
	 * @param dinterval 指定的时间区间
	 * @param resetPlanBeyondScope 是否删除指定区间以外未来的计划
	 * @return 删除的数量/-1失败
	 */
	public static int deleteProdplan(DateInterval dinterval, boolean resetPlanBeyondScope) {
		if(dinterval==null) {
			logger.error("不能删除计划.区间参数为空.");
			return -1;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("创建Session错误:",ex);
			return -1;
		}
		Query query;
		String hql="delete from ProdplanContent where date>=:begindate";
		if(!resetPlanBeyondScope)
			hql+=" and date<=:enddate";
		try {
			query=session.createQuery(hql);
			query.setDate("begindate", dinterval.begindate);
			if(!resetPlanBeyondScope) query.setDate("enddate", dinterval.enddate);
			int deletedqty=query.executeUpdate();
			session.close();
			return deletedqty;
		} catch(Throwable ex) {
			logger.error("不能删除计划记录：",ex);
			session.close();
			return -1;
		}
	}
	
}
