package com.logsys.prodplan.bwi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.prodplan.ProdplanContent;
import com.logsys.setting.Settings;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.setting.pd.bwi.BWIPLInfo.ProdLine;
import com.logsys.setting.pp.bwi.BWIPPExcelInfo;

/**
 * BWI�����ƻ����ݶ�ȡ��, Excel���ݶ�ȡ��
 * @author lx8sn6
 */
public class BWIProdplanDataReaderExcel {
	
	private static final Logger logger=Logger.getLogger(BWIProdplanDataReaderExcel.class);
	
	private static BWIPPExcelInfo ppExcelInfo=Settings.BWISettings.ppExcelInfo;		//BWI��ExcelPP������
	
	/**
	 * ��Excel�ļ��л�ȡ��װ�ƻ����ƻ�Sheet���Ʊ���ΪUploadPlan������BWI��FA�ƻ��ļ���
	 * @param filepath �ļ�·�����ƻ�Sheet���Ʊ���ΪUploadPlan
	 * @param startdate ��ȡ�ƻ�����ʼ���ڣ����startdateΪnull,���Զ�����Ϊ��һ�ܵ���һ
	 * @param enddate ��ȡ�ƻ��Ľ������ڣ����enddateΪnull,����������
	 * @return ��ȡ�ļƻ���
	 */
	public static List<ProdplanContent> getFAPlanFromFileBWI(String filepath, Date startdate, Date enddate) {
		Workbook wb;
		InputStream readstream;
		try {
			readstream=new FileInputStream(filepath);
			wb=WorkbookFactory.create(readstream);
		} catch(Throwable ex) {
			logger.error("���ܴ��ļ�·����ȡExcel�ļ���",ex);
			return null;
		}
		Sheet plansheet=null;
		try {
			plansheet=wb.getSheet("UploadPlan");
			if(plansheet==null) {
				logger.error("���ܶ�ȡ�ƻ���û����ΪUploadPlan��Sheet.");
				return null;
			}
			Map<Date, Integer> datecolmap=new HashMap<Date, Integer>();	//����<->������Map��ϵ
			Date maxdate=null;											//���������
			for(Cell datecell:plansheet.getRow(ppExcelInfo.getFA1DateRow())) //��ʼ������<->���ڵ�Map
				try {
					if(datecell.getDateCellValue()==null) continue;
					datecolmap.put(datecell.getDateCellValue(),datecell.getColumnIndex());
					maxdate=datecell.getDateCellValue();
				} catch(Throwable ex) {				//�������ת�������������Ԫ��
					continue;
				}
			if(startdate==null) {					//���û��ָ����ʼ���ڣ���Ĭ��Ϊ���ܵ���һ
				Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
				int week=cal.get(Calendar.WEEK_OF_YEAR)+1;
				int year=cal.get(Calendar.YEAR);
				cal.clear();
				cal.setWeekDate(year, week, Calendar.MONDAY);
				startdate=cal.getTime();
			}
			if(enddate==null) {						//���û��ָ���������ڣ���Ĭ��Ϊ�������
				enddate=maxdate;
			}
			String modelpn;							//�ͺ�
			int begincol;							//��ʼ��
			int endcol;								//������
			if(datecolmap.containsKey(startdate)) 
				begincol=datecolmap.get(startdate);	//��ȡ��ʼ������
			else {
				logger.error("û�ж�Ӧ��ʼ���ڣ�"+startdate+" �Ķ�Ӧ�ƻ��С���˲�ƻ���");
				return null;
			}
			if(datecolmap.containsKey(enddate))	
				endcol=datecolmap.get(enddate);		//��ȡ��������
			else {
				logger.error("û�ж�Ӧ�������ڣ�"+enddate+" �Ķ�Ӧ�ƻ��С���˲�ƻ���");
				return null;
			}
			List<ProdplanContent> pplist=new ArrayList<ProdplanContent>();
			ProdplanContent temp;
			Date plandate;
			for(Row row:plansheet) {					//�����У���ȡFA1�ļƻ�
				if(row.getRowNum()>=ppExcelInfo.getFA1PPBeginRow() && row.getRowNum()<=ppExcelInfo.getFA1PPEndRow()) {	//��ȡFA1�ļƻ�
					row.getCell(ppExcelInfo.getPnCol()).setCellType(Cell.CELL_TYPE_STRING);	//����ΪString����
					modelpn=row.getCell(ppExcelInfo.getPnCol()).getStringCellValue();		//��ȡ�ͺ�
					for(Cell cell:row) {				//������Ԫ
						if(cell.getColumnIndex()<begincol||cell.getColumnIndex()>endcol) continue;	//����ʼ������֮ǰ��ȫ������
						try {
							if(cell.getNumericCellValue()==0) continue;		//���û�мƻ���������
						} catch (Throwable ex) {
							logger.error("��Ԫ���в������֣��޷���ȡ�ƻ�:λ��["+cell.getRowIndex()+"]��["+cell.getColumnIndex()+"]��");
							throw ex;
						}
						plandate=plansheet.getRow(ppExcelInfo.getFA1DateRow()).getCell(cell.getColumnIndex()).getDateCellValue();	//��ȡ��ǰ��Ԫ��ƻ�����
						if(plandate==null) continue;					//���û�����Ӧ�����ڣ�������
						temp=new ProdplanContent();
						temp.setDate(plandate);			//��ȡDate
						temp.setPn(modelpn);
						temp.setPrdline(BWIPLInfo.getStdNameByLineEnum(ProdLine.DAMPER_FA_FA1));
						temp.setQty(cell.getNumericCellValue());
						pplist.add(temp);
					}
				} else if(row.getRowNum()>=ppExcelInfo.getFA2PPBeginRow() && row.getRowNum()<=ppExcelInfo.getFA2PPEndRow()) {	//��ȡFA2�ļƻ�
					row.getCell(ppExcelInfo.getPnCol()).setCellType(Cell.CELL_TYPE_STRING);	//����ΪString����
					modelpn=row.getCell(ppExcelInfo.getPnCol()).getStringCellValue();		//��ȡ�ͺ�
					for(Cell cell:row) {				//������Ԫ
						if(cell.getColumnIndex()<begincol||cell.getColumnIndex()>endcol) continue;	//����ʼ������֮ǰ��ȫ������
						if(cell.getNumericCellValue()==0) continue;		//���û�мƻ���������
						plandate=plansheet.getRow(ppExcelInfo.getFA1DateRow()).getCell(cell.getColumnIndex()).getDateCellValue();	//��ȡ��ǰ�ƻ����ڣ�FA1��FA2�ƻ�����Ŀǰһ��
						if(plandate==null) continue;					//���û�����Ӧ�����ڣ�������
						temp=new ProdplanContent();
						//FA1��FA2��������ͬ
						temp.setDate(plandate);	//��ȡDate
						temp.setPn(modelpn);
						temp.setPrdline(BWIPLInfo.getStdNameByLineEnum(ProdLine.DAMPER_FA_FA2));
						temp.setQty(cell.getNumericCellValue());
						pplist.add(temp);
					}
				} else
					continue;
			}
			return pplist;
		} catch(Throwable ex) {
			logger.error("���ܶ�ȡExcel�е�����",ex);
			return null;
		}
	}
	
}
