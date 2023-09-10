package com.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafe.POJO.Category;
import com.cafe.POJO.Product;


@Repository
public interface ProductDao extends JpaRepository<Product, Long>{
	
	public List<Product> getByCategory(Category category);
}
