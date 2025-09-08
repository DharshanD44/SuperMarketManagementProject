package com.supermarketmanagement.api.Model.Custom.OrderDetails;

import java.util.List;

public class OrderListResponse {

	private String status;
	private List<OrderDetailsListDto> data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrderDetailsListDto> getData() {
		return data;
	}

	public void setData(List<OrderDetailsListDto> data) {
		this.data = data;
	}

	public OrderListResponse(String status, List<OrderDetailsListDto> data) {
		this.status = status;
		this.data = data;
	}

	public OrderListResponse() {

	}

}
