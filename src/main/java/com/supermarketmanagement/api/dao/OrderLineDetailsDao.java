package com.supermarketmanagement.api.dao;

import java.util.List;

import com.supermarketmanagement.api.Model.Custom.CommonListResponse;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.OrderLineItemDetailsDto;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;

public interface OrderLineDetailsDao {
	
	List<OrderLineItemDetailsModel> findByOrderLineId(List<Long> id);
	
	OrderLineItemDetailsModel findByOrderLineId(Long orderLineId);

	CommonListResponse<?> getOrderLineDetails();

	OrderLineItemDetailsDto getOrderLineDetailsById(Long lineid);

}
