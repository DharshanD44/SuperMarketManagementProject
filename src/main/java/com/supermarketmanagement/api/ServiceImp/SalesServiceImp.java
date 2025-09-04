package com.supermarketmanagement.api.ServiceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supermarketmanagement.api.Model.Custom.Product.SalesProductListDto;
import com.supermarketmanagement.api.Service.SalesService;
import com.supermarketmanagement.api.dao.SalesDao;

@Service
public class SalesServiceImp implements SalesService{

	@Autowired
	private SalesDao salesDao;
	
	@Override
	public List<SalesProductListDto> findTopSellingProducts(String filter) {
		return salesDao.findTopSellingProducts(filter);
	}

}
