package com.salesmanager.core.business.modules.cms.product;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.banner.Banners;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.content.ImageContentFile;

public interface OtherAssetsManager {
	
	public static final String CATEGORY_ROOT = "category";

	public static final String MANUFACTURER_ROOT = "manufacturer";
	
	public static final String BANNER_ROOT = "banner";
	
	void addCategoryImage(Category category, ImageContentFile contentImage) throws ServiceException;

	void removeCategoryImage(Category category) throws ServiceException;

	void addManufacturerImage(Manufacturer manufacturer, ImageContentFile contentImage) throws ServiceException;

	void removeManufacturerImage(Manufacturer manufacturer) throws ServiceException;

	void addBannerImage(Banners banner, ImageContentFile contentImage) throws ServiceException;

	void removeBannerImage(Banners banner) throws ServiceException;

}
