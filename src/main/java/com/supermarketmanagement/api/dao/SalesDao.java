package com.supermarketmanagement.api.dao;

import java.util.List;

import com.supermarketmanagement.api.Model.Custom.Product.SalesProductListDto;

public interface SalesDao {

	List<SalesProductListDto> findTopSellingProducts(String filter);

}
