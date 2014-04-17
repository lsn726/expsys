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
	/**��׼����: DAMPER->FA->Module*/
	public static final String STDNAME_DAMPER_FA_MODULE="DamperFA Module";
	/**��׼����: DAMPER->FA->UV*/
	public static final String STDNAME_DAMPER_FA_UV="DamperFA UV";
	/**��׼����: DAMPER->FA->Rebound Stop BF 20/21/22*/
	public static final String STDNAME_DAMPER_FA_REBSTP202122="DamperFA Rebound Stop 202122";
	/**��׼����: DAMPER->FA->Rebound Stop BF 23*/
	public static final String STDNAME_DAMPER_FA_REBSTP23="DamperFA Rebound Stop 23";
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
	/**��׼����: DAMPER->PR->Hardening Line 1*/
	public static final String STDNAME_DAMPER_PR_HARDENING1="DamperPR Hardening Line 1";
	/**��׼����: DAMPER->PR->Hardening Line 2*/
	public static final String STDNAME_DAMPER_PR_HARDENING2="DamperPR Hardening Line 2";
	/**��׼����: DAMPER->PR->Cr Plating Line*/
	public static final String STDNAME_DAMPER_PR_CRPLATING="DamperPR Cr Plating Line";
	/**��׼����: DAMPER->PR->Grinding Line 1*/
	public static final String STDNAME_DAMPER_PR_GRINDING1="DamperPR Grinding Line 1";
	/**��׼����: DAMPER->PR->Grinding Line 2*/
	public static final String STDNAME_DAMPER_PR_GRINDING2="DamperPR Grinding Line 2";
	/**��׼����: DAMPER->PR->Superfinished after Cr-Plating 1*/
	public static final String STDNAME_DAMPER_PR_SUPFIN_AFTER_CRPLATE1="DamperPR Super Finish 1";
	/**��׼����: DAMPER->PR->Superfinished after Cr-Plating 2*/
	public static final String STDNAME_DAMPER_PR_SUPFIN_AFTER_CRPLATE2="DamperPR Super Finish 2";
	/**��׼����: DAMPER->PR->Frication Welding Line 1*/
	public static final String STDNAME_DAMPER_PR_FRICATION_WELD1="DamperPR Frication Welding 1";
	/**��׼����: DAMPER->PR->Frication Welding Line 2*/
	public static final String STDNAME_DAMPER_PR_FRICATION_WELD2="DamperPR Frication Welding 2";
	/**��׼����: DAMPER->PR->CNC0007*/
	public static final String STDNAME_DAMPER_PR_CNC0007="DamperPR CNC0007";
	/**��׼����: DAMPER->PR->CNC0008*/
	public static final String STDNAME_DAMPER_PR_CNC0008="DamperPR CNC0008";
	/**��׼����: DAMPER->PR->CNC0008*/
	public static final String STDNAME_DAMPER_PR_CNC0009="DamperPR CNC0009";
	/**��׼����: DAMPER->PR->CNC0806*/
	public static final String STDNAME_DAMPER_PR_CNC0806="DamperPR CNC0806";
	/**��׼����: DAMPER->PR->CNC0807*/
	public static final String STDNAME_DAMPER_PR_CNC0807="DamperPR CNC0807";
	/**��׼����: DAMPER->PR->CNC0808*/
	public static final String STDNAME_DAMPER_PR_CNC0808="DamperPR CNC0808";
	/**��׼����: DAMPER->PR->CNC0809*/
	public static final String STDNAME_DAMPER_PR_CNC0809="DamperPR CNC0809";
	
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
