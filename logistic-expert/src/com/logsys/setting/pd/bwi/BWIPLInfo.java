package com.logsys.setting.pd.bwi;

import java.util.Date;

/**
 * BWI生产线信息抽象类
 * @author ShaonanLi
 */
public abstract class BWIPLInfo {
	
	//**************************生产区域名称设置*************************
	/**标准名称: DAMPER->FA*/
	public static final String STDNAME_DAMPER_FA="DamperFA";
	
	/**标准名称: DAMPER->RTA*/
	public static final String STDNAME_DAMPER_RTA="DamperRTA";
	
	/**标准名称: DAMPER->PR*/
	public static final String STDNAME_DAMPER_PR="DamperPR";
	//**************************生产线名称设置--FA**************************
	/**标准名称: DAMPER->FA->Final Assembly 1*/
	public static final String STDNAME_DAMPER_FA_FA1="DamperFA Final Assembly 1";
	
	/**标准名称: DAMPER->FA->Final Assembly 2*/
	public static final String STDNAME_DAMPER_FA_FA2="DamperFA Final Assembly 2";
	//**************************生产线名称设置--RTA**************************	
	/**标准名称: DAMPER->RTA->Hot Bottom Forming 0031*/
	public static final String STDNAME_DAMPER_RTA_HBF0031="DamperRTA Hot Bottom Forming 0031";
	/**标准名称: DAMPER->RTA->Hot Bottom Forming 0835*/
	public static final String STDNAME_DAMPER_RTA_HBF0835="DamperRTA Hot Bottom Forming 0835";
	/**标准名称: DAMPER->RTA->Neck Down*/
	public static final String STDNAME_DAMPER_RTA_NECKDOWN="DamperRTA Neck Down";
	/**标准名称: DAMPER->RTA->Front Welding Cell*/
	public static final String STDNAME_DAMPER_RTA_FRONTWELDINGCELL="DamperRTA Front Welding Cell";
	/**标准名称: DAMPER->RTA->Rear Welding Cell*/
	public static final String STDNAME_DAMPER_RTA_REARWELDINGCELL="DamperRTA Rear Welding Cell";
	/**标准名称: DAMPER->RTA->Chamfering and Washing*/
	public static final String STDNAME_DAMPER_RTA_CHAMFER_WASH="DamperRTA Chamfering and Washing";
	/**标准名称: DAMPER->RTA->Leak Test and Washing*/
	public static final String STDNAME_DAMPER_RTA_LEAKTEST_WASH="DamperRTA Leak Test and Washing";
	/**标准名称: DAMPER->RTA->Spring Seat/Bracket Welding Cell*/
	public static final String STDNAME_DAMPER_RTA_SSEATBRACKET_WELD="DamperRTA Seat Bracket Welding";
	/**标准名称: DAMPER->RTA->Punching Cell*/
	public static final String STDNAME_DAMPER_RTA_PUNCHING_CELL="DamperRTA Punching Cell";
	/**标准名称: DAMPER->RTA->KTL*/
	public static final String STDNAME_DAMPER_RTA_KTL="DamperRTA KTL";
	/**标准名称: DAMPER->RTA->Punch Bushing Cell*/
	public static final String STDNAME_DAMPER_RTA_PUNCHBUSHING_CELL="DamperRTA Punch Bushing Cell";
	//**************************生产线名称设置--PR**************************
	/**标准名称: DAMPER->PR->Coarse Grinding*/
	public static final String STDNAME_DAMPER_PR_COARSE_GRINDING="DamperPR Coarse Grinding";
	/**标准名称: DAMPER->PR->Hardening Line*/
	public static final String STDNAME_DAMPER_PR_HARDENING="DamperPR Hardening Line";
	/**标准名称: DAMPER->PR->Cr Plating Line*/
	public static final String STDNAME_DAMPER_PR_CRPLATING="DamperPR Cr Plating Line";
	/**标准名称: DAMPER->PR->Grinding Line*/
	public static final String STDNAME_DAMPER_PR_GRINDING="DamperPR Grinding Line";
	/**标准名称: DAMPER->PR->Grinding Line 2*/
	public static final String STDNAME_DAMPER_PR_GRINDING2="DamperPR Grinding Line 2";
	
	/**
	 * 通过别名获取标准生产线名称
	 * @param alias 别名
	 * @return 生产线名称/失败null
	 */
	public abstract String getStdProdlineNameByAlias(String alias);
	
	/**
	 * 通过生产线标准名获取生产区域名
	 * @param StdProdlineName 标准生产线名称
	 * @return 生产区域名称
	 */
	public abstract String getProdzoneByStdProdlineName(String StdProdlineName);
	
	/**
	 * 返回信息类版本
	 * @return 版本
	 */
	public abstract Date getVersion();
	
}
