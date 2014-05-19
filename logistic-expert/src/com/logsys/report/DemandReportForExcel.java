package com.logsys.report;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.logsys.demand.DemandContent;
import com.logsys.demand.DemandContent_Month;
import com.logsys.demand.DemandContent_Week;
import com.logsys.demand.DemandDataReaderDB;
import com.logsys.demand.DemandUtil;
import com.logsys.model.ModelContent;
import com.logsys.model.ModelDataReaderDB;
import com.logsys.model.ModelUtil;
import com.logsys.util.DateInterval;
import com.logsys.util.GeneralUtils;
import com.logsys.util.Location;
import com.logsys.util.Matrixable;

/**
 * ���󱨱�--���յ�Sheet�����ܵ�Sheet�Ͱ��µ�Sheet
 * @author ShaonanLi
 */
public class DemandReportForExcel {

	private static final Logger logger=Logger.getLogger(DemandReportForExcel.class);
	
	/**����Ʒ˳��*/
	private Map<String,Integer> matorder_fin;
	
	/**����Ʒ�б�*/
	private List<ModelContent> matlist_fin;
	
	/**����Ʒ��*/
	private Set<String> matset_fin;
	
	/**���������*/
	private Matrixable dematrix_onday;
	
	/**���������*/
	private Matrixable dematrix_onweek;
	
	/**���������*/
	private Matrixable dematrix_onmonth;
	
	/**Sheet���ƣ���������*/
	private static final String SHEETNAME_DEM_ONDAY="Demand_Daily";
	
	/**Sheet���ƣ���������*/
	private static final String SHEETNAME_DEM_ONWEEK="Demand_Weekly";
	
	/**Sheet���ƣ���������*/
	private static final String SHEETNAME_DEM_ONMONTH="Demand_Monthly";
	
	public DemandReportForExcel() throws Exception {
		if(!init()) {
			logger.error("��ʼ�����󱨸�ʧ�ܡ�");
			throw new Exception("��ʼ�����󱨸�ʧ�ܡ�");
		}
	}
	
	private boolean init() {
		matlist_fin=ModelDataReaderDB.getDataFromDB(null);			//��ʼ����Ʒ�б���ȡ����Model
		if(matlist_fin==null) return false;
		matset_fin=ModelUtil.getModelSet(matlist_fin);				//��ʼ����Ʒ��
		if(matset_fin==null) return false;
		matorder_fin=ModelDataReaderDB.sortModels(matset_fin);		//��ʼ����Ʒ˳��ͼ
		if(matorder_fin==null) return false;
		Calendar begindate=GeneralUtils.getValidCalendar();
		Calendar now=GeneralUtils.getValidCalendar();
		begindate.clear();
		begindate.set(Calendar.YEAR,now.get(Calendar.YEAR));
		begindate.set(Calendar.WEEK_OF_YEAR, now.get(Calendar.WEEK_OF_YEAR));
		begindate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);				//����ƻ��Ͱ��ܼƻ���ʼ����Ϊ������һ
		dematrix_onday=genDemandMatrix_OnDay(begindate.getTime(),null);		//��ȡ����ƻ�
		if(dematrix_onday==null) return false;
		dematrix_onweek=genDemandMatrix_OnWeek(begindate.getTime(),null);	//��ȡ���ܼƻ�
		if(dematrix_onweek==null) return false;
		begindate.clear();
		begindate.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1);	//���¼ƻ���ʼ����
		dematrix_onmonth=genDemandMatrix_OnMonth(begindate.getTime(),null);	//��ȡ���¼ƻ�
		if(dematrix_onmonth==null) return false;
		return true;
	}
	
	/**
	 * ��ȡ�����������ݾ���
	 * @return ��������������
	 */
	private Matrixable genDemandMatrix_OnDay(Date begindate, Date enddate) {
		List<DemandContent> demlist_onday=DemandDataReaderDB.getDemandDataFromDB_OnDay(null, begindate, enddate);	//��ð�������Ԫ����
		if(demlist_onday==null) {
			logger.error("�������ɰ����������󣬰��������б��ȡ����");
			return null;
		}
		Matrixable demmat_onday=new Matrixable();		//���������������
		if(demlist_onday.size()==0) return demmat_onday;
		for(String pn:matorder_fin.keySet())		//����Ʒ��д���б�ͷ
			demmat_onday.putRowHeaderCell(matorder_fin.get(pn), pn);
		DateFormat dateconv=new SimpleDateFormat("yyyy/MM/dd");	//���ڸ�ʽ
		DateInterval interval=DemandUtil.getMinMaxDateInDemandList(demlist_onday);	//��ȡʱ����Сֵ�����ֵ
		Calendar begin=GeneralUtils.getValidCalendar();
		Calendar end=GeneralUtils.getValidCalendar();
		if(begindate==null)							//������ʼʱ��
			begin.setTime(interval.begindate);
		else
			begin.setTime(begindate);
		if(enddate==null)							//���ý���ʱ��
			end.setTime(interval.enddate);
		else
			end.setTime(enddate);
		for(int counter=1;!begin.after(end);begin.add(Calendar.DAY_OF_YEAR, 1),counter++)	//������д���б�ͷ
			demmat_onday.putColHeaderCell(counter, dateconv.format(begin.getTime()));
		boolean result;
		for(DemandContent demcont:demlist_onday) { //д����������
			result=demmat_onday.setData(demcont.getPn(), dateconv.format(demcont.getDate()), (Double)demcont.getQty());
			if(!result) {
				logger.error("�ھ��������д������ʧ�ܡ�");
				return null;
			}
		}
		return demmat_onday;
	}
	
	/**
	 * ��ȡ�����������ݾ���
	 * @return ��������������
	 */
	private Matrixable genDemandMatrix_OnWeek(Date begindate, Date enddate) {
		List<DemandContent_Week> demlist_onweek=DemandDataReaderDB.getDemandDataFromDB_OnWeek(null, begindate, enddate);	//��ð�������Ԫ����
		if(demlist_onweek==null) {
			logger.error("�������ɰ��ܵ�������󣬰��������б��ȡ����");
			return null;
		}
		Matrixable demmat_onweek=new Matrixable();		//���������������
		if(demlist_onweek.size()==0) return demmat_onweek;
		for(String pn:matorder_fin.keySet())		//����Ʒ��д���б�ͷ
			demmat_onweek.putRowHeaderCell(matorder_fin.get(pn), pn);
		int colcounter=0;		//������
		String colheader;		//�б�ͷ
		for(DemandContent_Week weekdem:demlist_onweek)	{ //ѭ��д���ͷ����������
			colheader=weekdem.getYear()+"��"+weekdem.getWeek()+"��";	//�б�ͷ
			if(!demmat_onweek.isContentInColHeader(colheader))			//������������б�ͷ�У�������б�ͷ
				demmat_onweek.putColHeaderCell(++colcounter, colheader);
			demmat_onweek.setData(weekdem.getPn(), colheader, weekdem.getQty()); //д������
		}
		return demmat_onweek;
	}
	
	/**
	 * ��ȡ�����������ݾ���
	 * @return ��������������
	 */
	private Matrixable genDemandMatrix_OnMonth(Date begindate, Date enddate) {
		List<DemandContent_Month> demlist_onmonth=DemandDataReaderDB.getDemandDataFromDB_OnMonth(null, begindate, enddate);	//��ð�������Ԫ����
		if(demlist_onmonth==null) {
			logger.error("�������ɰ��ܵ�������󣬰��������б��ȡ����");
			return null;
		}
		Matrixable demmat_onmonth=new Matrixable();		//���������������
		if(demlist_onmonth.size()==0) return demmat_onmonth;
		for(String pn:matorder_fin.keySet())		//����Ʒ��д���б�ͷ
			demmat_onmonth.putRowHeaderCell(matorder_fin.get(pn), pn);
		int colcounter=0;		//������
		String colheader;		//�б�ͷ
		for(DemandContent_Month monthdem:demlist_onmonth)	{ //ѭ��д���ͷ����������
			colheader=monthdem.getYear()+"��"+monthdem.getMonth()+"��";	//�б�ͷ
			if(!demmat_onmonth.isContentInColHeader(colheader))			//������������б�ͷ�У�������б�ͷ
				demmat_onmonth.putColHeaderCell(++colcounter, colheader);
			demmat_onmonth.setData(monthdem.getPn(), colheader, monthdem.getQty());	//д������
		}
		return demmat_onmonth;
	}
	
	/**
	 * ������д��Excel�ļ�
	 * @param filepath Ҫд����ļ�·��
	 * @return �ɹ�true/ʧ��false
	 */
	public boolean writeReportToFile(String filepath) {
		if(filepath==null) {
			logger.error("���ܲ����������д��Excel���ļ�·��Ϊ�ա�");
			return false;
		}
		File file=new File(filepath);
		if(file.exists()) {
			logger.error("���ܲ����������д��Excel���ļ�["+filepath+"]�Ѵ���");
			return false;
		}
		Workbook wb=new XSSFWorkbook();								//����������
		Sheet ondaysheet=wb.createSheet(SHEETNAME_DEM_ONDAY);		//��������sheet
		dematrix_onday.writeToExcelSheet(ondaysheet, new Location(0,0));	//д�����б�
		Sheet onweeksheet=wb.createSheet(SHEETNAME_DEM_ONWEEK);		//��������sheet
		dematrix_onweek.writeToExcelSheet(onweeksheet, new Location(0,0));	//д�����б�
		Sheet onmonthsheet=wb.createSheet(SHEETNAME_DEM_ONMONTH);	//��������sheet
		dematrix_onmonth.writeToExcelSheet(onmonthsheet, new Location(0,0));//д�����б�
		try {
			FileOutputStream fileOut=new FileOutputStream(filepath);
			wb.write(fileOut);
			fileOut.close();
			logger.info("�ɹ��������󱨸沢д��Excel�ļ�["+filepath+"]");
			return true;
		} catch(Throwable ex) {
			logger.error("���ܲ������󱨸沢д��Excel�����ִ���",ex);
			return false;
		}
	}
	
}