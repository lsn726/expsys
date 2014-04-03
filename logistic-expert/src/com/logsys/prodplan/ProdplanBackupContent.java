package com.logsys.prodplan;

import java.util.Date;

/***
 * �����ƻ���������
 * @author lx8sn6
 */
public class ProdplanBackupContent {

	private int id;
	
	private String pn;
	
	private double qty;
	
	private String prdline;
	
	private Date date;
	
	private Date version;

	public ProdplanBackupContent() {}
	
	/**
	 * ���������ƻ����ݴ������������ƻ�����
	 * @param ppcont
	 */
	public ProdplanBackupContent(ProdplanContent ppcont,Date version) {
		this.pn=ppcont.getPn();
		this.qty=ppcont.getQty();
		this.prdline=ppcont.getPrdline();
		this.date=ppcont.getDate();
		this.version=version;
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

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	public String getPrdline() {
		return prdline;
	}

	public void setPrdline(String prdline) {
		this.prdline = prdline;
	}
	
}
