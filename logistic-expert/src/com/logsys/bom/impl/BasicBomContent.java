package com.logsys.bom.impl;

import com.logsys.bom.interfaces.IBOMContentBean;

/**
 * ����BOM����Bean��ֻ�ṩ���������Ϣ������������������������ļ�����λ
 * @author ShaonanLi
 */
public class BasicBomContent implements IBOMContentBean {

	/**�������*/
	private String pn;
	
	/**��Ҫ����*/
	private double qty;
	
	/**������λ*/
	private String uom;
	
	public BasicBomContent() {
		super();
	}

	public BasicBomContent(String pn, double qty, String uom) {
		this.pn = pn;
		this.qty = qty;
		this.uom = uom;
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

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}
	
}
