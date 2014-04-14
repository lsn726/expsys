package com.logsys.setting.pd.bwi;

import java.util.Date;

/**
 * BWI生产线信息抽象类
 * @author ShaonanLi
 */
public abstract class BWIPLInfo {
	
	//**************************生产区域名称设置*************************
	/**标准名称: DAMPER->FA*/
	public static final String STDNAME_DAMPER_FA="Damper FA";
	
	/**标准名称: DAMPER->RTA*/
	public static final String STDNAME_DAMPER_RTA="Damper RTA";
	
	/**标准名称: DAMPER->PR*/
	public static final String STDNAME_DAMPER_PR="Damper PR";
	//**************************生产线名称设置--FA**************************
	/**标准名称: DAMPER->FA->Final Assembly 1*/
	public static final String STDNAME_DAMPER_FA_FA1="Damper Final Assembly 1";
	
	/**标准名称: DAMPER->FA->Final Assembly 2*/
	public static final String STDNAME_DAMPER_FA_FA2="Damper Final Assembly 2";
	//**************************生产线名称设置--RTA**************************	
	/**标准名称: DAMPER->RTA->Hot Bottom Forming 0031*/
	public static final String STDNAME_DAMPER_RTA_HBF0031="Damper Hot Bottom Forming 0031";
	/**标准名称: DAMPER->RTA->Leak Test and Washing*/
	public static final String STDNAME_DAMPER_RTA_LEAKTESTWASHING="Damper Leak Test and Washing";
	/**标准名称: DAMPER->RTA->Hot Bottom Forming 0835*/
	public static final String STDNAME_DAMPER_RTA_HBF0835="Damper Hot Bottom Forming 0835";
	/**标准名称: DAMPER->RTA->Neck Down*/
	public static final String STDNAME_DAMPER_RTA_NECKDOWN="Damper Neck Down";
	/**标准名称: DAMPER->RTA->Front Welding Cell*/
	public static final String STDNAME_DAMPER_RTA_FRONTWELDINGCELL="Damper Front Welding Cell";
	/**标准名称: DAMPER->RTA->Rear Welding Cell*/
	public static final String STDNAME_DAMPER_RTA_REARWELDINGCELL="Damper Rear Welding Cell";
	/**标准名称: DAMPER->RTA->Chamfering and Washing*/
	public static final String STDNAME_DAMPER_RTA_CHAMFER_WASH="Damper Rear Welding Cell";
	
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
