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

import com.logsys.model.ModelContent;
import com.logsys.model.ModelDataReaderDB;
import com.logsys.model.ModelUtil;
import com.logsys.util.DateInterval;

/**
 * Demand����ʱExcel���ķ�װ����
 * ���й�������getDemandExcelContainer()��ȡ����
 * @author lx8sn6
 */
public class DemandExcelContainer {

	private static final Logger logger=Logger.getLogger(DemandExcelContainer.class);
	
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
	private DemandExcelContainer() {}
	
	/**
	 * ����Excel���������Ĺ�������
	 * @param filepath �ļ�·��
	 * @return ��������/null
	 */
	public static DemandExcelContainer getDemandExcelContainer(String filepath) {
		if(filepath==null) {
			logger.error("�������ļ�["+filepath+"]��������Excel������:�ļ�·��Ϊ��");
			return null;
		}
		File file=new File(filepath);
		if(!file.exists()) {
			logger.error("�������ļ�["+filepath+"]��������Excel������:�ļ�������");
			return null;
		}
		DemandExcelContainer container=new DemandExcelContainer();
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
	 * ������������
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
	 * @return �ɹ�true/ʧ��false
	 */
	private boolean reviseData() {
		return true;
	}
	
}
