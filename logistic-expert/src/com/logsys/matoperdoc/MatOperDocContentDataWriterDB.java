package com.logsys.matoperdoc;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * 物料操作数据写入器--数据库写入器
 * @author ShaonanLi
 */
public class MatOperDocContentDataWriterDB {

	private static final Logger logger=Logger.getLogger(MatOperDocContentDataWriterDB.class);
	
	/**
	 * 将pplist列表写入数据库
	 * @param pplist 生产计划列表
	 * @return 写入数据库的数据
	 */
	public static int writeToDB(List<MatOperDocContent> doclist) {
		if(doclist==null) {
			logger.error("不能将物料操作记录写入数据库,列表参数为空.");
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
			for(MatOperDocContent doccont:doclist) {
				//System.out.println(doccont);
				session.save(doccont);
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
	
}
