package com.salesmanager.core.business.services.banner;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.banner.Bannergroups;

public interface BannerGroupService {

	void saveOrUpdate(Bannergroups bannergroups) throws ServiceException;

}
