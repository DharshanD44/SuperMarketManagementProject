package com.supermarketmanagement.api.ServiceImp;

import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Service.CustomerService;
import com.supermarketmanagement.api.dao.CustomerDao;

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
		return customerDao.addCustomerDetails(customerListDto);
		
	}

	@Override
	public CustomerModel deleteCustomerById(Long id) {
		return customerDao.deleteCustomerById(id);
		
	}

}
