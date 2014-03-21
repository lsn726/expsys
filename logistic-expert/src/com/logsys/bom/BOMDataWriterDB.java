package com.logsys.bom;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.bom.BOMUtil.BOM_PN_STATUS;
import com.logsys.hibernate.HibernateSessionFactory;

/**
 * BOM��Ϣ����д���������ݿ�д����
 * @author lx8sn6
 */
public class BOMDataWriterDB {

	private static Logger logger=Logger.getLogger(BOMDataWriterDB.class);
	
	/**
	 * ��BOM�е���Ϣд��BOM�б�
	 * @param bomlist ����BOM��Ϣ���б� 
	 * @param version BOM�汾,null��Ϊ��ǰʱ��
	 * @return д��BOM������
	 */
	public static int writeDataToDB(List<BOMContent> bomlist,Date version) {
		if(bomlist==null) {
			logger.error("���󣬲���Ϊ�ա�");
			return -1;
		}
		if(bomlist.size()==0)
			return 0;
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:"+ex);
			return -1;
		}
		if(version==null) version=new Date();
		int counter=0;
		try {
			for(BOMContent bcont:bomlist) {
				counter++;
				bcont.setVersion(version);
				session.save(bcont);
				if(counter%50==0) {
					session.flush();
					session.clear();
				}
			}
			session.getTransaction().commit();
			return counter;
		} catch(Throwable ex) {
			logger.error("���ݿ�д�����"+ex);
			return -1;
		}
	}
	
	/**
	 * ����BOM���е�pn��BOM������BOM_Backup��PN��Ҫ���������������Ƕ����ڵ�(LvlΪ0)��
	 * @param topnode PartNumber���룬pn�������Ϊ�����ڵ�
	 * @param validto ����BOMʱ ����������BOM�ĵ��ڽ�ֹ���ڼ���Ϊʲô��
	 * @return �ɹ�true/ʧ��false
	 */
	public static boolean backupBOMFromTopNode(String topnode,Date validto) {
		if(topnode==null) {
			logger.error("���ܱ������ݿ⣬���Ϻ�Ϊ�ա�");
			return false;
		}
		BOM_PN_STATUS status=BOMUtil.getPNStatusInBOMDB(topnode);
		if(status!=BOM_PN_STATUS.TOPNODE&&status!=BOM_PN_STATUS.MIXNODE) {	//ȷ���Ƿ��Ƕ����ڵ�
			logger.error("���ܱ�������BOM�б�,PN"+topnode+"���Ƕ����ڵ�");
			return false;
		}
		//��ȡ�ýڵ������BOM
		//��ȫ���ڵ����ת��ΪBOMBackupContent
		//д�����ж���
		return false;
	}
	
}
