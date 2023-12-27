package com.example.jobportal.exceptionhandling;

public class JobDataNotPresent extends RuntimeException {
	private String message;

	public JobDataNotPresent(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
