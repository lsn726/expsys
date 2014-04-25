package com.logsys.material;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;

/**
 * �������ݶ�ȡ��--���ݿ��ȡ��
 * @author lx8sn6
 */
public class MaterialDataReaderDB {

	private static final Logger logger=Logger.getLogger(MaterialDataReaderDB.class);
	
	/**
	 * �����ݿ��ȡ�������ݣ�ɸѡ����Ϊpnset�к��е�part number����
	 * @param pnset ���Ϻ��뼯��,nullΪ���������Ϻ���
	 * @param makebuy ������"make"�����⹺"buy",��������������ǻ���null����������
	 * @param inuse �����Ƿ�����ʹ��
	 * @param orderedlist �Ƿ���Ҫ�����Ͻ�������
	 * @return List<������Ϣ>�б�/null����ʧ��
	 */
	public static List<MaterialContent> getDataFromDB(Set<String> pnset, String makebuy, boolean inuse, boolean orderedlist) {
		if(pnset!=null)
			if(pnset.size()==1) return new ArrayList<MaterialContent>();
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("Session��������"+ex);
			return null;
		}
		String hql="from MaterialContent where 1=1";
		if(pnset!=null) {							//�����pnset����
			hql+=" and (";
			for(String pn:pnset)
				hql+="pn='"+pn+"' or ";
			hql=hql.substring(0,hql.length()-" or ".length());
			hql+=")";
		}
		if(inuse) hql+=" and inuse=1";				//������������Ƿ�����ʹ��
		if(makebuy!=null)							//���makebuy������
			if(makebuy.equals("make")||makebuy.equals("buy"))
				hql+=(" and makebuy='"+makebuy+"'");//���makebuy������
		if(orderedlist)								//�Ƿ���Ҫ��������
			hql+=" order by description,makebuy,buyer,provider,pn";
		List<MaterialContent> matlist;
		Query query;
		try {
			query=session.createQuery(hql);
			matlist=query.list();
		} catch(Throwable ex) {
			logger.error("���ݿ����ϲ��ҳ��ִ���.",ex);
			return null;
		} finally {
			session.close();
		}
		return matlist;
	}
	
}
