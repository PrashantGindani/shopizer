package com.salesmanager.core.business.services.banner;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.banners.BannerGroupRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.banner.Bannergroups;

@Service("bannerGroupService")
public class BannerGroupServiceImpl  extends SalesManagerEntityServiceImpl<Integer, Bannergroups> 
implements BannerGroupService{

	private static final Logger LOGGER = LoggerFactory.getLogger(BannerGroupServiceImpl.class);
	  
	private BannerGroupRepository bannerGroupRepository;
	  
	@Inject
	public BannerGroupServiceImpl(BannerGroupRepository bannerGroupRepository) {
		super(bannerGroupRepository);
		this.bannerGroupRepository = bannerGroupRepository;
	}
	
	@Override
	  public void saveOrUpdate(Bannergroups bannergroups) throws ServiceException {

	    LOGGER.debug("Creating Manufacturer");

	    if (bannergroups.getId() != null && bannergroups.getId() > 0) {
	      super.update(bannergroups);

	    } else {
	      super.create(bannergroups);

	    }
	  }
	
}