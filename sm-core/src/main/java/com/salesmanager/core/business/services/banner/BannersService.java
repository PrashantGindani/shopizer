package com.salesmanager.core.business.services.banner;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.banner.Banners;
import com.salesmanager.core.model.merchant.MerchantStore;

public interface BannersService extends SalesManagerEntityService<Integer, Banners> {

	/**
	 * Gets all the banners of given group name
	 * virtual return list will be null
	 * 
	 * @param bannerGroupname
	 * @return
	 * @throws ServiceException
	 */
	List<Banners> getBannersFromGroupName(String bannerGroupname, MerchantStore store) throws ServiceException;

	void saveOrUpdate(Banners banner) throws ServiceException;
	
	void addBannerImage(Banners banner) throws ServiceException;

	void removeBannerImage(Banners banner) throws ServiceException;

}
