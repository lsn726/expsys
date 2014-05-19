package com.logsys.model;

/**
 * �ͺ�������,�Խ����ݿ�model��
 * @author lx8sn6
 */
public class ModelContent {

	/**����*/
	private int id;
	
	/**�ͻ���*/
	private String client;
	
	/**�ͺ�*/
	private String model;
	
	/**��Ŀ����*/
	private String prjcode;
	
	/**�ͺ���Ϣ*/
	private String info;
	
	/**��װ����*/
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
