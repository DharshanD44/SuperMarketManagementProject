package com.supermarketmanagement.api.dao;

import java.util.List;
import java.util.Map;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderDetailsListDto;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.OrderLineItemDetailsDto;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;

public interface OrderDetailsDao {

	OrderDetailsModel findByOrderId(Long id);

	Object saveOrderDetails(OrderDetailsModel orderDetailsDao);

	Map<String, Object> getOrderListDetails(CommonListRequestModel commonListRequestModel);

	OrderDetailsListDto getOrderDetailsById(Long orderid);

	List<OrderLineItemDetailsDto> findOrderLineItemStatus(Long id);

}
