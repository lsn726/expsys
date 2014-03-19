package com.logsys.bom;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * BOM�Ľڵ����,�����ڵ��Ӧ��pn,�Լ��������pn����Ӧ�����б�
 * @author lx8sn6
 */
public class BOMNode {
	
	private static final Logger logger=Logger.getLogger(BOMNode.class);

	private String pn;
	
	private List<BOMContent> subpnlist;
	
	public BOMNode(String pn) {
		this.pn=pn;
		subpnlist=new ArrayList<BOMContent>();
	}
	
	/**
	 * ������pn����
	 * @param content ��pn����
	 * @return �ɹ�true/ʧ��false
	 */
	public boolean addSubPn(BOMContent content) {
		if(content==null) {
			logger.error("�����Ͻڵ㲻��д�룬����Ϊnull����");
			return false;
		}
		return subpnlist.add(content);
	}
	
}
