package com.salesmanager.shop.store.api.v1.wishlist;

import java.security.Principal;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.catalog.ReadableCatalog;
import com.salesmanager.shop.model.catalog.product.ReadableProductList;
import com.salesmanager.shop.model.entity.ReadableEntity;
import com.salesmanager.shop.model.entity.ReadableEntityList;
import com.salesmanager.shop.model.shoppingcart.PersistableWishListItem;
import com.salesmanager.shop.store.controller.wishlist.facade.WishListFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = {"WishList management resource (WishList Management Api)"})
@SwaggerDefinition(tags = {
    @Tag(name = "WishList management resource", description = "Manage WishList and attached products")
})
public class WishListApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(WishListApi.class);

  @Autowired
  private WishListFacade wishListFacade;
  
  @GetMapping(value = "/auth/wishlist")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(httpMethod = "GET", value = "Get wishlist products of user", notes = "",
      response = ReadableProductList.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
      @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")})
  public ReadableProductList getWishListProducts(
      @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,
      @RequestParam(value = "page", required = false, defaultValue="0") Integer page,
      @RequestParam(value = "count", required = false, defaultValue="10") Integer count,HttpServletRequest request, HttpServletResponse response) throws Exception {
	  
	  Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
	  
      return wishListFacade.getWishlistProducts(userName, merchantStore, language, page, count);

  }
  
  
  @PutMapping(value = "/auth/wishlist/add")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(httpMethod = "PUT", value = "Add product to user wishlist", notes = "",
      response = Void.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
      @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")})
  public void addWishListProduct(
		  @Valid @RequestBody PersistableWishListItem wishListItem,
      @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,HttpServletRequest request, HttpServletResponse response) throws Exception {

	  Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
	  
      wishListFacade.findOrAddWishListItem(userName,wishListItem.getProduct(), merchantStore, language);
      
      return;

  }

  

  @DeleteMapping(value = "/auth/wishlist/remove/{productSKU}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(httpMethod = "DELETE", value = "Delete product from user wishlist", notes = "",
      response = Void.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
      @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")})
  public void removeWishListProduct(
		  @PathVariable String productSKU,
      @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,HttpServletRequest request, HttpServletResponse response) throws Exception {

	  Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
	  
      wishListFacade.removeWishListItem(userName,productSKU, merchantStore, language);
      
      return;

  }
  
}
