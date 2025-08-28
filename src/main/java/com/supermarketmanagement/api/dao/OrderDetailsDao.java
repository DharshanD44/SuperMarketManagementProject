package com.supermarketmanagement.api.dao;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderUpdateRequestDto;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;

public interface OrderDetailsDao {

	Object placeOrder(OrderRequestDto requestDto);

	OrderDetailsModel findByOrderId(Long id);

	Object updatePlacedOrder(OrderUpdateRequestDto updaterequestDto);

	List<OrderLineItemDetailsModel> findByOrderLineId(List<Long> id);

}
