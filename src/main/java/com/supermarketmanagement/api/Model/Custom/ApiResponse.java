package com.supermarketmanagement.api.Model.Custom;

import org.springframework.http.HttpStatusCode;

public class ApiResponse<T> {

	private HttpStatusCode status;
	private String message;
	private T data;

	public HttpStatusCode getStatus() {
		return status;
	}

	public void setStatus(HttpStatusCode status) {
		this.status = status;
	}

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ApiResponse(HttpStatusCode status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public ApiResponse() {
	}

}
