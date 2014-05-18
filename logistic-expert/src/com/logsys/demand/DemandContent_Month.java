package com.logsys.demand;

/**
 * 按月需求数据
 * @author ShaonanLi
 */
public class DemandContent_Month {

	private String pn;
	
	private int year;
	
	private int month;
	
	private double qty;

	public DemandContent_Month(String pn, int year, int month, double qty) {
		this.pn = pn;
		this.year = year;
		this.month = month;
		this.qty = qty;
	}
	
	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "DemandContent_Week [pn=" + pn + ", year=" + year + ", month="
				+ month + ", qty=" + qty + "]";
	}
	
}
