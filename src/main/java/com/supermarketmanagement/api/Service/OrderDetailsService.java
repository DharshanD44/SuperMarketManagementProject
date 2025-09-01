package com.supermarketmanagement.api.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderUpdateRequestDto;

public interface OrderDetailsService {

	Object placeOrder(OrderRequestDto requestDto);

	Object updatePlacedOrder(OrderUpdateRequestDto updaterequestDto);

	Object updateOrderStatus(String status,Long id);

	Object updateLineOrderLineStatus(String status, List<Long> id);

	Object getOrderDetailsById(Long orderid);

}
