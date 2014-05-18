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
		begindate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);		//��ʼ����Ϊ������һ
		dematrix_onday=genDemandMatrix_OnDay(begindate.getTime(),null);	//��ȡ����ƻ�
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
		Matrixable dem_onday=new Matrixable();		//���������������
		if(demlist_onday.size()==0) return dem_onday;
		for(String pn:matorder_fin.keySet())		//����Ʒ��д���б�ͷ
			dem_onday.putRowHeaderCell(matorder_fin.get(pn), pn);
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
		for(int counter=1;!begin.after(end);begin.add(Calendar.DAY_OF_YEAR, 1),counter++)	//������д���б�ͷ,�ӵ����п�ʼ
			dem_onday.putColHeaderCell(counter, dateconv.format(begin.getTime()));
		boolean result;
		for(DemandContent demcont:demlist_onday) { //д����������
			result=dem_onday.setData(demcont.getPn(), dateconv.format(demcont.getDate()), (Double)demcont.getQty());
			if(!result) {
				logger.error("�ھ��������д������ʧ�ܡ�");
				return null;
			}
		}
		return dem_onday;
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
		Sheet ondaysheet=wb.createSheet("Demand_Daily");			//����sheet
		dematrix_onday.writeToExcelSheet(ondaysheet, new Location(0,0));
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