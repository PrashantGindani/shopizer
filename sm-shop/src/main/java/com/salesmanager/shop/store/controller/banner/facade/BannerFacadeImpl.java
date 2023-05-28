package com.salesmanager.shop.store.controller.banner.facade;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.aether.util.StringUtils;
import org.jsoup.helper.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.business.services.banner.BannerGroupBannerService;
import com.salesmanager.core.business.services.banner.BannerGroupService;
import com.salesmanager.core.business.services.banner.BannersService;
import com.salesmanager.core.model.banner.Bannergroupbanners;
import com.salesmanager.core.model.banner.Banners;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.banner.banner.PersistableBanner;
import com.salesmanager.shop.model.banner.banner.ReadableBanner;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.utils.ImageFilePath;

@Service
public class BannerFacadeImpl implements BannerFacade {

	private static final Logger LOG = LoggerFactory.getLogger(BannerFacadeImpl.class);

	 @Inject
	 private BannersService bannersService;
	 @Inject
	 private BannerGroupService bannerGroupService;
	 @Inject
	 private BannerGroupBannerService bannerGroupBannerService;
	 
	 @Inject
	 @Qualifier("img")
	 ImageFilePath imageUtils;
	 
	@Override
	public List<ReadableBanner> getBannersFromGroupName(String bannergrpName, MerchantStore store, Language language)
			throws Exception {
		
		List<Banners> banners = bannersService.getBannersFromGroupName(bannergrpName, store);
		
		List<ReadableBanner> readableBanner = convert(banners,store);
		
		return readableBanner;
	}
	
	private List<ReadableBanner> convert(List<Banners> banners, MerchantStore store) {
		
		List<ReadableBanner> aa = new ArrayList<>();
		for(Banners bb: banners) {
			ReadableBanner rb = new ReadableBanner();
			rb.setId(bb.getId());
			rb.setBannertype(bb.getBannertype());
			rb.setBannerpositions(bb.getBannerpositions());
			rb.setImageUrl(imageUtils.buildOtherAssetFilePathWithoutStore(store, Constants.BANNER_URI, bb.getImageName()));
			rb.setBrandid(bb.getRelatedbrandid());
			rb.setCatogryid(bb.getRelatedcategoryid());
			rb.setCustomProductlist(!StringUtils.isEmpty(bb.getCustomsqlquery()));
			rb.setSearchText(bb.getCustomtexttosearch());
			rb.setWebpagelink(bb.getWebpagelink());
			rb.setResponseType(bb.getOnclickresponsetype());
			
			aa.add(rb);
		}
		
		return aa;
	}

	@Override
	public List<ReadableBanner> getHomeBanners(MerchantStore store, Language language)
			throws Exception {
		
		List<Banners> banners = bannersService.getBannersFromGroupName("HOME", store);
		
		List<ReadableBanner> readableBanners = convert(banners,store);
		
		return readableBanners;
	}

	@Override
	public void createUpdateBanner(PersistableBanner pb,MultipartFile image, MerchantStore store, Language language) throws Exception {
		
		Validate.notNull(image, "Image must not be null");
		Validate.notNull(store, "Store must not be null");
		
		Banners bb= new Banners();
//		if(bb==null) {
//			throw new ServiceRuntimeException("Error no Banner [" + id + "] exists");
//		}

		bb.setBannertype(pb.getBannertype());
		bb.setBannerpositions(pb.getBannerpositions());
		bb.setCustomsqlquery(pb.getCustomsqlquery());
		bb.setCustomtexttosearch(pb.getCustomtexttosearch());
		bb.setOnclickresponsetype(pb.getOnclickresponsetype());
		bb.setRelatedbrandid(pb.getRelatedbrandid());
		bb.setRelatedcategoryid(pb.getRelatedcategoryid());
		bb.setRelatedsubcategoryid(pb.getRelatedsubcategoryid());
		bb.setWebpagelink(pb.getWebpagelink());
		
		bb.setImageName(pb.getImageName());
		bb.setImage(image.getInputStream());
		
		if(pb.getId()!=null && pb.getId()>0) {
			bb.setId(pb.getId());
			bannersService.saveOrUpdate(bb);
		}
		else {
			bannersService.save(bb);
		}
		
		bb.setImageName("B"+bb.getId()+"-"+image.getOriginalFilename());
		
		bannersService.addBannerImage(bb);
		
		//categoryService.saveOrUpdate(cc);
	}
	
	@Override
	public void setImage(Integer id, MultipartFile image, MerchantStore store, Language language) throws Exception {
		
		Validate.notNull(id, "Banner id must not be null");
		Validate.notNull(image, "Image must not be null");
		Validate.notNull(store, "Store must not be null");
		
		Banners bb= bannersService.getById(id);
		if(bb==null) {
			throw new ServiceRuntimeException("Error no Banner [" + id + "] exists");
		}
		
		bb.setImageName("B"+id+"-"+image.getOriginalFilename());
		bb.setImage(image.getInputStream());
		
		bannersService.addBannerImage(bb);
		
		//categoryService.saveOrUpdate(cc);
	}
	
	@Override
	public void bannerDelete(Integer id, MerchantStore store, Language language) throws Exception {
		
		Validate.notNull(id, "Category id must not be null");
		Validate.notNull(store, "Store must not be null");
		
		Banners bb= bannersService.getById(id);
		if(bb==null) {
			throw new ServiceRuntimeException("Error no Banner [" + id + "] exists");
		}		
		
		bannerGroupBannerService.deleteAllByBannerId(id);
		
		bannersService.removeBannerImage(bb);
		
	}
}
