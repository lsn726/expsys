package com.logsys.production;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.setting.pd.bwi.BWIPLInfo.ProdLine;
import com.logsys.util.DateInterval;

/**
 * 生产数据读取器
 * @author ShaonanLi
 */
public class ProductionDataReaderDB {

	private static final Logger logger=Logger.getLogger(ProductionDataReaderDB.class);
	
	/**
	 * 从数据库中提取prodline生产线，在interval区间之间的产出数据
	 * @param prodline 指定的生产线, 如果为null则不限制生产线
	 * @param interval 指定生产数据的区间,begindate为null则默认为不限制起始时间,enddate为null则默认为昨天
	 * @return 返回取得的生产数据列表/null失败
	 */
	public static List<ProductionContent> getProductionDataFromDB(ProdLine prodline, DateInterval interval) {
		if(interval==null) {
			logger.error("不能从数据库中读取生产数据，参数为空。");
			return null;
		}
		String prdlinename=null;
		if(prodline!=null) {										//获取标准的生产线名称
			prdlinename=BWIPLInfo.getStdNameByLineEnum(prodline);
			if(prdlinename==null) {
				logger.error("不能从数据库中读取生产数据，生产线枚举参数不能获取相应的生产线名称。");
				return null;
			}
		}
		Calendar begindate=Calendar.getInstance();
		Calendar enddate=Calendar.getInstance();
		if(interval.begindate==null)			//初始化起始时间
			begindate=null;
		else
			begindate.setTime(interval.begindate);
		if(interval.enddate==null)				//初始化结束时间
			enddate.setTimeInMillis(enddate.getTimeInMillis()-24*3600*1000);
		else
			enddate.setTime(interval.enddate);
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("创建Session错误:",ex);
			return null;
		}
		String hql=" from ProductionContent where date<=:enddate";	//HQL语句
		if(begindate!=null) hql+=" and date>=:begindate";			//设置起始时间
		if(prodline!=null) hql+=" and workcenter=:prdlinename";		//设置生产线
		List<ProductionContent> prodlist;
		try {
			Query query=session.createQuery(hql);
			query.setDate("enddate", enddate.getTime());
			if(begindate!=null) query.setDate("begindate", begindate.getTime());
			if(prodline!=null) query.setString("prdlinename", prdlinename);
			prodlist=query.list();
			session.close();
			return prodlist;
		} catch(Throwable ex) {
			logger.error("不能从数据库中读取生产数据，读取数据的过程出现错误:",ex);
			session.close();
			return null;
		}
	}
	
}
