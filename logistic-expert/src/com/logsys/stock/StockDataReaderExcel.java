package com.logsys.stock;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Stock���ݶ�ȡ��--Excel���ݶ�ȡ��
 * @author lx8sn6
 *
 */
public class StockDataReaderExcel {

	private static final Logger logger=Logger.getLogger(StockDataReaderExcel.class);
	
	/**
	 * ��SAP��mb52�������Excel�б�����ȡ����
	 * @param filepath Excel��sheet����
	 * @return �����Ϣ�б�/null
	 */
	public static List<StockContent> readDataFromExcelSheet_SAPMB52(String filepath) {
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
		Workbook datasrc;
		InputStream instream;
		try {
			instream=new FileInputStream(filepath);
			datasrc=WorkbookFactory.create(instream);
			if(!validator_mb52(datasrc)) {
				logger.error("�ļ���֤���󣬲��ܴ�Excel���ж�ȡBOM��Ϣ��");
				return null;
			}
		} catch(Throwable ex) {
			logger.error("�ļ��򿪴���Ҳ������ȷ��Excel��ʽ��");
			return null;
		}
		return null;
	}
	
	/**
	 * MB52�ļ���֤��
	 * @param wb �������ݵ�Workbook����
	 * @return ��֤�ɹ�true/��֤ʧ��false
	 */
	private static boolean validator_mb52(Workbook wb) {
		if(wb==null) {
			logger.error("������֤MB52������Excel������������Ϊnull.");
			return false;
		}
		return true;
	}

}
