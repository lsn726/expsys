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
	
	private String outputprd;
	
	private int countablewkmin;
	
	private int operatorqty;
	
	private double outputqty;
	
	private Date tfbegin;
	
	private Date tfend;

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

	public int getOperatorqty() {
		return operatorqty;
	}

	public void setOperatorqty(int operatorqty) {
		this.operatorqty = operatorqty;
	}

	public double getOutputqty() {
		return outputqty;
	}

	public void setOutputqty(double outputqty) {
		this.outputqty = outputqty;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getCountablewkmin() {
		return countablewkmin;
	}

	public void setCountablewkmin(int countablewkmin) {
		this.countablewkmin = countablewkmin;
	}

	public String getOutputprd() {
		return outputprd;
	}

	public void setOutputprd(String outputprd) {
		this.outputprd = outputprd;
	}

	@Override
	public String toString() {
		return "ProductionContent [id=" + id + ", workcenter=" + workcenter
				+ ", date=" + date + ", outputprd=" + outputprd
				+ ", countablewkmin=" + countablewkmin + ", operatorqty="
				+ operatorqty + ", outputqty=" + outputqty + ", tfbegin="
				+ tfbegin + ", tfend=" + tfend + "]";
	}
	
}
