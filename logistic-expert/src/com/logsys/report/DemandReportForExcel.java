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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
 * 需求报表--按日的Sheet，按周的Sheet和按月的Sheet
 * @author ShaonanLi
 */
public class DemandReportForExcel {

	private static final Logger logger=Logger.getLogger(DemandReportForExcel.class);
	
	/**产成品顺序*/
	private Map<String,Integer> matorder_fin;
	
	/**产成品列表*/
	private List<ModelContent> matlist_fin;
	
	/**产成品图*/
	private Map<String,ModelContent> matmap_fin;
	
	/**产成品集*/
	private Set<String> matset_fin;
	
	/**按天需求表*/
	private Matrixable dematrix_onday;
	
	/**按周需求表*/
	private Matrixable dematrix_onweek;
	
	/**按月需求表*/
	private Matrixable dematrix_onmonth;
	
	/**Sheet名称：按天需求*/
	private static final String SHEETNAME_DEM_ONDAY="Demand_Daily";
	
	/**Sheet名称：按周需求*/
	private static final String SHEETNAME_DEM_ONWEEK="Demand_Weekly";
	
	/**Sheet名称：按月需求*/
	private static final String SHEETNAME_DEM_ONMONTH="Demand_Monthly";
	
	public DemandReportForExcel() throws Exception {
		if(!init()) {
			logger.error("初始化需求报告失败。");
			throw new Exception("初始化需求报告失败。");
		}
	}
	
	private boolean init() {
		matlist_fin=ModelDataReaderDB.getDataFromDB(null);			//初始化成品列表，获取所有Model
		if(matlist_fin==null) return false;
		matset_fin=ModelUtil.getModelSet(matlist_fin);				//初始化成品集
		if(matset_fin==null) return false;
		matorder_fin=ModelDataReaderDB.sortModels(matset_fin);		//初始化成品顺序图
		if(matorder_fin==null) return false;
		matmap_fin=ModelUtil.convModelListToModelMap(matlist_fin);
		if(matmap_fin==null) return false;
		Calendar begindate=GeneralUtils.getValidCalendar();
		Calendar now=GeneralUtils.getValidCalendar();
		begindate.clear();
		begindate.set(Calendar.YEAR,now.get(Calendar.YEAR));
		begindate.set(Calendar.WEEK_OF_YEAR, now.get(Calendar.WEEK_OF_YEAR));
		begindate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);				//按天计划和安周计划起始日期为本周周一
		dematrix_onday=genDemandMatrix_OnDay(begindate.getTime(),null);		//获取按天计划
		if(dematrix_onday==null) return false;
		dematrix_onweek=genDemandMatrix_OnWeek(begindate.getTime(),null);	//获取按周计划
		if(dematrix_onweek==null) return false;
		begindate.clear();
		begindate.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1);	//按月计划起始日期
		dematrix_onmonth=genDemandMatrix_OnMonth(begindate.getTime(),null);	//获取按月计划
		if(dematrix_onmonth==null) return false;
		return true;
	}
	
	/**
	 * 获取按天需求数据矩阵
	 * @return 按天需求矩阵对象
	 */
	private Matrixable genDemandMatrix_OnDay(Date begindate, Date enddate) {
		List<DemandContent> demlist_onday=DemandDataReaderDB.getDemandDataFromDB_OnDay(null, begindate, enddate);	//获得按天需求元数据
		if(demlist_onday==null) {
			logger.error("不能生成按天的需求矩阵，按天需求列表获取错误。");
			return null;
		}
		Matrixable demmat_onday=new Matrixable();		//声明按天需求矩阵
		if(demlist_onday.size()==0) return demmat_onday;
		for(String pn:matorder_fin.keySet())			//将成品号写入行表头
			demmat_onday.putRowHeaderCell(matorder_fin.get(pn)+2, pn);
		DateFormat dateconv=new SimpleDateFormat("yyyy/MM/dd");	//日期格式
		DateInterval interval=DemandUtil.getMinMaxDateInDemandList(demlist_onday);	//获取时间最小值和最大值
		Calendar begin=GeneralUtils.getValidCalendar();
		Calendar end=GeneralUtils.getValidCalendar();
		if(begindate==null)							//设置起始时间
			begin.setTime(interval.begindate);
		else
			begin.setTime(begindate);
		if(enddate==null)							//设置结束时间
			end.setTime(interval.enddate);
		else
			end.setTime(enddate);
		for(int counter=1;!begin.after(end);begin.add(Calendar.DAY_OF_YEAR, 1),counter++)	//将日期写入列表头
			demmat_onday.putColHeaderCell(counter, dateconv.format(begin.getTime()));
		boolean result;
		for(DemandContent demcont:demlist_onday) { //写入需求数据
			result=demmat_onday.setData(demcont.getPn(), dateconv.format(demcont.getDate()), (Double)demcont.getQty());
			if(!result) {
				logger.error("在矩阵对象中写入数据失败。");
				return null;
			}
		}
		return demmat_onday;
	}
	
	/**
	 * 获取按周需求数据矩阵
	 * @return 按周需求矩阵对象
	 */
	private Matrixable genDemandMatrix_OnWeek(Date begindate, Date enddate) {
		List<DemandContent_Week> demlist_onweek=DemandDataReaderDB.getDemandDataFromDB_OnWeek(null, begindate, enddate);	//获得按周需求元数据
		if(demlist_onweek==null) {
			logger.error("不能生成按周的需求矩阵，按周需求列表获取错误。");
			return null;
		}
		Matrixable demmat_onweek=new Matrixable();		//声明按周需求矩阵
		if(demlist_onweek.size()==0) return demmat_onweek;
		for(String pn:matorder_fin.keySet())		//将成品号写入行表头
			demmat_onweek.putRowHeaderCell(matorder_fin.get(pn), pn);
		int colcounter=0;		//列索引
		String colheader;		//列表头
		for(DemandContent_Week weekdem:demlist_onweek)	{ //循环写入表头和需求数据
			colheader=weekdem.getYear()+"年"+weekdem.getWeek()+"周";	//列表头
			if(!demmat_onweek.isContentInColHeader(colheader))			//如果不存在于列表头中，则加入列表头
				demmat_onweek.putColHeaderCell(++colcounter, colheader);
			demmat_onweek.setData(weekdem.getPn(), colheader, weekdem.getQty()); //写入数据
		}
		return demmat_onweek;
	}
	
	/**
	 * 获取按月需求数据矩阵
	 * @return 按月需求矩阵对象
	 */
	private Matrixable genDemandMatrix_OnMonth(Date begindate, Date enddate) {
		List<DemandContent_Month> demlist_onmonth=DemandDataReaderDB.getDemandDataFromDB_OnMonth(null, begindate, enddate);	//获得按周需求元数据
		if(demlist_onmonth==null) {
			logger.error("不能生成按周的需求矩阵，按周需求列表获取错误。");
			return null;
		}
		Matrixable demmat_onmonth=new Matrixable();		//声明按天需求矩阵
		if(demlist_onmonth.size()==0) return demmat_onmonth;
		for(String pn:matorder_fin.keySet())		//将成品号写入行表头
			demmat_onmonth.putRowHeaderCell(matorder_fin.get(pn), pn);
		int colcounter=0;		//列索引
		String colheader;		//列表头
		for(DemandContent_Month monthdem:demlist_onmonth)	{ //循环写入表头和需求数据
			colheader=monthdem.getYear()+"年"+monthdem.getMonth()+"月";	//列表头
			if(!demmat_onmonth.isContentInColHeader(colheader))			//如果不存在于列表头中，则加入列表头
				demmat_onmonth.putColHeaderCell(++colcounter, colheader);
			demmat_onmonth.setData(monthdem.getPn(), colheader, monthdem.getQty());	//写入数据
		}
		return demmat_onmonth;
	}
	
	/**
	 * 将报告写入Excel文件
	 * @param filepath 要写入的文件路径
	 * @return 成功true/失败false
	 */
	public boolean writeReportToFile(String filepath) {
		if(filepath==null) {
			logger.error("不能产生需求矩阵并写入Excel，文件路径为空。");
			return false;
		}
		File file=new File(filepath);
		if(file.exists()) {
			logger.error("不能产生需求矩阵并写入Excel，文件["+filepath+"]已存在");
			return false;
		}
		Workbook wb=new XSSFWorkbook();			//创建工作簿
		writeOnDayDemandToWorkbook(wb);			//写入按天需求数据
		writeOnWeekDemandToWorkbook(wb);		//写入按周需求数据
		writeOnMonthDemandToWorkbook(wb);		//写入按月需求数据		
		try {
			FileOutputStream fileOut=new FileOutputStream(filepath);
			wb.write(fileOut);
			fileOut.close();
			logger.info("成功生成需求报告并写入Excel文件["+filepath+"]");
			return true;
		} catch(Throwable ex) {
			logger.error("不能产生需求报告并写入Excel，出现错误：",ex);
			return false;
		}
	}
	
	/**
	 * 将按天需求写入Excel工作簿对象
	 * @param wb Excel工作簿对象
	 */
	private void writeOnDayDemandToWorkbook(Workbook wb) {
		if(wb==null) {
			logger.error("不能在工作簿中写入按天需求数据，工作簿对象为空。");
			return;
		}
		final int startrow=1;			//矩阵起始行
		final int startcol=2;			//矩阵起始咧
		final int colwidth=12;			//列宽度，12字符
		Sheet ondaysheet=wb.createSheet(SHEETNAME_DEM_ONDAY);								//创建按天sheet
		//开始配置单元格格式
		CellStyle weekdaystyle = wb.createCellStyle();		//一周七天单元格格式
		CellStyle datestyle = wb.createCellStyle();			//日期单元格格式
		CellStyle weekstyle = wb.createCellStyle();			//日期单元格格式
		CellStyle clientstyle = wb.createCellStyle();		//客户单元格格式
		CreationHelper createHelper = wb.getCreationHelper();
		weekdaystyle.setDataFormat(createHelper.createDataFormat().getFormat("aaaa"));		//配置一周七天单元格格式
		weekdaystyle.setAlignment(CellStyle.ALIGN_CENTER);
		weekdaystyle.setBorderTop(CellStyle.BORDER_THIN);
		weekdaystyle.setBorderBottom(CellStyle.BORDER_THIN);
		weekdaystyle.setBorderLeft(CellStyle.BORDER_THIN);
		weekdaystyle.setBorderRight(CellStyle.BORDER_THIN);
		datestyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd"));	//配置日期单元格格式
		datestyle.setAlignment(CellStyle.ALIGN_CENTER);
		datestyle.setBorderTop(CellStyle.BORDER_THIN);
		datestyle.setBorderBottom(CellStyle.BORDER_THIN);
		datestyle.setBorderLeft(CellStyle.BORDER_THIN);
		datestyle.setBorderRight(CellStyle.BORDER_THIN);
		weekstyle.setAlignment(CellStyle.ALIGN_CENTER);										//配置日期单元格格式
		weekstyle.setBorderLeft(CellStyle.BORDER_THIN);
		weekstyle.setBorderRight(CellStyle.BORDER_THIN);
		clientstyle.setAlignment(CellStyle.ALIGN_CENTER);									//配置客户单元格格式
		clientstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		clientstyle.setWrapText(true);		//允许换行
		//开始写入星期几和WK信息
		dematrix_onday.writeToExcelSheet(ondaysheet, new Location(startrow,startcol));		//写入天列表
		Row daterow=ondaysheet.getRow(startrow);			//获取日期列所在行
		Row weekdayrow=ondaysheet.createRow(startrow+1);	//创建一周7天列所在行
		Row weekrow=ondaysheet.createRow(startrow-1);		//创建周数列所在行
		Cell weekdaycell;		//weekday单元格
		Cell datecell;			//日期单元格
		Cell weekcell;			//周数单元格
		Calendar itcal=GeneralUtils.getValidCalendar();		//正在遍历的日期
		for(int counter=startcol+1;daterow.getCell(counter)!=null;counter++) {	//遍历日期行,写入星期几和周数据
			datecell=daterow.getCell(counter);
			itcal.setTime(new Date(datecell.getStringCellValue()));
			weekdaycell=weekdayrow.createCell(counter);
			weekdaycell.setCellValue(itcal);			//写入周中日期信息
			datecell.setCellValue(itcal);				//以日期格式重新写入表头信息
			weekdaycell.setCellStyle(weekdaystyle);		//写入周中日期格式
			datecell.setCellStyle(datestyle);			//写入日期格式
			if((counter-startcol)%7==0) {				//如果已经过了7天，则合并weekrow的7个单元格，并写入周数信息
				weekcell=weekrow.createCell(counter-6);			//创建单元格
				weekcell.setCellStyle(weekstyle);
				weekcell.setCellValue(itcal.get(Calendar.YEAR)+"年"+itcal.get(Calendar.WEEK_OF_YEAR)+"周");	//写入周数
				ondaysheet.addMergedRegion(new CellRangeAddress(weekrow.getRowNum(),weekrow.getRowNum(),counter-6,counter));	//合并单元格
			}
			ondaysheet.setColumnWidth(counter, colwidth*256);	//设置列宽度
		}
		//开始写入客户和型号信息
		String lastclient="NOTCLIENT";	//上一个客户
		String curclient;		//当前客户
		int clientbegin=-1;		//clientbegin起始行
		ModelContent temp;
		Cell clientcell;		//客户cell
		Cell infocell;			//项目cell
		Cell pncell;			//成品cell
		for(int counter=startrow+2;;counter++) {	//遍历pn列，填写
			if(ondaysheet.getRow(counter)==null) {	//如果是空行，则需要合并上一批单元格的客户单元格
				if(clientbegin!=-1)	{		//如果为-1，则说明是第一个客户,初始化clientbegin和lastclient
					clientcell=ondaysheet.getRow(clientbegin).createCell(startcol-2);	//创建客户Cell
					clientcell.setCellValue(lastclient);
					clientcell.setCellStyle(clientstyle);
					ondaysheet.addMergedRegion(new CellRangeAddress(clientbegin,counter-1,startcol-2,startcol-2));	//合并单元格
				}
				break;
			}
			pncell=ondaysheet.getRow(counter).getCell(startcol);
			if(pncell==null) break;
			temp=matmap_fin.get(pncell.getStringCellValue());				//获取pn的Model对象
			if(temp==null) {
				logger.error("不能在工作簿中写入按天需求数据，成品图中没有对应的成品号["+pncell.getStringCellValue()+"]");
				return;
			}
			curclient=temp.getClient()+"\n"+temp.getPrjcode();	//确认当前客户
			if(!curclient.equals(lastclient)) {					//如果当前客户不是以前客户，则说明新客户开始了，需要合并老客户单元格，并写入老客户信息
				if(clientbegin!=-1)	{		//如果为-1，则说明是第一个客户,初始化clientbegin和lastclient
					clientcell=ondaysheet.getRow(clientbegin).createCell(startcol-2);	//创建客户Cell
					clientcell.setCellValue(lastclient);
					clientcell.setCellStyle(clientstyle);
					ondaysheet.addMergedRegion(new CellRangeAddress(clientbegin,counter-1,startcol-2,startcol-2));	//合并单元格
				}
				clientbegin=counter;
				lastclient=curclient;
			}
			infocell=ondaysheet.getRow(counter).createCell(startcol-1);		//创建infocell
			infocell.setCellValue(temp.getInfo());
		}
		ondaysheet.setColumnWidth(startcol-2, 12*256);			//设置客户行宽度
		ondaysheet.setColumnWidth(startcol-1, 15*256);			//设置型号航宽度
		ondaysheet.createFreezePane(startcol+1, startrow+2);	//冻结窗格
		Row itrow;			//遍历行
		Cell itcell;		//遍历单元格
		double demqty;		//需求数量
		boolean hasdemand=false;	//是否有需求
		//没有需求记录的行自动隐藏
		for(int pncounter=startrow+2;;pncounter++) {			//外循环遍历行
			itrow=ondaysheet.getRow(pncounter);		//提取遍历行
			if(itrow==null) break;
			hasdemand=false;
			for(int colcounter=startcol+1;;colcounter++) {		//内循环遍历单元格
				itcell=itrow.getCell(colcounter);		//提取遍历单元格
				datecell=daterow.getCell(colcounter);	//提取日期单元格
				if(datecell==null) break;			//如果日期列为空，则跳出循环
				if(itcell==null) continue;			//如果单元格为空，继续遍历
				else {
					demqty=itcell.getNumericCellValue();	//获取需求值
					if(demqty==0) continue;			//如果需求为0，则继续
					else {
						hasdemand=true;				//如果不为0，说明有需求，则不能隐藏
						break;
					}
				}
			}
			if(!hasdemand)	//如果没有需求，则隐藏该列
				itrow.setZeroHeight(true);
		}
	}
	
	/**
	 * 将按周需求写入Excel工作簿对象
	 * @param wb Excel工作簿对象
	 */
	private void writeOnWeekDemandToWorkbook(Workbook wb) {
		if(wb==null) {
			logger.error("不能在工作簿中写入按周需求数据，工作簿对象为空。");
			return;
		}
		final int startrow=0;			//矩阵起始行
		final int startcol=2;			//矩阵起始咧
		final int colwidth=12;			//列宽度，12字符
		Sheet onweeksheet=wb.createSheet(SHEETNAME_DEM_ONWEEK);		//创建按周sheet
		//开始配置单元格格式
		CellStyle weekstyle = wb.createCellStyle();			//日期单元格格式
		CellStyle clientstyle = wb.createCellStyle();		//客户单元格格式
		weekstyle.setAlignment(CellStyle.ALIGN_CENTER);		//配置按周单元格格式
		weekstyle.setBorderLeft(CellStyle.BORDER_THIN);
		weekstyle.setBorderRight(CellStyle.BORDER_THIN);
		clientstyle.setAlignment(CellStyle.ALIGN_CENTER);	//配置客户单元格格式
		clientstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		clientstyle.setWrapText(true);		//允许换行
		//开始将周数信息写入格式
		dematrix_onweek.writeToExcelSheet(onweeksheet, new Location(startrow,startcol));	//写入周列表
		Row weekrow=onweeksheet.getRow(startrow);			//创建周数列所在行
		Cell weekcell;			//周数单元格
		for(int counter=startcol+1;weekrow.getCell(counter)!=null;counter++) {	//遍历周行,改变单元格格式
			weekcell=weekrow.getCell(counter);
			weekcell.setCellStyle(weekstyle);
			onweeksheet.setColumnWidth(counter, colwidth*256);	//设置列宽度
		}
		//开始写入客户和型号信息
		String lastclient="NOTCLIENT";	//上一个客户
		String curclient;		//当前客户
		int clientbegin=-1;		//clientbegin起始行
		ModelContent temp;
		Cell clientcell;		//客户cell
		Cell infocell;			//项目cell
		Cell pncell;			//成品cell
		for(int counter=startrow+1;;counter++) {	//遍历pn列，填写
			if(onweeksheet.getRow(counter)==null) {	//如果是空行，则需要合并上一批单元格的客户单元格
				if(clientbegin!=-1)	{				//如果为-1，则说明是第一个客户,初始化clientbegin和lastclient
					clientcell=onweeksheet.getRow(clientbegin).createCell(startcol-2);	//创建客户Cell
					clientcell.setCellValue(lastclient);
					clientcell.setCellStyle(clientstyle);
					onweeksheet.addMergedRegion(new CellRangeAddress(clientbegin,counter-1,startcol-2,startcol-2));	//合并单元格
				}
				break;
			}
			pncell=onweeksheet.getRow(counter).getCell(startcol);
			if(pncell==null) break;
			temp=matmap_fin.get(pncell.getStringCellValue());	//获取pn的Model对象
			if(temp==null) {
				logger.error("不能在工作簿中写入按天需求数据，成品图中没有对应的成品号["+pncell.getStringCellValue()+"]");
				return;
			}
			curclient=temp.getClient()+"\n"+temp.getPrjcode();	//确认当前客户
			if(!curclient.equals(lastclient)) {					//如果当前客户不是以前客户，则说明新客户开始了，需要合并老客户单元格，并写入老客户信息
				if(clientbegin!=-1)	{							//如果为-1，则说明是第一个客户,初始化clientbegin和lastclient
					clientcell=onweeksheet.getRow(clientbegin).createCell(startcol-2);	//创建客户Cell
					clientcell.setCellValue(lastclient);
					clientcell.setCellStyle(clientstyle);
					onweeksheet.addMergedRegion(new CellRangeAddress(clientbegin,counter-1,startcol-2,startcol-2));	//合并单元格
				}
				clientbegin=counter;
				lastclient=curclient;
			}
			infocell=onweeksheet.getRow(counter).createCell(startcol-1);		//创建infocell
			infocell.setCellValue(temp.getInfo());
		}
		onweeksheet.setColumnWidth(startcol-2, 12*256);			//设置客户行宽度
		onweeksheet.setColumnWidth(startcol-1, 15*256);			//设置型号航宽度
		onweeksheet.createFreezePane(startcol+1, startrow+1);	//冻结窗格
		Row itrow;			//遍历行
		Cell itcell;		//遍历单元格
		double demqty;		//需求数量
		boolean hasdemand=false;	//是否有需求
		//没有需求记录的行自动隐藏
		for(int pncounter=startrow+1;;pncounter++) {	//外循环遍历行
			itrow=onweeksheet.getRow(pncounter);		//提取遍历行
			if(itrow==null) break;
			hasdemand=false;
			for(int colcounter=startcol+1;;colcounter++) {		//内循环遍历单元格
				itcell=itrow.getCell(colcounter);		//提取遍历单元格
				weekcell=weekrow.getCell(colcounter);	//提取日期单元格
				if(weekcell==null) break;			//如果日期列为空，则跳出循环
				if(itcell==null) continue;			//如果单元格为空，继续遍历
				else {
					demqty=itcell.getNumericCellValue();	//获取需求值
					if(demqty==0) continue;			//如果需求为0，则继续
					else {
						hasdemand=true;				//如果不为0，说明有需求，则不能隐藏
						break;
					}
				}
			}
			if(!hasdemand)	//如果没有需求，则隐藏该列
				itrow.setZeroHeight(true);
		}
	}
	
	/**
	 * 将按月需求写入Excel工作簿对象
	 * @param wb Excel工作簿对象
	 */
	private void writeOnMonthDemandToWorkbook(Workbook wb) {
		if(wb==null) {
			logger.error("不能在工作簿中写入按周需求数据，工作簿对象为空。");
			return;
		}
		final int startrow=0;			//矩阵起始行
		final int startcol=2;			//矩阵起始咧
		final int colwidth=12;			//列宽度，12字符
		Sheet onmonthsheet=wb.createSheet(SHEETNAME_DEM_ONMONTH);	//创建按月sheet
		//开始配置单元格格式
		CellStyle monthstyle = wb.createCellStyle();		//按月单元格格式
		CellStyle clientstyle = wb.createCellStyle();		//客户单元格格式
		monthstyle.setAlignment(CellStyle.ALIGN_CENTER);	//配置按月单元格格式
		monthstyle.setBorderLeft(CellStyle.BORDER_THIN);
		monthstyle.setBorderRight(CellStyle.BORDER_THIN);
		clientstyle.setAlignment(CellStyle.ALIGN_CENTER);	//配置客户单元格格式
		clientstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		clientstyle.setWrapText(true);		//允许换行
		//开始将周数信息写入格式
		dematrix_onmonth.writeToExcelSheet(onmonthsheet, new Location(startrow,startcol));	//写入月列表
		Row monthrow=onmonthsheet.getRow(startrow);			//创建月数列所在行
		Cell monthcell;			//月数单元格
		for(int counter=startcol+1;monthrow.getCell(counter)!=null;counter++) {	//遍历月行,改变单元格格式
			monthcell=monthrow.getCell(counter);
			monthcell.setCellStyle(monthstyle);
			onmonthsheet.setColumnWidth(counter, colwidth*256);	//设置列宽度
		}
		//开始写入客户和型号信息
		String lastclient="NOTCLIENT";	//上一个客户
		String curclient;		//当前客户
		int clientbegin=-1;		//clientbegin起始行
		ModelContent temp;
		Cell clientcell;		//客户cell
		Cell infocell;			//项目cell
		Cell pncell;			//成品cell
		for(int counter=startrow+1;;counter++) {		//遍历pn列，填写
			if(onmonthsheet.getRow(counter)==null) {	//如果是空行，则需要合并上一批单元格的客户单元格
				if(clientbegin!=-1)	{					//如果为-1，则说明是第一个客户,初始化clientbegin和lastclient
					clientcell=onmonthsheet.getRow(clientbegin).createCell(startcol-2);	//创建客户Cell
					clientcell.setCellValue(lastclient);
					clientcell.setCellStyle(clientstyle);
					onmonthsheet.addMergedRegion(new CellRangeAddress(clientbegin,counter-1,startcol-2,startcol-2));	//合并单元格
				}
				break;
			}
			pncell=onmonthsheet.getRow(counter).getCell(startcol);
			if(pncell==null) break;
			temp=matmap_fin.get(pncell.getStringCellValue());	//获取pn的Model对象
			if(temp==null) {
				logger.error("不能在工作簿中写入按天需求数据，成品图中没有对应的成品号["+pncell.getStringCellValue()+"]");
				return;
			}
			curclient=temp.getClient()+"\n"+temp.getPrjcode();	//确认当前客户
			if(!curclient.equals(lastclient)) {					//如果当前客户不是以前客户，则说明新客户开始了，需要合并老客户单元格，并写入老客户信息
				if(clientbegin!=-1)	{							//如果为-1，则说明是第一个客户,初始化clientbegin和lastclient
					clientcell=onmonthsheet.getRow(clientbegin).createCell(startcol-2);	//创建客户Cell
					clientcell.setCellValue(lastclient);
					clientcell.setCellStyle(clientstyle);
					onmonthsheet.addMergedRegion(new CellRangeAddress(clientbegin,counter-1,startcol-2,startcol-2));	//合并单元格
				}
				clientbegin=counter;
				lastclient=curclient;
			}
			infocell=onmonthsheet.getRow(counter).createCell(startcol-1);		//创建infocell
			infocell.setCellValue(temp.getInfo());
		}
		onmonthsheet.setColumnWidth(startcol-2, 12*256);			//设置客户行宽度
		onmonthsheet.setColumnWidth(startcol-1, 15*256);			//设置型号航宽度
		onmonthsheet.createFreezePane(startcol+1, startrow+1);		//冻结窗格
		Row itrow;			//遍历行
		Cell itcell;		//遍历单元格
		double demqty;		//需求数量
		boolean hasdemand=false;	//是否有需求
		//没有需求记录的行自动隐藏
		for(int pncounter=startrow+1;;pncounter++) {			//外循环遍历行
			itrow=onmonthsheet.getRow(pncounter);		//提取遍历行
			if(itrow==null) break;
			hasdemand=false;
			for(int colcounter=startcol+1;;colcounter++) {		//内循环遍历单元格
				itcell=itrow.getCell(colcounter);		//提取遍历单元格
				monthcell=monthrow.getCell(colcounter);	//提取日期单元格
				if(monthcell==null) break;			//如果日期列为空，则跳出循环
				if(itcell==null) continue;			//如果单元格为空，继续遍历
				else {
					demqty=itcell.getNumericCellValue();	//获取需求值
					if(demqty==0) continue;			//如果需求为0，则继续
					else {
						hasdemand=true;				//如果不为0，说明有需求，则不能隐藏
						break;
					}
				}
			}
			if(!hasdemand)	//如果没有需求，则隐藏该列
				itrow.setZeroHeight(true);
		}
	}
	
}