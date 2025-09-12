package com.supermarketmanagement.api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Entity.SuperMarketCode;

@Repository
public interface SuperMarketCodeRepoistory extends JpaRepository<SuperMarketCode, Long>{

	SuperMarketCode findByCode(String customerGender);

	SuperMarketCode findByDescription(String status);
	

}
