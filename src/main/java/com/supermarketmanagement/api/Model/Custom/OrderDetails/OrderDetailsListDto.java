package com.supermarketmanagement.api.Model.Custom.OrderDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderDetailsListDto {

	private Long orderId;
	private LocalDateTime orderDate;
	private Long customerId;
	private LocalDate orderExpectedDate;
	private OrderStatusDto orderStatus;
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

	public LocalDate getOrderExpectedDate() {
		return orderExpectedDate;
	}

	public void setOrderExpectedDate(LocalDate orderExpectedDate) {
		this.orderExpectedDate = orderExpectedDate;
	}

	public OrderStatusDto getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatusDto orderStatus) {
		this.orderStatus = orderStatus;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	
	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public OrderDetailsListDto() {
	}

	public OrderDetailsListDto(long orderId, LocalDateTime orderDate, long customerId, LocalDate orderExpectedDate,
			OrderStatusDto orderStatus,LocalDateTime updateDate, float totalPrice) {
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.customerId = customerId;
		this.orderExpectedDate = orderExpectedDate;
		this.orderStatus = orderStatus;
		this.updateDate=updateDate;
		this.totalPrice = totalPrice;
	}
}
