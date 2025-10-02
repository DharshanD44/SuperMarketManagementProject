package com.supermarketmanagement.api.Model.Custom.OrderDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderDetailsListDto {

	private Integer sno;
	private Long orderId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDateTime orderedDate;

	private Long customerId;

	private String customerFirstName;

	private String customerMiddleName;

	private String customerLastName;

	private Long customerMobileno;

	private String customerEmail;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
	private LocalDate orderExpectedDate;

	private String orderStatus;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDateTime updatedDate;
	private float totalPrice;

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerMiddleName() {
		return customerMiddleName;
	}

	public void setCustomerMiddleName(String customerMiddleName) {
		this.customerMiddleName = customerMiddleName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public Long getCustomerMobileno() {
		return customerMobileno;
	}

	public void setCustomerMobileno(Long customerMobileno) {
		this.customerMobileno = customerMobileno;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	public LocalDateTime getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(LocalDateTime orderedDate) {
		this.orderedDate = orderedDate;
	}

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

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public OrderDetailsListDto() {
	}

	public OrderDetailsListDto(long orderId, LocalDateTime orderedDate, long customerId, String customerFirstName,
			String customerMiddleName, String customerLastName, long customerMobileno, String customerEmail,
			LocalDate orderExpectedDate, LocalDateTime updatedDate, String orderStatus, float totalPrice) {
		this.orderId = orderId;
		this.orderedDate = orderedDate;
		this.customerId = customerId;
		this.customerFirstName = customerFirstName;
		this.customerMiddleName = customerMiddleName;
		this.customerLastName = customerLastName;
		this.customerMobileno = customerMobileno;
		this.customerEmail = customerEmail;
		this.orderExpectedDate = orderExpectedDate;
		this.updatedDate = updatedDate;
		this.orderStatus = orderStatus;
		this.totalPrice = totalPrice;
	}
}
