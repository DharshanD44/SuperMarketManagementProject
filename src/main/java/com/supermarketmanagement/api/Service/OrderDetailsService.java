package com.supermarketmanagement.api.Service;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.CommonMessageResponse;

public interface OrderDetailsService {
	
	Object updateOrderStatus(String status,Long id);
	
	Object getOrderListDetails(CommonListRequestModel commonListRequestModel);

	Object assignOrderToSupplier(Long orderid, Long supplierid);

	CommonMessageResponse getOrderDetailsById(Long orderid);

}
