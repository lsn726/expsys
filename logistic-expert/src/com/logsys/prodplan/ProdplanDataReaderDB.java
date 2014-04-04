package com.logsys.prodplan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;
import com.logsys.util.DateInterval;

/**
 * �����ƻ����ݶ�ȡ��
 * @author lx8sn6
 */
public class ProdplanDataReaderDB {

	private static final Logger logger=Logger.getLogger(ProdplanDataReaderDB.class);
	
	/**
	 * ��ȡ�����ƻ���
	 * @param dinterval �����ƻ������䡣���beginΪnull,��Ĭ������Ϊ����һ�����endΪnull,��Ĭ�ϲ��������ޡ� 
	 * @return ��ȡ�������ƻ���Ϣ�б�
	 */
	public static List<ProdplanContent> getProdplan(DateInterval dinterval) {
		if(dinterval==null) {
			logger.error("���ܻ�ȡ�����ƻ�������Ϊ�ա�");
			return null;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:",ex);
			return null;
		}
		Query query;
		String hql="from ProdplanContent where 1=1";
		if(dinterval.begindate==null) {					//���û�п�ʼʱ�䣬������Ĭ��Ϊ����һ
			Calendar cal=Calendar.getInstance();
			int week=cal.get(Calendar.WEEK_OF_YEAR)+1;
			int year=cal.get(Calendar.YEAR);
			cal.setWeekDate(year, week, cal.MONDAY);
			hql+=" and date>=:begindate";
			dinterval.begindate=new Date(year+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(cal.DAY_OF_MONTH));
		}
		if(dinterval.enddate!=null) hql+=" and date<=:enddate";			//�����enddate�����������
		List<ProdplanContent> pplist=new ArrayList<ProdplanContent>();	//�����ƻ��б�
		try {											//��ʼ��ȡ�б�
			query=session.createQuery(hql);
			query.setDate("begindate", dinterval.begindate);
			if(dinterval.enddate!=null) query.setDate("enddate", dinterval.enddate);
			pplist=query.list();
			session.close();
			return pplist;
		} catch(Throwable ex) {
			logger.error("���ݿ��ȡ���ִ���:",ex);
			session.close();
			return null;
		}
	}

}
