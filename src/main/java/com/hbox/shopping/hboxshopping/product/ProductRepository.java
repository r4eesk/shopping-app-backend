package com.hbox.shopping.hboxshopping.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hbox.shopping.hboxshopping.product.Product.Category;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> findByCategory(Category category, Pageable pageable);
	
	Page<Product> findByCategoryAndQuantityGreaterThan(Category category, int quantity, Pageable pageable);
	
	Page<Product> findByQuantityGreaterThan(int quantity, Pageable pageable);

}
