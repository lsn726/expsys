package com.logsys.schelines;

import java.util.Calendar;

/**
 * Schedule Lines Bean
 * @author lx8sn6
 */
public class SchelinesContent {

	private int id;
	
	private Calendar slversion; 
	
	private long podoc;
	
	private int poitem;
	
	private int slid;
	
	private Calendar dlvdate;
	
	private double schqty;
	
	private double preschqty;
	
	private double dlvedqty;
	
	private char creatmtd;
	
	private Calendar orderdate;
	
	private char fix;
	
	private double qtyred;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getSlversion() {
		return slversion;
	}

	public void setSlversion(Calendar slversion) {
		this.slversion = slversion;
	}

	public long getPodoc() {
		return podoc;
	}

	public void setPodoc(long podoc) {
		this.podoc = podoc;
	}

	public int getPoitem() {
		return poitem;
	}

	public void setPoitem(int poitem) {
		this.poitem = poitem;
	}

	public int getSlid() {
		return slid;
	}

	public void setSlid(int slid) {
		this.slid = slid;
	}

	public Calendar getDlvdate() {
		return dlvdate;
	}

	public void setDlvdate(Calendar dlvdate) {
		this.dlvdate = dlvdate;
	}

	public double getSchqty() {
		return schqty;
	}

	public void setSchqty(double schqty) {
		this.schqty = schqty;
	}

	public double getPreschqty() {
		return preschqty;
	}

	public void setPreschqty(double preschqty) {
		this.preschqty = preschqty;
	}

	public double getDlvedqty() {
		return dlvedqty;
	}

	public void setDlvedqty(double dlvedqty) {
		this.dlvedqty = dlvedqty;
	}

	public char getCreatmtd() {
		return creatmtd;
	}

	public void setCreatmtd(char creatmtd) {
		this.creatmtd = creatmtd;
	}

	public Calendar getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Calendar orderdate) {
		this.orderdate = orderdate;
	}

	public char getFix() {
		return fix;
	}

	public void setFix(char fix) {
		this.fix = fix;
	}

	public double getQtyred() {
		return qtyred;
	}

	public void setQtyred(double qtyred) {
		this.qtyred = qtyred;
	}

	@Override
	public String toString() {
		return "SchelinesContent [id=" + id + ", slversion=" + slversion
				+ ", podoc=" + podoc + ", poitem=" + poitem + ", slid=" + slid
				+ ", dlvdate=" + dlvdate + ", schqty=" + schqty
				+ ", preschqty=" + preschqty + ", dlvedqty=" + dlvedqty
				+ ", creatmtd=" + creatmtd + ", orderdate=" + orderdate
				+ ", fix=" + fix + ", qtyred=" + qtyred + "]";
	}
	
}
