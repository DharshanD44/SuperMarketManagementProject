package com.supermarketmanagement.api.daoImp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails.OrderLineItemDetailsDto;
import com.supermarketmanagement.api.Model.Custom.SupplierDetails.SupplierAssignedOrderList;
import com.supermarketmanagement.api.Model.Custom.SupplierDetails.SupplierListDto;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Model.Entity.SuppliersModel;
import com.supermarketmanagement.api.Repository.SupplierDetailsRepository;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.SupplierDetailsDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class SupplierDetailsDaoImp implements SupplierDetailsDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private SupplierDetailsRepository supplierDetailsRepository;

	@Override
	public Map<String, Object> getSuppliersDetails(CommonListRequestModel commonListRequestModel) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

	    CriteriaQuery<SupplierListDto> criteriaQuery = cb.createQuery(SupplierListDto.class);
	    Root<SuppliersModel> root = criteriaQuery.from(SuppliersModel.class);

	    List<Predicate> predicates = new ArrayList<>();

	    if (commonListRequestModel.getSearchBy() != null && commonListRequestModel.getSearchValue() != null) {
			String value = commonListRequestModel.getSearchValue().toLowerCase();
			switch (commonListRequestModel.getSearchBy().toLowerCase()) {
			case "suppliername":
				predicates.add(cb.like(cb.concat(" ", cb.concat(cb.lower(root.get("supplierName")), " ")),
						"% " + value + " %"));
				break;
			case "mobilenumber":
				predicates.add(
						cb.equal(root.get("supplierMobileNumber"), Long.valueOf(commonListRequestModel.getSearchValue())));
				break;
			case "emailid":
				predicates.add(cb.equal(cb.lower(root.get("supplierEmailId")),value));
				break;
			default:
				break;
			}
		}

	    criteriaQuery.multiselect(
	            root.get("supplierId"),
	            root.get("supplierName"),
	            root.get("supplierEmailId"),
	            root.get("supplierMobileNumber"),
	            root.get("address"),
	            root.get("city"),
	            root.get("pincode"),
	            root.get("country"),
	            root.get("isActive")
	    )
	    .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));


	    TypedQuery<SupplierListDto> queryresult = entityManager.createQuery(criteriaQuery);

		if (commonListRequestModel.getStart() != null || commonListRequestModel.getLength() != null) {
			queryresult.setFirstResult(commonListRequestModel.getStart());
			queryresult.setMaxResults(commonListRequestModel.getLength());
		}
	    List<SupplierListDto> results = queryresult.getResultList();

	    CriteriaQuery<Long> totalQuery = cb.createQuery(Long.class);
	    totalQuery.select(cb.count(totalQuery.from(SuppliersModel.class)));
	    Long totalCount = entityManager.createQuery(totalQuery).getSingleResult();

	    CriteriaQuery<Long> filterCountQuery = cb.createQuery(Long.class);
	    Root<SuppliersModel> filterRoot = filterCountQuery.from(SuppliersModel.class);
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
			response.put("data", results);
			response.put("totalCount", totalCount);
			response.put("filteredCount", filteredCount);
		}
		return response;	
	}

	@Override
	public Object getAssignedOrderDetails(Long supplierId) {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<SupplierAssignedOrderList> cq = cb.createQuery(SupplierAssignedOrderList.class);

	    Root<OrderLineItemDetailsModel> lineItemRoot = cq.from(OrderLineItemDetailsModel.class);
	    Join<OrderLineItemDetailsModel, OrderDetailsModel> orderJoin = lineItemRoot.join("order");
	    Join<OrderLineItemDetailsModel, ProductModel> productJoin = lineItemRoot.join("product");

	    cq.select(cb.construct(
	            SupplierAssignedOrderList.class,
	            lineItemRoot.get("orderLineId"),
	            orderJoin.get("orderId"),
	            productJoin.get("productName"),
	            lineItemRoot.get("orderQuantityIndividualUnit"),
	            lineItemRoot.get("orderQuantityInPackage"),
	            orderJoin.get("orderStatus").get("description")
	    ))
	    .where(cb.equal(orderJoin.get("assignedTo"), supplierId));

	    return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public SuppliersModel findBySupplierId(Long supplierid) {
		return supplierDetailsRepository.findBySupplierId(supplierid);
	}

	@Override
	public void saveSupplierDetails(SuppliersModel supplierList) {
		  supplierDetailsRepository.save(supplierList);
	}


}
