package com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderStatusDto;

public class OrderLineItemDetailsDto {

	private Integer sno;
	private Long orderLineId;
	private Long orderId;
	private Long productId;
	private String productName;
	private Double productPrice;
	private Integer orderQuantityIndividualUnit;
	private Integer orderQuantityInPackage;
	private String orderItemstatus;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDateTime orderedDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDateTime updatedDate;
	
	private float orderItemPrice;

	public OrderLineItemDetailsDto(Long orderLineId, Long orderId, Long productId, String productName,
			Double productPrice, Integer orderQuantityIndividualUnit, Integer orderQuantityInPackage,
			String orderItemstatus,LocalDateTime orderedDate, LocalDateTime updatedDate, float orderItemPrice) {
		this.orderLineId = orderLineId;
		this.orderId = orderId;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.orderQuantityIndividualUnit = orderQuantityIndividualUnit;
		this.orderQuantityInPackage = orderQuantityInPackage;
		this.orderItemstatus = orderItemstatus;
		this.orderedDate=orderedDate;
		this.updatedDate = updatedDate;
		this.orderItemPrice = orderItemPrice;
	}

	
	public LocalDateTime getOrderedDate() {
		return orderedDate;
	}


	public void setOrderedDate(LocalDateTime orderedDate) {
		this.orderedDate = orderedDate;
	}


	public OrderLineItemDetailsDto(Long orderLineId,Long orderId, String orderItemstatus) {
		this.orderLineId=orderLineId;
		this.orderId = orderId;
		this.orderItemstatus = orderItemstatus;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	public Long getOrderLineId() {
		return orderLineId;
	}

	public void setOrderLineId(Long orderLineId) {
		this.orderLineId = orderLineId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getOrderQuantityIndividualUnit() {
		return orderQuantityIndividualUnit;
	}

	public void setOrderQuantityIndividualUnit(Integer orderQuantityIndividualUnit) {
		this.orderQuantityIndividualUnit = orderQuantityIndividualUnit;
	}

	public Integer getOrderQuantityInPackage() {
		return orderQuantityInPackage;
	}

	public void setOrderQuantityInPackage(Integer orderQuantityInPackage) {
		this.orderQuantityInPackage = orderQuantityInPackage;
	}

	public String getOrderItemstatus() {
		return orderItemstatus;
	}

	public void setOrderItemstatus(String orderItemstatus) {
		this.orderItemstatus = orderItemstatus;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public float getOrderItemPrice() {
		return orderItemPrice;
	}

	public void setOrderItemPrice(float orderItemPrice) {
		this.orderItemPrice = orderItemPrice;
	}


}
