package com.logsys.report;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.logsys.demand.DemandBackupContent_Week;
import com.logsys.demand.DemandContent;
import com.logsys.demand.DemandContent_Month;
import com.logsys.demand.DemandContent_Week;
import com.logsys.demand.DemandDataReaderDB;
import com.logsys.demand.DemandUtil;
import com.logsys.model.ModelContent;
import com.logsys.model.ModelDataReaderDB;
import com.logsys.model.ModelUtil;
import com.logsys.util.DateInterval;
import com.logsys.util.DateTimeUtils;
import com.logsys.util.Location;
import com.logsys.util.Matrixable;

/**
 * ���󱨱�����:
 * --�������󱨱�
 * --�������󱨱�
 * --�������󱨱�
 * @author ShaonanLi
 */
public class DemandReportForExcel {

	private static final Logger logger=Logger.getLogger(DemandReportForExcel.class);
	
	/**����Ʒ˳��*/
	private Map<String,Integer> matorder_fin;
	
	/**����Ʒ�б�*/
	private List<ModelContent> matlist_fin;
	
	/**����Ʒͼ*/
	private Map<String,ModelContent> matmap_fin;
	
	/**����Ʒ��*/
	private Set<String> matset_fin;
	
	/**���������*/
	private Matrixable dematrix_onday;
	
	/**���������*/
	private Matrixable dematrix_onweek;
	
	/**���������*/
	private Matrixable dematrix_onmonth;
	
	/**׷�������������б�:��Ʒ��->�����������ͼ*/
	private Map<String,Matrixable> dematrixmap_backtrace;
	
	/**Sheet���ƣ���������*/
	private static final String SHEETNAME_DEM_ONDAY="Demand_Daily";
	
	/**Sheet���ƣ���������*/
	private static final String SHEETNAME_DEM_ONWEEK="Demand_Weekly";
	
	/**Sheet���ƣ���������*/
	private static final String SHEETNAME_DEM_ONMONTH="Demand_Monthly";
	
	/**Sheet���ƣ�������ٻ���*/
	private static final String SHEETNAME_DEM_BACKTRACE="Demand_Backtrace";
	
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
		matmap_fin=ModelUtil.convModelListToModelMap(matlist_fin);
		if(matmap_fin==null) return false;
		Calendar begindate;
		Calendar nullcal=null;
		begindate=DateTimeUtils.getFirstDayOfWeek(nullcal);				//��������Ͱ���������ʼ����Ϊ���ܵ�һ��
		dematrix_onday=genDemandMatrix_OnDay(begindate.getTime(),null);		//��ȡ��������
		if(dematrix_onday==null) return false;
		dematrix_onweek=genDemandMatrix_OnWeek(begindate.getTime(),null);	//��ȡ��������
		if(dematrix_onweek==null) return false;
		begindate=DateTimeUtils.getFirstDayOfMonth(nullcal);				//����������ʼ����Ϊ����1��
		dematrix_onmonth=genDemandMatrix_OnMonth(begindate.getTime(),null);	//��ȡ��������
		if(dematrix_onmonth==null) return false;
		begindate=DateTimeUtils.getFirstDayOfWeek(nullcal);				//��������б��������ʼ����Ϊ���ܵ�һ��
		dematrixmap_backtrace=genDemandMatrix_Backtrace(begindate,null);
		if(dematrixmap_backtrace==null) return false;
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
		for(String pn:matorder_fin.keySet())			//����Ʒ��д���б�ͷ
			demmat_onday.putRowHeaderCell(matorder_fin.get(pn)+1, pn);
		DateFormat dateconv=new SimpleDateFormat("yyyy/MM/dd");	//���ڸ�ʽ
		DateInterval interval=DemandUtil.getMinMaxDateInDemandList(demlist_onday);	//��ȡʱ����Сֵ�����ֵ
		Calendar begin=DateTimeUtils.getValidCalendar();
		Calendar end=DateTimeUtils.getValidCalendar();
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
	 * @param ��ʼʱ�䣬null������
	 * @param ����ʱ�䣬null������
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
	 * @param ��ʼʱ�䣬null������
	 * @param ����ʱ�䣬null������
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
	 * ���������������ͼ���б�ͷΪ�����ܣ��б�ͷΪ�汾�ܣ����ø��ܵ�һ��������ֵ 
	 * @param begindate ��ʼ����
	 * @param enddate ��������
	 * @return ��������������ͼ<��Ʒ��->�����������ͼ>
	 */
	private Map<String,Matrixable> genDemandMatrix_Backtrace(Calendar begindate, Calendar enddate) {
		List<DemandBackupContent_Week> bkupdemwklist=DemandDataReaderDB.getBackupDemandDataFromDB_OnWeek(null, begindate, enddate);	//��ȡ���������б�
		if(bkupdemwklist==null) {
			logger.error("���ܲ�������������󣬱��������ȡ����");
			return null;
		}
		Map<String,Matrixable> btracedemmap=new HashMap<String,Matrixable>();
		if(bkupdemwklist.size()==0)
			return btracedemmap;
		Map<String,Date> verIntervalMap=DemandUtil.getMinMaxVersionDateInBackupDemandList(bkupdemwklist);	//��ȡ�����ͺ����ֵİ汾����������Сֵ.
		Map<String,Date> demIntervalMap=DemandUtil.getMinMaxDemandDateInBackupDemandList(bkupdemwklist);	//��ȡ�����ͺ����ֵ���������������Сֵ.
		if(verIntervalMap==null) {
			logger.error("���ܲ���������������б����ܻ�ð����ͺ����ֵ������Сֵ��");
			return null;
		}
		for(String fertpn:matset_fin)					//������Ʒ�ţ���ʼ������ͼ
			btracedemmap.put(fertpn, new Matrixable());
		Calendar begincal;		//��ʼʱ��
		Calendar endcal;		//����ʱ��
		//д���б�ͷ����������
		begincal=DateTimeUtils.getFirstDayOfWeek(demIntervalMap.get(DemandUtil.PREFIX_MINDATE+DemandUtil.TOTAL_STR));	//ȷ����������ʼ����
		endcal=DateTimeUtils.getFirstDayOfWeek(demIntervalMap.get(DemandUtil.PREFIX_MAXDATE+DemandUtil.TOTAL_STR));		//ȷ���������������
		for(int counter=1;!begincal.after(endcal);begincal.add(Calendar.WEEK_OF_YEAR, 1),counter++)		//��������д�������ܣ����б�ͷ
			for(String pn:btracedemmap.keySet())			//������ÿ��Matrixableд���б�ͷ
				btracedemmap.get(pn).putColHeaderCell(counter, DateTimeUtils.getFormattedTimeStr_YearWeek(begincal));
		//д���б�ͷ�����汾��
		Matrixable tempMatrix;
		for(String fertpn:matset_fin) {						//������Ʒ�ţ�д�������Сʱ����
			if(verIntervalMap.containsKey(DemandUtil.PREFIX_MINDATE+fertpn))		//��ȡ��С����
				begincal.setTime(verIntervalMap.get(DemandUtil.PREFIX_MINDATE+fertpn));
			else {
				logger.info("��Ʒ����["+fertpn+"]û���κ��������ݣ��������ݾ���ʱ�������˳�Ʒ��");
				btracedemmap.remove(fertpn);				//���û�и�Ʒ�ŵ���С����Ҳ��û��������ڣ���û���������ݣ���ɾ���˻��ݾ������
				continue;
			}
			endcal.setTime(verIntervalMap.get(DemandUtil.PREFIX_MAXDATE+fertpn));	//�������С���ڣ����Ȼ�����������
			begincal=DateTimeUtils.getFirstDayOfWeek(begincal);	//��ʼ�汾���ڱ�Ϊ���������ܵ���һ
			endcal=DateTimeUtils.getFirstDayOfWeek(endcal);		//�����汾���ڱ�Ϊ���������ܵ���һ
			tempMatrix=btracedemmap.get(fertpn);			//��ȡ���ڱ�����Ʒ�ŵľ������
			for(int counter=1;!begincal.after(endcal);begincal.add(Calendar.WEEK_OF_YEAR, 1),counter++)	//��������д��汾�ܣ����б�ͷ
				tempMatrix.putRowHeaderCell(counter, DateTimeUtils.getFormattedTimeStr_YearWeek(begincal));
		}
		//�������������б�ȷ�ϻ����������ݣ�ÿ�������Ե��ܵ���������Ϊ��׼
		//Map<"FERTPN#DemandWeek#VersionWeek"->DemandBackupContent>����λ��ͨ��Ψһ��λʵ��ȷ�ϵ�����������  Request Demand Map--����Ҫ��������������ͼ
		Map<String,DemandBackupContent_Week> reqdemmap=new HashMap<String,DemandBackupContent_Week>();
		String locator;
		DemandBackupContent_Week tempcont;
		for(DemandBackupContent_Week bkupcont:bkupdemwklist) {	//�����������������б�д����ݾ���ͼ
			locator=bkupcont.getPn()+"#"				//���ɶ�λ�ַ�������Ҫȫ�����ܵĵ�һ��������
					+DateTimeUtils.getFormattedTimeStr_YearWeek(DateTimeUtils.getFirstDayOfWeek(bkupcont.getDate()))+"#"
					+DateTimeUtils.getFormattedTimeStr_YearWeek(DateTimeUtils.getFirstDayOfWeek(bkupcont.getVersion()));
			if(!reqdemmap.containsKey(locator))			//���û�У���д��
				reqdemmap.put(locator, bkupcont);
			else {
				tempcont=reqdemmap.get(locator);
				if(bkupcont.getVersion().before(tempcont.getVersion()))		//������ָ����Version�汾�����滻�����������
					reqdemmap.put(locator, bkupcont);
			}
		}
		//����Request Demand Map--����Ҫ��������������ͼ������������������
		String[] locarr;		//��ֺ�Ķ�λ���� 0:pn 1:DemandWeek 2:VersionWeek
		for(String locstr:reqdemmap.keySet()) {
			//System.out.println(locstr+"---"+reqdemmap.get(locstr));
			locarr=locstr.split("#");
			btracedemmap.get(locarr[0]).setData(locarr[2], locarr[1], new Double(reqdemmap.get(locstr).getQty()));	//д����������
		}
		return btracedemmap;
	}
	
	/**
	 * ������д��Excel�ļ�
	 * @param filepath Ҫд����ļ�·��
	 * @param genBackTraceSheet �Ƿ�д������������
	 * @return �ɹ�true/ʧ��false
	 */
	public boolean writeReportToFile(String filepath, boolean genBackTraceSheet) {
		if(filepath==null) {
			logger.error("���ܲ����������д��Excel���ļ�·��Ϊ�ա�");
			return false;
		}
		File file=new File(filepath);
		if(file.exists()) {
			logger.error("���ܲ����������д��Excel���ļ�["+filepath+"]�Ѵ���");
			return false;
		}
		Workbook wb=new XSSFWorkbook();			//����������
		writeOnDayDemandToWorkbook(wb);			//д�밴����������
		writeOnWeekDemandToWorkbook(wb);		//д�밴����������
		writeOnMonthDemandToWorkbook(wb);		//д�밴����������
		if(genBackTraceSheet)
			writeBacktraceDemandToWorkbook(wb);	//д�������������
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
	
	/**
	 * ����������д��Excel����������
	 * @param wb Excel����������
	 */
	private void writeOnDayDemandToWorkbook(Workbook wb) {
		if(wb==null) {
			logger.error("�����ڹ�������д�밴���������ݣ�����������Ϊ�ա�");
			return;
		}
		final int startrow=1;			//������ʼ��
		final int startcol=2;			//������ʼ��
		final int colwidth=12;			//�п�ȣ�12�ַ�
		Sheet ondaysheet=wb.createSheet(SHEETNAME_DEM_ONDAY);								//��������sheet
		//��ʼ���õ�Ԫ���ʽ
		CellStyle weekdaystyle = wb.createCellStyle();		//һ�����쵥Ԫ���ʽ
		CellStyle datestyle = wb.createCellStyle();			//���ڵ�Ԫ���ʽ
		CellStyle weekstyle = wb.createCellStyle();			//���ڵ�Ԫ���ʽ
		CellStyle clientstyle = wb.createCellStyle();		//�ͻ���Ԫ���ʽ
		CreationHelper createHelper = wb.getCreationHelper();
		weekdaystyle.setDataFormat(createHelper.createDataFormat().getFormat("aaaa"));		//����һ�����쵥Ԫ���ʽ
		weekdaystyle.setAlignment(CellStyle.ALIGN_CENTER);
		weekdaystyle.setBorderTop(CellStyle.BORDER_THIN);
		weekdaystyle.setBorderBottom(CellStyle.BORDER_THIN);
		weekdaystyle.setBorderLeft(CellStyle.BORDER_THIN);
		weekdaystyle.setBorderRight(CellStyle.BORDER_THIN);
		datestyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd"));	//�������ڵ�Ԫ���ʽ
		datestyle.setAlignment(CellStyle.ALIGN_CENTER);
		datestyle.setBorderTop(CellStyle.BORDER_THIN);
		datestyle.setBorderBottom(CellStyle.BORDER_THIN);
		datestyle.setBorderLeft(CellStyle.BORDER_THIN);
		datestyle.setBorderRight(CellStyle.BORDER_THIN);
		weekstyle.setAlignment(CellStyle.ALIGN_CENTER);										//�������ڵ�Ԫ���ʽ
		weekstyle.setBorderLeft(CellStyle.BORDER_THIN);
		weekstyle.setBorderRight(CellStyle.BORDER_THIN);
		clientstyle.setAlignment(CellStyle.ALIGN_CENTER);									//���ÿͻ���Ԫ���ʽ
		clientstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		clientstyle.setWrapText(true);		//������
		//��ʼд�����ڼ���WK��Ϣ
		dematrix_onday.writeToExcelSheet(ondaysheet, new Location(startrow,startcol));		//д�����б�
		Row daterow=ondaysheet.getRow(startrow);			//��ȡ������������
		Row weekdayrow=ondaysheet.createRow(startrow+1);	//����һ��7����������
		Row weekrow=ondaysheet.createRow(startrow-1);		//����������������
		Cell weekdaycell;		//weekday��Ԫ��
		Cell datecell;			//���ڵ�Ԫ��
		Cell weekcell;			//������Ԫ��
		Calendar itcal=DateTimeUtils.getValidCalendar();		//���ڱ���������
		for(int counter=startcol+1;daterow.getCell(counter)!=null;counter++) {	//����������,д�����ڼ���������
			datecell=daterow.getCell(counter);
			itcal.setTime(new Date(datecell.getStringCellValue()));
			weekdaycell=weekdayrow.createCell(counter);
			weekdaycell.setCellValue(itcal);			//д������������Ϣ
			datecell.setCellValue(itcal);				//�����ڸ�ʽ����д���ͷ��Ϣ
			weekdaycell.setCellStyle(weekdaystyle);		//д���������ڸ�ʽ
			datecell.setCellStyle(datestyle);			//д�����ڸ�ʽ
			if((counter-startcol)%7==0) {				//����Ѿ�����7�죬��ϲ�weekrow��7����Ԫ�񣬲�д��������Ϣ
				weekcell=weekrow.createCell(counter-6);			//������Ԫ��
				weekcell.setCellStyle(weekstyle);
				weekcell.setCellValue(itcal.get(Calendar.YEAR)+"��"+itcal.get(Calendar.WEEK_OF_YEAR)+"��");	//д������
				ondaysheet.addMergedRegion(new CellRangeAddress(weekrow.getRowNum(),weekrow.getRowNum(),counter-6,counter));	//�ϲ���Ԫ��
			}
			ondaysheet.setColumnWidth(counter, colwidth*256);	//�����п��
		}
		//��ʼд��ͻ����ͺ���Ϣ
		String lastclient="NOTCLIENT";	//��һ���ͻ�
		String curclient;		//��ǰ�ͻ�
		int clientbegin=-1;		//clientbegin��ʼ��
		ModelContent temp;
		Cell clientcell;		//�ͻ�cell
		Cell infocell;			//��Ŀcell
		Cell pncell;			//��Ʒcell
		for(int counter=startrow+2;;counter++) {	//����pn�У���д
			if(ondaysheet.getRow(counter)==null) {	//����ǿ��У�����Ҫ�ϲ���һ����Ԫ��Ŀͻ���Ԫ��
				if(clientbegin!=-1)	{		//���Ϊ-1����˵���ǵ�һ���ͻ�,��ʼ��clientbegin��lastclient
					clientcell=ondaysheet.getRow(clientbegin).createCell(startcol-2);	//�����ͻ�Cell
					clientcell.setCellValue(lastclient);
					clientcell.setCellStyle(clientstyle);
					ondaysheet.addMergedRegion(new CellRangeAddress(clientbegin,counter-1,startcol-2,startcol-2));	//�ϲ���Ԫ��
				}
				break;
			}
			pncell=ondaysheet.getRow(counter).getCell(startcol);
			if(pncell==null) break;
			temp=matmap_fin.get(pncell.getStringCellValue());				//��ȡpn��Model����
			if(temp==null) {
				logger.error("�����ڹ�������д�밴���������ݣ���Ʒͼ��û�ж�Ӧ�ĳ�Ʒ��["+pncell.getStringCellValue()+"]");
				return;
			}
			curclient=temp.getClient()+"\n"+temp.getPrjcode();	//ȷ�ϵ�ǰ�ͻ�
			if(!curclient.equals(lastclient)) {					//�����ǰ�ͻ�������ǰ�ͻ�����˵���¿ͻ���ʼ�ˣ���Ҫ�ϲ��Ͽͻ���Ԫ�񣬲�д���Ͽͻ���Ϣ
				if(clientbegin!=-1)	{		//���Ϊ-1����˵���ǵ�һ���ͻ�,��ʼ��clientbegin��lastclient
					clientcell=ondaysheet.getRow(clientbegin).createCell(startcol-2);	//�����ͻ�Cell
					clientcell.setCellValue(lastclient);
					clientcell.setCellStyle(clientstyle);
					ondaysheet.addMergedRegion(new CellRangeAddress(clientbegin,counter-1,startcol-2,startcol-2));	//�ϲ���Ԫ��
				}
				clientbegin=counter;
				lastclient=curclient;
			}
			infocell=ondaysheet.getRow(counter).createCell(startcol-1);		//����infocell
			infocell.setCellValue(temp.getInfo());
		}
		ondaysheet.setColumnWidth(startcol-2, 12*256);			//���ÿͻ��п��
		ondaysheet.setColumnWidth(startcol-1, 15*256);			//�����ͺź����
		ondaysheet.createFreezePane(startcol+1, startrow+2);	//���ᴰ��
		Row itrow;			//������
		Cell itcell;		//������Ԫ��
		double demqty;		//��������
		boolean hasdemand=false;	//�Ƿ�������
		//û�������¼�����Զ�����
		for(int pncounter=startrow+1;;pncounter++) {	//��ѭ��������
			itrow=ondaysheet.getRow(pncounter);			//��ȡ������
			if(itrow==null) break;
			hasdemand=false;
			for(int colcounter=startcol+1;;colcounter++) {		//��ѭ��������Ԫ��
				itcell=itrow.getCell(colcounter);		//��ȡ������Ԫ��
				datecell=daterow.getCell(colcounter);	//��ȡ���ڵ�Ԫ��
				if(datecell==null) break;			//���������Ϊ�գ�������ѭ��
				if(itcell==null) continue;			//�����Ԫ��Ϊ�գ���������
				else {
					demqty=itcell.getNumericCellValue();	//��ȡ����ֵ
					if(demqty==0) continue;			//�������Ϊ0�������
					else {
						hasdemand=true;				//�����Ϊ0��˵����������������
						break;
					}
				}
			}
			if(!hasdemand)	//���û�����������ظ���
				itrow.setZeroHeight(true);
		}
	}
	
	/**
	 * ����������д��Excel����������
	 * @param wb Excel����������
	 */
	private void writeOnWeekDemandToWorkbook(Workbook wb) {
		if(wb==null) {
			logger.error("�����ڹ�������д�밴���������ݣ�����������Ϊ�ա�");
			return;
		}
		final int startrow=0;			//������ʼ��
		final int startcol=2;			//������ʼ��
		final int colwidth=12;			//�п�ȣ�12�ַ�
		Sheet onweeksheet=wb.createSheet(SHEETNAME_DEM_ONWEEK);		//��������sheet
		//��ʼ���õ�Ԫ���ʽ
		CellStyle weekstyle = wb.createCellStyle();			//���ڵ�Ԫ���ʽ
		CellStyle clientstyle = wb.createCellStyle();		//�ͻ���Ԫ���ʽ
		weekstyle.setAlignment(CellStyle.ALIGN_CENTER);		//���ð��ܵ�Ԫ���ʽ
		weekstyle.setBorderLeft(CellStyle.BORDER_THIN);
		weekstyle.setBorderRight(CellStyle.BORDER_THIN);
		clientstyle.setAlignment(CellStyle.ALIGN_CENTER);	//���ÿͻ���Ԫ���ʽ
		clientstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		clientstyle.setWrapText(true);		//������
		//��ʼ��������Ϣд���ʽ
		dematrix_onweek.writeToExcelSheet(onweeksheet, new Location(startrow,startcol));	//д�����б�
		Row weekrow=onweeksheet.getRow(startrow);			//����������������
		Cell weekcell;			//������Ԫ��
		for(int counter=startcol+1;weekrow.getCell(counter)!=null;counter++) {	//��������,�ı䵥Ԫ���ʽ
			weekcell=weekrow.getCell(counter);
			weekcell.setCellStyle(weekstyle);
			onweeksheet.setColumnWidth(counter, colwidth*256);	//�����п��
		}
		//��ʼд��ͻ����ͺ���Ϣ
		String lastclient="NOTCLIENT";	//��һ���ͻ�
		String curclient;		//��ǰ�ͻ�
		int clientbegin=-1;		//clientbegin��ʼ��
		ModelContent temp;
		Cell clientcell;		//�ͻ�cell
		Cell infocell;			//��Ŀcell
		Cell pncell;			//��Ʒcell
		for(int counter=startrow+1;;counter++) {	//����pn�У���д
			if(onweeksheet.getRow(counter)==null) {	//����ǿ��У�����Ҫ�ϲ���һ����Ԫ��Ŀͻ���Ԫ��
				if(clientbegin!=-1)	{				//���Ϊ-1����˵���ǵ�һ���ͻ�,��ʼ��clientbegin��lastclient
					clientcell=onweeksheet.getRow(clientbegin).createCell(startcol-2);	//�����ͻ�Cell
					clientcell.setCellValue(lastclient);
					clientcell.setCellStyle(clientstyle);
					onweeksheet.addMergedRegion(new CellRangeAddress(clientbegin,counter-1,startcol-2,startcol-2));	//�ϲ���Ԫ��
				}
				break;
			}
			pncell=onweeksheet.getRow(counter).getCell(startcol);
			if(pncell==null) break;
			temp=matmap_fin.get(pncell.getStringCellValue());	//��ȡpn��Model����
			if(temp==null) {
				logger.error("�����ڹ�������д�밴���������ݣ���Ʒͼ��û�ж�Ӧ�ĳ�Ʒ��["+pncell.getStringCellValue()+"]");
				return;
			}
			curclient=temp.getClient()+"\n"+temp.getPrjcode();	//ȷ�ϵ�ǰ�ͻ�
			if(!curclient.equals(lastclient)) {					//�����ǰ�ͻ�������ǰ�ͻ�����˵���¿ͻ���ʼ�ˣ���Ҫ�ϲ��Ͽͻ���Ԫ�񣬲�д���Ͽͻ���Ϣ
				if(clientbegin!=-1)	{							//���Ϊ-1����˵���ǵ�һ���ͻ�,��ʼ��clientbegin��lastclient
					clientcell=onweeksheet.getRow(clientbegin).createCell(startcol-2);	//�����ͻ�Cell
					clientcell.setCellValue(lastclient);
					clientcell.setCellStyle(clientstyle);
					onweeksheet.addMergedRegion(new CellRangeAddress(clientbegin,counter-1,startcol-2,startcol-2));	//�ϲ���Ԫ��
				}
				clientbegin=counter;
				lastclient=curclient;
			}
			infocell=onweeksheet.getRow(counter).createCell(startcol-1);		//����infocell
			infocell.setCellValue(temp.getInfo());
		}
		onweeksheet.setColumnWidth(startcol-2, 12*256);			//���ÿͻ��п��
		onweeksheet.setColumnWidth(startcol-1, 15*256);			//�����ͺź����
		onweeksheet.createFreezePane(startcol+1, startrow+1);	//���ᴰ��
		Row itrow;			//������
		Cell itcell;		//������Ԫ��
		double demqty;		//��������
		boolean hasdemand=false;	//�Ƿ�������
		//û�������¼�����Զ�����
		for(int pncounter=startrow+1;;pncounter++) {	//��ѭ��������
			itrow=onweeksheet.getRow(pncounter);		//��ȡ������
			if(itrow==null) break;
			hasdemand=false;
			for(int colcounter=startcol+1;;colcounter++) {		//��ѭ��������Ԫ��
				itcell=itrow.getCell(colcounter);		//��ȡ������Ԫ��
				weekcell=weekrow.getCell(colcounter);	//��ȡ���ڵ�Ԫ��
				if(weekcell==null) break;			//���������Ϊ�գ�������ѭ��
				if(itcell==null) continue;			//�����Ԫ��Ϊ�գ���������
				else {
					demqty=itcell.getNumericCellValue();	//��ȡ����ֵ
					if(demqty==0) continue;			//�������Ϊ0�������
					else {
						hasdemand=true;				//�����Ϊ0��˵����������������
						break;
					}
				}
			}
			if(!hasdemand)	//���û�����������ظ���
				itrow.setZeroHeight(true);
		}
	}
	
	/**
	 * ����������д��Excel����������
	 * @param wb Excel����������
	 */
	private void writeOnMonthDemandToWorkbook(Workbook wb) {
		if(wb==null) {
			logger.error("�����ڹ�������д�밴���������ݣ�����������Ϊ�ա�");
			return;
		}
		final int startrow=0;			//������ʼ��
		final int startcol=2;			//������ʼ��
		final int colwidth=12;			//�п�ȣ�12�ַ�
		Sheet onmonthsheet=wb.createSheet(SHEETNAME_DEM_ONMONTH);	//��������sheet
		//��ʼ���õ�Ԫ���ʽ
		CellStyle monthstyle = wb.createCellStyle();		//���µ�Ԫ���ʽ
		CellStyle clientstyle = wb.createCellStyle();		//�ͻ���Ԫ���ʽ
		monthstyle.setAlignment(CellStyle.ALIGN_CENTER);	//���ð��µ�Ԫ���ʽ
		monthstyle.setBorderLeft(CellStyle.BORDER_THIN);
		monthstyle.setBorderRight(CellStyle.BORDER_THIN);
		clientstyle.setAlignment(CellStyle.ALIGN_CENTER);	//���ÿͻ���Ԫ���ʽ
		clientstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		clientstyle.setWrapText(true);	//������
		//��ʼ��������Ϣд���ʽ
		dematrix_onmonth.writeToExcelSheet(onmonthsheet, new Location(startrow,startcol));	//д�����б�
		Row monthrow=onmonthsheet.getRow(startrow);			//����������������
		Cell monthcell;			//������Ԫ��
		for(int counter=startcol+1;monthrow.getCell(counter)!=null;counter++) {	//��������,�ı䵥Ԫ���ʽ
			monthcell=monthrow.getCell(counter);
			monthcell.setCellStyle(monthstyle);
			onmonthsheet.setColumnWidth(counter, colwidth*256);	//�����п��
		}
		//��ʼд��ͻ����ͺ���Ϣ
		String lastclient="NOTCLIENT";	//��һ���ͻ�
		String curclient;		//��ǰ�ͻ�
		int clientbegin=-1;		//clientbegin��ʼ��
		ModelContent temp;
		Cell clientcell;		//�ͻ�cell
		Cell infocell;			//��Ŀcell
		Cell pncell;			//��Ʒcell
		for(int counter=startrow+1;;counter++) {		//����pn�У���д
			if(onmonthsheet.getRow(counter)==null) {	//����ǿ��У�����Ҫ�ϲ���һ����Ԫ��Ŀͻ���Ԫ��
				if(clientbegin!=-1)	{					//���Ϊ-1����˵���ǵ�һ���ͻ�,��ʼ��clientbegin��lastclient
					clientcell=onmonthsheet.getRow(clientbegin).createCell(startcol-2);	//�����ͻ�Cell
					clientcell.setCellValue(lastclient);
					clientcell.setCellStyle(clientstyle);
					onmonthsheet.addMergedRegion(new CellRangeAddress(clientbegin,counter-1,startcol-2,startcol-2));	//�ϲ���Ԫ��
				}
				break;
			}
			pncell=onmonthsheet.getRow(counter).getCell(startcol);
			if(pncell==null) break;
			temp=matmap_fin.get(pncell.getStringCellValue());	//��ȡpn��Model����
			if(temp==null) {
				logger.error("�����ڹ�������д�밴���������ݣ���Ʒͼ��û�ж�Ӧ�ĳ�Ʒ��["+pncell.getStringCellValue()+"]");
				return;
			}
			curclient=temp.getClient()+"\n"+temp.getPrjcode();	//ȷ�ϵ�ǰ�ͻ�
			if(!curclient.equals(lastclient)) {					//�����ǰ�ͻ�������ǰ�ͻ�����˵���¿ͻ���ʼ�ˣ���Ҫ�ϲ��Ͽͻ���Ԫ�񣬲�д���Ͽͻ���Ϣ
				if(clientbegin!=-1)	{							//���Ϊ-1����˵���ǵ�һ���ͻ�,��ʼ��clientbegin��lastclient
					clientcell=onmonthsheet.getRow(clientbegin).createCell(startcol-2);	//�����ͻ�Cell
					clientcell.setCellValue(lastclient);
					clientcell.setCellStyle(clientstyle);
					onmonthsheet.addMergedRegion(new CellRangeAddress(clientbegin,counter-1,startcol-2,startcol-2));	//�ϲ���Ԫ��
				}
				clientbegin=counter;
				lastclient=curclient;
			}
			infocell=onmonthsheet.getRow(counter).createCell(startcol-1);		//����infocell
			infocell.setCellValue(temp.getInfo());
		}
		onmonthsheet.setColumnWidth(startcol-2, 12*256);			//���ÿͻ��п��
		onmonthsheet.setColumnWidth(startcol-1, 15*256);			//�����ͺź����
		onmonthsheet.createFreezePane(startcol+1, startrow+1);		//���ᴰ��
		Row itrow;			//������
		Cell itcell;		//������Ԫ��
		double demqty;		//��������
		boolean hasdemand=false;	//�Ƿ�������
		//û�������¼�����Զ�����
		for(int pncounter=startrow+1;;pncounter++) {			//��ѭ��������
			itrow=onmonthsheet.getRow(pncounter);		//��ȡ������
			if(itrow==null) break;
			hasdemand=false;
			for(int colcounter=startcol+1;;colcounter++) {		//��ѭ��������Ԫ��
				itcell=itrow.getCell(colcounter);		//��ȡ������Ԫ��
				monthcell=monthrow.getCell(colcounter);	//��ȡ���ڵ�Ԫ��
				if(monthcell==null) break;			//���������Ϊ�գ�������ѭ��
				if(itcell==null) continue;			//�����Ԫ��Ϊ�գ���������
				else {
					demqty=itcell.getNumericCellValue();	//��ȡ����ֵ
					if(demqty==0) continue;			//�������Ϊ0�������
					else {
						hasdemand=true;				//�����Ϊ0��˵����������������
						break;
					}
				}
			}
			if(!hasdemand)	//���û�����������ظ���
				itrow.setZeroHeight(true);
		}
	}
	
	/**
	 * �����������д��Excel������
	 * @param wb Excel����������
	 */
	private void writeBacktraceDemandToWorkbook(Workbook wb) {
		if(wb==null) {
			logger.error("�����ڹ�������д������������ݣ�����������Ϊ�ա�");
			return;
		}
		int startrow=1;		//��ʼ��
		int startcol=0;		//��ʼ��
		Sheet btracesheet=wb.createSheet(SHEETNAME_DEM_BACKTRACE);
		Matrixable fertMatrix;		//��ƷMatrix����
		String fertpn;
		BiMap<Integer,String> fertorderbimap=HashBiMap.create(this.matorder_fin).inverse();	//��˳��ͼת��Ϊ˫��ͼ
		for(int counter=1;fertorderbimap.containsKey(counter);counter++) {	//����˳��
			fertpn=fertorderbimap.get(counter);				//��ȡpn
			fertMatrix=dematrixmap_backtrace.get(fertpn);	//��ȡ����
			if(fertMatrix==null) {
				logger.info("û���ҵ���Ʒ��["+fertpn+"]����Ӧ�ľ������,Ӧ���Ǹó�Ʒ������");
				continue;
			}
			fertMatrix.writeToExcelSheet(btracesheet, new Location(startrow,startcol));	//д��Sheet
			Cell fertpncell=btracesheet.getRow(startrow).createCell(startcol);
			fertpncell.setCellValue(fertpn);
			startrow+=(fertMatrix.getRowHeaderSize()+2);	//��startrow������д����������
		}
	}
	
}