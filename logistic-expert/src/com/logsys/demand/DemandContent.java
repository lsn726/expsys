package com.logsys.demand;

import java.util.Date;

/**
 * ���ݿ�ӳ�����������Bean
 * @author lx8sn6
 */
public class DemandContent {

	/**����*/
	private int id;
	
	/**���Ϻ�*/
	private String pn;
	
	/**��������*/
	private double qty;
	
	/**����*/
	private Date date;
	
	/**����ʱ����������ڵ�����*/
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
