package com.logsys.bom;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * BOM��Excel���ݶ�ȡ��
 * @author lx8sn6
 */
public class BOMDataReaderExcel {

	private static final Logger logger=Logger.getLogger(BOMDataReaderExcel.class);
	
	/**SAP CS12��������*/
	private static final int ROW_HEADER_SAPCS12=0;
	
	/**SAP CS12���ݿ�ʼ��*/
	private static final int ROW_BEGIN_SAPCS12=1;
	
	/**SAP CS12�ȼ���*/
	private static final int COL_LVL_SAPCS12=0;
	
	/**SAP CS12���������*/
	private static final int COL_COMPN_SAPCS12=1;
	
	/**SAP CS12����������*/
	private static final int COL_QTY_SAPCS12=3;
	
	/**SAP CS12���ļ�����λ*/
	private static final int COL_CUOM_SAPCS12=4;
	
	/**
	 * ��SAP��CS12������������Excel�������ȡ����,����CS12�ĵ����ļ���Ҫ���Ƚ��ܳɼ��м���ROW_BEGIN_SAPCS12�У�����Level��Ϊ0
	 * @param filepath �ļ�·��
	 * @return List<BOM��Ϣ>�б�/nullʧ��
	 */
	public static List<BOMContent> getDataFromExcel_CS12(String filepath) {
		if(filepath==null) {
			logger.error("���ܴ�Excel�л�ȡBOM��Ϣ,�ļ�·��Ϊnull.");
			return null;
		}
		File file=new File(filepath);
		if(!file.exists()) {
			logger.error("���ܴ�Excel�ж�ȡBOM��Ϣ,�ļ������ڡ�");
			return null;
		}
		if(file.isDirectory()) {
			logger.error("���ܴ�Excel�л�ȡBOM��Ϣ���ļ�·����Ŀ¼.");
			return null;
		}
		Workbook datasrc;
		InputStream instream;
		try {
			instream=new FileInputStream(filepath);
			datasrc=WorkbookFactory.create(instream);
			if(!validator_CS12(datasrc)) {
				logger.error("�ļ���֤���󣬲��ܴ�Excel���ж�ȡBOM��Ϣ��");
				return null;
			}
		} catch(Throwable ex) {
			logger.error("�ļ��򿪴���Ҳ������ȷ��Excel��ʽ��");
			return null;
		}
		Sheet sheet;
		List<BOMContent> bomlist;
		BOMContent bomcon;
		try {
			sheet=datasrc.getSheetAt(0);
			bomlist=new ArrayList<BOMContent>();
			for(Row row:sheet) {
				if(row.getRowNum()<ROW_BEGIN_SAPCS12) continue;
				bomcon=new BOMContent();
				row.getCell(COL_LVL_SAPCS12).setCellType(Cell.CELL_TYPE_STRING);
				if(row.getCell(COL_LVL_SAPCS12).getStringCellValue().contains("."))
					bomcon.setLvl(Integer.parseInt(row.getCell(COL_LVL_SAPCS12).getStringCellValue().replace(".", "")));
				else
					bomcon.setLvl(Integer.parseInt(row.getCell(COL_LVL_SAPCS12).getStringCellValue()));
				row.getCell(COL_COMPN_SAPCS12).setCellType(Cell.CELL_TYPE_STRING);
				bomcon.setPn(row.getCell(COL_COMPN_SAPCS12).getStringCellValue());
				row.getCell(COL_QTY_SAPCS12).setCellType(Cell.CELL_TYPE_NUMERIC);
				bomcon.setQty(row.getCell(COL_QTY_SAPCS12).getNumericCellValue());
				bomcon.setUnit(row.getCell(COL_CUOM_SAPCS12).getStringCellValue());
				bomlist.add(bomcon);
			}
		} catch(Throwable ex) {
			logger.error("���ݶ�ȡ����:"+ex);
			return null;
		}
		return bomlist;
	}
	
	/**
	 * SAP_CS12�ļ���֤����ͨ����ͷ��֤�ļ��Ϸ���
	 * @param filepath Ҫ��֤���ļ�·��
	 * @return ��֤ͨ��true/��֤ʧ��false
	 */
	private static boolean validator_CS12(Workbook wb) {
		if(wb==null) {
			logger.error("�޷���֤�ļ�������Ϊ�ա�");
			return false;
		}
		Sheet sheet=wb.getSheetAt(0);
		Row header=sheet.getRow(ROW_HEADER_SAPCS12);
		if(header.getCell(COL_LVL_SAPCS12).getStringCellValue().equals("Explosion level"))
			if(header.getCell(COL_COMPN_SAPCS12).getStringCellValue().equals("Component number"))
				if(header.getCell(COL_QTY_SAPCS12).getStringCellValue().equals("Comp. Qty (CUn)"))
					if(header.getCell(COL_CUOM_SAPCS12).getStringCellValue().equals("Component unit"))
						return true;
		return false;
	}
	
}
