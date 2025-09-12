package com.supermarketmanagement.api.dao;

import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;

public interface SuperMarketCodeDao {

	SuperMarketCode findByCode(String orderStatus);

	SuperMarketCode findByDescription(String status);

}
