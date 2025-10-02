package com.supermarketmanagement.api.Model.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.supermarketmanagement.api.Util.LocalDateDeserializer;
import com.supermarketmanagement.api.Util.LocalDateSerializer;

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

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@Column(name = "product_effective_date")
	private LocalDate productEffectiveDate;

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@Column(name = "product_last_effective_date")
	private LocalDate productLastEffectiveDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_status", referencedColumnName = "CODE")
	private SuperMarketCode productStatus;

	@Column(name = "old_product_id")
	private Long oldProductId;

	@Column(name = "product_created_date")
	private LocalDateTime productCreatedDate;

	@Column(name = "product_updated_date")
	private LocalDateTime productUpdatedtedDate;

	@Column(name = "is_deleted")
	private Boolean isDeleted = false;
	
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

	public LocalDateTime getProductCreatedDate() {
		return productCreatedDate;
	}

	public void setProductCreatedDate(LocalDateTime productCreatedDate) {
		this.productCreatedDate = productCreatedDate;
	}

	public LocalDateTime getProductUpdatedtedDate() {
		return productUpdatedtedDate;
	}

	public void setProductUpdatedtedDate(LocalDateTime productUpdatedtedDate) {
		this.productUpdatedtedDate = productUpdatedtedDate;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public SuperMarketCode getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(SuperMarketCode productStatus) {
		this.productStatus = productStatus;
	}

	public ProductModel() {
		this.productStatus = new SuperMarketCode();
		this.productStatus.setCode("ACTIVE");
	}

}
