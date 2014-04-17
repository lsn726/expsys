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
		aliasplmap.put("淬火生产线 Hardening line 1", STDNAME_DAMPER_PR_HARDENING1);
		aliasplmap.put("淬火生产线 Hardening line 2", STDNAME_DAMPER_PR_HARDENING2);
		aliasplmap.put("电镀生产线 Cr-Plating line", STDNAME_DAMPER_PR_CRPLATING);
		aliasplmap.put("镀前研磨生产线/Grinding Line 1", STDNAME_DAMPER_PR_GRINDING1);
		aliasplmap.put("镀前研磨生产线/Grinding Line 2", STDNAME_DAMPER_PR_GRINDING2);
		aliasplmap.put("镀后超精生产线 Superfinished after Cr-Plating 1", STDNAME_DAMPER_PR_SUPFIN_AFTER_CRPLATE1);
		aliasplmap.put("镀后超精生产线 Superfinished after Cr-Plating 2", STDNAME_DAMPER_PR_SUPFIN_AFTER_CRPLATE2);
		aliasplmap.put("摩擦焊生产线 Frication Welding Line", STDNAME_DAMPER_PR_FRICATION_WELD1);
		aliasplmap.put("摩擦焊生产线 Frication Welding Line", STDNAME_DAMPER_PR_FRICATION_WELD2);
		aliasplmap.put("机加工CNC 0007生产线", STDNAME_DAMPER_PR_CNC0007);
		aliasplmap.put("机加工CNC 0008生产线", STDNAME_DAMPER_PR_CNC0008);
		aliasplmap.put("机加工CNC 0009生产线", STDNAME_DAMPER_PR_CNC0009);
		aliasplmap.put("机加工CNC 0806生产线", STDNAME_DAMPER_PR_CNC0806);
		aliasplmap.put("机加工CNC 0807生产线", STDNAME_DAMPER_PR_CNC0807);
		aliasplmap.put("机加工CNC 0808生产线", STDNAME_DAMPER_PR_CNC0808);
		aliasplmap.put("机加工CNC 0809生产线", STDNAME_DAMPER_PR_CNC0809);
		//别名图初始化FA
		aliasplmap.put("组装生产线/Assy-1", STDNAME_DAMPER_FA_FA1);
		aliasplmap.put("组装生产线/Assy-2", STDNAME_DAMPER_FA_FA2);
		aliasplmap.put("组装生产线-module", STDNAME_DAMPER_FA_MODULE);
		aliasplmap.put("UV生产线/UV", STDNAME_DAMPER_FA_UV);
		aliasplmap.put("止动环生产线/Rebound Stop BF-0020-21-22", STDNAME_DAMPER_FA_REBSTP202122);
		aliasplmap.put("止动环生产线/Rebound StopBF-0023", STDNAME_DAMPER_FA_REBSTP23);
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
		//生产线->生产区域图初始化FA
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
