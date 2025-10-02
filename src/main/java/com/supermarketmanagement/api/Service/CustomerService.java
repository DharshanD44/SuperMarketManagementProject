package com.supermarketmanagement.api.Service;

import com.supermarketmanagement.api.Model.Custom.CommonMessageResponse;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListRequest;

public interface CustomerService {

	CommonMessageResponse addorUpdateCustomerDetails(CustomerListDto customerListDto);

	CommonMessageResponse deleteCustomerById(Long id);

	Object getCustomerDetails(CustomerListRequest commonListRequestModel);

	Object findCustomerDetailsById(Long id);

	CommonMessageResponse activateOrInactivatedCustomer(Boolean customerStatus, Long customerid);
}
