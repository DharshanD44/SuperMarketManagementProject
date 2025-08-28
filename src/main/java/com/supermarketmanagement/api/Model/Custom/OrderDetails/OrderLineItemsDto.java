package com.supermarketmanagement.api.Model.Custom.OrderDetails;

public class OrderLineItemsDto {
	
	private Long productId;
    private Integer orderQuantityIndividualUnit;
    private Integer orderQuantityInPackage;
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
	
	public OrderLineItemsDto(Long productId, Integer orderQuantityIndividualUnit, Integer orderQuantityInPackage) {
		
		this.productId = productId;
		this.orderQuantityIndividualUnit = orderQuantityIndividualUnit;
		this.orderQuantityInPackage = orderQuantityInPackage;
	}
	
	public OrderLineItemsDto() {
		
	}
    
	
    

}
