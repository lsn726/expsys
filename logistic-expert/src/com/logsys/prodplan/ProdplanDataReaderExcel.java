package com.logsys.prodplan;

import java.io.File;
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

import com.logsys.util.DateInterval;

/**
 * �����ƻ����ݶ�ȡ��, Excel���ݶ�ȡ��
 * @author lx8sn6
 */
public class ProdplanDataReaderExcel {
	
	private static final Logger logger=Logger.getLogger(ProdplanDataReaderExcel.class);
	
	/**BWI��FA�ƻ���PN��*/
	private static final int BWI_FA_PN_COL=0;
	
	/**BWI��FA1�ƻ���������*/
	private static final int BWI_FA1_DATE_ROW=3;
	
	/**BWI��FA1�ƻ�����ʼ��*/
	private static final int BWI_FA1_PLANROW_BEGIN=4;
	
	/**BWI��FA1�ƻ��Ľ�����*/
	private static final int BWI_FA1_PLANROW_END=23;
	
	/**BWI��FA2�ƻ���������*/
	private static final int BWI_FA2_DATE_ROW=25;
	
	/**BWI��FA2�ƻ�����ʼ��*/
	private static final int BWI_FA2_PLANROW_BEGIN=27;
	
	/**BWI��FA2�ƻ��Ľ�����*/
	private static final int BWI_FA2_PLANROW_END=44;
	
	/**BWI��FA2�ƻ����������е��в�*/
	private static final int BWI_FA2_ROW_DIFF=2;
	
	/**
	 * ��Excel�ļ��л�ȡ��װ�ƻ����ƻ�Sheet���Ʊ���ΪUploadPlan������BWI��FA�ƻ��ļ���
	 * @param filepath �ļ�·�����ƻ�Sheet���Ʊ���ΪUploadPlan
	 * @param startdate ��ȡ�ƻ�����ʼ���ڣ����startdateΪnull,���Զ�����Ϊ��һ�ܵ���һ
	 * @param enddate ��ȡ�ƻ��Ľ������ڣ����enddateΪnull,����������
	 * @return ��ȡ�ļƻ���
	 */
	public static List<ProdplanContent> getFAPlanFromFileBWI(String filepath, Date startdate, Date enddate) {
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
		Sheet plansheet=null;
		try {
			plansheet=wb.getSheet("UploadPlan");
			if(plansheet==null) {
				logger.error("���ܶ�ȡ�ƻ���û����ΪUploadPlan��Sheet.");
				return null;
			}
			Map<Date, Integer> datecolmap=new HashMap<Date, Integer>();	//����<->������Map��ϵ
			Date maxdate=null;												//���������
			for(Cell datecell:plansheet.getRow(BWI_FA1_DATE_ROW)) 		//��ʼ������<->���ڵ�Map
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
				cal.setWeekDate(year, week, Calendar.MONDAY);
				startdate=new Date(cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH));
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
				if(row.getRowNum()>=BWI_FA1_PLANROW_BEGIN && row.getRowNum()<=BWI_FA1_PLANROW_END) {	//��ȡFA1�ļƻ�
					row.getCell(BWI_FA_PN_COL).setCellType(Cell.CELL_TYPE_STRING);	//����ΪString����
					modelpn=row.getCell(BWI_FA_PN_COL).getStringCellValue();		//��ȡ�ͺ�
					for(Cell cell:row) {				//������Ԫ
						if(cell.getColumnIndex()<begincol||cell.getColumnIndex()>endcol) continue;	//����ʼ������֮ǰ��ȫ������
						if(cell.getNumericCellValue()==0) continue;		//���û�мƻ���������
						plandate=plansheet.getRow(BWI_FA1_DATE_ROW).getCell(cell.getColumnIndex()).getDateCellValue();
						if(plandate==null) continue;					//���û�����Ӧ�����ڣ�������
						temp=new ProdplanContent();
						temp.setDate(plandate);	//��ȡDate
						temp.setPn(modelpn);
						temp.setPrdline("Final Assembly 1");
						temp.setQty(cell.getNumericCellValue());
						pplist.add(temp);
					}
				} else if(row.getRowNum()>=BWI_FA2_PLANROW_BEGIN && row.getRowNum()<=BWI_FA2_PLANROW_END) {	//��ȡFA2�ļƻ�
					row.getCell(BWI_FA_PN_COL).setCellType(Cell.CELL_TYPE_STRING);	//����ΪString����
					modelpn=row.getCell(BWI_FA_PN_COL).getStringCellValue();		//��ȡ�ͺ�
					for(Cell cell:row) {				//������Ԫ
						if(cell.getColumnIndex()<begincol||cell.getColumnIndex()>endcol) continue;	//����ʼ������֮ǰ��ȫ������
						if(cell.getNumericCellValue()==0) continue;		//���û�мƻ���������
						plandate=plansheet.getRow(BWI_FA1_DATE_ROW).getCell(cell.getColumnIndex()).getDateCellValue();
						if(plandate==null) continue;					//���û�����Ӧ�����ڣ�������
						temp=new ProdplanContent();
						//FA1��FA2��������ͬ
						temp.setDate(plandate);	//��ȡDate
						temp.setPn(modelpn);
						temp.setPrdline("Final Assembly 2");
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
