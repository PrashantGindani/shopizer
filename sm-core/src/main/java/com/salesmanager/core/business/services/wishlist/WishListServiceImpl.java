package com.salesmanager.core.business.services.wishlist;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.shoppingcart.ShoppingCartAttributeRepository;
import com.salesmanager.core.business.repositories.shoppingcart.ShoppingCartItemRepository;
import com.salesmanager.core.business.repositories.shoppingcart.ShoppingCartRepository;
import com.salesmanager.core.business.repositories.wishlist.WishListAttributeRepository;
import com.salesmanager.core.business.repositories.wishlist.WishListItemRepository;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.shoppingcart.ShoppingCartService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.common.UserContext;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shipping.ShippingProduct;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import com.salesmanager.core.model.wishlist.WishListAttributeItem;
import com.salesmanager.core.model.wishlist.WishListItem;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("wishListService")
public class WishListServiceImpl extends SalesManagerEntityServiceImpl<Long, WishListItem>
		implements WishListService {

	@Inject
	private WishListItemRepository wishListItemRepository;

	@Inject
	private WishListAttributeRepository wishListAttributeItemRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(WishListServiceImpl.class);

	@Inject
	public WishListServiceImpl(WishListItemRepository wishListItemRepository) {
		super(wishListItemRepository);
		this.wishListItemRepository = wishListItemRepository;

	}
	

	@Override
	public WishListItem populateWishListItem(Product product, MerchantStore store) throws ServiceException {
		//TODO
		Validate.notNull(product, "Product should not be null");
		Validate.notNull(product.getMerchantStore(), "Product.merchantStore should not be null");
		Validate.notNull(store, "MerchantStore should not be null");

		WishListItem item = new WishListItem(product);
		item.setSku(product.getSku());//already in the constructor

		return item;

	}

	@Override
	@Transactional
	public void deleteWishListItem(Long id) {

		WishListItem item = wishListItemRepository.findOne(id);
		if (item != null) {

			if (item.getAttributes() != null) {
				item.getAttributes().forEach(a -> wishListAttributeItemRepository.deleteById(a.getId()));
				item.getAttributes().clear();
			}

			// refresh
			item = wishListItemRepository.findOne(id);

			// delete
			wishListItemRepository.deleteById(id);

		}

	}

	@Override	
	public void saveOrUpdate(WishListItem wishListItem) throws ServiceException {

		LOGGER.debug("Creating Customer");
		
		if(wishListItem.getId()!=null && wishListItem.getId()>0) {
			super.update(wishListItem);
		} else {			
		
			super.create(wishListItem);

		}
	}
}
