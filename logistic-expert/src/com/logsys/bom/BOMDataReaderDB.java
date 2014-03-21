package com.logsys.bom;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * BOM���ݶ�ȡ�������ݿ��ȡ��
 * @author lx8sn6
 */
public class BOMDataReaderDB {

	private static final Logger logger=Logger.getLogger(BOMDataReaderDB.class);
	
	/**
	 * ��ȡ��PN��BOM�������еĽڵ㡣ע��!:���ǻ�ȡ��PN��ϽBOM�������б����ǻ�ȡ���PN���뱾��Ľڵ�.
	 * @return �ɹ��б�/ʧ��null
	 */
	public static List<BOMContent> getBOMListByPN(String pn) {
		if(pn==null) {
			logger.error("����BOM�ڵ��б�pnΪ�ա�");
			return null;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session��������:"+ex);
			return null;
		}
		List<BOMContent> bomlist=null;
		Query query;
		String hql;
		hql="from BOMContent where pn="+pn;
		try {
			query=session.createQuery(hql);
			bomlist=query.list();
		} catch(Throwable ex) {
			logger.error("BOM��PN�б��ܻ�ȡ����ȡ���ݿ���ִ���:"+ex);
		} finally {
			session.close();
		}
		return bomlist;
	}
	
	/**
	 * ��ȡ����ָ��BOM����Ľڵ�
	 * @param level ָ����BOM���𣬱������0
	 * @return �����ڵ��б�/null
	 */
	public static List<BOMContent> getNodesByLevel(int level) {
		if(level<0) {
			logger.error("BOM������С��0.");
			return null;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:"+ex);
			return null;
		}
		Query query;
		List<BOMContent> levellist=null;
		String hql;
		hql="from BOMContent where lvl="+level;
		try {
			query=session.createQuery(hql);
			levellist=query.list();
		} catch(Throwable ex) {
			logger.error("�б��ȡ���ִ���:"+ex);
		} finally {
			session.close();
		}
		return levellist;
	}
	
	/**
	 * ��ȡָ��PN�ڵ��µ������ӽڵ㣬�����ڵ㱾��.����ȡ������BOM��
	 * @param pn ��Ҫ��ȡ�ڵ�����Ϻ�
	 * @return ������BOM�б�/ʧ��null
	 */
	public static List<BOMContent> getComplBOMByPN(String pn) {
		if(pn==null) {
			logger.error("�б�������Ϻ�ֵΪ��.");
			return null;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:"+ex);
			return null;
		}
		List<BOMContent> pnlist=getBOMListByPN(pn);			//�������BOM��pn�Ľڵ�
		if(pnlist==null) return null;
		if(pnlist.size()==0) return null;
		int pnlvl=pnlist.get(0).getLvl();
		List<BOMContent> lvllist=getNodesByLevel(pnlvl);	//�������ͬ����Ľڵ�
		if(lvllist==null) return null;
		if(lvllist.size()==0) return null;
		int beginkey=-1;
		int endkey=-1;
		for(BOMContent bcont:lvllist) {						//
			
		}
		return null;
	}
	
}
