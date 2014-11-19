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
		return getBomByAsmPnRecursion(version,asmpn,bomtype,1);		//作为第0级别开始遍历
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
		if(subnodelist.size()==0) 					//如果没有子BOM,则返回null
			return null;
		BOMNode firstnode=null;						//第一节点
		BOMNode indexnode=null;						//遍历节点
		BOMNode lastnode=null;						//上次遍历的节点
		BOMNode subnode=null;						//子节点
		String subpn;
		for(BOMContent bcont:subnodelist) {		//遍历子键列表，遍历创建BOMNode
			subpn=bcont.getSubpn();
			if(bomtype==BOM_LEVEL_SINGLE)		//如果为单级BOM，则直接返回BOM对象
				indexnode=new BOMNode(bcont,bomlevel);
			else if(bomtype==BOM_LEVEL_MULTI) {	//如果为多级BOM，则需要递归调用，确认子层级BOM
				indexnode=new BOMNode(bcont,bomlevel);
				subnode=getBomByAsmPnRecursion(version,subpn,bomtype,bomlevel+1);
				if(subnode!=null)				//如果有下层结构，则设置为子结构
					indexnode.setSubnode(subnode);
			}
			if(firstnode==null)					//如果第一节点为空，则将遍历节点设置为第一节点
				firstnode=indexnode;
			else {								//如果不是第一节点，则将上一遍历节点的nextnode设置为本次遍历节点
				lastnode.setNextnode(indexnode);
				indexnode.setPrevnode(lastnode);
			}
			lastnode=indexnode;					//将本次节点设置为当前节点
		}
		return firstnode;
	}
	
	/**
	 * 获取本Node节点下的信息，包含所有统计节点和子节点。
	 * @return 节点信息字符串
	 */
	public static String getBomNodeInfo(BOMNode node) {
		if(node==null) return null;
		String nodestr="";
		int level=0;
		int index=0;
		while(node.getNextnode()!=null) {		//只要node还有下一个节点就要继续遍历
			level=node.getLevel();
			index=0;
			while(index<node.getLevel()) {		//先写入level信息
				nodestr+='-';
				index++;
			}
			nodestr+=level;
			nodestr+='[';
			nodestr+=("ASMPN="+node.getBcont().getAsmpn()+"\t");
			nodestr+=("SUBPN="+node.getBcont().getSubpn()+"\t");
			nodestr+=("Qty="+node.getBcont().getQty()+"\t");
			nodestr+=("Uom="+node.getBcont().getUom()+"\t");
			nodestr+="\n";
			if(node.getSubnode()!=null)
				nodestr+=getBomNodeInfo(node.getSubnode());		//如果有子node，则遍递归遍历子node
			node=node.getNextnode();
		}
		return nodestr;
	}

	
}