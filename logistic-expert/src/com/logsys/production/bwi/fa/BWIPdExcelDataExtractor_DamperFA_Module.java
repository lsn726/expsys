package com.logsys.production.bwi.fa;

import com.logsys.util.Location;



/**
 * BWI����Excel��Ϣ��ȡ��--������FA��ȡ��--Moduleѹװ
 * @author lx8sn6
 */
public class BWIPdExcelDataExtractor_DamperFA_Module extends BWIPdExcelDataExtractor_DamperFA {

	/**FTQ and Lost Time��ʼ��*/
	protected static final int FTQ_LT_STARTROW=21;
	
	/**FTQ and Lost Time��ͷ��ʼ��*/
	protected static final int FTQ_LT_HEADER_STARTROW=23;
	
	protected void initLocValidatorStrMap() {
		sheetValidator.clear();
		sheetValidator.put(new Location(4,SHIFTEARLY_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "���/���飺\nShift/Group");
		sheetValidator.put(new Location(4,SHIFTMIDDLE_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "���/���飺\nShift/group");
		sheetValidator.put(new Location(4,SHIFTNIGHT_OUTPUTQTYCOL+RELATIVECAL_INTERVAL_BEGIN), "���/���飺\nShift/group");
		sheetValidator.put(new Location(4,10), "����\nDate");
		sheetValidator.put(new Location(4,37), "����\nDate");
		sheetValidator.put(new Location(4,64), "����\nDate");
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
	
}