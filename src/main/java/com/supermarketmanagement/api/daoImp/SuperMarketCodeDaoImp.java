package com.supermarketmanagement.api.daoImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;
import com.supermarketmanagement.api.Repository.SuperMarketCodeRepoistory;
import com.supermarketmanagement.api.dao.SuperMarketCodeDao;

@Repository
public class SuperMarketCodeDaoImp implements SuperMarketCodeDao{
	
	@Autowired
	private SuperMarketCodeRepoistory codeRepoistory;

	@Override
	public SuperMarketCode findByCode(String orderStatus) {
		return codeRepoistory.findByCode(orderStatus);
	}

	@Override
	public SuperMarketCode findByDescription(String status) {
		return codeRepoistory.findByDescription(status);
	}

}
