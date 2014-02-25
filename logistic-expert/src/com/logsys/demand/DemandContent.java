package com.logsys.demand;

import java.util.Date;

/**
 * 数据库映射的需求内容Bean
 * @author lx8sn6
 */
public class DemandContent {

	/**主键*/
	private int id;
	
	/**物料号*/
	private String pn;
	
	/**需求数量*/
	private double qty;
	
	/**日期*/
	private Date date;
	
	/**发货时间相较于日期的修正*/
	private int dlvfix;

	public DemandContent() {
		super();
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDlvfix() {
		return dlvfix;
	}

	public void setDlvfix(int dlvfix) {
		this.dlvfix = dlvfix;
	}
	
}
