package com.bwi.bom.mrp;

import com.logsys.bom.interfaces.IBOMCellBean;

/**
 * 用于MRP的BomCellBean,因此只需要有限度的信息
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

	public MRPBomCell() {}
	
	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getMakebuy() {
		return makebuy;
	}

	public void setMakebuy(String makebuy) {
		this.makebuy = makebuy;
	}
	
	
	
}
