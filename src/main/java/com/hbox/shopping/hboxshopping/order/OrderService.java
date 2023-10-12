package com.hbox.shopping.hboxshopping.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hbox.shopping.hboxshopping.address.Address;
import com.hbox.shopping.hboxshopping.address.AddressRepository;
import com.hbox.shopping.hboxshopping.cart.Cart;
import com.hbox.shopping.hboxshopping.cart.CartRepository;
import com.hbox.shopping.hboxshopping.cart.CartService;
import com.hbox.shopping.hboxshopping.order.Order.Status;
import com.hbox.shopping.hboxshopping.payment.Payment;
import com.hbox.shopping.hboxshopping.payment.PaymentRepository;
import com.hbox.shopping.hboxshopping.payment.PaymentStatus;
import com.hbox.shopping.hboxshopping.product.Product;
import com.hbox.shopping.hboxshopping.product.ProductRepository;
import com.hbox.shopping.hboxshopping.user.User;
import com.hbox.shopping.hboxshopping.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartService cartService;

	public List<Order> getOrdersByUser(String userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null)
			return new ArrayList<Order>();
		return orderRepository.findByUser(user);
	}

	@Transactional
	public Order createNewOrder(String userId, Long addressId) {

		Order order = new Order();
		User user = userRepository.findById(userId).orElse(null);
		Cart cart = cartRepository.findByUser(user);
		
		if (!user.getUserId().equals(userId))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");

		Map<Long, Integer> cartProducts = cart.getProducts();
		Map<Long, Integer> orderProducts = new HashMap<Long, Integer>();
		cartProducts.forEach((productId, quantity) -> {
			Product product = productRepository.findById(productId).orElse(null);
			if (product == null)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product");
			if (product.getQuantity() < quantity)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is not available");
			product.setQuantity(product.getQuantity() - quantity);
			orderProducts.put(productId, quantity);
			productRepository.save(product);

		});

		Payment payment = new Payment();
		payment.setAmount(cartService.getTotalValue(userId).total());
		payment.setStatus(PaymentStatus.SUCCESS);
		paymentRepository.save(payment);

		Address address = addressRepository.findById(addressId).orElse(null);
		if (address == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid address");

		order.setPayment(payment);
		order.setProducts(orderProducts);
		order.setAddress(address);
		order.setUser(user);
		order.setStatus(Status.ORDER_PLACED);

		cartRepository.deleteById(cart.getId());

		orderRepository.save(order);

		return order;
	}
	

	@Transactional
	public Order cancelOrder(Long orderId) {

		Order order = orderRepository.findById(orderId).orElse(null);
		Map<Long, Integer> products = order.getProducts();
		
		products.forEach((productId,quantity) -> {
			Product product = productRepository.findById(productId).orElse(null);
			if(product!=null) {
				product.setQuantity(product.getQuantity()+quantity);
				productRepository.save(product);
			}
		});
		
		Payment payment = order.getPayment();
		payment.setStatus(PaymentStatus.REFUNDED);
		paymentRepository.save(payment);
		
		order.setStatus(Status.CANCELLED);
		orderRepository.save(order);
		return order;
		

	}

}
