package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderLineItemsDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderMessageDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderStatusDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderUpdateRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.UpdateOrderLineItemsDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Service.OrderDetailsService;
import com.supermarketmanagement.api.dao.OrderDetailsDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImp implements OrderDetailsService {

	@Autowired
	private OrderDetailsDao orderdetailsDao;

	@Override
	public Object placeOrder(OrderRequestDto requestDto) {

		CustomerModel customerModel = orderdetailsDao.findByCustomerId(requestDto.getCustomerId());

		float totalPrice = 0;
		OrderDetailsModel orderDetailsDao = new OrderDetailsModel();
		orderDetailsDao.setCustomer(customerModel);
		orderDetailsDao.setOrderExpectedDate(LocalDateTime.now().plusDays(2));
		orderDetailsDao.setOrderDate(LocalDateTime.now());
		orderDetailsDao.setCreatedDate(LocalDateTime.now());

		for (OrderLineItemsDto itemsDto : requestDto.getItems()) {

			float individualPrice = 0;

			ProductModel productModel = orderdetailsDao.findByProductId(itemsDto.getProductId());
			Integer packageSize = productModel.getProductPackQuantity();

			Integer requestUnits = itemsDto.getOrderQuantityIndividualUnit();

			Integer requiredPackage = (int) Math.ceil((double) requestUnits / packageSize);

			Integer adjustedUnits = requiredPackage * packageSize;

			individualPrice = (float) (requiredPackage * productModel.getProductPrice());

			totalPrice += individualPrice;

			if (requiredPackage <= productModel.getProductCurrentStockPackageCount()) {
				OrderLineItemDetailsModel itemDetailsModel1 = new OrderLineItemDetailsModel();
				itemDetailsModel1.setProduct(productModel);
				itemDetailsModel1.setOrder(orderDetailsDao);
				itemDetailsModel1.setPrice(individualPrice);
				itemDetailsModel1.setCreatedDate(LocalDateTime.now());
				itemDetailsModel1.setOrderQuantityInPackage(requiredPackage);
				itemDetailsModel1.setOrderQuantityIndividualUnit(adjustedUnits);
				orderDetailsDao.getLineItemDetailsModels().add(itemDetailsModel1);
				productModel.setProductCurrentStockPackageCount(productModel.getProductCurrentStockPackageCount() - requiredPackage);
			} else {
				return OrderMessageDto.ORDER_QUANTITY_OUT_OF_STOCK + productModel.getProductName();
			}
		}
		orderDetailsDao.setTotalprice(totalPrice);
		return orderdetailsDao.saveOrderDetails(orderDetailsDao);
	}

	@Override
	public Object updatePlacedOrder(OrderUpdateRequestDto updaterequestDto) {
		OrderDetailsModel detailsModel = orderdetailsDao.findByOrderId(updaterequestDto.getOrderId());
		if (detailsModel.getOrderStatus() != OrderStatusDto.NEW) {
			return OrderMessageDto.CANT_UPDATE_ORDER;
		}

		for (UpdateOrderLineItemsDto items : updaterequestDto.getItems()) {

			detailsModel.getLineItemDetailsModels();
			OrderLineItemDetailsModel lineItem = orderdetailsDao.findByOrderLineId(items.getOrderLineId());
			if (!lineItem.getOrder().getOrderId().equals(detailsModel.getOrderId())) {
	            throw new RuntimeException("OrderLineItem " + items.getOrderLineId() + " does not belong to Order " + detailsModel.getOrderId());
	        }
			
			if (lineItem.getOrderLineItemStatus() == OrderStatusDto.PACKED) {
				return lineItem.getProduct().getProductName() + " has been packed. Product can't be updated!";
			}

			ProductModel productModel = orderdetailsDao.findByProductId(lineItem.getProduct().getProductId());
			
			Integer packageSize = productModel.getProductPackQuantity();

			Integer requestUnits = items.getOrderQuantityIndividualUnit();

			Integer requiredPackage = (int) Math.ceil((double) requestUnits / packageSize);

			Integer adjustedUnits = requiredPackage * packageSize;
			detailsModel.setUpdateDate(LocalDateTime.now());

			lineItem.setOrderQuantityIndividualUnit(null);
			lineItem.setProduct(productModel);
			lineItem.setOrder(detailsModel);
			lineItem.setCreatedDate(LocalDateTime.now());
			lineItem.setOrderQuantityInPackage(requiredPackage);
			lineItem.setOrderQuantityIndividualUnit(adjustedUnits);
		}
		detailsModel.setUpdateDate(LocalDateTime.now());
		return detailsModel;
	}

	@Override
	public Object updateOrderStatus(String status, Long id) {

		OrderDetailsModel orderDetailsModel = orderdetailsDao.findByOrderId(id);
		switch (status.toUpperCase()) {
		case "PACKED":
			orderDetailsModel.setOrderStatus(OrderStatusDto.PACKED);
			orderDetailsModel.setUpdateDate(LocalDateTime.now());
			return OrderMessageDto.ORDER_STATUS_PACKED;
		case "DELIVERED":
			orderDetailsModel.setOrderStatus(OrderStatusDto.DELIVERED);
			orderDetailsModel.setUpdateDate(LocalDateTime.now());
			return OrderMessageDto.ORDER_STATUS_DELIVERED;
		case "SHIPPED":
			orderDetailsModel.setOrderStatus(OrderStatusDto.SHIPPED);
			orderDetailsModel.setUpdateDate(LocalDateTime.now());
			return OrderMessageDto.ORDER_STATUS_SHIPPED;
		case "CANCELLED":
			orderDetailsModel.setOrderStatus(OrderStatusDto.CANCELLED);
			orderDetailsModel.setUpdateDate(LocalDateTime.now());
			return OrderMessageDto.ORDER_STATUS_CANCELLED;
		default:
			return "INVALID ORDER STATUS!";
		}
	}

	@Override
	public Object updateLineOrderLineStatus(String status, List<Long> id) {
		List<OrderLineItemDetailsModel> orderLineItemDetailsModel = orderdetailsDao.findByOrderLineId(id);

		OrderStatusDto newStatus;
		String message;

		switch (status.toUpperCase()) {
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

		for (OrderLineItemDetailsModel items : orderLineItemDetailsModel) {
			items.setOrderLineItemStatus(newStatus);
			items.setUpdateDate(LocalDateTime.now());
		}
		return message;
	}

	@Override
	public Object getOrderDetailsById(Long orderid) {
		return orderdetailsDao.getOrderDetailsById(orderid);
	}

}
