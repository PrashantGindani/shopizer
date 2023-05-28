package com.salesmanager.shop.model.shoppingcart;

import java.io.Serializable;
import java.util.List;

import com.salesmanager.shop.model.catalog.product.attribute.ProductAttribute;

public class PersistableWishListItem  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String product;// or product sku (instance or product)
	private List<ProductAttribute> attributes;

	public List<ProductAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<ProductAttribute> attributes) {
		this.attributes = attributes;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}


}
