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

	public ModelContent() {}

	public ModelContent(int id, String client, String model, String prjcode, String info) {
		super();
		this.id = id;
		this.client = client;
		this.model = model;
		this.prjcode = prjcode;
		this.info = info;
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
	
}
