package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarketmanagement.api.Model.Custom.CommonListResponse;
import com.supermarketmanagement.api.Model.Custom.ResponseData;
import com.supermarketmanagement.api.Model.Custom.ResponseMessage;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderStatusDto;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.OrderLineItemDetailsDto;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Service.OrderLineDetailsService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.OrderLineDetailsDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderLineDetailsServiceImp implements OrderLineDetailsService{
	
	@Autowired
	private OrderLineDetailsDao orderLineDetailsDao;

	@Override
	public Object updateLineOrderLineStatus(String status, List<Long> id) {
		List<OrderLineItemDetailsModel> orderLineItemDetailsModel = orderLineDetailsDao.findByOrderLineId(id);
		ResponseMessage responseMessage= new ResponseMessage();
		OrderStatusDto newStatus;
		switch (status.toUpperCase()) {
		case "PACKED":
			newStatus = OrderStatusDto.PACKED;
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_PACKED);
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			break;
		case "DELIVERED":
			newStatus = OrderStatusDto.DELIVERED;
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_DELIVERED);
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			break;
		case "SHIPPED":
			newStatus = OrderStatusDto.SHIPPED;
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_SHIPPED);
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			break;
		case "CANCELLED":
			newStatus = OrderStatusDto.CANCELLED;
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_CANCELLED);
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			break;
		default:
			responseMessage.setMessage(WebServiceUtil.INVALID_ORDER_STATUS);
			responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
			return responseMessage;
		}

		for (OrderLineItemDetailsModel items : orderLineItemDetailsModel) {
			items.setOrderLineItemStatus(newStatus);
			items.setUpdateDate(LocalDateTime.now());
		}
		return responseMessage;
	}

	@Override
	public CommonListResponse<?> getOrderLineDetails() {
		return orderLineDetailsDao.getOrderLineDetails();
	}

	@Override
	public Object getOrderLineDetailsById(Long lineid) {
		OrderLineItemDetailsDto result = orderLineDetailsDao.getOrderLineDetailsById(lineid);
		if(result==null)
		{
			ResponseMessage message = new ResponseMessage();
			message.setMessage(WebServiceUtil.ORDER_LINE_ID_NOT_FOUND);
			message.setStatus(WebServiceUtil.FAILED_STATUS);
			return message;
		}
		else
		{
			ResponseData data = new ResponseData();
			data.setData(result);
			data.setStatus(WebServiceUtil.SUCCESS_STATUS);
			return data;
		}
	}
}
