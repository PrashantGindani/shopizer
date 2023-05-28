package com.salesmanager.shop.model.catalog.manufacturer;

import java.io.Serializable;



public class ManufacturerEntity extends Manufacturer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int order;
	private String imageUrl;

	public void setOrder(int order) {
		this.order = order;
	}
	public int getOrder() {
		return order;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


}
