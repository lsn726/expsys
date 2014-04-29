package com.logsys.report;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.logsys.util.Location;
import com.logsys.util.Matrixable;

/**
 * ����������
 * @author ShaonanLi
 */
public class ReportProcess {

	private static final Logger logger=Logger.getLogger(ReportProcess.class);
	
	/**
	 * �����������д��Excel���
	 * @param weeknum �����񳤶�
	 * @return �ɹ�true/ʧ��false
	 */
	public static boolean genDemandMatrixToExcel(String filepath,int weeknum) {
		if(filepath==null) {
			logger.error("���ܲ����������д��Excel���ļ�·��Ϊ�ա�");
			return false;
		}
		File file=new File(filepath);
		if(file.exists()) {
			logger.error("���ܲ����������д��Excel���ļ�["+filepath+"]�Ѵ���");
			return false;
		}
		MrpReportForExcel mrpreport=new MrpReportForExcel();		//����MRP�������
		Matrixable<Double> demmatrix=mrpreport.getDemandMatrix(weeknum); //���ɱ������
		if(demmatrix==null) {
			logger.error("���ܲ����������д��Excel�������������ʧ�ܡ�");
			return false;
		} else
			logger.info("����������ɳɹ���");
		Workbook wb=new XSSFWorkbook();								//����������
		Sheet demsheet=wb.createSheet("DemandMatrix");				//����sheet
		demmatrix.writeToExcelSheet(demsheet, new Location(0,0));	//�������д��sheet����ʼλ��0,0
		try {
			FileOutputStream fileOut=new FileOutputStream(filepath);
			wb.write(fileOut);
			fileOut.close();
			logger.info("�ɹ������������д���ļ�["+filepath+"]");
			return true;
		} catch(Throwable ex) {
			logger.error("���ܲ����������д��Excel��Excel���д�����ʱ���ִ���",ex);
			return false;
		}
	}
	
}
