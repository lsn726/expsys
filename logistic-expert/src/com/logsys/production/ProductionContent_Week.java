package com.logsys.production;

/**
 * 按周产出数据
 * @author ShaonanLi
 */
public class ProductionContent_Week {

	private String workcenter;
	
	private int year;
	
	private int week;
	
	private String output;
	
	private double qty;

	public ProductionContent_Week(String workcenter, int year, int week, String output, double qty) {
		this.workcenter = workcenter;
		this.year = year;
		this.week = week;
		this.output = output;
		this.qty = qty;
	}
	
	public ProductionContent_Week(String workcenter, String year, String week, String output, double qty) {
		this.workcenter = workcenter;
		this.year = Integer.parseInt(year);
		this.week = Integer.parseInt(week);
		this.output = output;
		this.qty = qty;
	}

	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "ProductionContent_Week [workcenter=" + workcenter + ", year="
				+ year + ", week=" + week + ", output=" + output + ", qty="
				+ qty + "]";
	}
	
}
