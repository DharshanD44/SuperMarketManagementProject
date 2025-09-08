package com.supermarketmanagement.api.Model.Custom;

import java.util.List;

public class CommonListResponse<T> {

    private String status;
    private T data;

    public CommonListResponse(String status, T data) {
		super();
		this.status = status;
		this.data = data;
	}

	public CommonListResponse() {
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

    

}

