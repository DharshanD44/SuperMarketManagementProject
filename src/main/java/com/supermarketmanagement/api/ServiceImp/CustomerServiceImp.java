package com.supermarketmanagement.api.ServiceImp;

import com.supermarketmanagement.api.Model.Custom.Response;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListResponse;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerMessageDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Service.CustomerService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.CustomerDao;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerServiceImp implements CustomerService{
	
	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public CustomerListResponse getAllCustomerDetails() {
		CustomerListResponse customerListResponse = new CustomerListResponse();
		customerDao.getCustomerListDtos();
		customerListResponse.setStatus(WebServiceUtil.SUCCESS_STATUS);
		customerListResponse.setCustomerListDtos(customerDao.getCustomerListDtos());
		return customerListResponse;
	}

	@Override
	public Response addorUpdateCustomerDetails(CustomerListDto customerListDto) {
		
		CustomerModel entity;
		Response response= new Response();
		if(customerListDto.getCustomerId() == null)
		{
			entity = new CustomerModel();
			entity.setCustomerCreatedDate(LocalDateTime.now());
		}
		else
		{
			entity = customerDao.findByCustomerId(customerListDto.getCustomerId());
			if(entity==null)
			{
				response.setStatus(WebServiceUtil.FAILED_STATUS);
				response.setData(CustomerMessageDto.CUSTOMER_NOT_FOUND);
				return response;
			}
			entity.setCustomerUpdatedDate(LocalDateTime.now());
		}
		entity.setCustomerName(customerListDto.getCustomerName());
		entity.setCustomerMobileno(customerListDto.getCustomerMobileno());
		entity.setCustomerAddress(customerListDto.getCustomerAddress());
		entity.setCustomerLocation(customerListDto.getCustomerLocation());
		entity.setCustomerCity(customerListDto.getCustomerCity());
		entity.setCustomerPincode(customerListDto.getCustomerPincode());
		entity.setCustomerEmail(customerListDto.getCustomerEmail());
		
		customerDao.saveCustomer(entity);
//		entity.setCustomerUpdatedDate(customerListDto.getCustomerUpdatedDate());
		response.setStatus(WebServiceUtil.SUCCESS_STATUS);
		response.setData(CustomerMessageDto.CUSTOMER_UPDATED);
		return response;
		
	}

	@Override
	public String deleteCustomerById(Long id) {
		CustomerModel customerModel = customerDao.findByCustomerId(id);
		customerModel.setCustomerLastEffectiveDate(LocalDateTime.now());
		return CustomerMessageDto.CUSTOMER_DELETED;
	}

}
