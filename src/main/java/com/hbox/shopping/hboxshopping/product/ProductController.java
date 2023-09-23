package com.hbox.shopping.hboxshopping.product;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hbox.shopping.hboxshopping.product.Product.Category;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping
	public ProductPage getAllProducts(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		return productService.getAllProducts(pageNo, pageSize, sortBy);

	}

	@GetMapping("/{category}")
	public ProductPage getProductsByCategory(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@PathVariable Category category) {
		return productService.getProductsByCategory(pageNo, pageSize, sortBy, category);

	}

	@GetMapping("/id/{id}")
	public Product getProductById(@PathVariable long id) {
		return productService.getProductById(id);
	}

	@PostMapping
	public ResponseEntity<Object> addProduct(@RequestBody Product product) {
		Long productId = productService.addProduct(product);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{productId}").buildAndExpand(productId)
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PostMapping("/random")
	public ResponseEntity<Object> insertRandom() {
		productService.insertRandom();
		return ResponseEntity.ok().build();
	}

}
