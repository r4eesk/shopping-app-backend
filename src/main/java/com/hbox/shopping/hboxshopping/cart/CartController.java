package com.hbox.shopping.hboxshopping.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping("/{userId}")
	public Cart getCartByUserId(@PathVariable String userId) {
		Cart cart = cartService.getCartByUserId(userId);
		if (cart == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found");
		return cart;
	}

	@PostMapping("/add/{userId}/{productId}")
	public ResponseEntity<String> addToCart(@PathVariable String userId, @PathVariable Long productId,
			@RequestParam Integer quantity) {
		Cart cart = cartService.addtoCart(userId, productId, quantity);
		if (cart == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id or product");

		return ResponseEntity.ok("Created");
	}

	@PutMapping("/remove/{userId}/{productId}")
	public ResponseEntity<Cart> removeFromCart(@PathVariable String userId, @PathVariable Long productId) {
		Cart cart = cartService.removeFromCart(userId, productId);
		if (cart == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id or product");

		return ResponseEntity.ok(cart);
	}

	@PutMapping("/update/{userId}/{productId}")
	public ResponseEntity<Cart> updateQuantityInCart(@PathVariable String userId, @PathVariable Long productId,
			@RequestParam Integer quantity) {
		Cart cart = cartService.updateQuantityInCart(userId, productId, quantity);
		if (cart == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid update");

		return ResponseEntity.ok(cart);
	}

	@GetMapping("/isincart/{userId}/{productId}")
	public IsIncart isInCart(@PathVariable String userId, @PathVariable Long productId) {
		return new IsIncart(cartService.checkInCart(userId, productId));
	}

	@GetMapping("/total/{userId}")
	public Total getTotalvalue(@PathVariable String userId) {
		return cartService.getTotalValue(userId);
	}

}

record IsIncart(Boolean IsInCart) {
}
