package com.logsys.setting.pd.bwi;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ��������Ϣ�汾20140406
 * @author ShaonanLi
 */
public class BWIPLInfo20140331 extends BWIPLInfo {
	
	/**Map<����,��׼����������>ͼ*/
	private static Map<String,String> aliasplmap=new HashMap<String,String>();
	
	/**Map<��׼��������,����������>*/
	private static Map<String,String> prdzonemap=new HashMap<String,String>();

	{
		//����ͼ��ʼ�� RTA
		aliasplmap.put("�ȳ��ͺ���������HBF0031", STDNAME_DAMPER_RTA_HBF0031);
		aliasplmap.put("�ȳ��ͺ���������HBF0835", STDNAME_DAMPER_RTA_HBF0835);
		aliasplmap.put("���&����������Neck Down", STDNAME_DAMPER_RTA_NECKDOWN);
		aliasplmap.put("ǰ����������Front Welding Cell", STDNAME_DAMPER_RTA_FRONTWELDINGCELL);
		aliasplmap.put("�󺸽�������Rear Welding Cell", STDNAME_DAMPER_RTA_REARWELDINGCELL);
		aliasplmap.put("������ϴ Chamfering&Washing", STDNAME_DAMPER_RTA_CHAMFER_WASH);
		aliasplmap.put("��©��ϴLeak test&Washing",STDNAME_DAMPER_RTA_LEAKTEST_WASH);
		aliasplmap.put("�����̣�֧�ܣ�����������S/Seat(Bracket) Welding Cell",STDNAME_DAMPER_RTA_SSEATBRACKET_WELD);
		aliasplmap.put("���������Punching Cell", STDNAME_DAMPER_RTA_PUNCHING_CELL);
		aliasplmap.put("��Ӿ������KTL", STDNAME_DAMPER_RTA_KTL);
		aliasplmap.put("����ѹװ������Punching Press Cell", STDNAME_DAMPER_RTA_PUNCHBUSHING_CELL);
		//����ͼ��ʼ�� PR
		aliasplmap.put("��ĥ������/Coarse grinding", STDNAME_DAMPER_PR_COARSE_GRINDING);
		aliasplmap.put("��������� Hardening line 1", STDNAME_DAMPER_PR_HARDENING1);
		aliasplmap.put("��������� Hardening line 2", STDNAME_DAMPER_PR_HARDENING2);
		aliasplmap.put("��������� Cr-Plating line", STDNAME_DAMPER_PR_CRPLATING);
		aliasplmap.put("��ǰ��ĥ������/Grinding Line 1", STDNAME_DAMPER_PR_GRINDING1);
		aliasplmap.put("��ǰ��ĥ������/Grinding Line 2", STDNAME_DAMPER_PR_GRINDING2);
		aliasplmap.put("�ƺ󳬾������� Superfinished after Cr-Plating 1", STDNAME_DAMPER_PR_SUPFIN_AFTER_CRPLATE1);
		aliasplmap.put("�ƺ󳬾������� Superfinished after Cr-Plating 2", STDNAME_DAMPER_PR_SUPFIN_AFTER_CRPLATE2);
		aliasplmap.put("Ħ���������� Frication Welding Line", STDNAME_DAMPER_PR_FRICATION_WELD1);
		aliasplmap.put("Ħ���������� Frication Welding Line", STDNAME_DAMPER_PR_FRICATION_WELD2);
		aliasplmap.put("���ӹ�CNC 0007������", STDNAME_DAMPER_PR_CNC0007);
		aliasplmap.put("���ӹ�CNC 0008������", STDNAME_DAMPER_PR_CNC0008);
		aliasplmap.put("���ӹ�CNC 0009������", STDNAME_DAMPER_PR_CNC0009);
		aliasplmap.put("���ӹ�CNC 0806������", STDNAME_DAMPER_PR_CNC0806);
		aliasplmap.put("���ӹ�CNC 0807������", STDNAME_DAMPER_PR_CNC0807);
		aliasplmap.put("���ӹ�CNC 0808������", STDNAME_DAMPER_PR_CNC0808);
		aliasplmap.put("���ӹ�CNC 0809������", STDNAME_DAMPER_PR_CNC0809);
		//����ͼ��ʼ��FA
		aliasplmap.put("��װ������/Assy-1", STDNAME_DAMPER_FA_FA1);
		aliasplmap.put("��װ������/Assy-2", STDNAME_DAMPER_FA_FA2);
		aliasplmap.put("��װ������-module", STDNAME_DAMPER_FA_MODULE);
		aliasplmap.put("UV������/UV", STDNAME_DAMPER_FA_UV);
		aliasplmap.put("ֹ����������/Rebound Stop BF-0020-21-22", STDNAME_DAMPER_FA_REBSTP202122);
		aliasplmap.put("ֹ����������/Rebound StopBF-0023", STDNAME_DAMPER_FA_REBSTP23);
		//������->��������ͼ��ʼ�� RTA
		prdzonemap.put(STDNAME_DAMPER_RTA_HBF0031, STDNAME_DAMPER_RTA);
		prdzonemap.put(STDNAME_DAMPER_RTA_HBF0835, STDNAME_DAMPER_RTA);
		prdzonemap.put(STDNAME_DAMPER_RTA_NECKDOWN, STDNAME_DAMPER_RTA);
		prdzonemap.put(STDNAME_DAMPER_RTA_FRONTWELDINGCELL, STDNAME_DAMPER_RTA);
		prdzonemap.put(STDNAME_DAMPER_RTA_REARWELDINGCELL, STDNAME_DAMPER_RTA);
		prdzonemap.put(STDNAME_DAMPER_RTA_CHAMFER_WASH, STDNAME_DAMPER_RTA);
		prdzonemap.put(STDNAME_DAMPER_RTA_LEAKTEST_WASH, STDNAME_DAMPER_RTA);
		prdzonemap.put(STDNAME_DAMPER_RTA_SSEATBRACKET_WELD, STDNAME_DAMPER_RTA);
		prdzonemap.put(STDNAME_DAMPER_RTA_PUNCHING_CELL, STDNAME_DAMPER_RTA);
		prdzonemap.put(STDNAME_DAMPER_RTA_KTL, STDNAME_DAMPER_RTA);
		prdzonemap.put(STDNAME_DAMPER_RTA_PUNCHBUSHING_CELL, STDNAME_DAMPER_RTA);
		//������->��������ͼ��ʼ�� PR
		prdzonemap.put(STDNAME_DAMPER_PR_COARSE_GRINDING, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_HARDENING1, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_HARDENING2, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_CRPLATING, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_GRINDING1, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_GRINDING2, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_SUPFIN_AFTER_CRPLATE1, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_SUPFIN_AFTER_CRPLATE2, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_FRICATION_WELD1, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_FRICATION_WELD2, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_CNC0007, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_CNC0008, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_CNC0009, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_CNC0806, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_CNC0807, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_CNC0808, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_CNC0809, STDNAME_DAMPER_PR);
		//������->��������ͼ��ʼ��FA
		prdzonemap.put(STDNAME_DAMPER_FA_FA1, STDNAME_DAMPER_FA);
		prdzonemap.put(STDNAME_DAMPER_FA_FA2, STDNAME_DAMPER_FA);
		prdzonemap.put(STDNAME_DAMPER_FA_MODULE, STDNAME_DAMPER_FA);
		prdzonemap.put(STDNAME_DAMPER_FA_UV, STDNAME_DAMPER_FA);
		prdzonemap.put(STDNAME_DAMPER_FA_REBSTP202122, STDNAME_DAMPER_FA);
		prdzonemap.put(STDNAME_DAMPER_FA_REBSTP23, STDNAME_DAMPER_FA);
	}

	@Override
	public String getStdProdlineNameByAlias(String alias) {
		if(aliasplmap.containsKey(alias)) return aliasplmap.get(alias);
		return null;
	}
	
	@Override
	public String getProdzoneByStdProdlineName(String StdProdlineName) {
		if(prdzonemap.containsKey(StdProdlineName)) return prdzonemap.get(StdProdlineName);
		return null;
	}
	
	@Override
	public Date getVersion() {
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(2014, Calendar.MARCH, 31);
		return cal.getTime();
	}
	
}
