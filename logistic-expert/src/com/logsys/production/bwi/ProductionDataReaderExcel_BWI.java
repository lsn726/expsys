package com.logsys.production.bwi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.logsys.setting.pd.bwi.fa.BWIPdExcelInfoFA;
import com.logsys.setting.pd.bwi.fa.BWIPdExcelInfoFA_Module;
import com.logsys.setting.pd.bwi.fa.BWIPdExcelInfoFA_ReboundStop;
import com.logsys.setting.pd.bwi.fa.BWIPdExcelInfoFA_UV;
import com.logsys.setting.pd.bwi.pr.BWIPdExcelInfoPR;
import com.logsys.setting.pd.bwi.pr.BWIPdExcelInfoPR_CNC0009;
import com.logsys.setting.pd.bwi.pr.BWIPdExcelInfoPR_CoarseGrinding;
import com.logsys.setting.pd.bwi.pr.BWIPdExcelInfoPR_CrPlating;
import com.logsys.setting.pd.bwi.pr.BWIPdExcelInfoPR_FricationWeld;
import com.logsys.setting.pd.bwi.pr.BWIPdExcelInfoPR_Grinding;
import com.logsys.setting.pd.bwi.pr.BWIPdExcelInfoPR_Hardening;
import com.logsys.setting.pd.bwi.rta.BWIPdExcelInfoRTA;
import com.logsys.setting.pd.bwi.rta.BWIPdExcelInfoRTA_Chemfer;
import com.logsys.setting.pd.bwi.rta.BWIPdExcelInfoRTA_KTL;
import com.logsys.setting.pd.bwi.rta.BWIPdExcelInfoRTA_NeckDown;
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
			//wb.getSheet("1").getRow(15).getCell(4).setCellType(Cell.CELL_TYPE_STRING);
			//System.out.println(wb.getSheet("1").getRow(15).getCell(4).getStringCellValue());
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
			for(;daycounter<=maxday;daycounter++) {	//��ʼ����
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
			else if(pline.equals(ProdLine.DAMPER_RTA_KTL))		//[��Ӿ��]
				return new BWIPdExcelInfoRTA_KTL();
			else if(pline.equals(ProdLine.DAMPER_RTA_CHAMFER_WASH)||pline.equals(ProdLine.DAMPER_RTA_WASH_POSTNK))	//[����]/[����ǰ��ϴ]���õ��ǵ�������
				return new BWIPdExcelInfoRTA_Chemfer();
			else
				return new BWIPdExcelInfoRTA();					//����������ʹ��RTA��׼��ȡ��
		} else if(BWIPLInfo.getProdZoneByProdLine(pline).equals(ProdLine.DAMPER_ZONE_PR)) {	//PR��
			if(pline.equals(ProdLine.DAMPER_PR_COARSE_GRIND))	//��ĥ��
				return new BWIPdExcelInfoPR_CoarseGrinding();
			else if(pline.equals(ProdLine.DAMPER_PR_CR_PLATING))//�����
				return new BWIPdExcelInfoPR_CrPlating();
			else if(pline.equals(ProdLine.DAMPER_PR_HARDENING1)||pline.equals(ProdLine.DAMPER_PR_HARDENING2))//�����
				return new BWIPdExcelInfoPR_Hardening();
			else if(pline.equals(ProdLine.DAMPER_PR_GRINDING_PRECR1)||pline.equals(ProdLine.DAMPER_PR_GRINDING_PRECR2))//��ĥ��
				return new BWIPdExcelInfoPR_Grinding();
			else if(pline.equals(ProdLine.DAMPER_PR_FRICATION_WELD1)||pline.equals(ProdLine.DAMPER_PR_FRICATION_WELD2))//Ħ����
				return new BWIPdExcelInfoPR_FricationWeld();
			else if(pline.equals(ProdLine.DAMPER_PR_CNC0009))	//CNC0009
				return new BWIPdExcelInfoPR_CNC0009();
			else												//����������ʹ��PR��׼��ȡ��
				return new BWIPdExcelInfoPR();
		} else if(BWIPLInfo.getProdZoneByProdLine(pline).equals(ProdLine.DAMPER_ZONE_FA)) {	//FA����
			if(pline.equals(ProdLine.DAMPER_FA_MODULE))			//Moduleѹװ
				return new BWIPdExcelInfoFA_Module();
			else if(pline.equals(ProdLine.DAMPER_FA_UV))		//UV��
				return new BWIPdExcelInfoFA_UV();
			else if(pline.equals(ProdLine.DAMPER_FA_REBOUND_STOP_202122)||pline.equals(ProdLine.DAMPER_FA_REBOUND_STOP_23))	//�ƶ�����
				return new BWIPdExcelInfoFA_ReboundStop();
			else
				return new BWIPdExcelInfoFA();
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
				logger.error("������ȡ�������ݣ�Ӧ�ô����������ݵ�Sheet["+sheet.getSheetName()+"]Row["+loc.row+"]Column["+loc.column+"]��Ԫ��Ϊ�ա�");
				return null;
			}
			try {
				if(cell.getNumericCellValue()==0) continue;			//�������Ϊ0�������������Ԫ��
			} catch(Throwable ex) {
				logger.error("������ȡ�������ݣ�Ӧ�ô����������ݵ�Sheet["+sheet.getSheetName()+"]Row["+loc.row+"]Column["+loc.column+"]��Ԫ����ȡ���ݴ���!",ex);
				return null;
			}
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
	 * ��ȡͳ����Ϣ
	 * @param prodlist ��ȡ����Ϣ�б�
	 */
	public static String getStastisticsInfo(List<ProductionContent> prodlist) {
		if(prodlist==null) {
			logger.error("���󣡲���Ϊ�ա�");
			return null;
		}
		String info="��ȡ���ݵİ�Сʱ������Ϣ���£�\n";
		try {
			Map<Date,Double> count=new HashMap<Date,Double>();
			Double temp;
			for(ProductionContent pc:prodlist) {
				temp=pc.getQty();
				if(count.containsKey(pc.getDate()))
					temp+=count.get(pc.getDate());
				count.put(pc.getDate(), temp);
			}
			//��������ʼ
			List<Entry<Date, Double>> mapentlist=new ArrayList<Entry<Date, Double>>(count.entrySet());
			Collections.sort(mapentlist, new Comparator<Entry<Date,Double>>(){
				public int compare(Entry<Date, Double> e1, Entry<Date, Double> e2) {      
			        return e1.getKey().compareTo(e2.getKey());
			    }
			});
			//�����������
			DateFormat dateformat=new SimpleDateFormat("YYYY-MM-dd");
			Double sum=0.0;
			for(int i=0;i<mapentlist.size();i++) {
				info+="Date [ "+dateformat.format(mapentlist.get(i).getKey())+" ] "+"--------> OutputQty ["+mapentlist.get(i).getValue()+"]       \n";
				sum+=mapentlist.get(i).getValue();
			}
			info+="�ܼƲ�����"+sum+"\n";
		} catch (Throwable e) {
			logger.error("ͳ�Ʋ�����Ϣ����",e);
			return null;
		}
		return info;
	}
	
}
