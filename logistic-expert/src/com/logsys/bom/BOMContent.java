package com.logsys.bom;

import java.util.Calendar;

/**
 * ���ݿ�BOM���Bean����
 * @author lx8sn6
 */
public class BOMContent {

	/**����*/
	private int id;
	
	/**��װ�����Ϻ�*/
	private String asmpn;
	
	/**����*/
	private String plant;
	
	/**������װ��װ������������Ϻ�*/
	private String subpn;
	
	/**��������*/
	private double qty;
	
	/**������λ*/
	private String uom;
	
	/**BOM�汾*/
	private Calendar version;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAsmpn() {
		return asmpn;
	}

	public void setAsmpn(String asmpn) {
		this.asmpn = asmpn;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getSubpn() {
		return subpn;
	}

	public void setSubpn(String subpn) {
		this.subpn = subpn;
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

	public Calendar getVersion() {
		return version;
	}

	public void setVersion(Calendar version) {
		this.version = version;
	}
	
}
