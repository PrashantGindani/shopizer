package com.salesmanager.core.business.repositories.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import com.salesmanager.core.model.wishlist.WishListItem;

public interface WishListItemRepository extends JpaRepository<WishListItem, Long> {
  
  @Query("select i from WishListItem i left join fetch i.attributes ia where i.id = ?1")
  WishListItem findOne(Long id);
  
  @Modifying
  @Query("delete from WishListItem i where i.id = ?1")
  void deleteById(Long id);


}
