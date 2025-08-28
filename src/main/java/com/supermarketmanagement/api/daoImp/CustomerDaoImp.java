package com.supermarketmanagement.api.daoImp;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.apigateway.model.Op;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
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
			    root.get("customerCreatedDate")
			);

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public CustomerModel addCustomerDetails(CustomerListDto customerListDto) {
				
		CustomerModel entity;
		
		if(customerListDto.getCustomerId() == null)
		{
			entity = new CustomerModel();
			entity.setCustomerCreatedDate(LocalDate.now());
		}
		else
		{
			entity = customerRepoistory.findById(customerListDto.getCustomerId()).orElseThrow(
					()->
						new RuntimeException("Customer with Id "+customerListDto.getCustomerId()+" not found")
					);
		}
		entity.setCustomerName(customerListDto.getCustomerName());
		entity.setCustomerMobileno(customerListDto.getCustomerMobileno());
		entity.setCustomerAddress(customerListDto.getCustomerAddress());
		entity.setCustomerLocation(customerListDto.getCustomerLocation());
		entity.setCustomerCity(customerListDto.getCustomerCity());
		entity.setCustomerPincode(customerListDto.getCustomerPincode());
		entity.setCustomerEmail(customerListDto.getCustomerEmail());
		
		return customerRepoistory.save(entity);

	}

	@Override
	public CustomerModel deleteCustomerById(Long id) {
		CustomerModel customerModel = customerRepoistory.findById(id).orElseThrow(
				()->new RuntimeException("Customer with id "+id+" not found!")
				);
		customerModel.setCustomerLastEffectiveDate(LocalDate.now());
	
		return customerRepoistory.save(customerModel);
	}
	
	

}
