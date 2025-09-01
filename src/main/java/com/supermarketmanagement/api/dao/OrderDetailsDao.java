package com.supermarketmanagement.api.dao;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderUpdateRequestDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Model.Entity.ProductModel;

public interface OrderDetailsDao {

	OrderDetailsModel findByOrderId(Long id);

	List<OrderLineItemDetailsModel> findByOrderLineId(List<Long> id);

	CustomerModel findByCustomerId(Long customerId);

	ProductModel findByProductId(Long productId);

	Object saveOrderDetails(OrderDetailsModel orderDetailsDao);

	OrderLineItemDetailsModel findByOrderLineId(Long orderLineId);

	Object getOrderDetailsById(Long orderid);

}
