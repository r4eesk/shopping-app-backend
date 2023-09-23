package com.hbox.shopping.hboxshopping.order;

import java.util.Map;

import com.hbox.shopping.hboxshopping.address.Address;
import com.hbox.shopping.hboxshopping.payment.Payment;
import com.hbox.shopping.hboxshopping.user.User;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	public Order() {
		super();
	}

	public Order(Long id, Payment payment, User user, Map<Long, Integer> products, Status status, Address address) {
		super();
		this.id = id;
		this.payment = payment;
		this.user = user;
		this.products = products;
		this.status = status;
		this.address = address;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
	@SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
	private Long id;

	@OneToOne
	private Payment payment;

	@ManyToOne
	private User user;

	@ElementCollection
	private Map<Long, Integer> products;

	private Status status;

	@ManyToOne
	private Address address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	enum Status {
		ORDER_PLACED, PACKED, SHIPPED, DELIVERED, CANCELLED
	}

}
