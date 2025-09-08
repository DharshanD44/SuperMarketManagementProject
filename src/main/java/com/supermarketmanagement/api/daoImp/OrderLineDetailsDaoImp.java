package com.supermarketmanagement.api.daoImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Custom.CommonListResponse;
import com.supermarketmanagement.api.Model.Custom.ResponseData;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderMessageDto;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.OrderLineItemDetailsDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Repository.OrderLineItemDetailsRepoistory;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.OrderLineDetailsDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class OrderLineDetailsDaoImp implements OrderLineDetailsDao{

	@Autowired
	private OrderLineItemDetailsRepoistory orderLineItemDetailsRepoistory;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<OrderLineItemDetailsModel> findByOrderLineId(List<Long> id) {
		List<OrderLineItemDetailsModel> items = orderLineItemDetailsRepoistory.findAllById(id);
		if (items.isEmpty()) {
			throw new RuntimeException(OrderMessageDto.ORDER_ID_NOT_FOUND);
		}
		return items;
	}
	
	@Override
	public OrderLineItemDetailsModel findByOrderLineId(Long orderLineId) {
		return orderLineItemDetailsRepoistory.findByOrderLineId(orderLineId);
	}

	@Override
	public CommonListResponse<List<OrderLineItemDetailsDto>> getOrderLineDetails() {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<OrderLineItemDetailsDto> criteriaQuery = cb.createQuery(OrderLineItemDetailsDto.class);
	    Root<OrderLineItemDetailsModel> root = criteriaQuery.from(OrderLineItemDetailsModel.class);

	    criteriaQuery.select(cb.construct(
	            OrderLineItemDetailsDto.class,
	            root.get("orderLineId"),
	            root.get("order").get("orderId"),
	            root.get("product").get("productId"),
	            root.get("orderQuantityIndividualUnit"),
	            root.get("orderQuantityInPackage"),
	            root.get("statusDto"),
	            root.get("updateDate"),
	            root.get("price")
	    ));

	    List<OrderLineItemDetailsDto> result = entityManager.createQuery(criteriaQuery).getResultList();

	    CommonListResponse<List<OrderLineItemDetailsDto>> commonListResponse = new CommonListResponse<>();
	    commonListResponse.setStatus(WebServiceUtil.SUCCESS_STATUS);
	    commonListResponse.setData(result);

	    return commonListResponse;
	}

	@Override
	public OrderLineItemDetailsDto getOrderLineDetailsById(Long lineid) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<OrderLineItemDetailsDto> criteriaQuery = cb.createQuery(OrderLineItemDetailsDto.class);
	    Root<OrderLineItemDetailsModel> root = criteriaQuery.from(OrderLineItemDetailsModel.class);

	    criteriaQuery.select(cb.construct(
	            OrderLineItemDetailsDto.class,
	            root.get("orderLineId"),
	            root.get("order").get("orderId"),
	            root.get("product").get("productId"),
	            root.get("orderQuantityIndividualUnit"),
	            root.get("orderQuantityInPackage"),
	            root.get("statusDto"),
	            root.get("updateDate"),
	            root.get("price")
	    )).where(cb.equal(root.get("orderLineId"), lineid));
	    try {
	        return entityManager.createQuery(criteriaQuery).getSingleResult();
	    } catch (NoResultException e) {
	        return null; 
	    }
	}

}
