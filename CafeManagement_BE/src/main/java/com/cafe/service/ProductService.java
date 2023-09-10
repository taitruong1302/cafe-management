package com.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cafe.POJO.Product;

public interface ProductService {
	
	ResponseEntity<String> addNewProduct(Map<String, String> requestMap);
	ResponseEntity<List<Product>> getAllProduct(String filterValue);
	ResponseEntity<String> updateProduct(Map<String, String> requestMap);
	ResponseEntity<String> deleteProduct(Long id);
	ResponseEntity<String> updateProductStatus(Map<String, String> requestMap);
	ResponseEntity<List<Product>> getByCategory(Long id);
	ResponseEntity<Product> getProductById(Long id);
}
