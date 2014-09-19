package com.logsys.prodplan;

import java.util.Date;

/**
 * 合并生产线的按天生产计划信息类
 * @author lx8sn6
 */
public class ProdplanContent_PrdLineCombined {

	private Date date;
	
	private String pn;
	
	private Double qty;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public ProdplanContent_PrdLineCombined(Date date, String pn, Double qty) {
		super();
		this.date = date;
		this.pn = pn;
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "ProdplanContent_PrdLineCombined [date=" + date + ", pn=" + pn
				+ ", qty=" + qty + "]";
	}
	
}
