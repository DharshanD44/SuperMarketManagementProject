package com.supermarketmanagement.api.Model.Custom.OrderDetails;

import java.time.LocalDate;
import java.util.List;

public class OrderRequestDto {
	
	private Long customerId;
    private List<OrderLineItemsDto> items;
    
    
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public List<OrderLineItemsDto> getItems() {
		return items;
	}
	public void setItems(List<OrderLineItemsDto> items) {
		this.items = items;
	}
	public OrderRequestDto(Long customerId, List<OrderLineItemsDto> items) {
		
		this.customerId = customerId;
		this.items = items;
	}
	public OrderRequestDto() {
		
	}
    
    

}
