package com.logsys.setting.pd.bwi;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 生产线信息版本20140406
 * @author ShaonanLi
 */
public class BWIPLInfo20140331 extends BWIPLInfo {
	
	/**Map<别名,标准生产线名称>图*/
	private static Map<String,String> aliasplmap=new HashMap<String,String>();
	
	/**Map<标准生产线名,生产区域名>*/
	private static Map<String,String> prdzonemap=new HashMap<String,String>();

	{
		//别名图初始化
		aliasplmap.put("热成型焊接生产线HBF0031", STDNAME_DAMPER_RTA_HBF0031);
		aliasplmap.put("热成型焊接生产线HBF0835", STDNAME_DAMPER_RTA_HBF0835);
		aliasplmap.put("测漏清洗Leak test&Washing",STDNAME_DAMPER_RTA_LEAKTESTWASHING);
		//生产线->生产区域图初始化
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
