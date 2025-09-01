package com.supermarketmanagement.api.daoImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerMessageDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderDetailsListDto;
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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaBuilder.In;
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
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private OrderLineItemDetailsRepoistory orderLineItemDetailsRepoistory;
	

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

	@Override
	public CustomerModel findByCustomerId(Long customerId) {
		return customerRepoistory.findById(customerId).orElseThrow(
				()-> new RuntimeException(CustomerMessageDto.CUSTOMER_NOT_FOUND +" " + customerId)
				);
	}

	@Override
	public ProductModel findByProductId(Long productId) {
		return productRepository.findById(productId).orElseThrow(
				()-> new RuntimeException(ProductMessageDto.PRODUCT_ID_NOT_FOUND +" " + productId)
				);
	}

	@Override
	public Object saveOrderDetails(OrderDetailsModel orderDetailsDao) {
		return orderdetailsRepoistory.save(orderDetailsDao);
	}

	@Override
	public OrderLineItemDetailsModel findByOrderLineId(Long orderLineId) {
		return orderLineItemDetailsRepoistory.findById(orderLineId).orElseThrow(
				()-> new RuntimeException(OrderMessageDto.ORDER_LINE_ID_NOT_FOUND +" " + orderLineId)
				);
	}

	@Override
	public Object getOrderDetailsById(Long orderid) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<OrderDetailsListDto> criteriaQuery = cb.createQuery(OrderDetailsListDto.class);
		Root<OrderDetailsModel> orderDetailsModelRoot = criteriaQuery.from(OrderDetailsModel.class);
		Predicate predicate = cb.equal(orderDetailsModelRoot.get("orderId"),orderid);
		criteriaQuery.multiselect(
		        orderDetailsModelRoot.get("orderId"),
		        orderDetailsModelRoot.get("orderDate"),
		        orderDetailsModelRoot.get("customer").get("customerId"),
		        orderDetailsModelRoot.get("orderExpectedDate"),
		        orderDetailsModelRoot.get("orderStatus"),
		        orderDetailsModelRoot.get("createdDate"),
		        orderDetailsModelRoot.get("updateDate"),
		        orderDetailsModelRoot.get("totalprice"))
				.where(predicate);
		
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
}
