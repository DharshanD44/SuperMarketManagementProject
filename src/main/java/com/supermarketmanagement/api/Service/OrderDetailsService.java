package com.supermarketmanagement.api.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.supermarketmanagement.api.Model.Custom.ResponseMessage;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderDetailsListDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderListResponse;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderUpdateRequestDto;

public interface OrderDetailsService {
	
	Object updateOrderStatus(String status,Long id);
	
	Object getOrderDetailsById(Long orderid);

	OrderListResponse getOrderListDetails();

}
