package com.logsys.material;

/**
 * 物料信息类，链接数据库的material表
 * @author lx8sn6
 */
public class MaterialContent {
	
	private int id;
	
	private String pn;
	
	private String plant;
	
	private String description;
	
	private String type;
	
	private String uom;
	
	private double price;
	
	private String currency;
	
	private int qtyprice;
	
	private String provider;
	
	private String makebuy;
	
	private String buyer;
	
	private boolean inuse;
	
	public MaterialContent() {
		super();
	}

	public MaterialContent(int id, String pn, String plant, String description,
			String type, String uom, double price, String currency,
			int qtyprice, String provider, boolean inuse) {
		super();
		this.id = id;
		this.pn = pn;
		this.plant = plant;
		this.description = description;
		this.type = type;
		this.uom = uom;
		this.price = price;
		this.currency = currency;
		this.qtyprice = qtyprice;
		this.provider = provider;
		this.inuse = inuse;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getQtyprice() {
		return qtyprice;
	}

	public void setQtyprice(int qtyprice) {
		this.qtyprice = qtyprice;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public boolean isInuse() {
		return inuse;
	}

	public void setInuse(boolean inuse) {
		this.inuse = inuse;
	}

	public String getMakebuy() {
		return makebuy;
	}

	public void setMakebuy(String makebuy) {
		this.makebuy = makebuy;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

}
