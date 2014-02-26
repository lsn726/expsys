package com.logsys.demand;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

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
	public static int writeToDatabase(List<DemandContent> demandlist) {
		if(demandlist==null) {
			logger.error("参数为空。");
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
			logger.error("需求数据写入出现问题。"+ex.toString());
			return 0;
		}
	}
	
}
