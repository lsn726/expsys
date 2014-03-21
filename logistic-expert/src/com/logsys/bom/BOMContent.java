package com.logsys.bom;

import java.util.Date;

/**
 * BOM内容类，对应数据库的bom表bean
 * @author lx8sn6
 */
public class BOMContent {

	private int id;
	
	private int lvl;
	
	private String pn;
	
	private double qty;
	
	private String unit;
	
	private Date version;

	public BOMContent() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}
	
}
