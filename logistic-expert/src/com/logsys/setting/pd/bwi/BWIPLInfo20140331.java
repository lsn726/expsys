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
		aliasplmap.put("��©��ϴLeak test&Washing",STDNAME_DAMPER_RTA_LEAKTESTWASHING);
		//������->��������ͼ��ʼ��
		prdzonemap.put(STDNAME_DAMPER_FA_FA1, STDNAME_DAMPER_FA);
		prdzonemap.put(STDNAME_DAMPER_FA_FA2, STDNAME_DAMPER_FA);
		prdzonemap.put(STDNAME_DAMPER_RTA_HBF0031, STDNAME_DAMPER_RTA);
		prdzonemap.put(STDNAME_DAMPER_RTA_LEAKTESTWASHING, STDNAME_DAMPER_RTA);
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
