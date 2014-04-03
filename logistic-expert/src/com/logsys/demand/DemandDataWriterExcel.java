package com.logsys.demand;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.logsys.model.ModelDataReaderDB;

/**
 * ��������д����:Excel
 * @author lx8sn6
 */
public class DemandDataWriterExcel {

	private static final Logger logger=Logger.getLogger(DemandDataWriterExcel.class);
	
	/**�ͺű�ͷ��*/
	private static final int ROW_HEADER=0;
	
	/**������ʼ��*/
	private static final int ROW_BEGIN=1;
	
	/**������*/
	private static final int COL_DATE=0;
	
	
	/**
	 * ����DemandUtil.demListToMapByPn()���ɵ�����д��Excel���ע�⣡���demmap�а���Model����û�е��ͺţ���Ὣ��Щ�ͺŵ�������demmap���Զ�ɾ��
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
			Workbook wb=new XSSFWorkbook();				//����������
			Sheet demsheet=wb.createSheet("Demand");	//����sheet
			Set<String> modelset=demmap.keySet();		//��ȡ�ͺż�
			Map<String,Integer> modelorder=ModelDataReaderDB.sortModels(modelset);	//�����ͺż���������
			Row row=demsheet.createRow(ROW_HEADER);		//������ͷ��
			Set<String> illegalmodel=new HashSet<String>();		//�Ƿ��ͺż�
			for(String model:modelset)	{				//д���ͷ
				if(modelorder.get(model)==null) {		//���û������ͺţ����������ͺ�,����Ƿ��ͺż�
					logger.warn("�ͺű�model��û�������б��г��ֵ��ͺ�:"+model+",��������ͺ�.");
					illegalmodel.add(model);			//����Ƿ��ͺż�
					continue;
				}
				row.createCell(modelorder.get(model)+1).setCellValue(model);
			}
			for(String model:illegalmodel) {			//ɾ���Ƿ��ͺ�
				demmap.remove(model);
			}
			Date mindate=DemandUtil.getMinDateInMultiModel(demmap);			//��ȡʱ������
			Date maxdate=DemandUtil.getMaxDateInMultiModel(demmap);			//��ȡʱ������
			int rowcounter=ROW_BEGIN;					//��ʼ��
			CellStyle datestyle=wb.createCellStyle();	//���ڸ�ʽ
			datestyle.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("yyyy/MM/dd"));
			Calendar dateindex=Calendar.getInstance();	//��ȡʱ��ʵ����Ϊ����
			dateindex.setTime(mindate);					//������ʼΪʱ������
			Map<Date,DemandContent> modeldem;
			DemandContent tempDemCon;
			while(true) {								//д��ʱ�����������
				row=demsheet.createRow(rowcounter);		//��������
				row.createCell(COL_DATE).setCellValue(dateindex);	//д������
				row.getCell(COL_DATE).setCellStyle(datestyle);		//�������ڷ��
				for(String model:demmap.keySet()) {		//ģ�ͱ������ҵ������Ƿ�������
					modeldem=demmap.get(model);
					tempDemCon=modeldem.get(dateindex.getTime());
					if(tempDemCon==null) continue;
					row.createCell(modelorder.get(model)+1).setCellValue(tempDemCon.getQty());
				}
				dateindex.add(Calendar.DAY_OF_MONTH, 1);//�¼�һ��
				if(dateindex.getTime().after(maxdate))	//��������������������֮�����˳�ѭ��
					break;
				rowcounter++;
			}
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
