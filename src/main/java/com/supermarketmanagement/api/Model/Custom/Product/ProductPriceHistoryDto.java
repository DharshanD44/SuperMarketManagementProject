package com.supermarketmanagement.api.Model.Custom.Product;

import java.time.LocalDateTime;

public class ProductPriceHistoryDto {

	private Long productId;
	
	private String productName;
	
	private Double productPrice;
	
	private LocalDateTime productEffectiveDate;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public LocalDateTime getProductEffectiveDate() {
		return productEffectiveDate;
	}

	public void setProductEffectiveDate(LocalDateTime productEffectiveDate) {
		this.productEffectiveDate = productEffectiveDate;
	}

	public ProductPriceHistoryDto(Long productId, String productName, Double productPrice,
			LocalDateTime productEffectiveDate) {
		
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productEffectiveDate = productEffectiveDate;
	}

	public ProductPriceHistoryDto() {
		
	}
	
	
	
}
