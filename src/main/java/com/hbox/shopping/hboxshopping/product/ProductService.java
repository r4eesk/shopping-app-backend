package com.hbox.shopping.hboxshopping.product;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hbox.shopping.hboxshopping.product.Product.Category;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	private Pageable getPaging(Integer pageNo, Integer pageSize, String sortBy) {
		// System.out.println(sortBy);
		Pageable paging;
		if (sortBy.equals("relevance")) {
			paging = PageRequest.of(pageNo, pageSize);
		} else if (sortBy.equals("price-low")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("price"));
		} else if (sortBy.equals("price-high")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("price").descending());
		} else if (sortBy.equals("name")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("title"));
		} else {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		}
		// System.out.println(paging);
		return paging;
	}

	public ProductPage getAllProducts(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = getPaging(pageNo, pageSize, sortBy);

		Page<Product> pagedResult = productRepository.findAll(paging);

		if (pagedResult.hasContent()) {
			return new ProductPage(pagedResult.getContent(), pagedResult.getTotalPages());
		} else {
			return new ProductPage(new ArrayList<Product>(), 0);
		}
	}

	public Product getProductById(long id) {
		return productRepository.findById(id).get();
	}

	public ProductPage getProductsByCategory(Integer pageNo, Integer pageSize, String sortBy, Category category) {

		Pageable paging = getPaging(pageNo, pageSize, sortBy);
		Page<Product> pagedResult = productRepository.findByCategory(category, paging);

		if (pagedResult.hasContent()) {
			return new ProductPage(pagedResult.getContent(), pagedResult.getTotalPages());
		} else {
			return new ProductPage(new ArrayList<Product>(), 0);
		}
	}

	public Long addProduct(Product product) {
		productRepository.save(product);
		return product.getId();
	}

	public void insertRandom() {
		Random random = new Random();
		for (int i = 1; i <= 500; i++) {
			String title = random.ints(97, 123).limit(10)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
			String short_description = random.ints(97, 123).limit(100)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
			String description = random.ints(97, 123).limit(1000)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
			Double price = Math.random() * 1000;
			price = (double) Math.round(price * 100);
			price = price / 100;
			int quantity = (int) (Math.random() * 30);
			int popularity = (int) (Math.random() * 100);
			Product product = new Product(i, title, short_description, description, price, quantity, popularity,
					Category.values()[new Random().nextInt(Category.values().length)]);
			productRepository.save(product);
		}
	}

}
