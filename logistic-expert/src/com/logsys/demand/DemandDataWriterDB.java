package com.logsys.demand;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * 需求数据写入器，写入Database
 * @author lx8sn6
 */
public class DemandDataWriterDB {

	private static Logger logger=Logger.getLogger(DemandDataWriterDB.class);
	
	/**
	 * 批量写入数据库数据
	 * @param demandlist 需求数据列表
	 * @return 写入数据库的数量 
	 */
	public static int insert(List<DemandContent> demandlist) {
		if(demandlist==null) {
			logger.error("参数为空，需求列表未能写入。");
			return 0;
		}
		if(demandlist.size()==0) return 0;
		try {
			Session session=HibernateSessionFactory.getSession();
			session.beginTransaction();
			int counter=0;
			for(DemandContent dcontent:demandlist) {
				session.save(dcontent);
				if(counter%100==0) {
					session.flush();
					session.clear();
				}
				counter++;
			}
			session.getTransaction().commit();
			return counter;
		} catch(Throwable ex) {
			logger.error("需求数据写入出现错误："+ex.toString());
			return 0;
		}
	}
	
	/**
	 * 更新数据库需求表,如果该条目没有在数据库中，则写入数据库
	 * @param demandlist 需求数据
	 * @return 更新的条目数量
	 */
	public static int updateOrInsert(List<DemandContent> demandlist) {
		if(demandlist==null) {
			logger.error("参数为空，需求列表未能更新");
			return 0;
		}
		if(demandlist.size()==0) return 0;
		try {
			Session session=HibernateSessionFactory.getSessionFactory().getCurrentSession();
			Criteria criteria;
			List<DemandContent> result;
			DemandContent temp;
			for(DemandContent dcontent:demandlist) {
				criteria=session.createCriteria("DemandContent")
					.add(Restrictions.eq("pn", dcontent.getPn()))
					.add(Restrictions.eq("date", dcontent.getDate()));
				result=criteria.list();
				if(result.size()==0) continue;
				if(result.size()!=1) {
					logger.error("日期:"+dcontent.getDate()+",pn:"+dcontent.getPn()+",有多个记录存在.数据库错误,违反pn-date独立约束.");
					return 0;
				}
				temp=result.get(0);
				temp.setQty(dcontent.getQty());
				temp.setDlvfix(dcontent.getDlvfix());
				criteria=session.createCriteria("DemandContent");
				
			}
		} catch(Throwable ex) {
			logger.error("需求数据更新出现错误："+ex.toString());
			return 0;
		}
	}
	
}
