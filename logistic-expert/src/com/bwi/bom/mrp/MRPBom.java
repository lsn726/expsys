package com.bwi.bom.mrp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.logsys.bom.interfaces.IBOMBean;
import com.logsys.bom.interfaces.IBOMContentBean;

/**
 * 专用于MRP的BomBean,在加入BOMCell时,重复Cell的Qty将叠加
 * @author lx8sn6
 */
public class MRPBom implements IBOMBean {

	private Logger logger=Logger.getLogger(MRPBom.class);
	
	private Map<String,MRPBomCell> container=new HashMap<String,MRPBomCell>();
	
	public MRPBom() {
		container=new HashMap<String,MRPBomCell>();
	}
	
	@Override
	public boolean insertCell(IBOMContentBean cellbean) {
		if(cellbean==null) {
			logger.error("参数为空");
			return false;
		}
		try {
			MRPBomCell bomcell=(MRPBomCell)cellbean;
			if(!container.containsKey(bomcell.getPn()))
				container.put(bomcell.getPn(), bomcell);
			else {
				logger.info("BOM中已存在物料，原有消耗数量将叠加");
				MRPBomCell temp=container.get(bomcell.getPn());
				temp.setQty(temp.getQty()+bomcell.getQty());
			}
			return true;
		} catch (Throwable ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return false;
	}

	@Override
	public boolean removeCell(IBOMContentBean cellbean) {
		if(cellbean==null) {
			logger.error("参数为空");
			return false;
		}
		try {
			MRPBomCell bomcell=(MRPBomCell)cellbean;
			if(container.containsValue(bomcell)) {
				container.remove(bomcell);
				return true;
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return false;
	}

	@Override
	public List<IBOMContentBean> getCellBean(Object model) {
		if(container.containsKey(model)) {
			List<IBOMContentBean> list=new ArrayList<IBOMContentBean>();
			list.add(container.get(model));
			return list;
		}
		return null;
	}

	@Override
	public Set getPNSet(Object model) {
		return container.keySet();
	}
	
}
