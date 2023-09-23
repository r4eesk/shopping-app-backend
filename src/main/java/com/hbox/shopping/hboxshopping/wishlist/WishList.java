package com.hbox.shopping.hboxshopping.wishlist;

import java.util.List;

import com.hbox.shopping.hboxshopping.product.Product;
import com.hbox.shopping.hboxshopping.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class WishList {

	public WishList() {
		super();
	}

	public WishList(long id, User user, List<Product> products) {
		super();
		this.id = id;
		this.user = user;
		this.products = products;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Wishlist_sequence")
	@SequenceGenerator(name = "Wishlist_sequence", sequenceName = "Wishlist_sequence", allocationSize = 1)
	private long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany
	@JoinColumn(name = "id")
	private List<Product> products;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
