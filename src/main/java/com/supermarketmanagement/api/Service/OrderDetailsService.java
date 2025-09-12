package com.supermarketmanagement.api.Service;

import java.time.LocalDateTime;

import com.supermarketmanagement.api.Model.Custom.ApiResponse;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;

public interface OrderDetailsService {
	
	Object updateOrderStatus(String status,Long id);
	
	Object getOrderListDetails(CommonListRequestModel commonListRequestModel);

}
