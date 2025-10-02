package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.CommonListResponseDto;
import com.supermarketmanagement.api.Model.Custom.CommonMessageResponse;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderDetailsListDto;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.OrderLineItemDetailsDto;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Service.OrderLineDetailsService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.OrderLineDetailsDao;
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@Transactional
public class OrderLineDetailsServiceImp implements OrderLineDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsServiceImp.class);	
	
	@Autowired
	private OrderLineDetailsDao orderLineDetailsDao;

	@Autowired
	private SuperMarketCodeDao codeDao;

	/**
	 * Updates the status of multiple order line items.
	 *
	 * {@link OrderLineDetailsDao#findByOrderLineId(List)}
	 * {@link SuperMarketCodeDao#findByDescription(String)}
	 *
	 * @param status order line status
	 * @param id     list of order line IDs
	 * @return {@link CommonMessageResponse} with success/failure and message
	 */
	@Override
	public Object updateLineOrderLineStatus(String status, List<Long> id) {
		
		logger.info("updateLineOrderLineStatus: Updating order line IDs {} to status '{}'", id, status);

		List<OrderLineItemDetailsModel> orderLineItemDetailsModel = orderLineDetailsDao.findByOrderLineId(id);
		CommonMessageResponse responseMessage = new CommonMessageResponse();
		SuperMarketCode orderstatus = codeDao.findByDescription(status);
		if (orderLineItemDetailsModel.isEmpty() && orderstatus == null) {
			responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
			responseMessage.setData(WebServiceUtil.INVALID_ORDER_STATUS + " "+"and " + WebServiceUtil.ORDER_LINE_ID_NOT_FOUND);
		} else if (orderLineItemDetailsModel.isEmpty()) {
			responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
			responseMessage.setData(WebServiceUtil.ORDER_LINE_ID_NOT_FOUND);
		} else if (orderstatus == null) {
			responseMessage.setStatus(WebServiceUtil.FAILED_STATUS);
			responseMessage.setData(WebServiceUtil.INVALID_ORDER_STATUS);
		} else {
			orderstatus.getCode();
			switch (status.toUpperCase()) {
			case "PACKED":
				responseMessage.setData(WebServiceUtil.ORDER_STATUS_PACKED);
				break;
			case "DELIVERED":
				responseMessage.setData(WebServiceUtil.ORDER_STATUS_DELIVERED);
				break;
			case "SHIPPED":
				responseMessage.setData(WebServiceUtil.ORDER_STATUS_SHIPPED);
				break;
			case "CANCELLED":
				responseMessage.setData(WebServiceUtil.ORDER_STATUS_CANCELLED);
				break;
			}
			for (OrderLineItemDetailsModel items : orderLineItemDetailsModel) {
				items.setOrderStatus(orderstatus);
				items.setUpdateDate(LocalDateTime.now());
			}
			responseMessage.setStatus(WebServiceUtil.SUCCESS_STATUS);
		}
		return responseMessage;
	}

	
	
	/**
	 * Retrieves a list of order line details with filters and pagination.
	 *
	 * {@link OrderLineDetailsDao#getOrderLineListDetails(CommonListRequestModel)}
	 *
	 * @param commonListRequestModel filter and pagination details
	 * @return Map containing order line list details
	 */
	@Override
	public Object getOrderLineListDetails(CommonListRequestModel commonListRequestModel) {
		logger.info("getOrderLineListDetails: Retrieving order line details with provided filters");
		Map<String , Object> orderLineList = orderLineDetailsDao.getOrderLineListDetails(commonListRequestModel);
		CommonListResponseDto response = new CommonListResponseDto();

		if(orderLineList.get("message") !=null) {
	        response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(orderLineList.get("message"));
		}else {
	        response.setData(orderLineList.get("data"));
	        response.setStatus(WebServiceUtil.SUCCESS_STATUS);
	        response.setDraw(commonListRequestModel.getDraw());
	        response.setRecordsTotal((Long) orderLineList.get(WebServiceUtil.KEY_TOTAL_COUNT));
	        response.setRecordsFiltered((Long) orderLineList.get(WebServiceUtil.KEY_FILTER_COUNT));
		}
        return response;
        
	}
	
	
	@Override
	public CommonMessageResponse getOrderLineItemDetailsById(Long orderid) {
		OrderLineItemDetailsDto orderLineDetails = orderLineDetailsDao.getOrderLineItemDetailsById(orderid);
		CommonMessageResponse response = new CommonMessageResponse();

		if(orderLineDetails==null) {
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(WebServiceUtil.ORDER_LINE_ID_NOT_FOUND);
		}
		else
		{
			orderLineDetails.setSno(1);
			response.setStatus(WebServiceUtil.SUCCESS_STATUS);
			response.setData(orderLineDetails);
		}
		return response;
	}
}
