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
		//����ͼ��ʼ��
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
		//������->��������ͼ��ʼ��
		prdzonemap.put(STDNAME_DAMPER_FA_FA1, STDNAME_DAMPER_FA);
		prdzonemap.put(STDNAME_DAMPER_FA_FA2, STDNAME_DAMPER_FA);
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
