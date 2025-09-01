package com.supermarketmanagement.api.Model.Custom.OrderDetails;

import java.time.LocalDateTime;

public class OrderDetailsListDto {

	private Long orderId;
    private LocalDateTime orderDate;
    private Long customerId;  
    private LocalDateTime orderExpectedDate;
    private OrderStatusDto orderStatus; 
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private float totalPrice;

 
    public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public LocalDateTime getOrderExpectedDate() {
		return orderExpectedDate;
	}

	public void setOrderExpectedDate(LocalDateTime orderExpectedDate) {
		this.orderExpectedDate = orderExpectedDate;
	}

	public OrderStatusDto getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatusDto orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public OrderDetailsListDto() {}

	public OrderDetailsListDto(
	        long orderId,
	        LocalDateTime orderDate,
	        long customerId,
	        LocalDateTime orderExpectedDate,
	        OrderStatusDto orderStatus,
	        LocalDateTime createdDate,
	        LocalDateTime updateDate,
	        float totalPrice
	    ) {
	        this.orderId = orderId;
	        this.orderDate = orderDate;
	        this.customerId = customerId;
	        this.orderExpectedDate = orderExpectedDate;
	        this.orderStatus = orderStatus;
	        this.createdDate = createdDate;
	        this.updateDate = updateDate;
	        this.totalPrice = totalPrice;
	    }
}
