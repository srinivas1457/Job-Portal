package com.example.jobportal.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jobportal.enums.BusinessType;
import com.example.jobportal.exceptionhandling.UserNotFoundException;
import com.example.jobportal.requestdto.CompanyRequest;
import com.example.jobportal.responsedto.CompanyResponse;
import com.example.jobportal.service.CompanyService;
import com.example.jobportal.utility.ResponseStructure;

import jakarta.validation.Valid;


@RestController
public class CompanyController {
	
	@Autowired
	CompanyService compService;
	
	
	@PostMapping("/users/{userId}/BusinessType/{buss}/companies")  
	public ResponseEntity<ResponseStructure<CompanyResponse>> inserUser(@RequestBody @Valid CompanyRequest userReq,@PathVariable int userId,@PathVariable BusinessType buss)
	{
		
		 return compService.insertCompany(userReq,buss,userId);
		
	}
	


	
	
}
