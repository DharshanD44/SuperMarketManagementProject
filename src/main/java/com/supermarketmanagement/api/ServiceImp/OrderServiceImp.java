package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderMessageDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderStatusDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderUpdateRequestDto;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Service.OrderDetailsService;
import com.supermarketmanagement.api.dao.OrderDetailsDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImp implements OrderDetailsService{

	@Autowired
	private OrderDetailsDao detailsDao;
	
	@Override
	public Object placeOrder(OrderRequestDto requestDto) {
		
		return detailsDao.placeOrder(requestDto);
	}

	@Override
	public Object updatePlacedOrder(OrderUpdateRequestDto updaterequestDto) {
		
		return detailsDao.updatePlacedOrder(updaterequestDto);
	}

	@Override
	public Object updateOrderStatus(String status, Long id) {
		
		OrderDetailsModel orderDetailsModel = detailsDao.findByOrderId(id);
		switch(status) {
		case "PACKED":
			orderDetailsModel.setOrderStatus(OrderStatusDto.PACKED);
			orderDetailsModel.setUpdateDate(LocalDate.now());
			return OrderMessageDto.ORDER_STATUS_PACKED;
		case "DELIVERED":
			orderDetailsModel.setOrderStatus(OrderStatusDto.DELIVERED);
			orderDetailsModel.setUpdateDate(LocalDate.now());
			return OrderMessageDto.ORDER_STATUS_DELIVERED;
		case "SHIPPED":
			orderDetailsModel.setOrderStatus(OrderStatusDto.SHIPPED);
			orderDetailsModel.setUpdateDate(LocalDate.now());
			return OrderMessageDto.ORDER_STATUS_SHIPPED;
		case "CANCELLED":
			orderDetailsModel.setOrderStatus(OrderStatusDto.CANCELLED);
			orderDetailsModel.setUpdateDate(LocalDate.now());
			return OrderMessageDto.ORDER_STATUS_CANCELLED;
		default:
			return null;
		}
	}

	@Override
	public Object updateLineOrderLineStatus(String status, List<Long> id) {
		List<OrderLineItemDetailsModel> orderLineItemDetailsModel = detailsDao.findByOrderLineId(id);
		
		OrderStatusDto newStatus;
		String message;
		
		switch(status.toUpperCase()) {
		case "PACKED":
			newStatus = OrderStatusDto.PACKED;
			message = OrderMessageDto.ORDER_STATUS_PACKED;
			break;
		case "DELIVERED":
			newStatus = OrderStatusDto.DELIVERED;
			message = OrderMessageDto.ORDER_STATUS_DELIVERED;
			break;
		case "SHIPPED":
			newStatus = OrderStatusDto.SHIPPED;
			message = OrderMessageDto.ORDER_STATUS_SHIPPED;
			break;
		case "CANCELLED":
			newStatus = OrderStatusDto.CANCELLED;
			message = OrderMessageDto.ORDER_STATUS_CANCELLED;
			break;
		default:
			return "Invalid Status Provided!";
		}
		
		for(OrderLineItemDetailsModel items : orderLineItemDetailsModel) {
			items.setOrderLineItemStatus(newStatus);
			items.setUpdateDate(LocalDate.now());
		}
		return message;
	}

}
