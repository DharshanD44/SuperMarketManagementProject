package com.supermarketmanagement.api.Model.Custom.SupplierDetails;

public class SupplierAssignedOrderList {

	private Long orderLineId;
	private Long orderId;
	private String productName;
	private Integer orderQuantityIndividualUnit;
	private Integer orderQuantityInPackage;
	private String status;

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
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public SupplierAssignedOrderList(Long orderLineId, Long orderId, String productName,
			Integer orderQuantityIndividualUnit, Integer orderQuantityInPackage, String status) {
		this.orderLineId = orderLineId;
		this.orderId = orderId;
		this.productName = productName;
		this.orderQuantityIndividualUnit = orderQuantityIndividualUnit;
		this.orderQuantityInPackage = orderQuantityInPackage;
		this.status = status;
	}

	public SupplierAssignedOrderList() {
	}

}
