package com.supermarketmanagement.api.Model.Custom.OrderDetails;

import java.util.List;

public class OrderUpdateRequestDto {
	
	private Long orderId;
    private List<UpdateOrderLineItemsDto> items;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public List<UpdateOrderLineItemsDto> getItems() {
		return items;
	}
	public void setItems(List<UpdateOrderLineItemsDto> items) {
		this.items = items;
	}
	public OrderUpdateRequestDto(Long orderId, List<UpdateOrderLineItemsDto> items) {
		
		this.orderId = orderId;
		this.items = items;
	}
	public OrderUpdateRequestDto() {
		
	}
	
    
    

}
