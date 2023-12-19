package com.example.jobportal.exceptionhandling;

public class CompanyNotFoundException extends RuntimeException {
	
	private String mess;

	public CompanyNotFoundException(String mess) {
		
		this.mess = mess;
	}

	@Override
	public String getMessage() {
		return mess;
	}
	

}
