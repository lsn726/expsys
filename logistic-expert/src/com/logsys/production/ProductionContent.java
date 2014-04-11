package com.logsys.production;

import java.util.Date;

/**
 * 生产内容Bean，对应生产内容数据库
 * @author lx8sn6
 */
public class ProductionContent {

	private int id;
	
	private String workcenter;
	
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

}
