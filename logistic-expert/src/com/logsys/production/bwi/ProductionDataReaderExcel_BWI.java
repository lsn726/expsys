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
 * ����������ȡ������Excel��ȡ
 * @author ShaonanLi
 */
public class ProductionDataReaderExcel_BWI {

	private static final Logger logger=Logger.getLogger(ProductionDataReaderExcel_BWI.class);
	
	/**
	 * ���ļ�filepath�ж�ȡ��������
	 * @param filepath Ŀ��Excel���ļ�·��
	 * @param dayofmonth ָ��ĳһ������ݽ��ж�ȡ,���<=0,��˵������������ȫ����ȡ
	 * @return ��ȡ����������
	 */
	public static List<ProductionContent> readDataFromFile(String filepath,int dayofmonth) {
		Workbook wb;
		InputStream readstream;
		try {
			readstream=new FileInputStream(filepath);
			wb=WorkbookFactory.create(readstream);
		} catch(Throwable ex) {
			logger.error("���ܴ��ļ�·����ȡExcel�ļ���",ex);
			return null;
		}
		Sheet sheet;
		String plname;				//����������
		ProdLine pline;				//��׼����������
		BWIPdExcelInfo excelinfo;	//Excel��Ϣ��
		try {
			//��ȡ����������
			plname=wb.getSheet(BWIPdExcelInfo.getConfigSheetName()).getRow(BWIPdExcelInfo.PLLOC.row).getCell(BWIPdExcelInfo.PLLOC.column).getStringCellValue();
			pline=BWIPLInfo.getStdLineByAlias(plname);
			if(pline==ProdLine.INVALID) {
				logger.error("��Excel��ȡ������Ϣ���󣡲�����ȷ��ȡ��׼������ö��,���������ƴ��󣬻�����Excel��Ϣ�еı���ӳ����û�д�����Ӧ�ı�׼������������Ϊ��["+plname+"].");
				return null;
			}
			//���������߻�ȡExcel��Ϣ��
			excelinfo=getExcelInfoByPrdLine(pline);
			if(excelinfo==null) {
				logger.error("��Excel��ȡ������Ϣ����û�к��ʵ�ǰ�����ߣ�["+plname+"]����Ϣ�ࡣ");
				return null;
			}
			int daycounter;										//����������
			int maxday;											//�������������
			if(dayofmonth<=0) {									//���ָ��������<=0����ӵ�һ�쿪ʼ�������������������Ϊĳ���µ�100��
				daycounter=1;
				maxday=100;
			} else {											//���򽫿�ʼ��������Ϊdayofmonth,�������ҲΪdayofmonth
				daycounter=dayofmonth;
				maxday=dayofmonth;
			}
			List<ProductionContent> pcontlist=new ArrayList<ProductionContent>();
			List<ProductionContent> temp;						//��ʱ����
			for(daycounter=1;daycounter<=maxday;daycounter++) {	//��ʼ����
				sheet=wb.getSheet(String.valueOf(daycounter));	//�������ڱ���������Sheet
				if(sheet==null) break;							//��������û��Sheetʱ��˵�������Ѿ��������
				if(!verifySheet(sheet,excelinfo)) {				//��֤Sheet
					logger.error("��Excel��ȡ������Ϣ����Sheet["+sheet.getSheetName()+"]δͨ����֤��");
					return null;
				}
				temp=getOutputDataFromSheet(sheet,excelinfo,pline);	//��ȡsheet�еĲ�������
				if(temp==null) {
					logger.error("Sheet["+sheet.getSheetName()+"]��ȡ���ݳ��ִ�����ȡ������ֹ.");
					return null;
				}
				pcontlist.addAll(temp);							//��������ʱ�б�������б� 
			}
			return pcontlist;
		} catch(Throwable ex) {
			logger.error("��Excel����ж�ȡ�������ݳ��ִ���",ex);
			return null;
		}
	}
	
	/**
	 * ͨ��������ö�ٻ�ȡExcel��Ϣ��
	 * @param ProdLine ������ö��
	 * @return Excel������ȡ��
	 */
	private static BWIPdExcelInfo getExcelInfoByPrdLine(ProdLine pline) {
		if(pline==ProdLine.INVALID) return null;
		if(BWIPLInfo.getProdZoneByProdLine(pline).equals(ProdLine.DAMPER_ZONE_RTA))	{ //RTA����
			if(pline.equals(ProdLine.DAMPER_RTA_NECKDOWN))		//���ڵ�������
				return new BWIPdExcelInfoRTA_NeckDown();
			else if(pline.equals(ProdLine.DAMPER_RTA_KTL)||pline.equals(ProdLine.DAMPER_RTA_CHAMFER_WASH)||pline.equals(ProdLine.DAMPER_RTA_WASH_POSTNK))		//[��Ӿ��]/[�и��]/[����ǰ��ϴ]����KTL�ĵ�������
				return new BWIPdExcelInfoRTA_KTL();
			else
				return new BWIPdExcelInfoRTA();
		}
		return null;
	}
	
	/**
	 * ��֤Sheet�е������Ƿ����excelinfo�е���֤�ַ�
	 * @param sheet ��Ҫ��֤��Sheet����
	 * @param excelinfo ������֤��Excel��Ϣ����
	 * @return ͨ����֤True/δͨ����֤false
	 */
	private static boolean verifySheet(Sheet sheet, BWIPdExcelInfo excelinfo) {
		if(sheet==null||excelinfo==null) {
			logger.error("������֤Sheet��ȷ�ԣ�����Ϊ�ա�");
			return false;
		}
		Map<Location,String> verifyMap=excelinfo.getLocVerifyStrMap();
		Cell cell;			//��Ԫ��
		String content;		//��Ԫ������
		String verifystr;	//��֤�ַ���
		for(Location loc:verifyMap.keySet()) {						//��������Location�е�������ȷ�ϱ��������
			cell=sheet.getRow(loc.row).getCell(loc.column);
			if(cell==null) {
				logger.error("Sheet��֤ʧ�ܣ�Sheet["+sheet.getSheetName()+"]Row["+loc.row+"]Column["+loc.column+"]������ĵĵ�Ԫ��Ϊ�ա�");
				return false;
			}
			cell.setCellType(Cell.CELL_TYPE_STRING);				//��ȡCell������
			content=cell.getStringCellValue();
			content=content.replace('��', ':');						//���������ģ���ΪӢ��:
			verifystr=verifyMap.get(loc);
			if(!content.equals(verifystr)) 							//����ַ�����ƥ��
				if(!(content.equals("")&&verifystr.equals(BWIPdExcelInfo.EMPTY_STR))) {	//�����Ԫ��Ϊ�գ�����֤�ַ�������Ϊ�գ����ܺϸ�
					logger.error("Sheet��֤ʧ�ܣ�Sheet["+sheet.getSheetName()+"]Row["+loc.row+"]Column["+loc.column+"]�е�����["+content+"]����֤�ַ���["+verifystr+"]����.");
					return false;
				}
		}
		return true;
	}

	/**
	 * ��ָ��Sheet����ȡ����������Ϣ
	 * @param sheet �������ݵ�Sheet����
	 * @param excelinfo ƥ��Sheet��Excel��Ϣ����
	 * @param pline ������ö��
	 * @return ��ȡ���б�/null
	 */
	private static List<ProductionContent> getOutputDataFromSheet(Sheet sheet,BWIPdExcelInfo excelinfo,ProdLine pline) {
		if(sheet==null||excelinfo==null) {
			logger.error("������ȡ�������ݣ�����Ϊ�ա�");
			return null;
		}
		Date date;												//��ȡ��������
		Location dateloc=excelinfo.getDateLocation();
		try {
			date=sheet.getRow(dateloc.row).getCell(dateloc.column).getDateCellValue();
			if(date==null)
				logger.error("������ȡ�������ݣ������������ڵ�Sheet["+sheet.getSheetName()+"]Row["+dateloc.row+"]Column["+dateloc.column+"]��Ԫ����ȡ������Ϊ��.");
		} catch(Throwable ex) {
			logger.error("������ȡ�������ݣ������������ڵ�Sheet["+sheet.getSheetName()+"]Row["+dateloc.row+"]Column["+dateloc.column+"]��Ԫ������ȡ������Ϣ��",ex);
			return null;
		}
		Map<Location,ProductionInterval> outputmap=excelinfo.getLocIntervalMap();
		List<ProductionContent> pcontlist=new ArrayList<ProductionContent>();
		ProductionContent tempcont;
		ProductionInterval tempitv;
		Cell cell;
		String oripn;											//Excel�еĲ���������
		String stdpn;											//��׼��������
		Calendar cal=Calendar.getInstance();
		for(Location loc:outputmap.keySet()) {					//��ʼ��������λ��
			cell=sheet.getRow(loc.row).getCell(loc.column);
			if(cell==null) {
				logger.error("������ȡ�������ݣ�Sheet["+sheet.getSheetName()+"]Row["+loc.row+"]Column["+loc.column+"]��Ԫ��Ϊ�ա�");
				return null;
			}
			if(cell.getNumericCellValue()==0) continue;			//�������Ϊ0�������������Ԫ��
			tempcont=new ProductionContent();
			try {
				oripn=sheet.getRow(loc.row).getCell(loc.column+BWIPdExcelInfo.RELCOL_PARTNUM_OUTPUTQTY).getStringCellValue();	//��ȡ������Ʒ
				stdpn=excelinfo.getProdAliasStdPnMap().get(oripn);	//��ò�����Ʒ��׼��
				if(stdpn==null) {
					logger.error("������ȡ�������ݣ�Sheet["+sheet.getSheetName()+"]Row["+loc.row+"]Column["+(loc.column+BWIPdExcelInfo.RELCOL_PARTNUM_OUTPUTQTY)+"]��Ԫ����ȡ�ı���["+oripn+"]û�б�׼����");
					return null;
				}
				tempitv=outputmap.get(loc);						//��ȡ����������Ϣ
				tempcont.setWorkcenter(BWIPLInfo.getStdNameByLineEnum(pline));	//���ù�������
				tempcont.setOutput(stdpn);						//���ò���
				tempcont.setQty(cell.getNumericCellValue());	//��ȡ��������
				tempcont.setDate(date);							//��������
				tempcont.setOperqty((int)Math.floor(sheet.getRow(loc.row).getCell(loc.column+BWIPdExcelInfo.RELCOL_OPERQTY_OUTPUTQTY).getNumericCellValue()));	//���ò���������
				tempcont.setEffmin(tempitv.effmin);				//������Ч����ʱ��
				cal.setTime(date);
				cal.set(Calendar.HOUR, tempitv.beginhour);
				cal.set(Calendar.MINUTE, tempitv.beginmin);
				tempcont.setTfbegin(cal.getTime());				//������ʼ����
				cal.setTime(date);
				cal.set(Calendar.HOUR, tempitv.endhour);
				cal.set(Calendar.MINUTE, tempitv.endmin);
				tempcont.setTfend(cal.getTime());				//���ý�������
				pcontlist.add(tempcont);						//�����б�
			} catch(Throwable ex) {
				logger.error("������ȡ�������ݣ�����Sheet["+sheet.getSheetName()+"]Row["+loc.row+"]Column["+loc.column+"]������ĵĵ�Ԫ��ʱ���ִ���",ex);
				return null;
			}
		}
		return pcontlist;
	}
	
	/**
	 * ��������Ϣ�ӿڣ���ʾ��ȡ�����ݵ�ͳ����Ϣ
	 * @param prodlist ��ȡ����Ϣ�б�
	 */
	public static void debugInfoStastistics(List<ProductionContent> prodlist) {
		if(prodlist==null) {
			logger.error("���󣡲���Ϊ�ա�");
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
			logger.error("����",e);
		}
	}
	
}
