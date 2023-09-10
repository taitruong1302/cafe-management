package com.cafe.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.POJO.Product;
import com.cafe.constants.CafeConstants;
import com.cafe.rest.ProductRest;
import com.cafe.service.ProductService;
import com.cafe.utils.CafeUtils;

@RestController
public class ProductRestImpl implements ProductRest{

	@Autowired
	ProductService productService;
	
	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		try {
			return productService.addNewProduct(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Product>> getAllProduct(String filterValue) {
		try {
			return productService.getAllProduct(filterValue);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<List<Product>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
		try {
			return productService.updateProduct(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> deleteProduct(Long id) {
		try {
			return productService.deleteProduct(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateProductStatus(Map<String, String> requestMap) {
		try {
			return productService.updateProductStatus(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Product>> getByCategory(Long id) {
		try {
			return productService.getByCategory(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Product> getProductById(Long id) {
		try {
			return productService.getProductById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new Product(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
