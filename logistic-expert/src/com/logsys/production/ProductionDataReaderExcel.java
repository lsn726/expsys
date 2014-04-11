package com.logsys.production;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.setting.Settings;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.setting.pd.bwi.BWIProductionExcelInfo;
import com.logsys.util.Location;

/**
 * ����������ȡ������Excel��ȡ
 * @author ShaonanLi
 */
public class ProductionDataReaderExcel {

	private static final Logger logger=Logger.getLogger(ProductionDataReaderExcel.class);
	
	private static BWIProductionExcelInfo pdExcelInfo=Settings.BWISettings.pdExcelInfo;
	
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
			sheet=wb.getSheet(pdExcelInfo.getConfigSheetName());
			temploc=pdExcelInfo.getPrdLineNameLocInConfigSheet();
			prodline=sheet.getRow(temploc.row).getCell(temploc.row).getStringCellValue();	//��ȡ����������
			prodline=plinfo.getStdProdlineNameByAlias(prodline);							//��ȡ��ʽ����
			if(prodline==null) {						//���û����ʽ���������ƣ����˳�
				logger.error("���ִ���û���ҵ�ָ�������ߵ���ʽ���ơ�");
				return null;
			}
			temploc=pdExcelInfo.getDateInfoLocInConfigSheet();
			tempdate=sheet.getRow(temploc.row).getCell(temploc.column).getDateCellValue();	//��ȡ�·���Ϣ
			tempcal=Calendar.getInstance();
			tempcal.setTime(tempdate);
			month=tempcal.get(Calendar.MONTH);
			pdlist=new ArrayList<ProductionContent>();
			for(daycounter=1;;daycounter++) {			//ѭ������ÿ�������Sheet����1�տ�ʼ
				sheet=wb.getSheet(String.valueOf(month));
				if(sheet==null) break;					//���û�и���day_of_month��sheet����˵���Ѿ��������
				for(Row row:sheet) {					//���б�����ǰsheet
					
				}
			}
		} catch(Throwable ex) {
			logger.error("��Excel����ж�ȡ�������ݳ��ִ���",ex);
			return null;
		}
		return null;
	}

}
