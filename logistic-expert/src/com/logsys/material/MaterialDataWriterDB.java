package com.logsys.material;

import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.logsys.demand.DemandProcess;
import com.logsys.hibernate.HibernateSessionFactory;

/**
 * ������Ϣд������д�����ݿ�
 * @author lx8sn6
 */
public class MaterialDataWriterDB {

	private static Logger logger=Logger.getLogger(DemandProcess.class);
	
	/**
	 * ��Map<String,MaterialContent>�а�����������Ϣд��������Ϣ��
	 * @param matmap ����Map<���Ϻ�,������Ϣ>��ͼ
	 * @return д���������������/-1ʧ��
	 */
	public static int insert(Map<String,MaterialContent> matmap) {
		if(matmap==null) {
			logger.error("����Ϊ�ա�");
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
					logger.error("����null����,�������bug.");
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
			logger.error("������Ϣд�����:"+ex);
			return -1;
		}
	}


}
