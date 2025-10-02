package com.supermarketmanagement.api.daoImp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListRequest;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Repository.CustomerRepoistory;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.CustomerDao;
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
public class CustomerDaoImp implements CustomerDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	CustomerRepoistory customerRepoistory;


	@Override
	public Map<String, Object> getCustomerDetails(CustomerListRequest commonListRequestModel) {
		CriteriaBuilder criteriabuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<CustomerListDto> criteriaQuery = criteriabuilder.createQuery(CustomerListDto.class);
		Root<CustomerModel> root = criteriaQuery.from(CustomerModel.class);
		
		List<Predicate> predicates = new ArrayList<>();
		Map<String, Object> response = new LinkedHashMap<>();

		if (commonListRequestModel.getIsActive() != null) {

			if (commonListRequestModel.getIsActive()) {
				predicates.add(criteriabuilder.equal(root.get("customerStatus").get("code"), WebServiceUtil.STATUS_ACTIVE));
			} else {
				predicates.add(criteriabuilder.equal(root.get("customerStatus").get("code"), WebServiceUtil.STATUS_INACTIVE));
			}
		}

		boolean searchColumn = false;
		if (!commonListRequestModel.getSearchBy().isEmpty() && !commonListRequestModel.getSearchValue().isEmpty()) {

			String value = commonListRequestModel.getSearchValue().toLowerCase();
			switch (commonListRequestModel.getSearchBy().toLowerCase()) {
			case WebServiceUtil.CUSTOMER_NAME:
				predicates
						.add(criteriabuilder.like(criteriabuilder.lower(root.get("customerName")), "%" + value + "%"));

				break;
			case WebServiceUtil.CUSTOMER_MOBILE_NO:
				predicates.add(criteriabuilder.equal(root.get("customerMobileno"),
						Long.valueOf(commonListRequestModel.getSearchValue())));

				break;
			case WebServiceUtil.CUSTOMER_EMAIL:
				predicates.add(criteriabuilder.like(criteriabuilder.lower(root.get("customerEmail")),
						value.toLowerCase() + "%"));
				break;
			default:
				searchColumn = true;
				break;
			}
		}

		if (searchColumn) {
			response.put("status", WebServiceUtil.FAILED_STATUS);
			response.put("message", WebServiceUtil.INVALID_SEARCH_COLUMN);
			return response;
		}
		if (!commonListRequestModel.getSearchBy().isEmpty() && commonListRequestModel.getSearchValue().isEmpty()) {
			response.put("status", WebServiceUtil.WARNING_STATUS);
			response.put("message", WebServiceUtil.SEARCH_VALUE_NULL);
			return response;
		}
		predicates.add(criteriabuilder.isNull(root.get("customerLastEffectiveDate")));

		criteriaQuery.multiselect(
				root.get("customerId"), 
				root.get("customerFirstName"),
				root.get("customerMiddleName"),
				root.get("customerLastName"),
				root.get("customerGender").get("description"), 
				root.get("customerMobileno"),
				root.get("customerAddress"),
				root.get("customerLocation"), 
				root.get("customerCity"),
				root.get("customerPincode"), 
				root.get("customerEmail"), 
				root.get("customerStatus").get("description"))
				.where(criteriabuilder.and(predicates.toArray(new Predicate[0])));

		if (!commonListRequestModel.getOrderBy().isEmpty() && !commonListRequestModel.getOrderType().isEmpty()) {
			switch (commonListRequestModel.getOrderBy()) {
			case WebServiceUtil.SORT_BY_SNO:
				if (WebServiceUtil.SORT_ASECENDING.equalsIgnoreCase(commonListRequestModel.getOrderType())) 
					criteriaQuery.orderBy(criteriabuilder.asc(root.get("customerId")));
				else if((WebServiceUtil.SORT_DESCENDING.equalsIgnoreCase(commonListRequestModel.getOrderType())))
					criteriaQuery.orderBy(criteriabuilder.desc(root.get("customerId")));
				break;
			default:
				if (WebServiceUtil.SORT_ASECENDING.equalsIgnoreCase(commonListRequestModel.getOrderType()))
					criteriaQuery.orderBy(criteriabuilder.asc(root.get(commonListRequestModel.getOrderBy())));
				else
					criteriaQuery.orderBy(criteriabuilder.desc(root.get(commonListRequestModel.getOrderBy())));
				break;
			}
		}
		
		TypedQuery<CustomerListDto> queryresult = entityManager.createQuery(criteriaQuery);

		if (commonListRequestModel.getStart() != null) {
			queryresult.setFirstResult(commonListRequestModel.getStart());
		}
		if (commonListRequestModel.getLength() != null) {
			queryresult.setMaxResults(commonListRequestModel.getLength());
		}

		
		List<CustomerListDto> result = queryresult.getResultList();

		CriteriaQuery<Long> totalQuery = criteriabuilder.createQuery(Long.class);
		totalQuery.select(criteriabuilder.count(totalQuery.from(CustomerModel.class)));
		Long totalCount = entityManager.createQuery(totalQuery).getSingleResult();

		CriteriaQuery<Long> filterCountQuery = criteriabuilder.createQuery(Long.class);
		Root<CustomerModel> filterRoot = filterCountQuery.from(CustomerModel.class);
		filterCountQuery.select(criteriabuilder.count(filterRoot))
				.where(criteriabuilder.and(predicates.toArray(new Predicate[0])));
		Long filteredCount = entityManager.createQuery(filterCountQuery).getSingleResult();
		
		int start = commonListRequestModel.getStart(); 

		if (WebServiceUtil.SORT_BY_SNO.equalsIgnoreCase(commonListRequestModel.getOrderBy())
		        && WebServiceUtil.SORT_DESCENDING.equalsIgnoreCase(commonListRequestModel.getOrderType())) {

		    for (int i = 0; i < result.size(); i++) {
		        result.get(i).setSno((int) (totalCount - (start + i)));
		    }

		} else {
		    for (int i = 0; i < result.size(); i++) {
		        result.get(i).setSno(start + i + 1);
		    }
		}

		response.put("status", WebServiceUtil.SUCCESS_STATUS);
		response.put("totalCount", totalCount);
		response.put("filterCount", filteredCount);
		response.put("data", result);

		return response;
	}

	@Override
	public CustomerModel findByCustomerId(Long customerId) {
		return customerRepoistory.findByCustomerId(customerId);
	}

	@Override
	public void saveCustomer(CustomerModel entity) {
		customerRepoistory.save(entity);
	}

	@Override
	public SuperMarketCode find(Class<SuperMarketCode> class1, String string) {
		return entityManager.find(SuperMarketCode.class, "NEW");
	}

	@Override
	public CustomerListDto findCustomerDetailsById(Long customerid) {
		CriteriaBuilder criteriabuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CustomerListDto> criteriaQuery = criteriabuilder.createQuery(CustomerListDto.class);
		Root<CustomerModel> root = criteriaQuery.from(CustomerModel.class);

		criteriaQuery.multiselect(
				root.get("customerId"),
				root.get("customerFirstName"),
				root.get("customerMiddleName"),
				root.get("customerLastName"),
				root.get("customerGender").get("description"),
				root.get("customerMobileno"),
				root.get("customerAddress"),
				root.get("customerLocation"), 
				root.get("customerCity"),
				root.get("customerPincode"), 
				root.get("customerEmail"), 
				root.get("customerStatus").get("description"))
				.where(criteriabuilder.equal(root.get("customerId"), customerid));
		return entityManager.createQuery(criteriaQuery).getResultStream().findFirst().orElse(null);
	}
}
