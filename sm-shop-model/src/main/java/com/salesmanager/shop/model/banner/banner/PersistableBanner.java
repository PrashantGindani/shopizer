package com.salesmanager.shop.model.banner.banner;

import java.io.Serializable;

import javax.persistence.Column;

import org.springframework.web.multipart.MultipartFile;

public class PersistableBanner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer    id;
	
	private String     imageName="";

    private String     bannertype = null ;

    private String     bannerpositions = null;

    private String     relatedbrandid = null;

    private String     relatedcategoryid  = null;

    private String     relatedsubcategoryid = null;

    private Integer    onclickresponsetype = 2;

    private String     webpagelink = null;

    private String     customsqlquery = null;

    private String     customtexttosearch = null;
    
    private MultipartFile file;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
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

	public String getRelatedbrandid() {
		return relatedbrandid;
	}

	public void setRelatedbrandid(String relatedbrandid) {
		this.relatedbrandid = relatedbrandid;
	}

	public String getRelatedcategoryid() {
		return relatedcategoryid;
	}

	public void setRelatedcategoryid(String relatedcategoryid) {
		this.relatedcategoryid = relatedcategoryid;
	}

	public String getRelatedsubcategoryid() {
		return relatedsubcategoryid;
	}

	public void setRelatedsubcategoryid(String relatedsubcategoryid) {
		this.relatedsubcategoryid = relatedsubcategoryid;
	}

	public Integer getOnclickresponsetype() {
		return onclickresponsetype;
	}

	public void setOnclickresponsetype(Integer onclickresponsetype) {
		this.onclickresponsetype = onclickresponsetype;
	}

	public String getWebpagelink() {
		return webpagelink;
	}

	public void setWebpagelink(String webpagelink) {
		this.webpagelink = webpagelink;
	}

	public String getCustomsqlquery() {
		return customsqlquery;
	}

	public void setCustomsqlquery(String customsqlquery) {
		this.customsqlquery = customsqlquery;
	}

	public String getCustomtexttosearch() {
		return customtexttosearch;
	}

	public void setCustomtexttosearch(String customtexttosearch) {
		this.customtexttosearch = customtexttosearch;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
    
}
