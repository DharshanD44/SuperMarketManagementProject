package com.supermarketmanagement.api.daoImp;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListRequest;
import com.supermarketmanagement.api.Model.Custom.SupplierDetails.SupplierAssignedOrderList;
import com.supermarketmanagement.api.Model.Custom.SupplierDetails.SupplierListDto;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;
import com.supermarketmanagement.api.Model.Entity.ProductModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Model.Entity.SuppliersModel;
import com.supermarketmanagement.api.Repository.SupplierDetailsRepository;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;
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
	public Map<String, Object> getSuppliersDetails(CustomerListRequest commonListRequestModel) {
		CriteriaBuilder criteriabuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<SupplierListDto> criteriaQuery = criteriabuilder.createQuery(SupplierListDto.class);
	    Root<SuppliersModel> root = criteriaQuery.from(SuppliersModel.class);
		Join<SuppliersModel,SuperMarketCode> codeJoin = root.join("isActive");

	    Map<String, Object> response = new LinkedHashMap<>();
	    List<Predicate> predicates = new ArrayList<>();

	    if (commonListRequestModel.getIsActive() != null) {

			if (commonListRequestModel.getIsActive()) {
				predicates.add(criteriabuilder.equal(codeJoin.get("code"), WebServiceUtil.STATUS_ACTIVE));
			} else {
				predicates.add(criteriabuilder.equal(codeJoin.get("code"), WebServiceUtil.STATUS_INACTIVE));
			}
		}
	    
	    boolean searchColumn =false;
	    if (!commonListRequestModel.getSearchBy().isEmpty() && !commonListRequestModel.getSearchValue().isEmpty()) {
			String value = commonListRequestModel.getSearchValue().toLowerCase();
			switch (commonListRequestModel.getSearchBy().toLowerCase()) {
			case WebServiceUtil.SUPPLIER_NAME:
				predicates.add(criteriabuilder.like(criteriabuilder.concat(" ", criteriabuilder.concat(criteriabuilder.lower(root.get("supplierName")), " ")),
						"% " + value + " %"));
				break;
			case WebServiceUtil.SUPPLIER_MOBILE_NO:
				predicates.add(
						criteriabuilder.equal(root.get("supplierMobileNumber"), Long.valueOf(commonListRequestModel.getSearchValue())));
				break;
			case WebServiceUtil.SUPPLIER_EMAIL_ID:
				predicates.add(criteriabuilder.equal(criteriabuilder.lower(root.get("supplierEmailId")),value));
				break;
			default:
				searchColumn=true;
				break;
			}
		}
	    if(searchColumn) {
			response.put("status", WebServiceUtil.FAILED_STATUS);
	        response.put("message", WebServiceUtil.INVALID_SEARCH_COLUMN);
	        return response;
		}
	    predicates.add(criteriabuilder.equal(root.get("deleteFlag"), false));
	    criteriaQuery.multiselect(
	            root.get("supplierId"),
	            root.get("supplierName"),
	            root.get("supplierEmailId"),
	            root.get("supplierMobileNumber"),
	            root.get("address"),
	            root.get("city"),
	            root.get("pincode"),
	            root.get("country"),
	            codeJoin.get("description")
	    )
	    .where(criteriabuilder.and(predicates.toArray(new Predicate[predicates.size()])));

	    if (!commonListRequestModel.getOrderBy().isEmpty() && !commonListRequestModel.getOrderType().isEmpty()) {
			switch (commonListRequestModel.getOrderBy()) {
			case WebServiceUtil.SORT_BY_SNO:
				if (WebServiceUtil.SORT_ASECENDING.equalsIgnoreCase(commonListRequestModel.getOrderType())) {
					criteriaQuery.orderBy(criteriabuilder.asc(root.get("supplierId")));
				} 
				else if((WebServiceUtil.SORT_DESCENDING.equalsIgnoreCase(commonListRequestModel.getOrderType()))){
					criteriaQuery.orderBy(criteriabuilder.desc(root.get("supplierId")));
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
	    
	    TypedQuery<SupplierListDto> queryresult = entityManager.createQuery(criteriaQuery);

		if (commonListRequestModel.getStart() != null || commonListRequestModel.getLength() != null) {
			queryresult.setFirstResult(commonListRequestModel.getStart());
			queryresult.setMaxResults(commonListRequestModel.getLength());
		}
		
		List<SupplierListDto> results = queryresult.getResultList();

		CriteriaQuery<Long> totalQuery = criteriabuilder.createQuery(Long.class);
		totalQuery.select(criteriabuilder.count(totalQuery.from(SuppliersModel.class)));
		Long totalCount = entityManager.createQuery(totalQuery).getSingleResult();

		CriteriaQuery<Long> filterCountQuery = criteriabuilder.createQuery(Long.class);
		Root<SuppliersModel> filterRoot = filterCountQuery.from(SuppliersModel.class);
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
		response.put("data", results);
		response.put("totalCount", totalCount);
		response.put("filterCount", filteredCount);

		return response;
	}

	@Override
	public Object getAssignedOrderDetails(Long supplierId) {
	    CriteriaBuilder criteriabuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<SupplierAssignedOrderList> cq = criteriabuilder.createQuery(SupplierAssignedOrderList.class);

	    Root<OrderLineItemDetailsModel> lineItemRoot = cq.from(OrderLineItemDetailsModel.class);
	    Join<OrderLineItemDetailsModel, OrderDetailsModel> orderJoin = lineItemRoot.join("order");
	    Join<OrderLineItemDetailsModel, ProductModel> productJoin = lineItemRoot.join("product");

	    cq.select(criteriabuilder.construct(
	            SupplierAssignedOrderList.class,
	            lineItemRoot.get("orderLineId"),
	            orderJoin.get("orderId"),
	            productJoin.get("productName"),
	            lineItemRoot.get("orderQuantityIndividualUnit"),
	            lineItemRoot.get("orderQuantityInPackage"),
	            orderJoin.get("orderStatus").get("description")
	    ))
	    .where(criteriabuilder.equal(orderJoin.get("assignedTo"), supplierId));

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

	@Override
	public SuppliersModel findByEmail(String email) {
		return supplierDetailsRepository.findBySupplierEmailId(email);
	}

	@Override
	public SuppliersModel findByMobile(Long mobileNumber) {
		return supplierDetailsRepository.findBySupplierMobileNumber(mobileNumber);
	}


}
