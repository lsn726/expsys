package com.logsys.bom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.logsys.bom.BOMUtil.BOM_PN_STATUS;
import com.logsys.hibernate.HibernateSessionFactory;

/**
 * BOM信息数据写入器，数据库写入器
 * @author lx8sn6
 */
public class BOMDataWriterDB {

	private static Logger logger=Logger.getLogger(BOMDataWriterDB.class);
	
	/**
	 * 将BOM中的信息写入BOM列表
	 * @param bomlist 包含BOM信息的列表 
	 * @param version BOM版本,null则为当前时间
	 * @return 写入BOM的条数
	 */
	public static int writeDataToDB(List<BOMContent> bomlist,Date version) {
		if(bomlist==null) {
			logger.error("错误，参数为空。");
			return -1;
		}
		if(bomlist.size()==0)
			return 0;
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.getTransaction().begin();
		} catch(Throwable ex) {
			logger.error("创建Session错误:"+ex);
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
			logger.error("数据库写入错误："+ex);
			return -1;
		}
	}
	
	/**
	 * 将在BOM表中的pn下BOM备份至BOM_Backup表，PN需要满足条件，必须是顶级节点(Lvl为0)。
	 * @param topnode PartNumber号码，pn本身必须为顶级节点
	 * @param validto 备份BOM时 ，将所备份BOM的到期截止日期记载为什么。
	 * @return 成功备份条数/失败-1
	 */
	public static int backupComplBOMFromTopNode(String topnode,Date validto) {
		if(topnode==null) {
			logger.error("不能备份数据库，物料号为空。");
			return -1;
		}
		BOM_PN_STATUS status=BOMUtil.getPNStatusInBOMDB(topnode);
		if(status==BOM_PN_STATUS.ERROR||status==BOM_PN_STATUS.NOTEXIST) {
			logger.error("不能备份数据,出现错误或者指定备份pn不存在。");
			return -1;
		}
		if(status!=BOM_PN_STATUS.TOPNODE&&status!=BOM_PN_STATUS.MIXNODE) {	//确定是否是顶级节点
			logger.error("不能备份数据BOM列表,PN"+topnode+"不是顶级节点");
			return -1;
		}
		return backupComplBOM(topnode,validto);
	}
	
	/**
	 * 备份指定pn下完整的BOM列表，如果有多次重复，选取主键最小的BOM记录。
	 * @param pn 指定物料
	 * @param validto 有效期至
	 * @return 备份条目数量
	 */
	public static int backupComplBOM(String pn,Date validto) {
		if(pn==null) {
			logger.error("不能备份数据库，物料号为空。");
			return -1;
		}
		List<BOMContent> srclist=BOMDataReaderDB.getComplBOMByPN(pn);		//获取源完整列表
		if(srclist==null) return -1;
		if(srclist.size()==0) return 0;
		if(validto==null) validto=new Date();
		List<BOMBackupContent> targetlist=new ArrayList<BOMBackupContent>();
		BOMBackupContent temp;
		for(BOMContent bcont:srclist)	{									//转换为BOMBackupContent列表
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
			logger.error("创建Session错误:"+ex);
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
			logger.error("写入本分数据表出现错误.");
			session.close();
		}
		return counter;
	}
	
}
