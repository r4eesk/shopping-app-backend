package com.hbox.shopping.hboxshopping.cart;

import java.util.Map;

import com.hbox.shopping.hboxshopping.user.User;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Cart {

	public Cart() {
		super();
	}

	public Cart(long id, User user, Map<Long, Integer> products) {
		super();
		this.id = id;
		this.user = user;
		this.products = products;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_sequence")
	@SequenceGenerator(name = "cart_sequence", sequenceName = "cart_sequence", allocationSize = 1)
	private long id;

	@OneToOne
	private User user;

	@ElementCollection
	private Map<Long, Integer> products;

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

	public Map<Long, Integer> getProducts() {
		return products;
	}

	public void setProducts(Map<Long, Integer> products) {
		this.products = products;
	}

}
