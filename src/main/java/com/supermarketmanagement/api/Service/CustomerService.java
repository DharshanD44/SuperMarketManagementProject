package com.supermarketmanagement.api.Service;

import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;

import java.util.List;


public interface CustomerService {

	List<CustomerListDto> getAllCustomerDetails();

	String addorUpdateCustomerDetails(CustomerListDto customerListDto);

	String deleteCustomerById(Long id);
}
