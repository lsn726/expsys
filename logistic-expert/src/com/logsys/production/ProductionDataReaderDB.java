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
 * �������ݶ�ȡ��
 * @author ShaonanLi
 */
public class ProductionDataReaderDB {

	private static final Logger logger=Logger.getLogger(ProductionDataReaderDB.class);
	
	/**
	 * �����ݿ�����ȡprodline�����ߣ���interval����֮��Ĳ�������
	 * @param prodline ָ����������, ���Ϊnull������������
	 * @param interval ָ���������ݵ�����,begindateΪnull��Ĭ��Ϊ��������ʼʱ��,enddateΪnull��Ĭ��Ϊ����
	 * @return ����ȡ�õ����������б�/nullʧ��
	 */
	public static List<ProductionContent> getProductionDataFromDB(ProdLine prodline, DateInterval interval) {
		if(interval==null) {
			logger.error("���ܴ����ݿ��ж�ȡ�������ݣ�����Ϊ�ա�");
			return null;
		}
		String prdlinename=null;
		if(prodline!=null) {										//��ȡ��׼������������
			prdlinename=BWIPLInfo.getStdNameByLineEnum(prodline);
			if(prdlinename==null) {
				logger.error("���ܴ����ݿ��ж�ȡ�������ݣ�������ö�ٲ������ܻ�ȡ��Ӧ�����������ơ�");
				return null;
			}
		}
		Calendar begindate=Calendar.getInstance();
		Calendar enddate=Calendar.getInstance();
		if(interval.begindate==null)			//��ʼ����ʼʱ��
			begindate=null;
		else
			begindate.setTime(interval.begindate);
		if(interval.enddate==null)				//��ʼ������ʱ��
			enddate.setTimeInMillis(enddate.getTimeInMillis()-24*3600*1000);
		else
			enddate.setTime(interval.enddate);
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:",ex);
			return null;
		}
		String hql=" from ProductionContent where date<=:enddate";	//HQL���
		if(begindate!=null) hql+=" and date>=:begindate";			//������ʼʱ��
		if(prodline!=null) hql+=" and workcenter=:prdlinename";		//����������
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
			logger.error("���ܴ����ݿ��ж�ȡ�������ݣ���ȡ���ݵĹ��̳��ִ���:",ex);
			session.close();
			return null;
		}
	}
	
}
