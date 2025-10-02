package com.supermarketmanagement.api.Model.Custom.Product;

import java.time.LocalDate;
public class ActivateDeactivateProductDto {

	private boolean activate;
	private Long productId;
	private LocalDate productEffectiveDate;
	private LocalDate productLastEffectiveDate;

	public boolean isActivate() {
		return activate;
	}

	public void setActivate(boolean activate) {
		this.activate = activate;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public ActivateDeactivateProductDto(boolean activate, Long productId, LocalDate productEffectiveDate,
			LocalDate productLastEffectiveDate) {
		super();
		this.activate = activate;
		this.productId = productId;
		this.productEffectiveDate = productEffectiveDate;
		this.productLastEffectiveDate = productLastEffectiveDate;
	}

}
