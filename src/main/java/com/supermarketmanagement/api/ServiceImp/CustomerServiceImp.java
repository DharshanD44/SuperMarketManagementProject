package com.supermarketmanagement.api.ServiceImp;


import com.supermarketmanagement.api.Model.Custom.CommonListResponseDto;
import com.supermarketmanagement.api.Model.Custom.CommonMessageResponse;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListDto;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListRequest;
import com.supermarketmanagement.api.Model.Entity.CustomerModel;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Repository.SuperMarketCodeRepoistory;
import com.supermarketmanagement.api.Service.CustomerService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.CustomerDao;
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImp implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImp.class);

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private SuperMarketCodeRepoistory codeRepoistory;

    @Autowired
    private SuperMarketCodeDao superMarketCodeDao;

    /** * Retrieve customer details with filters and pagination. 
     * 
     * 
     * {@link CustomerDao#getCustomerDetails(CustomerListRequest)} 
     * @param commonListRequestModel request body containing filters, pagination info  
     * @return Map<String, Object> containing customer details */
    
    @SuppressWarnings("unchecked")
	@Override
    public CommonListResponseDto getCustomerDetails(CustomerListRequest commonListRequestModel) {
        logger.info("Fetching customer details with request: {}", commonListRequestModel);
        Map<String, Object> customerDetails = customerDao.getCustomerDetails(commonListRequestModel);
        
        CommonListResponseDto response = new CommonListResponseDto();
        response.setData((List<CustomerListDto>) customerDetails.get("data"));
        response.setStatus(WebServiceUtil.SUCCESS_STATUS);
        response.setDraw(commonListRequestModel.getDraw());
        response.setRecordsTotal((Long) customerDetails.get(WebServiceUtil.KEY_TOTAL_COUNT));
        response.setRecordsFiltered((Long) customerDetails.get(WebServiceUtil.KEY_FILTER_COUNT));

        return response;
    }

    
    /** * Add or update customer details.
     * 
     * {@link CustomerDao#findByCustomerId(Long)} 
     * {@link CustomerDao#saveCustomer(CustomerModel)} 
     * @param customerListDto request body containing customer details 
     * @return CommonResponse with success or failure message 
     */
    @Override
    public CommonMessageResponse addorUpdateCustomerDetails(CustomerListDto customerListDto) {
        logger.info("Request received to add or update customer: {}", customerListDto);

        CommonMessageResponse response = new CommonMessageResponse();
        List<String> errors = new ArrayList<>();

		if (customerListDto.getCustomerFirstName() == null || customerListDto.getCustomerFirstName().trim().isEmpty()) {
			errors.add("Customer first name is required");
		}
		if (customerListDto.getCustomerLastName() == null || customerListDto.getCustomerLastName().trim().isEmpty()) {
			errors.add("Customer last name is required");
		}
		if (customerListDto.getCustomerMobileno() == null
				|| String.valueOf(customerListDto.getCustomerMobileno()).length() != 10) {
			errors.add("Invalid mobile number. Must be 10 digits");
		}
		if (!customerListDto.getCustomerEmail().isEmpty() && !customerListDto.getCustomerEmail().matches("^[a-z0-9]+@[a-z0-9]+[.](com|in)$")) {
			errors.add("Invalid email format");
		}
		if(customerListDto.getCustomerEmail().isEmpty())
			errors.add("Email required");
		
		if (customerListDto.getCustomerCity() == null || customerListDto.getCustomerCity().trim().isEmpty()) {
			errors.add("Customer city is required");
		}
		
		if (customerListDto.getCustomerAddress() == null || customerListDto.getCustomerAddress().trim().isEmpty()) {
			errors.add("Customer address is required");
		}
		if (!errors.isEmpty()) {
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(String.join(", ", errors));
			return response;
		}

		CustomerModel customermodel;
		if (customerListDto.getCustomerId() == null) {
			logger.info("Adding a new customer");
			customermodel = new CustomerModel();
			customermodel.setCustomerCreatedDate(LocalDateTime.now());
		} else {
			logger.info("Updating existing customer with ID: {}", customerListDto.getCustomerId());
			customermodel = customerDao.findByCustomerId(customerListDto.getCustomerId());
			if (customermodel == null) {
				response.setStatus(WebServiceUtil.FAILED_STATUS);
				response.setData(WebServiceUtil.CUSTOMER_NOT_FOUND);
				return response;
			}
			customermodel.setCustomerUpdatedDate(LocalDateTime.now());
		}
		SuperMarketCode gender = codeRepoistory.findByDescription(customerListDto.getCustomerGender());
		if (gender != null) {
			customermodel.setCustomerGender(gender);
		}

        customermodel.setCustomerFirstName(customerListDto.getCustomerFirstName());
        customermodel.setCustomerMiddleName(customerListDto.getCustomerMiddleName());
        customermodel.setCustomerLastName(customerListDto.getCustomerLastName());
        customermodel.setCustomerMobileno(customerListDto.getCustomerMobileno());
        customermodel.setCustomerAddress(customerListDto.getCustomerAddress());
        customermodel.setCustomerLocation(customerListDto.getCustomerLocation());
        customermodel.setCustomerCity(customerListDto.getCustomerCity().trim());
        customermodel.setCustomerPincode(customerListDto.getCustomerPincode());
        customermodel.setCustomerEmail(customerListDto.getCustomerEmail().trim());

        try {
            customerDao.saveCustomer(customermodel);
            response.setStatus(WebServiceUtil.SUCCESS_STATUS);
            if (customerListDto.getCustomerId() == null) {
                response.setData(WebServiceUtil.NEW_CUSTOMER_ADDED);
                logger.info("New customer added successfully with ID: {}", customermodel.getCustomerId());
            } else {
                response.setData(WebServiceUtil.CUSTOMER_UPDATED);
                logger.info("Customer updated successfully with ID: {}", customermodel.getCustomerId());
            }
        } catch (DataIntegrityViolationException e) {
            Throwable cause = e.getMostSpecificCause();
            String message = cause != null ? cause.getMessage() : e.getMessage();

            String userMessage;
            if (message.contains("customer_email")) {
                userMessage = "Email already registered";
            } else if (message.contains("customer_mobileno")) {
                userMessage = "Mobile number already registered";
            } else {
                userMessage = "Database constraint violation";
            }

            response.setStatus(WebServiceUtil.FAILED_STATUS);
            response.setData(userMessage);
            logger.error("Constraint violation", e);
            return response;
        }
        return response;
    }



    /** 
     * Delete a customer by ID.  
     * 
     * {@link CustomerDao#findByCustomerId(Long)} 
     * {@link SuperMarketCodeDao#findByCode(String)} 
     * @param id customer ID (path variable) 
     * @return CommonResponse with success, warning, or failure message 
     */
    @Transactional
    @Override
    public CommonMessageResponse deleteCustomerById(Long id) {
        logger.info("Request received to delete customer with ID: {}", id);
        CustomerModel customerModel = customerDao.findByCustomerId(id);
        CommonMessageResponse response = new CommonMessageResponse();

        if (customerModel == null) {
            logger.info("Customer with ID {} not found. Cannot delete.", id);
            response.setStatus(WebServiceUtil.FAILED_STATUS);
            response.setData(WebServiceUtil.CUSTOMER_NOT_FOUND);
        } else {
            if (customerModel.getCustomerLastEffectiveDate() != null) {
                logger.info("Customer with ID {} is already deleted", id);
                response.setStatus(WebServiceUtil.SUCCESS_STATUS);
                response.setData(WebServiceUtil.CUSTOMER_ALREADY_DELETED);
            } else {
                SuperMarketCode inactivecode = superMarketCodeDao.findByCode(WebServiceUtil.STATUS_INACTIVE);
                customerModel.setCustomerLastEffectiveDate(LocalDateTime.now());
                customerModel.setCustomerStatus(inactivecode);
                customerModel.setCustomerUpdatedDate(LocalDateTime.now());
                response.setStatus(WebServiceUtil.SUCCESS_STATUS);
                response.setData(WebServiceUtil.CUSTOMER_DELETED);
                logger.info("Customer with ID {} marked as deleted", id);
            }
        }
        return response;
    }

    @Transactional
	@Override
	public Object findCustomerDetailsById(Long customerid) {
		CustomerListDto customerList = customerDao.findCustomerDetailsById(customerid);
		CommonMessageResponse response = new CommonMessageResponse();
		if(customerList == null) {
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(WebServiceUtil.CUSTOMER_NOT_FOUND);
		}
		else {
			customerList.setSno(1);
			response.setStatus(WebServiceUtil.SUCCESS_STATUS);
			response.setData(customerList);
		}
		return response;
	}

    @Transactional
	@Override
	public CommonMessageResponse activateOrInactivatedCustomer(Boolean customerStatus, Long customerid) {
		CustomerModel customerList = customerDao.findByCustomerId(customerid);
		CommonMessageResponse response = new CommonMessageResponse();
		if(customerList==null) {
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(WebServiceUtil.CUSTOMER_NOT_FOUND);
		}
		else {
			if(customerStatus) {
		        SuperMarketCode activeCode = codeRepoistory.findByCode(WebServiceUtil.STATUS_ACTIVE);
				customerList.setCustomerStatus(activeCode);
				customerList.setCustomerUpdatedDate(LocalDateTime.now());
				response.setStatus(WebServiceUtil.SUCCESS_STATUS);
				response.setData(WebServiceUtil.CUSTOMER_STATUS_ACTIVE);
			}
			else {
				SuperMarketCode activeCode = codeRepoistory.findByCode(WebServiceUtil.STATUS_INACTIVE);
				customerList.setCustomerStatus(activeCode);
				customerList.setCustomerUpdatedDate(LocalDateTime.now());
				response.setStatus(WebServiceUtil.SUCCESS_STATUS);
				response.setData(WebServiceUtil.CUSTOMER_STATUS_INACTIVE);
			}
		}
		return response;
	}
}


