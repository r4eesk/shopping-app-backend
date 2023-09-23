package com.hbox.shopping.hboxshopping.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Payment {

	public Payment() {
		super();
	}

	public Payment(Long id, Double amount) {
		super();
		this.id = id;
		this.amount = amount;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_sequence")
	@SequenceGenerator(name = "payment_sequence", sequenceName = "payment_sequence", allocationSize = 1)

	private Long id;
	private Double amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
