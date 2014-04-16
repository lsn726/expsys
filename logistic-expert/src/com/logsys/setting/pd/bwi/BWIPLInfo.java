package com.logsys.setting.pd.bwi;

import java.util.Date;

/**
 * BWI��������Ϣ������
 * @author ShaonanLi
 */
public abstract class BWIPLInfo {
	
	//**************************����������������*************************
	/**��׼����: DAMPER->FA*/
	public static final String STDNAME_DAMPER_FA="DamperFA";
	
	/**��׼����: DAMPER->RTA*/
	public static final String STDNAME_DAMPER_RTA="DamperRTA";
	
	/**��׼����: DAMPER->PR*/
	public static final String STDNAME_DAMPER_PR="DamperPR";
	//**************************��������������--FA**************************
	/**��׼����: DAMPER->FA->Final Assembly 1*/
	public static final String STDNAME_DAMPER_FA_FA1="DamperFA Final Assembly 1";
	
	/**��׼����: DAMPER->FA->Final Assembly 2*/
	public static final String STDNAME_DAMPER_FA_FA2="DamperFA Final Assembly 2";
	//**************************��������������--RTA**************************	
	/**��׼����: DAMPER->RTA->Hot Bottom Forming 0031*/
	public static final String STDNAME_DAMPER_RTA_HBF0031="DamperRTA Hot Bottom Forming 0031";
	/**��׼����: DAMPER->RTA->Hot Bottom Forming 0835*/
	public static final String STDNAME_DAMPER_RTA_HBF0835="DamperRTA Hot Bottom Forming 0835";
	/**��׼����: DAMPER->RTA->Neck Down*/
	public static final String STDNAME_DAMPER_RTA_NECKDOWN="DamperRTA Neck Down";
	/**��׼����: DAMPER->RTA->Front Welding Cell*/
	public static final String STDNAME_DAMPER_RTA_FRONTWELDINGCELL="DamperRTA Front Welding Cell";
	/**��׼����: DAMPER->RTA->Rear Welding Cell*/
	public static final String STDNAME_DAMPER_RTA_REARWELDINGCELL="DamperRTA Rear Welding Cell";
	/**��׼����: DAMPER->RTA->Chamfering and Washing*/
	public static final String STDNAME_DAMPER_RTA_CHAMFER_WASH="DamperRTA Chamfering and Washing";
	/**��׼����: DAMPER->RTA->Leak Test and Washing*/
	public static final String STDNAME_DAMPER_RTA_LEAKTEST_WASH="DamperRTA Leak Test and Washing";
	/**��׼����: DAMPER->RTA->Spring Seat/Bracket Welding Cell*/
	public static final String STDNAME_DAMPER_RTA_SSEATBRACKET_WELD="DamperRTA Seat Bracket Welding";
	/**��׼����: DAMPER->RTA->Punching Cell*/
	public static final String STDNAME_DAMPER_RTA_PUNCHING_CELL="DamperRTA Punching Cell";
	/**��׼����: DAMPER->RTA->KTL*/
	public static final String STDNAME_DAMPER_RTA_KTL="DamperRTA KTL";
	/**��׼����: DAMPER->RTA->Punch Bushing Cell*/
	public static final String STDNAME_DAMPER_RTA_PUNCHBUSHING_CELL="DamperRTA Punch Bushing Cell";
	//**************************��������������--PR**************************
	/**��׼����: DAMPER->PR->Coarse Grinding*/
	public static final String STDNAME_DAMPER_PR_COARSE_GRINDING="DamperPR Coarse Grinding";
	/**��׼����: DAMPER->PR->Hardening Line*/
	public static final String STDNAME_DAMPER_PR_HARDENING="DamperPR Hardening Line";
	/**��׼����: DAMPER->PR->Cr Plating Line*/
	public static final String STDNAME_DAMPER_PR_CRPLATING="DamperPR Cr Plating Line";
	/**��׼����: DAMPER->PR->Grinding Line*/
	public static final String STDNAME_DAMPER_PR_GRINDING="DamperPR Grinding Line";
	/**��׼����: DAMPER->PR->Grinding Line 2*/
	public static final String STDNAME_DAMPER_PR_GRINDING2="DamperPR Grinding Line 2";
	
	/**
	 * ͨ��������ȡ��׼����������
	 * @param alias ����
	 * @return ����������/ʧ��null
	 */
	public abstract String getStdProdlineNameByAlias(String alias);
	
	/**
	 * ͨ�������߱�׼����ȡ����������
	 * @param StdProdlineName ��׼����������
	 * @return ������������
	 */
	public abstract String getProdzoneByStdProdlineName(String StdProdlineName);
	
	/**
	 * ������Ϣ��汾
	 * @return �汾
	 */
	public abstract Date getVersion();
	
}
