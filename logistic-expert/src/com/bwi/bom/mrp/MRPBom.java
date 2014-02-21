package com.bwi.bom.mrp;

import java.util.Collection;
import java.util.Map;

import com.logsys.bom.interfaces.IBOMBean;
import com.logsys.bom.interfaces.IBOMCellBean;

/**
 * 专用于MRP的BomBean
 * @author lx8sn6
 */
public class MRPBom implements IBOMBean {

	private Map<String,MRPBomCell> map;
	
	@Override
	public boolean insertCell(IBOMCellBean cellbean) {
		
		return false;
	}

	@Override
	public boolean removeCell(IBOMCellBean cellbean) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<IBOMCellBean> getCellBean(Object model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Object> getPNList(Object model) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
