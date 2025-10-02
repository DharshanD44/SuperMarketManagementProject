package com.supermarketmanagement.api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;

@Repository
public interface SuperMarketCodeRepoistory extends JpaRepository<SuperMarketCode, String>{

	SuperMarketCode findByCode(String code);

	SuperMarketCode findByDescription(String description);

	SuperMarketCode findByDescription(SuperMarketCode productStatus);
	

}
