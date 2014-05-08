package com.logsys.matoperdoc;

import java.util.Date;

/**
 * 物料操作文档内容，对应数据库的MatOperDoc表
 * @author lx8sn
 */
public class MatOperDocContent {

	private int id;
	
	private String pn;
	
	private String sloc;
	
	private String mvtype;
	
	private String docnum;
	
	private Date postdate;
	
	private double qty;
	
	private String uom;
	
	private String plant;
	
	private String ordernum;
	
	private String customer;
	
	private String vendor;
	
	private String header;

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

	public String getMvtype() {
		return mvtype;
	}

	public void setMvtype(String mvtype) {
		this.mvtype = mvtype;
	}

	public String getDocnum() {
		return docnum;
	}

	public void setDocnum(String docnum) {
		this.docnum = docnum;
	}

	public Date getPostdate() {
		return postdate;
	}

	public void setPostdate(Date postdate) {
		this.postdate = postdate;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSloc() {
		return sloc;
	}

	public void setSloc(String sloc) {
		this.sloc = sloc;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}
	
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	@Override
	public String toString() {
		return "MatOperDocContent [id=" + id + ", pn=" + pn + ", sloc=" + sloc
				+ ", mvtype=" + mvtype + ", docnum=" + docnum + ", postdate="
				+ postdate + ", qty=" + qty + ", uom=" + uom + ", plant="
				+ plant + ", ordernum=" + ordernum + ", customer=" + customer
				+ ", vendor=" + vendor + ", header=" + header + "]";
	}
	
}
