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

	public Payment(Long id, Double amount,PaymentStatus status) {
		super();
		this.id = id;
		this.amount = amount;
		this.status=status;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_sequence")
	@SequenceGenerator(name = "payment_sequence", sequenceName = "payment_sequence", allocationSize = 1)

	private Long id;
	private Double amount;
	private PaymentStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
