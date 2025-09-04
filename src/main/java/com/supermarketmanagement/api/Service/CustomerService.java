package com.supermarketmanagement.api.Service;

import com.supermarketmanagement.api.Model.Custom.Response;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListResponse;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;

import java.util.List;


public interface CustomerService {

	CustomerListResponse getAllCustomerDetails();

	Response addorUpdateCustomerDetails(CustomerListDto customerListDto);

	String deleteCustomerById(Long id);
}
