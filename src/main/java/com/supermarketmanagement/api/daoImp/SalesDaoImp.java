package com.supermarketmanagement.api.daoImp;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.supermarketmanagement.api.Model.Custom.Product.SalesProductListDto;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.SalesDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

@Repository
public class SalesDaoImp implements SalesDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Map<String, Object> findTopSellingProducts(String filter, LocalDateTime startDate,
			LocalDateTime endDate) {
		LocalDateTime now = LocalDateTime.now();

		if (filter != null) {
			switch (filter.toUpperCase()) {
			case "TODAY":
				startDate = now.toLocalDate().atStartOfDay();
				endDate = now.toLocalDate().atTime(23, 59, 59);
				break;

			case "WEEKLY":
				startDate = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
				endDate = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toLocalDate().atTime(23, 59, 59);
				break;

			case "MONTHLY":
				startDate = now.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay();
				endDate = now.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate().atTime(23, 59, 59);
				break;

			case "CUSTOM":
				if (startDate == null || endDate == null) {
					Map<String, Object> response = new LinkedHashMap<String, Object>();
					response.put("status", WebServiceUtil.FAILED_STATUS);
					response.put("message", WebServiceUtil.START_AND_END_DATE);
					return response;
				}
				break;
			default:
				throw new IllegalArgumentException("Invalid filter: " + filter);
			}
		}

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<OrderLineItemDetailsModel> root = cq.from(OrderLineItemDetailsModel.class);

		Join<OrderLineItemDetailsModel, ProductModel> productJoin = root.join("product");
		Expression<Long> totalSold = cb.sum(root.get("orderQuantityInPackage"));

		cq.multiselect(productJoin.get("productId").alias("productId"),
				productJoin.get("productName").alias("productName"),
				productJoin.get("productPackageUnitOfMeasure").alias("productPackageUnitOfMeasure"),
				productJoin.get("productPackQuantity").alias("productPackQuantity"),
				productJoin.get("productPrice").alias("productPrice"),
				productJoin.get("productEffectiveDate").alias("productEffectiveDate"),
				productJoin.get("productLastEffectiveDate").alias("productLastEffectiveDate"),
				totalSold.alias("totalPackagesSold")).where(cb.between(root.get("createdDate"), startDate, endDate))
				.groupBy(productJoin.get("productId"), productJoin.get("productName"),
						productJoin.get("productPackageUnitOfMeasure"), productJoin.get("productPackQuantity"),
						productJoin.get("productPrice"), productJoin.get("productEffectiveDate"),
						productJoin.get("productLastEffectiveDate"))
				.orderBy(cb.desc(totalSold));

		List<Tuple> tuples = entityManager.createQuery(cq).getResultList();
		List<SalesProductListDto> filterresponse = new ArrayList<>();

		for (Tuple tuple : tuples) {
			filterresponse.add(new SalesProductListDto(tuple.get("productId", Long.class),
					tuple.get("productName", String.class), tuple.get("productPackageUnitOfMeasure", String.class),
					tuple.get("productPackQuantity", Integer.class), tuple.get("productPrice", Double.class),
					tuple.get("productEffectiveDate", LocalDate.class),
					tuple.get("productLastEffectiveDate", LocalDate.class),
					tuple.get("totalPackagesSold", Long.class)));
		}
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", WebServiceUtil.SUCCESS_STATUS);
		response.put("data", filterresponse);
		return response;
	}

}
