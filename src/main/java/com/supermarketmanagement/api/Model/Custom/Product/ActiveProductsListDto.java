package com.supermarketmanagement.api.Model.Custom.Product;

import java.time.LocalDate;

public class ActiveProductsListDto {

	private Long productId;
    private String productName;
    private String productPackageType;
    private Integer productPackQuantity;
    private String productPackageUnitOfMeasure;
    private Double productPrice;
    private Integer productCurrentStockPackageCount;
    private LocalDate productEffectiveDate;
    private LocalDate productCreatedDate;

    public ActiveProductsListDto(Long productId, String productName, String productPackageType,
                          Integer productPackQuantity, String productPackageUnitOfMeasure,
                          Double productPrice, Integer productCurrentStockPackageCount,
                          LocalDate productEffectiveDate, LocalDate productCreatedDate) {
        this.productId = productId;
        this.productName = productName;
        this.productPackageType = productPackageType;
        this.productPackQuantity = productPackQuantity;
        this.productPackageUnitOfMeasure = productPackageUnitOfMeasure;
        this.productPrice = productPrice;
        this.productCurrentStockPackageCount = productCurrentStockPackageCount;
        this.productEffectiveDate = productEffectiveDate;
        this.productCreatedDate = productCreatedDate;
    }

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

	public String getProductPackageType() {
		return productPackageType;
	}

	public void setProductPackageType(String productPackageType) {
		this.productPackageType = productPackageType;
	}

	public Integer getProductPackQuantity() {
		return productPackQuantity;
	}

	public void setProductPackQuantity(Integer productPackQuantity) {
		this.productPackQuantity = productPackQuantity;
	}

	public String getProductPackageUnitOfMeasure() {
		return productPackageUnitOfMeasure;
	}

	public void setProductPackageUnitOfMeasure(String productPackageUnitOfMeasure) {
		this.productPackageUnitOfMeasure = productPackageUnitOfMeasure;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductCurrentStockPackageCount() {
		return productCurrentStockPackageCount;
	}

	public void setProductCurrentStockPackageCount(Integer productCurrentStockPackageCount) {
		this.productCurrentStockPackageCount = productCurrentStockPackageCount;
	}

	public LocalDate getProductEffectiveDate() {
		return productEffectiveDate;
	}

	public void setProductEffectiveDate(LocalDate productEffectiveDate) {
		this.productEffectiveDate = productEffectiveDate;
	}

	public LocalDate getProductCreatedDate() {
		return productCreatedDate;
	}

	public void setProductCreatedDate(LocalDate productCreatedDate) {
		this.productCreatedDate = productCreatedDate;
	}
    
    
	
}
