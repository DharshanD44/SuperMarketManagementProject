package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarketmanagement.api.Model.Custom.CommonResponse;
import com.supermarketmanagement.api.Model.Custom.Product.SalesProductListDto;
import com.supermarketmanagement.api.Service.SalesService;
import com.supermarketmanagement.api.dao.SalesDao;

@Service
public class SalesServiceImp implements SalesService{

	@Autowired
	private SalesDao salesDao;
	
	@Override
	public Map<String, Object> findTopSellingProducts(String filter,LocalDateTime startDate, LocalDateTime endDate) {
		return salesDao.findTopSellingProducts(filter,startDate,endDate);
	}

}
