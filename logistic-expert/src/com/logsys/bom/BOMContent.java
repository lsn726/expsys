package com.logsys.bom;

import java.util.Calendar;

/**
 * 数据库BOM表的Bean对象
 * @author lx8sn6
 */
public class BOMContent {

	/**主键*/
	private int id;
	
	/**组装件物料号*/
	private String asmpn;
	
	/**工厂*/
	private String plant;
	
	/**用于组装组装件的子零件物料号*/
	private String subpn;
	
	/**消耗数量*/
	private double qty;
	
	/**计量单位*/
	private String uom;
	
	/**BOM版本*/
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
