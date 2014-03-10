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
 * ��������д������д��Database
 * @author lx8sn6
 */
public class DemandDataWriterDB {

	private static Logger logger=Logger.getLogger(DemandDataWriterDB.class);
	
	/**
	 * ����д�����ݿ�����
	 * @param demandlist ���������б�
	 * @return д�����ݿ������ 
	 */
	public static int insert(List<DemandContent> demandlist) {
		if(demandlist==null) {
			logger.error("����Ϊ�գ������б�δ��д�롣");
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
			logger.error("��������д����ִ���"+ex.toString());
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * ���¸���DemandUtil��fillEmptyDemandNodes���ɵ�Map������������Demand��Ϣ,�������Ŀû�������ݿ��У���д�����ݿ�
	 * @param demandlist ��������
	 * @return ���µ���Ŀ����
	 */
	public static int updateOrInsert(Map<String,Map<Date,DemandContent>> demandmap) {
		if(demandmap==null) {
			logger.error("����Ϊ�գ������б�δ�ܸ���");
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
			Query delquery;			//ɾ���ɼ�¼��Query
			Query insertquery;		//д���¼�¼��Query
			for(Object pn:demandmap.keySet()) {
				//����ɾ��������ָ���ͺŵļ�¼
				submap=demandmap.get(pn);
				mindate=DemandUtil.getMinDate(submap);
				maxdate=DemandUtil.getMaxDate(submap);
				delquery=session.createQuery("delete from DemandContent where pn=:pn and date>=:mindate and date<=:maxdate");
				delquery.setString("pn", (String)pn);
				delquery.setDate("mindate", mindate);
				delquery.setDate("maxdate", maxdate);
				delquery.executeUpdate();
				//��д���µļ�¼
				DemandContent tempDContent;
				for(Object date:submap.keySet()) {
					tempDContent=(DemandContent)submap.get(date);
					if(tempDContent==null) {
						logger.warn("�յ�null��������������Bug��");
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
			logger.error("�������ݸ��³��ִ���"+ex.toString());
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * ��pnset�������ĵ��ͺż�����begin��end���������ݣ���demand���ݵ�demand_backup����
	 * @param pnset ��Ҫ���ݵ��ͺż�,null��Ϊ�����ͺ�
	 * @param begin ���ݿ�ʼʱ��,nullΪ���迪ʼʱ��
	 * @param end ���ݽ���ʱ��,nullΪ�������ʱ��
	 * @param version ���ݵİ汾,null���Զ�����Ϊ��ǰʱ��
	 * @return ���ݵ���Ŀ��
	 */
	public static int backupDemandData(Set<String> pnset, Date begin, Date end, Date version) {
		if(begin.after(end)) {
			logger.error("��ʼʱ�����ڽ���ʱ�䡣");
			return -1;
		}
		List<DemandContent> orilist=DemandDataReaderDB.getDataFromDB(pnset, begin, end);	//��ȡ��Ҫ���������
		if(orilist==null) {
			logger.error("����ԭʼ���ݶ�ȡ����");
			return -1;
		}
		if(orilist.size()==0)
			return 0;
		try {
			List<DemandBackupContent> backupDemList=new ArrayList<DemandBackupContent>();
			DemandBackupContent temp;
			if(version==null) version=new Date();
			//���������ת��ΪBackup�������
			for(DemandContent demcontent:orilist) {
				if(demcontent==null) {
					logger.error("���ֿն�������Bug��");
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
			logger.error("д����ִ���:"+ex);
			return -1;
		}
	}
	
	/**
	 * ��DemandUtil.demListToMapByPn�����Ľ������Ӧ�������ݿ�������е������ͺ�/ʱ�������е����������ݣ� ���Ǳ���demmap�е����ݣ���������Ӧ���ڵ�����
	 * @param demmap DemandUtil.demListToMapByPn�����Ľ������Ӧ��������
	 * @return ���ݵ�����
	 */
	public static int backupDemandData(Map<String,Map<Date,DemandContent>> demmap) {
		if(demmap==null) {
			logger.error("����Ϊ��");
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
				logger.error("�������ݳ��ִ���,һ���������Ѿ��������,�ѱ��ݵ�����:"+bkupcounter+".����ִ��󣬱�������ֹ.");
				return -1;
			}
			bkupcounter+=singleres;
		}
		return bkupcounter;
	}
	
}
