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
 * BWI生产计划数据读取类, Excel数据读取类
 * @author lx8sn6
 */
public class BWIProdplanDataReaderExcel {
	
	private static final Logger logger=Logger.getLogger(BWIProdplanDataReaderExcel.class);
	
	private static BWIPPExcelInfo ppExcelInfo=Settings.BWISettings.ppExcelInfo;		//BWI的ExcelPP配置类
	
	/**
	 * 从Excel文件中获取总装计划，计划Sheet名称必须为UploadPlan，用于BWI的FA计划文件。
	 * @param filepath 文件路径，计划Sheet名称必须为UploadPlan
	 * @param startdate 读取计划的起始日期，如果startdate为null,则自动设置为下一周的周一
	 * @param enddate 读取计划的结束日期，如果enddate为null,则不限制下限
	 * @return 读取的计划表
	 */
	public static List<ProdplanContent> getFAPlanFromFileBWI(String filepath, Date startdate, Date enddate) {
		Workbook wb;
		InputStream readstream;
		try {
			readstream=new FileInputStream(filepath);
			wb=WorkbookFactory.create(readstream);
		} catch(Throwable ex) {
			logger.error("不能从文件路径读取Excel文件。",ex);
			return null;
		}
		Sheet plansheet=null;
		try {
			plansheet=wb.getSheet("UploadPlan");
			if(plansheet==null) {
				logger.error("不能读取计划，没有名为UploadPlan的Sheet.");
				return null;
			}
			Map<Date, Integer> datecolmap=new HashMap<Date, Integer>();	//日期<->列数的Map关系
			Date maxdate=null;											//计最大日期
			for(Cell datecell:plansheet.getRow(ppExcelInfo.getFA1DateRow())) //初始化列数<->日期的Map
				try {
					if(datecell.getDateCellValue()==null) continue;
					datecolmap.put(datecell.getDateCellValue(),datecell.getColumnIndex());
					maxdate=datecell.getDateCellValue();
				} catch(Throwable ex) {				//如果不能转换，跳过这个单元格
					continue;
				}
			if(startdate==null) {					//如果没有指定开始日期，则默认为下周的周一
				Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
				int week=cal.get(Calendar.WEEK_OF_YEAR)+1;
				int year=cal.get(Calendar.YEAR);
				cal.clear();
				cal.setWeekDate(year, week, Calendar.MONDAY);
				startdate=cal.getTime();
			}
			if(enddate==null) {						//如果没有指定结束日期，则默认为最大日期
				enddate=maxdate;
			}
			String modelpn;							//型号
			int begincol;							//起始行
			int endcol;								//结束行
			if(datecolmap.containsKey(startdate)) 
				begincol=datecolmap.get(startdate);	//获取起始日期列
			else {
				logger.error("没有对应起始日期："+startdate+" 的对应计划列。请核查计划表。");
				return null;
			}
			if(datecolmap.containsKey(enddate))	
				endcol=datecolmap.get(enddate);		//获取结束日期
			else {
				logger.error("没有对应结束日期："+enddate+" 的对应计划列。请核查计划表。");
				return null;
			}
			List<ProdplanContent> pplist=new ArrayList<ProdplanContent>();
			ProdplanContent temp;
			Date plandate;
			for(Row row:plansheet) {					//遍历行，读取FA1的计划
				if(row.getRowNum()>=ppExcelInfo.getFA1PPBeginRow() && row.getRowNum()<=ppExcelInfo.getFA1PPEndRow()) {	//读取FA1的计划
					row.getCell(ppExcelInfo.getPnCol()).setCellType(Cell.CELL_TYPE_STRING);	//设置为String类型
					modelpn=row.getCell(ppExcelInfo.getPnCol()).getStringCellValue();		//获取型号
					for(Cell cell:row) {				//遍历单元
						if(cell.getColumnIndex()<begincol||cell.getColumnIndex()>endcol) continue;	//在起始日期列之前的全部跳过
						try {
							if(cell.getNumericCellValue()==0) continue;		//如果没有计划，则跳过
						} catch (Throwable ex) {
							logger.error("单元格中不是数字，无法提取计划:位置["+cell.getRowIndex()+"]行["+cell.getColumnIndex()+"]列");
							throw ex;
						}
						plandate=plansheet.getRow(ppExcelInfo.getFA1DateRow()).getCell(cell.getColumnIndex()).getDateCellValue();	//获取当前单元格计划日期
						if(plandate==null) continue;					//如果没有相对应的日期，则跳过
						temp=new ProdplanContent();
						temp.setDate(plandate);			//获取Date
						temp.setPn(modelpn);
						temp.setPrdline(BWIPLInfo.getStdNameByLineEnum(ProdLine.DAMPER_FA_FA1));
						temp.setQty(cell.getNumericCellValue());
						pplist.add(temp);
					}
				} else if(row.getRowNum()>=ppExcelInfo.getFA2PPBeginRow() && row.getRowNum()<=ppExcelInfo.getFA2PPEndRow()) {	//读取FA2的计划
					row.getCell(ppExcelInfo.getPnCol()).setCellType(Cell.CELL_TYPE_STRING);	//设置为String类型
					modelpn=row.getCell(ppExcelInfo.getPnCol()).getStringCellValue();		//获取型号
					for(Cell cell:row) {				//遍历单元
						if(cell.getColumnIndex()<begincol||cell.getColumnIndex()>endcol) continue;	//在起始日期列之前的全部跳过
						if(cell.getNumericCellValue()==0) continue;		//如果没有计划，则跳过
						plandate=plansheet.getRow(ppExcelInfo.getFA1DateRow()).getCell(cell.getColumnIndex()).getDateCellValue();	//获取当前计划日期，FA1与FA2计划日期目前一致
						if(plandate==null) continue;					//如果没有相对应的日期，则跳过
						temp=new ProdplanContent();
						//FA1和FA2日期轴相同
						temp.setDate(plandate);	//获取Date
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
			logger.error("不能读取Excel中的数据",ex);
			return null;
		}
	}
	
}
