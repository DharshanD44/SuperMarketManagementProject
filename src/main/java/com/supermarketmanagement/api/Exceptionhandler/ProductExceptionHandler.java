package com.supermarketmanagement.api.Exceptionhandler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.supermarketmanagement.api.Model.Custom.Product.ProductMessageDto;

@ControllerAdvice
public class ProductExceptionHandler {
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {
	    ErrorResponse error = new ErrorResponse(
	            ex.getTimestamp(),
	            ProductMessageDto.PRODUCT_ID_NOT_FOUND,
	            ex.getDetails()
	    );
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}	
}
