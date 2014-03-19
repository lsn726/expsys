package com.logsys.bom;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * BOM的节点对象,包含节点对应的pn,以及制作这个pn所对应的子列表
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
	 * 加入子pn内容
	 * @param content 子pn对象
	 * @return 成功true/失败false
	 */
	public boolean addSubPn(BOMContent content) {
		if(content==null) {
			logger.error("子物料节点不能写入，内容为null对象");
			return false;
		}
		return subpnlist.add(content);
	}
	
}
