package com.supermarketmanagement.api.ServiceImp;


import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.CommonResponse;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Service.OrderDetailsService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.OrderDetailsDao;
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderDetailsServiceImp implements OrderDetailsService{
	
	@Autowired
	private OrderDetailsDao orderdetailsDao;
	
	@Autowired
	private SuperMarketCodeDao codeDao;
	
	@Override
	public Object updateOrderStatus(String status, Long id) {

		OrderDetailsModel orderDetailsModel = orderdetailsDao.findByOrderId(id);
		CommonResponse responseMessage = new CommonResponse();
		SuperMarketCode orderstatus = codeDao.findByDescription(status);
		orderstatus.getCode();
		switch (status.toUpperCase()) {
		case "PACKED":
			orderDetailsModel.setOrderStatus(orderstatus);
			orderDetailsModel.setUpdateDate(LocalDateTime.now());
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_PACKED);
			return responseMessage;
		case "DELIVERED":
			orderDetailsModel.setOrderStatus(orderstatus);
			orderDetailsModel.setUpdateDate(LocalDateTime.now());
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_DELIVERED);
			return responseMessage;
		case "SHIPPED":
			orderDetailsModel.setOrderStatus(orderstatus);
			orderDetailsModel.setUpdateDate(LocalDateTime.now());
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_SHIPPED);
			return responseMessage;
		case "CANCELLED":
			orderDetailsModel.setOrderStatus(orderstatus);
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
	public Object getOrderListDetails(CommonListRequestModel commonListRequestModel) {
		return orderdetailsDao.getOrderListDetails(commonListRequestModel);
	}
}
