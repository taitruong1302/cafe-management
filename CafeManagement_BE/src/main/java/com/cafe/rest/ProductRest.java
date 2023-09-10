package com.cafe.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe.POJO.Product;

@RequestMapping(path = "/product")
public interface ProductRest {

	@PostMapping(path = "/add")
	ResponseEntity<String> addNewProduct(@RequestBody(required = true) Map<String, String> requestMap);
	
	@GetMapping(path = "/get")
	ResponseEntity<List<Product>> getAllProduct(@RequestParam(required = false) String filterValue);
	
	@PostMapping(path = "/update")
	ResponseEntity<String> updateProduct(@RequestBody Map<String, String> requestMap);
	
	@PostMapping(path = "/delete/{id}")
	ResponseEntity<String> deleteProduct(@PathVariable Long id);
	
	@PostMapping(path = "/updateStatus")
	ResponseEntity<String> updateProductStatus(@RequestBody Map<String, String> requestMap);
	
	@GetMapping(path = "/getByCategory/{id}")
	ResponseEntity<List<Product>> getByCategory(@PathVariable Long id);
	
	@GetMapping(path = "/getById/{id}")
	ResponseEntity<Product> getProductById(@PathVariable Long id);
}
