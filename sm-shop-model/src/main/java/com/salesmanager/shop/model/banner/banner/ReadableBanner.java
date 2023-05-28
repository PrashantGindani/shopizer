package com.salesmanager.shop.model.banner.banner;

import java.io.Serializable;

public class ReadableBanner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer    id ;
	private Integer    responseType ;
    private String     imageUrl ;
    private String     bannertype ;
    private String     bannerpositions ;
    private String     brandid ;
    private String     catogryid ;
    private String     webpagelink ;
    private String     searchText ;
    private boolean     isCustomProductlist ;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBannertype() {
		return bannertype;
	}
	public void setBannertype(String bannertype) {
		this.bannertype = bannertype;
	}
	public String getBannerpositions() {
		return bannerpositions;
	}
	public void setBannerpositions(String bannerpositions) {
		this.bannerpositions = bannerpositions;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getBrandid() {
		return brandid;
	}
	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}
	public String getCatogryid() {
		return catogryid;
	}
	public void setCatogryid(String catogryid) {
		this.catogryid = catogryid;
	}
	public String getWebpagelink() {
		return webpagelink;
	}
	public void setWebpagelink(String webpagelink) {
		this.webpagelink = webpagelink;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public boolean getIsCustomProductlist() {
		return isCustomProductlist;
	}
	public Integer getResponseType() {
		return responseType;
	}
	public void setResponseType(Integer responseType) {
		this.responseType = responseType;
	}
	public void setCustomProductlist(boolean isCustomProductlist) {
		this.isCustomProductlist = isCustomProductlist;
	}

}
