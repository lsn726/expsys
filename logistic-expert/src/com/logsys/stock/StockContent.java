package com.logsys.stock;

/**
 * 库存信息类--对接库存数据库表
 * @author lx8sn6
 */
public class StockContent {

	private int id;
	
	private String pn;
	
	private String plant;
	
	private String sloc;
	
	private String wkcenter;
	
	private double qtyuns;
	
	private double uom;

	private double qtyqi;
	
	private double qtyblk;

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

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getSloc() {
		return sloc;
	}

	public void setSloc(String sloc) {
		this.sloc = sloc;
	}

	public String getWkcenter() {
		return wkcenter;
	}

	public void setWkcenter(String wkcenter) {
		this.wkcenter = wkcenter;
	}

	public double getQtyuns() {
		return qtyuns;
	}

	public void setQtyuns(double qtyuns) {
		this.qtyuns = qtyuns;
	}

	public double getUom() {
		return uom;
	}

	public void setUom(double uom) {
		this.uom = uom;
	}

	public double getQtyqi() {
		return qtyqi;
	}

	public void setQtyqi(double qtyqi) {
		this.qtyqi = qtyqi;
	}

	public double getQtyblk() {
		return qtyblk;
	}

	public void setQtyblk(double qtyblk) {
		this.qtyblk = qtyblk;
	}

	@Override
	public String toString() {
		return "StockContent [id=" + id + ", pn=" + pn + ", plant=" + plant
				+ ", sloc=" + sloc + ", wkcenter=" + wkcenter + ", qtyuns="
				+ qtyuns + ", uom=" + uom + ", qtyqi=" + qtyqi + ", qtyblk="
				+ qtyblk + "]";
	}
	
}