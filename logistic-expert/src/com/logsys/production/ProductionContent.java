package com.logsys.production;

import java.util.Date;

/**
 * 生产内容Bean，对应生产内容数据库
 * @author lx8sn6
 */
public class ProductionContent {

	private int id;
	
	private String workcenter;
	
	private Date date;
	
	private String output;
	
	private int operqty;
	
	private double qty;
	
	private Date tfbegin;
	
	private Date tfend;
	
	private int effmin;

	public ProductionContent() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public int getOperqty() {
		return operqty;
	}

	public void setOperqty(int operqty) {
		this.operqty = operqty;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public Date getTfbegin() {
		return tfbegin;
	}

	public void setTfbegin(Date tfbegin) {
		this.tfbegin = tfbegin;
	}

	public Date getTfend() {
		return tfend;
	}

	public void setTfend(Date tfend) {
		this.tfend = tfend;
	}

	public int getEffmin() {
		return effmin;
	}

	public void setEffmin(int effmin) {
		this.effmin = effmin;
	}

	@Override
	public String toString() {
		return "ProductionContent [id=" + id + ", workcenter=" + workcenter
				+ ", date=" + date + ", output=" + output + ", operqty="
				+ operqty + ", qty=" + qty + ", tfbegin=" + tfbegin
				+ ", tfend=" + tfend + ", effmin=" + effmin + "]";
	}
	
}
