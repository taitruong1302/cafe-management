package com.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cafe.POJO.User;

public interface UserService {
	
	ResponseEntity<String> signUp(Map<String, String> requestMap);
	ResponseEntity<String> login(Map<String, String> requestMap);
	ResponseEntity<List<User>> getAllUser();
	ResponseEntity<String> update(Map<String, String> requestMap);
	ResponseEntity<String> checkToken();
	ResponseEntity<String> changePassword(Map<String, String> requestMap);
	ResponseEntity<String> forgotPassword(Map<String, String> requestMap);
}
