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
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderDetailsListDto;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Repository.OrderDetailsRepoistory;
import com.supermarketmanagement.api.Util.WebServiceUtil;
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
		Predicate predicate = cb.equal(orderDetailsModelRoot.get("orderId"), orderid);
		criteriaQuery
				.multiselect(orderDetailsModelRoot.get("orderId"), orderDetailsModelRoot.get("orderDate"),
						orderDetailsModelRoot.get("customer").get("customerId"),
						orderDetailsModelRoot.get("orderExpectedDate"), orderDetailsModelRoot.get("orderStatus"),
						orderDetailsModelRoot.get("updateDate"), orderDetailsModelRoot.get("totalprice"))
				.where(predicate);

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public Map<String, Object> getOrderListDetails(CommonListRequestModel commonListRequestModel) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<OrderDetailsListDto> cq = cb.createQuery(OrderDetailsListDto.class);
		Root<OrderDetailsModel> root = cq.from(OrderDetailsModel.class);

		List<Predicate> predicates = new ArrayList<>();

		if (commonListRequestModel.getSearchBy() != null && commonListRequestModel.getSearchValue() != null) {
			String value =commonListRequestModel.getSearchValue();
			switch (commonListRequestModel.getSearchBy().toLowerCase()) {
			case "orderstatus":
			    predicates.add(cb.equal(root.get("orderStatus").get("code"), value));
				break;
			case "orderid":
				predicates.add(cb.equal(root.get("orderId"), Integer.valueOf(value)));
				break;
			case "orderdate":
				 LocalDate searchDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				    java.sql.Date sqlDate = java.sql.Date.valueOf(searchDate);
				    predicates.add(cb.equal(
				        cb.function("DATE", Date.class, root.get("orderDate")),
				        sqlDate
				    ));
				break;
			default:
				break;
			}
		}

		cq.multiselect(root.get("orderId"), root.get("orderDate"), root.get("customer").get("customerId"),
				root.get("orderExpectedDate"), root.get("updateDate"), root.get("orderStatus").get("description"),
				root.get("totalprice")).where(cb.and(predicates.toArray(new Predicate[0])));

		List<OrderDetailsListDto> results = entityManager.createQuery(cq).getResultList();

		CriteriaQuery<Long> totalQuery = cb.createQuery(Long.class);
		totalQuery.select(cb.count(totalQuery.from(OrderDetailsModel.class)));
		Long totalCount = entityManager.createQuery(totalQuery).getSingleResult();

		CriteriaQuery<Long> filterCountQuery = cb.createQuery(Long.class);
		Root<OrderDetailsModel> filterRoot = filterCountQuery.from(OrderDetailsModel.class);
		filterCountQuery.select(cb.count(filterRoot)).where(cb.and(predicates.toArray(new Predicate[0])));
		Long filteredCount = entityManager.createQuery(filterCountQuery).getSingleResult();

		Map<String, Object> response = new LinkedHashMap<>();
		response.put("status", WebServiceUtil.SUCCESS_STATUS);
		response.put("data", results);
		response.put("total", totalCount);
		response.put("filtered", filteredCount);

		return response;
	}

}
