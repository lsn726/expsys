package com.logsys.production.bwi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * ������RTA�����ߵ�����������ȡ��
 * @author lx8sn6
 *
 */
public class BWIPdExcelDataExtractor_DamperRTA implements BWIPdExcelDataExtractor {
	
	private static final Logger logger=Logger.getLogger(BWIPdExcelDataExtractor_DamperRTA.class);
	
	/**��������Ϣ*/
	protected static final BWIPLInfo plinfo=Settings.BWISettings.plinfo;
	
	/**ÿ������ʱ���м�����������*/
	protected static final int SPAN_PER_INTERVAL=4;
	
	/**����������������*/
	protected static final int SHIFTEARLY_OUTPUTQTYCOL=8;
	
	/**�а��������������*/
	protected static final int SHIFTMIDDLE_OUTPUTQTYCOL=33;
	
	/**ҹ���������������*/
	protected static final int SHIFTNIGHT_OUTPUTQTYCOL=58;
	
	/**����PN����������ڲ������������еĲ�ֵ*/
	protected static final int RELATIVECAL_OUTPUTPN=-2;
	
	/**������������������ڲ������������еĲ�ֵ*/
	protected static final int RELATIVECAL_OPERATORQTY=-3;
	
	/**����������ʼ��*/
	protected static final int OUTPUTDATA_STARTROW=11;
	
	/**Sheet�Ĳ���ʱ��λ��*/
	protected static final Location DATE_LOC=new Location(4,9);
	
	/**���ڳ�ʼ����ʱ���������ʼ��outputlocdatemap*/
	protected static final Calendar INIT_CALENDAR=Calendar.getInstance();
	
	/**��֤�ַ���ö��->��֤�ַ������ձ�*/
	protected Map<ValidatorStr,String> validatorStrMap=new HashMap<ValidatorStr,String>();
	
	/**��Ʒ����->��׼�����ձ�*/
	protected Map<String,String> prdaliasmap=new HashMap<String,String>();
	
	/**Sheet������֤��:λ��->ʶ���ַ����ԵԱ�*/
	protected Map<Location,String> sheetValidator=new HashMap<Location,String>();
	
	/**Sheet����λ��->ʱ��������ձ�*/
	protected Map<Location,DateInterval> outputlocdatemap=new HashMap<Location,DateInterval>();
	
	/*��֤�ַ���ö�ٱ�*/
	protected enum ValidatorStr {
		Interval_0815_0900,
		Interval_0900_1000,
		Interval_1000_1100,
		Interval_1100_1200,
		Interval_1200_1300,
		Interval_1300_1400,
		Interval_1400_1500,
		Interval_1500_1600,
		Interval_1600_1645,
		Interval_1645_1700,
		Interval_1700_1800,
		Interval_1800_1900,
		Interval_1900_2000,
		Interval_2000_2100,
		Interval_2100_2200,
		Interval_2200_2300,
		Interval_2300_2400,
		Interval_2400_2515,
	}
	
	public BWIPdExcelDataExtractor_DamperRTA() {
		initValidatorStr();
		initPrdAliasMap();
		initSheetValidator();
		initOutputLocIntervalMap();
	}
	
	/**
	 * ��֤�ַ���ö��->��֤�ַ�������ʼ��
	 */
	protected void initValidatorStr() {
		validatorStrMap.clear();
		validatorStrMap.put(ValidatorStr.Interval_0815_0900, "8��15-9��00");
		validatorStrMap.put(ValidatorStr.Interval_0900_1000, "9��00-10��00");
		validatorStrMap.put(ValidatorStr.Interval_1000_1100, "10��00-11��00");
		validatorStrMap.put(ValidatorStr.Interval_1100_1200, "11��00-12��00");
		validatorStrMap.put(ValidatorStr.Interval_1200_1300, "12��00-13��00");
		validatorStrMap.put(ValidatorStr.Interval_1300_1400, "13��00-14��00");
		validatorStrMap.put(ValidatorStr.Interval_1400_1500, "14��00-15��00");
		validatorStrMap.put(ValidatorStr.Interval_1500_1600, "15��00-16��00");
		validatorStrMap.put(ValidatorStr.Interval_1600_1645, "16��00-16��45");
		validatorStrMap.put(ValidatorStr.Interval_1645_1700, "16��45-17��00");
		validatorStrMap.put(ValidatorStr.Interval_1700_1800, "17��00-18��00");
		validatorStrMap.put(ValidatorStr.Interval_1800_1900, "18��00-19��00");
		validatorStrMap.put(ValidatorStr.Interval_1900_2000, "19��00-20��00");
		validatorStrMap.put(ValidatorStr.Interval_2000_2100, "20��00-21��00");
		validatorStrMap.put(ValidatorStr.Interval_2100_2200, "21��00-22��00");
		validatorStrMap.put(ValidatorStr.Interval_2200_2300, "22��00-23��00");
		validatorStrMap.put(ValidatorStr.Interval_2300_2400, "23��00-24��00");
		validatorStrMap.put(ValidatorStr.Interval_2400_2515, "00��00-01��15");
	}
	
	/**
	 * ��ʼ����Ʒ����->��׼�����ձ�
	 */
	protected void initPrdAliasMap() {
		prdaliasmap.clear();
		prdaliasmap.put("22261665 �µ�ǰ��", "22261665");
		prdaliasmap.put("22261664 �µ�ǰ��", "22261664");
		prdaliasmap.put("22258275 �µϺ�", "22258275");
		prdaliasmap.put("22258280 �µϺ��", "22258280");
		prdaliasmap.put("22265450 ����ǰ", "22265450");
		prdaliasmap.put("22261401 ������", "22261401");
		prdaliasmap.put("22262045 �ֶ���ǰ", "22262045");
		prdaliasmap.put("22262043 �ֶ���ǰ", "22262043");
		prdaliasmap.put("22261449 �ֶ��ֺ�", "22261449");
		prdaliasmap.put("22272186 ����ǰ", "22272186");
		prdaliasmap.put("22272003 �����", "22272003");
	}
	
	/**
	 * ��ʼ��Sheet��֤��Ϣ���ձ�
	 * ����ע�⣡������޸Ķ�����Ϣ��Ҫͬʱ��������ͼ
	 */
	protected void initSheetValidator() {
		sheetValidator.clear();
		int rowindex=0;
		sheetValidator.put(new Location(4,2), "���/���飺\nShift/Group");
		sheetValidator.put(new Location(4,27), "���/���飺\nShift/group");
		sheetValidator.put(new Location(4,52), "���/���飺\nShift/group");
		sheetValidator.put(new Location(4,8), "����\nDate");
		sheetValidator.put(new Location(4,33), "����\nDate");
		sheetValidator.put(new Location(4,58), "����\nDate");
		sheetValidator.put(new Location(8,2), "ʱ��\nHour");
		sheetValidator.put(new Location(8,27), "ʱ��\nHour");
		sheetValidator.put(new Location(8,52), "ʱ��\nHour");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_OPERATORQTY), "�ϰ�����");			//����������+�ϰ����������еĲ�ֵ
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_OPERATORQTY), "�ϰ�����");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_OPERATORQTY), "�ϰ�����");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_OUTPUTPN), "�����\nPart No");		//����������+PN�����еĲ�ֵ
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_OUTPUTPN), "�����\nPart No");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_OUTPUTPN), "�����\nPart No");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL), "Сʱ\n����\nHourly Count");
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL), "Сʱ\n����\nHourly Count");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL), "Сʱ\n����\nHourly Count");
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_0815_0900)); //��ʼ��+ÿ�����������
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_1645_1700)); //��ʼ��+ÿ���������������������+1��������һ������
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_0900_1000));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_1700_1800));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1000_1100));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_1800_1900));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1100_1200));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_1900_2000));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1200_1300));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_2000_2100));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1300_1400));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_2100_2200));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1400_1500));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_2200_2300));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1500_1600));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_2300_2400));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex,2), validatorStrMap.get(ValidatorStr.Interval_1600_1645));
		sheetValidator.put(new Location(OUTPUTDATA_STARTROW+SPAN_PER_INTERVAL*rowindex++,27), validatorStrMap.get(ValidatorStr.Interval_2400_2515));
		sheetValidator.put(new Location(17,11), "F T Q");
		sheetValidator.put(new Location(17,36), "F T Q");
		sheetValidator.put(new Location(17,61), "F T Q");
		sheetValidator.put(new Location(19,11), "Part No\n�����");
		sheetValidator.put(new Location(19,36), "Part No\n�����");
		sheetValidator.put(new Location(19,61), "Part No\n�����");
		sheetValidator.put(new Location(19,12), "Failure Mode\nʧЧģʽ");
		sheetValidator.put(new Location(19,37), "Failure Mode\nʧЧģʽ");
		sheetValidator.put(new Location(19,62), "Failure Mode\nʧЧģʽ");
		sheetValidator.put(new Location(19,13), "Rework\n����");
		sheetValidator.put(new Location(19,38), "Rework\n����");
		sheetValidator.put(new Location(19,63), "Rework\n����");
		sheetValidator.put(new Location(19,14), "Scrap\n����");
		sheetValidator.put(new Location(19,39), "Scrap\n����");
		sheetValidator.put(new Location(19,64), "Scrap\n����");
		sheetValidator.put(new Location(19,15), "Remarks\n��ע");
		sheetValidator.put(new Location(19,40), "Remarks\n��ע");
		sheetValidator.put(new Location(19,65), "Remarks\n��ע");
		sheetValidator.put(new Location(17,16), "Lost time");
		sheetValidator.put(new Location(17,41), "Lost time");
		sheetValidator.put(new Location(17,66), "Lost time");
		sheetValidator.put(new Location(19,16), "Time\nʱ��");
		sheetValidator.put(new Location(19,41), "Time\nʱ��");
		sheetValidator.put(new Location(19,66), "Time\nʱ��");
		sheetValidator.put(new Location(19,17), "Lost time\n��ʧʱ��");
		sheetValidator.put(new Location(19,42), "Lost time\n��ʧʱ��");
		sheetValidator.put(new Location(19,67), "Lost time\n��ʧʱ��");
		sheetValidator.put(new Location(19,18), "LT mode\n��ʧ����");
		sheetValidator.put(new Location(19,43), "LT mode\n��ʧ����");
		sheetValidator.put(new Location(19,68), "LT mode\n��ʧ����");
		sheetValidator.put(new Location(19,19), "Cause\nԭ��");
		sheetValidator.put(new Location(19,44), "Cause\nԭ��");
		sheetValidator.put(new Location(19,69), "Cause\nԭ��");
		sheetValidator.put(new Location(19,20), "Remarks\n��ע");
		sheetValidator.put(new Location(19,45), "Remarks\n��ע");
		sheetValidator.put(new Location(19,70), "Remarks\n��ע");
	}
	
	/**
	 * ��ʼ��Sheet����λ��->ʱ��������ձ�
	 */
	protected void initOutputLocIntervalMap() {
		outputlocdatemap.clear();
		//��ʼ����֤����Ϣ
		Date begin;
		Date end;
		DateInterval dinterval;
		int rowindex=0;
		//�趨ʱ��β���λ��ͼ
		//��ʼ��ʱ����Ϣ
		INIT_CALENDAR.clear();
		INIT_CALENDAR.set(1900, 1, 1);			//��ʼ��Ϊ1900��1��1��
		Calendar cal=(Calendar)INIT_CALENDAR.clone();
		//��������ʼ��
		rowindex=OUTPUTDATA_STARTROW;			//��������ʼ��
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY, 8);		//�������俪ʼʱ��
		cal.set(Calendar.MINUTE, 15);
		begin=cal.getTime();
		cal.set(Calendar.HOUR_OF_DAY, 9);		//�����������ʱ��
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);	//�趨��������
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,9);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY, 10);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,10);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY, 11);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,11);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,12);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,13);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,13);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,14);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,14);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,15);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,15);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,16);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,16);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,16);
		cal.set(Calendar.MINUTE, 45);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTEARLY_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		//�а������ʼ��
		rowindex=OUTPUTDATA_STARTROW;			//��������ʼ��
		cal.set(Calendar.HOUR_OF_DAY, 16);		//�������俪ʼʱ��
		cal.set(Calendar.MINUTE, 45);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY, 17);		//�����������ʱ��
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);	//�趨��������
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,17);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,18);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,18);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,19);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,19);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,20);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,20);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,21);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,21);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,22);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,22);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,24);
		cal.set(Calendar.MINUTE, 0);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,24);
		cal.set(Calendar.MINUTE, 0);
		begin=cal.getTime();
		cal=(Calendar)INIT_CALENDAR.clone();
		cal.set(Calendar.HOUR_OF_DAY,25);
		cal.set(Calendar.MINUTE, 15);
		end=cal.getTime();
		dinterval=new DateInterval(begin,end);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
		outputlocdatemap.put(new Location(rowindex++,SHIFTMIDDLE_OUTPUTQTYCOL), dinterval);
	}
	
	@Override
	public List<ProductionContent> extractOutputData(Sheet datasrc, String stdprodline) throws Throwable {
		if(!validateDatasrc(datasrc,stdprodline)) return null;
		Date proddate=datasrc.getRow(DATE_LOC.row).getCell(DATE_LOC.column).getDateCellValue();		//��ȡ��ǰsheet������
		Calendar prodcal=Calendar.getInstance();
		prodcal.clear();
		prodcal.setTime(proddate);
		long milsecdiff=prodcal.getTimeInMillis()-INIT_CALENDAR.getTimeInMillis();					//�������ʼ������֮��Ĳ������� 
		List<ProductionContent> prodlist=new ArrayList<ProductionContent>();
		ProductionContent prodcont;
		Cell cell;
		Calendar startcal;		//ʱ��ο�ʼ
		Calendar endcal;		//ʱ��ν���
		String outputprd;		//������Ϣ
		for(Location loc:outputlocdatemap.keySet()) {			//������������λ�ö�ȡ��������
			cell=datasrc.getRow(loc.row).getCell(loc.column);
			if(cell==null) continue;							//���Ϊnull��˵��û�и�λ��û����������
			if(cell.getNumericCellValue()==0) continue;			//��������Ϊ0������
			prodcont=new ProductionContent();
			prodcont.setOutputqty(cell.getNumericCellValue());	//������������
			prodcont.setWorkcenter(stdprodline);				//���ù�������
			outputprd=datasrc.getRow(loc.row).getCell(loc.column+RELATIVECAL_OUTPUTPN).getStringCellValue();	//���вλ�������ݵ�Ԫ����ȡ
			if(!prdaliasmap.containsKey(outputprd)) {			//���û�ж����������Ʒ�ı�������˵�����ִ������ݣ����߶��ձ���Ҫ����
				logger.error("δ��ʶ�������Ʒ�ı���:"+outputprd+".��Ҫȷ���Ǵ������ݣ����߱������ձ������ݲ�ȫ��.λ����Ϣ��Sheet:"+datasrc.getSheetName()+" Row:"+loc.row+" Cell:"+(loc.column+RELATIVECAL_OUTPUTPN));
				return null;
			}
			prodcont.setOutputprd(prdaliasmap.get(outputprd));	//�趨��������
			prodcont.setOperatorqty((int)datasrc.getRow(loc.row).getCell(loc.column+RELATIVECAL_OPERATORQTY).getNumericCellValue());	//���вλ�������ݵ�Ԫ����ȡ�����趨��������
			prodcont.setDate(proddate);							//�趨��������
			startcal=Calendar.getInstance();					//�ɺ���������俪ʼ����ȷʱ��
			startcal.setTime(outputlocdatemap.get(loc).begindate);
			startcal.setTimeInMillis(startcal.getTimeInMillis()+milsecdiff);
			prodcont.setTfbegin(startcal.getTime());
			endcal=Calendar.getInstance();						//�ɺ�����������������ȷʱ��
			endcal.setTime(outputlocdatemap.get(loc).enddate);
			endcal.setTimeInMillis(endcal.getTimeInMillis()+milsecdiff);
			prodcont.setTfend(endcal.getTime());
			prodlist.add(prodcont);								//�����б�
		}
		return prodlist;
	}

	@Override
	public List<LostTimeContent> extractLostTimeData(Sheet datasrc, String stdprodline) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateDatasrc(Sheet datasrc, String stdprodline) throws Throwable {
		if(datasrc==null||stdprodline==null) {
			logger.error("��֤RTA������Excel����Դʧ�ܣ�����Ϊ��.");
			return false;
		}
		if(plinfo.getProdzoneByStdProdlineName(stdprodline)!=BWIPLInfo.STDNAME_DAMPER_RTA) {
			logger.error("��֤RTA������Excel����Դʧ�ܣ�������"+stdprodline+"����RTA��׼����������Դ.");
			return false;
		}
		Cell cell;
		for(Location loc:sheetValidator.keySet()) {			//��֤���������
			cell=datasrc.getRow(loc.row).getCell(loc.column);
			if(!cell.getStringCellValue().equals(sheetValidator.get(loc))) {
				logger.error("Sheet:"+datasrc.getSheetName()+" "+"λ����:"+loc.row+" ��:"+loc.column+" ������:["+cell.getStringCellValue()+"]����֤���е�����:["+sheetValidator.get(loc)+"]�������������������Ƿ���ȷ��������֤�������Ƿ����.");
				return false;
			}
			//System.out.println(cell.getStringCellValue()+"["+loc.row+"/"+loc.column+"]");
		}
		return true;
	}

}