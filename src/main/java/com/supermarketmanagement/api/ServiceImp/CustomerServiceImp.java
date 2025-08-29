package com.supermarketmanagement.api.ServiceImp;

import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Service.CustomerService;
import com.supermarketmanagement.api.dao.CustomerDao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImp implements CustomerService{
	
	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public List<CustomerListDto> getAllCustomerDetails() {
		
		return customerDao.getCustomerListDtos();
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
			entity = customerDao.findByCustomerId(customerListDto.getCustomerId());
		}
		entity.setCustomerName(customerListDto.getCustomerName());
		entity.setCustomerMobileno(customerListDto.getCustomerMobileno());
		entity.setCustomerAddress(customerListDto.getCustomerAddress());
		entity.setCustomerLocation(customerListDto.getCustomerLocation());
		entity.setCustomerCity(customerListDto.getCustomerCity());
		entity.setCustomerPincode(customerListDto.getCustomerPincode());
		entity.setCustomerEmail(customerListDto.getCustomerEmail());
		
		return entity;
		
	}

	@Override
	public CustomerModel deleteCustomerById(Long id) {
		CustomerModel customerModel = customerDao.findByCustomerId(id);
		customerModel.setCustomerLastEffectiveDate(LocalDate.now());
		return customerModel;
	}

}
