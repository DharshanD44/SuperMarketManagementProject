package com.supermarketmanagement.api.Model.Custom.Product;

import java.time.LocalDateTime;

public class SalesProductListDto {

	private Long productId;
	private String productName;
	private String productPackageUnitOfMeasure;
	private Integer productPackQuantity;
	private Double productPrice;
	private LocalDateTime productEffectiveDate;
	private LocalDateTime productLastEffectiveDate;
	private Long TotalPackagesold;

	public Long getProductId() {
		return productId;
	}

	public Long getTotalPackagesold() {
		return TotalPackagesold;
	}

	public void setTotalPackagesold(Long totalPackagesold) {
		TotalPackagesold = totalPackagesold;
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

	public String getProductPackageUnitOfMeasure() {
		return productPackageUnitOfMeasure;
	}

	public void setProductPackageUnitOfMeasure(String productPackageUnitOfMeasure) {
		this.productPackageUnitOfMeasure = productPackageUnitOfMeasure;
	}

	public Integer getProductPackQuantity() {
		return productPackQuantity;
	}

	public void setProductPackQuantity(Integer productPackQuantity) {
		this.productPackQuantity = productPackQuantity;
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

	public LocalDateTime getProductLastEffectiveDate() {
		return productLastEffectiveDate;
	}

	public void setProductLastEffectiveDate(LocalDateTime productLastEffectiveDate) {
		this.productLastEffectiveDate = productLastEffectiveDate;
	}

	public SalesProductListDto(Long productId, String productName, String productPackageUnitOfMeasure,
			Integer productPackQuantity, Double productPrice, LocalDateTime productEffectiveDate,
			LocalDateTime productLastEffectiveDate, Long totalPackagesold) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPackageUnitOfMeasure = productPackageUnitOfMeasure;
		this.productPackQuantity = productPackQuantity;
		this.productPrice = productPrice;
		this.productEffectiveDate = productEffectiveDate;
		this.productLastEffectiveDate = productLastEffectiveDate;
		TotalPackagesold = totalPackagesold;
	}

	
}
