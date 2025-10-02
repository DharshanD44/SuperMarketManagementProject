package com.supermarketmanagement.api.Model.Custom.Customer;

public class CustomerListDto {

	private Integer sno;
	private Long customerId;
	private String customerFirstName;
	private String customerMiddleName;
	private String customerLastName;
	private String customerGender;
	private Long customerMobileno;
	private String customerAddress;
	private String customerLocation;
	private String customerCity;
	private String customerPincode;
	private String customerEmail;
	private String customerStatus;

	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	public String getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}

	public String getCustomerGender() {
		return customerGender;
	}

	public void setCustomerGender(String customerGender) {
		this.customerGender = customerGender;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerMiddleName() {
		return customerMiddleName;
	}

	public void setCustomerMiddleName(String customerMiddleName) {
		this.customerMiddleName = customerMiddleName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public Long getCustomerMobileno() {
		return customerMobileno;
	}

	public void setCustomerMobileno(Long customerMobileno) {
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

	public CustomerListDto(Long customerId, String customerFirstName,String customerMiddleName,String customerLastName,String customerGender, Long customerMobileno,
			String customerAddress, String customerLocation, String customerCity, String customerPincode,
			String customerEmail, String customerStatus) {
		this.customerId = customerId;
		this.customerFirstName = customerFirstName;
		this.customerMiddleName = customerMiddleName;
		this.customerLastName = customerLastName;
		this.customerGender = customerGender;
		this.customerMobileno = customerMobileno;
		this.customerAddress = customerAddress;
		this.customerLocation = customerLocation;
		this.customerCity = customerCity;
		this.customerPincode = customerPincode;
		this.customerEmail = customerEmail;
		this.customerStatus = customerStatus;
	}

	public CustomerListDto() {

	}

}
