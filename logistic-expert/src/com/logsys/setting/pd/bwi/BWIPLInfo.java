package com.logsys.setting.pd.bwi;

import java.util.Date;

/**
 * BWI��������Ϣ������
 * @author ShaonanLi
 */
public abstract class BWIPLInfo {
	
	//**************************����������������*************************
	/**��׼����: DAMPER->FA*/
	public static final String STDNAME_DAMPER_FA="Damper FA";
	
	/**��׼����: DAMPER->RTA*/
	public static final String STDNAME_DAMPER_RTA="Damper RTA";
	
	/**��׼����: DAMPER->PR*/
	public static final String STDNAME_DAMPER_PR="Damper PR";
	//**************************��������������--FA**************************
	/**��׼����: DAMPER->FA->Final Assembly 1*/
	public static final String STDNAME_DAMPER_FA_FA1="Damper Final Assembly 1";
	
	/**��׼����: DAMPER->FA->Final Assembly 2*/
	public static final String STDNAME_DAMPER_FA_FA2="Damper Final Assembly 2";
	//**************************��������������--RTA**************************	
	/**��׼����: DAMPER->RTA->Hot Bottom Forming 0031*/
	public static final String STDNAME_DAMPER_RTA_HBF0031="Damper Hot Bottom Forming 0031";
	/**��׼����: DAMPER->RTA->Hot Bottom Forming 0835*/
	public static final String STDNAME_DAMPER_RTA_HBF0835="Damper Hot Bottom Forming 0835";
	/**��׼����: DAMPER->RTA->Neck Down*/
	public static final String STDNAME_DAMPER_RTA_NECKDOWN="Damper Neck Down";
	/**��׼����: DAMPER->RTA->Front Welding Cell*/
	public static final String STDNAME_DAMPER_RTA_FRONTWELDINGCELL="Damper Front Welding Cell";
	/**��׼����: DAMPER->RTA->Rear Welding Cell*/
	public static final String STDNAME_DAMPER_RTA_REARWELDINGCELL="Damper Rear Welding Cell";
	/**��׼����: DAMPER->RTA->Chamfering and Washing*/
	public static final String STDNAME_DAMPER_RTA_CHAMFER_WASH="Damper Chamfering and Washing";
	/**��׼����: DAMPER->RTA->Leak Test and Washing*/
	public static final String STDNAME_DAMPER_RTA_LEAKTEST_WASH="Damper Leak Test and Washing";
	/**��׼����: DAMPER->RTA->Spring Seat/Bracket Welding Cell*/
	public static final String STDNAME_DAMPER_RTA_SSEATBRACKET_WELD="Damper Seat Bracket Welding";
	/**��׼����: DAMPER->RTA->Punching Cell*/
	public static final String STDNAME_DAMPER_RTA_PUNCHING_CELL="Damper Punching Cell";
	/**��׼����: DAMPER->RTA->KTL*/
	public static final String STDNAME_DAMPER_RTA_KTL="Damper KTL";
	/**��׼����: DAMPER->RTA->Punch Bushing Cell*/
	public static final String STDNAME_DAMPER_RTA_PUNCHBUSHING_CELL="Damper Punch Bushing Cell";
	
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
