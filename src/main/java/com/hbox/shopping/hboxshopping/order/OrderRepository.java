package com.hbox.shopping.hboxshopping.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hbox.shopping.hboxshopping.user.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUser(User user);

}
