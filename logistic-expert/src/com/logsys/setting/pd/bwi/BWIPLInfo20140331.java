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
		//别名图初始化 RTA
		aliasplmap.put("热成型焊接生产线HBF0031", STDNAME_DAMPER_RTA_HBF0031);
		aliasplmap.put("热成型焊接生产线HBF0835", STDNAME_DAMPER_RTA_HBF0835);
		aliasplmap.put("起鼓&缩口生产线Neck Down", STDNAME_DAMPER_RTA_NECKDOWN);
		aliasplmap.put("前焊接生产线Front Welding Cell", STDNAME_DAMPER_RTA_FRONTWELDINGCELL);
		aliasplmap.put("后焊接生产线Rear Welding Cell", STDNAME_DAMPER_RTA_REARWELDINGCELL);
		aliasplmap.put("倒角清洗 Chamfering&Washing", STDNAME_DAMPER_RTA_CHAMFER_WASH);
		aliasplmap.put("测漏清洗Leak test&Washing",STDNAME_DAMPER_RTA_LEAKTEST_WASH);
		aliasplmap.put("弹簧盘（支架）焊接生产线S/Seat(Bracket) Welding Cell",STDNAME_DAMPER_RTA_SSEATBRACKET_WELD);
		aliasplmap.put("打孔生产线Punching Cell", STDNAME_DAMPER_RTA_PUNCHING_CELL);
		aliasplmap.put("电泳生产线KTL", STDNAME_DAMPER_RTA_KTL);
		aliasplmap.put("衬套压装生产线Punching Press Cell", STDNAME_DAMPER_RTA_PUNCHBUSHING_CELL);
		//别名图初始化 PR
		aliasplmap.put("粗磨生产线/Coarse grinding", STDNAME_DAMPER_PR_COARSE_GRINDING);
		aliasplmap.put("淬火生产线 Hardening line", STDNAME_DAMPER_PR_HARDENING);
		aliasplmap.put("电镀生产线 Cr-Plating line", STDNAME_DAMPER_PR_CRPLATING);
		aliasplmap.put("镀前研磨生产线/Grinding Line", STDNAME_DAMPER_PR_GRINDING);
		//生产线->生产区域图初始化 FA
		prdzonemap.put(STDNAME_DAMPER_FA_FA1, STDNAME_DAMPER_FA);
		prdzonemap.put(STDNAME_DAMPER_FA_FA2, STDNAME_DAMPER_FA);
		//生产线->生产区域图初始化 RTA
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
		//生产线->生产区域图初始化 PR
		prdzonemap.put(STDNAME_DAMPER_PR_COARSE_GRINDING, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_HARDENING, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_CRPLATING, STDNAME_DAMPER_PR);
		prdzonemap.put(STDNAME_DAMPER_PR_GRINDING, STDNAME_DAMPER_PR);
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
