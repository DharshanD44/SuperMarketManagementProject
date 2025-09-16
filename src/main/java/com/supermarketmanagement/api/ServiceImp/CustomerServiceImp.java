package com.supermarketmanagement.api.ServiceImp;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.CommonResponse;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Repository.SuperMarketCodeRepoistory;
import com.supermarketmanagement.api.Service.CustomerService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.CustomerDao;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerServiceImp implements CustomerService{
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImp.class);
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private SuperMarketCodeRepoistory codeRepoistory;

	@Override
	public CommonResponse addorUpdateCustomerDetails(CustomerListDto customerListDto) {
		
		logger.info("Request received to add or update customer: {}", customerListDto);
		CustomerModel entity;
		CommonResponse response= new CommonResponse();
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
		SuperMarketCode gender = codeRepoistory.findByCode(customerListDto.getCustomerGender());
		
		entity.setCustomerName(customerListDto.getCustomerName());
		entity.setCustomerMobileno(customerListDto.getCustomerMobileno());
		entity.setCustomerAddress(customerListDto.getCustomerAddress());
		entity.setCustomerLocation(customerListDto.getCustomerLocation());
		entity.setCustomerCity(customerListDto.getCustomerCity());
		entity.setCustomerPincode(customerListDto.getCustomerPincode());
		entity.setCustomerEmail(customerListDto.getCustomerEmail());
		entity.setCustomerGender(gender);
		
		customerDao.saveCustomer(entity);
		if(customerListDto.getCustomerId() == null) {
			response.setStatus(WebServiceUtil.SUCCESS_STATUS);
			response.setMessage(WebServiceUtil.NEW_CUSTOMER_ADDED);
			logger.info("New customer added with ID: {}", entity.getCustomerId());
		}
		else
		{
			response.setStatus(WebServiceUtil.SUCCESS_STATUS);
			response.setMessage(WebServiceUtil.CUSTOMER_UPDATED );
			logger.info("Customer with ID {} updated successfully.", entity.getCustomerId());
		}
		return response;
		
	}

	@Override
	public CommonResponse deleteCustomerById(Long id) {
		CustomerModel customerModel = customerDao.findByCustomerId(id);
		CommonResponse response= new CommonResponse();
		if(customerModel == null)
		{
			logger.warn("Customer with ID {} not found. Cannot delete.", id);
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setMessage(WebServiceUtil.CUSTOMER_NOT_FOUND);
			return response;
		}
		else
		{
			logger.info("Customer with ID {} marked as deleted.", id);
			customerModel.setCustomerLastEffectiveDate(LocalDateTime.now());
			customerModel.setCustomerUpdatedDate(LocalDateTime.now());
			response.setStatus(WebServiceUtil.SUCCESS_STATUS);
			response.setMessage(WebServiceUtil.CUSTOMER_DELETED);
			return response;
		}
		
	}

	@Override
	public Map<String, Object> getCustomerDetails(CommonListRequestModel commonListRequestModel) {
			return customerDao.getCustomerDetails(commonListRequestModel);
	}

}
