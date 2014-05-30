package com.logsys.demand;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Demand����ʱExcel���ķ�װ����
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
	private List<DemandContent> extractlist;
	
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
		if(file.isDirectory()) {
			logger.error("�������ļ�["+filepath+"]��������Excel������:�ļ�·�����ļ��� ");
			return null;
		}
		DemandExcelContainer container=new DemandExcelContainer();
		try {
			container.demwb=WorkbookFactory.create(file);
		} catch(Throwable ex) {
			logger.error("�������ļ�["+filepath+"]��������Excel������,����Workbook����ʱ���ִ���:",ex);
			return null;
		}
		if(container.demwb.getSheet(DEMAND_SHEET_NAME)==null) {
			logger.error("�������ļ�["+filepath+"]��������Excel������,�������в�������Ϊ["+DEMAND_SHEET_NAME+"]��Sheet��");
			return null;
		}
		return container;
	}
	
	/**
	 * ������������
	 * @return ����ɹ�/����ʧ��
	 */
	public boolean processWorkBook() {
		Sheet datasheet=demwb.getSheet(DEMAND_SHEET_NAME);
		if(!verifyDataSheet(datasheet)) {		//��һ������֤���׼ȷ��
			logger.error("���ܴ���Workbook����֤���δͨ����");
			return false;
		}
		if(!extractData()) {					//�ڶ�������ȡ�������
			logger.error("���ܴ���Workbook��δ�ܳɹ���ȡExcel���ݡ�");
			return false;
		}
		//TODO: ������:�����������
		return true;
	}
	
	/**
	 * �˲�����Sheet��׼ȷ��
	 * @param sheet �������ݵı�����
	 * @return ��֤�ɹ�true/��֤ʧ��false
	 */
	public boolean verifyDataSheet(Sheet sheet) {
		if(sheet==null) {
			logger.error("�޷��˲�����sheet��׼ȷ�ԡ�sheet����Ϊ�ա�");
			return false;
		}
		return true;
	}
	
	/**
	 * ��ȡ�ļ��е�����
	 * @return true��ȡ�ɹ�/false��ȡʧ��
	 */
	public boolean extractData() {
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
				logger.error("Excel�����ݸ�ʽ����");
				ex.printStackTrace();
				return false;
			}
			demlist.add(node);
		}
		extractlist=demlist;
		return true;
	}
	
	/**
	 * ��������е����ݣ���������ÿ���ͺŵ����ʱ�����Сʱ��ȷ��
	 * @return �����ɹ�true/����ʧ��false
	 */
	public boolean anaylzeData() {
		return false;
	}
	
}
