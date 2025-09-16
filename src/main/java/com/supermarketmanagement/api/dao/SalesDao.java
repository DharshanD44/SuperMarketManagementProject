package com.supermarketmanagement.api.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.supermarketmanagement.api.Model.Custom.CommonResponse;
import com.supermarketmanagement.api.Model.Custom.Product.SalesProductListDto;

public interface SalesDao {

	Map<String, Object> findTopSellingProducts(String filter, LocalDateTime startDate, LocalDateTime endDate);

}
