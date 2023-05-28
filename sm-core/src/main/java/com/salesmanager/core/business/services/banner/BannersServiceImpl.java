package com.salesmanager.core.business.services.banner;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.product.ProductFileManager;
import com.salesmanager.core.business.repositories.banners.BannerRepository;
import com.salesmanager.core.business.services.catalog.product.manufacturer.ManufacturerServiceImpl;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.banner.Banners;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.merchant.MerchantStore;

@Service("bannersService")
public class BannersServiceImpl extends SalesManagerEntityServiceImpl<Integer, Banners> 
implements BannersService{

	private static final Logger LOGGER = LoggerFactory.getLogger(BannersServiceImpl.class);
	  
	private BannerRepository bannerRepository;

	@Inject
	private ProductFileManager productFileManager;
	  
	@Inject
	public BannersServiceImpl(BannerRepository bannerRepository) {
		super(bannerRepository);
		this.bannerRepository = bannerRepository;
	}
	
	@Override
	  public void saveOrUpdate(Banners banner) throws ServiceException {

	    LOGGER.debug("Creating Manufacturer");

	    if (banner.getId() != null && banner.getId() > 0) {
	      super.update(banner);

	    } else {
	      super.create(banner);

	    }
	  }
	
	@Override
	public List<Banners> getBannersFromGroupName(final String bannerGroupname, MerchantStore store) throws ServiceException {

		Validate.notNull(bannerGroupname, "BannerGroupName should not be null");
		Validate.notNull(store, "MerchantStore should not be null");
		
		try {
			//find all banners
			List<Banners> banners = bannerRepository.findBannersFromGroupName(bannerGroupname);

			return banners;

		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}
	
	@Override
	public void addBannerImage(Banners banner)
			throws ServiceException {

		ImageContentFile cmsContentImage = new ImageContentFile();
		try {
			
			InputStream inputStream = banner.getImage();
			cmsContentImage.setFileName(banner.getImageName());
			cmsContentImage.setFile(inputStream);
			cmsContentImage.setFileContentType(FileContentType.BANNER);
			
			Assert.notNull(cmsContentImage.getFile(), "ImageContentFile.file cannot be null");
			productFileManager.addBannerImage(banner, cmsContentImage);

			saveOrUpdate(banner);

		} catch (Exception e) {
			throw new ServiceException(e);
		} finally {
			try {

				if (cmsContentImage.getFile() != null) {
					cmsContentImage.getFile().close();
				}

			} catch (Exception ignore) {

			}
		}

	}
	
	@Override
	public void removeBannerImage(Banners banner)
			throws ServiceException {

		try {
			
			productFileManager.removeBannerImage(banner);

			banner.setImage(null);
			//banner.setImageName(null);
			
			delete(banner);

		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}
}
