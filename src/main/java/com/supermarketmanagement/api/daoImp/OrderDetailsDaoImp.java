package com.supermarketmanagement.api.daoImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerMessageDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderLineItemsDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderMessageDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderStatusDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderUpdateRequestDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.UpdateOrderLineItemsDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductMessageDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Repository.CustomerRepoistory;
import com.supermarketmanagement.api.Repository.OrderDetailsRepoistory;
import com.supermarketmanagement.api.Repository.OrderLineItemDetailsRepoistory;
import com.supermarketmanagement.api.Repository.ProductRepository;
import com.supermarketmanagement.api.dao.OrderDetailsDao;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public class OrderDetailsDaoImp implements OrderDetailsDao {

	@Autowired
	private CustomerRepoistory customerRepoistory;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderDetailsRepoistory orderdetailsRepoistory;

	@Autowired
	private OrderLineItemDetailsRepoistory orderLineItemDetailsRepoistory;

	public Object placeOrder(OrderRequestDto requestDto) {

		CustomerModel customerModel = customerRepoistory.findById(requestDto.getCustomerId())
				.orElseThrow(() -> new RuntimeException(CustomerMessageDto.CUSTOMER_NOT_FOUND));

		OrderDetailsModel orderDetailsDao = new OrderDetailsModel();
		orderDetailsDao.setCustomer(customerModel);
		orderDetailsDao.setOrderExpectedDate(LocalDate.now().plusDays(2));
		orderDetailsDao.setOrderDate(LocalDate.now());
		orderDetailsDao.setCreatedDate(LocalDate.now());

		for (OrderLineItemsDto itemsDto : requestDto.getItems()) {

			ProductModel productModel = productRepository.findById(itemsDto.getProductId())
					.orElseThrow(() -> new RuntimeException(ProductMessageDto.PRODUCT_ID_NOT_FOUND));

			Integer packageSize = productModel.getProductPackQuantity();

			Integer requestUnits = itemsDto.getOrderQuantityIndividualUnit();

			Integer requiredPackage = (int) Math.ceil((double) requestUnits / packageSize);

			Integer adjustedUnits = requiredPackage * packageSize;

			if (requiredPackage <= productModel.getProductCurrentStockPackageCount()) {
				OrderLineItemDetailsModel itemDetailsModel1 = new OrderLineItemDetailsModel();
				itemDetailsModel1.setProduct(productModel);
				itemDetailsModel1.setOrder(orderDetailsDao);
				itemDetailsModel1.setCreatedDate(LocalDate.now());
				itemDetailsModel1.setOrderQuantityInPackage(requiredPackage);
				itemDetailsModel1.setOrderQuantityIndividualUnit(adjustedUnits);
				orderDetailsDao.getLineItemDetailsModels().add(itemDetailsModel1);
				productModel.setProductCurrentStockPackageCount(
						productModel.getProductCurrentStockPackageCount() - requiredPackage);
				productRepository.save(productModel);
			} else {
				return OrderMessageDto.ORDER_QUANTITY_OUT_OF_STOCK + productModel.getProductName();
			}
		}
		return orderdetailsRepoistory.save(orderDetailsDao);
	}

	public Object updatePlacedOrder(OrderUpdateRequestDto updaterequestDto) {

		OrderDetailsModel detailsModel = orderdetailsRepoistory.findById(updaterequestDto.getOrderId())
				.orElseThrow(() -> new RuntimeException(OrderMessageDto.ORDER_ID_NOT_FOUND));

		if (detailsModel.getOrderStatus() != OrderStatusDto.NEW) {
			return OrderMessageDto.CANT_UPDATE_ORDER;
		}

		for (UpdateOrderLineItemsDto items : updaterequestDto.getItems()) {

			detailsModel.getLineItemDetailsModels();
			OrderLineItemDetailsModel lineItem = orderLineItemDetailsRepoistory.findById(items.getOrderLineId())
					.orElseThrow(()->new RuntimeException(OrderMessageDto.ORDER_LINE_ID_NOT_FOUND));

			if (!lineItem.getOrder().getOrderId().equals(detailsModel.getOrderId())) {
	            throw new RuntimeException("OrderLineItem " + items.getOrderLineId() + " does not belong to Order " + detailsModel.getOrderId());
	        }
			
			if (lineItem.getOrderLineItemStatus() == OrderStatusDto.PACKED) {
				return lineItem.getProduct().getProductName() + " has been packed. Product can't be updated!";
			}

			ProductModel productModel = productRepository.findById(lineItem.getProduct().getProductId())
					.orElseThrow(() -> new RuntimeException(ProductMessageDto.PRODUCT_ID_NOT_FOUND));

			Integer packageSize = productModel.getProductPackQuantity();

			Integer requestUnits = items.getOrderQuantityIndividualUnit();

			Integer requiredPackage = (int) Math.ceil((double) requestUnits / packageSize);

			Integer adjustedUnits = requiredPackage * packageSize;
			detailsModel.setUpdateDate(LocalDate.now());

			lineItem.setOrderQuantityIndividualUnit(null);
			lineItem.setProduct(productModel);
			lineItem.setOrder(detailsModel);
			lineItem.setCreatedDate(LocalDate.now());
			lineItem.setOrderQuantityInPackage(requiredPackage);
			lineItem.setOrderQuantityIndividualUnit(adjustedUnits);
		}
		detailsModel.setUpdateDate(LocalDate.now());
		return detailsModel;
	}

	@Override
	public OrderDetailsModel findByOrderId(Long id) {
		return orderdetailsRepoistory.findById(id)
				.orElseThrow(() -> new RuntimeException(OrderMessageDto.ORDER_ID_NOT_FOUND));
	}

	@Override
	public List<OrderLineItemDetailsModel> findByOrderLineId(List<Long> id) {
		List<OrderLineItemDetailsModel> items = orderLineItemDetailsRepoistory.findAllById(id);
		if (items.isEmpty()) {
			throw new RuntimeException(OrderMessageDto.ORDER_ID_NOT_FOUND);
		}
		return items;
	}
}
