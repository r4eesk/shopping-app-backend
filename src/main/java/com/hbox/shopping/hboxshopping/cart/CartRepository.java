package com.hbox.shopping.hboxshopping.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hbox.shopping.hboxshopping.user.User;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByUser(User user);

}
