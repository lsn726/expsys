package com.logsys.production.bwi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.logsys.production.LostTimeContent;
import com.logsys.production.ProductionContent;
import com.logsys.setting.Settings;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.util.DateInterval;
import com.logsys.util.Location;

/**
 * BWI�������ձ�Excel��Ϣ��ȡ��������
 * @author ShaonanLi
 */
public abstract class BWIPdExcelDataExtractor {
	
	private static final Logger logger=Logger.getLogger(BWIPdExcelDataExtractor.class);
	
	/**������Hashmap��д����ַ���*/
	public static final String EMPTYSTRVALUE="EMPTYSTRVALUE";
	
	/**�����������ַ���������validateDatasrc()�б���ȡ���Ƿ�������ȷ����������*/
	public static String PRDZONE;
	
	/**��������Ϣ*/
	protected static final BWIPLInfo plinfo=Settings.BWISettings.plinfo;
	
	/*��֤�ַ���ö�ٱ�*/
	public static enum ValidatorStr {
		/**�������1*/
		Interval_Early1,
		/**�������2*/
		Interval_Early2,
		/**�������3*/
		Interval_Early3,
		/**�������4*/
		Interval_Early4,
		/**�������5*/
		Interval_Early5,
		/**�������6*/
		Interval_Early6,
		/**�������7*/
		Interval_Early7,
		/**�������8*/
		Interval_Early8,
		/**�������9*/
		Interval_Early9,
		/**�а�����1*/
		Interval_Middle1,
		/**�а�����2*/
		Interval_Middle2,
		/**�а�����3*/
		Interval_Middle3,
		/**�а�����4*/
		Interval_Middle4,
		/**�а�����5*/
		Interval_Middle5,
		/**�а�����6*/
		Interval_Middle6,
		/**�а�����7*/
		Interval_Middle7,
		/**�а�����8*/
		Interval_Middle8,
		/**�а�����9*/
		Interval_Middle9,
		/**�������1*/
		Interval_Night1,
		/**�������2*/
		Interval_Night2,
		/**�������3*/
		Interval_Night3,
		/**�������4*/
		Interval_Night4,
		/**�������5*/
		Interval_Night5,
		/**�������6*/
		Interval_Night6,
		/**�������7*/
		Interval_Night7,
		/**�������8*/
		Interval_Night8,
		/**�������9*/
		Interval_Night9
	}
	
	/**��Ʒ����->��׼�����ձ�*/
	protected Map<String,String> prdaliasmap=new HashMap<String,String>();
	
	/**Sheet������֤��:��Ҫ��֤��λ��->��֤�ַ������ձ�*/
	protected Map<Location,String> sheetValidator=new HashMap<Location,String>();
	
	/**Sheet����λ��->ʱ��������ձ�*/
	protected Map<Location,DateInterval> outputlocdatemap=new HashMap<Location,DateInterval>();
	
	/**��֤�ַ���ö��->��֤�ַ������ձ�������֤PR��RTA�ı���ʽ*/
	protected Map<ValidatorStr,String> validatorStrMap=new HashMap<ValidatorStr,String>();
	
	public BWIPdExcelDataExtractor() {
		initValidatorStr();		//���ȳ�ʼ����֤�ַ���
		initPrdAliasMap();		//��ʼ������->��׼�����ձ�
		initLocValidatorStrMap();	//��ʼ��λ��->��֤�ַ������ձ���Ҫ�ȳ�ʼ����֤�ַ����ͱ���->��׼�����ձ�
		initOutputLocIntervalMap();	//��ʼ��λ��->��������ԵԱ�
	}
	
	/**
	 * ��֤�ַ���ö��->��֤�ַ������ʼ��
	 */
	protected void initValidatorStr() {}
	
	/**
	 * ��ʼ����Ʒ����->��׼�����ձ�
	 */
	protected void initPrdAliasMap() {}
	
	/**
	 * ��ʼ��Sheetλ��->��֤�ַ������ձ�
	 * ����ע�⣡������޸Ķ�����Ϣ��Ҫͬʱ��������ͼ
	 */
	protected void initLocValidatorStrMap() {}
	
	/**
	 * ��ʼ��Sheet����λ��->ʱ��������ձ�
	 */
	protected void initOutputLocIntervalMap() {}
	
	/**
	 * ͨ��ָ����׼����������(BWIPLInfo)��ȡdatasrc�еĲ�������
	 * @param datasrc �������ݵ�������ϢSheet�е�����
	 * @param stdprodline ��׼����������(BWIPLInfo)
	 * @return ��ȡ�����������б�
	 */
	public abstract List<ProductionContent> extractOutputData(Sheet datasrc,String stdprodline) throws Throwable;
	
	/**
	 * ͨ��ָ����׼����������(BWIPLInfo)��ȡdatasrc�е���ʧʱ������
	 * @param datasrc �������ݵ�������ϢSheet�е�����
	 * @param stdprodline ��׼����������(BWIPLInfo)
	 * @return ��ȡ����ʧʱ�������б�
	 */
	public abstract List<LostTimeContent> extractLostTimeData(Sheet datasrc,String stdprodline) throws Throwable;
	
	/**
	 * ͨ��ָ����׼����������(BWIPLInfo)���ж�datasrc�е����ݸ�ʽ�Ƿ���ȷ.��֤�������ñ���PRDZONE����֤
	 * @param datasrc �������ݵ�������ϢSheet�е�����
	 * @param stdprodline ��׼����������(BWIPLInfo)
	 * @return ͨ��������֤true/δͨ��false
	 */
	public boolean validateDatasrc(Sheet datasrc, String stdprodline) throws Throwable {
		if(datasrc==null||stdprodline==null) {
			logger.error("��֤������Excel����Դʧ�ܣ�����Ϊ��.");
			return false;
		}
		if(!plinfo.getProdzoneByStdProdlineName(stdprodline).equals(PRDZONE)) {
			logger.error("��֤������Excel����Դʧ�ܣ�������"+stdprodline+"����"+PRDZONE+"��׼����������Դ.");
			return false;
		}
		Cell cell;
		String validstr;
		for(Location loc:sheetValidator.keySet()) {			//��֤��������
			cell=datasrc.getRow(loc.row).getCell(loc.column);
			validstr=sheetValidator.get(loc);
			//�����ȡֵ��ƥ�䣬���߻�ȡ�ַ���Ϊ""�Ĵ���ֵEMPTYSTRVALUEʱ
			if(!cell.getStringCellValue().equals(validstr))
				if(!validstr.equals(EMPTYSTRVALUE)) {		//�����֤�ַ�����ָ���Ŀ��ַ���
					logger.error("Sheet:"+datasrc.getSheetName()+" "+"λ����:"+loc.row+" ��:"+loc.column+" ������:["+cell.getStringCellValue()+"]����֤���е�����:["+sheetValidator.get(loc)+"]������������������Ƿ���ȷ��������֤�������Ƿ����.");
					return false;
				}
			//System.out.println(cell.getStringCellValue()+"["+loc.row+"/"+loc.column+"]");
		}
		return true;
	}
	
}
