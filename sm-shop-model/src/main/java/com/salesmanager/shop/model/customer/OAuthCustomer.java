package com.salesmanager.shop.model.customer;

import io.swagger.annotations.ApiModel;

@ApiModel(value="OAuthCustomer", description="OAuth Customer model object")
public class OAuthCustomer {

	private static final long serialVersionUID = 1L;
	private String idToken = null;
    private String accessToken = null;
	public String getIdToken() {
		return idToken;
	}
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
