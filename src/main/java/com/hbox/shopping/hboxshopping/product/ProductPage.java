package com.hbox.shopping.hboxshopping.product;

import java.util.List;

public class ProductPage {

	public ProductPage() {
		super();
	}

	public ProductPage(List<Product> product, int pages) {
		super();
		this.product = product;
		this.pages = pages;
	}

	private List<Product> product;
	private int pages;

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

}
