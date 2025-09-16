package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.CommonResponse;
import com.supermarketmanagement.api.Model.Custom.SupplierDetails.SupplierListDto;
import com.supermarketmanagement.api.Model.Entity.SuppliersModel;
import com.supermarketmanagement.api.Service.SupplierDetailsService;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.SupplierDetailsDao;

@Service
public class SupplierDetailsServiceImp implements SupplierDetailsService{
	
	@Autowired
	private SupplierDetailsDao supplierDetailsDao;

	@Override
	public Map<String, Object> getSuppliersDetails(CommonListRequestModel commonListRequestModel) {
		return supplierDetailsDao.getSuppliersDetails(commonListRequestModel);
	}

	@Override
	public Object getAssignedOrderDetails(Long supplierId) {
		return supplierDetailsDao.getAssignedOrderDetails(supplierId);
	}

	@Override
	public Object addSupplierDetails(SupplierListDto supplierListDto) {
		SuppliersModel supplierList = new SuppliersModel();
		CommonResponse response = new CommonResponse();
		supplierList.setSupplierName(supplierListDto.getSupplierName());
		supplierList.setAddress(supplierListDto.getAddress());
		supplierList.setCity(supplierListDto.getCity());
		supplierList.setCountry(supplierListDto.getCountry());
		supplierList.setSupplierEmailId(supplierListDto.getEmail());
		supplierList.setIsActive(true);
		supplierList.setSupplierMobileNumber(supplierListDto.getMobileNumber());
		supplierList.setPincode(supplierListDto.getPincode());
		supplierList.setCreatedDate(LocalDateTime.now());
		supplierList.setDeleteFlag(false);
		supplierDetailsDao.saveSupplierDetails(supplierList);
		response.setStatus(WebServiceUtil.SUCCESS_STATUS);
		response.setMessage(WebServiceUtil.NEW_SUPPLIER_ADDED);
		return response;
	}

}
