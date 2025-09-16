package com.supermarketmanagement.api.ServiceImp;


import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.CommonResponse;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Model.Entity.SuppliersModel;
import com.supermarketmanagement.api.Service.OrderDetailsService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.OrderDetailsDao;
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;
import com.supermarketmanagement.api.dao.SupplierDetailsDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderDetailsServiceImp implements OrderDetailsService{
	
	@Autowired
	private OrderDetailsDao orderdetailsDao;
	
	@Autowired
	private SuperMarketCodeDao codeDao;
	
	@Autowired 
	private SupplierDetailsDao supplierDetailsDao;
	
	@Override
	public Object updateOrderStatus(String status, Long id) {

		OrderDetailsModel orderDetailsModel = orderdetailsDao.findByOrderId(id);
		CommonResponse responseMessage = new CommonResponse();
		SuperMarketCode orderstatus = codeDao.findByDescription(status);
		if(orderstatus==null) {
			responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
			responseMessage.setMessage(WebServiceUtil.INVALID_ORDER_STATUS);
			return responseMessage;
		}
		orderstatus.getCode();
		switch (status.toUpperCase()) {
		case "PACKED":
			orderDetailsModel.setOrderStatus(orderstatus);
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_PACKED);
			break;
		case "DELIVERED":
			orderDetailsModel.setOrderStatus(orderstatus);
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_DELIVERED);
			break;
		case "SHIPPED":
			orderDetailsModel.setOrderStatus(orderstatus);
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_SHIPPED);
			break;
		case "CANCELLED":
			orderDetailsModel.setOrderStatus(orderstatus);
			responseMessage.setMessage(WebServiceUtil.ORDER_STATUS_CANCELLED);
			break;
		}
		if(responseMessage.getStatus()==null){
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
			orderDetailsModel.setUpdateDate(LocalDateTime.now());
		}
		return responseMessage;
	}


	@Override
	public Object getOrderListDetails(CommonListRequestModel commonListRequestModel) {
		return orderdetailsDao.getOrderListDetails(commonListRequestModel);
	}


	@Override
	public CommonResponse assignOrderToSupplier(Long orderId, Long supplierId) {
	    CommonResponse response = new CommonResponse();

	    SuppliersModel supplierid = supplierDetailsDao.findBySupplierId(supplierId);

	    OrderDetailsModel orderDetails = orderdetailsDao.findByOrderId(orderId);
	    
	    if (supplierid == null && orderDetails == null) {
	        response.setStatus(WebServiceUtil.FAILED_STATUS);
	        response.setMessage(WebServiceUtil.SUPPLIER_ORDER_ID_NOT_FOUND);
	        return response;
	    }
	    
	    if (supplierid == null || orderDetails == null) {
	        response.setStatus(WebServiceUtil.FAILED_STATUS);
	        response.setMessage(
	            supplierid == null 
	                ? WebServiceUtil.SUPPLIER_ID_NOT_FOUND 
	                : WebServiceUtil.ORDER_ID_NOT_FOUND
	        );
		    return response;
	    }

	    orderDetails.setAssignedTo(supplierId);

	    response.setStatus(WebServiceUtil.SUCCESS_STATUS);
	    response.setMessage(WebServiceUtil.SUPPLIER_ASSIGNED);
	    return response;
	}

}
