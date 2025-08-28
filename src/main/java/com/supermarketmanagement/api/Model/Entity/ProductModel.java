package com.supermarketmanagement.api.Model.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Product_master")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_package_type")
    private String productPackageType;

    @Column(name = "product_pack_quantity")
    private Integer productPackQuantity;

    @Column(name = "product_package_unit_of_measure")
    private String productPackageUnitOfMeasure;

    @Column(name = "product_price")
    private Double productPrice;

    @Column(name = "product_current_stock_package_count")
    private Integer productCurrentStockPackageCount;

    @Column(name = "product_effective_date")
    private LocalDate productEffectiveDate;

    @Column(name = "product_last_effective_date")
    private LocalDate productLastEffectiveDate;

    @Column(name = "old_product_id")
    private Long oldProductId;

    @Column(name = "product_created_date")
    private LocalDate productCreatedDate;

    @Column(name = "product_updated_date")
    private LocalDate productUpdatedtedDate;
    
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    // Getters and Setters

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

    public LocalDate getProductLastEffectiveDate() {
        return productLastEffectiveDate;
    }

    public void setProductLastEffectiveDate(LocalDate productLastEffectiveDate) {
        this.productLastEffectiveDate = productLastEffectiveDate;
    }

    public Long getOldProductId() {
        return oldProductId;
    }

    public void setOldProductId(Long oldProductId) {
        this.oldProductId = oldProductId;
    }

    public LocalDate getProductCreatedDate() {
        return productCreatedDate;
    }

    public void setProductCreatedDate(LocalDate productCreatedDate) {
        this.productCreatedDate = productCreatedDate;
    }

    public LocalDate getProductUpdatedtedDate() {
        return productUpdatedtedDate;
    }

    public void setProductUpdatedtedDate(LocalDate productUpdatedtedDate) {
        this.productUpdatedtedDate = productUpdatedtedDate;
    }

	public ProductModel(String productName, String productPackageType, Integer productPackQuantity,
			String productPackageUnitOfMeasure, Double productPrice, Integer productCurrentStockPackageCount,
			LocalDate productEffectiveDate, LocalDate productLastEffectiveDate, Long oldProductId,
			LocalDate productCreatedDate, LocalDate productUpdatedtedDate) {

		this.productName = productName;
		this.productPackageType = productPackageType;
		this.productPackQuantity = productPackQuantity;
		this.productPackageUnitOfMeasure = productPackageUnitOfMeasure;
		this.productPrice = productPrice;
		this.productCurrentStockPackageCount = productCurrentStockPackageCount;
		this.productEffectiveDate = productEffectiveDate;
		this.productLastEffectiveDate = productLastEffectiveDate;
		this.oldProductId = oldProductId;
		this.productCreatedDate = productCreatedDate;
		this.productUpdatedtedDate = productUpdatedtedDate;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public ProductModel() {
		
	}
	
	
    
    
}

