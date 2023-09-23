package com.hbox.shopping.hboxshopping.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

	@Autowired
	private WishListService wishListService;

	@GetMapping("/{userId}")
	public WishList getWishListByUserId(@PathVariable String userId) {

		return wishListService.getWishListByUserId(userId);
	}

	@PostMapping("/{userId}/{productId}")
	public ResponseEntity<Object> addToWishList(@PathVariable String userId, @PathVariable Long productId) {
		WishList wishList = wishListService.addtoWishlist(userId, productId);

		return ResponseEntity.ok(wishList);
	}

	@PutMapping("/remove/{userId}/{productId}")
	public ResponseEntity<WishList> removeFromWishList(@PathVariable String userId, @PathVariable Long productId) {
		WishList wishList = wishListService.removeFromWishList(userId, productId);

		return ResponseEntity.ok(wishList);
	}

	@GetMapping("/isWishlisted/{userId}/{productId}")
	public IsWishListed checkInWishlist(@PathVariable String userId, @PathVariable Long productId) {
		return new IsWishListed(wishListService.checkInWishlist(userId, productId));
	}
}

record IsWishListed(Boolean isWishlisted) {
}
