package com.supermarketmanagement.api.Model.Custom.OrderDetails;

public class UpdateOrderLineItemsDto {

	private Long orderLineId;
	private Integer orderQuantityIndividualUnit;
	private Integer orderQuantityInPackage;

	public Long getOrderLineId() {
		return orderLineId;
	}

	public void setOrderLineId(Long orderLineId) {
		this.orderLineId = orderLineId;
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

	public UpdateOrderLineItemsDto(Long orderLineId, Integer orderQuantityIndividualUnit,
			Integer orderQuantityInPackage) {

		this.orderLineId = orderLineId;
		this.orderQuantityIndividualUnit = orderQuantityIndividualUnit;
		this.orderQuantityInPackage = orderQuantityInPackage;
	}

	public UpdateOrderLineItemsDto() {

	}

}
