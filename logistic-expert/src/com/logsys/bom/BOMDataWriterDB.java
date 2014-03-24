package com.logsys.bom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
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
	 * @return �ɹ���������/ʧ��-1
	 */
	public static int backupComplBOMFromTopNode(String topnode,Date validto) {
		if(topnode==null) {
			logger.error("���ܱ������ݿ⣬���Ϻ�Ϊ�ա�");
			return -1;
		}
		BOM_PN_STATUS status=BOMUtil.getPNStatusInBOMDB(topnode);
		if(status==BOM_PN_STATUS.ERROR||status==BOM_PN_STATUS.NOTEXIST) {
			logger.error("���ܱ�������,���ִ������ָ������pn�����ڡ�");
			return -1;
		}
		if(status!=BOM_PN_STATUS.TOPNODE&&status!=BOM_PN_STATUS.MIXNODE) {	//ȷ���Ƿ��Ƕ����ڵ�
			logger.error("���ܱ�������BOM�б�,PN"+topnode+"���Ƕ����ڵ�");
			return -1;
		}
		return backupComplBOM(topnode,validto);
	}
	
	/**
	 * ����ָ��pn��������BOM�б�����ж���ظ���ѡȡ������С��BOM��¼��
	 * @param pn ָ������
	 * @param validto ��Ч����
	 * @return ������Ŀ����
	 */
	public static int backupComplBOM(String pn,Date validto) {
		if(pn==null) {
			logger.error("���ܱ������ݿ⣬���Ϻ�Ϊ�ա�");
			return -1;
		}
		List<BOMContent> srclist=BOMDataReaderDB.getComplBOMByPN(pn);		//��ȡԴ�����б�
		if(srclist==null) return -1;
		if(srclist.size()==0) return 0;
		if(validto==null) validto=new Date();
		List<BOMBackupContent> targetlist=new ArrayList<BOMBackupContent>();
		BOMBackupContent temp;
		for(BOMContent bcont:srclist)	{									//ת��ΪBOMBackupContent�б�
			temp=new BOMBackupContent();
			temp.setLvl(bcont.getLvl());
			temp.setPn(bcont.getPn());
			temp.setQty(bcont.getQty());
			temp.setUnit(bcont.getUnit());
			temp.setVersion(bcont.getVersion());
			temp.setValidto((Date)validto.clone());
			targetlist.add(temp);
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("����Session����:"+ex);
			return -1;
		}
		int counter=-1;
		try {
			for(BOMBackupContent bkupcont:targetlist) {
				session.save(bkupcont);
				counter++;
				if(counter%50==0) {
					session.flush();
					session.clear();
				}
			}
			session.getTransaction().commit();
		} catch(Throwable ex) {
			logger.error("д�뱾�����ݱ���ִ���.");
			session.close();
		}
		return counter;
	}
	
}
