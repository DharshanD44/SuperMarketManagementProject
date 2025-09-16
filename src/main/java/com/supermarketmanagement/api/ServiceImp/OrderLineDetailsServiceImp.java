package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.CommonResponse;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Service.OrderLineDetailsService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.OrderLineDetailsDao;
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderLineDetailsServiceImp implements OrderLineDetailsService {

	@Autowired
	private OrderLineDetailsDao orderLineDetailsDao;
	
	@Autowired
	private SuperMarketCodeDao codeDao;

	@Override
	public Object updateLineOrderLineStatus(String status, List<Long> id) {
		List<OrderLineItemDetailsModel> orderLineItemDetailsModel = orderLineDetailsDao.findByOrderLineId(id);
		CommonResponse responseMessage= new CommonResponse();
		SuperMarketCode orderstatus = codeDao.findByDescription(status);
		if(orderstatus==null) {
			responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
			responseMessage.setMessage(WebServiceUtil.INVALID_ORDER_STATUS);
			return responseMessage;
		}
		orderstatus.getCode();
		switch (status.toUpperCase()) {
		case "PACKED":
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_PACKED);
			break;
		case "DELIVERED":
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_DELIVERED);
			break;
		case "SHIPPED":
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_SHIPPED);
			break;
		case "CANCELLED":
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_CANCELLED);
			break;
		}
		for (OrderLineItemDetailsModel items : orderLineItemDetailsModel) {
			items.setOrderStatus(orderstatus);
			items.setUpdateDate(LocalDateTime.now());
		}
		responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
		return responseMessage;
	}

	@Override
	public Map<String, Object> getOrderLineListDetails(CommonListRequestModel commonListRequestModel) {
		return orderLineDetailsDao.getOrderLineListDetails(commonListRequestModel);
	}
}
