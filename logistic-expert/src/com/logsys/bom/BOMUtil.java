package com.logsys.bom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.logsys.material.MaterialContent;
import com.logsys.material.MaterialDataReaderDB;

/**
 * BOM工具类
 * @author lx8sn6
 */
public class BOMUtil {

	private static Logger logger=Logger.getLogger(BOMUtil.class);

	/**物料在BOM节点中的状态*/
	public static enum BOM_PN_STATUS {
		/**顶级节点*/
		TOPNODE,
		/**子节点*/
		SUBNODE,
		/**即存在顶级节点,又存在子节点*/
		MIXNODE,
		/**不存在的节点*/
		NOTEXIST,
		/**出现错误*/
		ERROR
	};
	
	/**
	 * 将BOMList转变为基于BOMNode的结构化数据
	 * @param bomlist 平行话BOM列表
	 * @return BOMNode结构化单元
	 */
	public static BOMNode bomListToBomNodeStructure(List<BOMContent> bomlist) {
		if(bomlist==null) {
			logger.error("不能结构化BOM列表,参数为空.");
			return null;
		}
		if(bomlist.size()==0) {
			logger.error("不能结构化BOM列表,列表的Size为0.");
			return null;
		}
		if(bomlist.get(0).getLvl()!=0) {
			logger.error("BOM列表错误,CS12导出后顶层需手动添加lvl为0的总成件pn.");
			return null;
		}
		BOMNode topnode=new BOMNode(bomlist.get(0));		//顶层节点
		topnode.setFatherNode(null);						//设置顶层节点的父节点为null
		BOMNode fathernode=topnode;							//父节点，初始为顶层节点
		BOMNode prevnode=topnode;							//前一个处理的节点
		BOMNode itnode;										//用于遍历的节点
		BOMContent bnow;									//现在正在处理的BOM信息
		int lvldiff;										//等级差异
		int index;
		for(index=1;index<bomlist.size();index++) {			//开始从头索引节点
			bnow=bomlist.get(index);
			itnode=new BOMNode(bnow);
			if(bnow.getLvl()==0) {
				logger.error("错误，出现多于一个0级别顶级节点.");
				return null;
			}
			lvldiff=prevnode.getBomContent().getLvl()-bnow.getLvl();	//获取等级差异
			if(lvldiff==0) {	//如果与上一处理节点是同级别的Node,则添加到同一个fathernode下
				fathernode.addSubNode(itnode);		//添加子节点,同时设置父节点
				prevnode=itnode;					//将当前节点设置为前一节点
				continue;
			}
			if(lvldiff<0) {		//如果当前物料等级大于上一处理节点，则说明新的零件是前一零件的子零件
				if(bnow.getLvl()-prevnode.getBomContent().getLvl()>1) {	//一次只能增加一个等级
					logger.error("错误,子零件级别相较于父级别增加大于1.");
					return null;
				} else {
					prevnode.addSubNode(itnode);	//将当前节点设置为前一节点的子节点，同时设置父节点为前一节点
					fathernode=prevnode;			//父节点设置为前一节点
					prevnode=itnode;				//前一节点设置为当前节点
					continue;
				}
			}
			if(lvldiff>0) {		//如果当前节点等级小于前一节点等级，则说前一节点组装件结束
				fathernode=prevnode.getFatherNode();		//先找到上一个父节点
				for(int i=lvldiff;i>0;i--) 				//循环遍历找到上一个父节点
					fathernode=fathernode.getFatherNode();
				fathernode.addSubNode(itnode);				//为父节点加入子节点
				prevnode=itnode;							//前一个节点设置为当前节点
				continue;
			}
		}
		return topnode;
	}
	
	/**
	 * 判断Part Number在数据库BOM表中的状态
	 * @param pn 需要查看的Part Number名称
	 * @return 是合理的顶级节点true/不是合理的顶级节点false
	 */
	public static BOM_PN_STATUS getPNStatusInBOMDB(String pn) {
		if(pn==null) {
			logger.error("不能判断是否是合理的顶级节点.Part Number为空.");
			return BOM_PN_STATUS.ERROR;
		}
		List<BOMContent> bomlist=BOMDataReaderDB.getBOMListByPN(pn);
		if(bomlist==null) return BOM_PN_STATUS.ERROR;
		if(bomlist.size()==0) return BOM_PN_STATUS.NOTEXIST;
		boolean isTopNode=false;
		boolean isSubNode=false;
		for(BOMContent cont:bomlist) {
			if(cont.getLvl()==0) isTopNode=true;
			if(cont.getLvl()!=0) isSubNode=true;
		}
		if(isTopNode&&isSubNode) return BOM_PN_STATUS.MIXNODE;
		if(isTopNode&&!isSubNode) return BOM_PN_STATUS.TOPNODE;
		if(!isTopNode&&isSubNode) return BOM_PN_STATUS.SUBNODE;
		if(!isTopNode&&!isSubNode) return BOM_PN_STATUS.ERROR;
		return BOM_PN_STATUS.ERROR;
	}
	
	/**
	 * 获取pnset物料集中物料的原材料BOM集,如果有相同的BOM物料消耗则相加
	 * @param modelset 要获取原材料BOM集的模型列表
	 * @return Map<pnset中的pn,Map<原材料pn,消耗数量>>对象
	 */
	public static Map<String,Map<String,Double>> getRowBomMatrix(Set<String> modelset) {
		if(modelset==null) {
			logger.error("不能获取物料矩阵，物料集为空。");
			return null;
		}
		if(modelset.size()==0) return new HashMap<String,Map<String,Double>>();
		Map<String,Map<String,Double>> bomRowMap=new HashMap<String,Map<String,Double>>();
		List<BOMContent> bomlist;							//pn的完整bom列表
		Set<String> matset;									//每个pn下的物料集
		List<MaterialContent> matlist;						//筛选过后的物料列表
		Map<String,Double> bomcons;							//物料的bom消耗图
		for(String pn:modelset) {			//循环获取BOM
			bomlist=BOMDataReaderDB.getComplBOMByPN(pn);	//先获取pn的完整BOM
			if(bomlist==null) {								//如果有物料无法获取完整BOM则跳过这个物料号
				logger.warn("有物料pn获取完整BOM出现错误,跳过这个物料:"+pn);
				continue;
			}
			matset=new HashSet<String>();
			for(BOMContent bcont:bomlist)					//将组装料转化为物料集
				matset.add(bcont.getPn());
			matlist=MaterialDataReaderDB.getDataFromDB(matset, "buy", true, false);	//筛选出buy的物料列表
			bomcons=new HashMap<String,Double>();
			for(MaterialContent mcont:matlist) 				//遍历所选列表，初始化bom消耗图
				bomcons.put(mcont.getPn(), 0.0);
			String asmpn;									//组装件Pn
			for(BOMContent bcont:bomlist)	{				//二次遍历BOM,写入消耗数量
				asmpn=bcont.getPn();						//获取组装件pn
				if(bomcons.containsKey(asmpn))				//如果存在于bom消耗图中，则说明是buy的物料
					bomcons.put(asmpn, bomcons.get(asmpn)+bcont.getQty());	//在原基础上加上消耗数量
			}
			bomRowMap.put(pn, bomcons);						//将子物料bom写入总图
		}
		return bomRowMap;
	}
	
}
