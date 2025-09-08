package com.supermarketmanagement.api.Model.Custom.Customer;

import java.util.List;

public class CustomerListResponse {

	private String status;
	List<CustomerListDto> data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CustomerListDto> getData() {
		return data;
	}

	public void setData(List<CustomerListDto> data) {
		this.data = data;
	}
	
	public CustomerListResponse(String status, List<CustomerListDto> data) {
		super();
		this.status = status;
		this.data = data;
	}

	public CustomerListResponse() {
	}
	
	@Override
	public String toString() {
		return "Response [status=" + status + ", data=" + data + "]";
	}

}
