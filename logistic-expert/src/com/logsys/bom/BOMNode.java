package com.logsys.bom;

/**
 * BOM节点类
 * @author lx8sn6
 */
public class BOMNode {

	/**该节点所代表的BOM内容*/
	private BOMContent bcont;
	
	/**所在BOM层级*/
	private int level;
	
	/**前一节点*/
	private BOMNode prevnode;
	
	/**后一节点*/
	private BOMNode nextnode;
	
	/**子节点*/
	private BOMNode subnode;
	
	public BOMNode(BOMContent bcont, int level) {
		this.bcont=bcont;
		this.level=level;
	}

	public BOMContent getBcont() {
		return bcont;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public BOMNode getPrevnode() {
		return prevnode;
	}

	public void setPrevnode(BOMNode prevnode) {
		this.prevnode = prevnode;
	}

	public BOMNode getNextnode() {
		return nextnode;
	}

	public void setNextnode(BOMNode nextnode) {
		this.nextnode = nextnode;
	}

	public BOMNode getSubnode() {
		return subnode;
	}

	public void setSubnode(BOMNode subnode) {
		this.subnode = subnode;
	}
	
}
