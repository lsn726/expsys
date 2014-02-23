package com.logsys.bom.impl;

/**
 * ����MRP��Bom����Bean, ��Basic����Bean�Ļ����ϣ�������MakeBuy��Ϣ
 * @author ShaonanLi
 */
public class MRPBomContent extends BasicBomContent {

	/**�ڲ����������ⲿ����*/
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
