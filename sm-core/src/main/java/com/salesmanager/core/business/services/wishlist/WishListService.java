package com.salesmanager.core.business.services.wishlist;


import java.util.List;
import java.util.Set;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shipping.ShippingProduct;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import com.salesmanager.core.model.wishlist.WishListItem;

public interface WishListService extends SalesManagerEntityService<Long, WishListItem> {

	/**
	 * Populates a ShoppingCartItem from a Product and attributes if any. Calculate price based on availability
	 * 
	 * @param product
	 * @return
	 * @throws ServiceException
	 */
	WishListItem populateWishListItem(Product product, MerchantStore store) throws ServiceException;

	/**
	 * Removes a shopping cart item
	 * @param item
	 */
	void deleteWishListItem(Long id);

	void saveOrUpdate(WishListItem wishListItem) throws ServiceException;
}