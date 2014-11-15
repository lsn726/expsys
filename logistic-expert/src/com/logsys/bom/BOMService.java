package com.logsys.bom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.logsys.util.DateTimeUtils;

/**
 * BOM服务对象
 * @author lx8sn6
 */
public class BOMService {

	private static Logger logger=Logger.getLogger(BOMService.class);
	
	/**单层BOM*/
	public static int BOM_LEVEL_SINGLE=100;
	
	/**多层BOM*/
	public static int BOM_LEVEL_MULTI=101;
	
	/**BOM版本->[Map:Asmpn->BOMContent列表],包含所有的BOM数据*/
	private static Map<Calendar,Map<String,List<BOMContent>>> bommap;
	
	/**静态初始化块*/
	static {
		initBomSerivce();
	}
	
	private BOMService() {};

	/**
	 * 初始化Bom服务类
	 */
	private static void initBomSerivce() {
		List<BOMContent> oribomlist=new ArrayList<BOMContent>();
		oribomlist=BOMDBReader.getBOMList(null);		//从数据库读取BOM的Node列表
		if(oribomlist==null) {
			logger.fatal("初始化BOM服务模块出现错误。");
			return;
		}
		bommap=new HashMap<Calendar,Map<String,List<BOMContent>>>();
		Calendar cal;
		String asmpn;
		Map<String,List<BOMContent>> submap;
		for(BOMContent bcont: oribomlist) {				//遍历整理BOM服务列表,将之拆分为bommap对象
			cal=bcont.getVersion();
			if(!bommap.containsKey(cal))				//如果没有对应版本的对象，则需先增加对应的HashMap容器
				bommap.put((Calendar)cal.clone(), new HashMap<String,List<BOMContent>>());
			submap=bommap.get(cal);
			asmpn=bcont.getAsmpn();
			if(!submap.containsKey(asmpn))				//如果没有对应的组成件列表，则需要先增加列表对象
				submap.put(new String(asmpn), new ArrayList<BOMContent>());
			submap.get(asmpn).add(bcont);
		}
		/** Debug Printer
		for(Calendar cald:bommap.keySet()) {
			submap=bommap.get(cald);
			for(String str:submap.keySet())
				System.out.println("["+cald.getTime()+"]->"+"["+str+"]::"+submap.get(str).size());
		}*/
	}
	
	/**
	 * 通过组成件的PN获取BOMNode对象，其中包含 其完整的BOM结构
	 * @param version BOM版本，null则默认为最新版本
	 * @param asmpn 组成件的PartNumber
	 * @param bomtype BOM_LEVEL_SINGLE 单层BOM(只包含ASMPN下面的一层的BOM)/BOM_LEVEL_MULTI 多层级BOM
	 * @return 该总成件下BOM结构的BOMNode对象，如果是底层结构/物料号不存在，则返回null.
	 */
	public static BOMNode getBomByAsmPn(Calendar version, String asmpn, int bomtype) {
		if(version==null) {			//如果版本为空，则挑选最新日期。
			version=DateTimeUtils.getMinCalendar();
			for(Calendar cal:bommap.keySet())
				if(cal.after(version)) version=cal;
		}
		return getBomByAsmPnRecursion(version,asmpn,bomtype,0);		//作为第0级别开始遍历
	}
	
	/**
	 * getBomByAsmPn的递归调用函数
	 * @param version 版本,不能为null
	 * @param asmpn 组成件的PartNumber
	 * @param bomtype BOM_LEVEL_SINGLE 单层BOM(只包含ASMPN下面的一层的BOM)/BOM_LEVEL_MULTI 多层级BOM
	 * @param bomlevel 该层BOM的等级，用于写入BOM级别
	 * @return 该总成件下BOM结构的BOMNode对象，如果是底层结构/物料号不存在，则返回null.
	 */
	private static BOMNode getBomByAsmPnRecursion(Calendar version, String asmpn, int bomtype, int bomlevel) {
		if(version==null) {
			logger.error("递归提取BOM结构出现错误，BOM版本为空。");
			return null;
		}
		Map<String,List<BOMContent>> asmbommap=bommap.get(version);
		if(!asmbommap.containsKey(asmpn))		//如果不包含该BOM的子结构，则确认为底层的BOM结构
			return null;
		List<BOMContent> subnodelist=asmbommap.get(asmpn);
		BOMNode firstnode;						//第一节点
		BOMNode indexnode;						//遍历节点
		String subpn;
		for(BOMContent bcont:subnodelist) {		//遍历子键列表，遍历创建BOMNode
			subpn=bcont.getSubpn();
			if(bomtype==BOM_LEVEL_SINGLE)		//如果为单级BOM，则直接返回BOM对象
				indexnode=new BOMNode(bcont,bomlevel);
			else if(bomtype==BOM_LEVEL_MULTI) {	//如果为多级BOM，则需要递归调用，确认子层级BOM
				indexnode=getBomByAsmPnRecursion(version,subpn,bomtype,bomlevel);
				
			}
		}
	}

}