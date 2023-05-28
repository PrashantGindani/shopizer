package com.salesmanager.core.business.services.banner;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.banners.BannerGroupRepository;
import com.salesmanager.core.business.repositories.banners.BannergroupbannersRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.banner.Bannergroupbanners;
import com.salesmanager.core.model.banner.BannergroupbannersId;
import com.salesmanager.core.model.banner.Bannergroups;

@Service("bannerGroupBannerService")
public class BannerGroupBannerServiceImpl extends SalesManagerEntityServiceImpl<BannergroupbannersId, Bannergroupbanners> 
implements BannerGroupBannerService{

	private static final Logger LOGGER = LoggerFactory.getLogger(BannerGroupBannerServiceImpl.class);
	  
	private BannergroupbannersRepository bannergroupbannersRepository;
	  
	@Inject
	public BannerGroupBannerServiceImpl(BannergroupbannersRepository bannergroupbannersRepository) {
		super(bannergroupbannersRepository);
		this.bannergroupbannersRepository = bannergroupbannersRepository;
	}
	
	@Override
	  public void saveOrUpdate(Bannergroupbanners bannergroupbanners) throws ServiceException {

	    LOGGER.debug("Creating Manufacturer");

	    if (bannergroupbanners.getId() != null && bannergroupbanners.getId().getBannerid() > 0) {
	      super.update(bannergroupbanners);

	    } else {
	      super.create(bannergroupbanners);

	    }
	  }
	
	@Override
	public List<Bannergroupbanners> getAllByBannerId(Integer bid) {
		return bannergroupbannersRepository.findAll().stream().filter(b -> b.getBannerid()==bid).collect(Collectors.toList());		
	}
	
	@Override
	public void deleteAllByBannerId(Integer bid) {
		bannergroupbannersRepository.findAll().stream().filter(b -> b.getBannerid()==bid).forEach(s -> bannergroupbannersRepository.delete(s));		
	}
	
}