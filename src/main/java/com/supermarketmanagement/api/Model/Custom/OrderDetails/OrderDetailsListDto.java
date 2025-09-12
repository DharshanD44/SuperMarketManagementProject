package com.supermarketmanagement.api.Model.Custom.OrderDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderDetailsListDto {

	private Long orderId;
	private LocalDateTime orderedDate;
	private Long customerId;
	private LocalDate orderExpectedDate;
	private String orderStatus;
	private LocalDateTime updateDate;
	private float totalPrice;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDateTime getOrderDate() {
		return orderedDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderedDate = orderDate;
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

	public OrderDetailsListDto(long orderId, LocalDateTime orderedDate, long customerId, LocalDate orderExpectedDate,
			LocalDateTime updateDate,String orderStatus, float totalPrice) {
		this.orderId = orderId;
		this.orderedDate = orderedDate;
		this.customerId = customerId;
		this.orderExpectedDate = orderExpectedDate;
		this.updateDate=updateDate;
		this.orderStatus = orderStatus;
		this.totalPrice = totalPrice;
	}
}
