package com.logsys.material;

import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.logsys.demand.DemandProcess;
import com.logsys.hibernate.HibernateSessionFactory;

/**
 * 物料信息写入器，写入数据库
 * @author lx8sn6
 */
public class MaterialDataWriterDB {

	private static Logger logger=Logger.getLogger(DemandProcess.class);
	
	/**
	 * 将Map<String,MaterialContent>中包含的物料信息写入物料信息表
	 * @param matmap 包含Map<物料号,物料信息>的图
	 * @return 写入的物料数量条数/-1失败
	 */
	public static int insert(Map<String,MaterialContent> matmap) {
		if(matmap==null) {
			logger.error("参数为空。");
			return -1;
		}
		if(matmap.size()==0) return 0;
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
			MaterialContent matcontent;
			int counter=0;
			for(String pn:matmap.keySet()) {
				matcontent=matmap.get(pn);
				if(matcontent==null) {
					logger.error("出现null对象,请检查程序bug.");
					session.close();
					return -1;
				}
				session.save(matcontent);
				if(counter%50==0) {
					session.flush();
					session.clear();
				}
				counter++;
			}
			session.getTransaction().commit();
			return counter;
		} catch(Throwable ex) {
			logger.error("物料信息写入错误:"+ex);
			return -1;
		}
	}


}
