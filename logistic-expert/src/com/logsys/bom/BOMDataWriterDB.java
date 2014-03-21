package com.logsys.bom;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
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
	 * @return 成功true/失败false
	 */
	public static boolean backupBOMFromTopNode(String topnode,Date validto) {
		if(topnode==null) {
			logger.error("不能备份数据库，物料号为空。");
			return false;
		}
		BOM_PN_STATUS status=BOMUtil.getPNStatusInBOMDB(topnode);
		if(status!=BOM_PN_STATUS.TOPNODE&&status!=BOM_PN_STATUS.MIXNODE) {	//确定是否是顶级节点
			logger.error("不能备份数据BOM列表,PN"+topnode+"不是顶级节点");
			return false;
		}
		//获取该节点的完整BOM
		//将全部节点对象转换为BOMBackupContent
		//写入所有对象
		return false;
	}
	
}
