package com.cafe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.POJO.Bill;

public interface BillDao extends JpaRepository<Bill, Long>{
	
	List<Bill> findByCreateBy(String createBy);

}
