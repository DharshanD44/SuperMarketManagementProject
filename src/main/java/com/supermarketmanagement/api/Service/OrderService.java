package com.supermarketmanagement.api.Service;

import com.supermarketmanagement.api.Model.Custom.ResponseMessage;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderUpdateRequestDto;

public interface OrderService {

	ResponseMessage placeOrder(OrderRequestDto requestDto);

	Object updatePlacedOrder(OrderUpdateRequestDto updaterequestDto);

}
