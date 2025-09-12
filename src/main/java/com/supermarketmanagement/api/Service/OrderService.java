package com.supermarketmanagement.api.Service;

import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.UpdateOrderLineItemsDto;

public interface OrderService {

	Object placeOrder(OrderRequestDto requestDto);

	Object updatePlacedOrder(UpdateOrderLineItemsDto updaterequestDto);

}
