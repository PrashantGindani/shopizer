package com.salesmanager.core.business.repositories.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.wishlist.WishListAttributeItem;

public interface WishListAttributeRepository extends JpaRepository<WishListAttributeItem, Long> {


}
