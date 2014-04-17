package com.logsys.production.bwi.fa;

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
import com.logsys.production.bwi.BWIPdExcelDataExtractor;
import com.logsys.setting.pd.bwi.BWIPLInfo;
import com.logsys.util.DateInterval;
import com.logsys.util.Location;

/**
 * ������RTA�����ߵ�����������ȡ��
 * @author lx8sn6
 */
public abstract class BWIPdExcelDataExtractor_DamperFA extends BWIPdExcelDataExtractor {
	
	private static final Logger logger=Logger.getLogger(BWIPdExcelDataExtractor_DamperFA.class);
	
	/*FA��֤����ö�ٱ�*/
	public static enum IntervalValidator {
		/**�������1��ʼ*/
		Interval_Early1_Begin,
		/**�������1����*/
		Interval_Early1_End,
		/**�������2��ʼ*/
		Interval_Early2_Begin,
		/**�������2����*/
		Interval_Early2_End,
		/**�������3��ʼ*/
		Interval_Early3_Begin,
		/**�������3����*/
		Interval_Early3_End,
		/**�������4��ʼ*/
		Interval_Early4_Begin,
		/**�������4����*/
		Interval_Early4_End,
		/**�������5��ʼ*/
		Interval_Early5_Begin,
		/**�������5����*/
		Interval_Early5_End,
		/**�������6��ʼ*/
		Interval_Early6_Begin,
		/**�������6����*/
		Interval_Early6_End,
		/**�������7��ʼ*/
		Interval_Early7_Begin,
		/**�������7����*/
		Interval_Early7_End,
		/**�������8��ʼ*/
		Interval_Early8_Begin,
		/**�������8����*/
		Interval_Early8_End,
		/**�������9��ʼ*/
		Interval_Early9_Begin,
		/**�������9����*/
		Interval_Early9_End,
		/**�а�����1��ʼ*/
		Interval_Middle1_Begin,
		/**�а�����1����*/
		Interval_Middle1_End,
		/**�а�����2��ʼ*/
		Interval_Middle2_Begin,
		/**�а�����2����*/
		Interval_Middle2_End,
		/**�а�����3��ʼ*/
		Interval_Middle3_Begin,
		/**�а�����3����*/
		Interval_Middle3_End,
		/**�а�����4��ʼ*/
		Interval_Middle4_Begin,
		/**�а�����4����*/
		Interval_Middle4_End,
		/**�а�����5��ʼ*/
		Interval_Middle5_Begin,
		/**�а�����5����*/
		Interval_Middle5_End,
		/**�а�����6��ʼ*/
		Interval_Middle6_Begin,
		/**�а�����6����*/
		Interval_Middle6_End,
		/**�а�����7��ʼ*/
		Interval_Middle7_Begin,
		/**�а�����7����*/
		Interval_Middle7_End,
		/**�а�����8��ʼ*/
		Interval_Middle8_Begin,
		/**�а�����8����*/
		Interval_Middle8_End,
		/**�а�����9��ʼ*/
		Interval_Middle9_Begin,
		/**�а�����9����*/
		Interval_Middle9_End,
		/**�������1��ʼ*/
		Interval_Night1_Begin,
		/**�������1����*/
		Interval_Night1_End,
		/**�������2��ʼ*/
		Interval_Night2_Begin,
		/**�������2����*/
		Interval_Night2_End,
		/**�������3��ʼ*/
		Interval_Night3_Begin,
		/**�������3����*/
		Interval_Night3_End,
		/**�������4��ʼ*/
		Interval_Night4_Begin,
		/**�������4����*/
		Interval_Night4_End,
		/**�������5��ʼ*/
		Interval_Night5_Begin,
		/**�������5����*/
		Interval_Night5_End,
		/**�������6��ʼ*/
		Interval_Night6_Begin,
		/**�������6����*/
		Interval_Night6_End,
		/**�������7��ʼ*/
		Interval_Night7_Begin,
		/**�������7����*/
		Interval_Night7_End,
		/**�������8��ʼ*/
		Interval_Night8_Begin,
		/**�������8����*/
		Interval_Night8_End,
		/**�������9��ʼ*/
		Interval_Night9_Begin,
		/**�������9����*/
		Interval_Night9_End
	}
	
	/**ÿ������ʱ���м�����������*/
	protected static final int SPAN_PER_INTERVAL=4;
	
	/**����������������*/
	protected static final int SHIFTEARLY_OUTPUTQTYCOL=10;
	
	/**�а��������������*/
	protected static final int SHIFTMIDDLE_OUTPUTQTYCOL=37;
	
	/**ҹ���������������*/
	protected static final int SHIFTNIGHT_OUTPUTQTYCOL=64;
	
	/**����PN����������ڲ������������еĲ�ֵ*/
	protected static final int RELATIVECAL_OUTPUTPN=-2;
	
	/**������������������ڲ������������еĲ�ֵ*/
	protected static final int RELATIVECAL_OPERATORQTY=-3;
	
	/**ʱ����������������ڲ������������еĲ�ֵ*/
	protected static final int RELATIVECAL_INTERVAL_BEGIN=-8;
	
	/**ʱ����������������ڲ������������еĲ�ֵ*/
	protected static final int RELATIVECAL_INTERVAL_END=-6;
	
	/**����������ʼ��*/
	protected static final int OUTPUTDATA_STARTROW=11;
	
	/**FTQ and Lost Time��ʼ��*/
	protected static final int FTQ_LT_STARTROW=17;
	
	/**FTQ and Lost Time��ͷ��ʼ��*/
	protected static final int FTQ_LT_HEADER_STARTROW=19;
	
	/**Sheet�Ĳ���ʱ��λ��*/
	protected static final Location DATE_LOC=new Location(4,11);
	
	/**���ڳ�ʼ����ʱ���������ʼ��outputlocdatemap*/
	protected static final Calendar INIT_CALENDAR=Calendar.getInstance();
	
	/**���ں˶�FA�����ʱ�������ʾ����*/
	protected static final Map<IntervalValidator,Date> intervalValidator=new HashMap<IntervalValidator,Date>();
	
	{
		//��ʼ��ʱ����Ϣ
		INIT_CALENDAR.clear();
		INIT_CALENDAR.set(1900, 1, 1);			//��ʼ��Ϊ1900��1��1��
		
	}
	
	public BWIPdExcelDataExtractor_DamperFA() {
		super();
		PRDZONE=BWIPLInfo.STDNAME_DAMPER_FA;
	}
	
	protected void initValidatorStr() {
		validatorStrMap.clear();
		//FAʹ����RTA��PR��ͬ�ı��ʱ�����䷽ʽ
	}
	
	/**
	 * ��ʼ��������֤��
	 */
	protected void initIntervalValidator() {
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 15);
		intervalValidator.put(IntervalValidator.Interval_Early1_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 9);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early1_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 9);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early2_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 10);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early2_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 10);
		cal.set(Calendar.MINUTE, 10);
		intervalValidator.put(IntervalValidator.Interval_Early3_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 11);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early3_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 11);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early4_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 12);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early4_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 12);
		cal.set(Calendar.MINUTE, 30);
		intervalValidator.put(IntervalValidator.Interval_Early5_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 13);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early5_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 13);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early6_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 14);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early6_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 14);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early7_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 15);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early7_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 15);
		cal.set(Calendar.MINUTE, 10);
		intervalValidator.put(IntervalValidator.Interval_Early8_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 16);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early8_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 16);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early9_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 17);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Early9_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 17);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle1_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 18);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle1_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 18);
		cal.set(Calendar.MINUTE, 30);
		intervalValidator.put(IntervalValidator.Interval_Middle2_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 19);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle2_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 19);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle3_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 20);
		cal.set(Calendar.MINUTE, 15);
		intervalValidator.put(IntervalValidator.Interval_Middle3_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 20);
		cal.set(Calendar.MINUTE, 15);
		intervalValidator.put(IntervalValidator.Interval_Middle4_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 21);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle4_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 21);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle5_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 22);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle5_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 22);
		cal.set(Calendar.MINUTE, 10);
		intervalValidator.put(IntervalValidator.Interval_Middle6_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle6_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle7_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle7_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 30);
		intervalValidator.put(IntervalValidator.Interval_Middle8_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle8_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle9_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Middle9_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night1_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night1_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 10);
		intervalValidator.put(IntervalValidator.Interval_Night2_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night2_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night3_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night3_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night4_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 6);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night4_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 6);
		cal.set(Calendar.MINUTE, 30);
		intervalValidator.put(IntervalValidator.Interval_Night5_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 7);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night5_End, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 7);
		cal.set(Calendar.MINUTE, 0);
		intervalValidator.put(IntervalValidator.Interval_Night6_Begin, cal.getTime());
		cal.clear();
		cal.set(1900, 1, 0);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 15);
		intervalValidator.put(IntervalValidator.Interval_Night6_End, cal.getTime());
	}
	
	protected void initPrdAliasMap() {
		prdaliasmap.clear();
		prdaliasmap.put("22261665 �µ�ǰ��", "22261665");
	}
	
	protected void initLocValidatorStrMap() {
		sheetValidator.put(new Location(4,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "���/���飺\nShift/Group");
		sheetValidator.put(new Location(4,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "���/���飺\nShift/group");
		sheetValidator.put(new Location(4,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "���/���飺\nShift/group");
		sheetValidator.put(new Location(4,8), "����\nDate");
		sheetValidator.put(new Location(4,33), "����\nDate");
		sheetValidator.put(new Location(4,58), "����\nDate");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "ʱ��\nHour");
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "ʱ��\nHour");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "ʱ��\nHour");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_OPERATORQTY), "�ϰ�����");			//����������+�ϰ����������еĲ�ֵ
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_OPERATORQTY), "�ϰ�����");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_OPERATORQTY), "�ϰ�����");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_OUTPUTPN), "�����\nPart No");		//����������+PN�����еĲ�ֵ
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_OUTPUTPN), "�����\nPart No");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_OUTPUTPN), "�����\nPart No");
		sheetValidator.put(new Location(8,SHIFTEARLY_OUTPUTQTYCOL), "Сʱ\n����\nHourly Count");
		sheetValidator.put(new Location(8,SHIFTMIDDLE_OUTPUTQTYCOL), "Сʱ\n����\nHourly Count");
		sheetValidator.put(new Location(8,SHIFTNIGHT_OUTPUTQTYCOL), "Сʱ\n����\nHourly Count");
		//ʱ��������֤�ַ�����������FA
		sheetValidator.put(new Location(FTQ_LT_STARTROW,13), "F T Q");
		sheetValidator.put(new Location(FTQ_LT_STARTROW,40), "F T Q");
		sheetValidator.put(new Location(FTQ_LT_STARTROW,67), "F T Q");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,13), "Part No\n�����");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,40), "Part No\n�����");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,67), "Part No\n�����");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,14), "Failure Mode\nʧЧģʽ");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,41), "Failure Mode\nʧЧģʽ");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,68), "Failure Mode\nʧЧģʽ");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,15), "Rework\n����");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,42), "Rework\n����");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,69), "Rework\n����");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,16), "Scrap\n����");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,43), "Scrap\n����");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,70), "Scrap\n����");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,17), "Remarks\n��ע");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,44), "Remarks\n��ע");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,71), "Remarks\n��ע");
		sheetValidator.put(new Location(FTQ_LT_STARTROW,18), "Lost time");
		sheetValidator.put(new Location(FTQ_LT_STARTROW,45), "Lost time");
		sheetValidator.put(new Location(FTQ_LT_STARTROW,72), "Lost time");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,18), "Time\nʱ��");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,45), "Time\nʱ��");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,72), "Time\nʱ��");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,19), "Lost time\n��ʧʱ��");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,46), "Lost time\n��ʧʱ��");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,73), "Lost time\n��ʧʱ��");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,20), "LT mode\n��ʧ����");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,47), "LT mode\n��ʧ����");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,74), "LT mode\n��ʧ����");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,21), "Cause\nԭ��");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,48), "Cause\nԭ��");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,75), "Cause\nԭ��");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,22), "Remarks\n��ע");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,49), "Remarks\n��ע");
		sheetValidator.put(new Location(FTQ_LT_HEADER_STARTROW,76), "Remarks\n��ע");
	}
	
	protected void initOutputLocIntervalMap() {
		outputlocdatemap.clear();
		//��ʼ����֤����Ϣ
		Date begin;
		Date end;
		DateInterval dinterval;
		int rowindex=0;
		//�趨ʱ��β���λ��ͼ
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
				logger.error("δ��ʶ�������Ʒ�ı���:"+outputprd+".��Ҫȷ���Ǵ������ݣ����߱������ձ�����ݲ�ȫ��.λ����Ϣ��Sheet:"+datasrc.getSheetName()+" Row:"+loc.row+" Cell:"+(loc.column+RELATIVECAL_OUTPUTPN));
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

}
