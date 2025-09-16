package com.supermarketmanagement.api.Service;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;

public interface OrderDetailsService {
	
	Object updateOrderStatus(String status,Long id);
	
	Object getOrderListDetails(CommonListRequestModel commonListRequestModel);

	Object assignOrderToSupplier(Long orderid, Long supplierid);

}
