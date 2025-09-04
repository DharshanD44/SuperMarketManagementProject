package com.supermarketmanagement.api.Exceptionhandler;

import java.time.LocalDateTime;

public class ProductNotFoundException extends RuntimeException {

	private LocalDateTime timestamp;
	private String details;

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public ProductNotFoundException(LocalDateTime timestamp, String message, String details) {
        super(message);
		this.timestamp = timestamp;
		this.details = details;
	}

}
