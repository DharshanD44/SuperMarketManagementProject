package com.supermarketmanagement.api.Exceptionhandler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
//public class ProductExceptionHandler {
//	
//	@ExceptionHandler(ProductNotFoundException.class)
//	public ResponseEntity<String> handlingProductNotFound(ProductNotFoundException ex){
//		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
//	}
//
//}
