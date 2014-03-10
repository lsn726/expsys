package com.logsys.demand;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
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
						session.getTransaction().rollback();
						return -1;
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
	
	/**
	 * 将pnset所包含的的型号集，从begin到end的需求数据，从demand表备份到demand_backup表中
	 * @param pnset 需要备份的型号集,null则为所有型号
	 * @param begin 备份开始时间,null为不设开始时间
	 * @param end 备份结束时间,null为不设结束时间
	 * @param version 备份的版本,null则自动设置为当前时间
	 * @return 备份的条目数
	 */
	public static int backupDemandData(Set<String> pnset, Date begin, Date end, Date version) {
		if(begin.after(end)) {
			logger.error("起始时间晚于结束时间。");
			return -1;
		}
		List<DemandContent> orilist=DemandDataReaderDB.getDataFromDB(pnset, begin, end);	//读取需要处理的数据
		if(orilist==null) {
			logger.error("备份原始数据读取错误。");
			return -1;
		}
		if(orilist.size()==0)
			return 0;
		try {
			List<DemandBackupContent> backupDemList=new ArrayList<DemandBackupContent>();
			DemandBackupContent temp;
			if(version==null) version=new Date();
			//将需求对象转换为Backup需求对象
			for(DemandContent demcontent:orilist) {
				if(demcontent==null) {
					logger.error("出现空对象，请检查Bug。");
					return -1;
				}
				temp=new DemandBackupContent();
				temp.setDate(demcontent.getDate());
				temp.setDlvfix(demcontent.getDlvfix());
				temp.setPn(demcontent.getPn());
				temp.setQty(demcontent.getQty());
				temp.setVersion((Date)version.clone());
				backupDemList.add(temp);
			}
			Session session=HibernateSessionFactory.getSession();
			session.beginTransaction();
			int counter=0;
			for(DemandBackupContent backupDem:backupDemList) {
				session.save(backupDem);
				if(counter%50==0) {
					session.flush();
					session.clear();
				}
				counter++;
			}
			session.getTransaction().commit();
			return counter;
		} catch(Throwable ex) {
			logger.error("写入出现错误:"+ex);
			return -1;
		}
	}
	
	/**
	 * 将DemandUtil.demListToMapByPn处理后的结果所对应的在数据库需求表中的需求型号/时间区间中的数据做备份， 不是备份demmap中的数据，而是所对应日期的数据
	 * @param demmap DemandUtil.demListToMapByPn处理后的结果所对应的在数据
	 * @return 备份的条数
	 */
	public static int backupDemandData(Map<String,Map<Date,DemandContent>> demmap) {
		if(demmap==null) {
			logger.error("参数为空");
			return -1;
		}
		if(demmap.size()==0) return 0;
		Set<String> pnset;
		int bkupcounter=0;
		int singleres=0;
		Date version=new Date();
		for(String model:demmap.keySet()) {
			pnset=new HashSet<String>();
			pnset.add(model);
			singleres=backupDemandData(pnset,DemandUtil.getMinDate(demmap.get(model)),DemandUtil.getMaxDate(demmap.get(model)),version);
			if(singleres<0) {
				logger.error("备份数据出现错误,一部分数据已经备份完毕,已备份的数量:"+bkupcounter+".因出现错误，备份已终止.");
				return -1;
			}
			bkupcounter+=singleres;
		}
		return bkupcounter;
	}
	
}
