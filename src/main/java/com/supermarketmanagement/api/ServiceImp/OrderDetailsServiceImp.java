package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.CommonListResponseDto;
import com.supermarketmanagement.api.Model.Custom.CommonMessageResponse;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderDetailsListDto;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.OrderLineItemDetailsDto;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
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
public class OrderDetailsServiceImp implements OrderDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsServiceImp.class);
	
	@Autowired
	private OrderDetailsDao orderdetailsDao;

	@Autowired
	private SuperMarketCodeDao codeDao;

	@Autowired
	private SupplierDetailsDao supplierDetailsDao;

	/**
	 * Update the status of an order.
	 * 
	 * 
	 * {@link OrderDetailsDao#findByOrderId(Long)}
	 * {@link SuperMarketCodeDao#findByDescription(String)}
	 * 
	 * @param status order status (e.g., PACKED, DELIVERED, SHIPPED, CANCELLED)
	 * @param id     order ID
	 * @return {@link CommonMessageResponse} with success/failure and message
	 */
	@Override
	public Object updateOrderStatus(String status, Long id) {
	    logger.info("updateOrderStatus: Updating order ID {} to status '{}'", id, status);

	    OrderDetailsModel orderDetailsModel = orderdetailsDao.findByOrderId(id);
	    CommonMessageResponse responseMessage = new CommonMessageResponse();

	    List<OrderLineItemDetailsDto> orderLineItems = orderdetailsDao.findOrderLineItemStatus(id);

	    if (orderDetailsModel == null) {
	        responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
	        responseMessage.setData(WebServiceUtil.ORDER_ID_NOT_FOUND);
	        return responseMessage;
	    }

	    SuperMarketCode orderStatus = codeDao.findByDescription(status);
	    if (orderStatus == null) {
	        responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
	        responseMessage.setData(WebServiceUtil.INVALID_ORDER_STATUS);
	        return responseMessage;
	    }

	    List<Long> notMatchingLineItems = orderLineItems.stream()
	            .filter(item -> !status.equalsIgnoreCase(item.getOrderItemstatus())) 
	            .map(OrderLineItemDetailsDto::getOrderLineId)
	            .toList();

	    if (!notMatchingLineItems.isEmpty()) {
	        responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
	        responseMessage.setData(WebServiceUtil.UPDATE_ORDERLINEITEM_STATUS + notMatchingLineItems);
	        return responseMessage;
	    }

	    orderDetailsModel.setOrderStatus(orderStatus);
	    orderDetailsModel.setUpdateDate(LocalDateTime.now());

	    switch (status.toUpperCase()) {
	        case "PACKED":
	            responseMessage.setData(WebServiceUtil.ORDER_STATUS_PACKED);
	            break;
	        case "SHIPPED":
	            responseMessage.setData(WebServiceUtil.ORDER_STATUS_SHIPPED);
	            break;
	        case "DELIVERED":
	            responseMessage.setData(WebServiceUtil.ORDER_STATUS_DELIVERED);
	            break;
	        case "CANCELLED":
	            responseMessage.setData(WebServiceUtil.ORDER_STATUS_CANCELLED);
	            break;
	    }

	    responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
	    return responseMessage;
	}


	
	
	/**
	 * Retrieve a list of order details.
	 * 
	 * 
	 * {@link OrderDetailsDao#getOrderListDetails(CommonListRequestModel)}
	 * 
	 * @param commonListRequestModel filter and pagination details
	 * @return Object containing paginated order list details
	 */
	@Override
	public Object getOrderListDetails(CommonListRequestModel commonListRequestModel) {
        logger.info("getOrderListDetails: Retrieving list of order details with provided filters");

        Map<String, Object> orderlist = orderdetailsDao.getOrderListDetails(commonListRequestModel);
        CommonListResponseDto response = new CommonListResponseDto();
        
        if(orderlist.get("message") !=null) {
	        response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(orderlist.get("message"));
		}else {
	        response.setData(orderlist.get("data"));
	        response.setStatus(WebServiceUtil.SUCCESS_STATUS);
	        response.setDraw(commonListRequestModel.getDraw());
	        response.setRecordsTotal((Long) orderlist.get(WebServiceUtil.KEY_TOTAL_COUNT));
	        response.setRecordsFiltered((Long) orderlist.get(WebServiceUtil.KEY_FILTER_COUNT));
		}
		return response;
	}

	
	
	/**
	 * Assign an order to a supplier.
	 * 
	 * 
	 * {@link SupplierDetailsDao#findBySupplierId(Long)}
	 * {@link OrderDetailsDao#findByOrderId(Long)}
	 * 
	 * @param orderId    the order ID to be assigned
	 * @param supplierId the supplier ID to assign the order to
	 * @return {@link CommonMessageResponse} with success/failure and message
	 */
	@Override
	public CommonMessageResponse assignOrderToSupplier(Long orderId, Long supplierId) {
        logger.info("assignOrderToSupplier: Assigning order ID {} to supplier ID {}", orderId, supplierId);

		CommonMessageResponse response = new CommonMessageResponse();

		SuppliersModel supplierid = supplierDetailsDao.findBySupplierId(supplierId);
		OrderDetailsModel orderDetails = orderdetailsDao.findByOrderId(orderId);

		if (supplierid == null && orderDetails == null) {
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(WebServiceUtil.SUPPLIER_ORDER_ID_NOT_FOUND);
		} else if (supplierid == null || orderDetails == null) {
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(
					supplierid == null ? WebServiceUtil.SUPPLIER_ID_NOT_FOUND : WebServiceUtil.ORDER_ID_NOT_FOUND);
		} else {
			orderDetails.setAssignedTo(supplierId);
			response.setStatus(WebServiceUtil.SUCCESS_STATUS);
			response.setData(WebServiceUtil.SUPPLIER_ASSIGNED);
		}
		return response;
	}



	@Override
	public CommonMessageResponse getOrderDetailsById(Long orderid) {
		OrderDetailsListDto orderDetails = orderdetailsDao.getOrderDetailsById(orderid);
		CommonMessageResponse response = new CommonMessageResponse();

		if(orderDetails==null) {
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(WebServiceUtil.ORDER_ID_NOT_FOUND);
		}
		else
		{
			orderDetails.setSno(1);
			response.setStatus(WebServiceUtil.SUCCESS_STATUS);
			response.setData(orderDetails);
		}
		return response;
	}
}
