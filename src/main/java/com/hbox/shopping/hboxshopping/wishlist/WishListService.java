package com.hbox.shopping.hboxshopping.wishlist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hbox.shopping.hboxshopping.product.Product;
import com.hbox.shopping.hboxshopping.product.ProductRepository;
import com.hbox.shopping.hboxshopping.user.User;
import com.hbox.shopping.hboxshopping.user.UserRepository;

@Service
public class WishListService {

	@Autowired
	private WishListRepository wishListRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	public WishList getWishListByUserId(String userId) {

		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found");
		}
		WishList wishList = wishListRepository.findByUser(user);
		return wishList == null ? new WishList(1, user, new ArrayList<Product>()) : wishList;
	}

	public WishList addtoWishlist(String userId, Long productId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found");
		}

		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product Not Found");
		}

		WishList wishList = wishListRepository.findByUser(user);
		if (wishList == null) {
			wishList = new WishList();
			wishList.setUser(user);
			wishList.setProducts(new ArrayList<Product>());
		}

		List<Product> wishListProducts = wishList.getProducts();
		wishListProducts.add(product);
		wishList.setProducts(wishListProducts);
		wishListRepository.save(wishList);
		return wishList;
	}

	public boolean checkInWishlist(String userId, Long productId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			return false;
		}

		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			return false;
		}
		WishList wishList = wishListRepository.findByUser(user);
		if (wishList == null) {
			return false;
		}
		return wishList.getProducts().contains(product);
	}

	public WishList removeFromWishList(String userId, Long productId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found");
		}

		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product Not Found");
		}

		WishList wishList = wishListRepository.findByUser(user);
		if (wishList == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wishlist Not Found");
		}

		List<Product> wishListProducts = wishList.getProducts();
		wishListProducts.remove(product);
		wishList.setProducts(wishListProducts);
		wishListRepository.save(wishList);
		return wishList;
	}

}
