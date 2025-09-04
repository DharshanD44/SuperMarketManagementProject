package com.supermarketmanagement.api.Model.Custom.Customer;

import java.util.List;

public class CustomerListResponse {

	private String status;
	List<CustomerListDto> customerListDtos;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CustomerListDto> getCustomerListDtos() {
		return customerListDtos;
	}

	public void setCustomerListDtos(List<CustomerListDto> customerListDtos) {
		this.customerListDtos = customerListDtos;
	}

	public CustomerListResponse(String status, List<CustomerListDto> customerListDtos) {
		super();
		this.status = status;
		this.customerListDtos = customerListDtos;
	}

	public CustomerListResponse() {
	}
	
	@Override
	public String toString() {
		return "Response [status=" + status + ", data=" + customerListDtos + "]";
	}

}
