package com.logsys.bom.interfaces;

import java.util.Collection;
import java.util.Set;

/**
 * BOM的Bean对象接口
 * @author lx8sn6
 */
public interface IBOMBean {
	
	/**写入新的BomCell节点*/
	public boolean insertCell(IBOMCellBean cellbean);
	
	/**移除新的BomCell节点*/
	public boolean removeCell(IBOMCellBean cellbean);
	
	/**根据model获取BomCell信息列表*/
	public Collection<IBOMCellBean> getCellBean(Object model);
	
	/**获取制造成品所需材料列表*/
	public Set getPNSet(Object model);

}