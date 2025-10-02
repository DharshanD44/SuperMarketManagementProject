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
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderDetailsListDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderMessageDto;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.OrderLineItemDetailsDto;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Repository.OrderLineItemDetailsRepoistory;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.OrderLineDetailsDao;
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class OrderLineDetailsDaoImp implements OrderLineDetailsDao {

	@Autowired
	private OrderLineItemDetailsRepoistory orderLineItemDetailsRepoistory;

	@Autowired
	private SuperMarketCodeDao superMarketCodeDao;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<OrderLineItemDetailsModel> findByOrderLineId(List<Long> id) {
		List<OrderLineItemDetailsModel> items = orderLineItemDetailsRepoistory.findAllById(id);
		return items;
	}

	@Override
	public OrderLineItemDetailsModel findByOrderLineId(Long orderLineId) {
		return orderLineItemDetailsRepoistory.findByOrderLineId(orderLineId);
	}

	@Override
	public Map<String, Object> getOrderLineListDetails(CommonListRequestModel commonListRequestModel) {
		CriteriaBuilder criteriabuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<OrderLineItemDetailsDto> criteriaQuery = criteriabuilder.createQuery(OrderLineItemDetailsDto.class);
		Root<OrderLineItemDetailsModel> root = criteriaQuery.from(OrderLineItemDetailsModel.class);
		Join<OrderLineItemDetailsModel,ProductModel> productJoin = root.join("product");
		List<Predicate> predicates = new ArrayList<>();
		Map<String, Object> response = new LinkedHashMap<>();

		boolean searchColumn=false;

		String value = commonListRequestModel.getSearchValue();
		if (!commonListRequestModel.getSearchValue().isEmpty()) {
			
			if(!commonListRequestModel.getSearchBy().isEmpty()) {
				switch (commonListRequestModel.getSearchBy().toLowerCase()) {
				case WebServiceUtil.ORDER_LINE_STATUS:
					predicates.add(criteriabuilder.equal(root.get("orderStatus").get("code"), value.toUpperCase()));
					break;
				case WebServiceUtil.CREATED_DATE:
					LocalDate parsedate = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					java.sql.Date createdDate = java.sql.Date.valueOf(parsedate);
					predicates.add(criteriabuilder.equal(criteriabuilder.function("DATE", LocalDate.class, root.get("createdDate")), createdDate));
					break;
				case WebServiceUtil.ORDER_LINE_ID:
					predicates.add(criteriabuilder.equal(root.get("orderLineId"), Long.valueOf(value)));	
					break;
				default:
					searchColumn=true;
					break;
				}
			}
			else {	
				try {
				predicates.add(criteriabuilder.equal(root.get("order").get("orderId"), Long.valueOf(value)));
				}
				catch (NumberFormatException e) {
		            response.put("message", WebServiceUtil.INVALID_SEARCH_VALUE);
		            return response;
		        }
			}		
			}
		
		if (searchColumn) {
			response.put("message", WebServiceUtil.INVALID_SEARCH_COLUMN);
			return response;
		}
		criteriaQuery.select(criteriabuilder.construct(OrderLineItemDetailsDto.class,
			    root.get("orderLineId"),
			    root.get("order").get("orderId"),
			    productJoin.get("productId"),
			    productJoin.get("productName"),
			    productJoin.get("productPrice"),
			    root.get("orderQuantityIndividualUnit"),
			    root.get("orderQuantityInPackage"),
			    root.get("orderStatus").get("description"), 
			    root.get("createdDate"),                     
			    root.get("updateDate"),                     
			    root.get("price"))
			).where(criteriabuilder.and(predicates.toArray(new Predicate[0])));
		

		if (!commonListRequestModel.getOrderBy().isEmpty() && !commonListRequestModel.getOrderType().isEmpty()) {
			switch (commonListRequestModel.getOrderBy()) {
			case WebServiceUtil.SORT_BY_SNO:
				if (WebServiceUtil.SORT_ASECENDING.equalsIgnoreCase(commonListRequestModel.getOrderType())) {
					criteriaQuery.orderBy(criteriabuilder.asc(root.get("orderLineId")));
				}
				else if((WebServiceUtil.SORT_DESCENDING.equalsIgnoreCase(commonListRequestModel.getOrderType()))){
					criteriaQuery.orderBy(criteriabuilder.desc(root.get("orderLineId")));
				}
				break;
			default:
				if (WebServiceUtil.SORT_ASECENDING.equalsIgnoreCase(commonListRequestModel.getOrderType()))
					criteriaQuery.orderBy(criteriabuilder.asc(root.get(commonListRequestModel.getOrderBy())));
				else
					criteriaQuery.orderBy(criteriabuilder.desc(root.get(commonListRequestModel.getOrderBy())));
				break;
			}
		}
		
		TypedQuery<OrderLineItemDetailsDto> queryresult = entityManager.createQuery(criteriaQuery);

		if (commonListRequestModel.getStart() != null || commonListRequestModel.getLength() != null) {
			queryresult.setFirstResult(commonListRequestModel.getStart());
			queryresult.setMaxResults(commonListRequestModel.getLength());
		}
		List<OrderLineItemDetailsDto> results = queryresult.getResultList();

		CriteriaQuery<Long> totalQuery = criteriabuilder.createQuery(Long.class);
		totalQuery.select(criteriabuilder.count(totalQuery.from(OrderLineItemDetailsModel.class)));
		Long totalCount = entityManager.createQuery(totalQuery).getSingleResult();

		CriteriaQuery<Long> filterCountQuery = criteriabuilder.createQuery(Long.class);
		Root<OrderLineItemDetailsModel> filterRoot = filterCountQuery.from(OrderLineItemDetailsModel.class);
		filterCountQuery.select(criteriabuilder.count(filterRoot))
				.where(criteriabuilder.and(predicates.toArray(new Predicate[predicates.size()])));
		Long filteredCount = entityManager.createQuery(filterCountQuery).getSingleResult();
		
		int start = commonListRequestModel.getStart(); 

		if (WebServiceUtil.SORT_BY_SNO.equalsIgnoreCase(commonListRequestModel.getOrderBy())
		        && WebServiceUtil.SORT_DESCENDING.equalsIgnoreCase(commonListRequestModel.getOrderType())) {

		    for (int i = 0; i < results.size(); i++) {
		        results.get(i).setSno((int) (totalCount - (start + i)));
		    }

		} else {
		    for (int i = 0; i < results.size(); i++) {
		        results.get(i).setSno(start + i + 1);
		    }
		}
		

		response.put("status", WebServiceUtil.SUCCESS_STATUS);
		response.put("totalCount", totalCount);
		response.put("filterCount", filteredCount);
		response.put("data", results);

		return response;
	}

	@Override
	public OrderLineItemDetailsDto getOrderLineItemDetailsById(Long orderlineid) {
		CriteriaBuilder criteriabuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<OrderLineItemDetailsDto> criteriaQuery = criteriabuilder.createQuery(OrderLineItemDetailsDto.class);
		Root<OrderLineItemDetailsModel> root = criteriaQuery.from(OrderLineItemDetailsModel.class);
		Join<OrderLineItemDetailsModel,ProductModel> productJoin = root.join("product");

		criteriaQuery.select(criteriabuilder.construct(OrderLineItemDetailsDto.class,
			    root.get("orderLineId"),
			    root.get("order").get("orderId"),
			    productJoin.get("productId"),
			    productJoin.get("productName"),
			    productJoin.get("productPrice"),
			    root.get("orderQuantityIndividualUnit"),
			    root.get("orderQuantityInPackage"),
			    root.get("orderStatus").get("description"), 
			    root.get("createdDate"),                     
			    root.get("updateDate"),                     
			    root.get("price"))
			).where(criteriabuilder.equal(root.get("orderLineId"), orderlineid));
		return entityManager.createQuery(criteriaQuery).getResultStream().findFirst().orElse(null);
	}

}
