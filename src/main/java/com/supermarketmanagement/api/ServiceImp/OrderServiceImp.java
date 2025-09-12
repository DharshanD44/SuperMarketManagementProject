package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supermarketmanagement.api.Model.Custom.CommonResponse;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderLineItemsDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.UpdateOrderLineItemsDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Service.OrderService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.CustomerDao;
import com.supermarketmanagement.api.dao.OrderDetailsDao;
import com.supermarketmanagement.api.dao.OrderLineDetailsDao;
import com.supermarketmanagement.api.dao.ProductDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImp implements OrderService {

	@Autowired
	private OrderDetailsDao orderdetailsDao;

	@Autowired
	private OrderLineDetailsDao orderLineDetailsDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private ProductDao productDao;

	@Override
	public Object placeOrder(OrderRequestDto requestDto) {

		CustomerModel customerModel = customerDao.findByCustomerId(requestDto.getCustomerId());
		CommonResponse response = new CommonResponse();
		float totalPrice = 0;
		OrderDetailsModel orderDetailsDao = new OrderDetailsModel();
		orderDetailsDao.setCustomer(customerModel);
		orderDetailsDao.setOrderExpectedDate(LocalDate.now().plusDays(2));
		orderDetailsDao.setOrderDate(LocalDateTime.now().withNano(0));
		orderDetailsDao.setCreatedDate(LocalDateTime.now());
		SuperMarketCode defaultStatus = customerDao.find(SuperMarketCode.class, "NEW");
		orderDetailsDao.setOrderStatus(defaultStatus);

		for (OrderLineItemsDto itemsDto : requestDto.getItems()) {

			float individualPrice = 0;

			ProductModel productModel = productDao.findByProductId(itemsDto.getProductId());
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
				productModel.setProductCurrentStockPackageCount(
						productModel.getProductCurrentStockPackageCount() - requiredPackage);
			} else {
				response.setStatus(WebServiceUtil.FAILED_STATUS);
				response.setMessage(WebServiceUtil.ORDER_QUANTITY_OUT_OF_STOCK + " " + productModel.getProductName());
				return response;
			}
		}
		orderDetailsDao.setTotalprice(totalPrice);
		response.setStatus(WebServiceUtil.SUCCESS_STATUS);
		response.setMessage(WebServiceUtil.ORDER_PLACED);
		orderdetailsDao.saveOrderDetails(orderDetailsDao);
		return response;
	}

	@Override
	public Object updatePlacedOrder(UpdateOrderLineItemsDto updaterequestDto) {
		CommonResponse response = new CommonResponse();

		OrderLineItemDetailsModel lineItem = orderLineDetailsDao.findByOrderLineId(updaterequestDto.getOrderLineId());

		if (lineItem == null) {
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setMessage(WebServiceUtil.ORDER_LINE_ID_NOT_FOUND);
			return response;
		}

		Long orderId = lineItem.getOrder().getOrderId();
		float beforePrice = lineItem.getPrice();

		OrderDetailsModel orderDetailsModel = orderdetailsDao.findByOrderId(orderId);
		float totalPrice = orderDetailsModel.getTotalprice();

		if ("P".equals(lineItem.getOrderStatus().getCode())) {
		    response.setStatus(WebServiceUtil.FAILED_STATUS);
		    response.setMessage(lineItem.getProduct().getProductName() + " " + WebServiceUtil.CANT_UPDATE_ORDER);
		    return response;
		}

		float individualPrice = 0;

		ProductModel productModel = productDao.findByProductId(lineItem.getProduct().getProductId());

		Integer packageSize = productModel.getProductPackQuantity();

		Integer requestUnits = updaterequestDto.getOrderQuantityIndividualUnit();

		Integer requiredPackage = (int) Math.ceil((double) requestUnits / packageSize);

		Integer adjustedUnits = requiredPackage * packageSize;

		individualPrice = (float) (requiredPackage * productModel.getProductPrice());

		beforePrice = totalPrice - beforePrice + individualPrice;

		lineItem.setOrderQuantityIndividualUnit(null);
		lineItem.setProduct(productModel);
		lineItem.setCreatedDate(LocalDateTime.now());
		lineItem.setOrderQuantityInPackage(requiredPackage);
		lineItem.setOrderQuantityIndividualUnit(adjustedUnits);
		lineItem.setPrice(individualPrice);
		orderDetailsModel.setTotalprice(beforePrice);
		productModel.setProductCurrentStockPackageCount(
		productModel.getProductCurrentStockPackageCount() - requiredPackage);
		orderDetailsModel.setUpdateDate(LocalDateTime.now());
		response.setStatus(WebServiceUtil.SUCCESS_STATUS);
		response.setMessage(WebServiceUtil.UPDATE_PLACED_ORDER);
		return response;
	}

}
