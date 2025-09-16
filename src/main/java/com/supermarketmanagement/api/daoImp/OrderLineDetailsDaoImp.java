package com.supermarketmanagement.api.daoImp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderMessageDto;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.OrderLineItemDetailsDto;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Repository.OrderLineItemDetailsRepoistory;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.OrderLineDetailsDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
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
	public Map<String, Object> getOrderLineListDetails(CommonListRequestModel commonListRequestModel) {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

	    CriteriaQuery<OrderLineItemDetailsDto> criteriaQuery = cb.createQuery(OrderLineItemDetailsDto.class);
	    Root<OrderLineItemDetailsModel> root = criteriaQuery.from(OrderLineItemDetailsModel.class);

	    List<Predicate> predicates = new ArrayList<>();

	    if (commonListRequestModel.getSearchBy() != null && commonListRequestModel.getSearchValue() != null) {
			String value = commonListRequestModel.getSearchValue();
			switch (commonListRequestModel.getSearchBy().toLowerCase()) {
			case "orderstatus":
				predicates.add(cb.equal(root.get("orderStatus"), value));
				break;
			case "orderlineid":
				predicates.add(cb.equal(root.get("orderLineId"), Integer.valueOf(value)));
				break;
			case "createddate":
				LocalDate parsedate = LocalDate.parse(value,
						DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			    java.sql.Date createdDate = java.sql.Date.valueOf(parsedate);
				predicates.add(cb.equal(cb.function("DATE", LocalDate.class, root.get("createdDate")), createdDate));
				break;
			default:
				break;
			}
		}

	    criteriaQuery.multiselect(
	    	    root.get("orderLineId"),
	    	    root.get("order").get("orderId"),   
	    	    root.get("product").get("productId"),
	    	    root.get("orderQuantityIndividualUnit"),
	    	    root.get("orderQuantityInPackage"),
	    	    root.get("orderStatus").get("description"),
	    	    root.get("updateDate"),
	    	    root.get("price")
	    	).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

	    TypedQuery<OrderLineItemDetailsDto> queryresult = entityManager.createQuery(criteriaQuery);

		if (commonListRequestModel.getStart() != null || commonListRequestModel.getLength() != null) {
			queryresult.setFirstResult(commonListRequestModel.getStart());
			queryresult.setMaxResults(commonListRequestModel.getLength());
		}
	    List<OrderLineItemDetailsDto> results = queryresult.getResultList();

	    CriteriaQuery<Long> totalQuery = cb.createQuery(Long.class);
	    totalQuery.select(cb.count(totalQuery.from(OrderLineItemDetailsModel.class)));
	    Long totalCount = entityManager.createQuery(totalQuery).getSingleResult();

	    CriteriaQuery<Long> filterCountQuery = cb.createQuery(Long.class);
	    Root<OrderLineItemDetailsModel> filterRoot = filterCountQuery.from(OrderLineItemDetailsModel.class);
	    filterCountQuery.select(cb.count(filterRoot))
	            .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
	    Long filteredCount = entityManager.createQuery(filterCountQuery).getSingleResult();

	    Map<String, Object> response = new LinkedHashMap<>();

		if (results == null || results.isEmpty()) {
			response.put("status", WebServiceUtil.FAILED_STATUS);
			response.put("data", "NO DATA FOUND");
			response.put("totalCount", 0);
			response.put("filteredCount", 0);
		} else {
			response.put("status", WebServiceUtil.SUCCESS_STATUS);
			response.put("totalCount", totalCount);
			response.put("filteredCount", filteredCount);
			response.put("data", results);
		
		}
		return response;
	}
	
}
