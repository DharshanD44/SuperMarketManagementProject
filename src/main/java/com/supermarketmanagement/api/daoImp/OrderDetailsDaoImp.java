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

import com.google.cloud.Date;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderDetailsListDto;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.OrderLineItemDetailsDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Repository.OrderDetailsRepoistory;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.OrderDetailsDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
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
	public Map<String, Object> getOrderListDetails(CommonListRequestModel commonListRequestModel) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<OrderDetailsListDto> criteriaQuery = criteriaBuilder.createQuery(OrderDetailsListDto.class);
		Root<OrderDetailsModel> root = criteriaQuery.from(OrderDetailsModel.class);
		Join<OrderDetailsModel,SuperMarketCode> codeJoin = root.join("orderStatus");
		Join<OrderDetailsModel,CustomerModel> customerJoin = root.join("customer");
		
		List<Predicate> predicates = new ArrayList<>();
		Map<String, Object> response = new LinkedHashMap<>();

		boolean serachColumn = false;

		if (!commonListRequestModel.getSearchValue().isEmpty()) {
			String value = commonListRequestModel.getSearchValue();
			if (!commonListRequestModel.getSearchBy().isEmpty()) {
				switch (commonListRequestModel.getSearchBy().toLowerCase()) {
				case WebServiceUtil.ORDER_STATUS:
					predicates.add(criteriaBuilder.equal(root.get("orderStatus").get("code"), value.toUpperCase()));
					break;
				case WebServiceUtil.ORDER_DATE:
					LocalDate searchDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
					java.sql.Date sqlDate = java.sql.Date.valueOf(searchDate);
					predicates.add(criteriaBuilder.equal(criteriaBuilder.function("DATE", Date.class, root.get("orderDate")), sqlDate));
					break;
				case WebServiceUtil.CUSTOMER_ID:
					predicates.add(criteriaBuilder.equal(root.get("customer").get("customerId"), Long.valueOf(value)));
					break;
				default:
					serachColumn = true;
					break;
				}
			} else {
				try {
					predicates.add(criteriaBuilder.equal(root.get("orderId"), Long.valueOf(value)));
				} catch (NumberFormatException e) {
					response.put("message", WebServiceUtil.INVALID_SEARCH_VALUE);
					return response;
				}
			}

		}
		if (serachColumn) {
			response.put("message", WebServiceUtil.INVALID_SEARCH_COLUMN);
			return response;
		}
		criteriaQuery.select(criteriaBuilder.construct(OrderDetailsListDto.class,
						root.get("orderId"), 
						root.get("orderDate"), 
						customerJoin.get("customerId"),
						customerJoin.get("customerFirstName"),
						customerJoin.get("customerMiddleName"),
						customerJoin.get("customerLastName"),
						customerJoin.get("customerMobileno"),
						customerJoin.get("customerEmail"),
						root.get("orderExpectedDate"), 
						root.get("updateDate"),
						codeJoin.get("description"),
						root.get("totalprice")))
				.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));


		if (!commonListRequestModel.getOrderBy().isEmpty() && !commonListRequestModel.getOrderType().isEmpty()) {
			switch (commonListRequestModel.getOrderBy()) {
			case WebServiceUtil.SORT_BY_SNO:
				if (WebServiceUtil.SORT_ASECENDING.equalsIgnoreCase(commonListRequestModel.getOrderType())) 
					criteriaQuery.orderBy(criteriaBuilder.asc(root.get("orderId")));
				else if((WebServiceUtil.SORT_DESCENDING.equalsIgnoreCase(commonListRequestModel.getOrderType())))
					criteriaQuery.orderBy(criteriaBuilder.desc(root.get("orderId")));
				break;
			default:
				if (WebServiceUtil.SORT_ASECENDING.equalsIgnoreCase(commonListRequestModel.getOrderType()))
					criteriaQuery.orderBy(criteriaBuilder.asc(root.get(commonListRequestModel.getOrderBy())));
				else
					criteriaQuery.orderBy(criteriaBuilder.desc(root.get(commonListRequestModel.getOrderBy())));
				break;
			}
		}
		TypedQuery<OrderDetailsListDto> queryresult = entityManager.createQuery(criteriaQuery);

		if (commonListRequestModel.getStart() != null) {
			queryresult.setFirstResult(commonListRequestModel.getStart());
		}
		if (commonListRequestModel.getLength() != null) {
			queryresult.setMaxResults(commonListRequestModel.getLength());
		}

		List<OrderDetailsListDto> results = queryresult.getResultList();

		CriteriaQuery<Long> totalQuery = criteriaBuilder.createQuery(Long.class);
		totalQuery.select(criteriaBuilder.count(totalQuery.from(OrderDetailsModel.class)));
		Long totalCount = entityManager.createQuery(totalQuery).getSingleResult();


		CriteriaQuery<Long> filterCountQuery = criteriaBuilder.createQuery(Long.class);
		Root<OrderDetailsModel> filterRoot = filterCountQuery.from(OrderDetailsModel.class);
		filterCountQuery.select(criteriaBuilder.count(filterRoot)).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
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
	public OrderDetailsListDto getOrderDetailsById(Long orderid) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<OrderDetailsListDto> criteriaQuery = criteriaBuilder.createQuery(OrderDetailsListDto.class);
		Root<OrderDetailsModel> root = criteriaQuery.from(OrderDetailsModel.class);
		Join<OrderDetailsListDto,SuperMarketCode> codeJoin = root.join("orderStatus");
		Join<OrderDetailsModel,CustomerModel> customerJoin = root.join("customer");
		criteriaQuery.select(criteriaBuilder.construct(OrderDetailsListDto.class,
				root.get("orderId"), 
				root.get("orderDate"), 
				customerJoin.get("customerId"),
				customerJoin.get("customerFirstName"),
				customerJoin.get("customerMiddleName"),
				customerJoin.get("customerLastName"),
				customerJoin.get("customerMobileno"),
				customerJoin.get("customerEmail"),
				root.get("orderExpectedDate"), 
				root.get("updateDate"),
				codeJoin.get("description"),
				root.get("totalprice")))
		.where(criteriaBuilder.equal(root.get("orderId"), orderid));

		return entityManager.createQuery(criteriaQuery).getResultStream().findFirst().orElse(null);
	}

	@Override
	public List<OrderLineItemDetailsDto> findOrderLineItemStatus(Long orderId) {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<OrderLineItemDetailsDto> cq = cb.createQuery(OrderLineItemDetailsDto.class);

	    Root<OrderLineItemDetailsModel> root = cq.from(OrderLineItemDetailsModel.class);

	    cq.select(cb.construct(
	            OrderLineItemDetailsDto.class,
	            root.get("orderLineId"),
	            root.get("order").get("orderId"),
	            root.get("orderStatus").get("code") 
	    )).where(cb.equal(root.get("order").get("orderId"), orderId));

	    return entityManager.createQuery(cq).getResultList();
	}


}
