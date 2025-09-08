package com.supermarketmanagement.api.ServiceImp;

import com.supermarketmanagement.api.Model.Custom.ResponseData;
import com.supermarketmanagement.api.Model.Custom.ResponseMessage;
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
		customerListResponse.setStatus(WebServiceUtil.SUCCESS_STATUS);
		customerListResponse.setData(customerDao.getCustomerListDtos());
		return customerListResponse;
	}

	@Override
	public ResponseMessage addorUpdateCustomerDetails(CustomerListDto customerListDto) {
		
		CustomerModel entity;
		ResponseMessage response= new ResponseMessage();
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
				response.setMessage(WebServiceUtil.CUSTOMER_NOT_FOUND);
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
		if(customerListDto.getCustomerId() == null) {
			response.setStatus(WebServiceUtil.SUCCESS_STATUS);
			response.setMessage(WebServiceUtil.CUSTOMER_ADDED);
			return response;	
		}
		else
		{
			response.setStatus(WebServiceUtil.SUCCESS_STATUS);
			response.setMessage(WebServiceUtil.CANT_UPDATE_ORDER );
			return response;	
		}
		
	}

	@Override
	public ResponseMessage deleteCustomerById(Long id) {
		CustomerModel customerModel = customerDao.findByCustomerId(id);
		ResponseMessage response= new ResponseMessage();
		if(customerModel == null)
		{
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setMessage(WebServiceUtil.CUSTOMER_NOT_FOUND);
			return response;
		}
		else
		{
			customerModel.setCustomerLastEffectiveDate(LocalDateTime.now());
			customerModel.setCustomerUpdatedDate(LocalDateTime.now());
			response.setStatus(WebServiceUtil.SUCCESS_STATUS);
			response.setMessage(WebServiceUtil.CUSTOMER_DELETED);
			return response;
		}
		
	}

	@Override
	public Object findCustomerDetailsById(Long id) {
		CustomerListDto customerListDto = customerDao.findCustomerDetailsById(id);
		if(customerListDto==null)
		{
			ResponseMessage message = new ResponseMessage();
			message.setMessage(WebServiceUtil.CUSTOMER_NOT_FOUND+" "+id);
			message.setStatus(WebServiceUtil.FAILED_STATUS);
			return message;
		}
		else
		{
			ResponseData data = new ResponseData();
			data.setData(customerListDto);
			data.setStatus(WebServiceUtil.SUCCESS_STATUS);
			return data;
		}
		
	}

}
