package com.logsys.production.bwi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.production.ProductionContent;
import com.logsys.production.ProductionInterval;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.setting.pd.bwi.BWIPLInfo.ProdLine;
import com.logsys.setting.pd.bwi.BWIPdExcelInfo;
import com.logsys.setting.pd.bwi.BWIPdExcelInfoRTA;
import com.logsys.setting.pd.bwi.BWIPdExcelInfoRTA_KTL;
import com.logsys.setting.pd.bwi.BWIPdExcelInfoRTA_NeckDown;
import com.logsys.util.Location;

/**
 * 生产数据提取器，从Excel提取
 * @author ShaonanLi
 */
public class ProductionDataReaderExcel_BWI {

	private static final Logger logger=Logger.getLogger(ProductionDataReaderExcel_BWI.class);
	
	/**
	 * 从文件filepath中读取生产数据
	 * @param filepath 目标Excel的文件路径
	 * @param dayofmonth 指定某一天的数据进行读取,如果<=0,则说明不限制日期全部读取
	 * @return 提取的生产数据
	 */
	public static List<ProductionContent> readDataFromFile(String filepath,int dayofmonth) {
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
		String plname;				//生产线名称
		ProdLine pline;				//标准生产线名称
		BWIPdExcelInfo excelinfo;	//Excel信息类
		try {
			//获取生产线名称
			plname=wb.getSheet(BWIPdExcelInfo.getConfigSheetName()).getRow(BWIPdExcelInfo.PLLOC.row).getCell(BWIPdExcelInfo.PLLOC.column).getStringCellValue();
			pline=BWIPLInfo.getStdLineByAlias(plname);
			if(pline==ProdLine.INVALID) {
				logger.error("从Excel读取生产信息错误！不能正确获取标准生产线枚举,可能是名称错误，或者是Excel信息中的别名映射尚没有创建对应的标准名。别名名称为：["+plname+"].");
				return null;
			}
			//根据生产线获取Excel信息类
			excelinfo=getExcelInfoByPrdLine(pline);
			if(excelinfo==null) {
				logger.error("从Excel读取生产信息错误！没有合适当前生产线：["+plname+"]的信息类。");
				return null;
			}
			int daycounter;										//天数计数器
			int maxday;											//遍历的最大天数
			if(dayofmonth<=0) {									//如果指定的日期<=0，则从第一天开始遍历，将最大天数设置为某个月的100天
				daycounter=1;
				maxday=100;
			} else {											//否则将开始天数设置为dayofmonth,最大天数也为dayofmonth
				daycounter=dayofmonth;
				maxday=dayofmonth;
			}
			List<ProductionContent> pcontlist=new ArrayList<ProductionContent>();
			List<ProductionContent> temp;						//临时变量
			for(daycounter=1;daycounter<=maxday;daycounter++) {	//开始遍历
				sheet=wb.getSheet(String.valueOf(daycounter));	//按照日期便利天数的Sheet
				if(sheet==null) break;							//当便利到没有Sheet时，说明本月已经遍历完成
				if(!verifySheet(sheet,excelinfo)) {				//验证Sheet
					logger.error("从Excel读取生产信息错误！Sheet["+sheet.getSheetName()+"]未通过验证。");
					return null;
				}
				temp=getOutputDataFromSheet(sheet,excelinfo,pline);	//读取sheet中的产出数据
				if(temp==null) {
					logger.error("Sheet["+sheet.getSheetName()+"]提取数据出现错误，提取过程终止.");
					return null;
				}
				pcontlist.addAll(temp);							//将所有临时列表加入主列表 
			}
			return pcontlist;
		} catch(Throwable ex) {
			logger.error("从Excel表格中读取生产数据出现错误。",ex);
			return null;
		}
	}
	
	/**
	 * 通过生产线枚举获取Excel信息类
	 * @param ProdLine 生产线枚举
	 * @return Excel数据提取器
	 */
	private static BWIPdExcelInfo getExcelInfoByPrdLine(ProdLine pline) {
		if(pline==ProdLine.INVALID) return null;
		if(BWIPLInfo.getProdZoneByProdLine(pline).equals(ProdLine.DAMPER_ZONE_RTA))	{ //RTA区域
			if(pline.equals(ProdLine.DAMPER_RTA_NECKDOWN))		//缩口单独配置
				return new BWIPdExcelInfoRTA_NeckDown();
			else if(pline.equals(ProdLine.DAMPER_RTA_KTL)||pline.equals(ProdLine.DAMPER_RTA_CHAMFER_WASH)||pline.equals(ProdLine.DAMPER_RTA_WASH_POSTNK))		//[电泳线]/[切割倒角]/[缩口前清洗]采用KTL的单独配置
				return new BWIPdExcelInfoRTA_KTL();
			else
				return new BWIPdExcelInfoRTA();
		}
		return null;
	}
	
	/**
	 * 验证Sheet中的内容是否符合excelinfo中的验证字符
	 * @param sheet 需要验证的Sheet对象
	 * @param excelinfo 用于验证的Excel信息对象
	 * @return 通过验证True/未通过验证false
	 */
	private static boolean verifySheet(Sheet sheet, BWIPdExcelInfo excelinfo) {
		if(sheet==null||excelinfo==null) {
			logger.error("不能验证Sheet正确性，参数为空。");
			return false;
		}
		Map<Location,String> verifyMap=excelinfo.getLocVerifyStrMap();
		Cell cell;			//单元格
		String content;		//单元格内容
		String verifystr;	//验证字符串
		for(Location loc:verifyMap.keySet()) {						//遍历所有Location中的内容已确认表格完整性
			cell=sheet.getRow(loc.row).getCell(loc.column);
			if(cell==null) {
				logger.error("Sheet验证失败：Sheet["+sheet.getSheetName()+"]Row["+loc.row+"]Column["+loc.column+"]所代表的的单元格为空。");
				return false;
			}
			cell.setCellType(Cell.CELL_TYPE_STRING);				//提取Cell中内容
			content=cell.getStringCellValue();
			content=content.replace('：', ':');						//将所有中文：变为英文:
			verifystr=verifyMap.get(loc);
			if(!content.equals(verifystr)) 							//如果字符串不匹配
				if(!(content.equals("")&&verifystr.equals(BWIPdExcelInfo.EMPTY_STR))) {	//如果单元格为空，切验证字符标明其为空，才能合格
					logger.error("Sheet验证失败：Sheet["+sheet.getSheetName()+"]Row["+loc.row+"]Column["+loc.column+"]中的内容["+content+"]与验证字符串["+verifystr+"]不符.");
					return false;
				}
		}
		return true;
	}

	/**
	 * 从指定Sheet中提取产出数据信息
	 * @param sheet 包含数据的Sheet对象
	 * @param excelinfo 匹配Sheet的Excel信息对象
	 * @param pline 生产线枚举
	 * @return 提取的列表/null
	 */
	private static List<ProductionContent> getOutputDataFromSheet(Sheet sheet,BWIPdExcelInfo excelinfo,ProdLine pline) {
		if(sheet==null||excelinfo==null) {
			logger.error("不能提取生产数据，参数为空。");
			return null;
		}
		Date date;												//提取生产日期
		Location dateloc=excelinfo.getDateLocation();
		try {
			date=sheet.getRow(dateloc.row).getCell(dateloc.column).getDateCellValue();
			if(date==null)
				logger.error("不能提取生产数据：代表生产日期的Sheet["+sheet.getSheetName()+"]Row["+dateloc.row+"]Column["+dateloc.column+"]单元格提取的数据为空.");
		} catch(Throwable ex) {
			logger.error("不能提取生产数据：代表生产日期的Sheet["+sheet.getSheetName()+"]Row["+dateloc.row+"]Column["+dateloc.column+"]单元格不能提取日期信息。",ex);
			return null;
		}
		Map<Location,ProductionInterval> outputmap=excelinfo.getLocIntervalMap();
		List<ProductionContent> pcontlist=new ArrayList<ProductionContent>();
		ProductionContent tempcont;
		ProductionInterval tempitv;
		Cell cell;
		String oripn;											//Excel中的产出物料名
		String stdpn;											//标准产出料名
		Calendar cal=Calendar.getInstance();
		for(Location loc:outputmap.keySet()) {					//开始遍历产出位置
			cell=sheet.getRow(loc.row).getCell(loc.column);
			if(cell==null) {
				logger.error("不能提取生产数据：Sheet["+sheet.getSheetName()+"]Row["+loc.row+"]Column["+loc.column+"]单元格为空。");
				return null;
			}
			if(cell.getNumericCellValue()==0) continue;			//如果产出为0，则跳过这个单元格
			tempcont=new ProductionContent();
			try {
				oripn=sheet.getRow(loc.row).getCell(loc.column+BWIPdExcelInfo.RELCOL_PARTNUM_OUTPUTQTY).getStringCellValue();	//提取产出产品
				stdpn=excelinfo.getProdAliasStdPnMap().get(oripn);	//获得产出产品标准名
				if(stdpn==null) {
					logger.error("不能提取生产数据：Sheet["+sheet.getSheetName()+"]Row["+loc.row+"]Column["+(loc.column+BWIPdExcelInfo.RELCOL_PARTNUM_OUTPUTQTY)+"]单元格提取的别名["+oripn+"]没有标准名。");
					return null;
				}
				tempitv=outputmap.get(loc);						//提取产出区间信息
				tempcont.setWorkcenter(BWIPLInfo.getStdNameByLineEnum(pline));	//设置工作重心
				tempcont.setOutput(stdpn);						//设置产出
				tempcont.setQty(cell.getNumericCellValue());	//提取生产数量
				tempcont.setDate(date);							//设置日期
				tempcont.setOperqty((int)Math.floor(sheet.getRow(loc.row).getCell(loc.column+BWIPdExcelInfo.RELCOL_OPERQTY_OUTPUTQTY).getNumericCellValue()));	//设置操作工数量
				tempcont.setEffmin(tempitv.effmin);				//设置有效工作时间
				cal.setTime(date);
				cal.set(Calendar.HOUR, tempitv.beginhour);
				cal.set(Calendar.MINUTE, tempitv.beginmin);
				tempcont.setTfbegin(cal.getTime());				//设置起始区间
				cal.setTime(date);
				cal.set(Calendar.HOUR, tempitv.endhour);
				cal.set(Calendar.MINUTE, tempitv.endmin);
				tempcont.setTfend(cal.getTime());				//设置结束区间
				pcontlist.add(tempcont);						//加入列表
			} catch(Throwable ex) {
				logger.error("不能提取生产数据：处理Sheet["+sheet.getSheetName()+"]Row["+loc.row+"]Column["+loc.column+"]所代表的的单元格时出现错误。",ex);
				return null;
			}
		}
		return pcontlist;
	}
	
	/**
	 * 调试用信息接口，显示提取后数据的统计信息
	 * @param prodlist 提取的信息列表
	 */
	public static void debugInfoStastistics(List<ProductionContent> prodlist) {
		if(prodlist==null) {
			logger.error("错误！参数为空。");
			return;
		}
		try {
			Map<Date,Double> count=new HashMap<Date,Double>();
			Double temp;
			for(ProductionContent pc:prodlist) {
				temp=pc.getQty();
				if(count.containsKey(pc.getDate()))
					temp+=count.get(pc.getDate());
				count.put(pc.getDate(), temp);
				System.out.println(pc);
			}
			Double sum=0.0;
			for(Date date:count.keySet()) {
				System.out.println("Date["+date+"]"+"Qty["+count.get(date)+"]");
				sum+=count.get(date);
			}
			System.out.println("Total:"+sum);
			//System.out.println(ProductionDataWriterDB.writeDataToDB(prodlist));
		} catch (Throwable e) {
			logger.error("错误：",e);
		}
	}
	
}
