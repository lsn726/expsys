package com.logsys.bom;

public class BOMContent {

	/**id*/
	private int id;
	
	/**物料PN*/
	private String pn;
	
	/**消耗数量*/
	private double qty;

	public BOMContent() {
		super();
	}

	public BOMContent(int id, String pn, double qty) {
		super();
		this.id = id;
		this.pn = pn;
		this.qty = qty;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
}
