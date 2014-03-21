package com.logsys.bom;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * BOM的节点对象,包含节点对应的pn,制作这个pn所对应的子列表,以及这个BOMNode的父节点
 * @author lx8sn6
 */
public class BOMNode {
	
	private static final Logger logger=Logger.getLogger(BOMNode.class);

	/**PN号码*/
	private String pn;
	
	/**BOM内容信息*/
	private BOMContent bcontent;
	
	/**这个BOM节点的子节点列表*/
	private List<BOMNode> subnodelist;
	
	/**这个BOM节点的父节点*/
	private BOMNode fathernode;
	
	public BOMNode(BOMContent bcont) {
		this.pn=bcont.getPn();
		this.bcontent=bcont;
		this.subnodelist=new ArrayList<BOMNode>();
	}
	
	/**
	 * 将一个BOMNode加入当前零件的子节点，并把subnode的父节点设置为this
	 * @param content 子pn对象
	 * @return 成功true/失败false
	 */
	public boolean addSubNode(BOMNode subnode) {
		if(subnode==null) {
			logger.error("子物料节点不能写入，节点为null对象。");
			return false;
		}
		subnode.setFatherNode(this);
		return subnodelist.add(subnode);
	}
	
	/**
	 * 获取子Node列表
	 * @return 子Node列表
	 */
	public List<BOMNode> getSubPnList() {
		return this.subnodelist;
	}
	
	/**
	 * 获取父Node节点对象
	 * @return 父Node节点
	 */
	public BOMNode getFatherNode() {
		return this.fathernode;
	}
	
	/**
	 * 设置父节点
	 * @param fathernode 需要设置的父节点对象
	 */
	public void setFatherNode(BOMNode fathernode) {
		this.fathernode=fathernode;
	}
	
	/**
	 * 获取这个BOM节点内的BOM内容。
	 * @return BOM节点内容
	 */
	public BOMContent getBomContent() {
		return this.bcontent;
	}
	
}