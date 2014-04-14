package com.logsys.production.bwi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.production.ProductionContent;
import com.logsys.setting.Settings;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.util.Location;

/**
 * 生产数据提取器，从Excel提取
 * @author ShaonanLi
 */
public class ProductionDataReaderExcel_BWI {

	private static final Logger logger=Logger.getLogger(ProductionDataReaderExcel_BWI.class);
	
	private static BWIPLInfo plinfo=Settings.BWISettings.plinfo;
	
	/**配置Sheet名*/
	public static final String CONFIGURE_SHEETNAME="Configuration";
	
	/**配置Sheet中，生产线名称所在列*/
	public static final Location PLLOC=new Location(1,0);
	
	/**
	 * 从文件filepath中读取数据
	 * @param filepath 目标Excel的文件路径
	 * @return 提取的生产数据
	 */
	public static List<ProductionContent> readDataFromFile(String filepath) {
		File file=new File(filepath);			//由文件路径创建新文件
		Workbook wb;
		InputStream readstream;
		try {
			readstream=new FileInputStream(filepath);
			wb=WorkbookFactory.create(readstream);
		} catch(Throwable ex) {
			logger.error("不能从文件路径读取Excel文件。",ex);
			return null;
		}
		Sheet sheet;
		List<ProductionContent> pcontlist;
		List<ProductionContent> temp;
		int daycounter;				//天数计数器
		String plname;				//生产线名称
		String stdplname;			//标准生产线名称
		BWIPdExcelDataExtractor extractor;	//数据提取器的版本
		try {
			//获取生产线名称
			plname=wb.getSheet(CONFIGURE_SHEETNAME).getRow(PLLOC.row).getCell(PLLOC.column).getStringCellValue();
			stdplname=plinfo.getStdProdlineNameByAlias(plname);
			if(stdplname==null) {
				logger.error("错误！不能正确获取标准生产线名称,可能是名称错误，或者是对应图中尚没有创建此名称的对应条目。别名名称为："+plname);
				return null;
			}
			extractor=getExtractorByStdPrdLine(stdplname);		//获取提取器
			if(extractor==null) {
				logger.error("错误！没有合适当前生产线的提取器："+stdplname);
				return null;
			}
			pcontlist=new ArrayList<ProductionContent>();
			for(daycounter=1;;daycounter++) {					//从当月第一天开始遍历
				sheet=wb.getSheet(String.valueOf(daycounter));	//按照日期便利天数的Sheet
				if(sheet==null) break;							//当便利到没有Sheet时，说明本月已经遍历完成
				
			}
		} catch(Throwable ex) {
			logger.error("从Excel表格中读取生产数据出现错误。",ex);
			return null;
		}
		return null;
	}
	
	/**
	 * 通过标准生产线名称获取Excel数据提取器
	 * @param stdplname 标准生产线名称
	 * @return Excel数据提取器
	 */
	private static BWIPdExcelDataExtractor getExtractorByStdPrdLine(String stdplname) {
		if(stdplname==null) return null;
		if(plinfo.getProdzoneByStdProdlineName(stdplname).equals(plinfo.STDNAME_DAMPER_RTA))	//如果是RTA的表格
			if(stdplname.equals(plinfo.STDNAME_DAMPER_RTA_LEAKTESTWASHING))		//侧漏清洗生产线
				return new BWIPdExcelDataExtractor_DamperLeakTestWashing();
			else if(stdplname.equals(plinfo.STDNAME_DAMPER_RTA_HBF0031)||stdplname.equals(plinfo.STDNAME_DAMPER_RTA_HBF0835))		//底部热成型0031生产线
				return new BWIPdExcelDataExtractor_DamperHBF();
		return null;
	}

}
