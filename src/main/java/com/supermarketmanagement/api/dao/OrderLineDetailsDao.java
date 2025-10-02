package com.supermarketmanagement.api.dao;

import java.util.List;
import java.util.Map;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderDetailsListDto;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.OrderLineItemDetailsDto;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;

public interface OrderLineDetailsDao {
	
	List<OrderLineItemDetailsModel> findByOrderLineId(List<Long> id);
	
	OrderLineItemDetailsModel findByOrderLineId(Long orderLineId);
	
	Map<String, Object> getOrderLineListDetails(CommonListRequestModel commonListRequestModel);

	OrderLineItemDetailsDto getOrderLineItemDetailsById(Long orderid);

}
