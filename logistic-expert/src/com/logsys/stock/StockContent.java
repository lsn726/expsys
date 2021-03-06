package com.logsys.stock;

import java.util.Date;

/**
 * 库存信息类--对接库存数据库表
 * @author lx8sn6
 */
public class StockContent {

	private int id;
	
	private Date date;
	
	private String pn;
	
	private String plant;
	
	private String sloc;
	
	private double qty;
	
	private String uom;
	
	private String status;
	
	public StockContent() {}
	
	public StockContent(Date date, String pn, String plant, String sloc, double qty, String uom, String status) {
		this.date = date;
		this.pn = pn;
		this.plant = plant;
		this.sloc = sloc;
		this.qty = qty;
		this.uom = uom;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "StockContent [id=" + id + ", date=" + date + ", pn=" + pn
				+ ", plant=" + plant + ", sloc=" + sloc
				+ ", qty=" + qty + ", uom=" + uom + ", status="
				+ status + "]";
	}
	
}