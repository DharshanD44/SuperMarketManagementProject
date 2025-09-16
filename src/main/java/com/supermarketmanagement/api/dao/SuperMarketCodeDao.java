package com.supermarketmanagement.api.dao;

import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;

public interface SuperMarketCodeDao {

	SuperMarketCode findByCode(String code);

	SuperMarketCode findByDescription(String code);

}
