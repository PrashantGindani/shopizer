package com.salesmanager.core.model.wishlist;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;


@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "WISHLIST_ITEM")
public class WishListItem extends SalesManagerEntity<Long, WishListItem> implements Auditable, Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "WSH_LST_ITEM_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "WSH_LST_ITM_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@JsonIgnore
	@ManyToOne(targetEntity = Customer.class)
	@JoinColumn(name = "CUST_ID", nullable = false)
	private Customer customer;

	@Embedded
	private AuditSection auditSection = new AuditSection();

	@Deprecated //Use sku
	@Column(name="PRODUCT_ID", nullable=false) 
    private Long productId;
	
	//SKU
	@Column(name="SKU", nullable=true) 
	private String sku;

	@JsonIgnore
	@Transient
	private boolean productVirtual;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "wishListItem")
	private Set<WishListAttributeItem> attributes = new HashSet<WishListAttributeItem>();
	
	@Column(name="PRODUCT_VARIANT", nullable=true)
	private Long variant;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku", referencedColumnName = "sku", insertable = false, updatable = false)
	private Product product;

	@JsonIgnore
	@Transient
	private boolean obsolete = false;




	public WishListItem(Customer customer, Product product) {
		this(product);
		this.customer = customer;
	}

	public WishListItem(Product product) {
		this.product = product;
		this.productId = product.getId();
		this.setSku(product.getSku());
		this.productVirtual = product.isProductVirtual();
	}

	/** remove usage to limit possibility to implement bugs, would use constructors above to make sure all needed attributes are set correctly **/
	@Deprecated
	public WishListItem() {
	}

	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

	@Override
	public void setAuditSection(AuditSection audit) {
		this.auditSection = audit;

	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

	public void setAttributes(Set<WishListAttributeItem> attributes) {
		this.attributes = attributes;
	}

	public Set<WishListAttributeItem> getAttributes() {
		return attributes;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void addAttributes(WishListAttributeItem wishListAttributeItem)
	{
	    this.attributes.add(wishListAttributeItem);
	}

	public void removeAttributes(WishListAttributeItem wishListAttributeItem)
	{
	    this.attributes.remove(wishListAttributeItem);
	}

	public void removeAllAttributes(){
		this.attributes.removeAll(Collections.EMPTY_SET);
	}

	public boolean isObsolete() {
		return obsolete;
	}

	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}


	public boolean isProductVirtual() {
		return productVirtual;
	}

	public void setProductVirtual(boolean productVirtual) {
		this.productVirtual = productVirtual;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getVariant() {
		return variant;
	}

	public void setVariant(Long variant) {
		this.variant = variant;
	}

}

