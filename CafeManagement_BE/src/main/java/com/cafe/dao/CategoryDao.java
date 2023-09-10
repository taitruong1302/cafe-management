package com.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafe.POJO.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long>{
	
	@Query("select c from Category c where c.id in (select p.category from Product p where p.status = 'true')")
	public List<Category> getAllCategory();
}
