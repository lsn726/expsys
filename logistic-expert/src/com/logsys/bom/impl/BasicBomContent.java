package com.logsys.bom.impl;

import com.logsys.bom.interfaces.IBOMContentBean;

/**
 * 基础BOM内容Bean，只提供最基本的信息：消耗零件，消耗数量，消耗计量单位
 * @author ShaonanLi
 */
public class BasicBomContent implements IBOMContentBean {

	/**消耗零件*/
	private String pn;
	
	/**需要数量*/
	private double qty;
	
	/**计量单位*/
	private String uom;
	
	public BasicBomContent() {
		super();
	}

	public BasicBomContent(String pn, double qty, String uom) {
		this.pn = pn;
		this.qty = qty;
		this.uom = uom;
	}

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
	
}
