package com.logsys.report.bwi;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.stock.StockWHContent;
import com.logsys.util.Location;

/**
 * BWI�ⷿExcel��Ϣ��ȡ��
 * @author lx8sn6
 */
public class BWIWarehouseExcelReport {

	private static final Logger logger=Logger.getLogger(BWIWarehouseExcelReport.class);
	
	/**��֤��*/
	private static Map<Location,String> validatorMap;
	
	/**�ⷿWorkbook����*/
	private Workbook whwb;
	
	/**�������ͼ��pn->��������->������,�������ڰ�����������*/
	private Map<String,TreeMap<Date,StockWHContent>> stockpool;
	
	/**����������*/
	private static final int ROW_HEADER=0;
	
	/**��ʼ������*/
	private static final int ROW_START=1;
	
	/**����������*/
	private static final int COL_AOGDATE=0;
	
	/**�������*/
	private static final int COL_PN=1;
	
	/**��������*/
	private static final int COL_PO=2;
	
	/**���κ���*/
	private static final int COL_BN=3;
	
	/**ASN��*/
	private static final int COL_ASN=4;
	
	/**�ջ�������*/
	private static final int COL_RECN=5;
	
	/**����������*/
	private static final int COL_ARVQTY=6;
	
	/**��Ӧ����*/
	private static final int COL_VENDOR=7;
	
	/**��������� */
	private static final int COL_PUTINDATE=8;
	
	/**���������*/
	private static final int COL_PUTINQTY=9;
	
	/**ʣ��������*/
	private static final int COL_QTYLEFT=10;
	
	static {
		validatorMap=new HashMap<Location,String>();
		validatorMap.put(new Location(ROW_HEADER,COL_AOGDATE), "��������");
		validatorMap.put(new Location(ROW_HEADER,COL_PN),"���Ʒ��");
		validatorMap.put(new Location(ROW_HEADER,COL_PO), "������");
		validatorMap.put(new Location(ROW_HEADER,COL_BN), "���κ�");
		validatorMap.put(new Location(ROW_HEADER,COL_ASN), "���򽻻�����");
		validatorMap.put(new Location(ROW_HEADER,COL_RECN), "�ӻ�ƾ֤");
		validatorMap.put(new Location(ROW_HEADER,COL_ARVQTY), "��������");
		validatorMap.put(new Location(ROW_HEADER,COL_VENDOR), "��Ӧ��");
		validatorMap.put(new Location(ROW_HEADER,COL_PUTINDATE), "�������");
		validatorMap.put(new Location(ROW_HEADER,COL_PUTINQTY), "�������");
		validatorMap.put(new Location(ROW_HEADER,COL_QTYLEFT), "ʣ����");
	}
	
	private BWIWarehouseExcelReport(Workbook whwb) {
		this.whwb=whwb;
		stockpool=new HashMap<String,TreeMap<Date,StockWHContent>>();
	}

	/**
	 * �����ⷿ������ȡ����
	 * @param whexcelpath �ⷿ��Excel��Դ�ļ�·��
	 * @return �����ı������/null����ʧ��
	 */
	public static BWIWarehouseExcelReport createReportObject(String whexcelpath) {
		try {
			Workbook wb=WorkbookFactory.create(new File(whexcelpath));
			return new BWIWarehouseExcelReport(wb);
		} catch(Exception ex) {
			logger.error("�����ⷿExcelԴ�ļ�������ִ���,�����������ʧ��.");
			return null;
		}
	}
	
	/**
	 * �����ⷿԴ�������,�������в���:
	 * 1. ��֤Sheet����
	 * 2. ��ȡ����
	 * 3. �������ݳ�
	 */
	public void analyzeWorkbook() {
		Sheet itsheet;		//����sheet����
		for(int sheetindex=0;;sheetindex++) {	//��������sheet
			itsheet=whwb.getSheetAt(sheetindex);
			if(itsheet==null) break;
			if(!validateSheet(itsheet)) {
				logger.info("Sheet["+itsheet.getSheetName()+"]δͨ����֤�����ܲ��ǰ������ݵ�sheet���������sheet��");
				continue;
			}
			StockWHContent cont;
			Cell cell=null;
			String pn;
			for(Row row:itsheet) {				//����sheet�е�������
				if(row.getRowNum()<ROW_START) continue;
				cont=new StockWHContent();
				try {
					cell=row.getCell(COL_PN);		//��ȡPart Number
					if(cell!=null&&cell.getCellType()!=Cell.CELL_TYPE_ERROR) {
						pn=String.valueOf(cell.getNumericCellValue());	//��ֵPart Number
						if(pn==""||pn==null) continue;	//û��PN��������
						cont.setPn(pn);
						if(!stockpool.containsKey(pn))	//��������ݳ���û��pn����Ӧ��Map������Ҫ�����µ�Map
							stockpool.put(pn, new TreeMap<Date,StockWHContent>());
					} else continue;				//û��PN��������
					cell=row.getCell(COL_AOGDATE);	//��ȡ��������
					if(cell!=null) cont.setAogdate(cell.getDateCellValue());
					cell=row.getCell(COL_PO);		//��ȡ������
					if(cell!=null) cont.setPn(String.valueOf(cell.getNumericCellValue()));
					cell=row.getCell(COL_BN);		//��ȡ���κ�
					if(cell!=null) cont.setBn(cell.getStringCellValue());
					cell=row.getCell(COL_ASN);		//��ȡASN
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if(cell!=null) cont.setAsn(cell.getStringCellValue());
					cell=row.getCell(COL_RECN);		//��ȡ�ջ�����
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if(cell!=null) cont.setRecn(cell.getStringCellValue());
					cell=row.getCell(COL_ARVQTY);	//��ȡ��������
					if(cell!=null) cont.setArvqty(cell.getNumericCellValue());
					cell=row.getCell(COL_VENDOR);	//��ȡ��Ӧ��
					if(cell!=null) cont.setVendor(cell.getStringCellValue());
					cell=row.getCell(COL_PUTINDATE);//��ȡ�������
					if(cell!=null) cont.setPutindate(cell.getDateCellValue());
					cell=row.getCell(COL_PUTINQTY); //��ȡ�������
					if(cell!=null) cont.setPutinqty(cell.getNumericCellValue());
					cell=row.getCell(COL_QTYLEFT);  //��ȡʣ����
					if(cell!=null) cont.setQtyleft(cell.getNumericCellValue());
				} catch(Exception ex) {
					logger.warn("����Sheet["+itsheet.getSheetName()+"]��λ����["+(cell.getRowIndex()+1)+"]��["+(cell.getColumnIndex()+1)+"]�ĵ�Ԫ��ʱ���ִ���,����������¼��");
					continue;
				}
				stockpool.get(pn).put(cont.getAogdate(), cont);		//�Ե���������Ϊ��������Map
			}
		}
	}
	
	/**
	 * ��֤Sheet����
	 * @return ��֤ͨ��true,��֤ʧ��false
	 */
	private boolean validateSheet(Sheet sheet) {
		if(sheet==null) {
			logger.info("������֤Sheet,����Ϊ��.");
			return false;
		}
		Cell valcell;		//��֤cell
		for(Location loc:validatorMap.keySet()) {
			valcell=sheet.getRow(loc.row).getCell(loc.column);
			if(valcell==null) return false;
			if(!valcell.getStringCellValue().equals(validatorMap.get(loc))) return false;
		}
		return true;
	}
	
}
