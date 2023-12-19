package com.example.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jobportal.enums.UserRole;
import com.example.jobportal.exceptionhandling.UserNotFoundException;
import com.example.jobportal.requestdto.UserRequest;
import com.example.jobportal.responsedto.UserResponse;
import com.example.jobportal.serviceimpl.UserServiceImpl;
import com.example.jobportal.utility.ResponseStructure;

import jakarta.validation.Valid;






@RestController
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	
	
	
	@PostMapping("/userroles/{role}/users")  
	public ResponseEntity<ResponseStructure<UserResponse>> inserUser(@RequestBody @Valid UserRequest userReq,@PathVariable UserRole role)
	{
		
		 return userService.insertUser(userReq,role);
		
	}
	
	
	@GetMapping("/users/{userId}")  
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(@PathVariable int  userId) throws UserNotFoundException
	{
		
		 return userService.findUserById(userId);
		
	}
	
	@PutMapping("/users/{userId}")  
	public ResponseEntity<ResponseStructure<UserResponse>> updateUserById(@RequestBody @Valid UserRequest userReq,@PathVariable int userId) throws UserNotFoundException
	{
		
		 return userService.updateUserById(userReq,userId);
		
	}
	
	@DeleteMapping("/users/{userId}")  
	public ResponseEntity<ResponseStructure<UserResponse>> DeleteUserById(@PathVariable int  userId) throws UserNotFoundException
	{
		
		 return userService.deleteUserById(userId);
		
	}

	
	
}
