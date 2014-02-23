package com.logsys.bom.interfaces;

import java.util.Collection;
import java.util.Set;

/**
 * BOM��Bean����ӿ�
 * @author lx8sn6
 */
public interface IBOMBean {
	
	/**д���µ�BomCell�ڵ�*/
	public boolean insertCell(IBOMContentBean cellbean);
	
	/**�Ƴ��µ�BomCell�ڵ�*/
	public boolean removeCell(IBOMContentBean cellbean);
	
	/**����model��ȡBomCell��Ϣ�б�*/
	public Collection<IBOMContentBean> getCellBean(Object model);
	
	/**��ȡ�����Ʒ��������б�*/
	public Set getPNSet(Object model);

}