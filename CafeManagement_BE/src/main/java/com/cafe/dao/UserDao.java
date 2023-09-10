package com.cafe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafe.POJO.User;

@Repository
public interface UserDao extends JpaRepository<User, Long>{
	public User findByEmail(String email);
	
}
