package com.supermarketmanagement.api.Model.Custom;

import java.util.List;

public class CommonListRequestModel {
	private Integer start = 0;
	private Integer length = 10;
	private String searchBy;
	private String searchValue;

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

	public CommonListRequestModel(Integer start, Integer length, String searchBy, String searchValue) {
		this.start = start;
		this.length = length;
		this.searchBy = searchBy;
		this.searchValue = searchValue;
	}

	public CommonListRequestModel() {
	}

}
