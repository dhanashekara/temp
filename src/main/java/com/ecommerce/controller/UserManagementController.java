package com.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.UserDTO;
import com.ecommerce.exception.UserManagementException;
import com.ecommerce.service.UserService;

@RestController
@RequestMapping("/usermanagement")
public class UserManagementController {

	@Autowired
	UserService userService;

	//@PostMapping("/login")
	@GetMapping("/login")
	public ResponseEntity<String> login(@RequestHeader(value = "Username") String userName,
			@RequestHeader(value = "Password") String password) throws Exception {

		return new ResponseEntity<String>(userService.login(userName, password), HttpStatus.FOUND);

	}

	@PostMapping("/user")
	public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO user) throws UserManagementException {

		return new ResponseEntity<String>(userService.registerUser(user), HttpStatus.CREATED);

	}

	@PutMapping("/user")
	public ResponseEntity<String> updateUserProfile(@RequestBody UserDTO userDTO) throws UserManagementException {

		return new ResponseEntity<String>(userService.updateUserProfile(userDTO), HttpStatus.OK);
	}

	@DeleteMapping("/users/{userid}")
	public ResponseEntity<String> deleteUserProfile(@PathVariable(name = "userid") long userId)
			throws UserManagementException {
		return new ResponseEntity<String>(userService.deleteUserProfile(userId), HttpStatus.OK);

	}

	@GetMapping("/users/buyers")
	public ResponseEntity<Object> getBuyersList() {

		Object status = userService.getUserListByCategory("buyer");
		if (!status.equals("Users are not available"))
			return new ResponseEntity<Object>(status, HttpStatus.OK);
		else
			return new ResponseEntity<Object>("Buyers are not available", HttpStatus.NOT_FOUND);

	}

	@GetMapping("/users/sellers")
	public ResponseEntity<Object> getSellersList() {

		Object status = userService.getUserListByCategory("seller");
		if (!status.equals("Users are not available"))
			return new ResponseEntity<Object>(status, HttpStatus.OK);
		else
			return new ResponseEntity<Object>("Sellers are not available", HttpStatus.NOT_FOUND);
	}

}
