package com.hbox.shopping.hboxshopping.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hbox.shopping.hboxshopping.user.User;

public interface WishListRepository extends JpaRepository<WishList, Long> {
	WishList findByUser(User user);
}
