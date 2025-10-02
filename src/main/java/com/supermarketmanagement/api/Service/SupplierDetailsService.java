package com.supermarketmanagement.api.Service;

import java.util.Map;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListRequest;
import com.supermarketmanagement.api.Model.Custom.SupplierDetails.SupplierListDto;

public interface SupplierDetailsService {

	Object getSuppliersDetails(CustomerListRequest commonListRequestModel);

	Object getAssignedOrderDetails(Long supplierId);

	Object saverUpdateSupplierDetails(SupplierListDto supplierListDto);

	Object deleteSupplierById(Long supplierid);

	Object activateOrInactivate(Boolean status, Long supplierid);

}
