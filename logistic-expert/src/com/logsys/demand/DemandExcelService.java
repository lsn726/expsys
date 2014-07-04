package com.logsys.demand;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;
import com.logsys.model.ModelContent;
import com.logsys.model.ModelService;
import com.logsys.util.DateInterval;

/**
 * Demand����ʱExcel���ķ�װ����
 * ���й�������getDemandExcelContainer()��ȡ����
 * @author lx8sn6
 */
public class DemandExcelService {

	private static final Logger logger=Logger.getLogger(DemandExcelService.class);
	
	/**����������*/
	private static final int COL_DATE=0;
	
	/**�����ͺ���*/
	private static final int COL_PN=1;
	
	/**����������*/
	private static final int COL_QTY=2;
	
	/**����Sheet������*/
	private static final String DEMAND_SHEET_NAME="UploadDemand";
	
	/**����Excel���Ķ���*/
	private Workbook demwb;
	
	/**��Excel����ȡ����������*/
	private List<DemandContent> demandlist;
	
	/**
	 * ʱ������ӳ��ͼ��PartNumber->ʱ���������mindateΪ����pn��Сֵ��maxdateΪ����pn���ֵ
	 * ��Ҫ���ڸ����������ݿ��ǰ���裬��ɾ��������������ʱ��ȷ��ɾ����������ڷ�Χ��
	 **/
	private Map<String,DateInterval> demandInterval;
	
	/**��ֹ���ⲿ��������*/
	private DemandExcelService() {}
	
	/**
	 * ����Excel���������Ĺ�������
	 * @param filepath �ļ�·��
	 * @return ��������/null
	 */
	public static DemandExcelService getDemandExcelService(String filepath) {
		if(filepath==null) {
			logger.error("�������ļ�["+filepath+"]��������Excel������:�ļ�·��Ϊ��");
			return null;
		}
		File file=new File(filepath);
		if(!file.exists()) {
			logger.error("�������ļ�["+filepath+"]��������Excel������:�ļ�������");
			return null;
		}
		DemandExcelService container=new DemandExcelService();
		try {
			container.demwb=WorkbookFactory.create(file);
		} catch(Throwable ex) {
			logger.error("�������ļ�["+filepath+"]��������Excel������,����Workbook����ʱ���ִ���:",ex);
			return null;
		}
		if(!container.processWorkBook()) {
			logger.error("���ܴ���Excel������["+filepath+"]�ļ���");
			return null;
		}
		return container;
	}
	
	/**
	 * �����ݿ����ͬ��:
	 * ��һ����������ȡ����������
	 * �ڶ�����ɾ����ǰ����������
	 * ��������д���µ���������
	 * @return �ɹ�true/ʧ��false
	 */
	public boolean syncDatabase() {
		int backupedRecordQty=backupDemandData();			//��һ����������ȡ����������
		if(backupedRecordQty<0) {
			logger.error("���������ݿ����ͬ�������ݿⱸ��ʧ�ܡ�");
		}
		int removedRecordQty=removeObsoleteDemandData();	//�ڶ�����ɾ����ǰ����������
		if(removedRecordQty<0) {
			logger.error("���������ݿ����ͬ�������������ݲ��ܱ�ɾ����");
			return false;
		}
		int writedRecordQty=writeDemandData();				//��������д���µ���������
		if(writedRecordQty<0) {
			logger.error("���������ݿ����ͬ����������д�����");
			logger.fatal("ע�⣡�����������Ѿ�ɾ����������������û��д�룡");
			return false;
		}
		logger.info("�������ݳɹ�д�����ݿ⣺��������["+backupedRecordQty+"]��,ɾ������������["+removedRecordQty+"]��������������д��["+writedRecordQty+"]��.");
		return true;
	}
	
	/**
	 * ������������
	 * ��һ������֤���׼ȷ��
	 * �ڶ�������ȡ�������
	 * ��������������ȡ������
	 * ���Ĳ����������ݼӹ�
	 * @return ����ɹ�/����ʧ��
	 */
	private boolean processWorkBook() {
		if(!verifyDataSheet()) {		//��һ������֤���׼ȷ��
			logger.error("���ܴ���������������֤���δͨ����");
			return false;
		}
		if(!extractData()) {			//�ڶ�������ȡ�������
			logger.error("���ܴ�������������δ�ܳɹ���ȡExcel���ݡ�");
			return false;
		}
		if(!analyzeData()) {			//��������������ȡ������
			logger.error("���ܴ�������������δ�ܳɹ�������ȡ�����������ݡ�");
			return false;
		}
		if(!reviseData()) {				//���Ĳ����������ݼӹ�
			logger.error("���ܴ�������������δ�ܳɹ����к������ݼӹ���");
		}
		return true;
	}
	
	/**
	 * �˲�����Sheet��׼ȷ��
	 * @return ��֤�ɹ�true/��֤ʧ��false
	 */
	private boolean verifyDataSheet() {
		Sheet sheet=demwb.getSheet(DEMAND_SHEET_NAME);
		if(sheet==null) {
			logger.error("�޷��˲�����sheet��׼ȷ�ԡ�û����Ϊ["+DEMAND_SHEET_NAME+"]��sheet���뽫�����������ݵ�sheet��������");
			return false;
		}
		return true;
	}
	
	/**
	 * ��ȡ�ļ��е�����
	 * @return true��ȡ�ɹ�/false��ȡʧ��
	 */
	private boolean extractData() {
		Sheet datasheet=demwb.getSheet(DEMAND_SHEET_NAME);
		List<DemandContent> demlist=new ArrayList<DemandContent>(); 
		DemandContent node;
		for(Row row:datasheet) {
			node=new DemandContent();
			try {
				node.setDate(row.getCell(COL_DATE).getDateCellValue());
				row.getCell(COL_PN).setCellType(Cell.CELL_TYPE_STRING);
				node.setPn(row.getCell(COL_PN).getStringCellValue());
				row.getCell(COL_QTY).setCellType(Cell.CELL_TYPE_NUMERIC);
				node.setQty(row.getCell(COL_QTY).getNumericCellValue());
			} catch(Throwable ex) {
				logger.error("���ܴӹ���������ȡ�������ݣ���ȡʱ���ִ���",ex);
				return false;
			}
			demlist.add(node);
		}
		demandlist=demlist;
		return true;
	}
	
	/**
	 * ��������е����ݣ�������ԭ�������ɱ�Ҫ���ݣ������費�ı�ԭ�����ݻ�������������
	 * 1����ÿ���ͺŵ����ʱ�����Сʱ��ȷ��
	 * @return �����ɹ�true/����ʧ��false
	 */
	private boolean analyzeData() {
		if(demandlist==null) {
			logger.error("���ܷ��������б������б���δ����ʼ����");
			return false;
		}
		Map<String,DateInterval> intervalmap=new HashMap<String,DateInterval>();
		String pn;
		Date date;
		DateInterval itval;
		for(DemandContent demcont:demandlist) {		//���������б����ÿ��pn��������Сʱ������ʱ��
			pn=demcont.getPn();
			date=demcont.getDate();
			itval=intervalmap.get(pn);
			if(itval==null) {						//���û����������򴴽����󣬽���ʼֵ����Ϊ��ǰdate
				itval=new DateInterval();
				itval.begindate=date;
				itval.enddate=date;
				intervalmap.put(pn, itval);			//�����½ڵ�����
				continue;
			}
			if(date.before(itval.begindate))		//���ʱ������������Сֵ���������Сֵ
				itval.begindate=date;
			if(date.after(itval.enddate))			//���ʱ�������������ֵ����������ֵ
				itval.enddate=date;
		}
		demandInterval=intervalmap;					//�����������
		return true;
	}
	
	/**
	 * �������ݵĺ����޶�������������ҵ����Ҫ������ԭ�����ݻ������������ݡ�
	 * ��һ�����ϲ��ظ�������ڵ�
	 * �ڶ��������·���������������
	 * @return �ɹ�true/ʧ��false
	 */
	private boolean reviseData() {
		if(demandlist==null) {
			logger.error("�������������б��б���δ����ʼ����");
			return false;
		}
		int recordMerged=mergeDuplicatedPnDateRecord();		//��һ�����ϲ��ظ�������ڵ�
		if(recordMerged<0) {
			logger.error("�������������б��ϲ��ظ��ڵ�ʱ���ִ���");
			return false;
		} else if(recordMerged>0)
			logger.info("���������е�pn-date�ظ�ֵ�Ѻϲ�������["+recordMerged+"]��.");
		if(!updateDlvFixDays()) {
			logger.error("���·�����������ʧ��!");
			return false;
		}
		return true;
	}
	
	/**
	 * �ϲ�ͬһ�ͺ�ͬһ���ڵĶ�����¼
	 * @return �ϲ�����Ŀ����
	 */
	private int mergeDuplicatedPnDateRecord() {
		if(demandlist==null) {
			logger.error("���ܺϲ��ظ�����/pn����б���δ����ʼ����");
			return -1;
		}
		Map<String,Map<Date,DemandContent>> demandmap=new HashMap<String,Map<Date,DemandContent>>();	//���ڸ���ɸ���ظ���Map<pn-Map<date,content>>����
		List<DemandContent> removelist=new ArrayList<DemandContent>();		//��Ҫɾ�����ظ��ڵ�
		Date demdate;
		String pn;
		int counter=0;
		for(DemandContent demand:demandlist) {					//��������
			pn=demand.getPn();
			if(!demandmap.containsKey(pn))						//����ͺŲ����ڣ����½�Map��д��
				demandmap.put(pn, new HashMap<Date,DemandContent>());
			demdate=demand.getDate();
			if(demandmap.get(pn).containsKey(demdate)) {		//����Ѿ��и��ͺ��¸����ڵ�dem����ϲ�����
				demandmap.get(pn).get(demdate).setQty(demandmap.get(pn).get(demdate).getQty()+demand.getQty());
				removelist.add(demand);							//������Ҫ�Ľڵ����ɾ���б�
				counter++;
			} else
				demandmap.get(pn).put((Date)demand.getDate().clone(), demand);	//д����������
		}
		for(DemandContent demand:removelist)					//����ɾ����Ҫɾ���Ľڵ�
			demandlist.remove(demand);
		return counter;
	}
	
	/**
	 * ����������б��и��·���������������
	 * @return true���³ɹ�/false����ʧ��
	 */
	private boolean updateDlvFixDays() {
		if(demandlist==null) {
			logger.error("���ܸ��·��������������ݣ��б���δ����ʼ����");
			return false;
		}
		String pn;
		ModelContent mcont;
		for(DemandContent demcont:demandlist) {
			pn=demcont.getPn();
			mcont=ModelService.getModelContentByPn(pn); //����Model����;����*-1���Ϊ����������ǰ����
			if(mcont==null) {
				logger.error("���ܸ��·��������������ݣ����ֲ���ʶ��������ͺ�["+pn+"]");
				return false;
			}
			demcont.setDlvfix(-mcont.getIntranday());
		}
		return true;
	}
	
	/**
	 * �Ƴ���ǰ���������ݣ����ݵ���demandInterval��¼��ÿ���ͺ����������������
	 * @return �Ƴ��������ݵ�����
	 */
	private int removeObsoleteDemandData() {
		if(demandInterval==null) {
			logger.error("�����Ƴ������������ݣ������������Ϊ�ա�");
			return -1;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("session�������ִ���:"+ex);
			return -1;
		}
		String hql;
		Query delquery;
		int counter=0;
		for(String pn:demandInterval.keySet()) {			//���������������ɾ����������
			hql="delete from DemandContent where pn=:pn and date>=:mindate and date<=:maxdate";
			delquery=session.createQuery(hql);
			delquery.setString("pn", pn);
			delquery.setDate("mindate", demandInterval.get(pn).begindate);
			delquery.setDate("maxdate", demandInterval.get(pn).enddate);
			counter+=delquery.executeUpdate();
		}
		session.getTransaction().commit();
		session.close();
		return counter;
	}
	
	/**
	 * ������������ȡ�����ݣ��汾Ϊ��ǰʱ��
	 * @return ���ݵ�������������
	 */
	private int backupDemandData() {
		if(demandlist==null) {
			logger.error("���ܱ����������ݣ���Ϊ�����б����Ϊ�ա�");
			return -1;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("session�������ִ���:"+ex);
			return -1;
		}
		int counter=0;
		Date version=new Date();		//ȡ�汾Ϊ��ǰ����
		for(DemandContent demcont:demandlist) {
			session.save(DemandBackupContent.createDemBkupContFromDemCont(demcont, version));
			if(counter%50==0) {
				session.flush();
				session.clear();
			}
			counter++;
		}
		session.getTransaction().commit();
		session.close();
		return counter;
	}
	
	/**
	 * д���������ݵ����ݿ�
	 * @return д��������¼����
	 */
	private int writeDemandData() {
		if(demandlist==null) {
			logger.error("����д���������ݣ���Ϊ�����б����Ϊ�ա�");
			return 0;
		}
		Session session;
		try {
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
		} catch(Throwable ex) {
			logger.error("session�������ִ���:"+ex);
			return -1;
		}
		int counter=0;
		for(DemandContent dcont:demandlist) {
			session.save(dcont);
			if(counter%100==0) {
				session.flush();
				session.clear();
			}
			counter++;
		}
		session.getTransaction().commit();
		session.close();
		return counter;
	}
	
}
