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
 * 需求数据写入器:Excel
 * @author lx8sn6
 */
public class DemandDataWriterExcel {

	private static final Logger logger=Logger.getLogger(DemandDataWriterExcel.class);
	
	/**型号表头行*/
	private static final int ROW_HEADER=0;
	
	/**需求起始行*/
	private static final int ROW_BEGIN=1;
	
	/**日期列*/
	private static final int COL_DATE=0;
	
	
	/**
	 * 将由DemandUtil.demListToMapByPn()生成的数据写入Excel表格。注意！如果demmap中包含Model表中没有的型号，则会将这些型号的需求在demmap中自动删除
	 * @param filepath 文件路径
	 * @param demmap 经过DemandUtil.demListToMapByPn()处理，包含Demand的数据表格
	 * @return 成功true/失败false
	 */
	public static boolean writeToExcel(String filepath,Map<String,Map<Date,DemandContent>> demmap) {
		if(filepath==null) {
			logger.error("文件路径参数为空.");
			return false;
		}
		File file=new File(filepath);
		if(file.exists()) {
			logger.error("文件已存在.");
			return false;
		}
		try {
			Workbook wb=new XSSFWorkbook();				//创建工作簿
			Sheet demsheet=wb.createSheet("Demand");	//创建sheet
			Set<String> modelset=demmap.keySet();		//获取型号集
			Map<String,Integer> modelorder=ModelDataReaderDB.sortModels(modelset);	//对于型号集进行排序
			Row row=demsheet.createRow(ROW_HEADER);		//创建表头行
			Set<String> illegalmodel=new HashSet<String>();		//非法型号集
			for(String model:modelset)	{				//写入表头
				if(modelorder.get(model)==null) {		//如果没有这个型号，则跳过此型号,加入非法型号集
					logger.warn("型号表model中没有需求列表中出现的型号:"+model+",忽略这个型号.");
					illegalmodel.add(model);			//加入非法型号集
					continue;
				}
				row.createCell(modelorder.get(model)+1).setCellValue(model);
			}
			for(String model:illegalmodel) {			//删除非法型号
				demmap.remove(model);
			}
			Date mindate=DemandUtil.getMinDateInMultiModel(demmap);			//获取时间下限
			Date maxdate=DemandUtil.getMaxDateInMultiModel(demmap);			//获取时间上限
			int rowcounter=ROW_BEGIN;					//起始行
			CellStyle datestyle=wb.createCellStyle();	//日期格式
			datestyle.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("yyyy/MM/dd"));
			Calendar dateindex=Calendar.getInstance();	//获取时间实例作为索引
			dateindex.setTime(mindate);					//索引起始为时间下限
			Map<Date,DemandContent> modeldem;
			DemandContent tempDemCon;
			while(true) {								//写入时间和需求数量
				row=demsheet.createRow(rowcounter);		//创建新行
				row.createCell(COL_DATE).setCellValue(dateindex);	//写入日期
				row.getCell(COL_DATE).setCellStyle(datestyle);		//设置日期风格
				for(String model:demmap.keySet()) {		//模型遍历，找到当日是否有需求
					modeldem=demmap.get(model);
					tempDemCon=modeldem.get(dateindex.getTime());
					if(tempDemCon==null) continue;
					row.createCell(modelorder.get(model)+1).setCellValue(tempDemCon.getQty());
				}
				dateindex.add(Calendar.DAY_OF_MONTH, 1);//新加一天
				if(dateindex.getTime().after(maxdate))	//如果日期索引到最大日期之后，则退出循环
					break;
				rowcounter++;
			}
			FileOutputStream fileOut=new FileOutputStream(filepath);
			wb.write(fileOut);
			fileOut.close();
			return true;
		} catch(Throwable ex) {
			logger.error("需求写入出现错误:"+ex);
			ex.printStackTrace();
			return false;
		}
	}

}
