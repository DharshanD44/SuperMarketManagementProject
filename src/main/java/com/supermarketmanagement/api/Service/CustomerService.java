package com.supermarketmanagement.api.Service;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.CommonResponse;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;

import java.util.List;
import java.util.Map;


public interface CustomerService {

	CommonResponse addorUpdateCustomerDetails(CustomerListDto customerListDto);

	CommonResponse deleteCustomerById(Long id);

	Map<String, Object> getCustomerDetails(CommonListRequestModel commonListRequestModel);
}
