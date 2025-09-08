package com.supermarketmanagement.api.Service;

import java.util.List;

import com.supermarketmanagement.api.Model.Custom.CommonListResponse;

public interface OrderLineDetailsService {

	Object updateLineOrderLineStatus(String status, List<Long> id);

	CommonListResponse<?> getOrderLineDetails();

	Object getOrderLineDetailsById(Long lineid);

}
