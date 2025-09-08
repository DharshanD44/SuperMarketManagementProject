package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarketmanagement.api.Model.Custom.ResponseMessage;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderListResponse;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderStatusDto;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Service.OrderDetailsService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.OrderDetailsDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderDetailsServiceImp implements OrderDetailsService{
	
	@Autowired
	private OrderDetailsDao orderdetailsDao;
	
	@Override
	public Object updateOrderStatus(String status, Long id) {

		OrderDetailsModel orderDetailsModel = orderdetailsDao.findByOrderId(id);
		ResponseMessage responseMessage = new ResponseMessage();
		switch (status.toUpperCase()) {
		case "PACKED":
			orderDetailsModel.setOrderStatus(OrderStatusDto.PACKED);
			orderDetailsModel.setUpdateDate(LocalDateTime.now());
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_PACKED);
			return responseMessage;
		case "DELIVERED":
			orderDetailsModel.setOrderStatus(OrderStatusDto.DELIVERED);
			orderDetailsModel.setUpdateDate(LocalDateTime.now());
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_DELIVERED);
			return responseMessage;
		case "SHIPPED":
			orderDetailsModel.setOrderStatus(OrderStatusDto.SHIPPED);
			orderDetailsModel.setUpdateDate(LocalDateTime.now());
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_SHIPPED);
			return responseMessage;
		case "CANCELLED":
			orderDetailsModel.setOrderStatus(OrderStatusDto.CANCELLED);
			orderDetailsModel.setUpdateDate(LocalDateTime.now());
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_CANCELLED);
			return responseMessage;
		default:
			responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
			responseMessage.setMessage(WebServiceUtil.INVALID_ORDER_STATUS);
			return responseMessage;
		}
	}
	
	@Override
	public Object getOrderDetailsById(Long orderid) {
		return orderdetailsDao.getOrderDetailsById(orderid);
	}
	
	@Override
	public OrderListResponse getOrderListDetails() {
		OrderListResponse responseData = new OrderListResponse();
		responseData.setData(orderdetailsDao.getOrderListDetails());
		responseData.setStatus(WebServiceUtil.SUCCESS_STATUS);
		return responseData;
	}


}
