package com.logsys.stock;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
import org.hibernate.Session;

import com.logsys.hibernate.HibernateSessionFactory;
import com.logsys.util.Location;

/**
 * Excel������,����������SAP��MB52�����������ݡ���Ҫ�ɹ�������getExcelReader()����������
 * @author lx8sn6
 */
public class ExcelStockService_SAP_MB52 {

	private static final Logger logger=Logger.getLogger(ExcelStockService_SAP_MB52.class);
	
	/**��֤ͼ*/
	private static Map<Location,String> validatorMap=new HashMap<Location,String>();
	
	/**������ʼ��*/
	private static final int STARTROW=3;
	
	/**������:PartNumber*/
	private static final int COL_PN=1;
	
	/**������:����*/
	private static final int COL_PLANT=2;
	
	/**������:�洢λ��*/
	private static final int COL_SLOC=3;
	
	/**������:��������*/
	private static final int COL_UNS=4;
	
	/**������:������λ*/
	private static final int COL_UOM=5;
	
	/**������:ת��������*/
	private static final int COL_INTRAN=6;
	
	/**������:IQC*/
	private static final int COL_IQC=7;
	
	/**������:��������*/
	private static final int COL_BLK=8;
	
	/**����״̬:������*/
	public static final String MAT_STATUS_UNS="Unrestricted";
	
	/**����״̬:IQC*/
	public static final String MAT_STATUS_IQC="IQC";
	
	/**����״̬:Block*/
	public static final String MAT_STATUS_BLK="Block";
	
	/**����״̬:������*/
	public static final String MAT_STATUS_INT="InTransit";
	
	/**����Stock���ݵ�sheet����*/
	private Sheet stocksheet; 
	
	/**��ȡ�Ŀ���б�����*/
	private List<StockContent> stocklist;
	
	static {
		validatorMap.put(new Location(1,1), "Material Number");
		validatorMap.put(new Location(1,2), "Plnt");
		validatorMap.put(new Location(1,3), "SLoc");
		validatorMap.put(new Location(1,4), "      Unrestricted");
		validatorMap.put(new Location(1,5), "BUn");
		validatorMap.put(new Location(1,6), "   Transit/Transf.");
		validatorMap.put(new Location(1,7), "  In Quality Insp.");
		validatorMap.put(new Location(1,8), "           Blocked");
	}
	
	private ExcelStockService_SAP_MB52(Sheet datasheet) {
		stocksheet=datasheet;
	}
	
	/**
	 * ���ļ�·��Ϊ������ȡExcel��SAP MB52��Excel�ļ���ȡ��
	 * @param filepath Excel�ļ�·��
	 * @return ��ȡ������
	 */
	public static ExcelStockService_SAP_MB52 getExcelReader(String filepath) {
		if(filepath==null) {
			logger.error("���ܶ�ȡExcel�ļ����ļ�·��Ϊ��.");
			return null;
		}
		File file=new File(filepath);
		if(!file.exists()) {
			logger.error("���ܶ�ȡExcel�ļ����ļ�������.");
			return null;
		}
		if(file.isDirectory()) {
			logger.error("���ܶ�ȡExcel�ļ����ļ���Ŀ¼.");
			return null;
		}
		ExcelStockService_SAP_MB52 mb52reader;
		Workbook datasrc;
		InputStream instream;
		try {
			instream=new FileInputStream(filepath);
			datasrc=WorkbookFactory.create(instream);
			if(!validator(datasrc)) {				//��֤Excel�ļ�
				logger.error("�ļ���֤���󣬲��ܴ�Excel���ж�ȡBOM��Ϣ��");
				return null;
			}
			mb52reader=new ExcelStockService_SAP_MB52(datasrc.getSheetAt(0));
		} catch(Throwable ex) {
			logger.error("�ļ��򿪴���Ҳ������ȷ��Excel��ʽ��");
			return null;
		}
		return mb52reader;
	}
	
	/**
	 * MB52�ļ���֤��
	 * @param wb �������ݵ�Workbook����
	 * @return ��֤�ɹ�true/��֤ʧ��false
	 */
	private static boolean validator(Workbook wb) {
		if(wb==null) {
			logger.error("������֤MB52������Excel������������Ϊnull.");
			return false;
		}
		Sheet sheet=wb.getSheetAt(0);
		if(sheet==null) {
			logger.error("SAP_MB52���Sheet��֤ʧ��.������ȡλ��λ��0��Sheet����.");
			return false;
		}
		Cell cell;
		String cellstr;
		for(Location loc:validatorMap.keySet()) {		//����λ������֤���
			cell=sheet.getRow(loc.row).getCell(loc.column);
			if(cell==null) {
				logger.error("SAP_MB52���Sheet��֤ʧ��.λ����["+loc.row+"]��["+loc.column+"]�ĵ�Ԫ��Ϊ��.");
				return false;
			}
			cellstr=cell.getStringCellValue();
			if(!cellstr.equals(validatorMap.get(loc))) {
				logger.error("SAP_MB52���Sheet��֤ʧ��.λ����["+loc.row+"]��["+loc.column+"]�ĵ�Ԫ������+["+cellstr+"]����֤�ַ�������["+validatorMap.get(loc)+"]�����.");
				return false;
			}
		}
		return true;
	}

	/**
	 * ��ȡStock�б�
	 * @param date ��ȡ���Ŀ������ע�Ŀ�����ڣ�null��Ϊ���졣
	 * @return true�ɹ�/falseʧ��
	 */
	public boolean extractStockList(Date date) {
		if(stocksheet==null) {
			logger.error("������ȡ������ݣ��������Sheet����Ϊ�գ�������û����ȷ��ʼ����");
			return false;
		}
		if(date==null) date=new Date();
		String pn;
		String plant;
		String sloc;
		String uom;
		stocklist=new ArrayList<StockContent>();
		double qty;
		try {
			for(Row row:stocksheet) {
				if(row.getRowNum()<STARTROW) continue;
				row.getCell(COL_PN).setCellType(Cell.CELL_TYPE_STRING);
				pn=row.getCell(COL_PN).getStringCellValue();
				plant=row.getCell(COL_PLANT).getStringCellValue();
				row.getCell(COL_SLOC).setCellType(Cell.CELL_TYPE_STRING);
				sloc=row.getCell(COL_SLOC).getStringCellValue();
				uom=row.getCell(COL_UOM).getStringCellValue();
				qty=row.getCell(COL_UNS).getNumericCellValue();		//��ȡ������
				if(qty!=0.0)				//�������������ֵ�����������ƶ���
					stocklist.add(new StockContent(date,pn,plant,sloc,qty,uom,MAT_STATUS_UNS));
				qty=row.getCell(COL_INTRAN).getNumericCellValue();	//��ȡ��;
				if(qty!=0.0)
					stocklist.add(new StockContent(date,pn,plant,sloc,qty,uom,MAT_STATUS_INT));
				qty=row.getCell(COL_IQC).getNumericCellValue();		//��ȡIQC
				if(qty!=0.0)
					stocklist.add(new StockContent(date,pn,plant,sloc,qty,uom,MAT_STATUS_IQC));
				qty=row.getCell(COL_BLK).getNumericCellValue();		//��ȡBlock
				if(qty!=0.0)
					stocklist.add(new StockContent(date,pn,plant,sloc,qty,uom,MAT_STATUS_BLK));
			}
		} catch(Exception ex) {
			logger.error("�ڶ�ȡ����б�ʱ���ִ���",ex);
			stocklist=null;
			return false;
		}
		return true;
	}
	
	/**
	 * ���������д�����ݿ�
	 * @return ��ȡ���ݵ�����/-1ʧ��
	 */
	public int writeStockListToDB() {
		if(stocklist==null) {
			logger.error("���ܽ��������д�����ݿ⣬����б���δ����ʼ������Ҫ����extractStockList()������");
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
		for(StockContent stockcont:stocklist) {
			session.save(stockcont);
			if(counter++%100==0) {
				session.flush();
				session.clear();
			}
		}
		session.getTransaction().commit();
		session.close();
		return counter;
	}
	
}
