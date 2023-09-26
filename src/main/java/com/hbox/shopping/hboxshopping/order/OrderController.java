package com.hbox.shopping.hboxshopping.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/new/{userId}")
	public Order createNewOrder(@PathVariable String userId, @RequestParam Long addressId) {
		return orderService.createNewOrder(userId, addressId);
	}

	@GetMapping("/get/{userId}")
	public List<Order> getOrderByUser(@PathVariable String userId) {
		return orderService.getOrdersByUser(userId);
	}
}
