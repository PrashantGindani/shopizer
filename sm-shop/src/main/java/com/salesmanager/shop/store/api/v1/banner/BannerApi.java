package com.salesmanager.shop.store.api.v1.banner;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.banner.banner.PersistableBanner;
import com.salesmanager.shop.model.banner.banner.ReadableBanner;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.api.v1.customer.CustomerApi;
import com.salesmanager.shop.store.controller.banner.facade.BannerFacade;
import com.salesmanager.shop.store.controller.customer.facade.CustomerFacade;
import com.salesmanager.shop.store.controller.user.facade.UserFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Banner management resource (Banner Management Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Banner management resource", description = "Manage banners") })
public class BannerApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(BannerApi.class);
	
	@Inject
	private BannerFacade bannerFacade;

	/** Create new customer for a given MerchantStore 
	 * @throws Exception */
	@GetMapping("/banner/home")
	@ApiOperation(httpMethod = "GET", value = "Gets Home Banners", notes = "", produces = "application/json", response = ReadableBanner.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public List<ReadableBanner> getHomeBanners(@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) throws Exception {
		return bannerFacade.getHomeBanners(merchantStore, language);

	}
	
	@ResponseStatus(OK)
	@RequestMapping(value = { "/private/banner"}, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE }, method = RequestMethod.PUT)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public void createUpdate(
			@ModelAttribute PersistableBanner pb,
			//@RequestParam(value = "file", required = true) MultipartFile file,
			@ApiIgnore MerchantStore merchantstore, @ApiIgnore Language language) {
		try {
			bannerFacade.createUpdateBanner(pb, pb.getFile(), merchantstore, language);
		} catch (Exception e) {
			LOGGER.error("Error while creating Banner", e);
			throw new ServiceRuntimeException("Error while creating Banner");
		}
	}
	
//	@Deprecated
//	@ResponseStatus(OK)
//	@RequestMapping(value = { "/private/banner/{id}"}, consumes = {
//			MediaType.MULTIPART_FORM_DATA_VALUE }, method = RequestMethod.PUT)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
//	public void bannerImageUpdate(@PathVariable("id") Integer id, 
//			@RequestParam(value = "file", required = true) MultipartFile file,
//			@ApiIgnore MerchantStore merchantstore, @ApiIgnore Language language) {
//		try {
//			bannerFacade.setImage(id, file, merchantstore, language);
//		} catch (Exception e) {
//			LOGGER.error("Error while updating Banner", e);
//			throw new ServiceRuntimeException("Error while updating Banner");
//		}
//	}
	
	@ResponseStatus(OK)
	@RequestMapping(value = { "/private/banner/{id}"}, method = RequestMethod.DELETE)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public void bannerDelete(@PathVariable("id") Integer id, 
			@ApiIgnore MerchantStore merchantstore, @ApiIgnore Language language) {
		try {
			bannerFacade.bannerDelete(id, merchantstore, language);
		} catch (Exception e) {
			LOGGER.error("Error while Banner Delete", e);
			throw new ServiceRuntimeException("Error while Banner Delete");
		}
	}
}
