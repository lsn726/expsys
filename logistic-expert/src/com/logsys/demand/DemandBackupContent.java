package com.logsys.demand;

import java.util.Date;

/**
 * ��Ӧ���ݿ�����󱸷ݱ�Bean
 * @author lx8sn6
 */
public class DemandBackupContent {

	/**����*/
	private int id;
	
	/**�汾*/
	private Date version;
	
	/**���Ϻ�*/
	private String pn;
	
	/**��������*/
	private double qty;
	
	/**����*/
	private Date date;
	
	/**����ʱ����������ڵ�����*/
	private int dlvfix;

	public DemandBackupContent() {
		
	}
	
	public DemandBackupContent(int id, Date version, String pn, double qty, Date date, int dlvfix) {
		super();
		this.id = id;
		this.version = version;
		this.pn = pn;
		this.qty = qty;
		this.date = date;
		this.dlvfix = dlvfix;
	}
	
	/**
	 * ��������󴴽������������
	 * @param demcont �����������ݵ�DemandContent����
	 * @param version ����汾
	 * @return DemandBackupContent����
	 */
	public static DemandBackupContent createDemBkupContFromDemCont(DemandContent demcont, Date version) {
		if(demcont==null||version==null) return null;
		return new DemandBackupContent(-1,version,demcont.getPn(),demcont.getQty(),demcont.getDate(),demcont.getDlvfix());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDlvfix() {
		return dlvfix;
	}

	public void setDlvfix(int dlvfix) {
		this.dlvfix = dlvfix;
	}

	@Override
	public String toString() {
		return "DemandBackupContent [id=" + id + ", version=" + version
				+ ", pn=" + pn + ", qty=" + qty + ", date=" + date
				+ ", dlvfix=" + dlvfix + "]";
	}
	
}
