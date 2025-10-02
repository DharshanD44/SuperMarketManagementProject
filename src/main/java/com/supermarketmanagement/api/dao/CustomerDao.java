package com.supermarketmanagement.api.dao;
import java.util.List;
import java.util.Map;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListRequest;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;

public interface CustomerDao {
	
	CustomerModel findByCustomerId(Long customerId);

	void saveCustomer(CustomerModel entity);

	Map<String, Object> getCustomerDetails(CustomerListRequest commonListRequestModel);

	SuperMarketCode find(Class<SuperMarketCode> class1, String string);

	CustomerListDto findCustomerDetailsById(Long customerid);
}
