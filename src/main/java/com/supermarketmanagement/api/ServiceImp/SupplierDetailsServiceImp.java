package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.supermarketmanagement.api.Model.Custom.CommonListResponseDto;
import com.supermarketmanagement.api.Model.Custom.CommonMessageResponse;
import com.supermarketmanagement.api.Model.Custom.Customer.CustomerListRequest;
import com.supermarketmanagement.api.Model.Custom.SupplierDetails.SupplierListDto;
import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Model.Entity.SuppliersModel;
import com.supermarketmanagement.api.Service.SupplierDetailsService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;
import com.supermarketmanagement.api.dao.SupplierDetailsDao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SupplierDetailsServiceImp implements SupplierDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsServiceImp.class);
	
	@Autowired
	private SupplierDetailsDao supplierDetailsDao;

	@Autowired
	private SuperMarketCodeDao superMarketCodeDao;

	/**
	 * Retrieves a list of supplier details with filters, pagination, and sorting.
	 *
	 * @param commonListRequestModel request containing filter and pagination info
	 * @return map containing supplier details
	 */
	@Override
	public Object getSuppliersDetails(CustomerListRequest commonListRequestModel) {
		logger.info("getSuppliersDetails: Retrieving supplier details with provided filters and pagination");
		Map<String , Object> supplierList = supplierDetailsDao.getSuppliersDetails(commonListRequestModel);
		
		CommonListResponseDto response = new CommonListResponseDto();
        response.setData(supplierList.get("data"));
        response.setStatus(WebServiceUtil.SUCCESS_STATUS);
        response.setDraw(commonListRequestModel.getDraw());
        response.setRecordsTotal((Long) supplierList.get(WebServiceUtil.KEY_TOTAL_COUNT));
        response.setRecordsFiltered((Long) supplierList.get(WebServiceUtil.KEY_FILTER_COUNT));
        return response;
	}

	
	/**
	 * Retrieves all assigned orders for a specific supplier.
	 *
	 * @param supplierId ID of the supplier
	 * @return list of assigned orders
	 */
	@Override
	public Object getAssignedOrderDetails(Long supplierId) {
		logger.info("getAssignedOrderDetails: Retrieving all assigned orders for supplier ID {}", supplierId);

		return supplierDetailsDao.getAssignedOrderDetails(supplierId);
	}

	
	/**
	 * Adds a new supplier into the system with ACTIVE status.
	 *
	 * @param supplierListDto supplier details DTO
	 * @return {@link CommonMessageResponse} with success message
	 */
	@Override
	public CommonMessageResponse saverUpdateSupplierDetails(SupplierListDto supplierListDto) {
	    logger.info("Request received to add or update supplier: {}", supplierListDto.getSupplierName());

	    CommonMessageResponse response = new CommonMessageResponse();
	    List<String> errors = new ArrayList<>();

	    // Field validations
	    if (supplierListDto.getSupplierName() == null || supplierListDto.getSupplierName().trim().isEmpty()) {
	        errors.add("Supplier name is required");
	    }
	    if (supplierListDto.getMobileNumber() == null 
	            || String.valueOf(supplierListDto.getMobileNumber()).length() != 10) {
	        errors.add("Invalid mobile number. Must be 10 digits");
	    }
	    if (supplierListDto.getEmail() == null || supplierListDto.getEmail().trim().isEmpty()) {
	        errors.add("Email required");
	    } else if (!supplierListDto.getEmail().matches("^[a-z0-9]+@[a-z0-9]+[.](com|in)$")) {
	        errors.add("Invalid email format");
	    }
	    if (supplierListDto.getCity() == null || supplierListDto.getCity().trim().isEmpty()) {
	        errors.add("City is required");
	    }
	    if (supplierListDto.getAddress() == null || supplierListDto.getAddress().trim().isEmpty()) {
	        errors.add("Supplier address is required");
	    }

	    if (!errors.isEmpty()) {
	        response.setStatus(WebServiceUtil.FAILED_STATUS);
	        response.setData(String.join(", ", errors));
	        return response;
	    }

	    boolean isNew = supplierListDto.getSupplierId() == null;
	    SuppliersModel entity;

	    if (isNew) {
	        logger.info("Adding a new supplier");
	        entity = new SuppliersModel();
	        entity.setCreatedDate(LocalDateTime.now());
	        entity.setDeleteFlag(false);
	    } else {
	        logger.info("Updating existing supplier with ID: {}", supplierListDto.getSupplierId());
	        entity = supplierDetailsDao.findBySupplierId(supplierListDto.getSupplierId());
	        if (entity == null) {
	            response.setStatus(WebServiceUtil.FAILED_STATUS);
	            response.setData(WebServiceUtil.SUPPLIER_ID_NOT_FOUND);
	            return response;
	        }
	        entity.setUpdatedDate(LocalDateTime.now());
	    }

	    // Pre-save duplicate checks
	    SuppliersModel existingByMobile = supplierDetailsDao.findByMobile(supplierListDto.getMobileNumber());
	    if (existingByMobile != null && !existingByMobile.getSupplierId().equals(supplierListDto.getSupplierId())) {
	        response.setStatus(WebServiceUtil.FAILED_STATUS);
	        response.setData("Mobile number already registered");
	        return response;
	    }

	    SuppliersModel existingByEmail = supplierDetailsDao.findByEmail(supplierListDto.getEmail());
	    if (existingByEmail != null && !existingByEmail.getSupplierId().equals(supplierListDto.getSupplierId())) {
	        response.setStatus(WebServiceUtil.FAILED_STATUS);
	        response.setData("Email already registered");
	        return response;
	    }

	    // Set entity fields
	    SuperMarketCode activeCode = superMarketCodeDao.findByCode("ACTIVE");
	    entity.setIsActive(activeCode);
	    entity.setSupplierName(supplierListDto.getSupplierName());
	    entity.setAddress(supplierListDto.getAddress());
	    entity.setCity(supplierListDto.getCity().trim());
	    entity.setCountry(supplierListDto.getCountry() != null ? supplierListDto.getCountry().trim() : null);
	    entity.setSupplierEmailId(supplierListDto.getEmail().trim());
	    entity.setSupplierMobileNumber(supplierListDto.getMobileNumber());
	    entity.setPincode(supplierListDto.getPincode());

	    // Save entity with try-catch for safety
	    try {
	        supplierDetailsDao.saveSupplierDetails(entity);
	        response.setStatus(WebServiceUtil.SUCCESS_STATUS);
	        if (isNew) {
	            response.setData(WebServiceUtil.NEW_SUPPLIER_ADDED);
	            logger.info("New supplier added successfully with ID: {}", entity.getSupplierId());
	        } else {
	            response.setData(WebServiceUtil.SUPPLIER_UPDATED);
	            logger.info("Supplier updated successfully with ID: {}", entity.getSupplierId());
	        }
	    } catch (DataIntegrityViolationException e) {
	        Throwable cause = e.getRootCause();
	        String message = cause != null ? cause.getMessage() : e.getMessage();

	        if (message != null) {
	            if (message.contains("mobile_number")) {
	                response.setStatus(WebServiceUtil.FAILED_STATUS);
	                response.setData("Mobile number already registered");
	            } else if (message.contains("email_id")) {
	                response.setStatus(WebServiceUtil.FAILED_STATUS);
	                response.setData("Email already registered");
	            } else {
	                response.setStatus(WebServiceUtil.FAILED_STATUS);
	                response.setData("Database constraint violation");
	            }
	        } else {
	            response.setStatus(WebServiceUtil.FAILED_STATUS);
	            response.setData("Unknown database error");
	        }
	        logger.error("Database constraint violation", e);
	        return response;
	    }

	    return response;
	}


	
	/**
	 * Deletes a supplier by ID by marking it inactive and setting delete flag.
	 *
	 * @param supplierid ID of the supplier
	 * @return {@link CommonMessageResponse} with success/failure/warning status and message
	 */
	@Override
	public Object deleteSupplierById(Long supplierid) {
		logger.info("deleteSupplierById: Deleting supplier with ID {}", supplierid);

		SuppliersModel supplierdetails = supplierDetailsDao.findBySupplierId(supplierid);
		CommonMessageResponse response = new CommonMessageResponse();
		if (supplierdetails == null) {
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(WebServiceUtil.SUPPLIER_ID_NOT_FOUND);
			return response;
		} else {
			if (supplierdetails.getDeleteFlag()) {
				response.setStatus(WebServiceUtil.WARNING_STATUS);
				response.setData(WebServiceUtil.SUPPLIER_ALREADY_DELETED);
				return response;
			} else {
				SuperMarketCode inactivecode = superMarketCodeDao.findByCode("INACTIVE");
				supplierdetails.setDeleteFlag(true);
				supplierdetails.setIsActive(inactivecode);
				supplierdetails.setUpdatedDate(LocalDateTime.now());
				response.setStatus(WebServiceUtil.SUCCESS_STATUS);
				response.setData(WebServiceUtil.SUPPLIER_DELETED);
				return response;
			}
		}
	}


	@Override
	public Object activateOrInactivate(Boolean status, Long supplierid) {
		SuppliersModel supplierDetail = supplierDetailsDao.findBySupplierId(supplierid);
		CommonMessageResponse response = new CommonMessageResponse();
		if(supplierDetail== null) {
			response.setStatus(WebServiceUtil.FAILED_STATUS);
			response.setData(WebServiceUtil.SUPPLIER_ID_NOT_FOUND);
		}
		else {
			if(status) {
		        SuperMarketCode activeCode = superMarketCodeDao.findByCode(WebServiceUtil.STATUS_ACTIVE);
				supplierDetail.setIsActive(activeCode);
				supplierDetail.setUpdatedDate(LocalDateTime.now());
				response.setStatus(WebServiceUtil.SUCCESS_STATUS);
				response.setData(WebServiceUtil.SUPPLIER_STATUS_ACTIVE);
			}
			else
			{
				SuperMarketCode deactivecode = superMarketCodeDao.findByCode(WebServiceUtil.STATUS_INACTIVE);
				supplierDetail.setIsActive(deactivecode);
				supplierDetail.setUpdatedDate(LocalDateTime.now());
				response.setStatus(WebServiceUtil.SUCCESS_STATUS);
				response.setData(WebServiceUtil.SUPPLIER_STATUS_INACTIVE);
			}
		}
		return response;
	}

}
