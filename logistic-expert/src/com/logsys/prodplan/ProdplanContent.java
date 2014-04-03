package com.logsys.prodplan;

import java.util.Date;

/**
 * 生产计划内容类，跟数据库中的生产计划表对接
 * @author lx8sn6
 */
public class ProdplanContent {

	private int id;
	
	private String pn;
	
	private String prdline;
	
	private double qty;
	
	private Date date;

	public ProdplanContent() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
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

	public String getPrdline() {
		return prdline;
	}

	public void setPrdline(String prdline) {
		this.prdline = prdline;
	}
	
}