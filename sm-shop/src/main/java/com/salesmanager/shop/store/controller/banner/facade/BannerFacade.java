package com.salesmanager.shop.store.controller.banner.facade;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.banner.Banners;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.banner.banner.PersistableBanner;
import com.salesmanager.shop.model.banner.banner.ReadableBanner;
import com.salesmanager.shop.model.customer.CustomerEntity;

/**
 * <p>Banner facade working as a bridge between {@link BannersService} and Controller
 * It will take care about interacting with Service API and doing any pre and post processing
 * </p>
 *
 * @author Prashant Gindani
 * @version 2.2.1 - 2.7.0
 * @author carlsamson
 *
 *
 */
public interface BannerFacade {

    /**
     * Method used to fetch banners based on the banner groupname.
     *
     * @param bannergrpName
     * @param store
     * @param store
     * @param language
     * @throws Exception
     *
     */
    List<ReadableBanner> getBannersFromGroupName(String bannergrpName, MerchantStore store, Language language) throws Exception;

    /**
     * Method used to fetch homepage banners.
     *
     * @param store
     * @param store
     * @param language
     * @throws Exception
     *
     */
	List<ReadableBanner> getHomeBanners(MerchantStore store, Language language) throws Exception;

	void setImage(Integer id, MultipartFile image, MerchantStore store, Language language) throws Exception;

	void bannerDelete(Integer id, MerchantStore store, Language language) throws Exception;

	void createUpdateBanner(PersistableBanner pb, MultipartFile image, MerchantStore store, Language language)
			throws Exception;

}
