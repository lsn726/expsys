package com.logsys.production;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.setting.Settings;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.util.Location;

/**
 * ����������ȡ������Excel��ȡ
 * @author ShaonanLi
 */
public class ProductionDataReaderExcel {

	private static final Logger logger=Logger.getLogger(ProductionDataReaderExcel.class);
	
	private static BWIPLInfo plinfo=Settings.BWISettings.plinfo;
	
	/**
	 * ���ļ�filepath�ж�ȡ����
	 * @param filepath
	 * @return
	 */
	public static List<ProductionContent> readDataFromFile(String filepath) {
		File file=new File(filepath);			//���ļ�·���������ļ�
		Workbook wb;
		InputStream readstream;
		try {
			readstream=new FileInputStream(filepath);
			wb=WorkbookFactory.create(readstream);
		} catch(Throwable ex) {
			logger.error("���ܴ��ļ�·����ȡExcel�ļ���",ex);
			return null;
		}
		Sheet sheet=null;
		String sheetname;
		Location temploc;
		Date tempdate;
		Calendar tempcal;
		String prodline;		//����������
		int month;				//Excel�ձ����ڵı���·�
		int daycounter;			//���ڼ�����
		List<ProductionContent> pdlist;
		try {
		} catch(Throwable ex) {
			logger.error("��Excel����ж�ȡ�������ݳ��ִ���",ex);
			return null;
		}
		return null;
	}

}
