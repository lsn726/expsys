package com.logsys.bom;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * BOM�Ľڵ����,�����ڵ��Ӧ��pn,�������pn����Ӧ�����б�,�Լ����BOMNode�ĸ��ڵ�
 * @author lx8sn6
 */
public class BOMNode {
	
	private static final Logger logger=Logger.getLogger(BOMNode.class);

	/**PN����*/
	private String pn;
	
	/**BOM������Ϣ*/
	private BOMContent bcontent;
	
	/**���BOM�ڵ���ӽڵ��б�*/
	private List<BOMNode> subnodelist;
	
	/**���BOM�ڵ�ĸ��ڵ�*/
	private BOMNode fathernode;
	
	public BOMNode(BOMContent bcont) {
		this.pn=bcont.getPn();
		this.bcontent=bcont;
		this.subnodelist=new ArrayList<BOMNode>();
	}
	
	/**
	 * ��һ��BOMNode���뵱ǰ������ӽڵ㣬����subnode�ĸ��ڵ�����Ϊthis
	 * @param content ��pn����
	 * @return �ɹ�true/ʧ��false
	 */
	public boolean addSubNode(BOMNode subnode) {
		if(subnode==null) {
			logger.error("�����Ͻڵ㲻��д�룬�ڵ�Ϊnull����");
			return false;
		}
		subnode.setFatherNode(this);
		return subnodelist.add(subnode);
	}
	
	/**
	 * ��ȡ��Node�б�
	 * @return ��Node�б�
	 */
	public List<BOMNode> getSubPnList() {
		return this.subnodelist;
	}
	
	/**
	 * ��ȡ��Node�ڵ����
	 * @return ��Node�ڵ�
	 */
	public BOMNode getFatherNode() {
		return this.fathernode;
	}
	
	/**
	 * ���ø��ڵ�
	 * @param fathernode ��Ҫ���õĸ��ڵ����
	 */
	public void setFatherNode(BOMNode fathernode) {
		this.fathernode=fathernode;
	}
	
	/**
	 * ��ȡ���BOM�ڵ��ڵ�BOM���ݡ�
	 * @return BOM�ڵ�����
	 */
	public BOMContent getBomContent() {
		return this.bcontent;
	}
	
}