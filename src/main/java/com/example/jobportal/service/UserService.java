package com.example.jobportal.service;

import org.springframework.http.ResponseEntity;

import com.example.jobportal.enums.UserRole;
import com.example.jobportal.requestdto.UserRequest;
import com.example.jobportal.responsedto.UserResponse;
import com.example.jobportal.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface UserService {

	public ResponseEntity<ResponseStructure<UserResponse>> insertUser(UserRequest userReq, UserRole role);
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId);
	public ResponseEntity<ResponseStructure<UserResponse>> updateUserById(@Valid UserRequest userReq, int userId);
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserById(int userId);
}
