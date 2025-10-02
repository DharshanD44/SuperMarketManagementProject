package com.supermarketmanagement.api.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.supermarketmanagement.api.Model.Custom.CommonMessageResponse;
import com.supermarketmanagement.api.Model.Custom.Product.SalesProductListDto;

public interface SalesService {

	Map<String, Object> findTopSellingProducts(String filter, LocalDateTime startDate, LocalDateTime endDate);

}
