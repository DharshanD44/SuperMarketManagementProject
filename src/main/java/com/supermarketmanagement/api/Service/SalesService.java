package com.supermarketmanagement.api.Service;

import java.util.List;

import com.supermarketmanagement.api.Model.Custom.Product.SalesProductListDto;

public interface SalesService {

	List<SalesProductListDto> findTopSellingProducts(String filter);

}
