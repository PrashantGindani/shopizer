package com.salesmanager.shop.store.controller.wishlist.facade;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.inject.Inject;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.catalog.product.relationship.ProductRelationshipService;
import com.salesmanager.core.business.services.wishlist.WishListService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.wishlist.WishListAttributeItem;
import com.salesmanager.core.model.wishlist.WishListItem;
import com.salesmanager.shop.model.banner.banner.ReadableBanner;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.catalog.product.ReadableProductList;
import com.salesmanager.shop.model.shoppingcart.PersistableWishListItem;
import com.salesmanager.shop.populator.catalog.ReadableProductPopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.controller.customer.facade.CustomerFacade;
import com.salesmanager.shop.utils.DateUtil;
import com.salesmanager.shop.utils.ImageFilePath;

@Service(value = "wishListFacade")
public class WishListFacadeImpl implements WishListFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(WishListFacadeImpl.class);
	
	@Autowired
	private CustomerFacade customerFacade;
	@Autowired
	private WishListService wishListService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private PricingService pricingService;
	@Autowired
	private ProductRelationshipService productRelationshipService;
	@Autowired
	@Qualifier("img")
	private ImageFilePath imageUtils;
	
	@Override
	public void findOrAddWishListItem(String user_nick, String sku, MerchantStore merchant, Language language) throws Exception
	{
		Validate.notNull(user_nick, "Customer Id must not be null");
		Validate.notNull(sku, "product sku must not be null");
		Validate.notNull(merchant, "MerchantStore must not be null");

		// get customer
		Customer customer = customerFacade.getCustomerByUserName(user_nick, merchant);

		if (customer == null) {
			throw new ResourceNotFoundException("Customer code [ " + user_nick + " ] not found");
		}

		Set<WishListItem> items = new HashSet<WishListItem>();
		WishListItem itemToDelete = null;
		for (WishListItem wishListItem : customer.getWishlistItems()) {
			if(itemToDelete==null) { itemToDelete = wishListItem; }
			if (wishListItem.getProduct().getSku().equals(sku)) {
				//already exists
				return;
			}
		}
		
		//replace oldest
		if(items.size()>=25 && itemToDelete!=null) {
			wishListService.deleteWishListItem(itemToDelete.getId());
		}
		
		//add if not exists
		PersistableWishListItem item = new PersistableWishListItem();
		item.setProduct(sku);


		WishListItem wishListItem = createWishListItem(customer, item, merchant);	
		customer.getWishlistItems().add(wishListItem);
		wishListService.saveOrUpdate(wishListItem);
	}

	
	@Override
	// KEEP
	public void removeWishListItem(String user_nick, String sku,
			MerchantStore merchant, Language language) throws Exception {
		Validate.notNull(user_nick, "Customer code must not be null");
		Validate.notNull(sku, "Product Sku must not be null");
		Validate.notNull(merchant, "MerchantStore must not be null");

		// get customer
		Customer customer = customerFacade.getCustomerByUserName(user_nick, merchant);

		if (customer == null) {
			throw new ResourceNotFoundException("Customer code [ " + user_nick + " ] not found");
		}

		Set<WishListItem> items = new HashSet<WishListItem>();
		WishListItem itemToDelete = null;
		for (WishListItem wishListItem : customer.getWishlistItems()) {
			if (wishListItem.getProduct().getSku().equalsIgnoreCase(sku)) {
				// get wishlist item
				itemToDelete = wishListItem;

			} else {
				items.add(wishListItem);
			}
		}
		// delete item
		if (itemToDelete != null) {
			wishListService.deleteWishListItem(itemToDelete.getId());
		}

		// remaining items
		if (items.size() > 0) {
			customer.setWishlistItems(items);
		} else {
			customer.getWishlistItems().clear();
		}

//		customerService.saveOrUpdate(customer);// update customer with remaining items
	}


	
	private WishListItem createWishListItem(Customer customer,
			PersistableWishListItem wishlistItem, MerchantStore store) throws Exception {

		// USE Product sku
		Product product = null;

		product = productService.getBySku(wishlistItem.getProduct(), store, store.getDefaultLanguage());// todo use
																											// language
																											// from api
																											// request
		if (product == null) {
			throw new ResourceNotFoundException(
					"Product with sku " + wishlistItem.getProduct() + " does not exist");
		}

		if (product.getMerchantStore().getId().intValue() != store.getId().intValue()) {
			throw new ResourceNotFoundException(
					"Item with id " + wishlistItem.getProduct() + " does not belong to merchant " + store.getId());
		}

		if (!product.isAvailable()) {
			throw new Exception("Product with sku " + product.getSku() + " is not available");
		}

		if (!DateUtil.dateBeforeEqualsDate(product.getDateAvailable(), new Date())) {
			throw new Exception("Item with sku " + product.getSku() + " is not available");
		}

		Set<ProductAvailability> availabilities = product.getAvailabilities();

		ProductVariant instance = null;
		if (CollectionUtils.isNotEmpty(product.getVariants())) {
			instance = product.getVariants().iterator().next();
			Set<ProductAvailability> instanceAvailabilities = instance.getAvailabilities();
			if(!CollectionUtils.isEmpty(instanceAvailabilities)) {
				availabilities = instanceAvailabilities;
			}
			
		}

//		if (CollectionUtils.isEmpty(availabilities)) {
//			throw new Exception(
//					"Item with id " + product.getId() + " is not properly configured. It contains no inventory");
//		}

		//todo filter sku and store
//		for (ProductAvailability availability : availabilities) {
//			if (availability.getProductQuantity() == null || availability.getProductQuantity().intValue() == 0) {
//				throw new Exception("Product with id " + product.getId() + " is not available");
//			}
//		}

		/**
		 * Check if product quantity is 0 Check if product is available Check if date
		 * available <= now
		 */

		// use a mapper
		WishListItem item = wishListService
				.populateWishListItem(product, store);

		item.setCustomer(customer);
		item.setSku(product.getSku());

		if (instance != null) {
			item.setVariant(instance.getId());
		}

		// set attributes
		List<com.salesmanager.shop.model.catalog.product.attribute.ProductAttribute> attributes = wishlistItem
				.getAttributes();
		if (!CollectionUtils.isEmpty(attributes)) {
			for (com.salesmanager.shop.model.catalog.product.attribute.ProductAttribute attribute : attributes) {

				ProductAttribute productAttribute = productAttributeService.getById(attribute.getId());

				if (productAttribute != null
						&& productAttribute.getProduct().getId().longValue() == product.getId().longValue()) {

					WishListAttributeItem attributeItem = new WishListAttributeItem(
							item, productAttribute);

					item.addAttributes(attributeItem);
				}
			}
		}

		return item;
	}

	public ReadableProductList getWishlistProducts(String user_nick, MerchantStore merchant, Language language, Integer page, Integer count) throws Exception{
		
		Validate.notNull(user_nick, "Customer Id must not be null");
		Validate.notNull(merchant, "MerchantStore must not be null");

		// get customer
		Customer customer = customerFacade.getCustomerByUserName(user_nick, merchant);

		if (customer == null) {
			throw new ResourceNotFoundException("Customer code [ " + user_nick + " ] not found");
		}

		ReadableProductPopulator populator = new ReadableProductPopulator();
		populator.setPricingService(pricingService);
		populator.setimageUtils(imageUtils);

		Set<WishListItem> items = new HashSet<WishListItem>();
		ReadableProductList productList = new ReadableProductList();
		List<ReadableProduct> prods = productList.getProducts();
		for (WishListItem wishListItem : customer.getWishlistItems()) {
			ReadableProduct readProduct = populator.populate(wishListItem.getProduct(), new ReadableProduct(), merchant, language);
			readProduct.setIsWishlist(true);
			prods.add(readProduct);
		}
		
		productList.setProducts(prods);
		productList.setNumber(1);
		productList.setTotalPages(1);//no paging
		
		return productList;
	}
}
