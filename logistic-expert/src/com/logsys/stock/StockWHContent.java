package com.logsys.stock;

import java.util.Date;

/**
 * �ⷿ�����Ϣ��
 * @author lx8sn6
 */
public class StockWHContent {

	/**���Ϻ�*/
	private String pn;
	
	/**��������*/
	private Date aogdate;
	
	/**������*/
	private String po;
	
	/**���κ�*/
	private String bn;
	
	private String asn;
	
	/**�ջ�����*/
	private String recn;
	
	/**��������*/
	private double arvqty;
	
	private String vendor;
	
	/**������� */
	private Date putindate;
	
	/**�������*/
	private double putinqty;
	
	/**ʣ����*/
	private double qtyleft;

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public Date getAogdate() {
		return aogdate;
	}

	public void setAogdate(Date aogdate) {
		this.aogdate = aogdate;
	}

	public String getPo() {
		return po;
	}

	public void setPo(String po) {
		this.po = po;
	}

	public String getBn() {
		return bn;
	}

	public void setBn(String bn) {
		this.bn = bn;
	}

	public String getAsn() {
		return asn;
	}

	public void setAsn(String asn) {
		this.asn = asn;
	}

	public String getRecn() {
		return recn;
	}

	public void setRecn(String recn) {
		this.recn = recn;
	}

	public double getArvqty() {
		return arvqty;
	}

	public void setArvqty(double arvqty) {
		this.arvqty = arvqty;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Date getPutindate() {
		return putindate;
	}

	public void setPutindate(Date putindate) {
		this.putindate = putindate;
	}

	public double getPutinqty() {
		return putinqty;
	}

	public void setPutinqty(double putinqty) {
		this.putinqty = putinqty;
	}

	public double getQtyleft() {
		return qtyleft;
	}

	public void setQtyleft(double qtyleft) {
		this.qtyleft = qtyleft;
	}

	public StockWHContent(String pn, Date aogdate, String po, String bn,
			String asn, String recn, double arvqty, String vendor,
			Date putindate, double putinqty, double qtyleft) {
		super();
		this.pn = pn;
		this.aogdate = aogdate;
		this.po = po;
		this.bn = bn;
		this.asn = asn;
		this.recn = recn;
		this.arvqty = arvqty;
		this.vendor = vendor;
		this.putindate = putindate;
		this.putinqty = putinqty;
		this.qtyleft = qtyleft;
	}

	public StockWHContent() {}
	
	@Override
	public String toString() {
		return "StockWHContent [pn=" + pn + ", aogdate=" + aogdate + ", po="
				+ po + ", bn=" + bn + ", asn=" + asn + ", recn=" + recn
				+ ", arvqty=" + arvqty + ", vendor=" + vendor + ", putindate="
				+ putindate + ", putinqty=" + putinqty + ", qtyleft=" + qtyleft
				+ "]";
	}
	
}
