package com.supermarketmanagement.api.Model.Custom.Customer;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class CustomerListDto {

	private Long customerId;
	private String customerName;
	private String customerMobileno;
	private String customerAddress;
	private String customerLocation;
	private String customerCity;
	private String customerPincode;
	private String customerEmail;


	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobileno() {
		return customerMobileno;
	}

	public void setCustomerMobileno(String customerMobileno) {
		this.customerMobileno = customerMobileno;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerLocation() {
		return customerLocation;
	}

	public void setCustomerLocation(String customerLocation) {
		this.customerLocation = customerLocation;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustomerPincode() {
		return customerPincode;
	}

	public void setCustomerPincode(String customerPincode) {
		this.customerPincode = customerPincode;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public CustomerListDto(Long customerId, String customerName, String customerMobileno, String customerAddress,
			String customerLocation, String customerCity, String customerPincode, String customerEmail) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerMobileno = customerMobileno;
		this.customerAddress = customerAddress;
		this.customerLocation = customerLocation;
		this.customerCity = customerCity;
		this.customerPincode = customerPincode;
		this.customerEmail = customerEmail;
	}

	public CustomerListDto() {

	}

}
