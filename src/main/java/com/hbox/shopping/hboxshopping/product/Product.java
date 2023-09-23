package com.hbox.shopping.hboxshopping.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Product {
	public Product() {
		super();
	}

	public Product(long id, String title, String short_description, String description, Double price, int quantity,
			int popularity, Category category) {
		super();
		this.id = id;
		this.title = title;
		this.short_description = short_description;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.popularity = popularity;
		this.category = category;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
	@SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
	private long id;
	private String title;
	private String short_description;

	@Column(columnDefinition = "TEXT")
	private String description;
	private Double price;
	private int quantity;
	private int popularity;
	private Category category;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public enum Category {
		Clothing, Footwear, Mobiles;

	}

}
