package com.logsys.bom;

/**
 * BOM�ڵ���
 * @author lx8sn6
 */
public class BOMNode {

	/**�ýڵ��������BOM����*/
	private BOMContent bcont;
	
	/**����BOM�㼶*/
	private int level;
	
	/**ǰһ�ڵ�*/
	private BOMNode prevnode;
	
	/**��һ�ڵ�*/
	private BOMNode nextnode;
	
	/**�ӽڵ�*/
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
