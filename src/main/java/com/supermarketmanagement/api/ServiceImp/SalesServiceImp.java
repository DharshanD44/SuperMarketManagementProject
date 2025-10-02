package com.supermarketmanagement.api.ServiceImp;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supermarketmanagement.api.Service.SalesService;
import com.supermarketmanagement.api.dao.SalesDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class SalesServiceImp implements SalesService{

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsServiceImp.class);

	
	@Autowired
	private SalesDao salesDao;
	
	
	/**
	 * Retrieves top selled product details.
	 *
	 * @param filter request
	 * @param startdate
	 * @param enddate
	 * @return map of product details
	 */
	@Override
	public Map<String, Object> findTopSellingProducts(String filter,LocalDateTime startDate, LocalDateTime endDate) {
		logger.info("findTopSellingProducts: Retrieving top selling products with filter '{}', from {} to {}", filter, startDate, endDate);

		return salesDao.findTopSellingProducts(filter,startDate,endDate);
	}

}
