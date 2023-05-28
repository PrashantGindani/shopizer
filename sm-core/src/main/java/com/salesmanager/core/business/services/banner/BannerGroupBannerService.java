package com.salesmanager.core.business.services.banner;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.banner.Bannergroupbanners;

public interface BannerGroupBannerService {

	void saveOrUpdate(Bannergroupbanners bannergroupbanners) throws ServiceException;

	List<Bannergroupbanners> getAllByBannerId(Integer bid);

	void deleteAllByBannerId(Integer bid);

}
