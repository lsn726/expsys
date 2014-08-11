package com.logsys.demand;

import java.util.Date;

/**
 * ��Ӧ���ݿ�����󱸷ݱ�Bean�����ܱ�����������
 * @author lx8sn6
 */
public class DemandBackupContent_Week {
	
	/**��*/
	private int year;
	
	/**��*/
	private int week;
	
	/**�汾*/
	private Date version;
	
	/**���Ϻ�*/
	private String pn;
	
	/**��������*/
	private double qty;
	
	/**����*/
	private Date date;
	
	/**����ʱ����������ڵ�����*/
	private int dlvfix;

	public DemandBackupContent_Week() {}

	public DemandBackupContent_Week(int year, int week, Date version, String pn, double qty, Date date, int dlvfix) {
		super();
		this.year = year;
		this.week = week;
		this.version = version;
		this.pn = pn;
		this.qty = qty;
		this.date = date;
		this.dlvfix = dlvfix;
	}
	
	public DemandBackupContent_Week(String year, String week, Date version, String pn, double qty, Date date, int dlvfix) {
		super();
		this.year = Integer.parseInt(year);
		this.week = Integer.parseInt(week);
		this.version = version;
		this.pn = pn;
		this.qty = qty;
		this.date = date;
		this.dlvfix = dlvfix;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
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

	public int getDlvfix() {
		return dlvfix;
	}

	public void setDlvfix(int dlvfix) {
		this.dlvfix = dlvfix;
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

	@Override
	public String toString() {
		return "DemandBackupContent_Week [year=" + year + ", week=" + week
				+ ", version=" + version + ", pn=" + pn + ", qty=" + qty
				+ ", date=" + date + ", dlvfix=" + dlvfix + "]";
	}
	
}
