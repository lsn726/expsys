package com.logsys.demand;

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
 * ��������Excel�ļ���ȡ��
 * @author lx8sn6
 */
public class DemandDataReaderExcel {
	
	private static Logger logger=Logger.getLogger(DemandDataReaderExcel.class);

	/**����������*/
	private static final int COL_DATE=0;
	
	/**�����ͺ���*/
	private static final int COL_PN=1;
	
	/**��������*/
	private static final int COL_QTY=2;
	
	/**
	 * ���ļ���ȡ����
	 * @return �����������ݵ�����
	 */
	public static List<DemandContent> getDataFromFile(String filepath) {
		if(filepath==null) {
			logger.error("�ļ�·��Ϊ��");
			return null;
		}
		File file=new File(filepath);
		if(!file.exists()) {
			logger.error("�ļ�������");
			return null;
		}
		if(file.isDirectory()) {
			logger.error("�ļ�·�����ļ��� ");
			return null;
		}
		List<DemandContent> demandlist;
		Workbook datasrc;
		InputStream readStream;
		Sheet sheet;
		try {
			readStream=new FileInputStream(filepath);
			datasrc=WorkbookFactory.create(readStream);
			sheet=datasrc.getSheetAt(0);
		} catch(Throwable e) {
			logger.error("�ļ��򿪴���,�����ļ���ʽ���󡣿��ܲ�����ȷ��Excel�ļ���");
			return null;
		}
		demandlist=new ArrayList<DemandContent>();
		DemandContent node;
		for(Row row:sheet) {
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
				return null;
			}
			demandlist.add(node);
		}
		return demandlist;
	}
	
}
