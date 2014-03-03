package com.logsys.demand;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 更新根据DemandUtil的fillEmptyDemandNodes生成的Map对象所包含的Demand信息,如果该条目没有在数据库中，则写入数据库
	 * @param demandlist 需求数据
	 * @return 更新的条目数量
	 */
	public static int updateOrInsert(Map<String,Map<Date,DemandContent>> demandmap) {
		if(demandmap==null) {
			logger.error("参数为空，需求列表未能更新");
			return 0;
		}
		if(demandmap.size()==0) return 0;
		try {
			Session session=HibernateSessionFactory.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			int counter=0;
			Map<Date,DemandContent> submap;
			Criteria criteria;
			Date mindate;
			Date maxdate;
			Query delquery;			//删除旧记录的Query
			Query insertquery;		//写入新纪录的Query
			for(Object pn:demandmap.keySet()) {
				//首先删除区间内指定型号的记录
				submap=demandmap.get(pn);
				mindate=DemandUtil.getMinDate(submap);
				maxdate=DemandUtil.getMaxDate(submap);
				delquery=session.createQuery("delete from DemandContent where pn=:pn and date>=:mindate and date<=:maxdate");
				delquery.setString("pn", (String)pn);
				delquery.setDate("mindate", mindate);
				delquery.setDate("maxdate", maxdate);
				delquery.executeUpdate();
				//再写入新的记录
				DemandContent tempDContent;
				for(Object date:submap.keySet()) {
					tempDContent=(DemandContent)submap.get(date);
					if(tempDContent==null) {
						logger.warn("收到null需求对象，请检查程序Bug。");
						continue;
					}
					session.save(tempDContent);
					counter++;
					if(counter%50==0) {
						session.flush();
						session.clear();
					}
				}
				session.flush();
				session.clear();
			}
			session.getTransaction().commit();
			return counter;
		} catch(Throwable ex) {
			logger.error("需求数据更新出现错误："+ex.toString());
			ex.printStackTrace();
			return 0;
		}
	}
	
}
