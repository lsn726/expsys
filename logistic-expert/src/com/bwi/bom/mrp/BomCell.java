package com.bwi.bom.mrp;

import com.logsys.bom.interfaces.IBOMCellBean;

/**
 * 
 * @author lx8sn6
 */
public class BomCell implements IBOMCellBean {

	/**Part Number*/
	private String pn;
	
	/**需要数量*/
	private double qty;
	
	/**计量单位*/
	private String uom;
	
	/***/
	private String makebuy;
	
}
