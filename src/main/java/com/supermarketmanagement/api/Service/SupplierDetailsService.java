package com.supermarketmanagement.api.Service;

import java.util.Map;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.SupplierDetails.SupplierListDto;

public interface SupplierDetailsService {

	Map<String, Object> getSuppliersDetails(CommonListRequestModel commonListRequestModel);

	Object getAssignedOrderDetails(Long supplierId);

	Object addSupplierDetails(SupplierListDto supplierListDto);

}
