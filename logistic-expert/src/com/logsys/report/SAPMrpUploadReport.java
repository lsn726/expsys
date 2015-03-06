package com.logsys.report;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.logsys.demand.DemandContent_Week;
import com.logsys.demand.DemandDataReaderDB;
import com.logsys.model.ModelService;
import com.logsys.prodplan.ProdplanContent_PrdLineCombined;
import com.logsys.prodplan.ProdplanContent_Week;
import com.logsys.prodplan.ProdplanDataReaderDB;
import com.logsys.util.DateInterval;
import com.logsys.util.DateTimeUtils;
import com.logsys.util.ExcelUtils;
import com.logsys.util.Location;
import com.logsys.util.Matrixable;

/**
 * SAP��MRP�ϴ�����
 * @author lx8sn6
 */
public class SAPMrpUploadReport {
	
	private static final Logger logger=Logger.getLogger(SAPMrpUploadReport.class);
	
	/**�ϴ��ƻ�ʱǰ�õİ���ƻ�������*/
	private static final int ONDAYPLAN_WEEKNUM=2;
	
	/**���ڿ�ʼ�е�����������������а����б�ͷ,����,��λ��MRP������*/
	private static final int PREFIX_DATECOL=4;
	
	/**SAP MRP���ϴ��ƻ�*/
	private static final String SHEET_NAME="SAP MRP Upload";
	
	/**��ʹ�õĹ�����*/
	private static final String FACTORY="FA01";

	/**�ϲ������ߵ������ƻ��б�*/
	private List<ProdplanContent_PrdLineCombined> pplist;
	
	/**���ܵ������б�*/
	private List<DemandContent_Week> wkdemlist;
	
	/**���ܼƻ��б�*/
	private List<ProdplanContent_Week> wkpplist; 
	
	/**������Model�б�*/
	private Map<String,Integer> sortedmodelmap;
	
	/**�������ݵľ������*/
	private Matrixable datamatrix;
	
	/**�ļ�·��*/
	private String filepath; 
	
	/**����+�ƻ��ܹ�������week��*/
	private int totalweek;
	
	/**
	 * ���캯��
	 * @param filepath �ļ�·��
	 * @param totalweek ����+�ƻ��ܹ�������week��
	 */
	private SAPMrpUploadReport(String filepath, int weeknum) {
		this.filepath=filepath;
		if(weeknum<ONDAYPLAN_WEEKNUM) {
			logger.warn("���棬������С�ڰ���ƻ�����������˽��������趨Ϊ����ƻ���������");
			totalweek=ONDAYPLAN_WEEKNUM;
		}
		else this.totalweek=weeknum;
	}
	
	/**
	 * �����������
	 * @filepath workbook�ļ�·��
	 * @weeknum �ܹ���Ҫ���ɵ����������������Ҫ����ONDAYPLAN_WEEKNUM�����������С��������֣����Զ�����ΪONDAYPLAN_WEEKNUM��
	 * @return �������ı������
	 */
	public static SAPMrpUploadReport createReportObject(String filepath, int weeknum) {
		File file=new File(filepath);
		if(file.exists()) {
			logger.error("��������SAPMrp�ϴ��������,�ļ��Ѵ���.");
			return null;
		}
		SAPMrpUploadReport report=new SAPMrpUploadReport(filepath,weeknum);
		if(!report.init()) {
			logger.error("��������SAPMrp�ϴ��������,��ʼ�����ִ���.");
			return null;
		}
		return report;
	}
	
	/**
	 * ��ʼ���������
	 * @return �ɹ�true/ʧ��false
	 */
	private boolean init() {
		Calendar cal=DateTimeUtils.getValidCalendar();
		long begin=0;
		long end=0;
		//���������ƻ�����ʼ����Ϊ���ܵ�һ��,����ΪONDAYPLAN_WEEKNUM�ܵ�����
		begin=DateTimeUtils.getFirstDayOfWeek(-1);
		cal.setTimeInMillis(begin);
		cal.add(Calendar.WEEK_OF_YEAR, ONDAYPLAN_WEEKNUM-1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		end=cal.getTimeInMillis();
		pplist=ProdplanDataReaderDB.getProdLineCombinedPPList(new DateInterval(begin,end));
		if(pplist==null) {
			logger.error("��ʼ��ʧ��,���ܲ��ܺϲ������ߵ������ƻ��б�");
			return false;
		}
		//����������ʼ��Ϊ���ܿ�ʼ��ONDAYPLAN_WEEKNUM�ܺ�ĵ�һ�죬������Ϊtotalweek�ܵ�����
		cal.setTimeInMillis(DateTimeUtils.getFirstDayOfWeek(-1));
		cal.add(Calendar.WEEK_OF_YEAR, ONDAYPLAN_WEEKNUM);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		begin=cal.getTimeInMillis();
		cal.setTimeInMillis(DateTimeUtils.getFirstDayOfWeek(-1));
		cal.add(Calendar.WEEK_OF_YEAR, totalweek-1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		end=cal.getTimeInMillis();
		wkdemlist=DemandDataReaderDB.getDemandDataFromDB_OnWeek(null, new Date(begin), new Date(end), true);
		if(wkdemlist==null) {
			logger.error("��ʼ��ʧ�ܣ����ܲ������������б�");
			return false;
		}
		//���ܼƻ�����ʼ��Ϊ���ܿ�ʼ��ONDAYPLAN_WEEKNUM�ܺ�ĵ�һ�죬������Ϊtotalweek�ܵ�����
		wkpplist=ProdplanDataReaderDB.getOnWeekProdplanDataFromDB(null, new DateInterval(begin, end));
		if(wkpplist==null) {
			logger.error("��ʼ��ʧ�ܣ����ܲ������ܼƻ��б�");
		}
		//��ȡ������Modelͼ
		sortedmodelmap=ModelService.getSortedModelMap();
		if(sortedmodelmap==null) {
			logger.error("��ʼ��ʧ�ܣ����ܲ���������Modelͼ.");
			return false;
		}
		//�����µľ������
		datamatrix=new Matrixable();
		if(!generateDataMatrix()) {
			logger.error("��ʼ��ʧ�ܣ����ܲ�����������");
			return false;
		}
		return true;
	}
	
	/**
	 * �����ϴ��������ݶ���
	 * @return �ɹ�true/ʧ��false
	 */
	private boolean generateDataMatrix() {
		//��һ����д���б�ͷ������Ʒ��
		for(String pn:sortedmodelmap.keySet())
			if(!datamatrix.putRowHeaderCell(sortedmodelmap.get(pn), pn)) {
				logger.error("�������ݾ������д���б�ͷ��Ʒ��ʱ���ִ����б�ͷ["+pn+"]����["+sortedmodelmap.get(pn)+"].");
				return false;
			}
		//�ڶ�����д�밴��ƻ������б�ͷ
		Calendar cal=DateTimeUtils.getValidCalendar(DateTimeUtils.getFirstDayOfWeek(-1));
		int index=PREFIX_DATECOL;
		while(index<=7*ONDAYPLAN_WEEKNUM+PREFIX_DATECOL-1) {
			if(!datamatrix.putColHeaderCell(index,DateTimeUtils.getFormattedTimeStr_YearWeekWkday(cal.getTimeInMillis()))) {
				logger.error("�������ݾ������д�밴�������ƻ����б�ͷʱ���ִ����б�ͷ����["+cal.getTime()+"]����["+index+"].");
				return false;
			}
			cal.add(Calendar.DAY_OF_YEAR, 1);
			index++;
		}
		//��������д�밴��ƻ�
		for(ProdplanContent_PrdLineCombined ppcont:pplist)
			if(!datamatrix.setData(ppcont.getPn(), DateTimeUtils.getFormattedTimeStr_YearWeekWkday(ppcont.getDate().getTime()), ppcont.getQty())) {
				logger.error("�������ݾ������д�밴�������ƻ����ִ������ݽڵ�["+ppcont+"].");
				return false;
			}
		//���Ĳ���д�밴�����󲿷��б�ͷ
		index=7*ONDAYPLAN_WEEKNUM+PREFIX_DATECOL;
		cal=DateTimeUtils.getValidCalendar(-1);
		cal.add(Calendar.WEEK_OF_YEAR, ONDAYPLAN_WEEKNUM);
		while(index<=totalweek+7*ONDAYPLAN_WEEKNUM+PREFIX_DATECOL-ONDAYPLAN_WEEKNUM-1)
			if(!datamatrix.putColHeaderCell(index, DateTimeUtils.getFormattedTimeStr_YearWeek(cal))) {
				logger.error("�������ݾ������д�밴�������б�ͷ���ִ�����λ��["+index+"]����["+DateTimeUtils.getFormattedTimeStr_YearWeek(cal)+"].");
				return false;
			} else {
				cal.add(Calendar.WEEK_OF_YEAR, 1);
				index++;
			}
		//���岽��д�밴������
		for(DemandContent_Week wkdem:wkdemlist)
			if(!datamatrix.setData(wkdem.getPn(), DateTimeUtils.getFormattedTimeStr_YearWeek(wkdem.getYear(),wkdem.getWeek()), wkdem.getQty())) {
				logger.error("�������ݾ������д�밴��������ִ�������ڵ�["+wkdem+"].");
				return false;
			}
		//��������д�밴�ܼƻ�
		for(ProdplanContent_Week wkpp:wkpplist)
			if(!datamatrix.setData(wkpp.getPn(), DateTimeUtils.getFormattedTimeStr_YearWeek(wkpp.getYear(),wkpp.getWeek()), wkpp.getQty())) {
				logger.error("�������ݾ������д�밴�ܼƻ����ִ��󣬼ƻ��ڵ�["+wkpp+"].");
				return false;
			}
		return true;
	}

	/**
	 * �����ݾ���д��Excel��񣬲���д�����ݽ��к��ڼӹ�
	 * @return true�ɹ�/falseʧ��
	 */
	public boolean writeToExcel() {
		//��һ������Matrixд��Excel
		Workbook wb=new XSSFWorkbook();
		Sheet datasheet=wb.createSheet(SHEET_NAME);
		datamatrix.writeToExcelSheet(datasheet, new Location(0,0));
		//�ڶ�����ɾ��û������Ŀ���
		for(int rowcounter=1;datasheet.getRow(rowcounter)!=null;rowcounter++)
			if(ExcelUtils.isExcelRowEmpty(datasheet.getRow(0), PREFIX_DATECOL, datasheet.getRow(rowcounter), PREFIX_DATECOL)) {
				if(rowcounter==datasheet.getLastRowNum()) {
					datasheet.removeRow(datasheet.getRow(rowcounter));
					break;
				}
				datasheet.shiftRows(rowcounter+1,datasheet.getLastRowNum(),-1);	//����һ�е����һ�������ƶ�һ�У���ɾ����������
				rowcounter--;
			}
		//��������д�빤��/��λ/��ʼ������Ϣ
		Calendar cal=DateTimeUtils.getValidCalendar(DateTimeUtils.getFirstDayOfWeek(-1));
		SimpleDateFormat dformat=new SimpleDateFormat("MM/dd/yyyy");
		Row row;
		Cell cell;
		for(int rowcounter=1;datasheet.getRow(rowcounter)!=null;rowcounter++) {
			row=datasheet.getRow(rowcounter);
			cell=row.createCell(1);			//��������
			cell.setCellValue(FACTORY);
			cell=row.createCell(2);			//������λ
			cell.setCellValue(1);
			cell=row.createCell(3);			//��������
			cell.setCellValue(dformat.format(cal.getTime()));
		}
		//���Ĳ����ϲ����������ƻ����ı�ͷ
		cal=DateTimeUtils.getValidCalendar(DateTimeUtils.getFirstDayOfWeek(-1));
		for(int index=0;index<ONDAYPLAN_WEEKNUM;index++) {
			datasheet.getRow(0).getCell(PREFIX_DATECOL+index*7).setCellValue(DateTimeUtils.getFormattedTimeStr_YearWeek(cal));
			datasheet.addMergedRegion(new CellRangeAddress(0,0,PREFIX_DATECOL+index*7,PREFIX_DATECOL+(index+1)*7-1));
			cal.add(Calendar.WEEK_OF_YEAR, 1);
		}
		//���岽��д��Ӳ��
		try {
			FileOutputStream fileOut=new FileOutputStream(filepath);
			wb.write(fileOut);
			fileOut.close();
			logger.info("�ɹ���SAP��MRP�ϴ�����д���ļ�["+filepath+"].");
			return true;
		} catch(Throwable ex) {
			logger.error("���ܽ�SAP��MRP�ϴ�����д���ļ���д��ʱ���ִ���",ex);
			return false;
		}
	}
	
}
