package com.supermarketmanagement.api.Service;

import com.supermarketmanagement.api.Model.Custom.ResponseData;
import com.supermarketmanagement.api.Model.Custom.ResponseMessage;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListResponse;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;

import java.util.List;


public interface CustomerService {

	CustomerListResponse getAllCustomerDetails();

	ResponseMessage addorUpdateCustomerDetails(CustomerListDto customerListDto);

	ResponseMessage deleteCustomerById(Long id);

	Object findCustomerDetailsById(Long id);
}
