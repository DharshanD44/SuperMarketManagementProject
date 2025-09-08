package com.supermarketmanagement.api.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarketmanagement.api.Model.Entity.OrderDetailsModel;

@Repository
public interface OrderDetailsRepoistory extends JpaRepository<OrderDetailsModel, Long>{

	OrderDetailsModel findOrderByOrderId(Long id);
	
}
