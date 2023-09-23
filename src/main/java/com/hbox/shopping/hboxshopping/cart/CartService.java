package com.hbox.shopping.hboxshopping.cart;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbox.shopping.hboxshopping.product.Product;
import com.hbox.shopping.hboxshopping.product.ProductRepository;
import com.hbox.shopping.hboxshopping.user.User;
import com.hbox.shopping.hboxshopping.user.UserRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	public Cart getCartByUserId(String userId) {

		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			return null;
		}
		Cart cart = cartRepository.findByUser(user);
		return cart == null ? new Cart(0, user, new HashMap<Long, Integer>()) : cart;
	}

	public Cart addtoCart(String userId, Long productId, Integer quantity) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			return null;
		}

		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			return null;
		}

		Cart cart = cartRepository.findByUser(user);
		if (cart == null) {
			cart = new Cart();
			cart.setUser(user);
			cart.setProducts(new HashMap<Long, Integer>());
		}

		Map<Long, Integer> cartProducts = cart.getProducts();
		cartProducts.put(productId, quantity);
		cart.setProducts(cartProducts);

		cartRepository.save(cart);
		return cart;
	}

	public Cart removeFromCart(String userId, Long productId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			return null;
		}

		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			return null;
		}

		Cart cart = cartRepository.findByUser(user);
		if (cart == null) {
			return null;
		}

		Map<Long, Integer> cartProducts = cart.getProducts();
		cartProducts.remove(productId);
		cart.setProducts(cartProducts);

		cartRepository.save(cart);
		return cart;
	}

	public Cart updateQuantityInCart(String userId, Long productId, Integer quantity) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			return null;
		}

		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			return null;
		}

		Cart cart = cartRepository.findByUser(user);
		if (cart == null) {
			return null;
		}
		if (quantity < 1)
			return null;

		Map<Long, Integer> cartProducts = cart.getProducts();
		cartProducts.replace(productId, quantity);
		cart.setProducts(cartProducts);

		cartRepository.save(cart);
		return cart;
	}

	public boolean checkInCart(String userId, Long productId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			return false;
		}

		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			return false;
		}
		Cart cart = cartRepository.findByUser(user);
		if (cart == null) {
			return false;
		}
		return cart.getProducts().containsKey(productId);
	}

	public Total getTotalValue(String userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			return new Total(0.0, 0.0, 0.0, 0.0, 0.0);
		}
		Cart cart = cartRepository.findByUser(user);
		if (cart == null) {
			return new Total(0.0, 0.0, 0.0, 0.0, 0.0);
		}
		Double total = cart.getProducts().entrySet().stream()
				.mapToDouble(entry -> productRepository.findById(entry.getKey()).get().getPrice() * entry.getValue())
				.reduce(0.0, (x, y) -> x + y);
		Double delivery = 0.00;
		Double tax = total * 9 / 100;
		Double discount = total * 20 / 100;
		if (total < 1000 && total > 0)
			delivery = 100.00;

		tax = (double) Math.round(tax * 100);
		tax = tax / 100;

		discount = (double) Math.round(discount * 100);
		discount = discount / 100;

		Double totalAmt = total + tax - discount + delivery;

		totalAmt = (double) Math.round(totalAmt * 100);
		totalAmt = totalAmt / 100;

		return new Total(total, tax, discount, delivery, totalAmt);
	}

}
