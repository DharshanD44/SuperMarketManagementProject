package com.supermarketmanagement.api.daoImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderDetailsListDto;
import com.supermarketmanagement.api.Model.Custom.Product.ProductListDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Repository.CustomerRepoistory;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.CustomerDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;


@Repository
@Transactional
public class CustomerDaoImp implements CustomerDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	CustomerRepoistory customerRepoistory;

	@Override
	public Map<String, Object> getCustomerDetails(CommonListRequestModel commonListRequestModel) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

	    CriteriaQuery<CustomerListDto> criteriaQuery = cb.createQuery(CustomerListDto.class);
	    Root<CustomerModel> root = criteriaQuery.from(CustomerModel.class);

	    List<Predicate> predicates = new ArrayList<>();

	    if (commonListRequestModel.getSearchBy() != null && commonListRequestModel.getSearchValue() != null) {
	        String value = "%" + commonListRequestModel.getSearchValue().toLowerCase() + "%";
	        switch (commonListRequestModel.getSearchBy().toLowerCase()) {
	            case "customername":
	                predicates.add(cb.like(cb.lower(root.get("customerName")), value));
	                break;
	            case "mobileno":
	                predicates.add(cb.equal(root.get("customerMobileno"),Long.valueOf(commonListRequestModel.getSearchValue())));
	                break;
	            case "emailid":
	                predicates.add(cb.equal(root.get("customerEmail"),commonListRequestModel.getSearchValue()));
	                break;
	            default:
	                break;
	        }
	    }
		criteriaQuery.multiselect(
			    root.get("customerId"),
			    root.get("customerName"),
			    root.get("customerGender").get("description"),
			    root.get("customerMobileno"),
			    root.get("customerAddress"),
			    root.get("customerLocation"),
			    root.get("customerCity"),
			    root.get("customerPincode"),
			    root.get("customerEmail")
		).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		TypedQuery<CustomerListDto> result = entityManager.createQuery(criteriaQuery);

	    if (commonListRequestModel.getStart() != null) {
	    	result.setFirstResult(commonListRequestModel.getStart());
	    }
	    if (commonListRequestModel.getLength() != null) {
	    	result.setMaxResults(commonListRequestModel.getLength());
	    }
	    
	    CriteriaQuery<Long> totalQuery = cb.createQuery(Long.class);
	    totalQuery.select(cb.count(totalQuery.from(CustomerModel.class)));
	    Long totalCount = entityManager.createQuery(totalQuery).getSingleResult();

	    CriteriaQuery<Long> filterCountQuery = cb.createQuery(Long.class);
	    Root<CustomerModel> filterRoot = filterCountQuery.from(CustomerModel.class);
	    filterCountQuery.select(cb.count(filterRoot))
	            .where(cb.and(predicates.toArray(new Predicate[0])));
	    Long filteredCount = entityManager.createQuery(filterCountQuery).getSingleResult();
	    
	    Map<String, Object> response = new LinkedHashMap<>();
	    response.put("status", WebServiceUtil.SUCCESS_STATUS);
	    response.put("data", result.getResultList());
	    response.put("totalCount", totalCount);
	    response.put("filteredCount", filteredCount); 
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
	public CustomerListDto findCustomerDetailsById(Long id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CustomerListDto> criteriaQuery = cb.createQuery(CustomerListDto.class);
		
		Root<CustomerModel> root = criteriaQuery.from(CustomerModel.class);
		Predicate lastEffective = root.get("customerLastEffectiveDate").isNull();
		Predicate customerid = cb.equal(root.get("customerId"),id);

		criteriaQuery.multiselect(
			    root.get("customerId"),
			    root.get("customerName"),
			    root.get("customerMobileno"),
			    root.get("customerAddress"),
			    root.get("customerLocation"),
			    root.get("customerCity"),
			    root.get("customerPincode"),
			    root.get("customerEmail")
			).where(cb.and(lastEffective,customerid));
		try {
	        return entityManager.createQuery(criteriaQuery).getSingleResult();
	    } catch (NoResultException e) {
	        return null; 
	    }
	}


	@Override
	public SuperMarketCode find(Class<SuperMarketCode> class1, String string) {
		return entityManager.find(SuperMarketCode.class, "NEW");
	}
}
