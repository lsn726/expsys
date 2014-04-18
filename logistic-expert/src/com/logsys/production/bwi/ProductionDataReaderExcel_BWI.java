package com.logsys.production.bwi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.logsys.production.ProductionContent;
import com.logsys.production.bwi.fa.BWIPdExcelDataExtractor_DamperFA_Module;
import com.logsys.production.bwi.pr.BWIPdExcelDataExtractor_DamperPR_CNC;
import com.logsys.production.bwi.pr.BWIPdExcelDataExtractor_DamperPR_CoarseGrinding;
import com.logsys.production.bwi.pr.BWIPdExcelDataExtractor_DamperPR_CrPlating;
import com.logsys.production.bwi.pr.BWIPdExcelDataExtractor_DamperPR_FricationWeld;
import com.logsys.production.bwi.pr.BWIPdExcelDataExtractor_DamperPR_Grinding;
import com.logsys.production.bwi.pr.BWIPdExcelDataExtractor_DamperPR_Hardening;
import com.logsys.production.bwi.pr.BWIPdExcelDataExtractor_DamperPR_SupFin;
import com.logsys.production.bwi.rta.BWIPdExcelDataExtractor_DamperRTA_ChamferWash;
import com.logsys.production.bwi.rta.BWIPdExcelDataExtractor_DamperRTA_FrontWeldingCell;
import com.logsys.production.bwi.rta.BWIPdExcelDataExtractor_DamperRTA_HBF;
import com.logsys.production.bwi.rta.BWIPdExcelDataExtractor_DamperRTA_KTL;
import com.logsys.production.bwi.rta.BWIPdExcelDataExtractor_DamperRTA_LeakTestWashing;
import com.logsys.production.bwi.rta.BWIPdExcelDataExtractor_DamperRTA_NeckDown;
import com.logsys.production.bwi.rta.BWIPdExcelDataExtractor_DamperRTA_PunchBushing;
import com.logsys.production.bwi.rta.BWIPdExcelDataExtractor_DamperRTA_PunchCell;
import com.logsys.production.bwi.rta.BWIPdExcelDataExtractor_DamperRTA_RearWeldingCell;
import com.logsys.production.bwi.rta.BWIPdExcelDataExtractor_DamperRTA_SSeatBracketWelding;
import com.logsys.setting.Settings;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.util.Location;

/**
 * ����������ȡ������Excel��ȡ
 * @author ShaonanLi
 */
public class ProductionDataReaderExcel_BWI {

	private static final Logger logger=Logger.getLogger(ProductionDataReaderExcel_BWI.class);
	
	private static BWIPLInfo plinfo=Settings.BWISettings.plinfo;
	
	/**����Sheet��*/
	public static final String CONFIGURE_SHEETNAME="Configuration";
	
	/**����Sheet�У�����������������*/
	public static final Location PLLOC=new Location(1,0);
	
	/**
	 * ���ļ�filepath�ж�ȡ����
	 * @param filepath Ŀ��Excel���ļ�·��
	 * @return ��ȡ����������
	 */
	public static List<ProductionContent> readDataFromFile(String filepath) {
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
		List<ProductionContent> pcontlist;
		List<ProductionContent> temp;
		int daycounter;				//����������
		String plname;				//����������
		String stdplname;			//��׼����������
		BWIPdExcelDataExtractor extractor;	//������ȡ���İ汾
		try {
			//��ȡ����������
			plname=wb.getSheet(CONFIGURE_SHEETNAME).getRow(PLLOC.row).getCell(PLLOC.column).getStringCellValue();
			stdplname=plinfo.getStdProdlineNameByAlias(plname);
			if(stdplname==null) {
				logger.error("���󣡲�����ȷ��ȡ��׼����������,���������ƴ��󣬻����Ƕ�Ӧͼ����û�д��������ƵĶ�Ӧ��Ŀ����������Ϊ��"+plname);
				return null;
			}
			extractor=getExtractorByStdPrdLine(stdplname);		//��ȡ��ȡ��
			if(extractor==null) {
				logger.error("����û�к��ʵ�ǰ�����ߵ���ȡ����"+stdplname);
				return null;
			}
			pcontlist=new ArrayList<ProductionContent>();
			for(daycounter=1;;daycounter++) {					//�ӵ��µ�һ�쿪ʼ����
				sheet=wb.getSheet(String.valueOf(daycounter));	//�������ڱ���������Sheet
				if(sheet==null) break;							//��������û��Sheetʱ��˵�������Ѿ��������
				temp=extractor.extractOutputData(sheet, stdplname);	//��ȡ���ݵ���ʱ�б�
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
	 * ͨ����׼���������ƻ�ȡExcel������ȡ��
	 * @param stdplname ��׼����������
	 * @return Excel������ȡ��
	 */
	private static BWIPdExcelDataExtractor getExtractorByStdPrdLine(String stdplname) {
		if(stdplname==null) return null;
		if(plinfo.getProdzoneByStdProdlineName(stdplname).equals(BWIPLInfo.STDNAME_DAMPER_RTA))	{ //RTA����
			if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_RTA_LEAKTEST_WASH))		//��©��ϴ������
				return new BWIPdExcelDataExtractor_DamperRTA_LeakTestWashing();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_RTA_HBF0031)||stdplname.equals(BWIPLInfo.STDNAME_DAMPER_RTA_HBF0835))		//�ײ��ȳ���0031/0835������
				return new BWIPdExcelDataExtractor_DamperRTA_HBF();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_RTA_NECKDOWN))		//�������
				return new BWIPdExcelDataExtractor_DamperRTA_NeckDown();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_RTA_FRONTWELDINGCELL))//������ǰ���ӵ�Ԫ
				return new BWIPdExcelDataExtractor_DamperRTA_FrontWeldingCell();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_RTA_REARWELDINGCELL))	//�󺸽ӵ�Ԫ
				return new BWIPdExcelDataExtractor_DamperRTA_RearWeldingCell();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_RTA_CHAMFER_WASH))	//������ϴ
				return new BWIPdExcelDataExtractor_DamperRTA_ChamferWash();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_RTA_SSEATBRACKET_WELD))	//������֧�ܺ���
				return new BWIPdExcelDataExtractor_DamperRTA_SSeatBracketWelding();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_RTA_PUNCHING_CELL))	//��׵�Ԫ
				return new BWIPdExcelDataExtractor_DamperRTA_PunchCell();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_RTA_KTL))				//��Ӿ��
				return new BWIPdExcelDataExtractor_DamperRTA_KTL();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_RTA_PUNCHBUSHING_CELL))	//����ѹװ
				return new BWIPdExcelDataExtractor_DamperRTA_PunchBushing();
		} else if(plinfo.getProdzoneByStdProdlineName(stdplname).equals(BWIPLInfo.STDNAME_DAMPER_PR)) { //PR����
			if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_COARSE_GRINDING))		//��ĥ������
				return new BWIPdExcelDataExtractor_DamperPR_CoarseGrinding();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_HARDENING1))		//���������1
				return new BWIPdExcelDataExtractor_DamperPR_Hardening();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_HARDENING2))		//���������2
				return new BWIPdExcelDataExtractor_DamperPR_Hardening();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_CRPLATING))		//���������
				return new BWIPdExcelDataExtractor_DamperPR_CrPlating();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_GRINDING1))		//��ǰ��ĥ��1
				return new BWIPdExcelDataExtractor_DamperPR_Grinding();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_GRINDING2))		//��ǰ��ĥ��2
				return new BWIPdExcelDataExtractor_DamperPR_Grinding();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_SUPFIN_AFTER_CRPLATE1))	//�ɺ󳬾�1
				return new BWIPdExcelDataExtractor_DamperPR_SupFin();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_SUPFIN_AFTER_CRPLATE2))	//�ɺ󳬾�2
				return new BWIPdExcelDataExtractor_DamperPR_SupFin();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_FRICATION_WELD1))	//Ħ����1
				return new BWIPdExcelDataExtractor_DamperPR_FricationWeld();
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_FRICATION_WELD2))	//Ħ����2
				return new BWIPdExcelDataExtractor_DamperPR_FricationWeld();
			//CNC * 7
			else if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_CNC0007)||stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_CNC0008)||stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_CNC0009)||stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_CNC0806)||stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_CNC0807)||stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_CNC0808)||stdplname.equals(BWIPLInfo.STDNAME_DAMPER_PR_CNC0809))
				return new BWIPdExcelDataExtractor_DamperPR_CNC();
		} else if(plinfo.getProdzoneByStdProdlineName(stdplname).equals(BWIPLInfo.STDNAME_DAMPER_FA)) {
			if(stdplname.equals(BWIPLInfo.STDNAME_DAMPER_FA_MODULE))	//��ĥ������
				return new BWIPdExcelDataExtractor_DamperFA_Module();
		}
		return null;
	}

}
