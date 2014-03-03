package com.logsys.demand;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ��������д����:Excel
 * @author lx8sn6
 */
public class DemandDataWriterExcel {

	private static final Logger logger=Logger.getLogger(DemandDataWriterExcel.class);
	
	/**
	 * ����DemandUtil.demListToMapByPn()���ɵ�����д��Excel���
	 * @param filepath �ļ�·��
	 * @param demmap ����DemandUtil.demListToMapByPn()��������Demand�����ݱ��
	 * @return �ɹ�true/ʧ��false
	 */
	public static boolean writeToExcel(String filepath,Map<String,Map<Date,DemandContent>> demmap) {
		if(filepath==null) {
			logger.error("�ļ�·������Ϊ��.");
			return false;
		}
		File file=new File(filepath);
		if(file.exists()) {
			logger.error("�ļ��Ѵ���.");
			return false;
		}
		try {
			Workbook wb=new XSSFWorkbook();
			Sheet demsheet=wb.createSheet("Demand");
			
			FileOutputStream fileOut=new FileOutputStream(filepath);
			wb.write(fileOut);
			fileOut.close();
			return true;
		} catch(Throwable ex) {
			logger.error("����д����ִ���:"+ex);
			ex.printStackTrace();
			return false;
		}
	}

}
