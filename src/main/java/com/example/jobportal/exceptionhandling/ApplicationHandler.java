package com.example.jobportal.exceptionhandling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.jobportal.utility.ErrorStructure;

@RestControllerAdvice
public class ApplicationHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<ObjectError> allErrors = ex.getAllErrors(); // UPCASTING : ObjectError is the SuperClass of
															// FieldError
		// which extends Error Class.

		Map<String, String> mapErrors = new HashMap<String, String>();

		for (ObjectError error : allErrors) {
			FieldError fieldError = (FieldError) error; // DOWNCASTING : ObjectError -> FieldError
			String message = fieldError.getDefaultMessage(); /** VALUE **/
			String field = fieldError.getField(); /** KEY **/

			mapErrors.put(field, message);
		}
		return new ResponseEntity<Object>(mapErrors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorStructure<String>> UserNotFoundById(UserNotFoundException exp) {
		ErrorStructure<String> es = new ErrorStructure<String>();
		es.setStatusCode(HttpStatus.NOT_FOUND.value());
		es.setMessage(exp.getMessage()); // message what we throw in service
		es.setErrordata(" USER WITH GIVEN ID NOT PRESENT ");

		return new ResponseEntity<ErrorStructure<String>>(es, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<ErrorStructure<String>> CompanyNotFoundById(CompanyNotFoundException exp) {
		ErrorStructure<String> es = new ErrorStructure<String>();
		es.setStatusCode(HttpStatus.NOT_FOUND.value());
		es.setMessage(exp.getMessage()); // message what we throw in service
		es.setErrordata(" COMPANY WITH GIVEN ID NOT PRESENT ");

		return new ResponseEntity<ErrorStructure<String>>(es, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(IllegalAccessException.class)
	public ResponseEntity<ErrorStructure<String>> illegalAccessExcp(IllegalAccessException exp) {
		ErrorStructure<String> es = new ErrorStructure<String>();
		es.setStatusCode(HttpStatus.NOT_FOUND.value());
		es.setMessage(exp.getMessage()); // message what we throw in service
		es.setErrordata(" USER NOT AUTHORISED FOR THIS OPERATION");

		return new ResponseEntity<ErrorStructure<String>>(es, HttpStatus.NOT_FOUND);

	}

}
