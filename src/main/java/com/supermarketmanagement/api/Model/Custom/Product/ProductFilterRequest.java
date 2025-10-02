package com.supermarketmanagement.api.Model.Custom.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProductFilterRequest {
	private Boolean isActive;
	private Integer draw;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy") 
	private LocalDate date;
	private String searchBy;
	private String searchValue;
	private Integer start = 0;
	private Integer length = 10;
	private String priceRange;
	private Double rangeValue1;
	private Double rangeValue2;
	private String orderBy;
	private String orderType;


	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public Double getRangeValue1() {
		return rangeValue1;
	}

	public void setRangeValue1(Double rangeValue1) {
		this.rangeValue1 = rangeValue1;
	}

	public Double getRangeValue2() {
		return rangeValue2;
	}

	public void setRangeValue2(Double rangeValue2) {
		this.rangeValue2 = rangeValue2;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

}
