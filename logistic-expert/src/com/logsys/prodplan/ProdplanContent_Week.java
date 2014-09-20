package com.logsys.prodplan;

/**
 * 按周生产计划信息
 * @author ShaonanLi
 */
public class ProdplanContent_Week {

	private String pn;
	
	private int year;
	
	private int week;
	
	private double qty;

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

	public ProdplanContent_Week(String pn, int year, int week, double qty) {
		this.pn = pn;
		this.year = year;
		this.week = week;
		this.qty = qty;
	}

	public ProdplanContent_Week(String pn, String year, String week, double qty) {
		this.pn = pn;
		this.year = Integer.valueOf(year);
		this.week = Integer.valueOf(week);
		this.qty = qty;
	}
	
	@Override
	public String toString() {
		return "ProdplanContent_Week [pn=" + pn + ", year=" + year + ", week="
				+ week + ", qty=" + qty + "]";
	}

}
