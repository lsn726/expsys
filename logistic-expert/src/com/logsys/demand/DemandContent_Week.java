package com.logsys.demand;

/**
 * 按周需求数据
 * @author ShaonanLi
 */
public class DemandContent_Week {

	private String pn;
	
	private int year;
	
	private int week;
	
	private double qty;

	public DemandContent_Week(String pn, int year, int week, double qty) {
		this.pn = pn;
		this.year = year;
		this.week = week;
		this.qty = qty;
	}
	
	public DemandContent_Week(String pn, String year, String week, double qty) {
		this.pn = pn;
		this.year = Integer.parseInt(year);
		this.week = Integer.parseInt(week);
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

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "DemandContent_Week [pn=" + pn + ", year=" + year + ", week="
				+ week + ", qty=" + qty + "]";
	}
	
}
