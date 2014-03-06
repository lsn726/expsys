package com.logsys.demand;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * �����ļ����ݿ��ȡ��
 * @author lx8sn6
 */
public class DemandDataReaderDB {

	private static final Logger logger=Logger.getLogger(DemandDataReaderDB.class);
	
	/**
	 * �����ݿ�����ȡָ���ͺţ�ָ��ʱ�������ڵ���������
	 * @param pnset �ͺż���null������Ҫ�����ͺ�
	 * @param begin ��ʼʱ�䣬null����û����ʼʱ������
	 * @param end ����ʱ�䣬null����û����ʼʱ������
	 * @return �ɹ����ǰ����������ݵ��б�/ʧ����Ϊnull
	 */
	public static List<DemandContent> getDataFromDB(Set<String> pnset, Date begin, Date end) {
		if(begin!=null&&end!=null) 
			if(begin.after(end)) {
				logger.error("��ʼʱ�����ڽ���ʱ�䡣");
				return null;
			}
		if(pnset!=null)
			if(pnset.size()==0)
				return new ArrayList<DemandContent>();
		//����hql���
		String hql;
		if(pnset!=null) {							//�����ҪpnsetԼ��
			hql="from DemandContent where (";
			for(Object pn:pnset)
				hql=hql+"pn='"+(String)pn+"' or ";
			hql=hql.substring(0,hql.length()-4);	//ȥ�����һ��or
			hql+=")";
			if(begin!=null) hql+=" and date>=:begindate";
			if(end!=null) hql+=" and date<=:enddate";
		}  else {
			hql="from DemandContent";
			if(begin!=null&&end!=null)
				hql+=" where date>=:begindate and date<=:enddate";
			else if(begin!=null&&end==null)
				hql+=" where date>=:begindate";
			else if(begin==null&&end!=null)
				hql+=" where date<=:enddate";
		}
		//��ʼ��ȡ����
		List<DemandContent> demlist;
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			Query query=session.createQuery(hql);
			if(begin!=null) query.setDate("begindate", begin);
			if(end!=null) query.setDate("enddate", end);
			demlist=query.list();
			session.close();
			return demlist;
		} catch(Throwable ex) {
			logger.error("������ȡ���ִ���"+ex);
			ex.printStackTrace();
			return null;
		}
	}
	
}
