package com.bwi.bom.mrp;

import com.logsys.bom.interfaces.IBOMCellBean;

/**
 * 用于MRP的BomCell单元
 * @author lx8sn6
 */
public class MRPBomCell implements IBOMCellBean {

	/**Part Number*/
	private String pn;
	
	/**需要数量*/
	private double qty;
	
	/**计量单位*/
	private String uom;
	
	/***/
	private String makebuy;
	
}
