package com.logsys.report;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.demand.DemandContent_Week;
import com.logsys.demand.DemandDataReaderDB;
import com.logsys.model.ModelService;
import com.logsys.prodplan.ProdplanContent_PrdLineCombined;
import com.logsys.prodplan.ProdplanDataReaderDB;
import com.logsys.util.DateInterval;
import com.logsys.util.DateTimeUtils;
import com.logsys.util.Matrixable;

/**
 * SAP��MRP�ϴ�����
 * @author lx8sn6
 */
public class SAPMrpUploadReport {
	
	private static final Logger logger=Logger.getLogger(SAPMrpUploadReport.class);
	
	/**�ϴ��ƻ�ʱǰ�õİ���ƻ�������*/
	private static final int ONDAYPLAN_WEEKNUM=2;

	/**�ϲ������ߵ������ƻ��б�*/
	private List<ProdplanContent_PrdLineCombined> pplist;
	
	/**���ܵ������б�*/
	private List<DemandContent_Week> wkdemlist;
	
	/**������Model�б�*/
	private Map<String,Integer> sortedmodelmap;
	
	/**�������ݵľ������*/
	private Matrixable datamatrix;
	
	/**Workbook����*/
	private Workbook wb;
	
	/**����+�ƻ��ܹ�������week��*/
	private int totalweek;
	
	/**
	 * ���캯��
	 * @param wkbk workbook����
	 * @param totalweek ����+�ƻ��ܹ�������week��
	 */
	private SAPMrpUploadReport(Workbook wkbk, int weeknum) {
		wb=wkbk;
		if(weeknum<ONDAYPLAN_WEEKNUM) totalweek=ONDAYPLAN_WEEKNUM;
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
		Workbook wkbk;
		try {
			wkbk=WorkbookFactory.create(file);
		} catch (Exception ex) {
			logger.error("��������SAPMrp�ϴ��������,����Workbook����ʱ���ִ���",ex);
			return null;
		}
		SAPMrpUploadReport report=new SAPMrpUploadReport(wkbk,weeknum);
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
		Calendar begin=DateTimeUtils.getValidCalendar();
		Calendar end=DateTimeUtils.getValidCalendar();
		begin.clear();
		begin.setWeekDate(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR), Calendar.MONDAY);
		end.clear();
		end.setWeekDate(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR)+ONDAYPLAN_WEEKNUM-1, Calendar.SUNDAY);
		pplist=ProdplanDataReaderDB.getProdLineCombinedPPList(new DateInterval(begin.getTime(),end.getTime()));
		if(pplist==null) {
			logger.error("��ʼ��ʧ��,���ܲ��ܺϲ������ߵ������ƻ��б�");
			return false;
		}
		end.clear();
		end.setWeekDate(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR)+totalweek-1, Calendar.SUNDAY);
		wkdemlist=DemandDataReaderDB.getDemandDataFromDB_OnWeek(null, begin.getTime(), end.getTime());
		if(wkdemlist==null) {
			logger.error("��ʼ��ʧ�ܣ����ܲ������������б�");
			return false;
		}
		sortedmodelmap=ModelService.getSortedModelMap();
		if(sortedmodelmap==null) {
			logger.error("��ʼ��ʧ�ܣ����ܲ���������Modelͼ.");
			return false;
		}
		datamatrix=new Matrixable();
		return true;
	}
	
	/**
	 * ���ɾ������ݶ���
	 * @return �ɹ�true/ʧ��false
	 */
	public boolean generateDataMatrix() {
		return false;
	}

}
