package com.bwi.bom.mrp;

import com.logsys.bom.interfaces.IBOMCellBean;

/**
 * ����MRP��BomCell��Ԫ
 * @author lx8sn6
 */
public class MRPBomCell implements IBOMCellBean {

	/**Part Number*/
	private String pn;
	
	/**��Ҫ����*/
	private double qty;
	
	/**������λ*/
	private String uom;
	
	/***/
	private String makebuy;
	
}
