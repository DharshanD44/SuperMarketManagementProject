package com.supermarketmanagement.api.Model.Custom.OrderLineItemDetails;

import java.time.LocalDateTime;

import com.supermarketmanagement.api.Model.Custom.OrderDetails.OrderStatusDto;

public class OrderLineItemDetailsDto {

    private Long orderLineId;
    private Long orderId;
    private Long productId;
    private Integer orderQuantityIndividualUnit;
    private Integer orderQuantityInPackage;
    private String status;
    private LocalDateTime updateDate;
    private float price;

    public OrderLineItemDetailsDto(Long orderLineId, Long orderId, Long productId,
                                   Integer orderQuantityIndividualUnit, Integer orderQuantityInPackage,
                                   OrderStatusDto statusDto, LocalDateTime updateDate, float price) {
        this.orderLineId = orderLineId;
        this.orderId = orderId;
        this.productId = productId;
        this.orderQuantityIndividualUnit = orderQuantityIndividualUnit;
        this.orderQuantityInPackage = orderQuantityInPackage;
        this.status = statusDto != null ? statusDto.name() : null;  
        this.updateDate = updateDate;
        this.price = price;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
    
    
}

