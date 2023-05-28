package com.salesmanager.core.business.repositories.catalog.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.catalog.product.Product;


public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

	
	@Query(value="select case when count(p.PRODUCT_ID)> 0 then 'true' else 'false' end"
			+ " from {h-schema}Product p join {h-schema}MERCHANT_STORE pm ON p.MERCHANT_ID = pm.MERCHANT_ID "
			+ " left join {h-schema}PRODUCT_VARIANT pinst ON pinst.PRODUCT_ID = p.PRODUCT_ID "
			+ " where pinst.sku=?1 or p.sku=?1 and pm.MERCHANT_ID=?2",nativeQuery = true)
	boolean existsBySku(String sku, Integer store);
	
	@Query(

			value = "select p.PRODUCT_ID from {h-schema}PRODUCT p join {h-schema}MERCHANT_STORE m ON p.MERCHANT_ID = m.MERCHANT_ID left join {h-schema}PRODUCT_VARIANT i ON i.PRODUCT_ID = p.PRODUCT_ID where p.SKU=?1 or i.SKU=?1 and m.MERCHANT_ID=?2",
			nativeQuery = true
	)
	List<Object> findBySku(String sku, Integer consultId);

}
