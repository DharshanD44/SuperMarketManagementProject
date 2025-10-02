package com.supermarketmanagement.api.dao;

import java.util.Map;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListRequest;
import com.supermarketmanagement.api.Model.Entity.SuppliersModel;

public interface SupplierDetailsDao {

	Map<String, Object> getSuppliersDetails(CustomerListRequest commonListRequestModel);

	Object getAssignedOrderDetails(Long supplierId);

	SuppliersModel findBySupplierId(Long supplierid);

	void saveSupplierDetails(SuppliersModel supplierList);

	SuppliersModel findByEmail(String email);

	SuppliersModel findByMobile(Long mobileNumber);

}
