package com.logsys.setting.pd.bwi;

import java.util.HashMap;
import java.util.Map;

/**
 * BWI��������Ϣ��
 * @author ShaonanLi
 */
public class BWIPLInfo {
	
	/**
	 * ������ö��
	 * @author lx8sn6
	 */
	public static enum ProdLine {
		/**��Ч������*/
		INVALID,
		/**������--����--FA*/
		DAMPER_ZONE_FA,
		/**������--����--RTA*/
		DAMPER_ZONE_RTA,
		/**������--����--PR*/
		DAMPER_ZONE_PR,
		/**������--FA--FA1*/
		DAMPER_FA_FA1,
		/**������--FA--FA2*/
		DAMPER_FA_FA2,
		/**������--FA--Module*/
		DAMPER_FA_MODULE,
		/**������--FA--UV*/
		DAMPER_FA_UV,
		/**������--FA--Rebound Stop BF20/21/22*/
		DAMPER_FA_REBOUND_STOP_202122,
		/**������--FA--Rebound Stop BF23*/
		DAMPER_FA_REBOUND_STOP_23,
		/**������--RTA--Hot Bottom Forming 0031*/
		DAMPER_RTA_HBF0031,
		/**������--RTA--Hot Bottom Forming 0835*/
		DAMPER_RTA_HBF0835,
		/**������--RTA--Neck Down*/
		DAMPER_RTA_NECKDOWN,
		/**������--RTA--Washing After Neck Down*/
		DAMPER_RTA_WASH_POSTNK,
		/**������--RTA--Front Welding Cell*/
		DAMPER_RTA_FRONTWELDCELL,
		/**������--RTA--Rear Welding Cell*/
		DAMPER_RTA_REARWELDCELL,
		/**������--RTA--Chamfering and Washing*/
		DAMPER_RTA_CHAMFER_WASH,
		/**������--RTA--Leak Test and Washing*/
		DAMPER_RTA_LEAKTEST_WASH,
		/**������--RTA--Spring Seat and Bracket Welding*/
		DAMPER_RTA_SEATBRACKET_WELD,
		/**������--RTA--Punching Cell*/
		DAMPER_RTA_PUNCHING,
		/**������--RTA--KTL*/
		DAMPER_RTA_KTL,
		/**������--RTA--Bushing Press Cell*/
		DAMPER_RTA_BUSHINGPRESS,
		/**������--PR--Coarse Grinding*/
		DAMPER_PR_COARSE_GRIND,
		/**������--PR--Frication Welding 1*/
		DAMPER_PR_FRICATION_WELD1,
		/**������--PR--Frication Welding 2*/
		DAMPER_PR_FRICATION_WELD2,
		/**������--PR--Hardening 1*/
		DAMPER_PR_HARDENING1,
		/**������--PR--Hardening 2*/
		DAMPER_PR_HARDENING2,
		/**������--PR--CNC 0007*/
		DAMPER_PR_CNC0007,
		/**������--PR--CNC 0008*/
		DAMPER_PR_CNC0008,
		/**������--PR--CNC 0009*/
		DAMPER_PR_CNC0009,
		/**������--PR--CNC 0806*/
		DAMPER_PR_CNC0806,
		/**������--PR--CNC 0807*/
		DAMPER_PR_CNC0807,
		/**������--PR--CNC 0808*/
		DAMPER_PR_CNC0808,
		/**������--PR--CNC 0809*/
		DAMPER_PR_CNC0809,
		/**������--PR--Grinding Before Cr-Plating 1*/
		DAMPER_PR_GRINDING_PRECR1,
		/**������--PR--Grinding Before Cr-Plating 2*/
		DAMPER_PR_GRINDING_PRECR2,
		/**������--PR--Cr Plating Line*/
		DAMPER_PR_CR_PLATING,
		/**������--PR--SuperFinish after Cr-Plating 1*/
		DAMPER_PR_SUPERFINISH1,
		/**������--PR--SuperFinish after Cr-Plating 2*/
		DAMPER_PR_SUPERFINISH2,
	}
	
	/**������ö��->��׼���� ӳ��ͼ*/
	private static Map<ProdLine,String> STDNAME_MAP=new HashMap<ProdLine,String>();
	
	/**������ö��->����������ö�� ӳ��ͼ*/
	private static Map<ProdLine,ProdLine> LINE_ZONE_MAP=new HashMap<ProdLine,ProdLine>();
	
	/**����->������ö�� ӳ��ͼ*/
	private static Map<String,ProdLine> ALIAS_LINE_MAP=new HashMap<String,ProdLine>();
	
	static {
		//**************��������������ö��->��׼���Ƴ�ʼ��***************
		STDNAME_MAP.put(ProdLine.DAMPER_ZONE_FA, "DamperFA");
		STDNAME_MAP.put(ProdLine.DAMPER_ZONE_RTA, "DamperRTA");
		STDNAME_MAP.put(ProdLine.DAMPER_ZONE_PR, "DamperPR");
		
		//**************FA����ö��->��׼���Ƴ�ʼ��***************
		STDNAME_MAP.put(ProdLine.DAMPER_FA_FA1, "DamperFA FA 1");
		STDNAME_MAP.put(ProdLine.DAMPER_FA_FA2, "DamperFA FA 2");
		STDNAME_MAP.put(ProdLine.DAMPER_FA_MODULE, "DamperFA Module");
		STDNAME_MAP.put(ProdLine.DAMPER_FA_UV, "DamperFA UV");
		STDNAME_MAP.put(ProdLine.DAMPER_FA_REBOUND_STOP_202122, "DamperFA RebStop 202122");
		STDNAME_MAP.put(ProdLine.DAMPER_FA_REBOUND_STOP_23, "DamperFA RebStop 23");
		//**************RTA����ö��->��׼���Ƴ�ʼ��***************
		STDNAME_MAP.put(ProdLine.DAMPER_RTA_HBF0031, "DamperRTA HBF 0031");
		STDNAME_MAP.put(ProdLine.DAMPER_RTA_HBF0835, "DamperRTA HBF 0835");
		STDNAME_MAP.put(ProdLine.DAMPER_RTA_NECKDOWN, "DamperRTA Neck Down");
		STDNAME_MAP.put(ProdLine.DAMPER_RTA_WASH_POSTNK, "DamperRTA Wash After ND");
		STDNAME_MAP.put(ProdLine.DAMPER_RTA_FRONTWELDCELL, "DamperRTA FrontWeldCell");
		STDNAME_MAP.put(ProdLine.DAMPER_RTA_REARWELDCELL, "DamperRTA RearWeldCell");
		STDNAME_MAP.put(ProdLine.DAMPER_RTA_CHAMFER_WASH, "DamperRTA Chemfer Wash");
		STDNAME_MAP.put(ProdLine.DAMPER_RTA_LEAKTEST_WASH, "DamperRTA LeakTest Wash");
		STDNAME_MAP.put(ProdLine.DAMPER_RTA_SEATBRACKET_WELD, "DamperRTA Seat Bracket Weld");
		STDNAME_MAP.put(ProdLine.DAMPER_RTA_PUNCHING, "DamperRTA Punching");
		STDNAME_MAP.put(ProdLine.DAMPER_RTA_KTL, "DamperRTA KTL");
		STDNAME_MAP.put(ProdLine.DAMPER_RTA_BUSHINGPRESS, "DamperRTA Bushing Press");
		//**************PR����ö��->��׼���Ƴ�ʼ��***************
		STDNAME_MAP.put(ProdLine.DAMPER_PR_COARSE_GRIND, "DamperPR Coarse Grinding");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_FRICATION_WELD1, "DamperPR Frication Weld 1");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_FRICATION_WELD2, "DamperPR Frication Weld 2");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_HARDENING1, "DamperPR Hardening 1");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_HARDENING2, "DamperPR Hardening 2");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_CNC0007, "DamperPR CNC0007");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_CNC0008, "DamperPR CNC0008");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_CNC0009, "DamperPR CNC0009");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_CNC0806, "DamperPR CNC0806");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_CNC0807, "DamperPR CNC0807");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_CNC0808, "DamperPR CNC0808");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_CNC0809, "DamperPR CNC0809");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_GRINDING_PRECR1, "DamperPR Grinding Pre Cr 1");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_GRINDING_PRECR2, "DamperPR Grinding Pre Cr 2");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_CR_PLATING, "DamperPR Cr Plating");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_SUPERFINISH1, "DamperPR SuperFinish 1");
		STDNAME_MAP.put(ProdLine.DAMPER_PR_SUPERFINISH2, "DamperPR SuperFinish 2");
		
		//**************FA������->���������ʼ��*************
		LINE_ZONE_MAP.put(ProdLine.DAMPER_FA_FA1, ProdLine.DAMPER_ZONE_FA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_FA_FA2, ProdLine.DAMPER_ZONE_FA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_FA_MODULE, ProdLine.DAMPER_ZONE_FA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_FA_UV, ProdLine.DAMPER_ZONE_FA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_FA_REBOUND_STOP_202122, ProdLine.DAMPER_ZONE_FA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_FA_REBOUND_STOP_23, ProdLine.DAMPER_ZONE_FA);
		//**************RTA������->���������ʼ��*************
		LINE_ZONE_MAP.put(ProdLine.DAMPER_RTA_HBF0031, ProdLine.DAMPER_ZONE_RTA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_RTA_HBF0835, ProdLine.DAMPER_ZONE_RTA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_RTA_NECKDOWN, ProdLine.DAMPER_ZONE_RTA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_RTA_WASH_POSTNK, ProdLine.DAMPER_ZONE_RTA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_RTA_FRONTWELDCELL, ProdLine.DAMPER_ZONE_RTA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_RTA_REARWELDCELL, ProdLine.DAMPER_ZONE_RTA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_RTA_CHAMFER_WASH, ProdLine.DAMPER_ZONE_RTA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_RTA_LEAKTEST_WASH, ProdLine.DAMPER_ZONE_RTA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_RTA_SEATBRACKET_WELD, ProdLine.DAMPER_ZONE_RTA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_RTA_PUNCHING, ProdLine.DAMPER_ZONE_RTA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_RTA_KTL, ProdLine.DAMPER_ZONE_RTA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_RTA_BUSHINGPRESS, ProdLine.DAMPER_ZONE_RTA);
		//**************PR������->���������ʼ��***************
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_COARSE_GRIND, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_FRICATION_WELD1, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_FRICATION_WELD2, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_HARDENING1, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_HARDENING2, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_CNC0007, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_CNC0008, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_CNC0009, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_CNC0806, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_CNC0807, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_CNC0808, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_CNC0809, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_GRINDING_PRECR1, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_GRINDING_PRECR2, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_CR_PLATING, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_SUPERFINISH1, ProdLine.DAMPER_ZONE_PR);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_PR_SUPERFINISH2, ProdLine.DAMPER_ZONE_PR);
		
		//**************FA�����߱���->������ö�� ӳ��ͼ��ʼ��*************
		ALIAS_LINE_MAP.put("��װ������/Assy-1", ProdLine.DAMPER_FA_FA1);
		ALIAS_LINE_MAP.put("��װ������/Assy-2", ProdLine.DAMPER_FA_FA2);
		ALIAS_LINE_MAP.put("��װ������-module", ProdLine.DAMPER_FA_MODULE);
		ALIAS_LINE_MAP.put("UV������/UV", ProdLine.DAMPER_FA_UV);
		ALIAS_LINE_MAP.put("ֹ����������/Rebound Stop BF-0020-21-22", ProdLine.DAMPER_FA_REBOUND_STOP_202122);
		ALIAS_LINE_MAP.put("ֹ����������/Rebound StopBF-0023", ProdLine.DAMPER_FA_REBOUND_STOP_23);
		//**************PR�����߱���->������ö�� ӳ��ͼ��ʼ��*************
		ALIAS_LINE_MAP.put("��ĥ������/Coarse grinding", ProdLine.DAMPER_PR_COARSE_GRIND);
		ALIAS_LINE_MAP.put("Ħ���������� Frication Welding Line 1", ProdLine.DAMPER_PR_FRICATION_WELD1);
		ALIAS_LINE_MAP.put("Ħ���������� Frication Welding Line 2", ProdLine.DAMPER_PR_FRICATION_WELD2);
		ALIAS_LINE_MAP.put("��������� Hardening line 1", ProdLine.DAMPER_PR_HARDENING1);
		ALIAS_LINE_MAP.put("��������� Hardening line 2", ProdLine.DAMPER_PR_HARDENING2);
		ALIAS_LINE_MAP.put("��ǰ��ĥ������/Grinding Line 1", ProdLine.DAMPER_PR_GRINDING_PRECR1);
		ALIAS_LINE_MAP.put("��ǰ��ĥ������/Grinding Line 2", ProdLine.DAMPER_PR_GRINDING_PRECR2);
		ALIAS_LINE_MAP.put("��������� Cr-Plating line", ProdLine.DAMPER_PR_CR_PLATING);
		ALIAS_LINE_MAP.put("�ƺ󳬾������� Superfinished after Cr-Plating 1", ProdLine.DAMPER_PR_SUPERFINISH1);
		ALIAS_LINE_MAP.put("�ƺ󳬾������� Superfinished after Cr-Plating 2", ProdLine.DAMPER_PR_SUPERFINISH2);
		ALIAS_LINE_MAP.put("���ӹ�CNC 0007������", ProdLine.DAMPER_PR_CNC0007);
		ALIAS_LINE_MAP.put("���ӹ�CNC 0008������", ProdLine.DAMPER_PR_CNC0008);
		ALIAS_LINE_MAP.put("���ӹ�CNC 0009������", ProdLine.DAMPER_PR_CNC0009);
		ALIAS_LINE_MAP.put("���ӹ�CNC 0806������", ProdLine.DAMPER_PR_CNC0806);
		ALIAS_LINE_MAP.put("���ӹ�CNC 0807������", ProdLine.DAMPER_PR_CNC0807);
		ALIAS_LINE_MAP.put("���ӹ�CNC 0808������", ProdLine.DAMPER_PR_CNC0808);
		ALIAS_LINE_MAP.put("���ӹ�CNC 0809������", ProdLine.DAMPER_PR_CNC0809);
		//**************RTA�����߱���->������ö�� ӳ��ͼ��ʼ��*************
		ALIAS_LINE_MAP.put("�ȳ��ͺ���������HBF0031", ProdLine.DAMPER_RTA_HBF0031);
		ALIAS_LINE_MAP.put("�ȳ��ͺ���������HBF��0031��", ProdLine.DAMPER_RTA_HBF0031);
		ALIAS_LINE_MAP.put("�ȳ��ͺ���������HBF0835", ProdLine.DAMPER_RTA_HBF0835);
		ALIAS_LINE_MAP.put("���&����������Neck Down", ProdLine.DAMPER_RTA_NECKDOWN);
		ALIAS_LINE_MAP.put("���ں���ϴ After Neck Down Washing", ProdLine.DAMPER_RTA_WASH_POSTNK);
		ALIAS_LINE_MAP.put("ǰ����������Front Welding Cell", ProdLine.DAMPER_RTA_FRONTWELDCELL);
		ALIAS_LINE_MAP.put("�����˺�������Front Welding Cell", ProdLine.DAMPER_RTA_FRONTWELDCELL);
		ALIAS_LINE_MAP.put("������������Front Welding Cell", ProdLine.DAMPER_RTA_FRONTWELDCELL);
		ALIAS_LINE_MAP.put("�󺸽�������Rear Welding Cell", ProdLine.DAMPER_RTA_REARWELDCELL);
		ALIAS_LINE_MAP.put("������ϴ Chamfering&Washing", ProdLine.DAMPER_RTA_CHAMFER_WASH);
		ALIAS_LINE_MAP.put("��©��ϴLeak test&Washing", ProdLine.DAMPER_RTA_LEAKTEST_WASH);
		ALIAS_LINE_MAP.put("�����̣�֧�ܣ�����������S/Seat(Bracket) Welding Cell", ProdLine.DAMPER_RTA_SEATBRACKET_WELD);
		ALIAS_LINE_MAP.put("���������Punching Cell", ProdLine.DAMPER_RTA_PUNCHING);
		ALIAS_LINE_MAP.put("��Ӿ������KTL", ProdLine.DAMPER_RTA_KTL);
		ALIAS_LINE_MAP.put("����ѹװ������Punching Press Cell", ProdLine.DAMPER_RTA_BUSHINGPRESS);
	}
	
	/**
	 * ͨ��������ȡ������ö��
	 * @param alias ����
	 * @return ������ö��/ProdLine.INVALID
	 */
	public static ProdLine getStdLineByAlias(String alias) {
		if(ALIAS_LINE_MAP.containsKey(alias)) return ALIAS_LINE_MAP.get(alias);
		else return ProdLine.INVALID;
	}
	
	/**
	 * ͨ��������ö�ٻ�ȡ�����߱�׼����
	 * @param alias ����
	 * @return ����������/null
	 */
	public static String getStdNameByLineEnum(ProdLine line) {
		if(STDNAME_MAP.containsKey(line)) return STDNAME_MAP.get(line);
		else return null;
	}
	
	/**
	 * ͨ��������ö�ٻ�ȡ��������ö��
	 * @param stdline ��׼������
	 * @return ��������/ProdLine.INVALID
	 */
	public static ProdLine getProdZoneByProdLine(ProdLine stdline) {
		if(LINE_ZONE_MAP.containsKey(stdline)) return LINE_ZONE_MAP.get(stdline);
		else return ProdLine.INVALID;
	}
	
}
