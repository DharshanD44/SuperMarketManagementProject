package com.supermarketmanagement.api.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.supermarketmanagement.api.Model.Entity.OrderLineItemDetailsModel;

@Repository
public interface OrderLineItemDetailsRepoistory extends  JpaRepository<OrderLineItemDetailsModel, Long>{
	
    Optional<OrderLineItemDetailsModel> findByOrder_OrderIdAndProduct_ProductId(Long orderId, Long productId);

}
