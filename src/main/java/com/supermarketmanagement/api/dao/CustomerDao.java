package com.supermarketmanagement.api.dao;
import java.util.List;

import com.supermarketmanagement.api.Model.Custom.Response;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListResponse;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;

public interface CustomerDao {

	List<CustomerListDto> getCustomerListDtos();
	
	CustomerModel findByCustomerId(Long customerId);

	void saveCustomer(CustomerModel entity);
}
