package com.cafe.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cafe.JWT.JwtFilter;
import com.cafe.POJO.Category;
import com.cafe.POJO.Product;
import com.cafe.constants.CafeConstants;
import com.cafe.dao.CategoryDao;
import com.cafe.dao.ProductDao;
import com.cafe.service.ProductService;
import com.cafe.utils.CafeUtils;
import com.google.common.base.Strings;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	JwtFilter jwtFilter;

	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		try {
			try {
				if (jwtFilter.isAdmin()) {
					if (validateProductMap(requestMap, false)) {
						productDao.save(getProductFromMap(requestMap, false));
						return CafeUtils.getResponseEntity("Product Added Successfully.", HttpStatus.OK);
					} else {
						return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
					}
				} else {
					return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
		Category category = new Category();
		category.setId(Long.parseLong(requestMap.get("category_id")));

		Product product = new Product();
		if (isAdd) {
			product.setId(Long.parseLong(requestMap.get("id")));
		} else {
			product.setStatus("true");
		}
		product.setCategory(category);
		product.setName(requestMap.get("name"));
		product.setDescription(requestMap.get("description"));
		product.setPrice(Double.parseDouble(requestMap.get("price")));
		return product;
	}

	private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
		if (requestMap.containsKey("name")) {
			if (requestMap.containsKey("id") && validateId) {
				return true;
			} else if (!validateId) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ResponseEntity<List<Product>> getAllProduct(String filterValue) {
		try {
			if (!Strings.isNullOrEmpty(filterValue)) {
				return new ResponseEntity<List<Product>>(productDao.findAll(), HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Product>>(productDao.findAll(), HttpStatus.OK);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
		try {
			if (jwtFilter.isAdmin()) {
				if (validateProductMap(requestMap, true)) {
					Optional<Product> productOptional = productDao.findById(Long.parseLong(requestMap.get("id")));
					if (!productOptional.isEmpty()) {
						Product product = getProductFromMap(requestMap, true);
						product.setStatus(productOptional.get().getStatus());
						productDao.save(product);
						return CafeUtils.getResponseEntity("Product updated successfully.", HttpStatus.OK);
					} else {
						return CafeUtils.getResponseEntity("Product does not exist.", HttpStatus.OK);
					}
				} else {
					return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
				}
			} else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> deleteProduct(Long id) {
		try {
			if (jwtFilter.isAdmin()) {
				Optional<Product> productOptional = productDao.findById(id);
				if (!productOptional.isEmpty()) {
					productDao.deleteById(id);
					return CafeUtils.getResponseEntity("Product deleted sucessfully", HttpStatus.OK);
				} else {
					return CafeUtils.getResponseEntity("Product does not exist.", HttpStatus.BAD_REQUEST);
				}
			} else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateProductStatus(Map<String, String> requestMap) {
		try {
			if (jwtFilter.isAdmin()) {
				Optional<Product> productOptional = productDao.findById(Long.parseLong(requestMap.get("id")));
				if (!productOptional.isEmpty()) {
					Product product = productOptional.get();
					product.setStatus(requestMap.get("status"));
					productDao.save(product);
					return CafeUtils.getResponseEntity("Product updated status sucessfully", HttpStatus.OK);
				} else {
					return CafeUtils.getResponseEntity("Product does not exist", HttpStatus.BAD_REQUEST);
				}
			} else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Product>> getByCategory(Long id) {
		try {
			Optional<Category> categoryOptional = categoryDao.findById(id);
			return new ResponseEntity<List<Product>>(productDao.getByCategory(categoryOptional.get()), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Product> getProductById(Long id) {
		try {
			return new ResponseEntity<Product>(productDao.getById(id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new Product(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
