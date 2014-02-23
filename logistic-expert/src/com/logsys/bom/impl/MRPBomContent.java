package com.logsys.bom.impl;

/**
 * 用于MRP的Bom内容Bean, 在Basic内容Bean的基础上，增加了MakeBuy信息
 * @author ShaonanLi
 */
public class MRPBomContent extends BasicBomContent {

	/**内部制作还是外部购买*/
	private String makebuy;

	public MRPBomContent() {
		super();
	}

	public MRPBomContent(String pn, double qty, String uom, String makebuy) {
		super(pn, qty, uom);
		this.makebuy=makebuy;
	}

	public String getMakebuy() {
		return makebuy;
	}

	public void setMakebuy(String makebuy) {
		this.makebuy = makebuy;
	}
	
}
