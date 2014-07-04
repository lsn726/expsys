package com.logsys.model;

/**
 * 型号内容类,对接数据库model表
 * @author lx8sn6
 */
public class ModelContent {

	/**主键*/
	private int id;
	
	/**客户名*/
	private String client;
	
	/**型号*/
	private String model;
	
	/**项目代号*/
	private String prjcode;
	
	/**型号信息*/
	private String info;
	
	/**包装数量*/
	private int pq;
	
	/**MRP乘数*/
	private double mrpfactor;
	
	/**MRP的Rounding Value*/
	private int mrpround; 
	
	/**运输在途天数*/
	private int intranday;

	public ModelContent() {}

	public ModelContent(int id, String client, String model, String prjcode, String info, int pq, double mrpfactor, int mrpround, int intranday) {
		this.id = id;
		this.client = client;
		this.model = model;
		this.prjcode = prjcode;
		this.info = info;
		this.pq = pq;
		this.mrpfactor = mrpfactor;
		this.mrpround = mrpround;
		this.intranday=intranday;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPrjcode() {
		return prjcode;
	}

	public void setPrjcode(String prjcode) {
		this.prjcode = prjcode;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getPq() {
		return pq;
	}

	public void setPq(int pq) {
		this.pq = pq;
	}

	public double getMrpfactor() {
		return mrpfactor;
	}

	public void setMrpfactor(double mrpfactor) {
		this.mrpfactor = mrpfactor;
	}

	public int getMrpround() {
		return mrpround;
	}

	public void setMrpround(int mrpround) {
		this.mrpround = mrpround;
	}
	
	public int getIntranday() {
		return intranday;
	}

	public void setIntranday(int intranday) {
		this.intranday = intranday;
	}

	public ModelContent clone() {
		ModelContent cloned=new ModelContent();
		cloned.id = id;
		cloned.client = new String(client);
		cloned.model = new String(model);
		cloned.prjcode = new String(prjcode);
		cloned.info = new String(info);
		cloned.pq = pq;
		cloned.mrpfactor = mrpfactor;
		cloned.mrpround = mrpround;
		cloned.intranday = intranday;
		return cloned;
	}
	
}
