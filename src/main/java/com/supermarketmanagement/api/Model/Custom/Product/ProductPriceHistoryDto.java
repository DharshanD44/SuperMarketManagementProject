package com.supermarketmanagement.api.Model.Custom.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.supermarketmanagement.api.Util.LocalDateRequestDeserializer;

public class ProductPriceHistoryDto {

	private Long productId;
	
	private String productName;
	
	private Double productPrice;
	
	@JsonDeserialize(using = LocalDateRequestDeserializer.class) 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy") 
	private LocalDate productEffectiveDate;

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

	public LocalDate getProductEffectiveDate() {
		return productEffectiveDate;
	}

	public void setProductEffectiveDate(LocalDate productEffectiveDate) {
		this.productEffectiveDate = productEffectiveDate;
	}

	public ProductPriceHistoryDto(Long productId, String productName, Double productPrice,
			LocalDate productEffectiveDate) {
		
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productEffectiveDate = productEffectiveDate;
	}

	public ProductPriceHistoryDto() {
		
	}
	
	
	
}
