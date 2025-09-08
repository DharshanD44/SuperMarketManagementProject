package com.supermarketmanagement.api.daoImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderDetailsListDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderMessageDto;

import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
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
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class OrderDetailsDaoImp implements OrderDetailsDao {

	@Autowired
	private OrderDetailsRepoistory orderdetailsRepoistory;
	
	@PersistenceContext
	private EntityManager entityManager;
	

	@Override
	public OrderDetailsModel findByOrderId(Long id) {
		return orderdetailsRepoistory.findOrderByOrderId(id);
	}


	@Override
	public Object saveOrderDetails(OrderDetailsModel orderDetailsDao) {
		return orderdetailsRepoistory.save(orderDetailsDao);
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
		        orderDetailsModelRoot.get("updateDate"),
		        orderDetailsModelRoot.get("totalprice"))
				.where(predicate);
		
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<OrderDetailsListDto> getOrderListDetails() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<OrderDetailsListDto> criteriaQuery = cb.createQuery(OrderDetailsListDto.class);
		Root<OrderDetailsModel> orderDetailsModelRoot = criteriaQuery.from(OrderDetailsModel.class);
		criteriaQuery.multiselect(
		        orderDetailsModelRoot.get("orderId"),
		        orderDetailsModelRoot.get("orderDate"),
		        orderDetailsModelRoot.get("customer").get("customerId"),
		        orderDetailsModelRoot.get("orderExpectedDate"),
		        orderDetailsModelRoot.get("orderStatus"),
		        orderDetailsModelRoot.get("totalprice"));
		
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
}
