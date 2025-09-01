package com.supermarketmanagement.api.daoImp;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerMessageDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Repository.CustomerRepoistory;
import com.supermarketmanagement.api.dao.CustomerDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
	public List<CustomerListDto> getCustomerListDtos() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CustomerListDto> criteriaQuery = cb.createQuery(CustomerListDto.class);
		
		Root<CustomerModel> root = criteriaQuery.from(CustomerModel.class);
		
		criteriaQuery.multiselect(
			    root.get("customerId"),
			    root.get("customerName"),
			    root.get("customerMobileno"),
			    root.get("customerAddress"),
			    root.get("customerLocation"),
			    root.get("customerCity"),
			    root.get("customerPincode"),
			    root.get("customerEmail"),
			    root.get("customerCreatedDate"),
			    root.get("customerUpdatedDate")
			).where(root.get("customerLastEffectiveDate").isNull());

		return entityManager.createQuery(criteriaQuery).getResultList();
	}


	@Override
	public CustomerModel findByCustomerId(Long customerId) {
		
		return customerRepoistory.findById(customerId).orElseThrow(
				()-> new RuntimeException(CustomerMessageDto.CUSTOMER_NOT_FOUND +" "+customerId)
				);
	}
	
	

}
