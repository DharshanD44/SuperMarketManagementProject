package com.supermarketmanagement.api.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderDetailsListDto;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;


public interface OrderDetailsDao {

	OrderDetailsModel findByOrderId(Long id);

	Object saveOrderDetails(OrderDetailsModel orderDetailsDao);

	Object getOrderDetailsById(Long orderid);

	Map<String, Object> getOrderListDetails(CommonListRequestModel commonListRequestModel);

}
