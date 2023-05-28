package com.salesmanager.shop.store.controller.wishlist.facade;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.ReadableProductList;

public interface WishListFacade {

	/**
	 * Removes a shopping cart item
	 * @param cartCode
	 * @param sku
	 * @param merchant
	 * @param language
	 * @param returnCart
	 * @return ReadableShoppingCart or NULL
	 * @throws Exception
	 */
	void removeWishListItem(String user_nick, String sku, MerchantStore merchant, Language language) throws Exception;

	/**
	 * Finds a shopping cart item
	 * @param cartCode
	 * @param sku
	 * @param merchant
	 * @param language
	 * @param returnCart
	 * @return ReadableShoppingCart or NULL
	 * @throws Exception
	 */
	void findOrAddWishListItem(String user_nick, String sku, MerchantStore merchant, Language language) throws Exception;
	
	/**
	 * Gets all wishlist products item
	 * @param cartCode
	 * @param sku
	 * @param merchant
	 * @param language
	 * @param returnCart
	 * @return ReadableShoppingCart or NULL
	 * @throws Exception
	 */
	ReadableProductList getWishlistProducts(String user_nick, MerchantStore merchant, Language language, Integer page, Integer count) throws Exception;

}
