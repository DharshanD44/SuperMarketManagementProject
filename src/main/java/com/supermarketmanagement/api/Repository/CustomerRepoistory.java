package com.supermarketmanagement.api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Entity.CustomerModel;

@Repository
public interface CustomerRepoistory extends JpaRepository<CustomerModel, Long>{

	CustomerModel findByCustomerId(Long customerId);

}
