package com.logsys.setting.pd.bwi;

import java.util.HashMap;
import java.util.Map;

/**
 * BWI生产线信息类
 * @author ShaonanLi
 */
public class BWIPLInfo {
	
	/**
	 * 生产线枚举
	 * @author lx8sn6
	 */
	public static enum ProdLine {
		/**无效生产线*/
		INVALID,
		/**减震器--区域--FA*/
		DAMPER_ZONE_FA,
		/**减震器--区域--RTA*/
		DAMPER_ZONE_RTA,
		/**减震器--区域--PR*/
		DAMPER_ZONE_PR,
		/**减震器--FA--FA1*/
		DAMPER_FA_FA1,
		/**减震器--FA--FA2*/
		DAMPER_FA_FA2,
		/**减震器--FA--Module*/
		DAMPER_FA_MODULE,
		/**减震器--FA--UV*/
		DAMPER_FA_UV,
		/**减震器--FA--Rebound Stop BF20/21/22*/
		DAMPER_FA_REBOUND_STOP_202122,
		/**减震器--FA--Rebound Stop BF23*/
		DAMPER_FA_REBOUND_STOP_23,
		/**减震器--RTA--Hot Bottom Forming 0031*/
		DAMPER_RTA_HBF0031,
		/**减震器--RTA--Hot Bottom Forming 0835*/
		DAMPER_RTA_HBF0835,
		/**减震器--RTA--Neck Down*/
		DAMPER_RTA_NECKDOWN,
		/**减震器--RTA--Washing After Neck Down*/
		DAMPER_RTA_WASH_POSTNK,
		/**减震器--RTA--Front Welding Cell*/
		DAMPER_RTA_FRONTWELDCELL,
		/**减震器--RTA--Rear Welding Cell*/
		DAMPER_RTA_REARWELDCELL,
		/**减震器--RTA--Chamfering and Washing*/
		DAMPER_RTA_CHAMFER_WASH,
		/**减震器--RTA--Leak Test and Washing*/
		DAMPER_RTA_LEAKTEST_WASH,
		/**减震器--RTA--Spring Seat and Bracket Welding*/
		DAMPER_RTA_SEATBRACKET_WELD,
		/**减震器--RTA--Punching Cell*/
		DAMPER_RTA_PUNCHING,
		/**减震器--RTA--KTL*/
		DAMPER_RTA_KTL,
		/**减震器--RTA--Bushing Press Cell*/
		DAMPER_RTA_BUSHINGPRESS,
		/**减震器--PR--Coarse Grinding*/
		DAMPER_PR_COARSE_GRIND,
		/**减震器--PR--Frication Welding 1*/
		DAMPER_PR_FRICATION_WELD1,
		/**减震器--PR--Frication Welding 2*/
		DAMPER_PR_FRICATION_WELD2,
		/**减震器--PR--Hardening 1*/
		DAMPER_PR_HARDENING1,
		/**减震器--PR--Hardening 2*/
		DAMPER_PR_HARDENING2,
		/**减震器--PR--CNC 0007*/
		DAMPER_PR_CNC0007,
		/**减震器--PR--CNC 0008*/
		DAMPER_PR_CNC0008,
		/**减震器--PR--CNC 0009*/
		DAMPER_PR_CNC0009,
		/**减震器--PR--CNC 0806*/
		DAMPER_PR_CNC0806,
		/**减震器--PR--CNC 0807*/
		DAMPER_PR_CNC0807,
		/**减震器--PR--CNC 0808*/
		DAMPER_PR_CNC0808,
		/**减震器--PR--CNC 0809*/
		DAMPER_PR_CNC0809,
		/**减震器--PR--Grinding Before Cr-Plating 1*/
		DAMPER_PR_GRINDING_PRECR1,
		/**减震器--PR--Grinding Before Cr-Plating 2*/
		DAMPER_PR_GRINDING_PRECR2,
		/**减震器--PR--Cr Plating Line*/
		DAMPER_PR_CR_PLATING,
		/**减震器--PR--SuperFinish after Cr-Plating 1*/
		DAMPER_PR_SUPERFINISH1,
		/**减震器--PR--SuperFinish after Cr-Plating 2*/
		DAMPER_PR_SUPERFINISH2,
	}
	
	/**生产线枚举->标准名称 映射图*/
	private static Map<ProdLine,String> STDNAME_MAP=new HashMap<ProdLine,String>();
	
	/**生产线枚举->生产线区域枚举 映射图*/
	private static Map<ProdLine,ProdLine> LINE_ZONE_MAP=new HashMap<ProdLine,ProdLine>();
	
	/**别名->生产线枚举 映射图*/
	private static Map<String,ProdLine> ALIAS_LINE_MAP=new HashMap<String,ProdLine>();
	
	static {
		//**************减震器生产区域枚举->标准名称初始化***************
		STDNAME_MAP.put(ProdLine.DAMPER_ZONE_FA, "DamperFA");
		STDNAME_MAP.put(ProdLine.DAMPER_ZONE_RTA, "DamperRTA");
		STDNAME_MAP.put(ProdLine.DAMPER_ZONE_PR, "DamperPR");
		
		//**************FA区域枚举->标准名称初始化***************
		STDNAME_MAP.put(ProdLine.DAMPER_FA_FA1, "DamperFA FA 1");
		STDNAME_MAP.put(ProdLine.DAMPER_FA_FA2, "DamperFA FA 2");
		STDNAME_MAP.put(ProdLine.DAMPER_FA_MODULE, "DamperFA Module");
		STDNAME_MAP.put(ProdLine.DAMPER_FA_UV, "DamperFA UV");
		STDNAME_MAP.put(ProdLine.DAMPER_FA_REBOUND_STOP_202122, "DamperFA RebStop 202122");
		STDNAME_MAP.put(ProdLine.DAMPER_FA_REBOUND_STOP_23, "DamperFA RebStop 23");
		//**************RTA区域枚举->标准名称初始化***************
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
		//**************PR区域枚举->标准名称初始化***************
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
		
		//**************FA生产线->生产区域初始化*************
		LINE_ZONE_MAP.put(ProdLine.DAMPER_FA_FA1, ProdLine.DAMPER_ZONE_FA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_FA_FA2, ProdLine.DAMPER_ZONE_FA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_FA_MODULE, ProdLine.DAMPER_ZONE_FA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_FA_UV, ProdLine.DAMPER_ZONE_FA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_FA_REBOUND_STOP_202122, ProdLine.DAMPER_ZONE_FA);
		LINE_ZONE_MAP.put(ProdLine.DAMPER_FA_REBOUND_STOP_23, ProdLine.DAMPER_ZONE_FA);
		//**************RTA生产线->生产区域初始化*************
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
		//**************PR生产线->生产区域初始化***************
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
		
		//**************FA生产线别名->生产线枚举 映射图初始化*************
		ALIAS_LINE_MAP.put("组装生产线/Assy-1", ProdLine.DAMPER_FA_FA1);
		ALIAS_LINE_MAP.put("组装生产线/Assy-2", ProdLine.DAMPER_FA_FA2);
		ALIAS_LINE_MAP.put("组装生产线-module", ProdLine.DAMPER_FA_MODULE);
		ALIAS_LINE_MAP.put("UV生产线/UV", ProdLine.DAMPER_FA_UV);
		ALIAS_LINE_MAP.put("止动环生产线/Rebound Stop BF-0020-21-22", ProdLine.DAMPER_FA_REBOUND_STOP_202122);
		ALIAS_LINE_MAP.put("止动环生产线/Rebound StopBF-0023", ProdLine.DAMPER_FA_REBOUND_STOP_23);
		//**************PR生产线别名->生产线枚举 映射图初始化*************
		ALIAS_LINE_MAP.put("粗磨生产线/Coarse grinding", ProdLine.DAMPER_PR_COARSE_GRIND);
		ALIAS_LINE_MAP.put("摩擦焊生产线 Frication Welding Line 1", ProdLine.DAMPER_PR_FRICATION_WELD1);
		ALIAS_LINE_MAP.put("摩擦焊生产线 Frication Welding Line 2", ProdLine.DAMPER_PR_FRICATION_WELD2);
		ALIAS_LINE_MAP.put("淬火生产线 Hardening line 1", ProdLine.DAMPER_PR_HARDENING1);
		ALIAS_LINE_MAP.put("淬火生产线 Hardening line 2", ProdLine.DAMPER_PR_HARDENING2);
		ALIAS_LINE_MAP.put("镀前研磨生产线/Grinding Line 1", ProdLine.DAMPER_PR_GRINDING_PRECR1);
		ALIAS_LINE_MAP.put("镀前研磨生产线/Grinding Line 2", ProdLine.DAMPER_PR_GRINDING_PRECR2);
		ALIAS_LINE_MAP.put("电镀生产线 Cr-Plating line", ProdLine.DAMPER_PR_CR_PLATING);
		ALIAS_LINE_MAP.put("镀后超精生产线 Superfinished after Cr-Plating 1", ProdLine.DAMPER_PR_SUPERFINISH1);
		ALIAS_LINE_MAP.put("镀后超精生产线 Superfinished after Cr-Plating 2", ProdLine.DAMPER_PR_SUPERFINISH2);
		ALIAS_LINE_MAP.put("机加工CNC 0007生产线", ProdLine.DAMPER_PR_CNC0007);
		ALIAS_LINE_MAP.put("机加工CNC 0008生产线", ProdLine.DAMPER_PR_CNC0008);
		ALIAS_LINE_MAP.put("机加工CNC 0009生产线", ProdLine.DAMPER_PR_CNC0009);
		ALIAS_LINE_MAP.put("机加工CNC 0806生产线", ProdLine.DAMPER_PR_CNC0806);
		ALIAS_LINE_MAP.put("机加工CNC 0807生产线", ProdLine.DAMPER_PR_CNC0807);
		ALIAS_LINE_MAP.put("机加工CNC 0808生产线", ProdLine.DAMPER_PR_CNC0808);
		ALIAS_LINE_MAP.put("机加工CNC 0809生产线", ProdLine.DAMPER_PR_CNC0809);
		//**************RTA生产线别名->生产线枚举 映射图初始化*************
		ALIAS_LINE_MAP.put("热成型焊接生产线HBF0031", ProdLine.DAMPER_RTA_HBF0031);
		ALIAS_LINE_MAP.put("热成型焊接生产线HBF（0031）", ProdLine.DAMPER_RTA_HBF0031);
		ALIAS_LINE_MAP.put("热成型焊接生产线HBF0835", ProdLine.DAMPER_RTA_HBF0835);
		ALIAS_LINE_MAP.put("起鼓&缩口生产线Neck Down", ProdLine.DAMPER_RTA_NECKDOWN);
		ALIAS_LINE_MAP.put("缩口后清洗 After Neck Down Washing", ProdLine.DAMPER_RTA_WASH_POSTNK);
		ALIAS_LINE_MAP.put("前焊接生产线Front Welding Cell", ProdLine.DAMPER_RTA_FRONTWELDCELL);
		ALIAS_LINE_MAP.put("机器人焊生产线Front Welding Cell", ProdLine.DAMPER_RTA_FRONTWELDCELL);
		ALIAS_LINE_MAP.put("机器人生产线Front Welding Cell", ProdLine.DAMPER_RTA_FRONTWELDCELL);
		ALIAS_LINE_MAP.put("后焊接生产线Rear Welding Cell", ProdLine.DAMPER_RTA_REARWELDCELL);
		ALIAS_LINE_MAP.put("倒角清洗 Chamfering&Washing", ProdLine.DAMPER_RTA_CHAMFER_WASH);
		ALIAS_LINE_MAP.put("测漏清洗Leak test&Washing", ProdLine.DAMPER_RTA_LEAKTEST_WASH);
		ALIAS_LINE_MAP.put("弹簧盘（支架）焊接生产线S/Seat(Bracket) Welding Cell", ProdLine.DAMPER_RTA_SEATBRACKET_WELD);
		ALIAS_LINE_MAP.put("打孔生产线Punching Cell", ProdLine.DAMPER_RTA_PUNCHING);
		ALIAS_LINE_MAP.put("电泳生产线KTL", ProdLine.DAMPER_RTA_KTL);
		ALIAS_LINE_MAP.put("衬套压装生产线Punching Press Cell", ProdLine.DAMPER_RTA_BUSHINGPRESS);
	}
	
	/**
	 * 通过别名获取生产线枚举
	 * @param alias 别名
	 * @return 生产线枚举/ProdLine.INVALID
	 */
	public static ProdLine getStdLineByAlias(String alias) {
		if(ALIAS_LINE_MAP.containsKey(alias)) return ALIAS_LINE_MAP.get(alias);
		else return ProdLine.INVALID;
	}
	
	/**
	 * 通过生产线枚举获取生产线标准名称
	 * @param alias 别名
	 * @return 生产线名称/null
	 */
	public static String getStdNameByLineEnum(ProdLine line) {
		if(STDNAME_MAP.containsKey(line)) return STDNAME_MAP.get(line);
		else return null;
	}
	
	/**
	 * 通过生产线枚举获取生产区域枚举
	 * @param stdline 标准生产线
	 * @return 生产区域/ProdLine.INVALID
	 */
	public static ProdLine getProdZoneByProdLine(ProdLine stdline) {
		if(LINE_ZONE_MAP.containsKey(stdline)) return LINE_ZONE_MAP.get(stdline);
		else return ProdLine.INVALID;
	}
	
}
