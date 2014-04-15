package com.logsys.production;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * 生产数据写入器--数据库写入器
 * @author lx8sn6
 */
public class ProductionDataWriterDB {

	private static final Logger logger=Logger.getLogger(ProductionDataWriterDB.class);
	
	/**
	 * 将生产信息列表写入数据库 
	 * @param pcontlist 包含生产信息的列表
	 * @return 写入数量/写入失败-1
	 */
	public static int writeDataToDB(List<ProductionContent> pcontlist) {
		if(pcontlist==null) {
			logger.error("不能将生产信息写入数据库，列表参数为空。");
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
			for(ProductionContent pcont: pcontlist) {
				session.save(pcont);
				counter++;
				if(counter%50==0) {
					session.flush();
					session.clear();
				}
			}
			session.getTransaction().commit();
			return counter;
		} catch(Throwable ex) {
			logger.error("数据库写入出现错误:",ex);
			session.close();
			return -1;
		}
	}
	
}
